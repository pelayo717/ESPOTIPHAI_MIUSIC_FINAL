package vista;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import modelo.contenido.Album;
import modelo.contenido.Cancion;
import modelo.contenido.EstadoCancion;
import modelo.contenido.Lista;

/**
 * Clase en la que se implementa la vista PantallaInicio con todo
 * lo necesario para cumplir los requisitos impuestos
 */
public class PantallaInicio extends PantallaPrincipal { //99.9% esta terminado

	private static final long serialVersionUID = 1L;
	private JScrollPane canciones;
	private JScrollPane albumes;
	private JScrollPane listas;
	private JList<String> lista_canciones;
	private JList<String> lista_albumes;
	private JList<String> lista_listas;
	public JButton seleccionarAlbum;
	public JButton seleccionarCancion;
	public JButton seleccionarLista;
	public JButton crearCancion;
	public JButton crearAlbum;
	public JButton crearLista;
	public JButton eliminarCancion;
	public JButton eliminarAlbum;
	public JButton eliminarLista;
	
	private JLabel susCanciones;
	private JLabel susAlbumes;
	private JLabel susListas;
	
	private Cancion[] misCanciones;
	private Album[] misAlbumes;
	private Lista[] misListas;
	
	private DefaultListModel<String> model1;
	private DefaultListModel<String> model2;
	private DefaultListModel<String> model3;
	
	/**
	 * Constructor de la clase PantallaInicio donde se inicializan
	 * todos los atributos con lo valores correspondientes 
	 */
	public PantallaInicio() {  //CAMBIADO, MEJORADO
		
		super();
		
		model1 = new DefaultListModel<>();
		model2 = new DefaultListModel<>();
		model3 = new DefaultListModel<>();
		
		
		lista_canciones = new JList<String>(model1);
		lista_canciones.setCellRenderer( new RowColor());
		lista_albumes = new JList<String>(model2);
		lista_listas = new JList<String>(model3);
		
		canciones = new JScrollPane(lista_canciones);
		albumes = new JScrollPane(lista_albumes);
		listas = new JScrollPane(lista_listas);
		
		susCanciones = new JLabel("Sus canciones",  SwingConstants.CENTER);
		susAlbumes = new JLabel("Sus albumes",  SwingConstants.CENTER);	
		susListas = new JLabel("Sus listas", SwingConstants.CENTER);
		
		seleccionarAlbum = new JButton("Elegir Album");
		seleccionarCancion = new JButton("Elegir Cancion");
		seleccionarLista = new JButton("Elegir Lista");
		
		crearCancion = new JButton("Crear Cancion");
		crearAlbum = new JButton("Crear Album");
		crearLista = new JButton("Crear Lista");
		
		eliminarCancion = new JButton("Eliminar Cancion");
		eliminarAlbum = new JButton("Eliminar Album");
		eliminarLista = new JButton("Eliminar Lista");
		
		
		//Cambio de estilo en los JLabel
		Font susCancionesFont = new Font(susCanciones.getFont().getName(), Font.BOLD, 16);
		Font susAlbumesFont = new Font(susAlbumes.getFont().getName(), Font.BOLD, 16);
		Font susListasFont = new Font(susListas.getFont().getName(), Font.BOLD, 16);
		canciones.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		albumes.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		listas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		this.susCanciones.setFont(susCancionesFont);
		this.susAlbumes.setFont(susAlbumesFont);
		this.susListas.setFont(susListasFont);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();				
		
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
		
		eliminarCancion.setBounds(screenSize.width/2 - 530, 530, 250, 30);
		eliminarAlbum.setBounds(screenSize.width/2 - 170, 530, 250, 30);
		eliminarLista.setBounds(screenSize.width/2 + 190, 530, 250, 30);
		
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
		this.add(eliminarCancion);
		this.add(eliminarAlbum);
		this.add(eliminarLista);
	}
		
	
	/**
	 * Funcion que asgina a cada boton la accion que se pasa como argumento
	 * @param c: accion que se va a pasar a cada boton para que luego sse asigne el usuario determinado
	 */
	public void setControlador(ActionListener c) {
		super.getBotonIzquierdaArriba().addActionListener(c);
		super.getBotonIzquierdaMedio().addActionListener(c);
		this.seleccionarCancion.addActionListener(c);
		this.seleccionarAlbum.addActionListener(c);
		this.seleccionarLista.addActionListener(c);
		super.getBotonBuscar().addActionListener(c);
		super.getBotonLimpiarBuscador().addActionListener(c);
		this.crearAlbum.addActionListener(c);
		this.crearCancion.addActionListener(c);
		this.crearLista.addActionListener(c);
		this.eliminarCancion.addActionListener(c);
		this.eliminarAlbum.addActionListener(c);
		this.eliminarLista.addActionListener(c);
	}
		 
