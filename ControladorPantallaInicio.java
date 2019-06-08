import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ControladorPantallaInicio implements ActionListener{
	private PantallaInicio vista;
	
public ControladorPantallaInicio(PantallaInicio x) {
	
	this.vista = x;
}

@Override
	public void actionPerformed(ActionEvent e) {
		if(((JButton)e.getSource()).getText() == "Elegir cancion") {
			Ventana.ventana.showReproducirCancion();
		}
		else if(((JButton)e.getSource()).getText() == "Elegir album") {
			Ventana.ventana.showReproducirAlbum();
		}
		else if(((JButton)e.getSource()).getText() == "Iniciar Sesion") {
			Ventana.ventana.showInicioSesion();
		}
		else if(((JButton)e.getSource()).getText() == "Registro") {
			Ventana.ventana.showRegistrarse();
		}
		else if(((JButton)e.getSource()).getText() == "Buscar") {
			System.out.println("buscar");
		}
		else if(((JButton)e.getSource()).getText() == "Limpiar Buscador") {
			vista.limpiarBuscador();
		}
		else {
			System.out.println(e.getSource());
		}
	}
	
}
