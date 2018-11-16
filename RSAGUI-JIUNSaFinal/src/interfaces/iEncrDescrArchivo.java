package interfaces;

public interface iEncrDescrArchivo {

	void encriptarArchivo(String rutaIn, String rutaOut);

	void desencriptarArchivo(String rutaIn, String rutaOut);

	iRSA getRsaInst();

}