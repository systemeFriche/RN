import com.sun.star.frame.XStorable;
import com.sun.star.lang.IllegalArgumentException;
import com.sun.star.lang.WrappedTargetException;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;

import java.util.ArrayList;
import java.util.List;

import com.sun.star.beans.PropertyValue;
import com.sun.star.beans.PropertyVetoException;
import com.sun.star.beans.UnknownPropertyException;
import com.sun.star.beans.XPropertySet;
import com.sun.star.container.XIndexAccess;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;

import com.sun.star.sheet.XSpreadsheet;
import com.sun.star.sheet.XSpreadsheets;
import com.sun.star.table.XCell;
import com.sun.star.text.XText;
import com.sun.star.sheet.XSpreadsheetDocument;

import ooo.connector.BootstrapSocketConnector;

public class SpreadsheetDocuments {
	
    private XSpreadsheetDocument mxDoc = null;
	private static XMultiComponentFactory mxRemoteServiceManager = null;       
	private static XComponentContext mxRemoteContext = null;   

    //
    // METHODES GENERIQUES 
    //
    //
	 
	 static XMultiComponentFactory getRemoteServiceManager()
	            throws java.lang.Exception
	        { 
	            if (mxRemoteContext == null && mxRemoteServiceManager == null) {
	                // get the remote office context. If necessary a new office
	                // process is started

					//POUR INTELLIJ IDE
					String oooExeFolder = "/Applications/OpenOffice.app/Contents/MacOS/";
					mxRemoteContext=BootstrapSocketConnector.bootstrap(oooExeFolder);

					//SOUS ECLIPSE JUSTE CETTE LIGNE FONCTIONNE
	                //mxRemoteContext = com.sun.star.comp.helper.Bootstrap.bootstrap();

	                System.out.println("Connected to a running office ...");

	                mxRemoteServiceManager = mxRemoteContext.getServiceManager();
	            }
	            return mxRemoteServiceManager;
	        }  	 

	    public XComponent openSpreadsheetDocument(String cheminFichier) throws java.lang.Exception {
	    	
	        // get the remote service manager
	        mxRemoteServiceManager = this.getRemoteServiceManager();
	        
	        // retrieve the Desktop object, we need its XComponentLoader
	        Object desktop = mxRemoteServiceManager.createInstanceWithContext(
	            "com.sun.star.frame.Desktop", mxRemoteContext);
	        
	        XComponentLoader xComponentLoader = (XComponentLoader)
	            UnoRuntime.queryInterface(XComponentLoader.class, desktop);
	        PropertyValue[] loadProps = new PropertyValue[0];
	  
	        XComponent xDoc = xComponentLoader.loadComponentFromURL(
	                cheminFichier, "_blank", 0, loadProps);
	        
	        return xDoc;
	    }
	    
	    public void closeSpreadsheetDocument(XComponent xDoc) throws java.lang.Exception {
	    	
	    	// Conditions: xDoc = m_xLoadedDocument
	    	  // Check supported functionality of the document (model or controller).
	    	
	    	  com.sun.star.frame.XModel xModel =
	    	    (com.sun.star.frame.XModel)UnoRuntime.queryInterface(
	    	      com.sun.star.frame.XModel.class,xDoc);
	    	  if(xModel!=null)
	    	  {
	    	    // It is a full featured office document.
	    	    // Try to use close mechanism instead of a hard dispose().
	    	    // But maybe such service is not available on this model.
	    	    com.sun.star.util.XCloseable xCloseable =
	    	      (com.sun.star.util.XCloseable)UnoRuntime.queryInterface(
	    	        com.sun.star.util.XCloseable.class,xModel);
	    	  if(xCloseable!=null)
	    	{ try
	    	      {
	    	        // use close(boolean DeliverOwnership)
	    	        // The boolean parameter DeliverOwnership tells objects vetoing the close process that they may
	    	        // assume ownership if they object the closure by throwing a CloseVetoException
	    			// Here we give up ownership. To be on the safe side, catch possible veto exception anyway.
	    	        
	    	xCloseable.close(true);
	    	      }
	    	      catch(com.sun.star.util.CloseVetoException exCloseVeto)
	    	      {
	    	      }
	    	}
	    	}
	    }
	    
