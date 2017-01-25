class NoteRn{
	
	private String codeApogee;
	private int ligneRN;
	private int colonneRecap;
	private double noteEtu;
	private double min;
	private double moy;
	private double max;
	private String classementEtu;
	private double seuilNote;
	private boolean lv2;
	
	public NoteRn(){
		lv2=false;
		ligneRN=-1;
		colonneRecap=-1;
	}
	
    public String getCodeApogee() {
        return codeApogee;
    }
    
    public void setCodeApogee(String codeApogee) {
        this.codeApogee = codeApogee;
    }
	
    public int getLigneRN() {
        return ligneRN;
    }

    public void setLigneRN(int ligneRN) {
        this.ligneRN = ligneRN;
    }

    public int getColonneRecap() {
        return colonneRecap;
    }

    public void setColonneRecap(int colonneRecap) {
        this.colonneRecap = colonneRecap;
    }    
    
    public double getNoteEtu() {
        return noteEtu;
    }

    public void setNoteEtu(double noteEtu) {
        this.noteEtu = noteEtu;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }
    
    public double getMoy() {
        return moy;
    }

    public void setMoy(double moy) {
        this.moy = moy;
    }
    
    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }
    
    public String getClassementEtu() {
        return classementEtu;
    }

    public void setClassementEtu(String classementEtu) {
        this.classementEtu = classementEtu;
    }
    
    public double getSeuilNote() {
        return seuilNote;
    }

    public void setSeuilNote(double seuilNote) {
        this.seuilNote = seuilNote;
    }
	
    public boolean getLv2() {
        return lv2;
    }

    public void setLv2(boolean lv2) {
        this.lv2 = lv2;
    }
    
}