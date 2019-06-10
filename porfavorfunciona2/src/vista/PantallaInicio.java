package vista;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import modelo.contenido.Album;
import modelo.contenido.Cancion;
import modelo.contenido.Lista;
import modelo.sistema.Sistema;

public class PantallaInicio extends PantallaPrincipal {

	private JScrollPane canciones;
	private JScrollPane albumes;
	private JScrollPane listas;
	public JList lista_canciones;
	public JList lista_albumes;
	public JList lista_listas;
	JButton seleccionarAlbum;
	JButton seleccionarCancion;
	JButton seleccionarLista;
	JButton crearCancion;
	JButton crearAlbum;
	JButton crearLista;
	
	public ArrayList<Cancion> misCanciones;
	public ArrayList<Album> misAlbumes;
	public ArrayList<Lista> misListas;

	public PantallaInicio() {  //CAMBIADO, MEJORADO
		
		super();
		
		canciones = new JScrollPane(lista_canciones);
		albumes = new JScrollPane(lista_albumes);
		listas = new JScrollPane(lista_listas);
		
		JLabel susCanciones = new JLabel("Sus canciones",  SwingConstants.CENTER);
		JLabel susAlbumes = new JLabel("Sus albumes",  SwingConstants.CENTER);	
		JLabel susListas = new JLabel("Sus listas", SwingConstants.CENTER);
		
		seleccionarAlbum = new JButton("Elegir album");
		seleccionarCancion = new JButton("Elegir cancion");
		seleccionarLista = new JButton("Elegir lista");
		
		crearCancion = new JButton("Crear cancion");
		crearAlbum = new JButton("Crear album");
		crearLista = new JButton("Crear lista");
		
		//Cambio de estilo en los JLabel
		Font susCancionesFont = new Font(susCanciones.getFont().getName(), Font.BOLD, 16);
		Font susAlbumesFont = new Font(susAlbumes.getFont().getName(), Font.BOLD, 16);
		Font susListasFont = new Font(susListas.getFont().getName(), Font.BOLD, 16);
		canciones.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		albumes.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		listas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//Distribucion en la pantalla
		//Manual Constraints
		//x axis, y axis, width, height 
				
		
		susCanciones.setBounds(screenSize.width/2 - 550, 200, 290, 30);
		susAlbumes.setBounds(screenSize.width/2 - 190, 200, 290, 30);
		susListas.setBounds(screenSize.width/2 + 160, 200, 290, 30);
		canciones.setBounds(screenSize.width/2 - 550, 250, 290, 200);
		albumes.setBounds(screenSize.width/2 - 190, 250, 290, 200);
		listas.setBounds(screenSize.width/2 + 170, 250, 290, 200);

		seleccionarCancion.setBounds(screenSize.width/2 - 530, 470, 250, 30);
		seleccionarAlbum.setBounds(screenSize.width/2 - 170, 470, 250, 30);
		seleccionarLista.setBounds(screenSize.width/2 + 190, 470, 250, 30);

		crearCancion.setBounds(screenSize.width/2 - 530, 500, 250, 30);
		crearAlbum.setBounds(screenSize.width/2 - 170, 500, 250, 30);
		crearLista.setBounds(screenSize.width/2 + 190, 500, 250, 30);
		
		
		//Anyadimos los elementos a la pantalla principal
		this.add(albumes);
		this.add(canciones);
		this.add(listas);
		this.add(susCanciones);
		this.add(susAlbumes);
		this.add(susListas);
		this.add(seleccionarAlbum);
		this.add(seleccionarCancion);
		this.add(seleccionarLista);
		this.add(crearAlbum);
		this.add(crearCancion);
		this.add(crearLista);
	}
		
	
	// metodo para asignar un controlador al boton
	public void setControlador(ActionListener c) {
		this.botonIzquierdaArriba.addActionListener(c);
		this.botonIzquierdaMedio.addActionListener(c);
		this.seleccionarCancion.addActionListener(c);
		this.seleccionarAlbum.addActionListener(c);
		this.seleccionarLista.addActionListener(c);
		this.botonBuscar.addActionListener(c);
		this.botonLimpiarBuscador.addActionListener(c);
		this.crearAlbum.addActionListener(c);
		this.crearCancion.addActionListener(c);
		this.crearLista.addActionListener(c);
	}
		 
	public void setUsuarioRegistrado() {
		this.botonIzquierdaArriba.setText("Ver Perfil");
		this.botonIzquierdaMedio.setVisible(false);
		this.botonIzquierdaAbajo.setVisible(false);
	}
			
	public void setUsuarioNoRegistrado() {
		this.botonIzquierdaArriba.setText("Iniciar Sesion");
		this.botonIzquierdaMedio.setVisible(true);
		this.botonIzquierdaAbajo.setVisible(false);
	}
	
	public void actualizarCanciones(ArrayList<Cancion> canciones_propias) {
		lista_canciones = new JList(canciones_propias.toArray());
		canciones = new JScrollPane(lista_canciones);
	}
	
	public void actualizarAlbumes(ArrayList<Album> albumes_propios) {
		lista_albumes = new JList(albumes_propios.toArray());
		albumes = new JScrollPane(lista_albumes);
	}
	
	public void actualizarListas(ArrayList<Lista> listas_propias) {
		lista_listas = new JList(listas_propias.toArray());
		listas = new JScrollPane(lista_listas);
	}
			
}  
