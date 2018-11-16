package logic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Calendar;
import interfaces.iEncrDescrArchivo;
import interfaces.iRSA;

public class EncrDescrArchivo implements iEncrDescrArchivo {
	private iRSA rsaInst;
	public EncrDescrArchivo(){ // usa longitud por defecto
		rsaInst = new RSA();
	}
	
	public EncrDescrArchivo(int longitud){ // usa longitud ingresada como parámetro
		rsaInst = new RSA(longitud);
	}
	
	//Ingresa primos como parámetros
	public EncrDescrArchivo(BigInteger p,BigInteger q){
		if (p.bitLength()>q.bitLength()){
			rsaInst = new RSA(p.bitLength(),p,q);
		}else{
			rsaInst = new RSA(q.bitLength(),p,q);
		}
	}
	
	// Ingresa claves como parámetros
	public EncrDescrArchivo (BigInteger e,BigInteger d,BigInteger n){
		rsaInst = new RSA(e,d,n);
	}
	
	public void encriptarArchivo (String rutaIn, String rutaOut){
		try {			
			BasePropia bp = new BasePropia();
			System.out.println("### INICIO ENCRIPTADO ###");
			System.out.println(Calendar.getInstance().getTime());
			int i = rutaIn.lastIndexOf(".");
			String extension = rutaIn.substring(i); // almacena extensión de archivo a encriptar
			
			FileInputStream streamIn = new FileInputStream(rutaIn);//Crea Stream de entrada con la ruta del archivo a encriptar
			BufferedInputStream bufferIn = new BufferedInputStream(streamIn);
			
			int aux = bufferIn.read(); //lee el primer byte del archivo a encriptar
			BigInteger intGrande;
			FileWriter archivoOut = new FileWriter(rutaOut+extension);//instancia un fileWriter con ruta de salida
			BufferedWriter bufferOut = new BufferedWriter(archivoOut);
			String linea = "";
			byte[] vector;
			while(aux!=-1) {
				while(aux!= -1 && linea.length()<rsaInst.getLongitud()/6) {
					linea = linea + aux+" ";
					aux = bufferIn.read(); //crea una cadena concatenando los bytes leidos, hasta una longitud menor o igual a el tamaño de primos/6
				}
				vector = linea.getBytes("ISO-8859-1"); 
				intGrande = new BigInteger(vector);
				intGrande = rsaInst.encriptar(intGrande);
				bufferOut.write(bp.divReiterada(intGrande));
				bufferOut.newLine();
				linea = "";
			}
		
			bufferOut.flush();
			bufferOut.close();
			bufferIn.close();
			streamIn.close();
			archivoOut.close();
			System.out.println("### FIN ENCRIPTADO ###");
			System.out.println(Calendar.getInstance().getTime());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void desencriptarArchivo(String rutaIn, String rutaOut){
		try {
			BasePropia bp = new BasePropia();
			int i = rutaIn.lastIndexOf(".");
			String extension = rutaIn.substring(i);//se guarda extensión de archivo a desencriptar
			System.out.println("### INICIO DESENCRIPTADO ###");
			System.out.println(Calendar.getInstance().getTime());
			FileReader archivoIn = new FileReader(rutaIn); //se instancia un fileReader con la ruta del archivo a desencriptar
			BufferedReader bufferArchivoIn = new BufferedReader(archivoIn);
			String linea = bufferArchivoIn.readLine();//copia la primer linea del archivo a desencriptar
			FileOutputStream streamOut = new FileOutputStream(rutaOut+extension);//se crea un stream de salida con la ruta más la extensión de salida
			BufferedOutputStream bufferStreamOut = new BufferedOutputStream(streamOut);
			String text, txt2;
			int indice;
			BigInteger intGrande;
			while(linea !=null) {
				
				intGrande= bp.multPonderada(linea);
				intGrande = rsaInst.desencriptar(intGrande);
				text = new String(intGrande.toByteArray(),"ISO-8859-1");
				
				while(text != "") {
					indice = text.indexOf(" ");
					txt2 = text.substring(0,indice);//guarda n txt2 byte por byte
					if (indice != text.length()-1) {
						text = text.replaceFirst(txt2+" ", "");
					}else {
						text = "";
					}
					bufferStreamOut.write((int)new Integer(txt2));//va escribiendo en el stream byte por byte
				}
				linea = bufferArchivoIn.readLine();
			}

			System.out.println("### FIN DESENCRIPTADO ###");
			System.out.println(Calendar.getInstance().getTime());
			bufferArchivoIn.close();
			archivoIn.close();
			bufferStreamOut.flush();
			bufferStreamOut.close();
			streamOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			System.out.println("ERROR");
			e.printStackTrace();
		}
	}

	public iRSA getRsaInst () { return this.rsaInst;}
}
