import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sun.star.sheet.XSpreadsheet;

//VOIR SI PAS D'AUTRES METHODES OU PROPRIETES A MUTUALISER - calcul moyenne, min, max,...
class Element {

	//Un élément = soit une UE, soit un module, soit une épreuve
	
	private String nomElement;
	private String codeApogeeElement;
	private double coeffElement;
	
	private double min;
	private double max;
	private double moy;
	private int nbrNotes;
	private int precisionDecimale;
	private double seuilNote;
	
	private int colonneRecap;
	private int colonneExportApogee;
	private int ligneRn;

	//La liste des notes des étudiants de cet élément
	private List<NoteElement> listeNotesElement = new ArrayList<NoteElement>();
	
	public Element(){
		colonneRecap=-1;
		ligneRn=-1;
	}
	
    public String getNomElement() {
        return nomElement;
    }

    public void setNomElement(String nomModule) {
        this.nomElement = nomModule;
    }
    
    public String getCodeApogeeElement() {
        return codeApogeeElement;
    }

    public void setCodeApogeeElement(String codeApogeeModule) {
        this.codeApogeeElement = codeApogeeModule;
    }

    public double getCoeffElement() {
        return coeffElement;
    }

    public void setCoeffElement(double coeffModule) {
        this.coeffElement = coeffModule;
    }
    
    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }   
    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }  
    
    public double getMoy() {
        return moy;
    }

    public void setMoy(double moy) {
        this.moy = moy;
    }   
 
    public int getNbrNotes() {
        return nbrNotes;
    }

    public void setNbrNotes(int nbrNotes) {
        this.nbrNotes = nbrNotes;
    }  
	
    public void setPrecisionDecimale(int precisionDecimale) {
        this.precisionDecimale = precisionDecimale;
    }
    
    public int getPrecisionDecimale() {
        return precisionDecimale;
    }

    public void setSeuilNote(double seuilNote) {
        this.seuilNote = seuilNote;
    } 
    
    public double getSeuilNote() {
        return seuilNote;
    } 
 
    public List<NoteElement> getListeNotesElement() {
        return listeNotesElement;
    }   
	
	public void setListeNotesApogeeElement(List<NoteElement> listeNotesElement){
        this.listeNotesElement = listeNotesElement;	
	}
    
    public int getColonneRecap() {
        return colonneRecap;
    }

    public void setColonneRecap(int colonneRecap) {
        this.colonneRecap = colonneRecap;
    } 

    public int getColonneExportApogee() {
        return colonneExportApogee;
    }

    public void setColonneExportApogee(int colonneExportApogee) {
        this.colonneExportApogee = colonneExportApogee;
    }
    
    public int getLigneRn() {
        return ligneRn;
    }

    public void setLigneRn(int ligneRn) {
        this.ligneRn = ligneRn;
    }
    
	public void setInfosElement(int nbrEtu){
		int i;
		NoteElement itemTrouve;
		int nbrNotesEtu=0;
		double noteEtu=0.0;
		double min=20.0,max=0.0,somme=0.0;
		
		for(i = 0; i < nbrEtu; i++){
			itemTrouve = this.getListeNotesElement().get(i);
			noteEtu = itemTrouve.getNoteEtu();
			if(noteEtu!=-1){
				nbrNotesEtu+=1;
				somme+=noteEtu;
				if(noteEtu<min){
					min=noteEtu;
				}
				if(noteEtu>max){
					max=noteEtu;
				}				
			}
		}
		//ARRONDIR A UN CHIFFRE APRES LA VIRGULE
		this.setMoy((double) (Math.round(somme/nbrNotesEtu*10.0)/10.0));
		this.setMin(min);
		this.setMax(max);
	}

	public void setClassementElement(int nbrEtu){
		
		int i;
		double noteEtu,noteEtuMem;
		int classement = 1;
		int classementMem = 0;
		String classementString;

		//On trie l'objet NoteElement par ordre décroissant des notes
		Collections.sort(this.getListeNotesElement(), (s1, s2) -> Double.compare(s2.getNoteEtu(), s1.getNoteEtu()));
		
		//on initialise le major de l'épreuve
		classementString = classement + "/" +this.nbrNotes;
		this.getListeNotesElement().get(0).setClassementEtu(classementString);
		//on mémorise note et classement
		classementMem=classement;
		noteEtuMem=this.getListeNotesElement().get(0).getNoteEtu();
		
		for(i = 1; i < nbrEtu; i++){
			noteEtu = this.getListeNotesElement().get(i).getNoteEtu();
			//si étudiant pas ABJ
			if(noteEtu!=-1){
				if(noteEtu!=noteEtuMem){
					classement=i+1;
					noteEtuMem=noteEtu;
					classementMem=classement;
				}
				else{
					classement=classementMem;
				}
				classementString = classement + "/" +this.nbrNotes;
				this.getListeNotesElement().get(i).setClassementEtu(classementString);
			}
			//on arrête dès qu'on trouve un étudiant ABJ
			else{
				break;
			}
		}
		
		//On trie l'objet NoteElement par ordre croissant des codeEtu		
		this.triElementCodeEtu();
		
		classementMem=0;
		
	}
	
	 public void triElementCodeEtu(){
	    
			//On trie l'objet NoteElement par ordre croissant des codeEtu
			Collections.sort(this.getListeNotesElement(), (s1, s2) -> Integer.compare(s1.getCodeEtu(), s2.getCodeEtu()));   
	}	
	
    
    public int getNotesExportElement(SpreadsheetDocuments spreadsheetDocument, XSpreadsheet xSheet, int colonneExportApogee, int nbrEtu){
    	
    	int nbrNotesEtu;
    	
    	//ON DETERMINE LE NBR DE NOTES SAISIES OU CALCULEES
    	nbrNotesEtu=spreadsheetDocument.getNbrNotes(xSheet, colonneExportApogee, nbrEtu);
    	
		//SI NOTE ALORS ON LES RECUPERE		
		if(nbrNotesEtu!=0){
			//MàJ NBR NOTES EPREUVE SAISIE DE L'ELEMENT
			this.setNbrNotes(nbrNotesEtu);
			//RECUPERATION DES NOTES ET CODE ETU DE CET ELEMENT
			this.setListeNotesApogeeElement(spreadsheetDocument.getNotes(xSheet,colonneExportApogee,nbrEtu));
			//ON CLASSE LES NOTES PAR ORDRE CROISSANT DU CODE ETU
			this.triElementCodeEtu();
		}
		else{
			//si élément pas de notes on initialise le codeEtu et la note de l'élément
			//if(!(this instanceof Epreuve2)){
				//initialiser tableau de notes (codeEtu et noteEtu = -1)
				this.setListeNotesApogeeElement(spreadsheetDocument.getCodesEtu(xSheet,colonneExportApogee,nbrEtu));
				this.triElementCodeEtu();
			//}
		}
		
		return nbrNotesEtu;
		
    }
    
    public void ajoutNoteRn(List<Etudiant> listeEtudiants,List<Integer> tableauIndiceCodeEtu, int nbrEtu, boolean lv2){
    	int i,ligneNoteRn,colonneNoteRecap,indiceCodeEtu;

		List<NoteElement> listeNotesElement;
		NoteElement noteElement;
		double noteEtu,seuilNote;
		String codeApogee;
		
		
		//on ajoute la note moyenne UE à toutes les fiches étudiant
		//on récupère les infos de l'élément s'il y a des notes et si destiné soit au RN soit au Recap
		if((this.getNbrNotes()>0)&&((this.getLigneRn()!=-1)||(this.getColonneRecap()!=-1))){
			listeNotesElement=this.getListeNotesElement();
			ligneNoteRn=this.getLigneRn();
			colonneNoteRecap=this.getColonneRecap();
			min=this.getMin();
			max=this.getMax();
			moy=this.getMoy();
			seuilNote=this.getSeuilNote();
			codeApogee=this.getCodeApogeeElement();

			for(i=0;i<nbrEtu;i++){
				indiceCodeEtu=tableauIndiceCodeEtu.get(i);
				noteElement=listeNotesElement.get(indiceCodeEtu);
				noteEtu=noteElement.getNoteEtu();
				
				//cas spécial pour la lv2 si ABJ on ne sort pas de noteRN
				if(!(lv2&&(noteEtu==-1))){
					NoteRn noteRn = new NoteRn();
					noteRn.setCodeApogee(codeApogee);
					noteRn.setLigneRN(ligneNoteRn);
					noteRn.setColonneRecap(colonneNoteRecap);
					noteRn.setMin(min);
					noteRn.setMax(max);
					noteRn.setMoy(moy);
					noteRn.setNoteEtu(noteEtu);
					noteRn.setClassementEtu(noteElement.getClassementEtu());
					noteRn.setSeuilNote(seuilNote);
					if(lv2){
						noteRn.setLv2(lv2);
					}
					listeEtudiants.get(i).getListeNotesRn().add(noteRn);
				}
			}
		} 
    }
    
    
}