	/**
	 * Funcion que cambia el texto de unos determinados botones para ponerlos el 
	 * texto a lo necesario para cuando el usuario si que esa registrado
	 */
	public void setUsuarioRegistrado() {
		super.getBotonIzquierdaArriba().setText("Ver Perfil");
		super.getBotonIzquierdaMedio().setVisible(false);
		super.getBotonIzquierdaAbajo().setVisible(false);
	}
	
	/**
	 * Funcion que cambia el texto de unos determinados botones para ponerlos el 
	 * texto a lo necesario para cuando el usuario no esta registrado
	 */
	public void setUsuarioNoRegistrado() {
		super.getBotonIzquierdaArriba().setText("Iniciar Sesion");
		super.getBotonIzquierdaMedio().setText("Registro");
		super.getBotonIzquierdaMedio().setVisible(true);
		super.getBotonIzquierdaAbajo().setVisible(false);
	}
	
	
	/**
	 * Funcion que delvuelve el atributo serialVersionUID de la clase
	 * @return serialVersionUID:
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Funcion que delvuelve un scrollPane que contiene todas las canciones del usuario
	 * @return canciones: atributo de tipo JScrollPane el cual contiene todas las canciones del usuario
	 */
	public JScrollPane getCanciones() {
		return canciones;
	}

	/**
	 * Funcion que delvuelve un scrollPane que contiene todas los albumes del usuario
	 * @return albumes: atributo de tipo JScrollPane el cual contiene todas las canciones del usuario
	 */
	public JScrollPane getAlbumes() {
		return albumes;
	}

	/**
	 * Funcion que delvuelve un scrollPane que contiene todas las listas del usuario
	 * @return listas: atributo de tipo JScrollPane el cual contiene todas las listas del usuario
	 */
	public JScrollPane getListas() {
		return listas;
	}

	/**
	 * Funcion que delvuelve un JList que contiene todas las canciones del usuario
	 * @return lista_canciones: atributo de tipo JList el cual contiene todas las canciones del usuario
	 */
	public JList<String> getLista_canciones() {
		return lista_canciones;
	}

	/**
	 * Funcion que delvuelve un JList que contiene todas los albumes del usuario
	 * @return lista_albumes: atributo de tipo JList el cual contiene todas los albumes del usuario
	 */
	public JList<String> getLista_albumes() {
		return lista_albumes;
	}

	/**
	 * Funcion que delvuelve un JList que contiene todas las listas del usuario
	 * @return lista_listas: atributo de tipo JList el cual contiene todas las listas del usuario
	 */
	public JList<String> getLista_listas() {
		return lista_listas;
	}

	/**
	 * Funcion que delvuelve un JButton que indicar la funcion de seleccionar un album determinado
	 * @return seleccionarAlbum: atributo de tipo JButton el cual contiene todas las canciones del usuario
	 */
	public JButton getSeleccionarAlbum() {
		return seleccionarAlbum;
	}
	
	/**
	 * Funcion que devuelve el boton de seleccionar cancion
	 * @return seleccionarCancion: devuelve el boton de seleccionarCancion
	 */
	public JButton getSeleccionarCancion() {
		return seleccionarCancion;
	}

	/**
	 * Funcion que devuelve el boton de seleccionar lista
	 * @return seleccionarAlbum: devuelve el boton de seleccionarLista
	 */
	public JButton getSeleccionarLista() {
		return seleccionarLista;
	}


