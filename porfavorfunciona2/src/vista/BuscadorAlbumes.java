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

	public Album[] losAlbumes;

	public JList<String> lista_albumes;

	private JScrollPane albumes;

	JLabel albumesEncontrados;
	
	JButton seleccionarAlbum;
	
	
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
		this.botonIzquierdaArriba.addActionListener(c);
		this.botonIzquierdaMedio.addActionListener(c);
		this.seleccionarAlbum.addActionListener(c);
		this.botonIzquierdaAbajo.addActionListener(c);
		this.botonBuscar.addActionListener(c);
		this.botonLimpiarBuscador.addActionListener(c);
	}
	
	public void setUsuarioRegistrado() {
		this.botonIzquierdaArriba.setText("Ver Perfil");
		this.botonIzquierdaMedio.setText("Inicio");
		this.botonIzquierdaAbajo.setVisible(false);
	}
			
	public void setUsuarioNoRegistrado() {
		this.botonIzquierdaArriba.setText("Iniciar Sesion");
		this.botonIzquierdaMedio.setText("Registro");
		this.botonIzquierdaAbajo.setVisible(true);
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