	    public void saveSpreadsheetDocumentODS(XComponent xDoc, String cheminFichier) throws java.lang.Exception {  		

	    	
	    	//si fichier existe déjà écrase le fichier
	        XStorable xStorable = (XStorable)UnoRuntime.queryInterface(
	                XStorable.class, xDoc);
	        
	            PropertyValue[] storeProps = new PropertyValue[1];
	            storeProps[0] = new PropertyValue();
	            
	            xStorable.storeAsURL(cheminFichier, storeProps); 	
	    }
	    
	    public void exportSpreadsheetDocumentPDF(XComponent xDoc, String cheminFichier) throws java.lang.Exception {  		
	    	
	    	//si fichier existe déjà écrase le fichier
	        XStorable xStorable = (XStorable)UnoRuntime.queryInterface(
	                XStorable.class, xDoc);
	        
	            PropertyValue[] storeProps = new PropertyValue[1];
	            storeProps[0] = new PropertyValue();
	            
	            storeProps[0].Name = "FilterName";
	            storeProps[0].Value = "calc_pdf_Export";
	            
	            xStorable.storeToURL(cheminFichier, storeProps);       
	    } 
	    
	    public XSpreadsheet getFeuille(XComponent xDoc, int index){
	    
	    XSpreadsheet xSheet=null;
        XSpreadsheetDocument xSpreadSheetDoc = null;
	    
        try {
        xSpreadSheetDoc = (XSpreadsheetDocument) UnoRuntime.queryInterface(
                XSpreadsheetDocument.class, xDoc);
	    	
        XSpreadsheets xSheets = xSpreadSheetDoc.getSheets();
        
        XIndexAccess oIndexSheets = (XIndexAccess) UnoRuntime.queryInterface(XIndexAccess.class, xSheets);
        
        xSheet = (XSpreadsheet) UnoRuntime.queryInterface(XSpreadsheet.class, oIndexSheets.getByIndex(index));
        
        } catch(Exception e){            
            System.err.println(" Exception " + e);
        }
        
        return xSheet;
        
	    }
	    
	    public int readIntCellule(XSpreadsheet xSheet, int x, int y){
		    
	        XCell xCell = null;
		    int valeur;
		    
	        try {
	            xCell = xSheet.getCellByPosition(x,y);
	        } catch (com.sun.star.lang.IndexOutOfBoundsException ex) {
	            System.err.println("Impossible de récupérer la cellule");
	            ex.printStackTrace(System.err);
	        }	    
		    
	        valeur = (int) xCell.getValue();
	        
	        return valeur;
	        
		    }
	    
	    public Double readDoubleCellule(XSpreadsheet xSheet, int x, int y){
	    
        XCell xCell = null;
	    Double valeur = null;
	    
        try {
            xCell = xSheet.getCellByPosition(x,y);
        } catch (com.sun.star.lang.IndexOutOfBoundsException ex) {
            System.err.println("Impossible de récupérer la cellule");
            ex.printStackTrace(System.err);
        }	    
	    
        valeur = (Double) xCell.getValue();
        
        return valeur;
        
	    }
	    
	    public String readStringCellule(XSpreadsheet xSheet, int x, int y){
		    
	        XCell xCell = null;
	        XText xCellText;
		    String texte = null;
		    
	        try {
	            xCell = xSheet.getCellByPosition(x,y);
		        xCellText = (XText)UnoRuntime.queryInterface(XText.class, xCell);	
		        //texte = (String) xCell.getFormula();
		        texte=xCellText.getString();
	        } catch (com.sun.star.lang.IndexOutOfBoundsException ex) {
	            System.err.println("Impossible de récupérer la cellule");
	            ex.printStackTrace(System.err);
	        }	    
	        return texte;  
		    }	    
	    
