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

import modelo.contenido.Cancion;

public class BuscadorCanciones extends PantallaPrincipal{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DefaultListModel<String> model1;

	public Cancion[] lasCanciones;

	public JList<String> lista_canciones;

	private JScrollPane canciones;

	JLabel cancionesEncontradas;
	
	JButton seleccionarCancion;
	
	public BuscadorCanciones() {
		
		super();
		
		model1 = new DefaultListModel<>();
		
		
		lista_canciones = new JList<String>(model1);
		
		
		canciones = new JScrollPane(lista_canciones);
		
		
		cancionesEncontradas = new JLabel("Canciones Encontradas",  SwingConstants.CENTER);
		
		
		seleccionarCancion = new JButton("Elegir cancion");
		
		
		
		//Cambio de estilo en los JLabel
		Font susCancionesFont = new Font(cancionesEncontradas.getFont().getName(), Font.BOLD, 16);
		
		canciones.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		this.cancionesEncontradas.setFont(susCancionesFont);
	
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();				
		
		cancionesEncontradas.setBounds(screenSize.width/2 - 115, 200, 290, 30);
		
		canciones.setBounds(screenSize.width/2 - 550, 250, 1100, 300);
		

		seleccionarCancion.setBounds(screenSize.width/2 - 115, 575, 250, 30);
		

		
		//Anyadimos los elementos a la pantalla principal
		this.add(canciones);
		this.add(cancionesEncontradas);
		this.add(seleccionarCancion);
		
	}

	public void setControlador(ActionListener c) {
		this.botonIzquierdaArriba.addActionListener(c);
		this.botonIzquierdaMedio.addActionListener(c);
		this.seleccionarCancion.addActionListener(c);
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
	
	public void actualizarCanciones(Cancion[] canciones_propias) {
		model1.clear();
		lasCanciones = canciones_propias;
		for(int i=0; i < lasCanciones.length; i++) {
			int horas = (int) (lasCanciones[i].getDuracion() / 3600);
		    int minutos = (int) ((lasCanciones[i].getDuracion()-horas*3600)/60);
		    int segundos = (int) (lasCanciones[i].getDuracion()-(horas*3600+minutos*60));
			model1.addElement("Titulo: " + lasCanciones[i].getTitulo() + " // Duracion HH-MM-SS: " + horas + "-" + minutos + "-" + segundos + " // Autor: " + lasCanciones[i].getAutor().getNombreAutor());
		}
	}
	
	public void limpiarDatos() {
		model1.clear();
	}
	
}
