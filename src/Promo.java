import java.util.ArrayList;
import java.util.List;

import com.sun.star.sheet.XSpreadsheet;

import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import javax.json.stream.JsonParser;

class Promo {


	private String nomPromo;
	private String codeApogeeSemestre;
	private int nbrEtu;
	private String urlNotes;
	
	
	private int colonneELPNoteSport;
	
	//A SUPPRIMER la liste des modules est incluse dans l'objet UE
	//la liste des épreuves est incluse dans l'objet Module
	List<Module> listeModules = new ArrayList<Module>();
	List<Epreuve> listeEpreuves = new ArrayList<Epreuve>();

	public String getNomPromo() {
        return nomPromo;
    }
	
    public void setNomPromo(String nomPromo) {
        this.nomPromo = nomPromo;
    }	
	
	public String getCodeApogeeSemestre() {
        return codeApogeeSemestre;
    }
	
    public void setCodeApogeeSemestre(String codeApogeeSemestre) {
        this.codeApogeeSemestre = codeApogeeSemestre;
    }    
    
	public String getUrlNotes() {
        return urlNotes;
    }
	
    public void setUrlNotes(String urlNotes) {
        this.urlNotes = urlNotes;
    }
		
    public int getNbrEtu() {
        return nbrEtu;
    }

    public void setNbrEtu(int nbrEtu) {
        this.nbrEtu = nbrEtu;
    }
    
    public int getColonneELPNoteSport() {
        return colonneELPNoteSport;
    }

    public void setColonneNoteSport(int colonneELPNoteSport) {
        this.colonneELPNoteSport = colonneELPNoteSport;
    } 
	
