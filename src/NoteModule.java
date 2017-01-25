//Cette classe peut être supprimée
//ANCIEN SYSTEME
class NoteModule {

    private int codeEtu;
    private double noteEtu;//note module calculée par notre système
    
    //pas besoin de stocker l'information noteEtuApogee
    //on calcule moyenne module avec outil interne
    //on récupère note module d'APOGEE
    //si pas pareil on affiche message d'erreur  
    private float noteEtuApogee;//note module calculée par APOGEE
    
    private String classementEtu;

    public int getCodeEtu() {
        return codeEtu;
    }

    public void setCodeEtu(int codeEtu) {
        this.codeEtu = codeEtu;
    }

    public double getNoteEtu() {
        return noteEtu;
    }

    public void setNoteEtu(double noteEtu) {
        this.noteEtu = noteEtu;
    }
    
    public String getClassementEtu() {
        return classementEtu;
    }

    public void setClassementEtu(String classementEtu) {
        this.classementEtu = classementEtu;
    }   

}