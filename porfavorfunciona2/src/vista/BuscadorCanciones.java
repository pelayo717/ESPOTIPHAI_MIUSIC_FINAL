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

	private  DefaultListModel<String> model1;

	private  Cancion[] lasCanciones;

	private  JList<String> lista_canciones;

	private JScrollPane canciones;

	private JLabel cancionesEncontradas;
	
	public JButton seleccionarCancion;
	
	public BuscadorCanciones() {
		
		super();
		
		model1 = new DefaultListModel<>();
		
		
		lista_canciones = new JList<String>(model1);
		
		
		canciones = new JScrollPane(lista_canciones);
		
		
		cancionesEncontradas = new JLabel("Canciones Encontradas",  SwingConstants.CENTER);
		
		
		seleccionarCancion = new JButton("Elegir Cancion");
		
		
		
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
		super.getBotonIzquierdaArriba().addActionListener(c);
		super.getBotonIzquierdaMedio().addActionListener(c);
		this.seleccionarCancion.addActionListener(c);
		super.getBotonIzquierdaAbajo().addActionListener(c);
		super.getBotonBuscar().addActionListener(c);
		super.getBotonLimpiarBuscador().addActionListener(c);
	}
	
	public void setUsuarioRegistrado() {
		super.getBotonIzquierdaArriba().setText("Ver Perfil");
		super.getBotonIzquierdaMedio().setText("Inicio");
		super.getBotonIzquierdaAbajo().setVisible(false);
	}
			
	public void setUsuarioNoRegistrado() {
		super.getBotonIzquierdaArriba().setText("Iniciar Sesion");
		super.getBotonIzquierdaMedio().setText("Registro");
		super.getBotonIzquierdaAbajo().setVisible(true);
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public DefaultListModel<String> getModel1() {
		return model1;
	}

	public Cancion[] getLasCanciones() {
		return lasCanciones;
	}

	public JList<String> getLista_canciones() {
		return lista_canciones;
	}

	public JScrollPane getCanciones() {
		return canciones;
	}

	public JLabel getCancionesEncontradas() {
		return cancionesEncontradas;
	}

	public JButton getSeleccionarCancion() {
		return seleccionarCancion;
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
