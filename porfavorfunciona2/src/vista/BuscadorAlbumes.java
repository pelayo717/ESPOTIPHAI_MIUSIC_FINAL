package vista;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import modelo.contenido.Album;

public class BuscadorAlbumes extends PantallaPrincipal{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DefaultListModel<String> model1;

	private  Album[] losAlbumes;

	private  JList<String> lista_albumes;

	private  JScrollPane albumes;

	private JLabel albumesEncontrados;
	
	public JButton seleccionarAlbum;
	
	
	public BuscadorAlbumes() {
		
		super();
		
		model1 = new DefaultListModel<>();
		
		
		lista_albumes = new JList<String>(model1);
		
		
		albumes = new JScrollPane(lista_albumes);
		
		
		albumesEncontrados = new JLabel("Albumes Encontrados",  SwingConstants.CENTER);
		
		
		seleccionarAlbum = new JButton("Elegir album");
		
		
		
		//Cambio de estilo en los JLabel
		Font susAlbumesFont = new Font(albumesEncontrados.getFont().getName(), Font.BOLD, 16);
		
		albumes.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		this.albumesEncontrados.setFont(susAlbumesFont);
	
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();				
		
		albumesEncontrados.setBounds(screenSize.width/2 - 115, 200, 290, 30);
		
		albumes.setBounds(screenSize.width/2 - 550, 250, 1100, 300);
		

		seleccionarAlbum.setBounds(screenSize.width/2 - 115, 575, 250, 30);
		

		
		//Anyadimos los elementos a la pantalla principal
		this.add(albumes);
		this.add(albumesEncontrados);
		this.add(seleccionarAlbum);
	}
	
		
	public void setControlador(ActionListener c) {
		this.getBotonIzquierdaArriba().addActionListener(c);
		this.getBotonIzquierdaMedio().addActionListener(c);
		this.seleccionarAlbum.addActionListener(c);
		this.getBotonIzquierdaAbajo().addActionListener(c);
		this.getBotonBuscar().addActionListener(c);
		this.getBotonLimpiarBuscador().addActionListener(c);
	}
	
	public void setUsuarioRegistrado() {
		this.getBotonIzquierdaArriba().setText("Ver Perfil");
		this.getBotonIzquierdaMedio().setText("Inicio");
		this.getBotonIzquierdaAbajo().setVisible(false);
	}
			
	public void setUsuarioNoRegistrado() {
		this.getBotonIzquierdaArriba().setText("Iniciar Sesion");
		this.getBotonIzquierdaMedio().setText("Registro");
		this.getBotonIzquierdaAbajo().setVisible(true);
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public DefaultListModel<String> getModel1() {
		return model1;
	}


	public Album[] getLosAlbumes() {
		return losAlbumes;
	}


	public JList<String> getLista_albumes() {
		return lista_albumes;
	}


	public JScrollPane getAlbumes() {
		return albumes;
	}


	public JLabel getAlbumesEncontrados() {
		return albumesEncontrados;
	}


	public JButton getSeleccionarAlbum() {
		return seleccionarAlbum;
	}


	public void actualizarAlbumes(Album[] albumes_propios) {
		model1.clear();
		losAlbumes = albumes_propios;
		
		for(int i=0; i < losAlbumes.length; i++) {
			int horas = (int) (losAlbumes[i].getDuracion() / 3600);
		    int minutos = (int) ((losAlbumes[i].getDuracion()-horas*3600)/60);
		    int segundos = (int) (losAlbumes[i].getDuracion()-(horas*3600+minutos*60));
		    
			model1.addElement("Titulo: " + losAlbumes[i].getTitulo() + " // Duracion HH-MM-SS: " + horas + "-" + minutos + "-" + segundos + " // Autor: " + losAlbumes[i].getAutor().getNombreAutor());
		}
	}
	
	public void limpiarDatos() {
		model1.clear();
	}


	public Album[] getAlbum() {
		return this.losAlbumes;
	}


	public JList<String> getListAlbum() {
		return this.lista_albumes;
	}

}
