package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import modelo.contenido.Album;
import modelo.contenido.Cancion;
import modelo.contenido.Contenido;
import modelo.sistema.Sistema;
import pads.musicPlayer.exceptions.Mp3PlayerException;
import vista.PantallaInicio;
import vista.PantallaInicioAdmin;
import vista.Ventana;

public class ControladorPantallaInicioAdmin implements ActionListener{
	private PantallaInicioAdmin vista;
	private int modelo;

	public ControladorPantallaInicioAdmin(PantallaInicioAdmin x, int modelo) {
		this.modelo = modelo;
		this.vista = x;
	}
	
	public void actionPerformed(ActionEvent e) {
		
	}


}