	    public void writeDoubleCellule(XSpreadsheet xSheet, int x, int y, Double valeur){
		    
	        XCell xCell = null;
		    
	        try {
	            xCell = xSheet.getCellByPosition(x,y);
	        } catch (com.sun.star.lang.IndexOutOfBoundsException ex) {
	            System.err.println("Impossible de récupérer la cellule");
	            ex.printStackTrace(System.err);
	        }	    

	        xCell.setValue(valeur);
	        
		}

	    public void writeDoubleCelluleCouleur(XSpreadsheet xSheet, int x, int y, Double valeur,Double limite) throws UnknownPropertyException, PropertyVetoException, IllegalArgumentException, WrappedTargetException{
		   
	    	String style;
	    	
	    	if(limite==10.0){
	    		style="noteBasse";
	    	}
	    	else{
	    		style="noteModBasse";    		
	    	}
	    	
	    	
	        try {
	            XCell xCell = xSheet.getCellByPosition(x,y);
				XPropertySet xCellProps = (com.sun.star.beans.XPropertySet) 
						UnoRuntime.queryInterface(com.sun.star.beans.XPropertySet.class, xCell);
	            
				if(valeur<limite){
					//style couleur = alerte note basse
					xCellProps.setPropertyValue("CellStyle", style);
				}
				else{
					//style noteOK
					xCellProps.setPropertyValue("CellStyle", "noteOK");
				}
				
		        xCell.setValue(valeur);
	            
	        } catch (com.sun.star.lang.IndexOutOfBoundsException ex) {
	            System.err.println("Impossible de récupérer la cellule");
	            ex.printStackTrace(System.err);
	        }	    


	        
		}

	    public void writeIntCellule(XSpreadsheet xSheet, int x, int y, int valeur){
		    
	        XCell xCell = null;
		    
	        try {
	            xCell = xSheet.getCellByPosition(x,y);
	        } catch (com.sun.star.lang.IndexOutOfBoundsException ex) {
	            System.err.println("Impossible de récupérer la cellule");
	            ex.printStackTrace(System.err);
	        }	    
	        xCell.setValue(valeur);  
		}
	    
	    public void writeStringCellule(XSpreadsheet xSheet, int x, int y, String valeur){
		    
	        XCell xCell = null;
	        XText xCellText;
	        
		    
	        try {
	            xCell = xSheet.getCellByPosition(x,y);
	        } catch (com.sun.star.lang.IndexOutOfBoundsException ex) {
	            System.err.println("Impossible de récupérer la cellule");
	            ex.printStackTrace(System.err);
	        }	

	        xCellText = (XText)UnoRuntime.queryInterface(XText.class, xCell);
	        xCellText.setString(valeur);
	    }
	    
	    public void writeFormulaCellule(XSpreadsheet xSheet, int x, int y, String formule){
		    
	        XCell xCell = null;
		    
	        try {
	            xCell = xSheet.getCellByPosition(x,y);
	        } catch (com.sun.star.lang.IndexOutOfBoundsException ex) {
	            System.err.println("Impossible de récupérer la cellule");
	            ex.printStackTrace(System.err);
	        }	
	        
	        xCell.setFormula(formule);
		}	
	    

	    public List<NoteElement> getNotes(XSpreadsheet xSheet, int colonne, int nbrEtu){
	    	
	    	List<NoteElement> notesElement = new ArrayList<NoteElement>();
			
			int i;
			int codeEtu;
			Double valeur;
			String valeurString;
			
			for(i = 1; i <= nbrEtu; i++){
				
				NoteElement note = new NoteElement();
				
				codeEtu = this.readIntCellule(xSheet,0,i);
				valeurString=this.readStringCellule(xSheet, colonne, i);
				//si pas de note alors étudiant ABJ on met une note égale à -1 pour la détecter dans les traitements ultérieurs
				if(valeurString.equals("")){
					valeur=-1.0;
				}
				else{
					valeur = this.readDoubleCellule(xSheet,colonne,i);
				}
					
				note.setCodeEtu(codeEtu);
				note.setNoteEtu(valeur);  
				notesElement.add(note);	
			}
			
			return notesElement;
	    	
	    }
	    
