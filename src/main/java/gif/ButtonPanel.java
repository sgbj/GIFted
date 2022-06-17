
package gif;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;

import roundedButtonsWithImage.ButtonRoundedWithImage;
import simple.chooser.DemoJavaFxStage;
import utils.Metodos;

public class ButtonPanel extends javax.swing.JPanel {

	private static final long serialVersionUID = 1L;

	Config config;

	private static Animator animator;

	private javax.swing.JButton open;

	private ButtonRoundedWithImage save;

	String carpeta;

	LinkedList<File> lista;

	DemoJavaFxStage test;

	private JSeparator separator;

	private JSeparator separator_1;

	public static JCheckBox loop;

	public static String archivoGif;

	public static int archivos;

	private JLabel lblNewLabel;

	private JSeparator separator_2;

	private static boolean gif = false;

	static LinkedList<String> carpetasSeleccion = new LinkedList<String>();

	private ButtonRoundedWithImage btnConfig;

	private static String[] lectura;

	private JCheckBox tool;

	static String os = System.getProperty("os.name");

	static String separador = Metodos.saberSeparador(os);

	static String directorioActual;

	public static LinkedList<String> listaImagenes = new LinkedList<String>();

	public static LinkedList<String> listaImagenesInterlace = new LinkedList<String>();
	private JLabel lblNewLabel_1;
	private JCheckBox reverse;
	private JLabel lblNewLabel_2;

	public static LinkedList<String> getListaImagenesInterlace() {
		return listaImagenesInterlace;
	}

	public static String getSeparador() {
		return separador;
	}

	public static String getDirectorioActual() {
		return directorioActual;
	}

	public static String[] getLectura() {
		return ButtonPanel.lectura;
	}

	public static void setLectura(String[] lectura) {
		ButtonPanel.lectura = lectura;
	}

	public ButtonPanel(Animator animator) throws IOException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {

		ButtonPanel.animator = animator;

		initComponents();

	}

