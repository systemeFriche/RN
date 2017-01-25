import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Epreuve2 extends Element{

	private String enseignantEpreuve;

    public String getEnseignantEpreuve() {
        return enseignantEpreuve;
    }

    public void setEnseignantEpreuve(String enseignantEpreuve) {
        this.enseignantEpreuve = enseignantEpreuve;
    }
    
    //A REVOIR ?

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
}