package ESPOTIPHAI_MIUSIC_FINAL.Grafic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.Sistema;

public class ControladorInicioSesion implements ActionListener{
		private InicioSesion vista;
		private int modelo;
		
		
		public ControladorInicioSesion(InicioSesion vista, int modelo) {
			this.vista = vista;
			this.modelo = modelo;
		}
	 
		@Override
		public void actionPerformed(ActionEvent e) {
			if (((JButton)e.getSource()).getText() == "Inicio") {
				Ventana.ventana.showReproducirCancion();
				Ventana.ventana.reproducirCancion.botonIzquierdaArriba.setText("Ver Perfil");
				Ventana.ventana.reproducirCancion.botonIzquierdaAbajo.setVisible(false);
			} else if(((JButton)e.getSource()).getText() == "Registrarse") {
				Ventana.ventana.showRegistrarse();
			} else if(((JButton)e.getSource()).getText() == "Iniciar Sesion") {
				Sistema.sistema.iniciarSesion(vista.usuarioTextfield.getText(), vista.passwordTextfield.getText());
			}
		}
}