	private void initComponents() throws IOException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {

		lista = new LinkedList<File>();

		test = new DemoJavaFxStage();

		directorioActual = new File(".").getCanonicalPath() + separador;

		try {

			lectura = Metodos.leerFicheroArray("ConfigEasyGifCreator.txt", 19);

		}

		catch (Exception e) {

			config = new Config();

			config.guardarDatos();

		}

		ButtonRoundedWithImage open = new ButtonRoundedWithImage();

		open.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				try {

					lista = test.showOpenFileDialog(carpeta, false, "");

					addImages(false, lista);

					carpeta = lista.get(0).toString();

				}

				catch (Exception e1) {

				}

			}

		});

		open.setIcon(new ImageIcon(ButtonPanel.class.getResource("/images/abrir.png")));

		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		btnConfig = new ButtonRoundedWithImage();

		btnConfig.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				new Config().setVisible(true);
			}

		});

		btnConfig.setIcon(new ImageIcon(ButtonPanel.class.getResource("/images/settings.png")));

		btnConfig.setFont(new Font("Dialog", Font.PLAIN, 20));

		add(btnConfig);

		add(open);

		save = new ButtonRoundedWithImage();

		save.setIcon(new ImageIcon(ButtonPanel.class.getResource("/images/save.png")));

		save.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveActionPerformed(evt);
			}

		});

		ButtonRoundedWithImage open_1 = new ButtonRoundedWithImage();

		open_1.setIcon(new ImageIcon(ButtonPanel.class.getResource("/images/folder.png")));

		open_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				try {

					lista = test.showOpenFileDialog(carpeta, true, "");

					addImages(true, lista);

					carpeta = lista.get(0).toString();

				}

				catch (Exception e1) {

				}

			}

		});

		separator = new JSeparator();

		add(separator);

		add(open_1);

		separator_1 = new JSeparator();

		add(separator_1);

		add(save);

		separator_2 = new JSeparator();

		add(separator_2);

		lblNewLabel = new JLabel("");

		lblNewLabel.setIcon(new ImageIcon(ButtonPanel.class.getResource("/images/repeat.png")));

		add(lblNewLabel);

		loop = new JCheckBox();

		loop.setText(" Loop");

		loop.setSelected(true);

		loop.setFont(new Font("Tahoma", Font.PLAIN, 22));

		add(loop);

		lblNewLabel_1 = new JLabel("");

		lblNewLabel_1.setIcon(new ImageIcon(ButtonPanel.class.getResource("/images/utilities.png")));

		add(lblNewLabel_1);

		tool = new JCheckBox(" Tool");

		tool.setFont(new Font("Tahoma", Font.PLAIN, 22));

		add(tool);

		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(ButtonPanel.class.getResource("/images/reverse.png")));
		add(lblNewLabel_2);

		reverse = new JCheckBox("Reverse");
		reverse.setFont(new Font("Tahoma", Font.PLAIN, 22));
		add(reverse);

	}

	public static void addImages(boolean carpeta, LinkedList<File> files) {

		LinkedList<File> archivos = new LinkedList<File>();

		if (carpeta) {

			for (int i = 0; i < files.size(); i++) {

				carpetasSeleccion = Metodos.directorio(files.get(i).getAbsolutePath() + Animator.getSeparador(),
						"images", true, true);

				for (int y = 0; y < carpetasSeleccion.size(); y++) {

					archivos.add(new File(carpetasSeleccion.get(y)));

				}

			}

		}

		else {

			archivos = files;
		}

		try {

			for (int i = 0; i < archivos.size(); i++) {

				if (archivos.get(i).getName().endsWith("gif")) {

					List<GifFrame> frames = Gif.read(archivos.get(i));

					for (GifFrame frame : frames) {

						animator.addGifFrame(frame);
					}

				}

				else {

					BufferedImage image = ImageIO.read(archivos.get(i));

					animator.addGifFrame(new GifFrame(image, 500));

				}

			}

		}

		catch (Exception ex) {

		}
	}

	private void saveActionPerformed(java.awt.event.ActionEvent evt) {

		try {

			if (animator.getGifFrames().size() > 1) {

				JFileChooser fc = new JFileChooser();

				fc.setSelectedFile(new File("*.gif"));

				fc.setFileFilter(new FileFilter() {

					@Override
					public boolean accept(File f) {
						return f.getName().toLowerCase().endsWith(".gif");
					}

					@Override
					public String getDescription() {
						return "GIF (*.gif)";
					}

				});

				int o = fc.showSaveDialog(getParent());

				if (o == JFileChooser.APPROVE_OPTION) {

					File file = fc.getSelectedFile();

					Gif.write(animator.getGifFrames(), loop.isSelected(), file);

					if (tool.isSelected()) {

						gifsicle(file);

					}

					if (reverse.isSelected()) {

						String comando = "";

						if (os.contains("indows")) {

							comando = directorioActual + "gifsicle.exe ";

						}

						else {

							comando = "gifsicle ";

						}

						Runtime.getRuntime().exec(comando + file.toString() + " \"#-1-0\" > "
								+ file.toString().substring(0, file.toString().lastIndexOf(".")) + "_reverse.gif");

					}

				}

			}

		}

		catch (Exception e) {

		}

	}

	private void gifsicle(File file) {

		try {

			if (lectura.length == 19) {

				String comando = "";

				if (!lectura[11].equals("None")) {

					comando += " --" + lectura[11].toLowerCase();

					LinkedList<String> lista = new LinkedList<String>();

					if (lectura[11].equals("Merge")) {

						lista = listaImagenes;

					}

					else {

						lista = listaImagenesInterlace;

					}

					for (int i = 0; i < lista.size(); i++) {

						comando += " " + lista.get(i);

					}

				}

				if (Integer.parseInt(lectura[0]) > 1 && Integer.parseInt(lectura[0]) < 257) {

					comando += " --colors " + lectura[0];

				}

				if (Integer.parseInt(lectura[1]) > 0) {

					comando += " --optimize=" + lectura[1];

				}

				Animator.saberSize();

				String escala = lectura[2];

				String tam = GifFramePanel.recorte.getText();

				int ancho = 0;

				int alto = 0;

				int wancho;

				int halto;

				int proporcion;

				if (escala.contains("%") && !tam.isEmpty()) {

					try {

						proporcion = Integer.parseInt(escala.substring(0, escala.indexOf("%")));

						if (proporcion != 100) {

							ancho = Integer.parseInt(tam.substring(0, tam.indexOf("x")).trim());

							alto = Integer.parseInt(tam.substring(tam.indexOf("x") + 1, tam.length()).trim());

							if (proporcion < 100) {

								wancho = (proporcion * 2) / 50;

								halto = (proporcion * 2) / 50;

								ancho /= wancho;

								alto /= halto;

							}

							else {

								wancho = (proporcion * 2) / 200;

								halto = (proporcion * 2) / 200;

								ancho *= wancho;

								alto *= halto;

							}

						}

						else {

							escala = tam;

						}

						escala = ancho + "x" + alto;

					}

					catch (Exception e) {

					}

					comando += " --resize " + escala;

				}

				if (escala.contains("x")) {
					comando += " --resize " + escala;
				}

				else {

					try {

						if (Float.parseFloat(escala) > 0f) {
							comando += " --scale " + escala;
						}

					}

					catch (Exception e) {

					}

				}

				if (Integer.parseInt(lectura[3]) > 0) {

					comando += " --lossy=" + lectura[3];

				}

				if (Integer.parseInt(lectura[4]) > 0) {

					comando += " --delay " + lectura[4];

				}

				if (Integer.parseInt(lectura[5]) > 0) {

					comando += " --loopcount=" + lectura[5];

				}

				if (Integer.parseInt(lectura[6]) >= 0 && Integer.parseInt(lectura[7]) >= 0) {

					int w = Integer.parseInt(lectura[6]);

					int h = Integer.parseInt(lectura[7]);

					if (lectura[8].equals("1")) {

						if (w > 0 && h == 0) {
							comando += " --resize-fit-width " + w;
						}

						if (h > 0 && w == 0) {
							comando += " --resize-fit-height " + h;
						}

						if (w > 0 && h > 0) {
							comando += " --resize-fit " + w + "x" + h;
						}

					}

					if (lectura[9].equals("1")) {

						if (w > 0 && h == 0) {
							comando += " --resize-touch-width " + w;
						}

						if (h > 0 && w == 0) {
							comando += " --resize-touch-height " + h;
						}

						if (w > 0 && h > 0) {
							comando += " --resize-touch " + w + "x" + h;
						}

					}

					if (lectura[8].equals("0") && lectura[9].equals("0")) {

						if (w > 0 && h == 0) {
							comando += " --resize-width " + w;
						}

						if (h > 0 && w == 0) {
							comando += " --resize-height " + h;
						}

						if (w > 0 && h > 0) {
							comando += " --resize " + w + "x" + h;
						}

					}

				}

				if (!lectura[10].equals("None")) {

					comando += " --rotate-" + lectura[10];

				}

				if (lectura[12].equals("1")) {

					comando += " --flip-" + lectura[13];

				}

				if (!lectura[14].equals("None")) {

					String opcion = "";

					switch (lectura[14]) {

					case "Black And White":

						opcion = "bw";

						break;

					case "Gray":

						opcion = "gray";

						break;

					case "Web":

						opcion = "web";

						break;

					}

					comando += " --use-colormap " + opcion;

				}

				if (lectura[15].equals("1") && Metodos.saberNumero(lectura[17]) >= 0
						&& Metodos.saberNumero(lectura[18]) >= 0) {

					comando += " --crop " + lectura[17] + "," + lectura[18] + "+" + lectura[16];

				}

				comando += " -i " + file.toString() + " -o " + file.toString();

				if (os.contains("indows")) {

					comando = directorioActual + "gifsicle.exe -O3" + comando;

				}

				else {

					comando = "gifsicle -O3" + comando;

				}

				Runtime.getRuntime().exec(comando);

			}

		}

		catch (Exception e) {

		}

	}

}
