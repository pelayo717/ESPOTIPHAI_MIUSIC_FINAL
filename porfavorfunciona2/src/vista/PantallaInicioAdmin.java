package vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import modelo.contenido.Cancion;
import modelo.contenido.EstadoCancion;
import modelo.reporte.Reporte;
import modelo.sistema.Sistema;

public class PantallaInicioAdmin extends PantallaPrincipal{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JScrollPane reportes;
	private JScrollPane canciones;
	
	private  JList<String> lista_reportes;
	private  JList<String> lista_canciones;
	
	private JButton modificarCriterios;
	
	private JButton valida;
	private JButton explicita;
	private JButton pendienteModificacion;
	private JButton eliminada;
	private JButton seleccionarCancion;
	
	private JButton aceptarReporte;
	private JButton denegarReporte;
	private JButton seleccionarReporte;
	
	private JButton cambiarCriterios;
	private JTextField campoUmbral;
	private JTextField campoPrecio;
	private JTextField campoReproducciones;
	
	private JLabel susCanciones;
	private JLabel susReportes;
	private JLabel precio;
	private JLabel umbral;
	private JLabel reproducciones;
	
	private  Cancion[] aValidar;
	private  Reporte[] aReportar;
	
	private  DefaultListModel<String> model1;
	private  DefaultListModel<String> model2;
	
	public PantallaInicioAdmin () {
		
		super();
		
		model1 = new DefaultListModel<>();
		model2 = new DefaultListModel<>();
		
		lista_reportes = new JList<String>(model1);
		lista_canciones = new JList<String>(model2);
		
		lista_canciones.setCellRenderer(new RowColor());
		
		canciones = new JScrollPane(lista_canciones);
		reportes = new JScrollPane(lista_reportes);
		
		botonIzquierdaArriba.setText("Ver Perfil");
		botonIzquierdaMedio.setVisible(false);
		botonIzquierdaAbajo.setVisible(false);
		
		susCanciones = new JLabel("Canciones a validar",  SwingConstants.CENTER);
		susReportes = new JLabel("Reportes a revisar",  SwingConstants.CENTER);	
		
		precio = new JLabel("Precio PRO => " + Sistema.sistema.getPrecioPremium() + " €" ,  SwingConstants.CENTER);
		umbral = new JLabel("Umbral Reproducciones => " + Sistema.sistema.getUmbralReproducciones(),  SwingConstants.CENTER);
		reproducciones = new JLabel("Reproducciones Maximas => " + Sistema.sistema.getMaxReproduccionesUsuariosNoPremium() ,  SwingConstants.CENTER);
		
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
		umbral.setBounds(screenSize.width/2 + 300, 275, 300, 30);
		reproducciones.setBounds(screenSize.width/2 + 300, 350, 300, 30);
		
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
			this.cambiarCriterios.addActionListener(c);
			
		}
		
		public void actualizarCanciones() {
			model2.clear();
			aValidar = Sistema.sistema.getCancionesPendientesValidacion().toArray(new Cancion[Sistema.sistema.getCancionesPendientesValidacion().size()]);
			for(int i=0;i< aValidar.length; i++) {
				int horas = (int) (aValidar[i].getDuracion() / 3600);
			    int minutos = (int) ((aValidar[i].getDuracion()-horas*3600)/60);
			    int segundos = (int) (aValidar[i].getDuracion()-(horas*3600+minutos*60));
				if(aValidar[i].getEstado() == EstadoCancion.PENDIENTEMODIFICACION) {
					LocalDate a = aValidar[i].getFechaModificacion();
					a = a.plusDays(3);
					model2.addElement("Titulo: " + aValidar[i].getTitulo() + " // Autor: " + aValidar[i].getAutor().getNombreAutor() + " // Fecha FIN Modificacion: " + a.toString() + " // Duracion HH/MM/SS: " + horas + "/" + minutos + "/" + segundos);
				}else {
					model2.addElement("Titulo: " + aValidar[i].getTitulo() + " // Autor: " + aValidar[i].getAutor().getNombreAutor() + " // Duracion HH-MM-SS: " + horas + "-" + minutos + "-" + segundos);
				}
			}
		}
		
