package gui;

import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import interfaces.iEncrDescrArchivo;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import logic.EncrDescrArchivo;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.awt.Font;

import net.miginfocom.swing.MigLayout;

import javax.swing.BoxLayout;
import javax.swing.UIManager;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class gui {

	private JFrame frmRsa;
	private JTextField textFieldP1;
	private JTextField textFieldQ1;
	private JTextField textFieldE1;
	private JTextField textFieldD1;
	private JTextField textFieldN1;
	private JTextField textFieldClaves2;
	private JTextField textFieldE2;
	private JTextField textFieldN2;
	private JTextField textFieldArchivo2;
	private JTextField textFieldClaves3;
	private JTextField textFieldD3;
	private JTextField textFieldN3;
	private JTextField textFieldArchivo3;
	private iEncrDescrArchivo instanciaRSA;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui window = new gui();
					window.frmRsa.setVisible(true);
					window.frmRsa.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */

	public gui() {
		initialize();
	}

	// metodo que lee los archivos .puk y prk obteniendo las claves
	private void leerClaves(String ruta, String [] clave ) {
		try {
			FileReader entrada = new FileReader(ruta);
			BufferedReader miBuffer = new BufferedReader(entrada);
			clave[0] = miBuffer.readLine();
			clave[1] = miBuffer.readLine();
			miBuffer.close();
			entrada.close();
		} catch (IOException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	// resta dos fechas y devuelve valor en segundos
	private static int restarFechas(Date fechaIn, Date fechaFinal ){
		long in = fechaIn.getTime();
		long fin = fechaFinal.getTime();
		Long diff= (fin-in)/1000;
		return diff.intValue();
	}
	
	SimpleDateFormat formatoTiempo = new SimpleDateFormat("HH:mm:ss");
    
	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })//Warnings suprimidos para comboBoxTama\u00f1o1
	private void initialize() {
		String aux="",dirClaves;
		File homePath = new File(ClassLoader.getSystemClassLoader().getResource(".").getPath());
		String homePathString =homePath.toString().replaceAll("%20", " ");
//		homePathString = homePathString.replaceAll("%20", " ");
		if(homePathString.indexOf("\\")!=-1) {
			aux="\\Claves Generadas";
			dirClaves =homePathString+aux+"\\";
		}else {
			aux = "/Claves Generadas";
			dirClaves =homePathString+aux+"/";
		}
		File homePathClaves = new File(homePathString+aux);
		
		frmRsa = new JFrame();
		BufferedImage image = null;
		try {
			image = ImageIO.read(this.getClass().getResource("/rsa_tabs_images/rsa_3.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		frmRsa.setTitle("RSA FileEnDecrypter v.1.08.23");
		frmRsa.setBounds(100, 100, 510, 310);
		frmRsa.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRsa.getContentPane().setLayout(new BoxLayout(frmRsa.getContentPane(), BoxLayout.X_AXIS));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

		frmRsa.getContentPane().add(tabbedPane);
		
		JPanel panelDesencriptar = new JPanel();
		

		panelDesencriptar.setBorder(new CompoundBorder());
		
		tabbedPane.addTab("Desencriptar", null, panelDesencriptar, null);
		
		JLabel lblClaves3 = new JLabel("Claves:");
		
		textFieldClaves3 = new JTextField();
		textFieldClaves3.setColumns(10);
		
		textFieldD3 = new JTextField();
		textFieldD3.setColumns(10);
		
		textFieldN3 = new JTextField();
		textFieldN3.setColumns(10);
		
		JLabel lblArchivo3 = new JLabel("Archivo:");
		
		textFieldArchivo3 = new JTextField();
		textFieldArchivo3.setColumns(10);
		panelDesencriptar.setLayout(new MigLayout("", "[20.00][30.00][280.00,grow][18px][120.00,right]", "[23px][25px][19px][19px][25px][25px][70px,grow]"));
		
		JLabel lblTitulo3 = new JLabel("Desencriptaci\u00f3n de archivos");
		JButton btnBuscar3 = new JButton("Buscar...");
		btnBuscar3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frmRsa, "¡Cuidado! Est\u00e1 por cargar clave privada diferente a su clave por defecto.","Advertencia",JOptionPane.WARNING_MESSAGE);
				JFileChooser Explorador = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("*prk", "prk");
				Explorador.setFileFilter(filter);

				if(Explorador.showOpenDialog(Explorador)==JFileChooser.APPROVE_OPTION){
					if(filter.accept(Explorador.getSelectedFile())==false) {
						JOptionPane.showMessageDialog(frmRsa, "No seleccion\u00f3 archivo valido .prk.","Advertencia",JOptionPane.WARNING_MESSAGE);
						textFieldClaves3.setText("");
						textFieldN3.setText("");
						textFieldD3.setText("");
					}else {
						textFieldClaves3.setText(Explorador.getSelectedFile().getAbsolutePath());
						String[] clave = new String[2];
						leerClaves(Explorador.getSelectedFile().getAbsolutePath(),clave);
						textFieldD3.setText(clave[0]);
						textFieldN3.setText(clave[1]);
					}
				}
			}
		});
		
		
		
		JButton btnAyuda3 = new JButton("?");
		btnAyuda3.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnAyuda3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frmRsa, "Instrucciones de Encriptaci\u00f3n:"
						+ "\n1_Necesita generar sus claves por defecto en pesta\u00f1a \"Claves\"."
						+ " Si lo desea puede seleccionar un\narchivo de clave privada (.prk) presionando "
						+ "el bot\u00f3n [Buscar...] o puede ingresarlas manualmente."
						+"\n2_Seleccione el archivo que desea desencriptar presionando el bot\u00f3n [Buscar...]."
						+"\n3_Presione el bot\u00f3n [Desencriptar], el archivo desencriptado "
						+ "se almacenar\u00e1 en el mismo\ndirectiorio del archivo encriptado.");
			}
		});
		

		JLabel labelD3 = new JLabel("d:");
		
				
				JLabel lblN3 = new JLabel("n:");
				JScrollPane scrollPane3 = new JScrollPane();
				JTextArea textArea3 = new JTextArea();
				textArea3.setEditable(false);
				scrollPane3.setViewportView(textArea3);		
				JButton btnBuscarArchivo3 = new JButton("Buscar...");
				btnBuscarArchivo3.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JFileChooser Explorador = new JFileChooser();
						if(Explorador.showOpenDialog(Explorador)==JFileChooser.APPROVE_OPTION){	
							textFieldArchivo3.setText(Explorador.getSelectedFile().getAbsolutePath());
						}
					}
				});
				
				JButton btnDesencriptar = new JButton("Desencriptar");
				btnDesencriptar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(textFieldD3.getText().equals("")!=true && textFieldN3.getText().equals("")!=true){
							if(textFieldArchivo3.getText().equals("")!=true){
								Calendar iniTime= Calendar.getInstance();
								instanciaRSA=new EncrDescrArchivo(new BigInteger("0"),new BigInteger(textFieldD3.getText()),new BigInteger(textFieldN3.getText()));
								int i = textFieldArchivo3.getText().lastIndexOf(".");
								String extension = textFieldArchivo3.getText().substring(i);
								String rutaOut = textFieldArchivo3.getText().substring(0, i);
								instanciaRSA.desencriptarArchivo(textFieldArchivo3.getText(), rutaOut+"[DECIFRADO]");
								Calendar finTime=Calendar.getInstance();
								textArea3.append("-DESENCRIPTACI\u00d3N FINALIZADA CON \u00c9XITO\n");
								textArea3.append("-Se cre\u00f3 el archivo en:\n"+rutaOut+"[DECIFRADO]"+extension+"\n");
								textArea3.append("-El proceso tom\u00f3 "+restarFechas(iniTime.getTime(), finTime.getTime())+" segundos.\n\n");
							}else{
								JOptionPane.showMessageDialog(frmRsa, "No carg\u00f3 archivo a desencriptar.","Advertencia",JOptionPane.WARNING_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(frmRsa, "Debe ingresar clave privada.","Advertencia",JOptionPane.WARNING_MESSAGE);
						}


							}
						});
						
				
				tabbedPane.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent arg0) {
						if(tabbedPane.getSelectedIndex()==0){
								if(new File(dirClaves+"PrivateKey.prk").exists()){
									textFieldClaves3.setText(dirClaves+"PrivateKey.prk");
									String[] clave = new String[2];
									leerClaves(dirClaves+"PrivateKey.prk",clave);
									textFieldD3.setText(clave[0]);
									textFieldN3.setText(clave[1]);
								}else{
									JOptionPane.showMessageDialog(frmRsa, "No se encontr\u00f3 clave por defecto o no se pudo cargar archivo .prk."
											+ "\nCargue un archivo .prk, ingrese su clave privada manualmente o\ngenere sus claves en la pesta\u00f1a \"Claves\".","Advertencia",JOptionPane.WARNING_MESSAGE);
								}
						}
					}
				});
				
					if(new File(dirClaves+"PrivateKey.prk").exists()){
						textFieldClaves3.setText(dirClaves+"PrivateKey.prk");
						String[] clave = new String[2];
						leerClaves(dirClaves+"PrivateKey.prk",clave);
						textFieldD3.setText(clave[0]);
						textFieldN3.setText(clave[1]);
					}else{
						JOptionPane.showMessageDialog(frmRsa, "No se encontr\u00f3 clave por defecto o no se pudo cargar archivo .prk."
								+ "\nCargue un archivo .prk, ingrese su clave privada manualmente o\ngenere sus claves en la pesta\u00f1a \"Claves\".","Advertencia",JOptionPane.WARNING_MESSAGE);
					}
				
				
						panelDesencriptar.add(lblTitulo3, "cell 0 0 3 1,alignx left,aligny center");
						panelDesencriptar.add(btnAyuda3, "cell 4 0,alignx right,aligny center");
						panelDesencriptar.add(btnBuscar3, "cell 4 1,alignx right,aligny center");
						panelDesencriptar.add(labelD3, "cell 0 2,alignx left,aligny center");
						panelDesencriptar.add(lblN3, "cell 0 3,alignx left,aligny center");
						panelDesencriptar.add(btnBuscarArchivo3, "cell 4 4,alignx right,aligny center");
						panelDesencriptar.add(btnDesencriptar, "cell 3 5 2 1,alignx right,aligny center");
						panelDesencriptar.add(lblClaves3, "cell 0 1 2 1,alignx left,aligny center");
						panelDesencriptar.add(textFieldClaves3, "cell 2 1 2 1,growx,aligny center");
						panelDesencriptar.add(textFieldD3, "cell 1 2 4 1,growx,aligny top");
						panelDesencriptar.add(textFieldN3, "cell 1 3 4 1,growx,aligny top");
						panelDesencriptar.add(lblArchivo3, "cell 0 4 2 1,alignx left,growy");
						panelDesencriptar.add(textFieldArchivo3, "cell 2 4 2 1,growx,aligny top");
						panelDesencriptar.add(scrollPane3, "cell 0 6 5 1,grow");
		//Fin primer pestaña
		
		JPanel panelEncriptar = new JPanel();
		panelEncriptar.setBorder(new CompoundBorder());
		tabbedPane.addTab("Encriptar", null, panelEncriptar, null);
		
		JLabel lblArchivo2 = new JLabel("Archivo:");
		panelEncriptar.setLayout(new MigLayout("", "[20.00][30.00][280.00,grow][18px][120.00,right]", "[23px][25px][19px][19px][25px][][109.00]"));
		
		JLabel lblTitulo2 = new JLabel("Encriptaci\u00f3n de archivos\n");
		
		JButton btnAyuda2 = new JButton("?");
		btnAyuda2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frmRsa, 
						"Instrucciones de Encriptaci\u00f3n:"
						+ "\n1_Seleccione el archivo de clave p\u00fablica (.puk) presionando el bot\u00f3n [Buscar...] \no ingr\u00E9selas manualmente."
						+"\n2_Seleccione el archivo que desea encriptar presionando el bot\u00f3n [Buscar...]."
						+"\n3_Presione el bot\u00f3n [Encriptar], el archivo encriptado se almacenar\u00e1 en el mismo\ndirectiorio del archivo encriptado.");
			}
		});
		btnAyuda2.setFont(new Font("Tahoma", Font.BOLD, 10));

		JLabel lblClave2 = new JLabel("Claves:");

		textFieldClaves2 = new JTextField();
		textFieldClaves2.setColumns(10);

		JButton btnBuscar2 = new JButton("Buscar...");
		btnBuscar2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser Explorador = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("*puk", "puk");
				Explorador.setFileFilter(filter);
				if(Explorador.showOpenDialog(Explorador)==JFileChooser.APPROVE_OPTION){
					if(filter.accept(Explorador.getSelectedFile())==false) {
						JOptionPane.showMessageDialog(frmRsa, "No seleccion\u00f3 un archivo .puk valido","Advertencia",JOptionPane.WARNING_MESSAGE);
						textFieldClaves2.setText("");
						textFieldN2.setText("");
						textFieldE2.setText("");
					}else {
						textFieldClaves2.setText(Explorador.getSelectedFile().getAbsolutePath());
						String[] clave = new String[2];
						leerClaves(Explorador.getSelectedFile().getAbsolutePath(),clave);
						textFieldE2.setText(clave[0]);
						textFieldN2.setText(clave[1]);
					}
				}
			}
		});
			
		JLabel lblE2 = new JLabel("e:");
		
		
		textFieldE2 = new JTextField();
		textFieldE2.setColumns(10);
		
		JLabel lblN2 = new JLabel("n:");
	
		textFieldN2 = new JTextField();
		textFieldN2.setColumns(10);
	
		textFieldArchivo2 = new JTextField();
		textFieldArchivo2.setColumns(10);
		
		JScrollPane scrollPane2 = new JScrollPane();		
		JTextArea textArea2 = new JTextArea();
		textArea2.setEditable(false);
		scrollPane2.setViewportView(textArea2);
	
		JButton btnBuscarArchivo2 = new JButton("Buscar...");
		btnBuscarArchivo2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser Explorador = new JFileChooser();
				if(Explorador.showOpenDialog(Explorador)==JFileChooser.APPROVE_OPTION){
					textFieldArchivo2.setText(Explorador.getSelectedFile().getAbsolutePath());
				}
			}
		});
						

		
		JButton btnEncriptar = new JButton("Encriptar");
		btnEncriptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					if(textFieldE2.getText().equals("")!=true && textFieldN2.getText().equals("")!=true){
						if(textFieldArchivo2.getText().equals("")!=true){
							Calendar iniTime=Calendar.getInstance();
							instanciaRSA=new EncrDescrArchivo(new BigInteger(textFieldE2.getText()),new BigInteger("0"),new BigInteger(textFieldN2.getText()));
							int i = textFieldArchivo2.getText().lastIndexOf(".");
							String extension = textFieldArchivo2.getText().substring(i);
							String rutaOut = textFieldArchivo2.getText().substring(0, i);//new File(textFieldArchivo2.getText()).getName().replaceFirst("[.][^.]+$", "");
							File f = new File(rutaOut+"[CIFRADO]"+extension);
							i = 0;
							String a="";
							while (f.exists()){
								i++;
								a = "("+i+")";
								f = new File(rutaOut+"[CIFRADO]"+a+extension);
							}
							instanciaRSA.encriptarArchivo(textFieldArchivo2.getText(),rutaOut+"[CIFRADO]"+a);
							Calendar finTime=Calendar.getInstance();
							textArea2.append("ENCRIPTACI\u00d3N FINALIZADA CON \u00c9XITO\n");
							textArea2.append("-Se cre\u00f3 el archivo en:\n"+rutaOut+"[CIFRADO]"+a+extension+"\n");
							textArea2.append("El proceso tom\u00f3 "+restarFechas(iniTime.getTime(), finTime.getTime())+" segundos.\n\n");
							
						}else{
							JOptionPane.showMessageDialog(frmRsa, "No carg\u00f3 archivo a encriptar.","Advertencia",JOptionPane.WARNING_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(frmRsa, "Debe ingresar clave p\u00fablica.","Advertencia",JOptionPane.WARNING_MESSAGE);
				}
			}
			
		});

		panelEncriptar.add(lblTitulo2, "cell 0 0 3 1,alignx left,aligny center");
		panelEncriptar.add(btnAyuda2, "cell 4 0,alignx right,aligny center");
		panelEncriptar.add(lblClave2, "cell 0 1 2 1,alignx left,aligny center");
		panelEncriptar.add(textFieldClaves2, "cell 2 1 2 1,growx,aligny center");				
		panelEncriptar.add(btnBuscar2, "cell 4 1,alignx right,aligny center");
		panelEncriptar.add(lblE2, "cell 0 2,alignx left,aligny center");
		panelEncriptar.add(textFieldE2, "cell 1 2 4 1,growx,aligny top");
		panelEncriptar.add(lblN2, "cell 0 3,alignx left,aligny center");
		panelEncriptar.add(textFieldN2, "cell 1 3 4 1,growx,aligny top");
		panelEncriptar.add(textFieldArchivo2, "cell 2 4 2 1,growx,aligny top");
		panelEncriptar.add(btnBuscarArchivo2, "cell 4 4,alignx right,aligny center");
		panelEncriptar.add(lblArchivo2, "cell 0 4 2 1,alignx left,aligny center");				
		panelEncriptar.add(btnEncriptar, "cell 3 5 2 1,alignx right,aligny center");
		panelEncriptar.add(scrollPane2, "cell 0 6 5 1,grow");						
		
		JPanel panelClaves = new JPanel();
		panelClaves.setBorder(new CompoundBorder());
		tabbedPane.addTab("Claves", null, panelClaves, null);
		
		JLabel lblTam1 = new JLabel("Tama\u00F1o (en bits):");
		
		JLabel lblP1 = new JLabel("p:");
		
		JLabel lblQ1 = new JLabel("q:");
		
		textFieldP1 = new JTextField();
		textFieldP1.setBackground(Color.WHITE);
		textFieldP1.setEditable(false);
		textFieldP1.setColumns(10);
		
		textFieldQ1 = new JTextField();
		textFieldQ1.setBackground(Color.WHITE);
		textFieldQ1.setEditable(false);
		textFieldQ1.setColumns(10);
		
		JComboBox comboBoxTam1 = new JComboBox();
		comboBoxTam1.setModel(new DefaultComboBoxModel(new String[] {"16", "32", "64", "128", "256", "512", "1024", "2048", "4096"}));
		comboBoxTam1.setSelectedIndex(6);
		
		JButton btnAyuda1 = new JButton("?");
		btnAyuda1.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnAyuda1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(frmRsa, 
						"Instrucciones para la generaci\u00f3n de Claves RSA:"
						+ "\n1_Seleccione un tama\u00f1o en bits para los n\u00fameros primos."
						+ "\n2_Presione el bot\u00f3n [Generar Primos] para generar dos primos p y q aleatoriamente."
						+"\n3_Presione el bot\u00f3n [Generar Claves] para generar las Claves P\u00fablica y Privada "
						+ "a \npartir de los primos generados."
						+"\n4_Si es que lo desea presione el bot\u00f3n [Establecer Claves] "
						+ "para establecer las claves\ngeneradas como sus claves por defecto.\n\n"
						+ "¡IMPORTANTE! Se recomienda cambiar sus claves por defecto periodicamente "
						+ "por\nseguridad (Por ejemplo cada 6 meses).");
			}
		});
		
		JButton btnGenerarPrimos1 = new JButton("Generar Primos");
		btnGenerarPrimos1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				instanciaRSA=new EncrDescrArchivo(new Integer(comboBoxTam1.getSelectedItem().toString()).intValue());
				textFieldP1.setText(instanciaRSA.getRsaInst().getP()+"");
				textFieldQ1.setText(instanciaRSA.getRsaInst().getQ()+"");
			}
		});
		
		JButton btnGenerarClaves1 = new JButton("Generar Claves");
		btnGenerarClaves1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textFieldP1.getText().equals("")&&textFieldQ1.getText().equals("")){
					JOptionPane.showMessageDialog(frmRsa, "Primero debe [Generar Primos].","Advertencia",JOptionPane.ERROR_MESSAGE);
				}else{
					if(textFieldN1.getText().equals("")&&textFieldE1.getText().equals("")&&textFieldD1.getText().equals("")) {
						textFieldN1.setText(instanciaRSA.getRsaInst().getN()+"");
						textFieldE1.setText(instanciaRSA.getRsaInst().getE()+"");
						textFieldD1.setText(instanciaRSA.getRsaInst().getD()+"");
					}else {
						instanciaRSA.getRsaInst().regenerarClaves();
						textFieldN1.setText(instanciaRSA.getRsaInst().getN()+"");
						textFieldE1.setText(instanciaRSA.getRsaInst().getE()+"");
						textFieldD1.setText(instanciaRSA.getRsaInst().getD()+"");
					}
				}
			}
		});
		panelClaves.setLayout(new MigLayout("", "[20][95][][55.00][60][216.00]", "[23px][25px][19px][19px][25px][19px][19px][][]"));
		
		JLabel lblTitulo1 = new JLabel("Creaci\u00f3n de Claves de cifrado.");
		
		

		
		JLabel lblE1 = new JLabel("e:");
		
		
		textFieldE1 = new JTextField();
		textFieldE1.setBackground(Color.WHITE);
		textFieldE1.setEditable(false);
		textFieldE1.setColumns(10);
		
		
		JLabel lblD1 = new JLabel("d:");
		
		
		textFieldD1 = new JTextField();
		textFieldD1.setBackground(Color.WHITE);
		textFieldD1.setEditable(false);
		textFieldD1.setColumns(10);
		
				JLabel lblN1 = new JLabel("n:");
				
				
				textFieldN1 = new JTextField();
				textFieldN1.setBackground(Color.WHITE);
				textFieldN1.setEditable(false);
				textFieldN1.setColumns(10);
				
				
				JButton btnGuardarClaves1 = new JButton("Establecer Claves");
				btnGuardarClaves1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(textFieldE1.getText().equals("")||textFieldN1.getText().equals("")||textFieldD1.getText().equals("")){
							JOptionPane.showMessageDialog(frmRsa, "Primero debe generar Claves.","Advertencia",JOptionPane.ERROR_MESSAGE);
						}else{
							if(homePathClaves.exists()==false){
								homePathClaves.mkdir();
								JOptionPane.showMessageDialog(frmRsa, "Se creo la carpeta 'Claves Generadas' en "+homePathString,"Aviso",JOptionPane.PLAIN_MESSAGE);
							}
							try{
							File f = new File(dirClaves+"PrivateKey.prk");
							if (f.exists()) {
								JOptionPane.showMessageDialog(frmRsa, "Se establecieron Nuevas Claves por defecto.","Aviso",JOptionPane.PLAIN_MESSAGE);
							}
							FileWriter ArchivoGenerado1 = new FileWriter(dirClaves+"PublicKey.puk");
							FileWriter ArchivoGenerado2 = new FileWriter(f);//new FileWriter(homePathClaves+a);
							BufferedWriter buffer1 = new BufferedWriter(ArchivoGenerado1);
							buffer1.write(textFieldE1.getText());
							buffer1.newLine();
							buffer1.write(textFieldN1.getText());
							buffer1.close();
							BufferedWriter buffer2 = new BufferedWriter(ArchivoGenerado2);
							buffer2.write(textFieldD1.getText());
							buffer2.newLine();
							buffer2.write(textFieldN1.getText());
							buffer2.close();
							ArchivoGenerado1.close();
							ArchivoGenerado2.close();
						}catch (IOException error){
							
						}
							JOptionPane.showMessageDialog(frmRsa, "Las claves fueron guardadas en "+homePathClaves,"Aviso",JOptionPane.PLAIN_MESSAGE);
						}
					}

				});
				//Primer pestaña
				panelClaves.add(lblTitulo1, "cell 0 0 5 1,alignx left,aligny center");
				panelClaves.add(btnAyuda1, "cell 5 0,alignx right,aligny center");
				panelClaves.add(comboBoxTam1, "cell 3 1,alignx left,aligny top");
				panelClaves.add(lblP1, "cell 0 2,alignx left,aligny center");
				panelClaves.add(textFieldP1, "cell 1 2 5 1,growx,aligny top");
				panelClaves.add(lblQ1, "cell 0 3,alignx left,aligny center");
				panelClaves.add(textFieldQ1, "cell 1 3 5 1,growx,aligny top");
				panelClaves.add(lblE1, "cell 0 5,alignx left,aligny center");
				panelClaves.add(textFieldE1, "cell 1 5 5 1,growx,aligny top");
				panelClaves.add(lblD1, "cell 0 6,alignx left,aligny center");
				panelClaves.add(textFieldD1, "cell 1 6 5 1,growx,aligny top");
				panelClaves.add(lblTam1, "cell 0 1 2 1,alignx left,aligny center");
				panelClaves.add(btnGenerarPrimos1, "cell 5 1,alignx right,aligny center");
				panelClaves.add(btnGenerarClaves1, "cell 5 4,alignx right,aligny center");
				panelClaves.add(lblN1, "cell 0 7,alignx left,aligny center");
				panelClaves.add(textFieldN1, "cell 1 7 5 1,growx,aligny top");
				panelClaves.add(btnGuardarClaves1, "cell 5 8,alignx right,aligny center");
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Acerca de ...", null, panel, null);
		panel.setLayout(null);

		//fin layout tercer panel
		
		tabbedPane.setTabComponentAt(0, getLabel("Desencriptar","/rsa_tabs_images/rsa_1.png"));
		tabbedPane.setTabComponentAt(1, getLabel("Encriptar","/rsa_tabs_images/rsa_0.png"));
		tabbedPane.setTabComponentAt(2, getLabel("Claves","/rsa_tabs_images/rsa_4.png"));
		tabbedPane.setTabComponentAt(3, getLabel("Acerca de","/rsa_tabs_images/rsa_5.png"));
		frmRsa.setIconImage(image);
		JLabel label=getLabel("","/rsa_tabs_images/Logo.png");
		label.setBounds(380, 100, 100, 140);
		panel.add(label);
		
		JTextArea textArea4 = new JTextArea();
		textArea4.setBounds(12, 12, 481, 229);
		textArea4.setBackground(UIManager.getColor("Button.background"));
		textArea4.setFont(new Font("Dialog", Font.BOLD, 14));
		textArea4.setEditable(false);
		textArea4.setWrapStyleWord(true);
		textArea4.setText("Este programa fue desarrollado como trabajo pr\u00e1ctico final "
				+ "de la c\u00e1tedra Algoritmos y Estructuras de Datos, cuyo tema consist\u00eda "
				+ "en crear una aplicaci\u00f3n visual que cifre y descifre archivos utilizando "
				+ "el algoritmo RSA y que permita abrir y guardar dichos archivos.\n\n"
				+ " Autores:\t\n\tBriega, C\u00e9sar Osvaldo\n\tCorrea, Antonio Rolando\n\tMar\u00edn, Fernando Edgardo"
				+ " \n\n17 de Julio de 2018");
		textArea4.setLineWrap(true);
		panel.add(textArea4);
		
	}
    private JLabel getLabel(String title, String icon) {
        JLabel label = new JLabel(title);
        try {
            label.setIcon(new ImageIcon(ImageIO.read(getClass().getResource(icon))));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return label;
    }
}
