package interfaces;

import java.math.BigInteger;

public interface iRSA {

	BigInteger encriptar(BigInteger msg);

	BigInteger desencriptar(BigInteger msg);
	
	void regenerarClaves();

	BigInteger getP();

	BigInteger getQ();

	BigInteger getE();

	BigInteger getD();

	BigInteger getN();

	int getLongitud();

}