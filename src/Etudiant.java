import java.util.ArrayList;
import java.util.List;

class Etudiant {
	
	private String nomEtu;
	private String prenomEtu;
	private int codeEtu;
	
	List<NoteRn> listeNotesRn = new ArrayList<NoteRn>();
	
    public String getNomEtu() {
        return nomEtu;
    }

    public void setNomEtu(String nomEtu) {
        this.nomEtu = nomEtu;
    }

    public String getPrenomEtu() {
        return prenomEtu;
    }

    public void setPrenomEtu(String prenomEtu) {
        this.prenomEtu = prenomEtu;
    }
    
    public int getCodeEtu() {
        return codeEtu;
    }

    public void setCodeEtu(int codeEtu) {
        this.codeEtu = codeEtu;
    }
	
    public List<NoteRn> getListeNotesRn() {
        return listeNotesRn;
    }
 
	public void setListeNotesRn(List<NoteRn> listeNotesRN){
        this.listeNotesRn = listeNotesRN;
	}
	
}