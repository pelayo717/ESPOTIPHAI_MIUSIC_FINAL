package ESPOTIPHAI_MIUSIC_FINAL.Grafic;

import java.awt.*;
import javax.swing.*;

public class PantallaPrincipal extends JPanel{
	public JButton botonIzquierdaArriba;
	public JButton botonIzquierdaAbajo;
	JTextField busquedaTextfield;
	JButton botonBuscar;
	JButton botonLimpiarBuscador;
	JRadioButton opcion1;
	JRadioButton opcion2;
	JRadioButton opcion3;
	ButtonGroup grupo_eleccion;
	public PantallaPrincipal() {
		
		this.setBackground(new Color(40,159,211));
		this.botonIzquierdaArriba = new JButton("Iniciar Sesion");
		this.botonIzquierdaAbajo = new JButton("Registro");
		JLabel titulo = new JLabel("ESPOTIPHAIMUSIC", SwingConstants.CENTER);
		this.busquedaTextfield = new JTextField(10);
		this.botonBuscar = new JButton("Buscar");
		this.botonLimpiarBuscador = new JButton("Limpiar Buscador");
		
		this.opcion1 = new JRadioButton("Titulo");
		this.opcion2 = new JRadioButton("Album");
		this.opcion3 = new JRadioButton("Autor");
		
		this.grupo_eleccion = new ButtonGroup();
		grupo_eleccion.add(opcion1);
		grupo_eleccion.add(opcion2);
		grupo_eleccion.add(opcion3);
		
		//Style changes
		Font tituloFont = new Font(titulo.getFont().getName(),Font.BOLD,22);

		titulo.setFont(tituloFont);
		titulo.setForeground(Color.yellow);

		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		//Manual Constraints
		//x axis, y axis, width, height  
		botonIzquierdaArriba.setBounds(10, 10, 150, 30);
		botonIzquierdaAbajo.setBounds(10, 50, 150, 30);
		titulo.setBounds(screenSize.width/2 - 300, 20, 600, 30);
		busquedaTextfield.setBounds(screenSize.width/2 - 250,90,500, 40);
		botonBuscar.setBounds(screenSize.width/2 + 270, 90, 150, 30);
		botonLimpiarBuscador.setBounds(screenSize.width/2 + 270,130,150, 30);
		opcion1.setBounds(screenSize.width/2 - 180, 130, 80, 30);
		opcion2.setBounds(screenSize.width/2 - 40, 130, 80, 30);
		opcion3.setBounds(screenSize.width/2 + 100, 130, 80, 30);


		
		//We add all the components
		this.add(botonIzquierdaArriba);
		this.add(botonIzquierdaAbajo);
		this.add(titulo);
		this.add(busquedaTextfield);
		this.add(botonBuscar);
		this.add(botonLimpiarBuscador);
		this.add(opcion1);
		this.add(opcion2);
		this.add(opcion3);
		
	}
	
	

}