		public void actualizarReportes() {
			model1.clear();
			aReportar = Sistema.sistema.getReportesTotales().toArray(new Reporte[Sistema.sistema.getReportesTotales().size()]);
			for(int i=0; i < aReportar.length; i++) {
				model1.addElement("Cancion: " + aReportar[i].getCancionReportada().getTitulo() + " // Reportador: " + aReportar[i].getUsuarioReportador().getNombreUsuario());
			}
		}
		
		
		public void actualizarCriterios() {	
			precio.setText("Precio PRO => " + Sistema.sistema.getPrecioPremium() + " €");
			umbral.setText("Umbral Reproducciones => " + Sistema.sistema.getUmbralReproducciones());
			reproducciones.setText("Reproducciones Maximas => " + Sistema.sistema.getMaxReproduccionesUsuariosNoPremium());			
		}
		
		private class RowColor extends DefaultListCellRenderer{
			
			private static final long serialVersionUID = 1L;

			public Component getListCellRendererComponent( JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus ) {
	            Component c = super.getListCellRendererComponent( list, value, index, isSelected, cellHasFocus );
	            if(aValidar[index].getEstado() == EstadoCancion.PENDIENTEAPROBACION){ //LAS PENDIENTES DE MODIFICACION
	                c.setBackground( Color.white );
	                c.setForeground( Color.black );
	            }else if(aValidar[index].getEstado() == EstadoCancion.PENDIENTEMODIFICACION) {
	                c.setBackground( Color.yellow );
	                c.setForeground( Color.black );
	            }
	            return c;
	        }
			
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		public JScrollPane getReportes() {
			return reportes;
		}

		public JScrollPane getCanciones() {
			return canciones;
		}

		public JList<String> getLista_reportes() {
			return lista_reportes;
		}

		public JList<String> getLista_canciones() {
			return lista_canciones;
		}

		public JButton getModificarCriterios() {
			return modificarCriterios;
		}

		public JButton getValida() {
			return valida;
		}

		public JButton getExplicita() {
			return explicita;
		}

		public JButton getPendienteModificacion() {
			return pendienteModificacion;
		}

		public JButton getEliminada() {
			return eliminada;
		}

		public JButton getSeleccionarCancion() {
			return seleccionarCancion;
		}

		public JButton getAceptarReporte() {
			return aceptarReporte;
		}

		public JButton getDenegarReporte() {
			return denegarReporte;
		}

		public JButton getSeleccionarReporte() {
			return seleccionarReporte;
		}

		public JButton getCambiarCriterios() {
			return cambiarCriterios;
		}

		public JTextField getCampoUmbral() {
			return campoUmbral;
		}

		public JTextField getCampoPrecio() {
			return campoPrecio;
		}

		public JTextField getCampoReproducciones() {
			return campoReproducciones;
		}

		public JLabel getSusCanciones() {
			return susCanciones;
		}

		public JLabel getSusReportes() {
			return susReportes;
		}

		public JLabel getPrecio() {
			return precio;
		}

		public JLabel getUmbral() {
			return umbral;
		}

		public JLabel getReproducciones() {
			return reproducciones;
		}

		public Cancion[] getaValidar() {
			return aValidar;
		}

		public Reporte[] getaReportar() {
			return aReportar;
		}

		public DefaultListModel<String> getModel1() {
			return model1;
		}

		public DefaultListModel<String> getModel2() {
			return model2;
		}

		public void limpiarCriterios() {
			this.campoPrecio.setText("");
			this.campoReproducciones.setText("");
			this.campoUmbral.setText("");
			
		}
	
}
