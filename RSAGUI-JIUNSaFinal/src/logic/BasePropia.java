package logic;

import java.math.BigInteger;

public class BasePropia {
	private final String base = "93";
	
	public String divReiterada(BigInteger a) {
		String b="";
		if  (a.divide(new BigInteger(base)).equals(new BigInteger("0"))) {
			b = (char)(a.mod(new BigInteger(base)).intValue()+33)+"";
		}else {
			b = divReiterada(a.divide(new BigInteger(base)))+(char)(a.mod(new BigInteger(base)).intValue()+33);
		}
		return b;
	}
	public BigInteger multPonderada(String A) {
		BigInteger n = new BigInteger("0");
		BigInteger aux;
		int cont=0;
		BigInteger b= new BigInteger(base);
		for(int i=A.length()-1;i>=0;i--) {
			aux= new BigInteger(((int)(A.charAt(i)))-33+"");
			n= n.add(aux.multiply(b.pow(cont)));
			cont++;
		}
		return n;
	}
}
