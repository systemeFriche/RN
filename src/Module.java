import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Module {

	//protected : modifiable classe fille
	//private : pas modifiable classe fille
	
	private String nomModule;
	private String codeApogeeModule;
	private Double coeffModule;
	
	//La liste des épreuves du module
	private List<Epreuve> listeEpreuvesModule = new ArrayList<Epreuve>();
	//La liste des notes des étudiants de ce module
	private List<NoteModule> listeNotesModule = new ArrayList<NoteModule>();

	private int colonneModule;
	private int ligneModuleRN;
	private int colonneRecap;
	private Double min;
	private Double max;
	private Double moy;
	private int nbrNotes;
	private int precisionDecimale;
	private float seuilNote;



    public String getNomModule() {
        return nomModule;
    }

    public void setNomModule(String nomModule) {
        this.nomModule = nomModule;
    }
	
	
    public String getCodeApogeeModule() {
        return codeApogeeModule;
    }

    public void setCodeApogeeModule(String codeApogeeModule) {
        this.codeApogeeModule = codeApogeeModule;
    }

    public Double getCoeffModule() {
        return coeffModule;
    }

    public void setCoeffModule(Double coeffModule) {
        this.coeffModule = coeffModule;
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
        return nbrNotes;
    }

    public void setNbrNotes(int nbrNotes) {
        this.nbrNotes = nbrNotes;
    }      

    public List<Epreuve> getListesEpreuve() {
        return listeEpreuvesModule;
    }   
	
	public void setListesEpreuve(List<Epreuve> listeEpreuves1){
        this.listeEpreuvesModule = listeEpreuves1;	
	}
    
    public List<NoteModule> getListeNotesModule() {
        return listeNotesModule;
    }   
	
	public void setListeNotesModule(List<NoteModule> listeNotesModule1){
        this.listeNotesModule = listeNotesModule1;	
	}
	
    public void setColonneModule(int colonneModule) {
        this.colonneModule = colonneModule;
    } 

    public int getColonneModule() {
        return colonneModule;
    }

    public void setPrecisionDecimale(int precisionDecimale) {
        this.precisionDecimale = precisionDecimale;
    }
    
    public int getPrecisionDecimale() {
        return precisionDecimale;
    }

    public void setSeuilNote(float seuilNote) {
        this.seuilNote = seuilNote;
    } 
    
    public float getSeuilNote() {
        return seuilNote;
    }   
 
    public int getColonneRecap() {
        return colonneRecap;
    }

    public void setColonneRecap(int colonneRecap) {
        this.colonneRecap = colonneRecap;
    }    
    
	public void setLigneModuleRN(int ligneModuleRN) {
        this.ligneModuleRN = ligneModuleRN;
		
	}

    public int getLigneModuleRN() {
        return ligneModuleRN;
    }
	
	public void setInfosModule(){
		int i;
		NoteModule itemTrouve;
		int nbrNotesEtu=0;
		Double noteEtu=0.0;
		Double min=20.0,max=0.0,somme=0.0;
		
		for(i = 0; i < this.listeNotesModule.size(); i++){			
			itemTrouve = (NoteModule)(this.listeNotesModule.get(i));
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
		Double moyenne = somme/nbrNotesEtu;
		this.moy=(Double) (Math.round(somme/nbrNotesEtu*10.)/10.);
		this.min=min;
		this.max=max;
		this.nbrNotes=nbrNotesEtu;
		//this.ligneModuleRN=this.recuperationLigneModuleRN(this.codeModule);
	}

	public void setClassementModule (){	
		int i;
		NoteModule itemTrouve, itemTrouvePrecedent;
		Double noteEtu,noteEtuPrecedent;
		int nbrNotes=this.nbrNotes;
		int classement = 1;
		int classementMem = 0;
		String classementString;

		
		//On tri par ordre décroissant
		Collections.sort(this.listeNotesModule, (s1, s2) -> Double.compare(s2.getNoteEtu(), s1.getNoteEtu()));
		
		//on initialise le major de l'épreuve
		itemTrouve = (NoteModule)(this.listeNotesModule.get(0));
		classementString = classement + "/" +nbrNotes;
		itemTrouve.setClassementEtu(classementString);
		this.listeNotesModule.set(0,itemTrouve);
		
		for(i = 1; i < this.listeNotesModule.size(); i++){
			itemTrouve = (NoteModule)(this.listeNotesModule.get(i));
			//si étudiant pas ABJ
			if(itemTrouve.getNoteEtu()!=-1){
				itemTrouvePrecedent = (NoteModule)(this.listeNotesModule.get(i-1));
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
				this.listeNotesModule.set(i,itemTrouve);
			}
			//on arrête dès qu'on trouve un étudiant ABJ=-1 car classé en dernier
			else{
				break;
			}
		}	
	}

	public Double recuperationNoteEtu(int codeEtuAtrouver){
		int i;
		Double noteEtuAtrouver = 0.0;
		NoteModule itemTrouve;

		for(i = 0; i < this.listeNotesModule.size(); i++){
			itemTrouve = (NoteModule)(this.listeNotesModule.get(i));
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
		NoteModule itemTrouve;

		for(i = 0; i < this.listeNotesModule.size(); i++){
			itemTrouve = (NoteModule)(this.listeNotesModule.get(i));
			if(itemTrouve.getCodeEtu()==codeEtuAtrouver){
				classementEtuAtrouver=itemTrouve.getClassementEtu();
				break;
			}
		}
		return classementEtuAtrouver;
	}	
	
	public void setNoteModuleEtu(int codeEtu, Double noteRN){
		
		NoteModule noteModule= new NoteModule();
		
		noteModule.setCodeEtu(codeEtu);
		noteModule.setNoteEtu(noteRN);

		this.listeNotesModule.add(noteModule);
		
	}	
}