	    public List<NoteElement> getCodesEtu(XSpreadsheet xSheet, int colonne, int nbrEtu){
	    	
	    	List<NoteElement> notesElement = new ArrayList<NoteElement>();
			
			int i;
			
			for(i = 1; i <= nbrEtu; i++){
				
				NoteElement note = new NoteElement();
					
				note.setCodeEtu(this.readIntCellule(xSheet,0,i));
				note.setNoteEtu(-1.0);  
				notesElement.add(note);	
			}
			
			return notesElement;   	
	    }

	    
	    public int getNbrNotes(XSpreadsheet xSheet, int colonne,int nbrEtu){
	    	
	    	int i=0, nbrNotes=0;
	    	String noteString;
	    	
	    	for(i=0;i<nbrEtu;i++){
	    		noteString=readStringCellule(xSheet,colonne,i+1);
	    		if(!(noteString.equals(""))){
	    			nbrNotes+=1;
	    		}
	    	}
			return nbrNotes;
	    }
	
	    public String getNomEtu(XSpreadsheet xSheet, int ligne){
			
			String nom;
			
			nom = this.readStringCellule(xSheet,1,ligne);

			return nom;
	    	
	    } 	

	    public String getPrenomEtu(XSpreadsheet xSheet, int ligne){
			
			String prenom;
			
			prenom = this.readStringCellule(xSheet,2,ligne);

			return prenom;
	    	
	    }
	    
	    public int getLigneEtu(XSpreadsheet xSheet, int codeEtu, int nbrEtu){
		
			int ligne,i;
			int codeEtuFeuille;
			
			for (i=1;i<=nbrEtu;i++){
				codeEtuFeuille = this.readIntCellule(xSheet,0,i);
				if(codeEtuFeuille==codeEtu){
					break;
				}
			}
			
			ligne = i;

			return ligne;
	    } 	    

	    //méthode pour récupérer la colonne du fichier d'export des notes d'un élément (sem,UE,module,épreuve)
	    public int getColonneNoteExport(XSpreadsheet xSheet, String codeApogeeElement){
			
			int colonne=-1;
			String code;
			String message;
			
			do{
				colonne++;
				code = this.readStringCellule(xSheet,colonne,0);
				if(code.equals("")){
					message="L'élément "+codeApogeeElement+" n'est pas présent dans le fichier d'export des notes.";
					Divers.addMessage(message);
					break;
				}
			}while(!(code.equals(codeApogeeElement)));
			
			return colonne;
	    }
	    
	    //méthode pour récupérer la colonne du fichier d'export des notes d'un élément (sem,UE,module,épreuve)
	    public int getLigneElementRN(XSpreadsheet xSheet, String codeApogeeElement){
			
			int colonne=Divers.conversionLettreChiffre(Constantes.COL_CODE_APOGEE_ELEMENT_RN),ligne=-1;
			String code;
			String message;
			
			//on se positionne sur le premier code APOGEE du RN
			do{
				ligne++;
				code = this.readStringCellule(xSheet,colonne,ligne);
			}while(code.equals(""));
			
			while(!(Divers.texteContientCodeApogee(code,codeApogeeElement))){
				ligne++;
				code = this.readStringCellule(xSheet,colonne,ligne);
				
				if(code.equals("")){
					message="L'élément "+codeApogeeElement+" n'est pas présent dans le relevé de notes.";
					Divers.addMessage(message);
					ligne=-1;
					break;
				}
			}
			return ligne;
	    } 
	    
	    
	    //
	    // METHODE POUR LIRE/ECRIRE DANS LE RELEVE DE NOTE
	    //
	    //
	    
