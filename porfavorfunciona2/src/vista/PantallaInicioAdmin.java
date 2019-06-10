package vista;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import modelo.contenido.Cancion;
import modelo.reporte.Reporte;
import modelo.sistema.Sistema;

public class PantallaInicioAdmin extends PantallaPrincipal{

	private JScrollPane reportes;
	private JScrollPane canciones;
	
	private JList lista_reportes;
	private JList lista_canciones;
	
	JButton modificarCriterios;
	
	JButton valida;
	JButton explicita;
	JButton pendienteModificacion;
	JButton eliminada;
	
	JButton aceptarReporte;
	JButton denegarReporte;
	
	JButton cambiarCriterios;
	JTextField campoUmbral;
	JTextField campoPrecio;
	JTextField campoReproducciones;
	
	JLabel susCanciones;
	JLabel susReportes;
	JLabel precio;
	JLabel umbral;
	JLabel reproducciones;
	ArrayList<Cancion> para_validar;
	ArrayList<Reporte> para_reportar;
	
	public PantallaInicioAdmin () {
		
		super();
		
		botonIzquierdaArriba.setText("Ver Perfil");
		botonIzquierdaMedio.setVisible(false);
		botonIzquierdaAbajo.setVisible(false);
		
		
		
		
		susCanciones = new JLabel("Canciones a validar",  SwingConstants.CENTER);
		susReportes = new JLabel("Reportes a revisar",  SwingConstants.CENTER);	
		
		precio = new JLabel("Precio Premium",  SwingConstants.CENTER);
		umbral = new JLabel("Umbral Reproducciones",  SwingConstants.CENTER);
		reproducciones = new JLabel("Reproducciones Maximas",  SwingConstants.CENTER);
		
		valida = new JButton("Valida");
		explicita = new JButton("Explicita");
		pendienteModificacion = new JButton("Pendiente Modificacion");
		eliminada = new JButton("Eliminada");
		aceptarReporte = new JButton("Aceptar");
		denegarReporte = new JButton("Denegar");
		cambiarCriterios = new JButton("Cambiar Criterios");

		campoUmbral = new JTextField(20);
		campoPrecio = new JTextField(20);
		campoReproducciones = new JTextField(20);
		
		//Cambio de estilo en los JLabel
		Font susCancionesFont = new Font(susCanciones.getFont().getName(), Font.BOLD, 16);
		Font susReportesFont = new Font(susReportes.getFont().getName(), Font.BOLD, 16);
		Font precioFont = new Font(precio.getFont().getName(), Font.BOLD, 16);
		Font umbralFont = new Font(umbral.getFont().getName(), Font.BOLD, 16);
		Font reproduccionesFont = new Font(reproducciones.getFont().getName(), Font.BOLD, 16);
		
		canciones = new JScrollPane(lista_canciones);
		reportes = new JScrollPane(lista_reportes);
		
		canciones.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		reportes.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//Distribucion en la pantalla
		//Manual Constraints
		//x axis, y axis, width, height 
				
		
		susCanciones.setBounds(screenSize.width/2 - 600, 200, 290, 30);
		canciones.setBounds(screenSize.width/2 - 600, 250, 290, 200);

		valida.setBounds(screenSize.width/2 - 300, 260, 100, 30);
		explicita.setBounds(screenSize.width/2 - 300, 310, 100, 30);
		pendienteModificacion.setBounds(screenSize.width/2 - 300, 360, 100, 30);
		eliminada.setBounds(screenSize.width/2 - 300, 410, 100, 30);
		
		susReportes.setBounds(screenSize.width/2 - 100, 200, 290, 30);
		reportes.setBounds(screenSize.width/2 - 100, 250, 290, 200);

		aceptarReporte.setBounds(screenSize.width/2 + 200, 300, 100, 30);
		denegarReporte.setBounds(screenSize.width/2 + 200, 350, 100, 30);
		
		precio.setBounds(screenSize.width/2 + 350, 200, 200, 30);
		umbral.setBounds(screenSize.width/2 + 350, 275, 200, 30);
		reproducciones.setBounds(screenSize.width/2 + 350, 350, 200, 30);
		
		campoPrecio.setBounds(screenSize.width/2 + 350, 225, 200, 30);
		campoUmbral.setBounds(screenSize.width/2 + 350, 300, 200, 30);
		campoReproducciones.setBounds(screenSize.width/2 + 350, 375, 200, 30);
		
		cambiarCriterios.setBounds(screenSize.width/2 + 375, 430, 150, 30);
		
		
		
		//Anyadimos los elementos a la pantalla principal
		this.add(canciones);
		this.add(susCanciones);
		this.add(valida);
		this.add(explicita);
		this.add(pendienteModificacion);
		this.add(eliminada);
		this.add(susReportes);
		this.add(aceptarReporte);
		this.add(denegarReporte);
		this.add(reportes);
		this.add(campoPrecio);
		this.add(campoUmbral);
		this.add(campoReproducciones);
		this.add(cambiarCriterios);
		this.add(umbral);
		this.add(precio);
		this.add(reproducciones);
	}
	
	// metodo para asignar un controlador al boton
		public void setControlador(ActionListener c) {
			this.botonIzquierdaArriba.addActionListener(c);
			this.botonIzquierdaMedio.addActionListener(c);
			this.valida.addActionListener(c);
			this.explicita.addActionListener(c);
			this.pendienteModificacion.addActionListener(c);
			this.botonBuscar.addActionListener(c);
			this.botonLimpiarBuscador.addActionListener(c);
			this.eliminada.addActionListener(c);
			
		}
		
		@SuppressWarnings("unchecked")
		public void actualizarCanciones() {
			para_validar = Sistema.sistema.getCancionesPendientesValidacion();
			lista_canciones = new JList(para_validar.toArray());
			canciones = new JScrollPane(lista_canciones);
		}
		
		@SuppressWarnings("unchecked")
		public void actualizarReportes() {
			para_reportar = Sistema.sistema.getReportesTotales();
			lista_reportes = new JList(para_reportar.toArray());
			reportes = new JScrollPane(lista_reportes);
		}
	
}
