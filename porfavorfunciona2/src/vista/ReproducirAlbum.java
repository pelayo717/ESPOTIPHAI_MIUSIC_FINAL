package vista;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.Period;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import modelo.sistema.*;
import modelo.contenido.*;



public class ReproducirAlbum extends PantallaPrincipal {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private  Album album;
	
	private  JList<String> lista_canciones;

	
	private JScrollPane comentariosScrollPane;
	private JScrollPane cancionesScrollPane;

	private JButton botonList;
	private JButton botonAnyadirComentario;
	private JButton eliminarCancion;
	private JButton anyadirLista;
	private JButton botonPlay;
	private JButton botonPause;
	
	
	private JLabel datos_album;
	private JLabel titulo_album;
	private JLabel anyo_album;
	private JLabel autor_album;
	private JLabel duracion_album;
	private JLabel comentarios_label;
	
	private Comentario[] comentarios;
	private Comentario comentarioSeleccionado;

	private JTree comentariosTree;
	private DefaultMutableTreeNode root;
	private DefaultTreeModel treeModel;
	
	private Cancion[] misCanciones;
	private DefaultListModel<String> model2;
	
	//public Cancion reproduciendose;
	
	private Dimension screenSize;
	
	public ReproducirAlbum() {
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
				if(selectedNode!= null && selectedNode.getUserObject() instanceof Comentario) {
					comentarioSeleccionado  = (Comentario)selectedNode.getUserObject();
				}
			  }
			});
        
        comentariosTree.setRootVisible(true);
        comentariosTree.setShowsRootHandles(true);
		comentariosScrollPane = new JScrollPane(comentariosTree);
		
		model2 = new DefaultListModel<>();

		lista_canciones = new JList<String>(model2);
		
		lista_canciones.setCellRenderer( new RowColor());

		
		
		
		//Declaracion
		ImageIcon icono_reproducir = new ImageIcon("src/vista/play.png");
		ImageIcon icono_parar = new ImageIcon("src/vista/pause.png");
		
	    
		this.botonPlay = new JButton("play");
		this.botonPause = new JButton("pause");
		this.botonList = new JButton("Ver Comentario");
		this.botonAnyadirComentario = new JButton("Añadir Comentario");
		
		this.eliminarCancion = new JButton("Retirar Cancion");
		
		this.anyadirLista = new JButton("Añadir a Lista");
		
		botonPlay.setIcon(icono_reproducir);
		botonPause.setIcon(icono_parar);

		
		datos_album = new JLabel("Datos del album", SwingConstants.CENTER);
		titulo_album = new JLabel("Titulo:\t\t\t\t\t" ,SwingConstants.CENTER);
		anyo_album = new JLabel("Año:\t\t\t\t\t",SwingConstants.LEFT);
		autor_album = new JLabel("Autor:\t\t\t\t\t",SwingConstants.LEFT);
		duracion_album = new JLabel("Duracion:\t\t\t\t\t" + " s",SwingConstants.LEFT);
		comentarios_label = new JLabel("Comentarios de la album", SwingConstants.CENTER);
	
		
		cancionesScrollPane = new JScrollPane(lista_canciones);
		
		//Style changes
		Font datosFont = new Font(datos_album.getFont().getName(),Font.BOLD,16);
		Font tituloFont = new Font(titulo_album.getFont().getName(),Font.BOLD,titulo_album.getFont().getSize());
		Font anyoFont = new Font(anyo_album.getFont().getName(),Font.BOLD,anyo_album.getFont().getSize());
		Font autorFont = new Font(autor_album.getFont().getName(),Font.BOLD,autor_album.getFont().getSize());
		Font duracionFont = new Font(duracion_album.getFont().getName(),Font.BOLD,duracion_album.getFont().getSize());
		Font comentariosFont = new Font(datos_album.getFont().getName(),Font.BOLD,datos_album.getFont().getSize());
        comentariosScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        cancionesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		datos_album.setFont(datosFont);
		titulo_album.setFont(tituloFont);
		anyo_album.setFont(anyoFont);
		autor_album.setFont(autorFont);
		duracion_album.setFont(duracionFont);
		comentarios_label.setFont(comentariosFont);

		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		//Distribucion
		datos_album.setBounds(screenSize.width/2 - 380, 170, 300, 50);
		autor_album.setBounds(screenSize.width/2  - 375, 210, 150, 50);
		anyo_album.setBounds(screenSize.width/2  - 375,250,150,50);
		duracion_album.setBounds(screenSize.width/2  - 375,290,180,50);
		comentarios_label.setBounds(screenSize.width/2  - 380, 340, 300, 50);
		comentariosScrollPane.setBounds(screenSize.width/2  - 380, 400, 300, 200);
		botonList.setBounds(screenSize.width/2 - 300, 610, 150, 30);
		botonAnyadirComentario.setBounds(screenSize.width/2 - 380, 640, 150, 30);
		anyadirLista.setBounds(screenSize.width/2 - 230, 640, 150, 30);

		titulo_album.setBounds(screenSize.width/2 + 150, 210, 200, 50);
		cancionesScrollPane.setBounds(screenSize.width/2  + 100, 260, 300, 300);

		botonPlay.setBounds(screenSize.width/2 + 180, 580, 60, 60);
		botonPause.setBounds(screenSize.width/2 + 250, 580, 60, 60);

		
		eliminarCancion.setBounds(screenSize.width/2  + 430, 390, 150, 40);
	
		
		//Añadimos
		this.add(datos_album);
		this.add(titulo_album);
		this.add(anyo_album);
		this.add(autor_album);
		this.add(duracion_album);
		this.add(comentarios_label);
		this.add(cancionesScrollPane);
		this.add(comentariosScrollPane);
		this.add(botonList);
		this.add(botonAnyadirComentario);
		this.add(botonPlay);
		this.add(botonPause);
		this.add(eliminarCancion);
		this.add(anyadirLista);
	}
	
	public void limpiarBuscador(){
		super.getBusquedaTextfield().setText("");
		super.getGrupo_eleccion().clearSelection();
	}
	
	public void setAdministrador() {
		super.getBotonIzquierdaArriba().setText("Ver Perfil");
		super.getBotonIzquierdaMedio().setText("Inicio");
		super.getBotonIzquierdaAbajo().setVisible(false);
		this.anyadirLista.setVisible(false);
		this.eliminarCancion.setVisible(false);
		this.botonAnyadirComentario.setBounds(screenSize.width/2 - 300, 640, 150, 30);
	}
	
	public void setUsuarioRegistradoPropia() {
		super.getBotonIzquierdaArriba().setText("Ver Perfil");
		super.getBotonIzquierdaMedio().setText("Inicio");
		super.getBotonIzquierdaAbajo().setVisible(false);
		this.anyadirLista.setVisible(true);
		this.eliminarCancion.setVisible(true);
		this.botonAnyadirComentario.setBounds(screenSize.width/2 - 380, 640, 150, 30);
	}
	
	public void setUsuarioRegistradoNoPropia() {
		super.getBotonIzquierdaArriba().setText("Iniciar Sesion");
		super.getBotonIzquierdaMedio().setText("Registro");
		super.getBotonIzquierdaAbajo().setVisible(true);
		this.anyadirLista.setVisible(true);
		this.eliminarCancion.setVisible(false);
		this.botonAnyadirComentario.setBounds(screenSize.width/2 - 380, 640, 150, 30);
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
		 this.botonPlay.addActionListener(c);
		 this.botonPause.addActionListener(c);
		 this.anyadirLista.addActionListener(c);
		 this.eliminarCancion.addActionListener(c);
	 }
	 
	 public void setInformacion(Album album_entrante) {
		this.album = album_entrante; 
		 
	 	this.datos_album.setText("Datos del album");
		this.titulo_album.setText("Titulo album:\t\t\t\t\t" + this.album.getTitulo());
		this.anyo_album.setText("Año:\t\t\t\t\t" + this.album.getAnyo());
		this.autor_album.setText("Autor:\t\t\t\t\t" + this.album.getAutor().getNombreAutor());
		int horas = (int) (this.album.getDuracion() / 3600);
	    int minutos = (int) ((this.album.getDuracion()-horas*3600)/60);
	    int segundos = (int) (this.album.getDuracion()-(horas*3600+minutos*60));
		this.duracion_album.setText("Duracion:\t\t\t\t\t" + horas + " h/" + minutos + " m/" + segundos + " s");
		this.comentarios_label.setText("Comentarios de la album");
		
		this.actualizarCanciones();
		this.setTree();
	 }
	 
		
		public void setTree() {
			root.removeAllChildren();
	        //create the child nodes
			if(album!=null) {
				comentarios = album.getComentarios().toArray(new Comentario[album.getComentarios().size()]);
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
				System.out.println("anyadimos subcomentario" + subc);
				this.addToTree(subNode,subc);
	          }
		}
	 
	 public void actualizarCanciones() {
		model2.clear();
		misCanciones = album.getContenido().toArray(new Cancion[album.getContenido().size()]);
		if(misCanciones != null) {
			for(int i=0; i < misCanciones.length;i++) {
				model2.addElement("Titulo: " + misCanciones[i].getTitulo() + " // Duracion: " + misCanciones[i].getDuracion() + " // Estado: " + misCanciones[i].getEstado().toString());
			}
		}
	 }
	 
	 public void insertarComentario(Comentario nuevoComentario) {
			if(this.album != null) {
				album.anyadirComentario(nuevoComentario);
	            ((DefaultTreeModel)comentariosTree.getModel()).reload(root);
			}
	 }
	 
	 private class RowColor extends DefaultListCellRenderer{
		
			LocalDate fecha_actual = LocalDate.now();

		 
			private static final long serialVersionUID = 1L;

			public Component getListCellRendererComponent( JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus ) {
	            Component c = super.getListCellRendererComponent( list, value, index, isSelected, cellHasFocus );
	            	
	            	if(Sistema.sistema.getUsuarioActual() != null) {
	            		if(Sistema.sistema.getAdministrador() == true) {
	            			c.setBackground( Color.green);
		                    c.setForeground( Color.black );
	            		}else{
	    					Period intervalo = Period.between(Sistema.sistema.getUsuarioActual().getFechaNacimiento(), fecha_actual);
	    					if(intervalo.getYears() >= 18) {
	    						c.setBackground( Color.green);
			                    c.setForeground( Color.black );
	    					}else {
	    						if(misCanciones[index].getEstado() == EstadoCancion.VALIDA) {
	    		                    c.setBackground( Color.green);
	    		                    c.setForeground( Color.black );
	    		            	}else {
	    		            		c.setBackground( Color.red );
	    		                    c.setForeground( Color.black );
	    		            	}
	    					}
	            			
	            		}
	            	}else {
	            		if(misCanciones[index].getEstado() == EstadoCancion.VALIDA) {
		                    c.setBackground( Color.green);
		                    c.setForeground( Color.black );
		            	}else if(misCanciones[index].getEstado() == EstadoCancion.EXPLICITA){
		            		c.setBackground( Color.red);
		                    c.setForeground( Color.black );
		            	}
	            	}
	            	
	            	
	            
	            return c;
	        }
			
		}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Album getAlbum() {
		return album;
	}

	public JList<String> getLista_canciones() {
		return lista_canciones;
	}

	public JScrollPane getComentariosScrollPane() {
		return comentariosScrollPane;
	}

	public JScrollPane getCancionesScrollPane() {
		return cancionesScrollPane;
	}

	public JButton getBotonList() {
		return botonList;
	}

	public JButton getBotonAnyadirComentario() {
		return botonAnyadirComentario;
	}

	public JButton getEliminarCancion() {
		return eliminarCancion;
	}

	public JButton getAnyadirLista() {
		return anyadirLista;
	}

	public JButton getBotonPlay() {
		return botonPlay;
	}

	public JButton getBotonPause() {
		return botonPause;
	}

	public JLabel getDatos_album() {
		return datos_album;
	}

	public JLabel getTitulo_album() {
		return titulo_album;
	}

	public JLabel getAnyo_album() {
		return anyo_album;
	}

	public JLabel getAutor_album() {
		return autor_album;
	}

	public JLabel getDuracion_album() {
		return duracion_album;
	}

	public JLabel getComentarios_label() {
		return comentarios_label;
	}

	public Comentario[] getComentarios() {
		return comentarios;
	}

	public Cancion[] getMisCanciones() {
		return misCanciones;
	}

	public DefaultListModel<String> getModel2() {
		return model2;
	}

	public Dimension getScreenSize() {
		return screenSize;
	}

	public Comentario getComentarioSeleccionado() {
		return comentarioSeleccionado;
	}
	 
	 
}