	    public void writeCodeEtuRN(XSpreadsheet xSheet, int codeEtu){
	    	String codeEtuString;
	    	codeEtuString = "Code : "+codeEtu;
			this.writeStringCellule(xSheet,1,0,codeEtuString);	    	
	    } 	    
	    public void writeNomEtuRN(XSpreadsheet xSheet, String nomEtu){	
	    	nomEtu="Nom : "+nomEtu;
			this.writeStringCellule(xSheet,1,1,nomEtu);   	
	    } 
	    public void writePrenomEtuRN(XSpreadsheet xSheet, String prenomEtu){
	    	prenomEtu="Prénom : "+prenomEtu;    	
			this.writeStringCellule(xSheet,1,2,prenomEtu);    	
	    }
	    public void writeNoteEtuRN(XSpreadsheet xSheet, int ligneEpreuve, Double noteEpreuve){
	    	int colonne = Divers.conversionLettreChiffre(Constantes.COL_NOTE_ETU_ELEMENT_RN);
			this.writeDoubleCellule(xSheet,colonne,ligneEpreuve,noteEpreuve);    	
	    }
	    public void writeMinRN(XSpreadsheet xSheet, int ligneEpreuve, Double min){
	    	int colonne = Divers.conversionLettreChiffre(Constantes.COL_MIN_ELEMENT_RN);
			this.writeDoubleCellule(xSheet,colonne,ligneEpreuve,min);	
	    }
	    public void writeMaxRN(XSpreadsheet xSheet, int ligneEpreuve, Double max){	
	    	int colonne = Divers.conversionLettreChiffre(Constantes.COL_MAX_ELEMENT_RN);
			this.writeDoubleCellule(xSheet,colonne,ligneEpreuve,max);	
	    }
	    public void writeMoyRN(XSpreadsheet xSheet, int ligneEpreuve, Double moy){
	    	int colonne = Divers.conversionLettreChiffre(Constantes.COL_MOY_ELEMENT_RN);
			this.writeDoubleCellule(xSheet,colonne,ligneEpreuve,moy);	
	    } 	    
	    public void writeClassementRN(XSpreadsheet xSheet, int ligneEpreuve, String classement){
	    	int colonne = Divers.conversionLettreChiffre(Constantes.COL_CLASSEMENT_ELEMENT_RN);
			this.writeStringCellule(xSheet,colonne,ligneEpreuve,classement);	
	    } 
	    public void writeIntituleRN(XSpreadsheet xSheet, int ligneEpreuve, String intitule){		
			this.writeStringCellule(xSheet,3,ligneEpreuve,intitule);	
	    } 
	    public void writeEnseignantRN(XSpreadsheet xSheet, int ligneEpreuve, String enseignant){		
			this.writeStringCellule(xSheet,5,ligneEpreuve,enseignant);	
	    }
	    
	    //
	    // METHODE POUR LIRE/ECRIRE DANS L'ONGLET RECAP
	    //
	    //
	    
	    public void setInfosEtuRecap(XSpreadsheet xSheet, int ligne, int codeEtu, String nomEtu, String prenomEtu){
	    	this.writeIntCellule(xSheet, 0, ligne+1, codeEtu);
	    	this.writeStringCellule(xSheet, 1, ligne+1, nomEtu);	    	
	    	this.writeStringCellule(xSheet, 2, ligne+1, prenomEtu);
	    }

		public void writeNoteModuleRecap(XSpreadsheet xSheet, int col, int lig, double valeur,double limite) throws UnknownPropertyException, PropertyVetoException, IllegalArgumentException, WrappedTargetException {
			
			//double valeurArrondie;
			//double precision=(Double)Math.pow(10, precisionDecimale);
			
			//valeurArrondie=Math.round(valeur*precision)/precision;
			//this.writeDoubleCelluleCouleur(xSheet,i,j,valeurArrondie,limite);
			this.writeDoubleCelluleCouleur(xSheet,col,lig,valeur,limite);
			
		}
		
}