    public void initialisationListeModules() {

		Double min=20.0,max=0.0,moy=0.0;
		
		Module module1 = new Module();
		
		//initialisation colonne note SPORT FEUILLE ELP FICHIER EXPORT NOTES
		this.setColonneNoteSport(3);
		
		//initialisation moyenne générale
    	module1.setCodeApogeeModule("711SEM11");
    	module1.setColonneModule(22);
    	module1.setLigneModuleRN(5);
    	module1.setColonneRecap(3);
    	module1.setMin(min);
    	module1.setMax(max);
    	module1.setMoy(moy);
    	module1.setPrecisionDecimale(3);
    	module1.setSeuilNote(10);
		this.listeModules.add(module1);
		
		//initialisation UE1
		module1 = new Module();
    	module1.setCodeApogeeModule("711UE111");
    	module1.setColonneModule(24);
    	module1.setLigneModuleRN(7);
    	module1.setColonneRecap(4);
    	module1.setPrecisionDecimale(3);
    	module1.setSeuilNote(10);
		this.listeModules.add(module1);
		
		//initialisation Anglais
		module1 = new Module();		
    	module1.setCodeApogeeModule("711MD101");
    	module1.setColonneModule(7);
    	module1.setLigneModuleRN(8);
    	module1.setColonneRecap(6);
    	module1.setPrecisionDecimale(1);
    	module1.setSeuilNote(8);
		this.listeModules.add(module1);
		
		//initialisation LV2
		module1 = new Module();
    	module1.setCodeApogeeModule("711MD117");
    	module1.setColonneModule(21);
    	module1.setLigneModuleRN(12);
    	module1.setColonneRecap(7);
    	module1.setPrecisionDecimale(1);
    	module1.setSeuilNote(8);
		this.listeModules.add(module1);
		
		//initialisation InfoCom
		module1 = new Module();
    	module1.setCodeApogeeModule("711MD102");
    	module1.setColonneModule(8);
    	module1.setLigneModuleRN(15);
    	module1.setColonneRecap(8);
    	module1.setPrecisionDecimale(1);
    	module1.setSeuilNote(8);
		this.listeModules.add(module1);
		
		//initialisation Expr artistique
		module1 = new Module();
    	module1.setCodeApogeeModule("711MD103");
    	module1.setColonneModule(9);
    	module1.setLigneModuleRN(19);
    	module1.setColonneRecap(9);
    	module1.setPrecisionDecimale(1);
    	module1.setSeuilNote(8);
		this.listeModules.add(module1);
		
		//initialisation écriture pour les médias
		module1 = new Module();
    	module1.setCodeApogeeModule("711MD104");
    	module1.setColonneModule(10);
    	module1.setLigneModuleRN(22);
    	module1.setColonneRecap(10);
    	module1.setPrecisionDecimale(1);
    	module1.setSeuilNote(8);
		this.listeModules.add(module1);
		
		//initialisation Communication
		module1 = new Module();
    	module1.setCodeApogeeModule("711MD105");
    	module1.setColonneModule(11);
    	module1.setLigneModuleRN(26);
    	module1.setColonneRecap(11);
    	module1.setPrecisionDecimale(1);
    	module1.setSeuilNote(8);
		this.listeModules.add(module1);
		
		//initialisation gestion de projet
		module1 = new Module();
    	module1.setCodeApogeeModule("711MD106");
    	module1.setColonneModule(12);
    	module1.setLigneModuleRN(29);
    	module1.setColonneRecap(12);
    	module1.setPrecisionDecimale(1);
    	module1.setSeuilNote(8);
		this.listeModules.add(module1);
		
		//initialisation PPP
		module1 = new Module();
    	module1.setCodeApogeeModule("711MD107");
    	module1.setColonneModule(13);
    	module1.setLigneModuleRN(32);
    	module1.setColonneRecap(13);
    	module1.setPrecisionDecimale(1);
    	module1.setSeuilNote(8);
    	this.listeModules.add(module1);
		
		//initialisation droit-éco-marketing
		module1 = new Module();
    	module1.setCodeApogeeModule("711MD108");
    	module1.setColonneModule(14);
    	module1.setLigneModuleRN(34);
    	module1.setColonneRecap(14);
    	module1.setPrecisionDecimale(1);
    	module1.setSeuilNote(8);
    	this.listeModules.add(module1);
		
		//initialisation UE2
		module1 = new Module();
    	module1.setCodeApogeeModule("711UE102");
    	module1.setColonneModule(23);
    	module1.setLigneModuleRN(37);
    	module1.setColonneRecap(5);
    	module1.setPrecisionDecimale(3);
    	module1.setSeuilNote(10);
    	this.listeModules.add(module1);
		
		//initialisation Maths-Signal
		module1 = new Module();
    	module1.setCodeApogeeModule("711MD110");
    	module1.setColonneModule(15);
    	module1.setLigneModuleRN(38);
    	module1.setColonneRecap(15);
    	module1.setPrecisionDecimale(1);
    	module1.setSeuilNote(8);
    	this.listeModules.add(module1);

		//initialisation Algo
		module1 = new Module();
    	module1.setCodeApogeeModule("711MD111");
    	module1.setColonneModule(16);
    	module1.setLigneModuleRN(45);
    	module1.setColonneRecap(16);
    	module1.setPrecisionDecimale(1);
    	module1.setSeuilNote(8);
    	this.listeModules.add(module1);

		//initialisation Réseaux
		module1 = new Module();
    	module1.setCodeApogeeModule("711MD112");
    	module1.setColonneModule(17);
    	module1.setLigneModuleRN(48);
    	module1.setColonneRecap(17);
    	module1.setPrecisionDecimale(1);
    	module1.setSeuilNote(8);
    	this.listeModules.add(module1);
		
		//initialisation Infographie
		module1 = new Module();
    	module1.setCodeApogeeModule("711MD113");
    	module1.setColonneModule(18);
    	module1.setLigneModuleRN(51);
    	module1.setColonneRecap(18);
    	module1.setPrecisionDecimale(1);
    	module1.setSeuilNote(8);
    	this.listeModules.add(module1);
		
		//initialisation Intégration web
		module1 = new Module();
    	module1.setCodeApogeeModule("711MD114");
    	module1.setColonneModule(19);
    	module1.setLigneModuleRN(53);
    	module1.setColonneRecap(19);
    	module1.setPrecisionDecimale(1);
    	module1.setSeuilNote(8);
    	this.listeModules.add(module1);
	
		//initialisation Audiovisuel
		module1 = new Module();
    	module1.setCodeApogeeModule("711MD115");
    	module1.setColonneModule(20);
    	module1.setLigneModuleRN(56);
    	module1.setColonneRecap(20);
    	module1.setPrecisionDecimale(1);
    	module1.setSeuilNote(8);
    	this.listeModules.add(module1);
		
	}

