package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import modelo.contenido.Album;
import modelo.sistema.Sistema;
import vista.CrearAlbum;
import vista.InicioSesion;
import vista.Ventana;

public class ControladorCrearAlbum implements ActionListener{
	
	private CrearAlbum vista;
	private int modelo;
	
	public ControladorCrearAlbum(CrearAlbum vista, int modelo) {
		this.vista = vista;
		this.modelo = modelo;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) { //CAMBIADO, MEJORADO
		if (((JButton)e.getSource()).getText() == "Crear") {
			
			String titulo = Ventana.ventana.crearAlbum.getTitulo().getText();
			int anyo_final = Integer.parseInt(Ventana.ventana.crearAlbum.getAnyo().getText());
			Album a;
			if((a = Sistema.sistema.crearAlbum(anyo_final, titulo)) != null) {
				JOptionPane.showMessageDialog(Ventana.ventana,"El album " + a.getTitulo() + " se ha creado correctamente");
				Ventana.ventana.showPantallaInicio();
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"El album no se ha creado correctamente");
				Ventana.ventana.showPantallaInicio();
			}
			
		} else if(((JButton)e.getSource()).getText() == "Cancelar") {
			Ventana.ventana.showPantallaInicio();
		}
	}

}
