package ESPOTIPHAI_MIUSIC_FINAL.Grafic;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.*;

import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.Sistema;
import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.contenido.*;

public class PantallaInicio extends PantallaPrincipal {

	private JScrollPane canciones;
	private JScrollPane albumes;
	DefaultListModel<Cancion>  cancionesModel;
	JList<Cancion> lista_canciones;
	JList<Album> lista_albumes;
	JButton seleccionarAlbum;
	JButton seleccionarCancion;
	
	public PantallaInicio() {
		
		JLabel susCanciones = new JLabel("Sus canciones",  SwingConstants.CENTER);
		JLabel susAlbumes = new JLabel("Sus albumes",  SwingConstants.CENTER);		
		seleccionarAlbum = new JButton("Elegir album");
		seleccionarCancion = new JButton("Elegir cancion");
		if(Sistema.sistema.getUsuarioActual() != null) {
			lista_canciones  = new JList<Cancion> (Sistema.sistema.getUsuarioActual().getCanciones().toArray(new Cancion[Sistema.sistema.getUsuarioActual().getCanciones().size()]));
		}
		
		cancionesModel = new DefaultListModel<Cancion> ();
		lista_canciones = new JList<Cancion> (cancionesModel);
		
		canciones = new JScrollPane(lista_canciones);
		albumes = new JScrollPane(lista_albumes);
		
		//Cambio de estilo en los JLabel
		Font susCancionesFont = new Font(susCanciones.getFont().getName(), Font.BOLD, 16);
		Font susAlbumesFont = new Font(susAlbumes.getFont().getName(), Font.BOLD, 16);
		
		
		susCanciones.setFont(susCancionesFont);
		susAlbumes.setFont(susAlbumesFont);
		
		
		canciones.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		albumes.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//Distribucion en la pantalla
		//Manual Constraints
		//x axis, y axis, width, height 
				
		
		susCanciones.setBounds(screenSize.width/2 - 300, 200, 290, 30);
		susAlbumes.setBounds(screenSize.width/2 +10, 200, 290, 30);
		canciones.setBounds(screenSize.width/2 - 300, 250, 290, 200);
		albumes.setBounds(screenSize.width/2 + 10, 250, 290, 200);
	
		seleccionarCancion.setBounds(screenSize.width/2 - 280, 470, 250, 30);
		seleccionarAlbum.setBounds(screenSize.width/2 + 30, 470, 250, 30);
		
		
		//A�adimos los elementos a la pantalla principal
		this.add(albumes);
		this.add(canciones);
		this.add(susCanciones);
		this.add(susAlbumes);
		this.add(seleccionarAlbum);
		this.add(seleccionarCancion);
	}
		
	
	// m�todo para asignar un controlador al bot�n
	public void setControlador(ActionListener c) {
		this.botonIzquierdaArriba.addActionListener(c);
		this.botonIzquierdaMedio.addActionListener(c);
		this.seleccionarCancion.addActionListener(c);
		this.seleccionarAlbum.addActionListener(c);
	}
		 
	public void setUsuarioRegistrado() {
		cancionesModel.removeAllElements();
		if(Sistema.sistema.getUsuarioActual() != null) {
			for(Cancion cancion : Sistema.sistema.getUsuarioActual().getCanciones()) {
				cancionesModel.addElement(cancion);
			}
		}
		this.botonIzquierdaArriba.setText("Ver Perfil");
		this.botonIzquierdaMedio.setVisible(false);
		this.botonIzquierdaAbajo.setVisible(false);
	}
			
	public void setUsuarioNoRegistrado() {
		this.botonIzquierdaArriba.setText("Iniciar Sesion");
		this.botonIzquierdaMedio.setVisible(true);
		this.botonIzquierdaAbajo.setVisible(false);
	}
			
}  
