import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Epreuve {

	private String codeEpreuve;
	private String intituleEpreuve;
	private String enseignantEpreuve;
	private int colonneEpreuveEPR;
	private int ligneEpreuveRN;
	private Double min;
	private Double max;
	private Double moy;
	private int nbrNotesSaisies;

	private List<NoteEtudiant> listeNotes = new ArrayList<NoteEtudiant>();
	
    public String getCodeEpreuve() {
        return codeEpreuve;
    }

    public void setCodeEpreuve(String codeEpreuve) {
        this.codeEpreuve = codeEpreuve;
    }

    public String getIntituleEpreuve() {
        return intituleEpreuve;
    }

    public void setIntituleEpreuve(String intituleEpreuve) {
        this.intituleEpreuve = intituleEpreuve;
    }    
 
    public String getEnseignantEpreuve() {
        return enseignantEpreuve;
    }

    public void setEnseignantEpreuve(String enseignantEpreuve) {
        this.enseignantEpreuve = enseignantEpreuve;
    }      
    
    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }   
    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }  
    
    public Double getMoy() {
        return moy;
    }

    public void setMoy(Double moy) {
        this.moy = moy;
    }   
 
    public int getNbrNotes() {
        return nbrNotesSaisies;
    }

    public void setNbrNotes(int nbrNotes) {
        this.nbrNotesSaisies = nbrNotes;
    }      
 
    public int getLigneEpreuveRN() {
        return ligneEpreuveRN;
    }

    public void setLigneEpreuveRN(int ligneEpreuveRN) {
        this.ligneEpreuveRN = ligneEpreuveRN;
    }  
    
    public List<NoteEtudiant> getArray() {
        return listeNotes;
    }   
	
	public void setArray(List<NoteEtudiant> noteEpreuve1){
        this.listeNotes = noteEpreuve1;	
	}

    public int getColonneEpreuveEPR() {
        return colonneEpreuveEPR;
    }

    public void setColonneEpreuveEPR(int colonneEpreuve) {
        this.colonneEpreuveEPR = colonneEpreuve;
    } 	
	
	public void setInfosEpreuve(int nbrEtu){
		int i;
		NoteEtudiant itemTrouve;
		int nbrNotesEtu=0;
		Double noteEtu=0.0;
		Double min=20.0,max=0.0,somme=0.0;
		
		for(i = 0; i < nbrEtu; i++){
			itemTrouve = (NoteEtudiant)(this.listeNotes.get(i));
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
		this.moy=(Double) (Math.round(somme/nbrNotesEtu*10.)/10.);
		this.min=min;
		this.max=max;
		//*************** A SUPRRIMER *******************//
		//this.nbrNotes=nbrNotesEtu;
		//this.ligneEpreuveRN=this.recuperationLigneEpreuveRN(this.codeEpreuve);
	}

	public void setClassementEpreuve (int nbrEtu){	
		int i;
		NoteEtudiant itemTrouve, itemTrouvePrecedent;
		Double noteEtu,noteEtuPrecedent;
		int nbrNotes=this.nbrNotesSaisies;
		int classement = 1;
		int classementMem = 0;
		String classementString;

		
		//On tri par ordre décroissant
		Collections.sort(this.listeNotes, (s1, s2) -> Double.compare(s2.getNoteEtu(), s1.getNoteEtu()));
		
		//on initialise le major de l'épreuve
		itemTrouve = (NoteEtudiant)(this.listeNotes.get(0));
		classementString = classement + "/" +nbrNotes;
		itemTrouve.setClassementEtu(classementString);
		this.listeNotes.set(0,itemTrouve);
		
		for(i = 1; i < nbrEtu; i++){
			itemTrouve = (NoteEtudiant)(this.listeNotes.get(i));
			//si étudiant pas ABJ
			if(itemTrouve.getNoteEtu()!=-1){
				itemTrouvePrecedent = (NoteEtudiant)(this.listeNotes.get(i-1));
				noteEtu=itemTrouve.getNoteEtu();
				noteEtuPrecedent=itemTrouvePrecedent.getNoteEtu();
				
				if(noteEtu!=noteEtuPrecedent){
					classement=classement+1+classementMem;
					classementMem=0;
				}
				else{
					classementMem=classementMem+1;
				}
				classementString = classement + "/" +nbrNotes;
				itemTrouve.setClassementEtu(classementString);
				this.listeNotes.set(i,itemTrouve);
			}
			//on arrête dès qu'on trouve un étudiant ABJ
			else{
				break;
			}
		}	
	}

	public Double recuperationNoteEtu(int codeEtuAtrouver){
		int i;
		Double noteEtuAtrouver = 0.0;
		NoteEtudiant itemTrouve;

		for(i = 0; i < this.listeNotes.size(); i++){
			itemTrouve = (NoteEtudiant)(this.listeNotes.get(i));
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
		NoteEtudiant itemTrouve;

		for(i = 0; i < this.listeNotes.size(); i++){
			itemTrouve = (NoteEtudiant)(this.listeNotes.get(i));
			if(itemTrouve.getCodeEtu()==codeEtuAtrouver){
				classementEtuAtrouver=itemTrouve.getClassementEtu();
				break;
			}
		}
		return classementEtuAtrouver;
	}
}