	/**
	 * Funcion que delvuelve el boton que lleva a cabo la funcion de crear una cancion
	 * @return crear_cancion: atributo de tipo JButton el cual representa graficamente
	 * la funcion de crear una cancion por parte del usuario
	 */
	public JButton getCrearCancion() {
		return crearCancion;
	}

	
	/**
	 * Funcion que delvuelve el boton que tiene por nombre CrearAlbum
	 * @return crearAlbum: atributo de tipo JButton la cual representa la accion 
	 * de crear un nuevo album
	 */
	public JButton getCrearAlbum() {
		return crearAlbum;
	}

	
	/**
	 * Funcion que delvuelve el boton que tiene por nombre CrearLista
	 * @return crearLista: atributo de la clase de tipo JButton el cual representa el boton
	 * de la vista llamado CrearLista
	 */
	public JButton getCrearLista() {
		return crearLista;
	}

	
	/**
	 * Funcion que delvuelve el boton de la vista denominado como EliminarCancion
	 * @return eliminarCancion: atributo que se devuelve en la funcion el cual representa la accion
	 * de borrar una cancion de tus propias canciones
	 */
	public JButton getEliminarCancion() {
		return eliminarCancion;
	}

	
	
	/**
	 * Funcion que delvuelve el boton que se usa para poder eliminar un album
	 * @return eliminarAlbum: boton que representa la accion de eliminar un album de los que has subido tu
	 */
	public JButton getEliminarAlbum() {
		return eliminarAlbum;
	}

	/**
	 * Funcion que delvuelve el boton que se usa para poder eliminar una lista
	 * @return eliminarAlbum: boton que representa la accion de eliminar un album de los que has subido tu
	 */
	public JButton getEliminarLista() {
		return eliminarLista;
	}


	
	/**
	 * Funcion que delvuelve un JLabel con todas las canciones que tiene el usuario
	 * que realiza la accion
	 * @return susCanciones: atributo de tipo JLabel que contiene todas las canciones del usuario
	 */
	public JLabel getSusCanciones() {
		return susCanciones;
	}
	
	/**
	 * Funcion que delvuelve un JLabel con todos los albumes del usuario
	 * @return susAlbumes: atributo de tipo JLabel que contiene todos los 
	 * albumes del usuario
	 */
	public JLabel getSusAlbumes() {
		return susAlbumes;
	}

	/**
	 * Funcion que delvuelve un JLabel con todas las listas del usuario
	 * @return susListas: atributo de tipo JLabel el cual contiene todas las 
	 * listas del usuario
	 */
	public JLabel getSusListas() {
		return susListas;
	}
	
	/**
	 * Funcion que delvuelve un array de tipo Cancion con todas las canciones
	 * del usuario
	 * @return misCanciones: atributo de tipo Canciones el cual contiene todas las 
	 * canciones del usuario
	 */
	public Cancion[] getMisCanciones() {
		return misCanciones;
	}
	
	/**
	 * Funcion que delvuelve un array de tipo Album con todas los albumes
	 * del usuario
	 * @return misAlbumes: atributo de tipo Album el cual contiene todos los 
	 * albumes del usuario
	 */
	public Album[] getMisAlbumes() {
		return misAlbumes;
	}

	/**
	 * Funcion que delvuelve un array de tipo Lista con todas las listas
	 * del usuario
	 * @return misListas: atributo de tipo Listas el cual contiene todas las 
	 * lsitas del usuario
	 */
	public Lista[] getMisListas() {
		return misListas;
	}

	/**
	 * Funcion que devuelve el modelo1 de la clase
	 * @param model1: atributo que indica que estamos en el modelo 1
	 */
	public DefaultListModel<String> getModel1() {
		return model1;
	}

	/**
	 * Funcion que devuelve el modelo2 de la clase
	 * @param model2: atributo que indica que estamos en el modelo 2
	 */
	public DefaultListModel<String> getModel2() {
		return model2;
	}

	/**
	 * Funcion que devuelve el modelo3 de la clase
	 * @param model3: atributo que indica que estamos en el modelo 3
	 */
	public DefaultListModel<String> getModel3() {
		return model3;
	}

