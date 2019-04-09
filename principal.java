

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;  


public class principal {  
	
	public static void main(String[] args) {
		JFrame ventana = new JFrame();
		Container contenedor = ventana.getContentPane();
		contenedor.setLayout(new CardLayout());

		JTextField campo = new JTextField(10);
		JButton boton = new JButton();
		JPanel inicioSesion = new JPanel();
		JPanel registrarse = new JPanel();
		
		contenedor.add(inicioSesion,"IniciarSesion");
		contenedor.add(registrarse, "Registrarse");
		
		boton.setBounds(350, 0, 100, 20);
		campo.setBounds(130,100,100, 40);//x axis, y axis, width, height  

			
		inicioSesion.add(campo);
		inicioSesion.add(boton);
		
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setSize(800,800);
		ventana.setVisible(true);
		
		
	}
	
	
}  




/*
JFrame frame = new JFrame();//creating instance of JFrame  
Container container = frame.getContentPane();
container.setLayout(new FlowLayout());

// crear componentes
JLabel etiqueta = new JLabel("Nombre");
final JTextField campo = new JTextField(10);
JButton boton = new JButton("Haz click");
// asociar acciones a componentes
boton.addActionListener(
		new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, campo.getText());
			}
		}
);


// añadir componentes al contenedor
container.add(etiqueta);
container.add(campo);
container.add(boton);
// mostrar ventana
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.setSize(100,140);
frame.setVisible(true);

JButton b = new JButton("click");//creating instance of JButton  
b.setBounds(130,100,100, 40);//x axis, y axis, width, height  
  
frame.add(b);//adding button in JFrame  
  
frame.setSize(400,500);//400 width and 500 height  
frame.setLayout(null);//using no layout managers  
frame.setVisible(true);//making the frame visible  */
