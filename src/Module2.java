import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Module2 extends Element {

	//protected : modifiable classe fille
	//private : pas modifiable classe fille
	
	//La liste des éléments du module (peut-être une liste de sous-modules ou une liste d'épreuves)
	private List<Element> listeElements = new ArrayList<Element>();
	
    public List<Element> getListeElements() {
        return listeElements;
    }
 
	public void setListeElements(List<Element> listeElement1){
        this.listeElements = listeElement1;	
	}

	public double recuperationNoteEtu(int codeEtuAtrouver){
		int i;
		double noteEtuAtrouver = 0.0;
		NoteElement itemTrouve;

		for(i = 0; i < this.getListeNotesElement().size(); i++){
			itemTrouve = this.getListeNotesElement().get(i);
			if(itemTrouve.getCodeEtu()==codeEtuAtrouver){
				noteEtuAtrouver=itemTrouve.getNoteEtu();
				break;
			}
		}
		return noteEtuAtrouver;
	}

	public String recuperationClassementEtu(int codeEtuAtrouver){
		int i;
		String classementEtuAtrouver = null;
		NoteElement itemTrouve;

		for(i = 0; i < this.getListeNotesElement().size(); i++){
			itemTrouve = this.getListeNotesElement().get(i);
			if(itemTrouve.getCodeEtu()==codeEtuAtrouver){
				classementEtuAtrouver=itemTrouve.getClassementEtu();
				break;
			}
		}
		return classementEtuAtrouver;
	}	
	
	public void setNoteModuleEtu(int codeEtu, double noteRN){
		
		NoteElement noteModule= new NoteElement();
		
		noteModule.setCodeEtu(codeEtu);
		noteModule.setNoteEtu(noteRN);

		this.getListeNotesElement().add(noteModule);
		
	}
	
	public void calculMoyenneModule(int nbrEtu){
		
		int i,j;
		double sommeCoeffEtu[]=new double[nbrEtu],sommeCoeffModule=0.0;
		double noteEtu[]=new double[nbrEtu];
		int codeEtu[] = new int[nbrEtu];
		List<Element> listeElements=this.getListeElements();
		double coeffModule[] = new double[listeElements.size()];
		Element element;//module ou épreuve
		List<NoteElement> notes;
		//= new ArrayList<NoteElement>();
		int nbrNotesCalculees=0;
		boolean etat=true;
		boolean etatErreur=true;
		String messageErreur;
		
		//on vérifie s'il y a des notes
		for(i=0;i<listeElements.size();i++){
			//on récupère l'élément
			element=listeElements.get(i);
			//on récupère le coefficient du module ou de l'épreuve
			coeffModule[i]=element.getCoeffElement();	
			//s'il y a des notes dans le module ou l'épreuve		
			if(element.getNbrNotes()!=0){
				sommeCoeffModule+=coeffModule[i];
			}
		}
		
		//s'il y a des notes dans au moins un module ou une épreuve
		if(sommeCoeffModule!=0){
			//Test si les notes du module n'ont pas été calculées par APOGEE
			if(this.getNbrNotes()==0){
				etatErreur=false;
				messageErreur="Erreur moyenne module "+this.getCodeApogeeElement()+" pas calculée par APOGEE alors qu'il y a des notes";
				Divers.addMessage(messageErreur);
			}
			else{
				messageErreur="Vérification calcul "+this.getCodeApogeeElement();
				Divers.addMessage(messageErreur);			
			}
			
			//on parcourt tous les modules ou épreuve
			for(i=0;i<listeElements.size();i++){
				//Element en cours : module ou épreuve
				element=listeElements.get(i);
				//si ce module ou épreuve possède des notes
				if(element.getNbrNotes()>0){
					//Notes du module ou épreuve
					notes=element.getListeNotesElement();
					//on parcourt tous les étudiants
					for(j=0;j<nbrEtu;j++){
							//on initialise le tableau des codeEtu - pour le message d'erreur
							if(etat){
								codeEtu[j]=notes.get(j).getCodeEtu();
								if(j==nbrEtu-1){
									etat=false;
								}
							}
							//si l'étudiant n'est pas ABJ
							if(notes.get(j).getNoteEtu()!=-1){
								noteEtu[j]+=coeffModule[i]*notes.get(j).getNoteEtu();
								sommeCoeffEtu[j]+=coeffModule[i];
							}
					}//fin 	boucle étudiant
				}
			}//fin boucle module/épreuve
			
			//ON FINALISE MOYENNE DU MODULE POUR CHAQUE ETUDIANT ET ON VERIFIE AVEC NOTE APOGEE
			for(j=0;j<nbrEtu;j++){
				//s'il y a des notes dans au moins un des éléments
				if(sommeCoeffEtu[j]!=0){
					//on calcule la moyenne du module, on arrondit à trois chiffres après la virgule
					double valeur=noteEtu[j]/sommeCoeffEtu[j];
					//
					noteEtu[j]=valeur;
					nbrNotesCalculees+=1;
				}
				//si l'étudiant n'a pas de note, il est ABJ sur la moyenne du module
				else{
					noteEtu[j]=-1.0;
				}
				
				//DERNIER ELEMENT MOYENNE ELEMENT CALCULEE
				//on compare la note APOGEE avec la note calculée
				//si note différente on ajoute un message d'erreur et garde la note APogée
				
				//si Note APOGEE calculée
				if(etatErreur){
					if(!(Divers.isEqual(this.getListeNotesElement().get(j).getNoteEtu(),noteEtu[j]))){
						messageErreur="Erreur moyenne module-Apogéee élément:"+this.getCodeApogeeElement()+"-Etudiant:"+codeEtu[j]+"-note Apogée:"+this.getListeNotesElement().get(j).getNoteEtu()+"-note Calculée:"+noteEtu[j];
						Divers.addMessage(messageErreur);
						//on garde la note calculée par APOGEE si note différent de -1 sinon on prend en compte la moyenne calculée
						if(this.getListeNotesElement().get(j).getNoteEtu()==-1){
							double valeur=noteEtu[j];
							double precision=(double)Math.pow(10,3);
							valeur=Math.round(valeur*precision)/precision;
							this.getListeNotesElement().get(j).setNoteEtu(valeur);	
						}
					}
				}
				//si pas de note Apogée
				else{
					//on met à jour la moyenne générale du module avec 3 chiffres après la virgule
					double valeur=noteEtu[j];
					double precision=(double)Math.pow(10,3);
					valeur=Math.round(valeur*precision)/precision;
					this.getListeNotesElement().get(j).setNoteEtu(valeur);							
				}
			}

			//ON MET A JOUR LE NOMBRE DE NOTES CALCULEES
			this.setNbrNotes(nbrNotesCalculees);
			//DETERMINER MIN, MAX ET MOY
			this.setInfosElement(nbrEtu);
			//DETERMINER CLASSEMENT MOYENNE GENERALE
			this.setClassementElement(nbrEtu);
		}
	}

	public void calculMoyenneModuleLV2(int nbrEtu){
	
		int i,j;
		double noteEtu[]=new double[nbrEtu];
		int codeEtu[] = new int[nbrEtu];
		List<Element> listeElements=this.getListeElements();
		Element element;//sous-module
		List<NoteElement> notes;
		int nbrNotesCalculees=0;
		String messageErreur;
		boolean etatErreur=true;
		boolean etat=true;
		boolean uneNote[]=new boolean[nbrEtu];
		
		//Test si les notes du module n'ont pas été calculées par APOGEE
		if(this.getNbrNotes()==0){
			etatErreur=false;
			messageErreur="Erreur moyenne module "+this.getCodeApogeeElement()+" pas calculée par APOGEE alors qu'il y a des notes";
			Divers.addMessage(messageErreur);
		}			else{
			messageErreur="Vérification calcul "+this.getCodeApogeeElement();
			Divers.addMessage(messageErreur);			
		}
		
		//on parcourt tous les sous-modules de LV2
		for(i=0;i<listeElements.size();i++){
			//on récupère le sous-module
			element=listeElements.get(i);
			//S'il y a des notes
			//les étudiants ne sont concernés par qu'un seul sous-module
			if(element.getNbrNotes()>0){	
				//on récupère les notes du module
				notes=element.getListeNotesElement();
				//on parcourt tous les éudiants
				for(j=0;j<nbrEtu;j++){
					//on initialise le tableau des codeEtu - pour le message d'erreur
					if(etat){
						codeEtu[j]=notes.get(j).getCodeEtu();
						if(j==nbrEtu-1){
							etat=false;
						}
					}
					if(notes.get(j).getNoteEtu()!=-1){
						noteEtu[j]=notes.get(j).getNoteEtu();
						uneNote[j]=true;
						nbrNotesCalculees+=1;
					}
				}//fin 	boucle étudiant
			}
		}//fin boucle module/épreuve
		
		//PAS BESOIN DE CALCUL DE MOYENNE CAR LES ETUDIANTS N'ONT QU'UN MODULE AVEC NOTES SUR TOUS LES MODULES
		
		//ON VERIFIE AVEC NOTE APOGEE POUR TOUT LES ETUDIANTS
		for(j=0;j<nbrEtu;j++){
			
			//Si un étudiant n'a aucune note à tous les sous-modules de LV2 il est considéré comme ABJ
			if(!uneNote[j]){
				noteEtu[j]=-1.0;
			}
			
			//on compare la note APOGEE avec la note calculée
			//si note différente on ajoute un message d'erreur et garde la note APogée
			
			//si Note APOGEE calculée
			if(etatErreur){
				if(!(Divers.isEqual(this.getListeNotesElement().get(j).getNoteEtu(),noteEtu[j]))){
					messageErreur="Erreur moyenne LV2-Apogéee élément:"+this.getCodeApogeeElement()+"-Etudiant:"+codeEtu[j]+"-note Apogée:"+this.getListeNotesElement().get(j).getNoteEtu()+"-note Calculée:"+noteEtu[j];
					Divers.addMessage(messageErreur);
				}
			}
			else{
				//on met à jour la moyenne générale de LV2 module avec 3 chiffres après la virgule
				double valeur=noteEtu[j];
				double precision=(double)Math.pow(10,3);
				valeur=Math.round(valeur*precision)/precision;
				this.getListeNotesElement().get(j).setNoteEtu(valeur);	
			}
		}
		
		//ON MET A JOUR LE NOMBRE DE NOTES CALCULEES
		this.setNbrNotes(nbrNotesCalculees);
		//DETERMINER MIN, MAX ET MOY
		this.setInfosElement(nbrEtu);
		//DETERMINER CLASSEMENT MOYENNE GENERALE
		this.setClassementElement(nbrEtu);
	}
}
