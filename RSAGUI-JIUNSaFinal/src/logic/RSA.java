package logic;

import java.math.BigInteger;
import java.security.SecureRandom;

import interfaces.iRSA;

public class RSA implements iRSA {
	private BigInteger p, q, e, d, n;
	private int longitud = 1024; // tamaño de primos 1024 bits por defecto

	public RSA() { // Usa longitud por defecto
		generarPrimos();
		cargarN();
		generarClaves();
	}
	
	public RSA(int bits) { // Usa tamaño de primos en bits ingresado como parametro
		longitud = bits;
		generarPrimos();
		cargarN();
		generarClaves();
	}
	
	public RSA(int bits, BigInteger p, BigInteger q) { //ingresa long y primos
		longitud = bits;
		this.p = p;
		this.q = q;
		cargarN();
		generarClaves();
	}
	public RSA(BigInteger e, BigInteger d, BigInteger n){
		this.e = e;
		this.d = d;
		this.n = n;
		this.longitud = n.bitLength()/2;
		this.p = new BigInteger("0");
		this.q = new BigInteger("0");
	}
	// Genera los numeros primos p y q
	private void  generarPrimos() {
		SecureRandom azar = new SecureRandom();
		p=new BigInteger(longitud,100,azar); 
		do q=new BigInteger(longitud,100,azar); 
		while(p.compareTo(q)==0);
	}
	//Calcula n
	private void cargarN() {n = p.multiply(q);}
	
	//Genera Claves
	private void generarClaves() {
		BigInteger aux = new BigInteger("2");
		BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
		e = new BigInteger("3");
		while((phi.gcd(e).compareTo(new BigInteger("1")))!=0) 
			e=e.add(aux);
		d=e.modInverse(phi);
	}
	
	public void regenerarClaves() {
		BigInteger aux = new BigInteger("2");
		BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
		e = e.add(aux);
		while((phi.gcd(e).compareTo(new BigInteger("1")))!=0) 
			e=e.add(aux);
		d=e.modInverse(phi);
	}
	
	public BigInteger encriptar(BigInteger msg) {
		BigInteger aux= new BigInteger("-1");
		if (msg.compareTo(new BigInteger("0"))==-1) {
			return (msg.abs().modPow(e, n)).multiply(aux);
		}else {
			return msg.modPow(e, n);
		}
	}
	
	public BigInteger desencriptar(BigInteger msg) {
		BigInteger aux= new BigInteger("-1");
		if (msg.compareTo(new BigInteger("0"))==-1) {
			return (msg.abs().modPow(d, n)).multiply(aux);
		}else {
			return msg.modPow(d, n);
		}	
	}

	public BigInteger getP() {return p;}

	public BigInteger getQ() {return q;}

	public BigInteger getE() {return e;}

	public BigInteger getD() {return d;}

	public BigInteger getN() {return n;}

	public int getLongitud() {return longitud;}
	
}
