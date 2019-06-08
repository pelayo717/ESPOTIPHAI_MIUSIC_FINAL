
package ESPOTIPHAI_MIUSIC_FINAL.Grafic;


import java.awt.event.*;
import javax.swing.JButton;

import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.Sistema;

public class ControladorPantallaInicio implements ActionListener{
	private PantallaInicio vista;
	private int modelo;

public ControladorPantallaInicio(PantallaInicio x, int modelo) {
	this.modelo = modelo;
	this.vista = x;
}

@Override
	public void actionPerformed(ActionEvent e) {
		if(((JButton)e.getSource()).getText() == "Elegir cancion") {
			Ventana.ventana.showReproducirCancion();
		}  else if(((JButton)e.getSource()).getText() == "Ver Perfil") {
			Ventana.ventana.showPerfil();
			Ventana.ventana.perfil.setInformacion(Sistema.sistema.getUsuarioActual());
		} else if(((JButton)e.getSource()).getText() == "Elegir album") {
			Ventana.ventana.showReproducirAlbum();
		} else if(((JButton)e.getSource()).getText() == "Iniciar Sesion") {
			Ventana.ventana.showInicioSesion();
		} else if(((JButton)e.getSource()).getText() == "Registro") {
			Ventana.ventana.showRegistrarse();
		} else if(((JButton)e.getSource()).getText() == "Buscar") {
			System.out.println("buscar");
		} else if(((JButton)e.getSource()).getText() == "Limpiar Buscador") {
			vista.limpiarBuscador();
		} else {
			System.out.println(e.getSource());
		}
	}
	
}
