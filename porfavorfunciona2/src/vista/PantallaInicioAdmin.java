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
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import modelo.contenido.Cancion;
import modelo.reporte.Reporte;
import modelo.sistema.Sistema;

public class PantallaInicioAdmin extends PantallaPrincipal{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JScrollPane reportes;
	private JScrollPane canciones;
	
	public JList<String> lista_reportes;
	public JList<String> lista_canciones;
	
	JButton modificarCriterios;
	
	JButton valida;
	JButton explicita;
	JButton pendienteModificacion;
	JButton eliminada;
	JButton seleccionarCancion;
	
	JButton aceptarReporte;
	JButton denegarReporte;
	JButton seleccionarReporte;
	
	JButton cambiarCriterios;
	JTextField campoUmbral;
	JTextField campoPrecio;
	JTextField campoReproducciones;
	
	JLabel susCanciones;
	JLabel susReportes;
	JLabel precio;
	JLabel umbral;
	JLabel reproducciones;
	
	public Cancion[] aValidar;
	public Reporte[] aReportar;
	
	public DefaultListModel<String> model1;
	public DefaultListModel<String> model2;
	
	public PantallaInicioAdmin () {
		
		super();
		
		model1 = new DefaultListModel<>();
		model2 = new DefaultListModel<>();
		
		lista_reportes = new JList<String>(model1);
		lista_canciones = new JList<String>(model2);
		
		canciones = new JScrollPane(lista_canciones);
		reportes = new JScrollPane(lista_reportes);
		
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
		pendienteModificacion = new JButton("Pendiente");
		eliminada = new JButton("Eliminada");
		seleccionarCancion = new JButton("Seleccionar Cancion");
		aceptarReporte = new JButton("Aceptar");
		denegarReporte = new JButton("Denegar");
		seleccionarReporte = new JButton("Seleccionar Reporte");
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
		
		this.susCanciones.setFont(susCancionesFont);
		this.susReportes.setFont(susReportesFont);
		this.umbral.setFont(umbralFont);
		this.precio.setFont(precioFont);
		this.reproducciones.setFont(reproduccionesFont);
		
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
		seleccionarCancion.setBounds(screenSize.width/2 - 525, 470, 150, 30);
		
		susReportes.setBounds(screenSize.width/2 - 100, 200, 290, 30);
		reportes.setBounds(screenSize.width/2 - 100, 250, 290, 200);

		aceptarReporte.setBounds(screenSize.width/2 + 200, 300, 100, 30);
		denegarReporte.setBounds(screenSize.width/2 + 200, 350, 100, 30);
		seleccionarReporte.setBounds(screenSize.width/2 -25 , 470, 150, 30);
		
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
		this.add(seleccionarCancion);
		this.add(seleccionarReporte);
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
			this.seleccionarCancion.addActionListener(c);
			this.aceptarReporte.addActionListener(c);
			this.denegarReporte.addActionListener(c);
			this.seleccionarReporte.addActionListener(c);
			
		}
		
		public void actualizarCanciones() {
			model2.clear();
			aValidar = Sistema.sistema.getCancionesPendientesValidacion().toArray(new Cancion[Sistema.sistema.getCancionesPendientesValidacion().size()]);
			for(int i=0;i< aValidar.length; i++) {
				model2.addElement(aValidar[i].getTitulo() + " // " + aValidar[i].getAutor().getNombreAutor());
			}
		}
		
		public void actualizarReportes() {
			model1.clear();
			aReportar = Sistema.sistema.getReportesTotales().toArray(new Reporte[Sistema.sistema.getReportesTotales().size()]);
			for(int i=0; i < aReportar.length; i++) {
				model1.addElement(aReportar[i].getCancionReportada().getTitulo() + " // " + aReportar[i].getUsuarioReportador().getNombreUsuario());
			}
		}
	
}