	/**
	 * Funcion que actualiza las canciones a partir del array list de canciones pasado
	 * como argumento 
	 * @param canciones_propias: canciones que se van a poner como nuevas al actualizar
	 */
	public void actualizarCanciones(ArrayList<Cancion> canciones_propias) {
		model1.clear();
		misCanciones = canciones_propias.toArray(new Cancion[canciones_propias.size()]);
		for(int i=0; i < misCanciones.length; i++) {			
			int horas = (int) (misCanciones[i].getDuracion() / 3600);
		    int minutos = (int) ((misCanciones[i].getDuracion()-horas*3600)/60);
		    int segundos = (int) (misCanciones[i].getDuracion()-(horas*3600+minutos*60));
			if(misCanciones[i].getEstado() == EstadoCancion.PENDIENTEMODIFICACION) {
				LocalDate a = misCanciones[i].getFechaModificacion();
				a = a.plusDays(3);
				model1.addElement("Titulo: " + misCanciones[i].getTitulo() + " // Duracion HH-MM-SS: " + horas + "-" + minutos + "-" + segundos + " // Estado: " + misCanciones[i].getEstado().name() + " // Fecha Fin Modificacion: " + a.toString());
			}else {
				model1.addElement("Titulo: " + misCanciones[i].getTitulo() + " // Duracion HH-MM-SS: " + horas + "-" + minutos + "-" + segundos + " // Estado: " + misCanciones[i].getEstado().name());
			}
		}
		
	}
	
	/**
	 * Funcion que actualiza los albumes por los albumes propios
	 * @param albumes_propios: albumes que se van a poner como nuevos al actualizar
	 */
	public void actualizarAlbumes(ArrayList<Album> albumes_propios) {
		model2.clear();
		misAlbumes = albumes_propios.toArray(new Album[albumes_propios.size()]);
		for(int i=0; i < misAlbumes.length; i++) {
			int horas = (int) (misAlbumes[i].getDuracion() / 3600);
		    int minutos = (int) ((misAlbumes[i].getDuracion()-horas*3600)/60);
		    int segundos = (int) (misAlbumes[i].getDuracion()-(horas*3600+minutos*60));
			model2.addElement("Titulo: " + misAlbumes[i].getTitulo() + " // Num.Canciones: " + misAlbumes[i].getContenido().size() + " // Duracion HH-MM-SS: " + horas + "-" + minutos + "-" + segundos);
		}
		
	}
	
	/**
	 * Funcion que actualiza las listas de las listas propias del usuario
	 * @param listas_propias: listasque se van a poner como nuevas al actualizar
	 */
	public void actualizarListas(ArrayList<Lista> listas_propias) {
		model3.clear();
		misListas = listas_propias.toArray(new Lista[listas_propias.size()]);
		for(int i=0; i < misListas.length; i++) {
			misListas[i].setDuracion(misListas[i].calcularTiempo()); //Lo realizamos aqui porque la lista desconoce cuando a un album le han anyadido algo, por tanto este recalcula su tiempo para hacerlo efectivo antes de presentarse 
			int horas = (int) (misListas[i].getDuracion() / 3600);
		    int minutos = (int) ((misListas[i].getDuracion()-horas*3600)/60);
		    int segundos = (int) (misListas[i].getDuracion()-(horas*3600+minutos*60));
			model3.addElement("Titulo: " + misListas[i].getTitulo() + " // Num.Contenido: "  + misListas[i].getContenido().size() + " // Duracion HH-MM-SS: " + horas + "-" + minutos + "-" + segundos);
		}
		
	}

	/**
	 * Funcion que limpia los datos completamente
	 */
	public void limpiarDatos() {
		model1.clear();
		model2.clear();
		model3.clear();
	}
	
	/**
	 * Clase que se ejecuta cada vez que se actualiza la pagina para comprobar que todos los 
	 * objetos de la vista son de tipo correcto
	 */
	private class RowColor extends DefaultListCellRenderer{
		
		private static final long serialVersionUID = 1L;

		public Component getListCellRendererComponent( JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus ) {
            Component c = super.getListCellRendererComponent( list, value, index, isSelected, cellHasFocus );
            if (misCanciones[index].getEstado() == EstadoCancion.VALIDA) {
                c.setBackground( Color.green); 
                c.setForeground( Color.black );
            }else if(misCanciones[index].getEstado() == EstadoCancion.PENDIENTEAPROBACION){ //LAS PENDIENTES DE MODIFICACION
                c.setBackground( Color.cyan );
                c.setForeground( Color.black );
            }else if(misCanciones[index].getEstado() == EstadoCancion.PENDIENTEMODIFICACION) {
                c.setBackground( Color.orange );
                c.setForeground( Color.black );
            }else if(misCanciones[index].getEstado() == EstadoCancion.EXPLICITA) {
            	c.setBackground( Color.pink );
                c.setForeground( Color.black );
            }
            return c;
        }
		
	}
			
}  
