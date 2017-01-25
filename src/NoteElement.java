class NoteElement {

    private int codeEtu;
    private double noteEtu;
    private String classementEtu;
    
    public NoteElement(){
    	this.setNoteEtu(-1.0);
    }
    
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