    public void initialisationListeEpreuves() {
		
		Double min=20.0,max=0.0,moy=0.0;
    	
		Epreuve epreuve1 = new Epreuve();

		//Epreuve 1
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN101P1");
    	epreuve1.setIntituleEpreuve("Anglais (Vocabulaire et cours)");
    	epreuve1.setEnseignantEpreuve("B. Buchet");
    	epreuve1.setColonneEpreuveEPR(3);
    	epreuve1.setLigneEpreuveRN(9);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);
		//Epreuve 2
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN101P2");
    	epreuve1.setIntituleEpreuve("");
    	epreuve1.setEnseignantEpreuve("B. Buchet");
    	epreuve1.setColonneEpreuveEPR(4);
    	epreuve1.setLigneEpreuveRN(10);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);
		//Epreuve 3
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN101P3");
    	epreuve1.setIntituleEpreuve("");
    	epreuve1.setEnseignantEpreuve("B. Buchet");
    	epreuve1.setColonneEpreuveEPR(5);
    	epreuve1.setLigneEpreuveRN(11);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);
		//Epreuve 4
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN118T1");
    	epreuve1.setIntituleEpreuve("Espagnol (T)");
    	epreuve1.setEnseignantEpreuve("A. Collet / D. Le Clezio");
    	epreuve1.setColonneEpreuveEPR(36);
    	epreuve1.setLigneEpreuveRN(13);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);
		//Epreuve 5
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN118P1");
    	epreuve1.setIntituleEpreuve("Espagnol (P)");
    	epreuve1.setEnseignantEpreuve("A. Collet / D. Le Clezio");
    	epreuve1.setColonneEpreuveEPR(35);
    	epreuve1.setLigneEpreuveRN(14);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);
		//Epreuve 6
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN119T1");
    	epreuve1.setIntituleEpreuve("Allemand (T)");
    	epreuve1.setEnseignantEpreuve("B. Heurtebize");
    	epreuve1.setColonneEpreuveEPR(38);
    	epreuve1.setLigneEpreuveRN(13);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);
		//Epreuve 7
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN119P1");
    	epreuve1.setIntituleEpreuve("Allemand (P)");
    	epreuve1.setEnseignantEpreuve("B. Heurtebize");
    	epreuve1.setColonneEpreuveEPR(37);
    	epreuve1.setLigneEpreuveRN(14);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);
		//Epreuve 8
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN120T1");
    	epreuve1.setIntituleEpreuve("Anglais renforcé (T)");
    	epreuve1.setEnseignantEpreuve("L. Davidson");
    	epreuve1.setColonneEpreuveEPR(40);
    	epreuve1.setLigneEpreuveRN(13);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);
		//Epreuve 9
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN120P1");
    	epreuve1.setIntituleEpreuve("Anglais renforcé (P)");
    	epreuve1.setEnseignantEpreuve("L. Davidson");
    	epreuve1.setColonneEpreuveEPR(39);
    	epreuve1.setLigneEpreuveRN(14);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);		
		//Epreuve 10
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN102T3");
    	epreuve1.setIntituleEpreuve("");
    	epreuve1.setEnseignantEpreuve("");
    	epreuve1.setColonneEpreuveEPR(7);
    	epreuve1.setLigneEpreuveRN(16);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);
		//Epreuve 11
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN102T4");
    	epreuve1.setIntituleEpreuve("");
    	epreuve1.setEnseignantEpreuve("");
    	epreuve1.setColonneEpreuveEPR(8);
    	epreuve1.setLigneEpreuveRN(18);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);
		//Epreuve 12
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN102P2");
    	epreuve1.setIntituleEpreuve("");
    	epreuve1.setEnseignantEpreuve("");
    	epreuve1.setColonneEpreuveEPR(6);
    	epreuve1.setLigneEpreuveRN(17);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);
		//Epreuve 13
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN103T1");
    	epreuve1.setIntituleEpreuve("");
    	epreuve1.setEnseignantEpreuve("");
    	epreuve1.setColonneEpreuveEPR(10);
    	epreuve1.setLigneEpreuveRN(20);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);
		//Epreuve 14
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN103P1");
    	epreuve1.setIntituleEpreuve("");
    	epreuve1.setEnseignantEpreuve("");
    	epreuve1.setColonneEpreuveEPR(9);
    	epreuve1.setLigneEpreuveRN(21);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);
		//Epreuve 15
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN104T1");
    	epreuve1.setIntituleEpreuve("");
    	epreuve1.setEnseignantEpreuve("");
    	epreuve1.setColonneEpreuveEPR(13);
    	epreuve1.setLigneEpreuveRN(23);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);
		//Epreuve 16
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN104P5");
    	epreuve1.setIntituleEpreuve("");
    	epreuve1.setEnseignantEpreuve("");
    	epreuve1.setColonneEpreuveEPR(12);
    	epreuve1.setLigneEpreuveRN(25);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);
		//Epreuve 17
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN104P3");
    	epreuve1.setIntituleEpreuve("");
    	epreuve1.setEnseignantEpreuve("");
    	epreuve1.setColonneEpreuveEPR(11);
    	epreuve1.setLigneEpreuveRN(24);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);
		//Epreuve 18
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN105T1");
    	epreuve1.setIntituleEpreuve("");
    	epreuve1.setEnseignantEpreuve("");
    	epreuve1.setColonneEpreuveEPR(15);
    	epreuve1.setLigneEpreuveRN(27);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);
		//Epreuve 19
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN105P3");
    	epreuve1.setIntituleEpreuve("");
    	epreuve1.setEnseignantEpreuve("");
    	epreuve1.setColonneEpreuveEPR(14);
    	epreuve1.setLigneEpreuveRN(28);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);
		//Epreuve 20
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN106T1");
    	epreuve1.setIntituleEpreuve("");
    	epreuve1.setEnseignantEpreuve("");
    	epreuve1.setColonneEpreuveEPR(17);
    	epreuve1.setLigneEpreuveRN(30);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);
		//Epreuve 21
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN106P1");
    	epreuve1.setIntituleEpreuve("");
    	epreuve1.setEnseignantEpreuve("");
    	epreuve1.setColonneEpreuveEPR(16);
    	epreuve1.setLigneEpreuveRN(31);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);
		//Epreuve 22
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN107P1");
    	epreuve1.setIntituleEpreuve("");
    	epreuve1.setEnseignantEpreuve("");
    	epreuve1.setColonneEpreuveEPR(18);
    	epreuve1.setLigneEpreuveRN(33);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);
		//Epreuve 23
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN108P1");
    	epreuve1.setIntituleEpreuve("");
    	epreuve1.setEnseignantEpreuve("");
    	epreuve1.setColonneEpreuveEPR(19);
    	epreuve1.setLigneEpreuveRN(35);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);
		//Epreuve 24
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN110T1");
    	epreuve1.setIntituleEpreuve("");
    	epreuve1.setEnseignantEpreuve("");
    	epreuve1.setColonneEpreuveEPR(24);
    	epreuve1.setLigneEpreuveRN(39);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);
		//Epreuve 25
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN110P1");
    	epreuve1.setIntituleEpreuve("");
    	epreuve1.setEnseignantEpreuve("");
    	epreuve1.setColonneEpreuveEPR(20);
    	epreuve1.setLigneEpreuveRN(40);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);
		//Epreuve 26
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN110T2");
    	epreuve1.setIntituleEpreuve("");
    	epreuve1.setEnseignantEpreuve("");
    	epreuve1.setColonneEpreuveEPR(25);
    	epreuve1.setLigneEpreuveRN(41);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);
		//Epreuve 27
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN110P2");
    	epreuve1.setIntituleEpreuve("");
    	epreuve1.setEnseignantEpreuve("");
    	epreuve1.setColonneEpreuveEPR(21);
    	epreuve1.setLigneEpreuveRN(42);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);
		//Epreuve 28
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN110P5");
    	epreuve1.setIntituleEpreuve("");
    	epreuve1.setEnseignantEpreuve("");
    	epreuve1.setColonneEpreuveEPR(22);
    	epreuve1.setLigneEpreuveRN(43);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);
		//Epreuve 29
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN110P6");
    	epreuve1.setIntituleEpreuve("");
    	epreuve1.setEnseignantEpreuve("");
    	epreuve1.setColonneEpreuveEPR(23);
    	epreuve1.setLigneEpreuveRN(44);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);
		//Epreuve 30
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN111T1");
    	epreuve1.setIntituleEpreuve("");
    	epreuve1.setEnseignantEpreuve("");
    	epreuve1.setColonneEpreuveEPR(27);
    	epreuve1.setLigneEpreuveRN(46);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);
		//Epreuve 31
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN111P4");
    	epreuve1.setIntituleEpreuve("");
    	epreuve1.setEnseignantEpreuve("");
    	epreuve1.setColonneEpreuveEPR(26);
    	epreuve1.setLigneEpreuveRN(47);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);
		//Epreuve 32
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN112T1");
    	epreuve1.setIntituleEpreuve("");
    	epreuve1.setEnseignantEpreuve("");
    	epreuve1.setColonneEpreuveEPR(28);
    	epreuve1.setLigneEpreuveRN(49);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);
		//Epreuve 33
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN112P1");
    	epreuve1.setIntituleEpreuve("");
    	epreuve1.setEnseignantEpreuve("");
    	epreuve1.setColonneEpreuveEPR(29);
    	epreuve1.setLigneEpreuveRN(50);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);
		//Epreuve 34
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN113P1");
    	epreuve1.setIntituleEpreuve("");
    	epreuve1.setEnseignantEpreuve("");
    	epreuve1.setColonneEpreuveEPR(30);
    	epreuve1.setLigneEpreuveRN(52);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);
		//Epreuve 35
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN114P3");
    	epreuve1.setIntituleEpreuve("");
    	epreuve1.setEnseignantEpreuve("");
    	epreuve1.setColonneEpreuveEPR(32);
    	epreuve1.setLigneEpreuveRN(54);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);
		//Epreuve 36
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN114P2");
    	epreuve1.setIntituleEpreuve("");
    	epreuve1.setEnseignantEpreuve("");
    	epreuve1.setColonneEpreuveEPR(31);
    	epreuve1.setLigneEpreuveRN(55);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);
		//Epreuve 3
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN115T1");
    	epreuve1.setIntituleEpreuve("");
    	epreuve1.setEnseignantEpreuve("");
    	epreuve1.setColonneEpreuveEPR(34);
    	epreuve1.setLigneEpreuveRN(57);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);
		//Epreuve 38
		epreuve1=new Epreuve();
    	epreuve1.setCodeEpreuve("711EN115P2");
    	epreuve1.setIntituleEpreuve("");
    	epreuve1.setEnseignantEpreuve("");
    	epreuve1.setColonneEpreuveEPR(33);
    	epreuve1.setLigneEpreuveRN(58);
    	epreuve1.setMin(min);
    	epreuve1.setMax(max);
    	epreuve1.setMoy(moy);
		this.listeEpreuves.add(epreuve1);
	}    
    

	public void ajoutNotesModuleEtu(SpreadsheetDocuments spreadsheetDocumentsRN, XSpreadsheet xSheet, XSpreadsheet xSheetELP, SpreadsheetDocuments spreadsheetDocumentsNotes, XSpreadsheet xSheetRecap, int codeEtu) {
		
    	//récupération moyenne générale calculée
    	this.ajoutNoteModuleEtu(spreadsheetDocumentsRN, xSheet, xSheetELP, codeEtu, "711SEM11");
    	//récupération moyenne UE1
    	this.ajoutNoteModuleEtu(spreadsheetDocumentsRN, xSheet, xSheetELP, codeEtu, "711UE111");
    	//récupération moyenne Anglais
    	this.ajoutNoteModuleEtu(spreadsheetDocumentsRN, xSheet, xSheetELP, codeEtu, "711MD101");   	
    	//récupération moyenne LV2
    	this.ajoutNoteModuleEtu(spreadsheetDocumentsRN, xSheet, xSheetELP, codeEtu, "711MD117");    	
    	//récupération moyenne InfoCom
    	this.ajoutNoteModuleEtu(spreadsheetDocumentsRN, xSheet, xSheetELP, codeEtu, "711MD102");
    	//récupération moyenne Expression artistique
    	this.ajoutNoteModuleEtu(spreadsheetDocumentsRN, xSheet, xSheetELP, codeEtu, "711MD103");
    	//récupération moyenne Ecriture pour les médias
    	this.ajoutNoteModuleEtu(spreadsheetDocumentsRN, xSheet, xSheetELP, codeEtu, "711MD104");
    	//récupération moyenne comm
    	this.ajoutNoteModuleEtu(spreadsheetDocumentsRN, xSheet, xSheetELP, codeEtu, "711MD105");
    	//récupération moyenne gestion de projet
    	this.ajoutNoteModuleEtu(spreadsheetDocumentsRN, xSheet, xSheetELP, codeEtu, "711MD106");
    	//récupération moyenne PPP
    	this.ajoutNoteModuleEtu(spreadsheetDocumentsRN, xSheet, xSheetELP, codeEtu, "711MD107");
    	//récupération moyenne éco-droit-gestion
    	this.ajoutNoteModuleEtu(spreadsheetDocumentsRN, xSheet, xSheetELP, codeEtu, "711MD108");
    	//récupération moyenne UE2
    	this.ajoutNoteModuleEtu(spreadsheetDocumentsRN, xSheet, xSheetELP, codeEtu, "711UE102");   	
    	//récupération moyenne Maths-Signal
    	this.ajoutNoteModuleEtu(spreadsheetDocumentsRN, xSheet, xSheetELP, codeEtu, "711MD110");
    	//récupération moyenne Algo
    	this.ajoutNoteModuleEtu(spreadsheetDocumentsRN, xSheet, xSheetELP, codeEtu, "711MD111");
    	//récupération moyenne Réseaux
    	this.ajoutNoteModuleEtu(spreadsheetDocumentsRN, xSheet, xSheetELP, codeEtu, "711MD112");
    	//récupération moyenne Infographie
    	this.ajoutNoteModuleEtu(spreadsheetDocumentsRN, xSheet, xSheetELP, codeEtu, "711MD113");
    	//récupération moyenne intégraiton web
    	this.ajoutNoteModuleEtu(spreadsheetDocumentsRN, xSheet, xSheetELP, codeEtu, "711MD114");
    	//récupération moyenne Production audiovisuelle
    	this.ajoutNoteModuleEtu(spreadsheetDocumentsRN, xSheet, xSheetELP, codeEtu, "711MD115");
	}

	
	
