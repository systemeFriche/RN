import java.util.ArrayList;
import java.util.List;

class Ue extends Element {
	
	//La liste des modules de l'UE
	private List<Module> listeModulesUe = new ArrayList<Module>();
	
	
    public List<Module> getListeModules() {
        return listeModulesUe;
    }

	public void setListeModules(List<Module> listeModulesUE1){
        this.listeModulesUe = listeModulesUE1;	
	}
	
public void calculMoyenneUe(int nbrEtu){
		
		int i,j;
		double sommeCoeffEtu[]=new double[nbrEtu],sommeCoeffModule=0.0;
		double noteEtu[]=new double[nbrEtu];
		int codeEtu[]=new int[nbrEtu];
		double coeffModule[] = new double[nbrEtu];
		Module module;
		List<Module> listeModule=this.getListeModules();
		List<NoteElement> notes;
		int nbrNotesCalculees=0;
		String messageErreur;
		boolean etatErreur=true;
		boolean etat=true;
		
		sommeCoeffModule=0.0;
		//on vérifie s'il y a des notes dans les modules
		for(i=0;i<listeModule.size();i++){
			//on récupère le module
			module=listeModule.get(i);
			//on récupère le coefficient du module
			coeffModule[i]=module.getCoeffElement();	
			//s'il y a des notes dans l'UE		
			if(module.getNbrNotes()!=0){
				sommeCoeffModule+=coeffModule[i];
			}
		}
		
		//s'il y a des notes dans au moins un module
		if(sommeCoeffModule!=0){
			//Test si les notes de l'UE n'ont pas été calculées par APOGEE
			if(this.getNbrNotes()==0){
				etatErreur=false;
				messageErreur="Erreur moyenne UE "+this.getCodeApogeeElement()+" pas calculée par APOGEE alors qu'il y a des notes";
				Divers.addMessage(messageErreur);
			}
			else{
				messageErreur="Vérification calcul "+this.getCodeApogeeElement();
				Divers.addMessage(messageErreur);			
			}
			
			//on parcourt tous les modules
			for(i=0;i<listeModule.size();i++){
				module=listeModule.get(i);
				//Si le module en cours possède des notes
				if(module.getNbrNotes()>0){
					//Notes du module
					notes=module.getListeNotesElement();
					//on parcourt tous les étudiants
					for(j=0;j<nbrEtu;j++){
						//on initialise le tableau des codeEtu - pour le message d'erreur
						if(etat){
							codeEtu[j]=notes.get(j).getCodeEtu();
							if(j==nbrEtu-1){
								etat=false;
							}
						}
						if(notes.get(j).getNoteEtu()!=-1){
							noteEtu[j]+=coeffModule[i]*notes.get(j).getNoteEtu();
							sommeCoeffEtu[j]+=coeffModule[i];						
						}
					}//fin 	boucle étudiant	
				}				
			}//fin boucle modules
			
			//ON FINALISE MOYENNE DU MODULE POUR CHAQUE ETUDIANT ET ON VERIFIE AVEC NOTE APOGEE
			for(j=0;j<nbrEtu;j++){
				//s'il y a des notes dans au moins un des module
				if(sommeCoeffEtu[j]!=0){
					//on calcule la moyenne du module
					noteEtu[j]=noteEtu[j]/sommeCoeffEtu[j];		
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
						messageErreur="Erreur moyenne -Apogéee élément:"+this.getCodeApogeeElement()+"-Etudiant:"+codeEtu[j]+"-note Apogée"+this.getListeNotesElement().get(j).getNoteEtu()+"-note Calculée:"+noteEtu[j];
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
				else{
					//on met à jour la moyenne générale de l'UE avec 3 chiffres après la virgule
					double valeur=noteEtu[j];
					double precision=(double)Math.pow(10,3);
					valeur=Math.round(valeur*precision)/precision;
					this.getListeNotesElement().get(j).setNoteEtu(valeur);										
				}	
				
			}
			//ON MET A JOUR LE NOMBRE DE NOTES CALCULEES
			this.setNbrNotes(nbrNotesCalculees);
			//DETERMINER MIN, MAX et MOY
			this.setInfosElement(nbrEtu);
			//DETERMINER CLASSEMENT MOYENNE GENERALE
			this.setClassementElement(nbrEtu);
		}
	}  
}