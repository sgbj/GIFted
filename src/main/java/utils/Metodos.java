package utils;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;

public class Metodos {

	public static String saberSeparador(String os) {
		if (os.equals("Linux")) {
			return "/";
		} else {
			return "\\";
		}
	}

	public static String eliminarEspacios(String cadena) {

		cadena = cadena.trim();

		cadena = cadena.replace("  ", " ");

		cadena = cadena.trim();

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
		} catch (Exception e) {

		}

		return cadena;
	}

	public static void renombrar(String ruta1, String ruta2) {

		File f1 = new File(ruta1);

		File f2 = new File(ruta2);

		f1.renameTo(f2);

	}

	public static LinkedList<String> directorio(String ruta, String extension, boolean filtro, boolean carpeta,
			boolean mover) {

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

		Collections.sort(lista);

		return lista;

	}

}
