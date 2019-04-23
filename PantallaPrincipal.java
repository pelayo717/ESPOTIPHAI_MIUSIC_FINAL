package ESPOTIPHAI_MIUSIC_FINAL;

import java.awt.*;
import javax.swing.*;

public class PantallaPrincipal extends JPanel{

	public PantallaPrincipal() {
		
		this.setBackground(new Color(40,159,211));
		JButton botonIzquierdaArriba = new JButton("Iniciar Sesion");
		JButton botonIzquierdaAbajo = new JButton("Registro");
		JLabel titulo = new JLabel("ESPOTIPHAIMUSIC", SwingConstants.CENTER);
		JTextField busquedaTextfield = new JTextField(10);
		JButton botonBuscar = new JButton("Buscar");
		JButton botonLimpiarBuscador = new JButton("Limpiar Buscador");
		
		JRadioButton opcion1 = new JRadioButton("Titulo");
		JRadioButton opcion2 = new JRadioButton("Album");
		JRadioButton opcion3 = new JRadioButton("Autor");
		
		ButtonGroup grupo_eleccion = new ButtonGroup();
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
		opcion1.setBounds(270, 130, 75, 30);
		opcion2.setBounds(370, 130, 75, 30);
		opcion3.setBounds(470, 130, 75, 30);


		
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
