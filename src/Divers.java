import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.sun.star.io.IOException;
import java.net.MalformedURLException;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;


public class Divers
{

public static void getFileURL(final String filename, final String urlString) throws MalformedURLException, java.io.IOException {
  
		//CODE QUI FONCTIONNE MAIS PAS A TRAVERS LE PROXY
	     BufferedInputStream in = null;
	     FileOutputStream fout = null;
	     try {
	         in = new BufferedInputStream(new URL(urlString).openStream());
	         fout = new FileOutputStream(filename);

	         final byte data[] = new byte[1024];
	         int count;
	         while ((count = in.read(data, 0, 1024)) != -1) {
	             fout.write(data, 0, count);
	         }
	     } finally {
	         if (in != null) {
	             in.close();
	         }
	         if (fout != null) {
	             fout.close();
	         }
	     }
	}

public static void getFileURL2(final String filename, final String urlString) throws Exception {
	
    CloseableHttpClient httpClient = HttpClients.createDefault();
    
    String hostname="www-apogee";
    HttpHost target = new HttpHost(hostname, 80, "http");
    HttpHost proxy = new HttpHost("proxy.univ-lemans.fr", 3128, "http");    
    
    RequestConfig config = RequestConfig.custom()
            .setProxy(proxy)
            .build();

    HttpGet request = new HttpGet(urlString);
    request.setConfig(config);
    
    CloseableHttpResponse httpResponse = httpClient.execute(target, request);    
    
	HttpEntity fileEntity = httpResponse.getEntity();

	if (fileEntity != null) {
	    FileUtils.copyInputStreamToFile(fileEntity.getContent(), new File(filename));
	}

    request.releaseConnection();
     
}

//CETTE METHODE NE MARCHE QUE POUR LES COLONNES ALLANT DE A à AZ
public static String conversionColonneLettre(int colonne){
	
	String lettre;
	
	if(colonne>26){
		colonne=colonne-26;
		lettre = "A"+conversionChiffreLettre(colonne);
	}
	else {
		lettre = conversionChiffreLettre(colonne);
	}
	return lettre;
}

public static String conversionChiffreLettre(int chiffre){
	
	String lettre="A";
	
	switch(chiffre){
	
	case 1: lettre="A";
		break;
	case 2: lettre="B";
		break;
	case 3: lettre="C";
		break;
	case 4: lettre="D";
		break;
	case 5: lettre="E";
		break;
	case 6: lettre="F";
		break;
	case 7: lettre="G";
		break;
	case 8: lettre="H";
		break;
	case 9: lettre="I";
		break;
	case 10: lettre="J";
		break;
	case 11: lettre="K";
		break;
	case 12: lettre="L";
		break;
	case 13: lettre="M";
	break;
	case 14: lettre="N";
	break;
	case 15: lettre="O";
	break;
	case 16: lettre="P";
	break;
	case 17: lettre="Q";
	break;
	case 18: lettre="R";
	break;
	case 19: lettre="S";
	break;
	case 20: lettre="T";
	break;
	case 21: lettre="U";
	break;
	case 22: lettre="V";
	break;
	case 23: lettre="W";
	break;
	case 24: lettre="X";
	break;
	case 25: lettre="Y";
	break;
	case 26: lettre="Z";
	break;
	}
	
	return lettre;
	
}

public static int conversionLettreChiffre(String lettre){
	
	//CETTE METHODE NE MARCHE QUE POUR LES COLONNES ALLANT DE A à Z
	
	int chiffre=-1;
	
	switch(lettre){
	
	case "A": chiffre=0;
		break;
	case "B": chiffre=1;
		break;
	case "C": chiffre=2;
		break;
	case "D": chiffre=3;
		break;
	case "E": chiffre=4;
		break;
	case "F": chiffre=5;
		break;
	case "G": chiffre=6;
		break;
	case "H": chiffre=7;
		break;
	case "I": chiffre=8;
		break;
	case "J": chiffre=9;
		break;
	case "K": chiffre=10;
		break;
	case "L": chiffre=11;
		break;
	case "M": chiffre=12;
	break;
	case "N": chiffre=13;
	break;
	case "O": chiffre=14;
	break;
	case "P": chiffre=15;
	break;
	case "Q": chiffre=16;
	break;
	case "R": chiffre=17;
	break;
	case "S": chiffre=18;
	break;
	case "T": chiffre=19;
	break;
	case "U": chiffre=20;
	break;
	case "V": chiffre=21;
	break;
	case "W": chiffre=22;
	break;
	case "X": chiffre=23;
	break;
	case "Y": chiffre=24;
	break;
	case "Z": chiffre=25;
	break;
	}
	
	return chiffre;
	
}

public static boolean isEqual(double a,double b)
{
	double difference=Math.abs(a-b);
	double precision=0.0006;
	boolean resultat;
	
	if(difference<=precision){
		resultat = true;
	}
	else resultat = false;
		
    return resultat;
}

public static void addMessage(String message){
	//à terme l'ajouter dans un fichier texte
	System.out.println(message);	
}

public static boolean caseContientCodeApogee(String codeCase, String codeApogee){
	
	int i;
	String[] codeCaseDecompose;
	boolean result=false;
	
	//si le code contient un /
	if (codeCase.contains("/")) {
		codeCaseDecompose = codeCase.split("/");
		for(i=0;i<codeCaseDecompose.length;i++){
			if(codeCaseDecompose[i].equals(codeApogee)){
				result=true;
				break;
			}
		}
	} else {	
	    result=codeCase.equals(codeApogee);
	}
	
	return result;
	
}

public static String getNomFichierUrl(String url){

	String[] urlDecompose;
	
	urlDecompose=url.split("/");
	
	return urlDecompose[urlDecompose.length-1];
}

}
