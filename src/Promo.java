import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sun.star.lang.XComponent;
import com.sun.star.sheet.XSpreadsheet;

import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import javax.json.stream.JsonParser;

class Promo extends Element {

	private String semestre;
	private int nbrEtu;
	private String urlNotes;
	private List<Integer> tableauIndiceCodeEtu = new ArrayList<Integer>();
	private List<Etudiant> listeEtudiants = new ArrayList<Etudiant>();

	//La liste des UE du semestre
	List<Ue> listeUe = new ArrayList<Ue>(); 
    
    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }
	
	public String getUrlNotes() {
        return urlNotes;
    }
	
    public void setUrlNotes(String urlNotes) {
        this.urlNotes = urlNotes;
    }
		
    public int getNbrEtu() {
        return nbrEtu;
    }

    public void setNbrEtu(int nbrEtu) {
        this.nbrEtu = nbrEtu;
    }
    
    public List<Ue> getListeUe() {
        return listeUe;
    }
 
	public void setListeUe(List<Ue> UE1){
        this.listeUe = UE1;	
	}
	
    public List<Integer> getTableauIndiceCodeEtu() {
        return tableauIndiceCodeEtu;
    }
 
	public void setTableauIndiceCodeEtu(ArrayList<Integer> tableauIndiceCodeEtu){
        this.tableauIndiceCodeEtu = tableauIndiceCodeEtu;	
	}
	
    public List<Etudiant> getListeEtudiant() {
        return listeEtudiants;
    }
 
	public void setListeEtudiant(List<Etudiant> listeEtudiants){
        this.listeEtudiants = listeEtudiants;	
	}
    
    public void getInfosPromo(String cheminFichierJSON) {
		
		JsonReader reader = null;
		
		try {
			reader = Json.createReader(new FileReader(cheminFichierJSON));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		JsonObject obj = reader.readObject();
		
		//on récupère le nom de la promo
		this.setNomElement(obj.getString("nomPromo"));
		//on récupère le code APOGEE du semestre
		this.setCodeApogeeElement(obj.getString("codeApogee"));
		//on récupère le semestre
		this.setSemestre(obj.getString("semestre"));
		//on récupère le nombre d'étudiants;
		this.setNbrEtu(obj.getInt("nbrEtu"));
		//on récupère la colonne du fichier Recap de cet élément
		this.setColonneRecap(Divers.conversionLettreChiffre(obj.getString("colonneRecap")));
		//on récupère le chemin du fichier des notes
		this.setUrlNotes(obj.getString("urlNotes"));
		//initialisation précision décimale
		this.setPrecisionDecimale(3);
		//initialisation seuil
		this.setSeuilNote(10.0);
		
    }
	
    public void getStructureEtNotesPromo(String cheminFichierJSON, SpreadsheetDocuments spreadsheetDocumentsNotes, XSpreadsheet feuilleELP, XSpreadsheet feuilleEPR, SpreadsheetDocuments spreadsheetDocumentsRN, XSpreadsheet feuilleRN) {
    			
		JsonReader reader = null;
		int colonneExportApogee;
		int nbrEtu=this.getNbrEtu();
		int nbrNotes;
		
		//on récupère la colonne de la moyenne du semestre dans le fichier d'export des notes
		colonneExportApogee=spreadsheetDocumentsNotes.getColonneNoteExport(feuilleELP,this.getCodeApogeeElement());
		this.setColonneExportApogee(colonneExportApogee);
		//on récupère la ligne de la moyenne du semestre dans le fichier relevé de notes		
		this.setLigneRn(spreadsheetDocumentsRN.getLigneElementRN(feuilleRN,this.getCodeApogeeElement()));
		//initialisation précision décimale
		this.setPrecisionDecimale(3);
		//initialisation seuil
		this.setSeuilNote(10.0);
		//Si notes calculées on les récupère
		this.getNotesExportElement(spreadsheetDocumentsNotes, feuilleELP, colonneExportApogee,nbrEtu);
		
		try {
			reader = Json.createReader(new FileReader(cheminFichierJSON));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		JsonObject obj = reader.readObject();	
		//on récupère toutes les UE
		JsonArray results = obj.getJsonArray("UE");
		
		for (JsonObject result : results.getValuesAs(JsonObject.class)) {	
			Ue ue1 = new Ue();
			//on récupère l'intitulé de l'UE
			ue1.setNomElement(result.getString("intitule"));
			//on récupère le code APOGEE de l'UE
			ue1.setCodeApogeeElement(result.getString("codeApogee"));
			//on récupère le coeff de l'UE
			double coeff = (double)result.getJsonNumber("coeff").doubleValue();
			ue1.setCoeffElement(coeff);
			//on récupère la colonne du fichier Recap de cet élément
			ue1.setColonneRecap(Divers.conversionLettreChiffre(result.getString("colonneRecap")));
			//initialisation précision décimale
			ue1.setPrecisionDecimale(3);
			//initialisation seuil
			ue1.setSeuilNote(8.0);
			//on récupère la colonne de l'UE dans le fichier d'export des notes
			colonneExportApogee=spreadsheetDocumentsNotes.getColonneNoteExport(feuilleELP,ue1.getCodeApogeeElement());
			ue1.setColonneExportApogee(colonneExportApogee);
			//on récupère la ligne de l'UE dans le fichier relevé de notes		
			ue1.setLigneRn(spreadsheetDocumentsRN.getLigneElementRN(feuilleRN,ue1.getCodeApogeeElement()));
			//Si notes calculées on les récupère
			ue1.getNotesExportElement(spreadsheetDocumentsNotes, feuilleELP, colonneExportApogee,nbrEtu);
			
				//ON NE RECUPERE PAS LA STRUCTURE D'UNE UEL
				if(!(result.getBoolean("UEL"))){	
			
				//on récupère les modules de l'UE
				JsonArray resultsModule = result.getJsonArray("module");
			
				for(JsonObject resultModule : resultsModule.getValuesAs(JsonObject.class)){
					Module module1 = new Module();
					//on récupère l'intitulé du module
					module1.setNomElement(resultModule.getString("intitule"));
					//on récupère le code APOGEE du module
					module1.setCodeApogeeElement(resultModule.getString("codeApogee"));
					//on récupère le coefficient du module
					module1.setCoeffElement((double)resultModule.getJsonNumber("coeff").doubleValue());
					//on récupère la colonne du fichier Recap de cet élément
					module1.setColonneRecap(Divers.conversionLettreChiffre(resultModule.getString("colonneRecap")));
					//on récupère la colonne du module dans le fichier d'export des notes
					colonneExportApogee=spreadsheetDocumentsNotes.getColonneNoteExport(feuilleELP,module1.getCodeApogeeElement());
					module1.setColonneExportApogee(colonneExportApogee);
					//on récupère la ligne de la moyenne du semestre dans le fichier relevé de notes		
					module1.setLigneRn(spreadsheetDocumentsRN.getLigneElementRN(feuilleRN,module1.getCodeApogeeElement()));
					//initialisation précision décimale
					module1.setPrecisionDecimale(3);
					//initialisation seuil
					module1.setSeuilNote(8.0);
					//Si notes calculées on les récupère
					module1.getNotesExportElement(spreadsheetDocumentsNotes, feuilleELP, colonneExportApogee,nbrEtu);
				
					//on récupère éventuellement le tableau de sous-modules
					JsonArray resultsSousModule = resultModule.getJsonArray("sous-module");
				
					if(resultsSousModule==null){
						//il n'y a pas de sous-module
						JsonArray resultsEpreuve = resultModule.getJsonArray("epreuve");
					
						//S'il y a des épreuves
						if(resultsEpreuve!=null){
							for(JsonObject resultEpreuve : resultsEpreuve.getValuesAs(JsonObject.class)){
								Epreuve epreuve1 = new Epreuve();
								//on récupère l'intitulé de l'épreuve
								epreuve1.setNomElement(resultEpreuve.getString("intitule"));
								//on récupère le code APOGEE de l'épreuve
								epreuve1.setCodeApogeeElement(resultEpreuve.getString("codeApogee"));
								//on récupère le coefficient de l'épreuve
								epreuve1.setCoeffElement((double)resultEpreuve.getJsonNumber("coeff").doubleValue());
								//on récupère la colonne de l'épreuve dans le fichier d'export des notes
								colonneExportApogee=spreadsheetDocumentsNotes.getColonneNoteExport(feuilleEPR,epreuve1.getCodeApogeeElement());
								epreuve1.setColonneExportApogee(colonneExportApogee);
								//on récupère la ligne de la moyenne du semestre dans le fichier relevé de notes		
								epreuve1.setLigneRn(spreadsheetDocumentsRN.getLigneElementRN(feuilleRN,epreuve1.getCodeApogeeElement()));
								//initialisation précision décimale
								epreuve1.setPrecisionDecimale(1);
								//Si notes calculées on les récupère et on récupère le nombre de notes récupérées
								nbrNotes=epreuve1.getNotesExportElement(spreadsheetDocumentsNotes, feuilleEPR, colonneExportApogee,nbrEtu);
								//on initialise le nombre de notes calculées
								epreuve1.setNbrNotes(nbrNotes);
								if(nbrNotes!=0){
									//Détermination min,max,moy épreuve
									epreuve1.setInfosElement(nbrEtu);
									//Détérmination Classement épreuve
									epreuve1.setClassementElement(nbrEtu);
								}
								module1.getListeElements().add(epreuve1);
							}						
						}
					}
					//il y a un sous-module
					else{
						//ATTENTION AU S4 il faudra encore ajouter un niveau de sous-module
						//A PRIORI LES SOUS-MODULE N'APPARAISSE PAS DANS LE RN
						for(JsonObject resultSousModule : resultsSousModule.getValuesAs(JsonObject.class)){
							Module module2= new Module();
							//on récupère l'intitulé du module
							module2.setNomElement(resultSousModule.getString("intitule"));
							//on récupère le code APOGEE du module
							module2.setCodeApogeeElement(resultSousModule.getString("codeApogee"));
							//on récupère le coefficient de l'épreuve
							module2.setCoeffElement((double)resultSousModule.getJsonNumber("coeff").doubleValue());
							//on récupère la colonne du module dans le fichier d'export des notes
							colonneExportApogee=spreadsheetDocumentsNotes.getColonneNoteExport(feuilleELP,module2.getCodeApogeeElement());
							module2.setColonneExportApogee(colonneExportApogee);
							//on récupère la ligne de la moyenne du semestre dans le fichier relevé de notes		
							module2.setLigneRn(spreadsheetDocumentsRN.getLigneElementRN(feuilleRN,module2.getCodeApogeeElement()));
							//initialisation précision décimale
							module2.setPrecisionDecimale(3);
							//initialisation seuil
							module2.setSeuilNote(8.0);
							//Si notes calculées on les récupère
							module2.getNotesExportElement(spreadsheetDocumentsNotes, feuilleELP, colonneExportApogee,nbrEtu);
							JsonArray resultsEpreuve = resultSousModule.getJsonArray("epreuve");
						
							//S'il y a des épreuves
							if(resultsEpreuve!=null){
								for(JsonObject resultEpreuve : resultsEpreuve.getValuesAs(JsonObject.class)){
									Epreuve epreuve1 = new Epreuve();
									//on récupère l'intitulé de l'épreuve
									epreuve1.setNomElement(resultEpreuve.getString("intitule"));
									//on récupère le code APOGEE de l'épreuve
									epreuve1.setCodeApogeeElement(resultEpreuve.getString("codeApogee"));
									//on récupère le coefficient de l'épreuve
									epreuve1.setCoeffElement((double)resultEpreuve.getJsonNumber("coeff").doubleValue());
									//on récupère la colonne de l'épreuve dans le fichier d'export des notes
									colonneExportApogee=spreadsheetDocumentsNotes.getColonneNoteExport(feuilleEPR,epreuve1.getCodeApogeeElement());
									epreuve1.setColonneExportApogee(colonneExportApogee);
									//on récupère la ligne de la moyenne du semestre dans le fichier relevé de notes		
									epreuve1.setLigneRn(spreadsheetDocumentsRN.getLigneElementRN(feuilleRN,epreuve1.getCodeApogeeElement()));
									//initialisation précision décimale
									epreuve1.setPrecisionDecimale(1);
									//Si notes calculées on les récupère et on récupère le nombre de notes récupérées
									nbrNotes=epreuve1.getNotesExportElement(spreadsheetDocumentsNotes, feuilleEPR, colonneExportApogee,nbrEtu);
									//on initialise le nombre de notes calculées
									epreuve1.setNbrNotes(nbrNotes);
									if(nbrNotes!=0){
										//Détermination min,max,moy épreuve
										epreuve1.setInfosElement(nbrEtu);
										//Détérmination Classement épreuve
										epreuve1.setClassementElement(nbrEtu);
									}
									module2.getListeElements().add(epreuve1);	
								}
							}
							module1.getListeElements().add(module2);
						}
					}
				
					ue1.getListeModules().add(module1);	
				}
			
			}
			
	    	this.listeUe.add(ue1);
		}
		
    }

	public void verificationMoyennesPromo() {
		
		Ue ue;
		List<Ue> listeUe;// = new ArrayList<Ue>();
		List<Module> listeModulesUe;// = new ArrayList<Module2>();
		
		Module module,sousModule;
		int nbrEtu=this.getNbrEtu();
		
		int i,j,k;
		
		listeUe=this.getListeUe();
		
		//on parcourt tous les UE pour calculer leur moyenne sauf l'UEL
		for(i=0;i<listeUe.size()-1;i++){
			
			ue=listeUe.get(i);
			listeModulesUe=ue.getListeModules();
			
			//on parcourt tous les modules de chaque UE			
			for(j=0;j<listeModulesUe.size();j++){
				
				module=listeModulesUe.get(j);
				
				//si les éléments de module sont des sous-modules il faut descendre encore d'un niveau
				//cas LV2
				//on regarde le premier sous-élément
				if(module.getListeElements().get(0) instanceof Module){
					//on parcourt tous les sous-modules de ce module
					for(k=0;k<module.getListeElements().size();k++){
						sousModule=(Module) module.getListeElements().get(k);
						//on calcule la moyenne du sous-module en cours
						sousModule.calculMoyenneModule(nbrEtu);	
					}
					//on calcule la moyenne du module en cours
					module.calculMoyenneModuleLV2(nbrEtu);
				}
				//le module contient des épreuves
				else{
					//on calcule la moyenne du module en cours
					module.calculMoyenneModule(nbrEtu);
				}		
			}
			//on calcule moyenne de chaque UE
			ue.calculMoyenneUe(nbrEtu);		
		}

		//on calcule moyenne du semestre
		this.calculMoyenneSemestre();
		
		//Les manipulations à suivre sont faites pour faciliter la génération de la feuille de recap
		//la création des fiches étudiant se font par ordre décroissant de la moyenne du semestre
		
		//On trie l'objet NoteElement de la promo par ordre décroissant des notes
		Collections.sort(this.getListeNotesElement(), (s1, s2) -> Double.compare(s2.getNoteEtu(), s1.getNoteEtu()));
		
		//On complètele le tableau d'indice pour retrouver plus facilement un étudiant
		int codeEtu,codeEtuTri;
		int position;
		for(i=0;i<nbrEtu;i++){
			position=0;
			codeEtuTri=this.getListeNotesElement().get(i).getCodeEtu();
			codeEtu=this.getListeUe().get(0).getListeNotesElement().get(position).getCodeEtu();
			while(codeEtu!=codeEtuTri){
				position+=1;
				codeEtu=this.getListeUe().get(0).getListeNotesElement().get(position).getCodeEtu();
			}
			this.getTableauIndiceCodeEtu().add(position);
		}
		
		//On re-trie l'objet NoteElement de la promo par ordre croissant du codeEtu
		Collections.sort(this.getListeNotesElement(), (s1, s2) -> Integer.compare(s1.getCodeEtu(), s2.getCodeEtu()));
			
	}
	
	public void calculMoyenneSemestre(){
		
		int i,j,k;
		int nbrEtu=this.getNbrEtu();
		double sommeCoeffEtu[] = new double[nbrEtu],sommeCoeffUe=0.0;
		double noteEtu[] = new double[nbrEtu];
		int codeEtu[] = new int[nbrEtu];
		double bonusUel=0.0;
		Ue ue;
		List<Ue> listeUe=this.getListeUe();
		double coeffUe[] = new double[nbrEtu];
		int nbrNotesCalculees=0;
		boolean etatErreur=true;
		String messageErreur;
		int etatUel=0;

		List<NoteElement> notes;//= new ArrayList<NoteElement>();
		
		sommeCoeffUe=0.0;
		//on vérifie s'il y a des notes toutes les UE sont considérées sauf l'UEL
		for(i=0;i<listeUe.size()-1;i++){
			//on récupère l'UE
			ue=listeUe.get(i);
			//on récupère le coefficient de l'UE
			coeffUe[i]=ue.getCoeffElement();	
			//s'il y a des notes dans l'UE		
			if(ue.getNbrNotes()!=0){
				sommeCoeffUe+=coeffUe[i];
			}
		}
		
		//s'il y a des notes dans au moins une UE non UEL
		if(sommeCoeffUe!=0){
			//Test si les notes de l'UE n'ont pas été calculées par APOGEE
			if(this.getNbrNotes()==0){
				etatErreur=false;
				messageErreur="Erreur moyenne semestre "+this.getCodeApogeeElement()+" pas calculée par APOGEE alors qu'il y a des notes";
				Divers.addMessage(messageErreur);
			}
			else{
				messageErreur="Vérification calcul "+this.getCodeApogeeElement();
				Divers.addMessage(messageErreur);			
			}
			//on parcourt toutes les UE
			for(i=0;i<listeUe.size();i++){
				ue=listeUe.get(i);
				//pour toutes les UE hors UEL
				if(i<listeUe.size()-1){
					//s'il y a des notes danc cette UE
					if(ue.getNbrNotes()>0){	
						//On récupère les notes calculées de l'UE
						notes=ue.getListeNotesElement();
						//on parcourt tous les étudiants
						for(j=0;j<nbrEtu;j++){
							//s'il ne s'agit pas de la dernière UE (i-e UEL) en effet l'UEL ne rentre pas dans le calcul de la moyenne du semestre comme une UE classique
							if(i<(listeUe.size()-1)){
								//si l'étudiant n'est pas ABJ
								if(notes.get(j).getNoteEtu()!=-1){
									noteEtu[j]+=coeffUe[i]*notes.get(j).getNoteEtu();
									sommeCoeffEtu[j]+=coeffUe[i];
								}
							}
						}
					}
				}
				//si dernière UE (i-e UEL)
				else{
					//On récupère les notes de l'UEL
					notes=ue.getListeNotesElement();
					//on parcourt tous les étudiants
					for(j=0;j<nbrEtu;j++){
						//on finalise le calcul de la moyenne du semestre
						//s'il y a des notes dans au moins un des éléments
						if(sommeCoeffEtu[j]!=0){
							//on calcule la moyenne du semestre sans bonus
							noteEtu[j]=noteEtu[j]/sommeCoeffEtu[j];
							nbrNotesCalculees+=1;
							//si la note de l'UEL est supérieure à 10
							if(notes.get(j).getNoteEtu()>=10){
								//On ajoute le bonus à la moyenne du semestre
								bonusUel=(notes.get(j).getNoteEtu()-10)*coeffUe[i];
								noteEtu[j]+=bonusUel;
							}							
						}	
						//si l'étudiant n'a pas de note, il est ABJ sur la moyenne générale
						else{
							//A MODIFIER A MODIFIER A MODIFIER
							//il vaut mieux faire un test plus précis
							//en effet avec cette méthode on ne peut pas faire la différence entre un étudiant qui a un 0 et qui est ABJ
							//rare mais il vaut mieux le prévoir
							noteEtu[j]=-1;
						}
							
						//on compare la note APOGEE avec la note calculée
						//si note différente on ajoute un message d'erreur et garde la note APogée
							
						//si Note APOGEE calculée
						if(etatErreur){
							if(!(Divers.isEqual(this.getListeNotesElement().get(j).getNoteEtu(),noteEtu[j]))){
								messageErreur="Erreur moyenne semestre-Apogéee élément:"+this.getCodeApogeeElement()+"-Etudiant:"+codeEtu[j]+"-note Apogée"+this.getListeNotesElement().get(j).getNoteEtu()+"-note Calculée:"+noteEtu[j];
								Divers.addMessage(messageErreur);
								//on garde la note calculée par APOGEE
								//noteEtu[j]=this.getListeNotesElement().get(j).getNoteEtu();
							}
						}
						else{
							//on met à jour la moyenne générale du semestre avec 3 chiffres après la virgule
							double valeur=noteEtu[j];
							double precision=(double)Math.pow(10,3);
							valeur=Math.round(valeur*precision)/precision;
							this.getListeNotesElement().get(j).setNoteEtu(valeur);									
						}
						
						//si bonus on met à jour la moyenne des UE non Uel de l'étudiant 
						if(bonusUel!=0){
							etatUel+=1;
							//on parcourt toutes les UE non UEL
							for(k=0;k<listeUe.size()-1;k++){
								//on ajoute le bonus à la moyenne UE connue si Ue possède des notes et si étudiant pas ABJ
								double noteUe=listeUe.get(k).getListeNotesElement().get(j).getNoteEtu();
								double noteUeBonus=noteUe;
								if((listeUe.get(k).getNbrNotes()>0)&&(noteUe!=-1)){
									noteUeBonus=+bonusUel;
									listeUe.get(k).getListeNotesElement().get(j).setNoteEtu(noteUeBonus);
									if(!(Divers.isEqual(noteUe,noteUeBonus))){
										messageErreur="Erreur moyenne UE-Apogéee élément:"+listeUe.get(k).getCodeApogeeElement()+"-Etudiant:"+codeEtu[j]+"-note Apogée"+noteUe+"-note Calculée:"+noteUeBonus;
										Divers.addMessage(messageErreur);
									}
									else
									{
										//plus un message correctif
										messageErreur="moyenne UE corrigée avec bonus UEL-Apogéee élément:"+listeUe.get(k).getCodeApogeeElement()+"-Etudiant:"+codeEtu[j]+"-note Apogée"+noteUe+"-note Calculée:"+noteUeBonus;
										Divers.addMessage(messageErreur);									
									}
								}
							}
						}
					}//fin 	boucle étudiant
					
					//S'il y a au moins un étudiant avec un bonus UEL on doit recalculer min,max,moy et classement de toutes les Ue non Uel
					if(etatUel>0){
						//on parcourt toutes les UE non UEL
						for(k=0;k<listeUe.size()-1;k++){
							//S'il y a des notes on recalcule  min, max, moy et classement
							if(listeUe.get(k).getNbrNotes()>0){
								//ON REDETERMINE MIN, MAX et MOY
								listeUe.get(k).setInfosElement(nbrEtu);
								//ON REDETERMINE CLASSEMENT MOYENNE GENERALE
								listeUe.get(k).setClassementElement(nbrEtu);
							}
						}
					}
				}
			}//fin boucle UE
			
			//ON MET A JOUR LE NOMBRE DE NOTES CALCULEES
			this.setNbrNotes(nbrNotesCalculees);
			//DETERMINER MIN, MAX et MOY
			this.setInfosElement(nbrEtu);
			//DETERMINER CLASSEMENT MOYENNE GENERALE
			this.setClassementElement(nbrEtu);			
			
		}
	}
	
	public void initialisationListeEtudiants(SpreadsheetDocuments spreadsheetDocumentsNotes,XSpreadsheet feuille){
		
		List<NoteElement> listeNotesSemestre = this.getListeNotesElement();
		List<Etudiant> listeEtudiants = this.getListeEtudiant();
		List<Integer> tableauIndiceCodeEtu=this.getTableauIndiceCodeEtu();
		int indiceCodeEtu;
		
		int i,ligne,codeEtu;
		int nbrEtu=this.getNbrEtu();

		for(i=0;i<nbrEtu;i++){
			Etudiant etudiant = new Etudiant();
			//on récupère le codeEtu de l'étudiant en cours par ordre décroissant de moyenne semestre
			indiceCodeEtu=tableauIndiceCodeEtu.get(i);
			codeEtu=listeNotesSemestre.get(indiceCodeEtu).getCodeEtu();
			etudiant.setCodeEtu(codeEtu);			
			ligne=spreadsheetDocumentsNotes.getLigneEtu(feuille, codeEtu, nbrEtu);
			etudiant.setNomEtu(spreadsheetDocumentsNotes.getNomEtu(feuille,ligne));
			etudiant.setPrenomEtu(spreadsheetDocumentsNotes.getPrenomEtu(feuille,ligne));
			listeEtudiants.add(etudiant);
		}
	}
	
	public void generationFicheEtudiant(){

		List<Integer> tableauIndiceCodeEtu;
		List<Etudiant> listeEtudiants=this.getListeEtudiant();
		List<Ue> listeUe;
		Ue ue;
		List<Module> listeModulesUe;
		Module module,sousModule;
		Epreuve epreuve;

		int i,j,k,l;

		//on récupère le nombre d'étudiants
		int nbrEtu=this.getNbrEtu();
		//on récupère le table d'indice des codeEtu
		tableauIndiceCodeEtu=this.getTableauIndiceCodeEtu();
		
		//on ajoute la moyenne du semestre à toutes les fiches étudiant
		this.ajoutNoteRn(listeEtudiants, tableauIndiceCodeEtu,nbrEtu,false);
		
		//on récupère la liste de tous les UE
		listeUe=this.getListeUe();
		
		//on parcourt tous les UE
		for(i=0;i<listeUe.size();i++){
			
			ue=listeUe.get(i);
			
			//on ajoute la moyenne de l'UE à toutes les fiches étudiant
			ue.ajoutNoteRn(listeEtudiants, tableauIndiceCodeEtu,nbrEtu,false);
			
			//on récupère tous les modules de chaque UE
			listeModulesUe=ue.getListeModules();
			
			for(j=0;j<listeModulesUe.size();j++){
				
				module=listeModulesUe.get(j);
				
				//on ajoute la note moyenne module à toutes les fiches étudiant
				module.ajoutNoteRn(listeEtudiants, tableauIndiceCodeEtu,nbrEtu,false);			
			
				//si les éléments du module en cours sont des sous-modules il faut descendre encore d'un niveau
				//cas LV2
				//on regarde le premier sous-élément
				if(module.getListeElements().get(0) instanceof Module){
				
					//on parcourt tous les sous-modules de ce module
					for(k=0;k<module.getListeElements().size();k++){
					
						sousModule=(Module) module.getListeElements().get(k);
					
						//on ajoute la moyenne de LV2 à toutes les fiches étudiant
						//A REVOIR - JE NE PEUX PAS FAIRE LA DIFFERENCE ENTRE PAS CONCERNE PAR UNE LV2 et ABJ A CETTE LV2
						sousModule.ajoutNoteRn(listeEtudiants, tableauIndiceCodeEtu,nbrEtu,true);
					
						//on parcourt toutes les épreuves de ce sous-module
						for(l=0;l<sousModule.getListeElements().size();l++){
	
							//on récupère l'épreuve
							epreuve=(Epreuve) sousModule.getListeElements().get(l);
					
							//on ajoute la note de l'epreuve à toutes les fiches étudiant
							//A REVOIR - JE NE PEUX PAS FAIRE LA DIFFERENCE ENTRE PAS CONCERNE PAR UNE LV2 et ABJ A CETTE LV2
							epreuve.ajoutNoteRn(listeEtudiants, tableauIndiceCodeEtu,nbrEtu,true);
						}
					}
				}
				//le module contient des épreuves
				else{
				
					//on parcourt toutes les épreuves de ce sous-module
					for(l=0;l<module.getListeElements().size();l++){

						//on récupère l'épreuve
						epreuve=(Epreuve) module.getListeElements().get(l);
				
						//on ajoute la note de l'epreuve à toutes les fiches étudiant
						epreuve.ajoutNoteRn(listeEtudiants, tableauIndiceCodeEtu,nbrEtu,false);
					}
				}	
			}
		}
	}
	
	public void generationRnEtudiant(SpreadsheetDocuments spreadsheetDocumentsRn,String cheminFichierRn,String cheminFichierRnEtuOdsBase,String cheminFichierRnEtuPdfBase){
		
		XComponent xDocRn=null;
		XSpreadsheet feuilleRn;
		String message,cheminFichierRnEtuOds,cheminFichierRnEtuPdf;
		int i,j,ligneNoteRn,codeEtu;
		Etudiant etudiant;
		String nomEtu="",prenomEtu="";
		List<NoteRn> listeNotesRn = new ArrayList<NoteRn>();
		NoteRn noteRn;
		double noteEtu;
						
		//MISE A JOUR RN ETUDIANT
		for(i=0;i<this.nbrEtu;i++){
			try {
				//ON OUVRE LE MODELE DE RELEVE DE NOTES
				xDocRn = spreadsheetDocumentsRn.openSpreadsheetDocument(cheminFichierRn);
				message="Fichier modèle RN ouvert - "+cheminFichierRn;
				Divers.addMessage(message);
				feuilleRn=spreadsheetDocumentsRn.getFeuille(xDocRn,0);
			
				//ON RECUPERE INFOS ETUDIANT
				etudiant=this.getListeEtudiant().get(i);
				nomEtu=etudiant.getNomEtu();
				prenomEtu=etudiant.getPrenomEtu();
				codeEtu=etudiant.getCodeEtu();
				listeNotesRn=etudiant.getListeNotesRn();
		
				//ON MET A JOUR L'ENTETE DU RN
				spreadsheetDocumentsRn.writeCodeEtuRN(feuilleRn,codeEtu);
				spreadsheetDocumentsRn.writeNomEtuRN(feuilleRn,nomEtu);		
				spreadsheetDocumentsRn.writePrenomEtuRN(feuilleRn,prenomEtu);
				
				//ON PARSE TOUS LES ELEMENTS
				for(j=0;j<listeNotesRn.size();j++){
					//ON RECUPERE INFOS NOTE
					noteRn=listeNotesRn.get(j);
					noteEtu=noteRn.getNoteEtu();
					ligneNoteRn=noteRn.getLigneRN();
					//SI L'ELEMENT EST PRESENT DANS LE RN
					if(ligneNoteRn!=-1){
						//ON MET A JOUR LE RN
						//SI LV2 ET PAS ABJ TOUT
						//SI LV2 ET ABJ ON NE FAIT RIEN
						//SI PAS LV2 ET PAS ABJ TOUT
						//SI PAS LV2 ET ABJ MIN,MAX,MOY
						if(noteRn.getLv2()){
							if(noteEtu!=-1){
								spreadsheetDocumentsRn.writeNoteEtuRN(feuilleRn,ligneNoteRn,noteEtu);
								spreadsheetDocumentsRn.writeMinRN(feuilleRn,ligneNoteRn,noteRn.getMin());
								spreadsheetDocumentsRn.writeMaxRN(feuilleRn,ligneNoteRn,noteRn.getMax());
								spreadsheetDocumentsRn.writeMoyRN(feuilleRn,ligneNoteRn,noteRn.getMoy());
								spreadsheetDocumentsRn.writeClassementRN(feuilleRn,ligneNoteRn,noteRn.getClassementEtu());
							}
						}
						else{
							if(noteEtu!=-1){
								spreadsheetDocumentsRn.writeNoteEtuRN(feuilleRn,ligneNoteRn,noteEtu);
								spreadsheetDocumentsRn.writeMinRN(feuilleRn,ligneNoteRn,noteRn.getMin());
								spreadsheetDocumentsRn.writeMaxRN(feuilleRn,ligneNoteRn,noteRn.getMax());
								spreadsheetDocumentsRn.writeMoyRN(feuilleRn,ligneNoteRn,noteRn.getMoy());
								spreadsheetDocumentsRn.writeClassementRN(feuilleRn,ligneNoteRn,noteRn.getClassementEtu());
							}
							else{
								spreadsheetDocumentsRn.writeMinRN(feuilleRn,ligneNoteRn,noteRn.getMin());
								spreadsheetDocumentsRn.writeMaxRN(feuilleRn,ligneNoteRn,noteRn.getMax());
								spreadsheetDocumentsRn.writeMoyRN(feuilleRn,ligneNoteRn,noteRn.getMoy());							}							
						}				
					}
				}
				
				//ON ENREGISTRE EN ODS LE RELEVE DE NOTES DE L'ETUDIANT EN COURS
				cheminFichierRnEtuOds=cheminFichierRnEtuOdsBase+nomEtu+"_"+prenomEtu+".ods";
				spreadsheetDocumentsRn.saveSpreadsheetDocumentODS(xDocRn,cheminFichierRnEtuOds);
				//ON ENREGISTRE EN PDF LE RELEVE DE NOTES DE L'ETUDIANT EN COURS
				cheminFichierRnEtuPdf=cheminFichierRnEtuPdfBase+codeEtu+".pdf";		
				spreadsheetDocumentsRn.exportSpreadsheetDocumentPDF(xDocRn,cheminFichierRnEtuPdf);
		
				//ON FERME LE RN DE L'ETUDIANT
				spreadsheetDocumentsRn.closeSpreadsheetDocument(xDocRn);
				message="RN ODS et RN PDF de "+nomEtu+" "+prenomEtu + " générés";
				Divers.addMessage(message);
		
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}
		
	}
	
	public void generationRecapPromo(SpreadsheetDocuments spreadsheetDocumentsRecap, String cheminFichierRecapPromo){
		
		
		XComponent xDocRecap=null;
		XSpreadsheet feuilleRecap;
		String message;
		int i,j,colonneRecap;
		Etudiant etudiant;
		List<NoteRn> listeNotesRn = new ArrayList<NoteRn>();
		NoteRn noteRn;
		double noteEtu;
		
		//ON OUVRE FICHIER RECAP PROMO		
		try {
			xDocRecap = spreadsheetDocumentsRecap.openSpreadsheetDocument(cheminFichierRecapPromo);
			message="Fichier recap promo ouvert - "+cheminFichierRecapPromo;
			Divers.addMessage(message);
			feuilleRecap=spreadsheetDocumentsRecap.getFeuille(xDocRecap,0);
			
			for(i=0;i<this.nbrEtu;i++){
				
				//ON RECUPERE INFOS ETUDIANT
				etudiant=this.getListeEtudiant().get(i);
				listeNotesRn=etudiant.getListeNotesRn();
				
				//ON ECRIT INFOS ETU DANS RECAP
				spreadsheetDocumentsRecap.setInfosEtuRecap(feuilleRecap, i, etudiant.getCodeEtu(), etudiant.getNomEtu(), etudiant.getPrenomEtu());
				
				//ON PARSE TOUS LES ELEMENTS
				for(j=0;j<listeNotesRn.size();j++){
					
					//ON RECUPERE INFOS NOTE
					noteRn=listeNotesRn.get(j);
					noteEtu=noteRn.getNoteEtu();
					colonneRecap=noteRn.getColonneRecap();
					
					//SI L'ELEMENT CONCERNE LE RECAP (LES EPREUVES NE SONT PAS AFFICHEES) 
					if(colonneRecap!=-1){
						//SI ETU PAS ABJ A L'ELEMENT						
						if(noteEtu!=-1){
							spreadsheetDocumentsRecap.writeNoteModuleRecap(feuilleRecap,colonneRecap,i+1,noteEtu,noteRn.getSeuilNote());
						}
					}
				}
			}
			
			//ON ENREGISTRE ET ON FERME LE FICHIERS RECAP PROMO
			spreadsheetDocumentsRecap.saveSpreadsheetDocumentODS(xDocRecap,cheminFichierRecapPromo);
			spreadsheetDocumentsRecap.closeSpreadsheetDocument(xDocRecap);
			message="Fichier recap promo enregistré et fermé";
			Divers.addMessage(message);			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}