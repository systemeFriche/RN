import com.sun.star.lang.XComponent;
import com.sun.star.sheet.XSpreadsheet;


public class Principale {
		
	public static void main(String[] args)
	{	
		SpreadsheetDocuments spreadsheetDocumentsNotes = new SpreadsheetDocuments();
		SpreadsheetDocuments spreadsheetDocumentsRn = new SpreadsheetDocuments();
		SpreadsheetDocuments spreadsheetDocumentsRecap = new SpreadsheetDocuments();
		XComponent xDocNotes, xDocRN;
		XSpreadsheet feuilleEPR, feuilleELP, feuilleRN;

		//A TESTER MAIS A PRIORI CHEMIN RELATIF ?
        String workDir="/Users/fguntz/Documents/NOTES/NOTES_2016-2017";
		
		try 
		{
			//**********************************************************************************
			// OPERATION DE RECUPERATION DES INFOS DE LA PROMO
			//**********************************************************************************
			//A TERME LE NOM DU FICHIER JSON DE LA PROMO SERAIT PASSE EN ARGUMENT DE L'APPLI
			//POUR L'INSTANT ON CHANGE A LA MANO
			//MMI1-S1
			//String cheminFichierJSON = workDir+"/mmi1S1.json";
			//MMI2-S3
			//String cheminFichierJSON = workDir+"/mmi2S3.json";
			//MMI2A-S3
			String cheminFichierJSON = workDir+"/mmi2S3A.json";
			
			
			//INITIALISATION PROMO	
			Promo promo = new Promo();
			promo.getInfosPromo(cheminFichierJSON);
			
			//**********************************************************************************
			// DEFINITION DE TOUS LES CHEMINS DE FICHIER ER REPERTOIRE
			//**********************************************************************************
			String semestre=promo.getSemestre();
			
			//chemin destination RN
			String workDestRNODS="RN_"+semestre+"_ODS/";
			String workDestRNPDF="RN_"+semestre+"_PDF/";	
			//chemin fichier Recap
			String fichierRecapPromo = "recap_"+semestre+".ods";	

			String modelRN = "1617_RN_"+semestre+"_model.ods";
			String dest = workDestRNODS+"1617_RN_"+semestre+"_";
			
			String fichierLocalNotes=Divers.getNomFichierUrl(promo.getUrlNotes());
			String cheminFichierLocalNotes="file:///"+workDir+"/"+fichierLocalNotes;
			String cheminFichierRecapPromo = "file:///"+workDir+"/"+fichierRecapPromo;
			String cheminFichierRn="file:///"+workDir+"/"+modelRN;
			String cheminFichierRnEtuOdsBase="file:///"+workDir+"/"+dest;
			String cheminFichierRnEtuPdfBase="file:///"+workDir+"/"+workDestRNPDF;
			
			
			//**********************************************************************************
			// OPERATION DE RECUPERATION SUR LE RESEAU DU FICHIER EXPORT DE NOTES
			//**********************************************************************************
			
			String filename = workDir+"/"+fichierLocalNotes;
			//à placer en argument de l'appli
			boolean onLine=true;
			if(onLine){Divers.getFichierNotes(filename,promo.getUrlNotes());}
			
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
			Divers.addMessage("fichier notes local ouvert - "+cheminFichierLocalNotes);
			//ON RECUPERE FEUILLE CONTENANT LES MODULES ET LES EPREUVES
			feuilleELP=spreadsheetDocumentsNotes.getFeuille(xDocNotes,0);		
			feuilleEPR=spreadsheetDocumentsNotes.getFeuille(xDocNotes,1);

			//ON OUVRE FICHIER MODELE RELEVE DE NOTES
			xDocRN = spreadsheetDocumentsRn.openSpreadsheetDocument(cheminFichierRn);
			//ON RECUPERE FEUILLE MODULE RN
			feuilleRN=spreadsheetDocumentsRn.getFeuille(xDocRN,0);
		
			//**********************************************************************************
			// OPERATION DE RECUPERATION DE LA STRUCTURE D'UNE PROMO : UE, MODULES, EPREUVES
			// AU PASSAGE ON RECUPERE TOUTES LES NOTES CONNUES ET CALCULEES PAR APOGEE
			// AU PASSAGE ON DETERMINE MIN, MAX, MOY ET CLASSEMENT EPREUVE
			//***********************************************************************************

			promo.getStructureEtNotesPromo(cheminFichierJSON,spreadsheetDocumentsNotes,feuilleELP,feuilleEPR,spreadsheetDocumentsRn,feuilleRN);
			
			//ON FERME RELEVE DE NOTES
			spreadsheetDocumentsRn.closeSpreadsheetDocument(xDocRN);
			
			//**********************************************************************************
			// OPERATION DE CALCUL MOYENNE DE MODULE DE CHAQUE UE ET MOYENNE GENERALE
			// ET INFOS MIN, MAX, MOY CLASSEMENT POUR CHAQUE MODULE, UE ET MOY GENERALE
			// AU PASSAGE ON VERIFIE COHERENCE ENTRE CALCUL INTERNE ET CALCUL APOGEE
			//**********************************************************************************
			promo.verificationMoyennesPromo();
			
			//**********************************************************************************
			// OPERATION DE GENERATION DES RELEVES DE NOTES
			// RECUPERATION DES NOTES DE CHAQUE ETUDIANT
			//**********************************************************************************			
			promo.initialisationListeEtudiants(spreadsheetDocumentsNotes,feuilleELP);
			promo.generationFicheEtudiant();
			promo.generationRnEtudiant(spreadsheetDocumentsRn,cheminFichierRn,cheminFichierRnEtuOdsBase,cheminFichierRnEtuPdfBase);
			
			//**********************************************************************************
			// OPERATION DE GENERATION DU RECAP PROMO
			//**********************************************************************************
			promo.generationRecapPromo(spreadsheetDocumentsRecap,cheminFichierRecapPromo);
			
			//FERMETURE FICHIER NOTES
			
			//ON FERME LE FICHIER DE NOTES
			spreadsheetDocumentsNotes.closeSpreadsheetDocument(xDocNotes);
			Divers.addMessage("fichier notes local fermé - "+cheminFichierLocalNotes);

			//ON SORT DU PROGRAMME
			Divers.addMessage("programme terminé");
			System.exit(0);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
				
	}
}