public void ajoutNoteModuleEtu(SpreadsheetDocuments spreadsheetDocumentsRN, XSpreadsheet xSheet, XSpreadsheet xSheetELP, int codeEtu, String codeModule) {
		
		String noteRNString,nomEtu,prenomEtu;
    	Double noteRN,noteApogee;
    	int colonneModule,ligneEtu;
    	int ligneRN;
    	Module module;

    	//pour récupérer les infos d'un module
    	module = getModule(codeModule);
    	//pour récupérer le numéro de la colonne de la feuille ELP correspondant à notre module
    	colonneModule = module.getColonneModule();
    	//pour récupérer le numéro de la ligne de la feuille ELP correspondant à notre étudiant
    	ligneEtu=spreadsheetDocumentsRN.getLigneEtu(xSheetELP, codeEtu, this.getNbrEtu());
    	
    	//pour lire la note module de l'étudiant dans son RN
    	ligneRN=module.getLigneModuleRN();
    	
    	noteRNString=spreadsheetDocumentsRN.readStringCellule(xSheet, 7, ligneRN);    	
    	noteRN=spreadsheetDocumentsRN.readDoubleCellule(xSheet, 7, ligneRN);
    	
    	//si la note module a été calculée
    	if(!(noteRNString.equals(""))){
    		//récupération note Module APOGEE	
    		noteApogee=spreadsheetDocumentsRN.readDoubleCellule(xSheetELP, colonneModule, ligneEtu);
    		//il y a une différence entre le calcul APOGEE et le calcul interne
    		if(noteApogee!=noteRN){
    			nomEtu=spreadsheetDocumentsRN.getNomEtu(xSheetELP, ligneEtu);
    			prenomEtu=spreadsheetDocumentsRN.getPrenomEtu(xSheetELP, ligneEtu);
    			System.out.println("ERREUR CALCUL / "+codeModule+" / "+nomEtu+" "+prenomEtu);
    		}
        	//on incrémente le nombre de notes
        	module.setNbrNotes(module.getNbrNotes()+1);
    	}
    	else{
    		noteRN=-1.0;
    	}
    	
    	module.setNoteModuleEtu(codeEtu,noteRN);
	}	
	
	public Module getModule(String codeModule){
	
		int i;
		Module item = new Module();

		for(i = 0; i < this.listeModules.size(); i++){
			item = this.listeModules.get(i);
			if(item.getCodeApogeeModule().equals(codeModule)){
				break;
			}
		}
		
		return item;
		
	}
	
	public Epreuve getEpreuve(String codeEpreuve){
		
		int i;
		Epreuve item = new Epreuve();

		for(i = 0; i < this.listeEpreuves.size(); i++){
			item = this.listeEpreuves.get(i);
			if(item.getCodeEpreuve().equals(codeEpreuve)){
				break;
			}
		}
		
		return item;
		
	}	
	
	public void setClassementInfosModules(){
		
		int i;
		Module item = new Module();
		
		for(i = 0; i < this.listeModules.size(); i++){
			item = this.listeModules.get(i);
			//SI IL Y A DES NOTES POUR CE MODULE ON LANCE LE CALCUL DU CLASSEMENT DU MODULE
			//ET ON DETERMINE MIN, MAX, MOYENNE MODULE
			if(item.getNbrNotes()!=0){
				item.setClassementModule();
				item.setInfosModule();
			}
		}
	}
	
    public double getNoteSportRN(SpreadsheetDocuments spreadsheetDocuments, XSpreadsheet xSheet, int codeEtu){
    	
    	int ligneEtu,colonneNoteSport;
    	String noteString;
    	Double note;
    	
    	ligneEtu=spreadsheetDocuments.getLigneEtu(xSheet,codeEtu,this.getNbrEtu());	
    	colonneNoteSport=this.getColonneELPNoteSport();

    	noteString=spreadsheetDocuments.readStringCellule(xSheet,colonneNoteSport,ligneEtu);
    	
    	if(noteString.equals("")){	
    		note=-1.0;
    	}
    	else{	
        	note=spreadsheetDocuments.readDoubleCellule(xSheet,colonneNoteSport,ligneEtu);    		
    	}
    	
		return note;
    }
    
    public void getInfosPromo(String cheminFichierJSON) {
		
		JsonReader reader = null;
		
		try {
			reader = Json.createReader(new FileReader(cheminFichierJSON));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		JsonObject obj = reader.readObject();
		
		//on récupère le nom de la promo
		this.setNomPromo(obj.getString("nomPromo"));
		//on récupère le code APOGEE du semestre
		this.setCodeApogeeSemestre(obj.getString("codeApogee"));
		//on récupère le nombre d'étudiants;
		this.setNbrEtu(obj.getInt("nbrEtu"));
		//on récupère le chemin du fichier des notes
		this.setUrlNotes(obj.getString("urlNotes"));
		
    }

}