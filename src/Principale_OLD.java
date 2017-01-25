import java.util.ArrayList;
import java.util.List;

import com.sun.star.beans.PropertyVetoException;
import com.sun.star.beans.UnknownPropertyException;
import com.sun.star.lang.IllegalArgumentException;
import com.sun.star.lang.WrappedTargetException;
import com.sun.star.lang.XComponent;
import com.sun.star.sheet.XSpreadsheet;


public class Principale_OLD {

	public static String workDir="/Users/fguntz/Documents/NOTES/NOTES_2016-2017";
	public static String workDestRNODS="RN_OLD/";
	public static String workDestRNPDF="RN_PDF_OLD/";	

	public static String fichierLocalNotes = "notesmmi1.xlsx";
	public static String fichierLocalNotesODS = "notesmmi1.ods";

	public static String fichierRecapPromo = "recap_s1.ods";	

	public static String modelRN = "1617_RN_S1_model_OLD.ods";
	public static String dest = workDestRNODS+"1617_RN_S1_";
	//public static String destPDF = workDestRNPDF+"1617_RN_S1_";	
	public static String destPDF = workDestRNPDF;
	
	
	public static void main(String[] args) 
	{	
		int codeEtu,i,j,ligneEpreuve,nbrNotes, nbrNotesSaisies,ligneModuleRN, colonneRecap;
		int nbrNotesEpreuve,precisionDecimale;
		double noteEtu,seuilNote;
		double noteSportEtu;
		double noteModule;
		String cheminFichierRNEtu="file:///"+workDir+"/"+dest;
		String cheminFichierLocalNotes="file:///"+workDir+"/"+fichierLocalNotes;
		String cheminFichierRecapPromo = "file:///"+workDir+"/"+fichierRecapPromo;
		String cheminFichierRNEtuPDF;
		String nomEtu, prenomEtu, classementEtu;
		String codeModule;
		
		SpreadsheetDocuments spreadsheetDocumentsNotes = new SpreadsheetDocuments();
		SpreadsheetDocuments spreadsheetDocumentsRN = new SpreadsheetDocuments();
		SpreadsheetDocuments spreadsheetDocumentsRecap = new SpreadsheetDocuments();
		
		XComponent xDocNotes = null, xDocRN=null, xDocRecap=null;
		
		XSpreadsheet feuilleEPR=null, feuilleELP, feuilleRN, feuilleRecap;
		
		Promo promoOLD = new Promo();
		promoOLD.setUrlNotes("www-apogee.univ-lemans.fr/Version_2.01/statistiques/bo/mmi/notesmmi1.xlsx");
		promoOLD.initialisationListeModules();
		promoOLD.initialisationListeEpreuves();		
		
		try 
		{
			
			//**********************************************************************************
			// OPERATION DE RECUPERATION DES INFOS DE LA PROMO
			//**********************************************************************************
			String cheminFichierJSON = workDir+"/mmi1S1.json";
			
			//INITIALISATION PROMO
			promoOLD.getInfosPromo(cheminFichierJSON);					
			
			//**********************************************************************************
			// OPERATION DE RECUPERATION SUR LE RESEAU DU FICHIER EXPORT DE NOTES
			//**********************************************************************************
			
			String filename = workDir+"/"+fichierLocalNotes;
			//ANCIEN SYSTEM
			getFichierNotes(filename,promoOLD.getUrlNotes());		
			
			//**********************************************************************************
			// OUVERTURE DU FICHIER EXPORT DE NOTES
			// RECUPERATION FEUILLE NOTES SEM, UE ET MODULES
			// RECUPERATION FEUILLE NOTES EPREUVES
			// OUVERTURE DU FICHIER RECAP PROMO
			// RECUPERATION FEUILLE RECAP PROMO
			// OUVERTURE MODELE RELEVE DE NOTES
			// RECUPERATION FEUILLE RELEVE DE NOTES
			//**********************************************************************************
			
			//ON OUVRE FICHIER NOTES
			xDocNotes = spreadsheetDocumentsNotes.openSpreadsheetDocument(cheminFichierLocalNotes);
			System.out.println("fichier notes local ouvert - "+cheminFichierLocalNotes);
			//ON RECUPERE FEUILLE CONTENANT LES MODULES ET LES EPREUVES
			feuilleELP=spreadsheetDocumentsNotes.getFeuille(xDocNotes,0);		
			feuilleEPR=spreadsheetDocumentsNotes.getFeuille(xDocNotes,1);		
			//ON OUVRE FICHIER RECAP PROMO		
			xDocRecap = spreadsheetDocumentsRecap.openSpreadsheetDocument(cheminFichierRecapPromo);		
			System.out.println("fichier recap promo ouvert - "+cheminFichierRecapPromo);	
			//ON RECUPERE FEUILLE RECAP PROMO
			feuilleRecap=spreadsheetDocumentsRecap.getFeuille(xDocRecap,0);
			
			String cheminFichierRN="file:///"+workDir+"/"+modelRN;
			
		
		//**********************************************************************************
		// OPERATION DE RECUPERATION NOTES ET DETERMINATION INFOS MIN MAX MOY CLASSEMENT 
		//**********************************************************************************	
		
		//ANCIEN SYSTEM
		
		promoOLD.initialisationListeEpreuves();
		promoOLD.initialisationListeModules();
		traitementNotesSaisies(spreadsheetDocumentsNotes,feuilleEPR,promoOLD);
		
		
		//**********************************************************************************
		// OPERATION DE CALCUL MOYENNE DE MODULE DE CHAQUE UE ET MOYENNE GENERALE
		// ET INFOS MIN, MAX, MOY CLASSEMENT POUR CHAQUE MODULE, UE ET MOY GENERALE
		// AU PASSAGE ON VERIFIE COHERENCE ENTRE CALCUL INTERNE ET CALCUL APOGEE
		//**********************************************************************************
		

		//POUR OPTIMISIER L'APPLI IL FAUDRAIT FAIRE CETTE ETAPE EN JAVA
			
		//GENERATION RELEVE DE NOTES DE CHAQUE ETUDIANT
		//ON PARSE TOUS LES ETUDIANTS ONGLET EPR
		
		i=1;
		//LECTURE CODE ETU
		codeEtu=spreadsheetDocumentsNotes.getCodeEtu(feuilleEPR,i);	
		
		do{
			
			Epreuve epreuve1 = new Epreuve();
			
			//ON OUVRE LE MODELE DE RELEVE DE NOTES
			cheminFichierRN="file:///"+workDir+"/"+modelRN;
			xDocRN = spreadsheetDocumentsRN.openSpreadsheetDocument(cheminFichierRN);
			System.out.println("fichier modèle RN ouvert - "+cheminFichierRN);
			
			//A REVOIR
			//A PRIORI PAS BESOIN ON L'A DEJA FAIT AVANT
			feuilleRN=spreadsheetDocumentsRN.getFeuille(xDocRN,0);
		
			//ON RECUPERE INFOS ETUDIANTS : NOM, PRENOM
			nomEtu=spreadsheetDocumentsNotes.getNomEtu(feuilleEPR,i);	
			prenomEtu=spreadsheetDocumentsNotes.getPrenomEtu(feuilleEPR,i);	
			
			//ON ECRIT DONNEES ETUDIANTS
			spreadsheetDocumentsRN.writeCodeEtuRN(feuilleRN,codeEtu);
			spreadsheetDocumentsRN.writeNomEtuRN(feuilleRN,nomEtu);		
			spreadsheetDocumentsRN.writePrenomEtuRN(feuilleRN,prenomEtu);
			
			//ON ECRIT NOTE DE SPORT SUR LE RN
			//ANCIEN SYSTEM
			
			noteSportEtu  = promoOLD.getNoteSportRN(spreadsheetDocumentsNotes,feuilleELP,codeEtu);
			spreadsheetDocumentsRN.writeNoteSportRN(feuilleRN,noteSportEtu);			
			
			
			//ON PARSE TOUTES LES EPREUVES
			//ANCIEN SYSTEM
			
			 for(j=0;j<promoOLD.listeEpreuves.size();j++){
				epreuve1=promoOLD.listeEpreuves.get(j);
				//SI L'EPREUVE A ETE SAISIE
				if(epreuve1.getNbrNotes()!=0){
					//RECUERATION CODE EPREUVE
					String codeEpreuve = epreuve1.getCodeEpreuve();
					//RECUPERATION NOTE DE CETTE EPREUVE DE NOTRE ETUDIANT
					noteEtu=epreuve1.recuperationNoteEtu(codeEtu);
					//RECUPERATION CLASSEMENT A CETTE EPREUVE DE NOTRE ETUDIANT
					classementEtu=epreuve1.recuperationClassementEtu(codeEtu);				
					//RECUPERATION NUMERO LIGNE RN
					ligneEpreuve=epreuve1.getLigneEpreuveRN();
					//ON ECRIT LA NOTE DANS LE RN SI PAS ABJ
					if(noteEtu!=-1){
						spreadsheetDocumentsRN.writeNoteEtuRN(feuilleRN,ligneEpreuve,noteEtu);
					}

					//ON ECRIT MIN-MAX-MOY-CLASSEMENT
					spreadsheetDocumentsRN.writeMinRN(feuilleRN,ligneEpreuve,epreuve1.getMin());
					spreadsheetDocumentsRN.writeMaxRN(feuilleRN,ligneEpreuve,epreuve1.getMax());
					spreadsheetDocumentsRN.writeMoyRN(feuilleRN,ligneEpreuve,epreuve1.getMoy());
					spreadsheetDocumentsRN.writeClassementRN(feuilleRN,ligneEpreuve,classementEtu);
					
					//SI EPREUVE DE LV2 ON MODIFIE INTITULE ET NOM ENSEIGNANT
					if((codeEpreuve.equals("711EN118T1"))||(codeEpreuve.equals("711EN118P1"))||(codeEpreuve.equals("711EN119T1"))||(codeEpreuve.equals("711EN119P1"))||(codeEpreuve.equals("711EN120T1"))||(codeEpreuve.equals("711EN120P1"))){
						String intituleEpreuve;
						String enseignantEpreuve;
						
						intituleEpreuve = epreuve1.getIntituleEpreuve();
						enseignantEpreuve = epreuve1.getEnseignantEpreuve();
						spreadsheetDocumentsRN.writeIntituleRN(feuilleRN,ligneEpreuve,intituleEpreuve);						
						spreadsheetDocumentsRN.writeEnseignantRN(feuilleRN,ligneEpreuve,enseignantEpreuve);							
					}
				}
			}
			
			
			//A EXECUTER AVANT QUAND CALCUL JAVA MODULE, UE ET MOY GENERAL
			
			//ON RECUPERE CALCUL ODS MODULE, UE ET MOY GENERAL
			//on ajoute les notes des modules de l'étudiant à notre objet listeModules
			//et on ajoute les notes des modules de l'étudiant dans l'onglet recapPromo
			//on vérifie cohérence entre module calcul interne et module calcul APOGEE
			//ANCIEN SYSTEM
			
			 promoOLD.ajoutNotesModuleEtu(spreadsheetDocumentsRN,feuilleRN,feuilleELP,spreadsheetDocumentsNotes,feuilleRecap,codeEtu);

			
			//ON ENREGISTRE EN ODS LE RELEVE DE NOTES DE L'ETUDIANT EN COURS
			cheminFichierRNEtu="file:///"+workDir+"/"+dest+nomEtu+"_"+prenomEtu+".ods";
			spreadsheetDocumentsRN.saveSpreadsheetDocumentODS(xDocRN,cheminFichierRNEtu);
			//ON FERME LE RN DE L'ETUDIANT
			spreadsheetDocumentsRN.closeSpreadsheetDocument(xDocRN);
			System.out.println("RN ODS de "+nomEtu+" "+prenomEtu + " généré et fermé");
			
			
			i=i+1;
			codeEtu=spreadsheetDocumentsNotes.getCodeEtu(feuilleEPR,i);

			
		}while(codeEtu!=0);
		
		//ON LANCE CALCUL CLASSEMENT ET INFOS POUR TOUS LES MODULES
		promoOLD.setClassementInfosModules();
		
		
		//**********************************************************************************
		// OPERATION ECRITURE RELEVE DE NOTES DE CHAQUE ETUDIANT
		//**********************************************************************************
		
		//ON MET A JOUR RN DE CHAQUE ETUDIANT
		//ON PARSE TOUS LES ETUDIANTS
		i=1;
		
		do{
			
			Module module1 = new Module();
			
			//LECTURE CODE, NOM, PRENOM ETU			
			codeEtu=spreadsheetDocumentsNotes.getCodeEtu(feuilleEPR,i);				
			nomEtu=spreadsheetDocumentsNotes.getNomEtu(feuilleEPR,i);	
			prenomEtu=spreadsheetDocumentsNotes.getPrenomEtu(feuilleEPR,i);	
			
			//ON OUVRE LE MODELE DE RELEVE DE NOTES
			cheminFichierRNEtu="file:///"+workDir+"/"+dest+nomEtu+"_"+prenomEtu+".ods";
			cheminFichierRNEtuPDF="file:///"+workDir+"/"+destPDF+codeEtu+".pdf";
			
			xDocRN = spreadsheetDocumentsRN.openSpreadsheetDocument(cheminFichierRNEtu);
			System.out.println("fichier étudiant RN ouvert - "+cheminFichierRNEtu);
			//A REVOIR
			//A PRIORI PAS BESOIN ON L'A DEJA FAIT AVANT
			feuilleRN=spreadsheetDocumentsRN.getFeuille(xDocRN,0);
			
			//ON PARSE TOUS LES MODULES
			
			 for(j=0;j<promoOLD.listeModules.size();j++){
				module1=promoOLD.listeModules.get(j);
				
				//RECUPERATION CLASSEMENT A CETTE EPREUVE DE NOTRE ETUDIANT
				classementEtu=module1.recuperationClassementEtu(codeEtu);				
				//RECUPERATION NUMERO LIGNE RN EN FONCTION CODE EPEUVE
				ligneModuleRN=module1.getLigneModuleRN();
				nbrNotes=module1.getNbrNotes();
				//ON MET A JOUR INFOS MODULE S'IL Y A DES NOTES
				if(nbrNotes>0){
					//ON ECRIT MIN-MAX-MOY-CLASSEMENT
					spreadsheetDocumentsRN.writeMinRN(feuilleRN,ligneModuleRN,module1.getMin());
					spreadsheetDocumentsRN.writeMaxRN(feuilleRN,ligneModuleRN,module1.getMax());
					spreadsheetDocumentsRN.writeMoyRN(feuilleRN,ligneModuleRN,module1.getMoy());
					spreadsheetDocumentsRN.writeClassementRN(feuilleRN,ligneModuleRN,classementEtu);
				}
			}
			
			//ON ENREGISTRE EN ODS LE RELEVE DE NOTES DE L'ETUDIANT EN COURS
			spreadsheetDocumentsRN.saveSpreadsheetDocumentODS(xDocRN,cheminFichierRNEtu);
			//ON ENREGISTRE EN PDF LE RELEVE DE NOTES DE L'ETUDIANT EN COURS
			spreadsheetDocumentsRN.exportSpreadsheetDocumentPDF(xDocRN,cheminFichierRNEtuPDF);
			
			//ON FERME LE RN DE L'ETUDIANT
			spreadsheetDocumentsRN.closeSpreadsheetDocument(xDocRN);
			System.out.println("RN ODS de "+nomEtu+" "+prenomEtu + " màj et fermé");
			
			i=i+1;
			codeEtu=spreadsheetDocumentsNotes.getCodeEtu(feuilleEPR,i);
			
		}while(codeEtu!=0);
		
		
		//**********************************************************************************
		// OPERATION GENERATION EXPORT RECAP PROMO
		//**********************************************************************************
		
		
		//ON CONSTRUIT RECAP PAR ORDRE DECROISSANT MOY GENERALE
		
		Module moyenneGeneralePromo = new Module();
		
		moyenneGeneralePromo=promoOLD.getModule("711SEM11");
		List<NoteModule> listeNotesModule = new ArrayList<NoteModule>();		
		listeNotesModule=moyenneGeneralePromo.getListeNotesModule();
		
		int ligneEtu;
		
		//On parse tous les étudiants du tableau listeNotesModule
		for(i=0;i<listeNotesModule.size();i++){
			
			codeEtu = listeNotesModule.get(i).getCodeEtu();
			ligneEtu=spreadsheetDocumentsNotes.getLigneEtu(feuilleEPR,codeEtu,promoOLD.getNbrEtu());
			nomEtu=spreadsheetDocumentsNotes.getNomEtu(feuilleEPR,ligneEtu);
			prenomEtu=spreadsheetDocumentsNotes.getPrenomEtu(feuilleEPR,ligneEtu);
			
			//ON ECRIT INFOS ETU DANS RECAP
			spreadsheetDocumentsRecap.setInfosEtuRecap(feuilleRecap, i, codeEtu, nomEtu, prenomEtu);
			
			//ON CHERCHE TOUS LES MODULES DE L'ETUDIANT
			//PARSING LISTEMODULES
			
			//ON PARSE TOUS LES MODULES
			for(j=0;j<promoOLD.listeModules.size();j++){
				
				Module module1 = new Module();
				
				module1=promoOLD.listeModules.get(j);
				nbrNotes=module1.getNbrNotes();
				colonneRecap=module1.getColonneRecap();
				noteModule=module1.recuperationNoteEtu(codeEtu);
				codeModule=module1.getCodeApogeeModule();
				seuilNote = module1.getSeuilNote();
				precisionDecimale = module1.getPrecisionDecimale();
							
				//ON MET A JOUR INFOS MODULE RECAP S'IL Y A DES NOTES
				if(nbrNotes>0){
					//SI ETU PAS ABJ SUR LE MODULE
					if(noteModule!=-1){
						spreadsheetDocumentsRecap.writeNoteModuleRecapOLD(feuilleRecap,colonneRecap,i+1,noteModule,precisionDecimale,seuilNote);
					}
				}
			}			
		}
		
		
		//**********************************************************************************
		// OPERATION FERMETURE FICHIER RECAP PROMO ET FICHIER NOTES
		//**********************************************************************************	
		
		//ON ENREGISTRE ET ON FERME LE FICHIER NOTES
		spreadsheetDocumentsNotes.saveSpreadsheetDocumentODS(xDocNotes,cheminFichierLocalNotes);
		spreadsheetDocumentsNotes.closeSpreadsheetDocument(xDocNotes);
		System.out.println("fichier notes local enregistré et fermé");
		//ON ENREGISTRE ET ON FERME LE FICHIERS RECAP PROMO
		spreadsheetDocumentsRecap.saveSpreadsheetDocumentODS(xDocRecap,cheminFichierRecapPromo);
		spreadsheetDocumentsRecap.closeSpreadsheetDocument(xDocRecap);
		System.out.println("fichier recap promo enregistré et fermé");

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		System.out.println("Terminé");
		
	}
	
	public static void getFichierNotes(String filename, String url) {
		
		//String url="http://perso.univ-lemans.fr/~fguntz/essai.ods";
		////String cheminDestFichierReseau=workDir+"/"+fichierLocalNotes;
	    ////Divers.getFileURL(cheminDestFichierReseau,url);
		try{
			Divers.getFileURL2(filename,url);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}		
	}

	
	//**********************************************************************************
	// OPERATION DE RECUPERATION NOTES ET DETERMINATION INFOS MIN MAX MOY CLASSEMENT 
	//**********************************************************************************	
	
	public static void traitementNotesSaisies(SpreadsheetDocuments spreadsheetDocumentsNotes, XSpreadsheet feuilleEPR, Promo promo){
		
		//ON PARSE TOUTES LES NOTES ON MàJ LES NOTES SAISIES
		//POUR CHAQUE NOTE SAISIE ON CALCULE LES DIFFERENTES INFOS : MIN, MOY, MAX, CLASSEMENT 
		int i=3;
				
		//TANT QU'IL Y A UN CODE EPREUVE ON CONTINUE LE PARSING DES NOTES
		int nbrNotesSaisies=0;
				
		//DETERMINATION NBR ETUDIANTS PROMO
		promo.setNbrEtu(spreadsheetDocumentsNotes.getNbrEtudiants(feuilleEPR));
				
		//LECTURE CODE EPREUVE
		String codeEpreuve=spreadsheetDocumentsNotes.getCodeEpreuve(feuilleEPR,i);	
		do{
					
			Epreuve epreuve1 = new Epreuve();
				
			//RECUPERATION EPREUVE
			epreuve1=promo.getEpreuve(codeEpreuve);
			//RECUPERATION NOMBRE DE NOTES DE CETTE EPREUVE
			int nbrNotesEpreuve=spreadsheetDocumentsNotes.getNbrNotes(feuilleEPR,i,promo.getNbrEtu());
					
			if(nbrNotesEpreuve!=0){
				//MàJ NBR NOTES EPREUVE SAISIE
				epreuve1.setNbrNotes(nbrNotesEpreuve);
				//RECUPERATION DES NOTES DE CETTE EPREUVE
				epreuve1.setArray(spreadsheetDocumentsNotes.getNotes2(feuilleEPR,i,promo.getNbrEtu()));
				//CALCUL INFOS DE CETTE EPREUVE - MIN,MAX,MOY,NBR NOTES
				epreuve1.setInfosEpreuve(promo.getNbrEtu());	
				//CALCUL CLASSEMENT DE CETTE EPREUVE
				epreuve1.setClassementEpreuve(promo.getNbrEtu());			
				//ON AJOUTE CETTE EPREUVE A LISTEEPREUVESSAISIES
				//listeEpreuveSaisie.add(epreuve1);
				nbrNotesSaisies+=1;
			}
					
			i=i+1;
			codeEpreuve=spreadsheetDocumentsNotes.getCodeEpreuve(feuilleEPR,i);
					
		}while(!(codeEpreuve.equals("")));
				
		System.out.println("nbr de notes saisies : "+ nbrNotesSaisies);
		
	}
	
}
