package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CrearAlbum extends JPanel{
	
	JTextField tituloText;
	JTextField anyoText;
	JButton crear;
	JButton cancelar;
	
	
	public CrearAlbum() { //CAMBIADO, MEJORADO
		
		//this.setBackground(new Color(40,159,211));
		this.tituloText = new JTextField(10);
		this.anyoText = new JTextField(10);
		this.crear = new JButton("Crear");
		this.cancelar = new JButton("Cancelar");
		
		JLabel titulo = new JLabel("Titulo del album",  SwingConstants.CENTER);
		JLabel anyo = new JLabel("Año del album",  SwingConstants.CENTER);


		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		//Manual Constraints
		//x axis, y axis, width, height  
		
		Font tituloFont = new Font(titulo.getFont().getName(), Font.BOLD, 16);
		Font anyoFont = new Font(anyo.getFont().getName(), Font.BOLD, 16);
		
		
		anyo.setBounds(screenSize.width/2 - 50, 70, 100, 30);
		anyoText.setBounds(screenSize.width/2 - 250,120,500, 40);
		
		titulo.setBounds(screenSize.width/2 - 100, 180, 200, 30);
		tituloText.setBounds(screenSize.width/2 - 250,230,500, 40);

		
		crear.setBounds(screenSize.width/2 - 170, 300, 150, 30);
		cancelar.setBounds(screenSize.width/2 + 20,300,150, 30);


		
		//We add all the components
		this.add(titulo);
		this.add(anyo);
		this.add(tituloText);
		this.add(anyoText);
		this.add(crear);
		this.add(cancelar);
		this.setBounds(screenSize.width/2, screenSize.height/2, 500, 400);
		
	}
	
	public void setControlador(ActionListener c) {
		 this.crear.addActionListener(c);
		 this.cancelar.addActionListener(c);
	}
	
	public JTextField getTitulo() {
		return this.tituloText;
	}
	
	public JTextField getAnyo() {
		return this.anyoText;
	}
}
