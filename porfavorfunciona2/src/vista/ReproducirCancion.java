package vista;

import java.awt.*;
import java.awt.event.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.*;


import modelo.contenido.*;

  



public class ReproducirCancion extends PantallaPrincipal {

	/**
	 * 
	 */
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
	

	// método para asignar un controlador al botón
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
	
	public void limpiarBuscador(){
		super.getBusquedaTextfield().setText("");
		super.getGrupo_eleccion().clearSelection();
	}
	
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
	
	public void addToTree(DefaultMutableTreeNode fatherNode, Comentario c) {
		for (Comentario subc : c.getSubComentarios()) {
			DefaultMutableTreeNode subNode = new DefaultMutableTreeNode(subc);
            fatherNode.add(subNode);
            ((DefaultTreeModel)comentariosTree.getModel()).reload(fatherNode);
			this.addToTree(subNode,subc);
          }
	}

	public void setAdministrador() {
		super.getBotonIzquierdaArriba().setText("Ver Perfil");
		super.getBotonIzquierdaMedio().setText("Inicio");
		super.getBotonIzquierdaAbajo().setVisible(false);
		this.anyadirAlbum.setVisible(false);
		this.anyadirLista.setVisible(false);
		this.botonReportar.setVisible(false);
		this.botonAnyadirComentario.setBounds(screenSize.width/2 + 150, 680, 150, 30);
	}
	
	public void setUsuarioRegistradoPropia() {
		super.getBotonIzquierdaArriba().setText("Ver Perfil");
		super.getBotonIzquierdaMedio().setText("Inicio");
		super.getBotonIzquierdaAbajo().setVisible(false);
		this.anyadirAlbum.setVisible(true);
		this.anyadirLista.setVisible(true);
		this.anyadirLista.setBounds(screenSize.width/2 - 210, 600, 150, 30);
		this.botonReportar.setVisible(false);
		this.botonAnyadirComentario.setBounds(screenSize.width/2 + 150, 680, 150, 30);

	}
	
	public void setUsuarioRegistradoNoPropia() {
		super.getBotonIzquierdaArriba().setText("Ver Perfil");
		super.getBotonIzquierdaMedio().setText("Inicio");
		super.getBotonIzquierdaAbajo().setVisible(false);
		this.anyadirAlbum.setVisible(false);
		this.anyadirLista.setVisible(true);
		this.anyadirLista.setBounds(screenSize.width/2 - 280, 600, 150, 30);
		this.botonReportar.setBounds(screenSize.width/2 + 230, 680, 150, 30);
		this.botonAnyadirComentario.setBounds(screenSize.width/2 + 80, 680, 150, 30);

	}
	
	
	public void setUsuarioNoRegistradoNoPropia() {
		super.getBotonIzquierdaArriba().setText("Iniciar Sesion");
		super.getBotonIzquierdaMedio().setText("Registro");
		super.getBotonIzquierdaAbajo().setVisible(true);
		this.anyadirAlbum.setVisible(false);
		this.anyadirLista.setVisible(true);
		this.anyadirLista.setBounds(screenSize.width/2 - 280, 600, 150, 30);
		this.botonReportar.setBounds(screenSize.width/2 + 150, 680, 150, 30);
		this.botonAnyadirComentario.setVisible(false);

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Cancion getCancion() {
		return cancion;
	}

	public JLabel getDatos_cancion() {
		return datos_cancion;
	}

	public JLabel getTitulo_cancion() {
		return titulo_cancion;
	}

	public JLabel getAutor_cancion() {
		return autor_cancion;
	}

	public JLabel getDuracion_cancion() {
		return duracion_cancion;
	}

	public JLabel getComentarios_label() {
		return comentarios_label;
	}

	public JLabel getEstadoCancion() {
		return estadoCancion;
	}

	public JButton getBotonPlay() {
		return botonPlay;
	}

	public JButton getBotonPause() {
		return botonPause;
	}


	public JScrollPane getComentariosScrollPane() {
		return comentariosScrollPane;
	}

	public JButton getBotonList() {
		return botonList;
	}

	public JButton getBotonAnyadirComentario() {
		return botonAnyadirComentario;
	}

	public JButton getBotonReportar() {
		return botonReportar;
	}

	public JButton getModificarCancion() {
		return modificarCancion;
	}

	public JButton getAnyadirAlbum() {
		return anyadirAlbum;
	}

	public JButton getAnyadirLista() {
		return anyadirLista;
	}

	public Dimension getScreenSize() {
		return screenSize;
	}

	public Comentario[] getComentarios() {
		return comentarios;
	}

	 public JTree getComentariosTree() {
		return comentariosTree;
	}
	
	public Comentario getComentarioSeleccionado() {
			return comentarioSeleccionado;
	}
}
