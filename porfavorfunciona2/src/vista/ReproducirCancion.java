package vista;

import java.awt.*;
import java.awt.event.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.*;


import modelo.contenido.*;

  
/**
 * Clase en la que se implementa la vista ReproducirCancion con todo
 * lo necesario para cumplir los requisitos impuestos
 */
public class ReproducirCancion extends PantallaPrincipal {

	private static final long serialVersionUID = 1L;

	private  Cancion cancion;
	
	private JLabel datos_cancion;
	private JLabel titulo_cancion;
	private JLabel autor_cancion;
	private JLabel duracion_cancion;
	private JLabel comentarios_label;
	private JLabel estadoCancion;
	
	private JButton botonPlay;
	private JButton botonPause;
	private JScrollPane comentariosScrollPane;
	private JButton botonList;
	private JButton botonAnyadirComentario;
	private JButton botonReportar;
	
	private JButton modificarCancion;
	
	private JButton anyadirAlbum;
	private JButton anyadirLista;
	
	private Dimension screenSize;
	
	private  Comentario[] comentarios;
	private Comentario comentarioSeleccionado;
	
	private JTree comentariosTree;
	private DefaultMutableTreeNode root;
	private DefaultTreeModel treeModel;
	
	/**
	 * Constructor de la clase ReproducirCancion donde se inicializan
	 * todos los atributos con lo valores correspondientes 
	 */
	public ReproducirCancion() {
		super();
		
				
		root = new DefaultMutableTreeNode("Comentarios");
		
		treeModel = new DefaultTreeModel(root);
		
        comentariosTree = new JTree(treeModel);
        
        treeModel.reload();
        setTree();
        comentariosTree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)comentariosTree.getLastSelectedPathComponent();  
				if(selectedNode != null && selectedNode.getUserObject() instanceof Comentario) {
					comentarioSeleccionado  = (Comentario)selectedNode.getUserObject();
				}
			  }
			});
        comentariosTree.setRootVisible(true);
        comentariosTree.setShowsRootHandles(true);
		comentariosScrollPane = new JScrollPane(comentariosTree);

		
		//Declaracion
		ImageIcon icono_corchea = new ImageIcon("src/vista/photo_default.jpg");
		ImageIcon icono_reproducir = new ImageIcon("src/vista/play.png");
		ImageIcon icono_parar = new ImageIcon("src/vista/pause.png");
		
	    
	    
		JLabel imagen_reproduccion = new JLabel("",icono_corchea,JLabel.CENTER);
		this.botonPlay = new JButton("play");
		this.botonPause = new JButton("pause");
		this.botonList = new JButton("Ver Comentario");
		this.botonAnyadirComentario = new JButton("Añadir Comentario");
		this.botonReportar = new JButton("Reportar");
		this.anyadirAlbum = new JButton("Añadir a Album");
		this.anyadirLista = new JButton("Añadir a Lista");
		this.modificarCancion = new JButton("Modificar Cancion");
		botonPlay.setIcon(icono_reproducir);
		botonPause.setIcon(icono_parar);

		
		
		datos_cancion = new JLabel("Datos de la cancion", SwingConstants.CENTER);
		titulo_cancion = new JLabel("Titulo:\t\t\t\t\t" ,SwingConstants.LEFT);
		autor_cancion = new JLabel("Autor:\t\t\t\t\t" ,SwingConstants.LEFT);
		duracion_cancion = new JLabel("Duracion:\t\t\t\t\t" + " s",SwingConstants.LEFT);
		comentarios_label = new JLabel("Comentarios de la cancion", SwingConstants.CENTER);
		estadoCancion = new JLabel("Estado Cancion:\t\t\t\t\t");
		
		
		
		

		
		//Style changes
		Font datosFont = new Font(datos_cancion.getFont().getName(),Font.BOLD,16);
		Font tituloFont = new Font(titulo_cancion.getFont().getName(),Font.BOLD,titulo_cancion.getFont().getSize());
		Font autorFont = new Font(autor_cancion.getFont().getName(),Font.BOLD,autor_cancion.getFont().getSize());
		Font duracionFont = new Font(duracion_cancion.getFont().getName(),Font.BOLD,duracion_cancion.getFont().getSize());
		Font comentariosFont = new Font(datos_cancion.getFont().getName(),Font.BOLD,datos_cancion.getFont().getSize());
        Font estadoCancionFont = new Font(estadoCancion.getFont().getName(),Font.BOLD,estadoCancion.getFont().getSize());
		comentariosScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		datos_cancion.setFont(datosFont);
		titulo_cancion.setFont(tituloFont);
		autor_cancion.setFont(autorFont);
		duracion_cancion.setFont(duracionFont);
		comentarios_label.setFont(comentariosFont);
		estadoCancion.setFont(estadoCancionFont);

		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		//Manual Constraints
		//x axis, y axis, width, height 
		
		//Distribucion
		datos_cancion.setBounds(screenSize.width/2 + 125, 170, 200, 50);
		titulo_cancion.setBounds(screenSize.width/2 + 50, 210, 200, 50);
		autor_cancion.setBounds(screenSize.width/2 + 50,250,200,50);
		duracion_cancion.setBounds(screenSize.width/2 + 50,290,250,50);
		comentarios_label.setBounds(screenSize.width/2 + 105, 370, 250, 50);
		estadoCancion.setBounds(screenSize.width/2 + 105, 330, 400, 50);
		comentariosScrollPane.setBounds(screenSize.width/2 + 80, 410, 300, 200);
		botonList.setBounds(screenSize.width/2 + 150, 640, 150, 30);
		botonAnyadirComentario.setBounds(screenSize.width/2 + 80, 680, 150, 30);
		botonReportar.setBounds(screenSize.width/2 + 230, 680, 150, 30);


		imagen_reproduccion.setBounds(screenSize.width/2 - 350, 190, 300, 300);
		botonPlay.setBounds(screenSize.width/2 - 280, 500, 60, 60);
		botonPause.setBounds(screenSize.width/2 - 210, 500, 60, 60);
		anyadirAlbum.setBounds(screenSize.width/2 - 370, 600, 150, 30);
		anyadirLista.setBounds(screenSize.width/2 - 210, 600, 150, 30);
	
		modificarCancion.setBounds(screenSize.width/2 + 420, 340, 150, 30);
		
		//Añadimos
		this.add(datos_cancion);
		this.add(titulo_cancion);
		this.add(autor_cancion);
		this.add(duracion_cancion);
		this.add(comentarios_label);
		this.add(estadoCancion);
		this.add(comentariosScrollPane);
		this.add(botonList);
		this.add(botonAnyadirComentario);
		this.add(botonReportar);
		this.add(imagen_reproduccion);
		this.add(botonPlay);
		this.add(botonPause);
		this.add(anyadirAlbum);
		this.add(anyadirLista);
		this.add(modificarCancion);
	}
	

	 /**
	 * Funcion que asgina a cada boton la accion que se pasa como argumento
	 * @param c: accion que se va a pasar a cada boton para que luego sse asigne el usuario determinado
	 */

	 public void setControlador(ActionListener c) {
		 super.getBotonIzquierdaArriba().addActionListener(c);
		 super.getBotonIzquierdaMedio().addActionListener(c);
		 super.getBotonIzquierdaAbajo().addActionListener(c);
		 super.getBotonBuscar().addActionListener(c);
		 super.getBotonLimpiarBuscador().addActionListener(c);
		 this.botonList.addActionListener(c);
		 this.botonAnyadirComentario.addActionListener(c);
		 this.botonReportar.addActionListener(c);
		 this.botonPlay.addActionListener(c);
		 this.botonPause.addActionListener(c);
		 this.anyadirAlbum.addActionListener(c);
		 this.anyadirLista.addActionListener(c);
		 this.modificarCancion.addActionListener(c);
	 }
	
		public void insertarComentario(Comentario nuevoComentario) {
		if(this.cancion != null) {
			cancion.anyadirComentario(nuevoComentario);
            ((DefaultTreeModel)comentariosTree.getModel()).reload(root);
		}
	}
	
	/**
	 * Funcion que limpia el buscador de la aplicacion y lo deja vacio 
	 * para que el usuario pueda realizar otra busqueda
	 */
	public void limpiarBuscador(){
		super.getBusquedaTextfield().setText("");
		super.getGrupo_eleccion().clearSelection();
	}
	
	 /**
	  * Funcion que pone la informacion necesaria sobre la vista en base al argumento de
	  * entrada, siendo este la lista que se va a reproducir
	  * @param cancion: Cancion que se va a poner en la vista ya que el usuario la ha seleccionado
	  */
	public void setInformacion(Cancion cancion) {
		this.cancion = cancion;

		int horas = (int) (cancion.getDuracion() / 3600);
	    int minutos = (int) ((cancion.getDuracion()-horas*3600)/60);
	    int segundos = (int) (cancion.getDuracion()-(horas*3600+minutos*60));
		
		datos_cancion.setText("Datos de la cancion");
		titulo_cancion.setText("Titulo:\t\t\t\t\t" + this.cancion.getTitulo());
		autor_cancion.setText("Autor:\t\t\t\t\t" + this.cancion.getAutor().getNombreAutor());
		duracion_cancion.setText("Duracion:\t\t\t\t\t" + minutos + " m/" + segundos + " s");
		estadoCancion.setText("Estado Cancion:\t\t\t\t\t" + this.cancion.getEstado().name());
		if(this.cancion.getEstado() == EstadoCancion.PENDIENTEMODIFICACION) {
			this.modificarCancion.setVisible(true);
		}else{
			this.modificarCancion.setVisible(false);
		}
		this.setTree();
	}
	
	
	/**
	 * Funcion que crea y ordena el arbol desde el principio para luego ir introduciendo 
	 * los diferentes comentarios que se van realizando sobre la cancion 
	 */
	public void setTree() {
		root.removeAllChildren();
        //create the child nodes
		if(cancion!=null) {
			comentarios = cancion.getComentarios().toArray(new Comentario[cancion.getComentarios().size()]);
		}
		
		if(comentarios != null) {
			for (Comentario c : comentarios)
	        {
	          DefaultMutableTreeNode comentario = new DefaultMutableTreeNode(c);
	          root.add(comentario);
	          for (Comentario subc : c.getSubComentarios()) {
				DefaultMutableTreeNode subNode = new DefaultMutableTreeNode(subc);
	            comentario.add(subNode);
				((DefaultTreeModel)comentariosTree.getModel()).reload(comentario);
				this.addToTree(subNode,subc);
	          }
	        }
		}
		treeModel = new DefaultTreeModel(root);
		((DefaultTreeModel)comentariosTree.getModel()).reload(root);
		treeModel.reload();
		expandAllNodes(comentariosTree, 0, comentariosTree.getRowCount());
		
	}
	
	private void expandAllNodes(JTree tree, int startingIndex, int rowCount){
	    for(int i=startingIndex;i<rowCount;++i){
	        tree.expandRow(i);
	    }

	    if(tree.getRowCount()!=rowCount){
	        expandAllNodes(tree, rowCount, tree.getRowCount());
	    }
	}
	
	
	/**
	 * Funcion que añade un comentario pasado como argumento a un arbol que contiene todos los comentarios que se
	 * han realizado sobre esa cancion
	 * @param fatherNode: padre del nodo en donde se va a introducir el comentario
	 * @param c: comentario el cual se va a introducir en el arbol
	 */
	public void addToTree(DefaultMutableTreeNode fatherNode, Comentario c) {
		for (Comentario subc : c.getSubComentarios()) {
			DefaultMutableTreeNode subNode = new DefaultMutableTreeNode(subc);
            fatherNode.add(subNode);
            ((DefaultTreeModel)comentariosTree.getModel()).reload(fatherNode);
			this.addToTree(subNode,subc);
          }
	}

	
	/**
	 * Funcion que cambia el texto de unos determinados botones para ponerlos el 
	 * texto a lo necesario para cuando el usuario es el administrador
	 */	
	public void setAdministrador() {
		super.getBotonIzquierdaArriba().setText("Ver Perfil");
		super.getBotonIzquierdaMedio().setText("Inicio");
		super.getBotonIzquierdaAbajo().setVisible(false);
		this.anyadirAlbum.setVisible(false);
		this.anyadirLista.setVisible(false);
		this.botonReportar.setVisible(false);
		this.botonAnyadirComentario.setBounds(screenSize.width/2 + 150, 680, 150, 30);
	}
	
	/**
	 * Funcion que cambia el texto de unos determinados botones para ponerlos el 
	 * texto a lo necesario para cuando el usuario si que esta registrado y la cancion que 
	 * se va a poner en la vista si es suya propia 
	 */
	public void setUsuarioRegistradoPropia() {
		super.getBotonIzquierdaArriba().setText("Ver Perfil");
		super.getBotonIzquierdaMedio().setText("Inicio");
		super.getBotonIzquierdaAbajo().setVisible(false);
		this.anyadirAlbum.setVisible(true);
		this.anyadirLista.setVisible(true);
		this.anyadirLista.setBounds(screenSize.width/2 - 210, 600, 150, 30);
		this.botonReportar.setVisible(true);
		this.botonAnyadirComentario.setBounds(screenSize.width/2 + 80, 680, 150, 30);

	}
	
	/**
	 * Funcion que cambia el texto de unos determinados botones para ponerlos el 
	 * texto a lo necesario para cuando el usuario si esta registrado y la cancion que 
	 * se va a poner en la vista no es suya propia
	 */
	public void setUsuarioRegistradoNoPropia() {
		super.getBotonIzquierdaArriba().setText("Ver Perfil");
		super.getBotonIzquierdaMedio().setText("Inicio");
		super.getBotonIzquierdaAbajo().setVisible(false);
		this.anyadirAlbum.setVisible(false);
		this.anyadirLista.setVisible(true);
		this.anyadirLista.setBounds(screenSize.width/2 - 280, 600, 150, 30);
		this.botonReportar.setVisible(true);
		this.botonAnyadirComentario.setBounds(screenSize.width/2 + 80, 680, 150, 30);

	}
	
	/**
	 * Funcion que cambia el texto de unos determinados botones para ponerlos el 
	 * texto a lo necesario para cuando el usuario no esta registrado y la cancion
	 * que se va a poner en la vista no es suya propia
	 */
	public void setUsuarioNoRegistradoNoPropia() {
		super.getBotonIzquierdaArriba().setText("Iniciar Sesion");
		super.getBotonIzquierdaMedio().setText("Registro");
		super.getBotonIzquierdaAbajo().setVisible(true);
		this.anyadirAlbum.setVisible(false);
		this.anyadirLista.setVisible(true);
		this.anyadirLista.setBounds(screenSize.width/2 - 280, 600, 150, 30);
		this.botonReportar.setVisible(true);
		this.botonAnyadirComentario.setBounds(screenSize.width/2 + 80, 680, 150, 30);

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Funcion que devuelve la cancion que el usuario a elegido
	 * @return cancion: atributo el cual se va a reproducir por parte del usuario
	 */
	public Cancion getCancion() {
		return cancion;
	}

	/**
	 * Funcopm que devuelve JLabel de los comentarios que hay en la lista
	 * @return comentarios_label: atributo que contiene todos los comentarios de la lista
	 */
	public JLabel getDatos_cancion() {
		return datos_cancion;
	}

	/**
	 * Funcion que devuelve JLabel con el titulo de la cancion
	 * @return titulo_cancion: atributo que identifica el nombre de la cancion
	 */
	public JLabel getTitulo_cancion() {
		return titulo_cancion;
	}

	/**
	 * Funcion que devuelve JLabel con el autor de la cancion
	 * @return autor_cancion: atributo que hace referencia al autor de la cancion
	 */
	public JLabel getAutor_cancion() {
		return autor_cancion;
	}

	/**
	 * Funcion que devuelve JLabel con la duracion de la cancion en cuestion
	 * @return duracion_cancion: atributo que representa la duracion que tiene la cancion
	 */
	public JLabel getDuracion_cancion() {
		return duracion_cancion;
	}

	/**
	 * Funcion que devuelve JLabel con los comentarios que tiene la cancion
	 * @return comentarios_label: atributo que contiene todos los comentarios realizados sobre la cancion
	 */
	public JLabel getComentarios_label() {
		return comentarios_label;
	}
	
	/**
	 * Funcion que devuelve JLabel con el estado de la cancion
	 * @return estadoCancion: atributo que identifica el estado de la cancion
	 */
	public JLabel getEstadoCancion() {
		return estadoCancion;
	}

	/**
	 * Funcion que devuelve el JButton  que representa el boton de play de la cancion
	 * @return botonPlay: atributo que representa la funcion de darle a play a la cancion
	 */	
	public JButton getBotonPlay() {
		return botonPlay;
	}
	
	/**
	 * Funcion que devuelve el JButton  que representa el boton de pausa de la cancion
	 * @return botonPause: atributo que representa la funcion de pausar la cancion
	 */
	public JButton getBotonPause() {
		return botonPause;
	}

	/**
	 * Funcion que devuelve el JScrollPane que contiene todos los comentarios de la cancion
	 * @return comentariosScrollPane: atributo que contiene todos los comentarios de la cancion
	 */
	public JScrollPane getComentariosScrollPane() {
		return comentariosScrollPane;
	}

	/**
	 * Funcion que devuelve el JButton  que representa el boton de lista
	 * @return botonList: atributo que representa la funcion de seleccionar lista a la hora de buscar
	 */
	public JButton getBotonList() {
		return botonList;
	}

	/**
	 * Funcion que devuelve el JButton  que representa el boton de anyadir comentario
	 * @return botonAnyadirComentario: atributo que representa la funcion de poder anyadir un 
	 * comentario a una cancion
	 */
	public JButton getBotonAnyadirComentario() {
		return botonAnyadirComentario;
	}
	
	/**
	 * Funcion que devuelve el JButton  que representa el boton de reportar una cancion
	 * @return botonReportar: atributo que representa la funcion de reportar una cancion
	 */
	public JButton getBotonReportar() {
		return botonReportar;
	}

	/**
	 * Funcion que devuelve el JButton  que representa el boton de modificar cancion
	 * @return modificarCancion: atributo que representa la funcion de modificar una cancion
	 */
	public JButton getModificarCancion() {
		return modificarCancion;
	}

	/**
	 * Funcion que devuelve el JButton  que representa el boton de anyadir a un album
	 * @return anyadirAlbum: atributo que representa la funcion de anyadira un album la cancion
	 */
	public JButton getAnyadirAlbum() {
		return anyadirAlbum;
	}

	/**
	 * Funcion que devuelve el JButton  que representa el boton de anyadir la cancion a una lista
	 * @return anyadirLista: atributo que representa la funcion de anyadir la cancion a una lista
	 */
	public JButton getAnyadirLista() {
		return anyadirLista;
	}
	
	/**
	 * Funcion que devuelve la dimension de la pantalla para ajustar la vista a la misma
	 * @return screenSize: atributo que define el tamaño de la pantalla en la que se 
	 * esta usando la aplicacion para ajustar la vista
	 */
	public Dimension getScreenSize() {
		return screenSize;
	}
	
	/**
	 * Funcion que devuelve un array de comentarios con todos los comentarios que tiene la cancion
	 * @return comentarios: atributo de tipo Comentario que contiene todos los comentarios que se han 
	 * hecho sobre la cancion pertinente
	 */
	public Comentario[] getComentarios() {
		return comentarios;
	}
	
	/**
	 * Funcion que devuelve un JTree con todos los comentarios en forma de arbol
	 * @return comentariosTree: atributo de tipo JTree el cual contiene todos los comentarios
	 */
	 public JTree getComentariosTree() {
		return comentariosTree;
	}
	
	/**
	 * Funcion que devuelve un Comentario seleccionado
	 * @return comentarioSeleccionado: atributo de tipo comentario el cual devuelve 
	 * un comentario seleccionado por el usuario
	 */
	public Comentario getComentarioSeleccionado() {
			return comentarioSeleccionado;
	}
}
