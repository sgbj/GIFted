package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import gif.Animator;
import gif.ButtonPanel;
import gif.GifFrame;
import gif.GifFramePanel;

public class Utilidades {

	public static float saberNumero(String cadena) {

		float resultado = 0f;

		try {

			resultado = Float.parseFloat(cadena.trim());

		}

		catch (Exception e) {

		}

		return resultado;

	}

	public static void ponerGif() {

		try {

			File dir = new File(GifFramePanel.ruta + "output" + Animator.getSeparador() + GifFramePanel.nombreGif);

			int x = GifFramePanel.indice;

			for (int i = 0; i < dir.listFiles().length; i++) {

				Animator.lista.m.add(x, new GifFrame(ImageIO.read(dir.listFiles()[i]), 500));

				x++;

			}

		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String[] leerFicheroArray(String fichero, int longitud) throws IOException {

		String[] salida = new String[longitud];

		fichero = ButtonPanel.getDirectorioActual() + fichero;

		File archivo = new File(fichero);

		if (archivo.exists()) {

			String texto = "";

			int i = 0;

			FileReader flE = null;

			BufferedReader fE = null;

			try {

				flE = new FileReader(fichero);

				fE = new BufferedReader(flE);

				texto = fE.readLine();

				while (texto != null && i < longitud) {

					salida[i] = texto;

					i++;

					texto = fE.readLine();

				}

				fE.close();

				flE.close();

			}

			catch (Exception e) {
				//
			}

			finally {

				if (fE != null) {

					try {
						fE.close();
					}

					catch (IOException e) {
						//
					}

				}

				if (flE != null) {

					try {
						flE.close();
					}

					catch (IOException e) {
						//
					}

				}
			}
		}

		else {

			throw new IOException();

		}

		return salida;

	}

	public static void abrirCarpeta(String ruta) throws IOException {

		if (ruta != null && !ruta.equals("") && !ruta.isEmpty()) {

			try {

				if (System.getProperty("os.name").contains("indows")) {

					Runtime.getRuntime().exec("cmd /c C:\\Windows\\explorer.exe " + "\"" + ruta + "\"");

				}

				if (System.getProperty("os.name").contains("inux")) {

					Runtime.getRuntime().exec("xdg-open " + ruta);

				}

				if (System.getProperty("os.name").contains("ac")) {

					Runtime.getRuntime().exec("open " + ruta);

				}

			}

			catch (IOException e) {

			}

		}

	}

	public static String saberSeparador(String os) {

		if (os.equals("Linux")) {

			return "/";

		}

		else {

			return "\\";

		}

	}

	public static String eliminarEspacios(String cadena, boolean filtro) {

		cadena = cadena.trim();

		cadena = cadena.replace("  ", " ");

		cadena = cadena.trim();

		if (filtro) {
			cadena = cadena.replace(" ", "");
		}

		return cadena;

	}

	public static String extraerExtension(String nombreArchivo) {

		String extension = "";

		if (nombreArchivo.length() >= 3) {

			extension = nombreArchivo.substring(nombreArchivo.length() - 3, nombreArchivo.length());

			extension = extension.toLowerCase();

			if (extension.equals("peg")) {
				extension = "jpeg";
			}

			if (extension.equals("fif")) {
				extension = "jfif";
			}

			if (extension.equals("ebp")) {
				extension = "webp";
			}

			if (extension.equals("ebm")) {
				extension = "webm";
			}

			if (extension.equals("3u8")) {
				extension = "m3u8";
			}

			if (extension.equals(".ts")) {
				extension = "ts";
			}

		}

		return extension;
	}

	public static String eliminarPuntos(String cadena) {

		String cadena2 = cadena;

		try {

			cadena2 = cadena.substring(0, cadena.length() - 4);

			cadena = cadena2.replace(".", "_") + "." + extraerExtension(cadena);
		}

		catch (Exception e) {

		}

		return cadena;
	}

	public static void renombrar(String ruta1, String ruta2) {

		File f1 = new File(ruta1);

		File f2 = new File(ruta2);

		f1.renameTo(f2);

	}

	public static void eliminarFichero(String archivo) {

		File fichero = new File(archivo);

		if (fichero.exists() && !fichero.isDirectory()) {
			fichero.delete();
		}

	}

	public static void eliminarArchivos(String ruta) {

		LinkedList<String> frames = directorio(ruta, ".", true, false);

		for (int i = 0; i < frames.size(); i++) {

			if (!frames.get(i).isEmpty()) {

				eliminarFichero(ruta + frames.get(i));
			}

		}

	}

	public static LinkedList<String> directorio(String ruta, String extension, boolean filtro, boolean carpeta) {

		LinkedList<String> lista = new LinkedList<String>();

		try {

			File f = new File(ruta);

			if (f.exists()) {

				File[] ficheros = f.listFiles();

				String fichero = "";

				String extensionArchivo;

				File folder;

				for (int x = 0; x < ficheros.length; x++) {

					fichero = ficheros[x].getName();

					folder = new File(ruta + fichero);

					extensionArchivo = extraerExtension(fichero);

					if (filtro) {

						if (folder.isFile()) {

							if (fichero.length() > 5 && fichero.substring(0, fichero.length() - 5).contains(".")) {

								renombrar(ruta + fichero, ruta + eliminarPuntos(fichero));

							}

							if ((extension.equals("images")
									&& (extensionArchivo.equals("jpg") || extensionArchivo.equals("png")
											|| extensionArchivo.equals("gif") || extensionArchivo.equals("jpeg")))
									|| (extension.equals("webp") && extensionArchivo.equals("webp")
											|| extension.equals("jpeg") && extensionArchivo.equals("jpeg")
											|| extension.equals(".") || extension.equals(extensionArchivo))) {

								if (carpeta) {
									lista.add(ruta + fichero);
								}

								else {
									lista.add(fichero);
								}

							}

						}

					}

					else {

						if (carpeta) {
							lista.add(ruta + fichero);
						}

						else {

							if (folder.isDirectory()) {

								if (!fichero.isEmpty()) {
									lista.add(fichero);
								}

							}

						}

					}

				}

			}
		}

		catch (Exception e) {

		}

		return lista;

	}

}
