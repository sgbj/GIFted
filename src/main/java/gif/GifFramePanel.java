
package gif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collections;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;

import ComboFonts.ComboFont;
import button.SimpleButton;
import button.SimpleButton.Form;
import checkbox.JCheckBoxCustom;
import drag_and_drop.DragAndDrop;
import drag_and_drop.UtilDragAndDrop;
import radio_button.RadioButtonCustom;
import roundedButtonsWithImage.ButtonRoundedWithImage;
import spinner.Spinner;
import textfield.TextField;
import util.Metodos;
import utils.Utilidades;

public class GifFramePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private Animator animator;

	static LinkedList<String> listaArchivos = new LinkedList<String>();

	private JPanel imagePanel;

	private JScrollPane imageScrollPane;

	public static Spinner fps;

	private TextField texto;

	public static TextField recorte;

	public static String sizeImage;

	private RadioButtonCustom actual;

	private RadioButtonCustom todas;

	public static String ruta;

	public static String nombreGif;

	public static int indice;

	private int indiceFrame;

	public static ComboFont fuente;

	private JLabel lblNewLabel_2;

	public static Spinner sizeFont;

	private JLabel lblNewLabel_3;

	public static DragAndDrop imagen;

	private Object frameAnterior;

	private ButtonRoundedWithImage btnrndwthmgCrop_1;

	public static JCheckBoxCustom center;

	public static Spinner centerSpace;

	public GifFramePanel(Animator animator) {

		setBackground(Color.WHITE);

		this.animator = animator;

		initComponents();

	}

	public static void loadGifImage(String path) {

		try {

			actualizarRuta();

			GifDef.mFrameImageList.removeAllElements();

			GifDecoder d = new GifDecoder();

			d.read(path);

			int n = d.getFrameCount();

			for (int i = 0; i < n; i++) {

				BufferedImage frame = d.getFrame(i);

				d.getDelay(i);

				if (frame != null) {

					GifDef.mFrameImageList.add(frame);
				}

			}

			if (!GifDef.mFrameImageList.isEmpty()) {

				@SuppressWarnings("unused")

				BufferedImage frame = GifDef.mFrameImageList.elementAt(0);

			}

		}

		catch (Exception e) {

		}

	}

	private void borrarDerecha() {

		try {

			indice = Animator.lista.getSelectedIndex();

			int i = indice;

			int y = i;

			y++;

			for (i++; i < Animator.lista.m.size(); i++) {

				Animator.lista.m.remove(y);

			}

			Animator.lista.m.remove(y);
		}

		catch (Exception e) {

		}

	}

	private void borrarIzquierda() {

		indice = Animator.lista.getSelectedIndex();

		if (indice > 0) {

			for (int i = 0; i < indice; i++) {

				Animator.lista.m.remove(0);

			}

		}

	}

	public static void actualizarRuta() {

		ruta = ButtonPanel.archivoGif.substring(0, ButtonPanel.archivoGif.lastIndexOf(Animator.getSeparador()) + 1);

		nombreGif = ButtonPanel.archivoGif.substring(ButtonPanel.archivoGif.lastIndexOf(Animator.getSeparador()) + 1,
				ButtonPanel.archivoGif.length() - 4);

	}

	public static void extraerFrames() {

		try {

			File carpeta = new File(ruta + "output");

			carpeta.mkdir();

			carpeta = new File(ruta + "output" + Animator.getSeparador() + nombreGif);

			carpeta.mkdir();

			carpeta = new File(ruta + "Resized");

			carpeta.mkdir();

			carpeta = new File(ruta + "Resized" + Animator.getSeparador() + nombreGif);

			carpeta.mkdir();

			int size = GifDef.mFrameImageList.size();

			BufferedImage frame = GifDef.mFrameImageList.elementAt(0);

			int ancho = frame.getWidth();

			int alto = frame.getHeight();

			File pngfile;

			Graphics gg;

			BufferedImage bfimg;

			String imagen = "";

			for (int i = 0; i < size; i++) {

				frame = GifDef.mFrameImageList.elementAt(i);

				bfimg = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);

				gg = bfimg.getGraphics();

				gg.drawImage(frame, 0, 0, null);

				imagen = ruta + "output" + Animator.getSeparador() + nombreGif + Animator.getSeparador()
						+ ButtonPanel.archivoGif.substring(
								ButtonPanel.archivoGif.lastIndexOf(Animator.getSeparador()) + 1,
								ButtonPanel.archivoGif.length() - 4)
						+ "_" + i + ".png";

				pngfile = new File(imagen);

				ImageIO.write(bfimg, "png", pngfile);

				listaArchivos.add(imagen);

			}

			Utilidades.ponerGif();

		}

		catch (Exception e) {

			e.printStackTrace();

		}

	}

	private void initComponents() {

		centerSpace = new Spinner();

		sizeFont = new Spinner();

		sizeFont.setValor(40);

		fuente = new ComboFont();

		imagen = new DragAndDrop("", "");

		imagen.setHorizontalAlignment(SwingConstants.CENTER);

		imagen.setBackground(Color.WHITE);

		imageScrollPane = new JScrollPane();

		imagePanel = new JPanel();

		imagePanel.setLayout(new java.awt.BorderLayout());

		imageScrollPane.setViewportView(imagePanel);

		fps = new Spinner();

		fps.setMinValor(1);

		fps.setLabelText("Delay");

		fps.getEditor().setFont(new Font("Dialog", Font.PLAIN, 16));

		fps.getEditor().addKeyListener(new KeyAdapter() {

			@Override

			public void keyReleased(KeyEvent e) {

				try {

					fps.ponerFiltro();

				} catch (Exception e1) {

				}

			}

		});

		texto = new TextField();
		texto.setLabelText("String To Frame");
		texto.setBackground(Color.WHITE);

		texto.setHorizontalAlignment(SwingConstants.CENTER);

		texto.setFont(new Font("Dialog", Font.PLAIN, 16));

		texto.setColumns(10);

		ButtonRoundedWithImage btnNewButton = new ButtonRoundedWithImage("Add Frame");
		btnNewButton.setText("Add");

		btnNewButton.setForeground(Color.BLACK);

		btnNewButton.setBackground(Color.WHITE);

		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));

		btnNewButton.setIcon(null);

		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				String dato = texto.getText().trim();

				dato = Utilidades.eliminarEspacios(dato, false);

				if (!dato.isEmpty()) {

					int ancho = 46;

					int alto = 46;

					try {

						ancho = Integer.parseInt(Metodos.eliminarEspacios(
								recorte.getText().substring(0, recorte.getText().indexOf("x")), true));

						alto = Integer.parseInt(Metodos.eliminarEspacios(recorte.getText()
								.substring(recorte.getText().indexOf("x") + 1, recorte.getText().length()), true));

						if (ancho <= 0 || alto <= 0) {

							ancho = 46;

							alto = 46;

						}

					}

					catch (Exception e) {

					}

					GifFrame[] frames = new GifFrame[] { Animator.createDemoGifFrame(ancho, alto, dato, 200) };

					Animator.animator.addGifFrame(frames[0]);

				}

			}

		});

		recorte = new TextField();

		recorte.setLabelText("Width x Height");

		recorte.setFont(new Font("Tahoma", Font.PLAIN, 16));

		recorte.setHorizontalAlignment(SwingConstants.CENTER);

		recorte.setColumns(10);

		actual = new RadioButtonCustom("Current Frame");

		actual.setBackground(new Color(19, 146, 57));

		actual.setFont(new Font("Tahoma", Font.PLAIN, 16));

		todas = new RadioButtonCustom("All Frames");

		todas.setBackground(new Color(19, 146, 57));

		todas.setSelected(true);

		todas.setFont(new Font("Tahoma", Font.PLAIN, 16));

		ButtonGroup bg1 = new ButtonGroup();

		bg1.add(actual);

		bg1.add(todas);

		lblNewLabel_2 = new JLabel("");

		lblNewLabel_2.setIcon(new ImageIcon(GifFramePanel.class.getResource("/images/multiple.png")));

		lblNewLabel_3 = new JLabel("");

		lblNewLabel_3.setIcon(new ImageIcon(GifFramePanel.class.getResource("/images/simple.png")));

		SimpleButton btnCreateGif = new SimpleButton("Create GIF");
		btnCreateGif.setText("Run");

		btnCreateGif.setColors(Color.BLACK, Color.WHITE, Color.WHITE, Color.WHITE);

		btnCreateGif.setFont(new Font("Tahoma", Font.PLAIN, 18));

		btnCreateGif.setForm(Form.ROUNDED_RECTANGLE);

		btnCreateGif.setIcon(new ImageIcon(GifFramePanel.class.getResource("/images/save.png")));

		btnCreateGif.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

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

							Gif.write(animator.getGifFrames(), ButtonPanel.loop.isSelected(),
									ButtonPanel.reverse.isSelected(), file);

							if (ButtonPanel.tool.isSelected()) {

								ButtonPanel.gifsicle(file);

							}

							if (ButtonPanel.reverse.isSelected()) {

								String comando = "";

								if (ButtonPanel.os.contains("indows")) {

									comando = ButtonPanel.directorioActual + "gifsicle.exe ";

								}

								else {

									comando = "gifsicle ";

								}

								Runtime.getRuntime()
										.exec(comando + file.toString() + " \"#-1-0\" > "
												+ file.toString().substring(0, file.toString().lastIndexOf("."))
												+ "_reverse.gif");

							}

						}

					}

				}

				catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		btnrndwthmgCrop_1 = new ButtonRoundedWithImage("Add Frame");

		btnrndwthmgCrop_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				try {

					int ancho = Integer.parseInt(Metodos
							.eliminarEspacios(recorte.getText().substring(0, recorte.getText().indexOf("x")), true));

					int alto = Integer.parseInt(Metodos.eliminarEspacios(
							recorte.getText().substring(recorte.getText().indexOf("x") + 1, recorte.getText().length()),
							true));

					if (todas.isSelected()) {

						for (int i = 0; i < listaArchivos.size(); i++) {

							Metodos.resizeImage(listaArchivos.get(i), ruta + "Resized" + Animator.getSeparador()
									+ nombreGif + Animator.getSeparador() + nombreGif + "_" + i + ".png", ancho, alto);

						}

					}

					else {

						int indiceFrame = Animator.lista.getSelectedIndex();

						Metodos.resizeImage(
								listaArchivos.get(indiceFrame), ruta + "Resized" + Animator.getSeparador() + nombreGif
										+ Animator.getSeparador() + nombreGif + "_" + indiceFrame + ".png",
								ancho, alto);

					}

				}

				catch (Exception e1) {
					e1.printStackTrace();
				}

			}

		});

		btnrndwthmgCrop_1.setIcon(new ImageIcon(GifFramePanel.class.getResource("/images/cut.png")));

		btnrndwthmgCrop_1.setText("Resize Frame/s");

		btnrndwthmgCrop_1.setForeground(Color.BLACK);

		btnrndwthmgCrop_1.setFont(new Font("Tahoma", Font.PLAIN, 16));

		btnrndwthmgCrop_1.setBackground(Color.WHITE);

		ButtonRoundedWithImage btnNewButton_1 = new ButtonRoundedWithImage("< Back Frame");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 14));

		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				indiceFrame = Animator.lista.getSelectedIndex();

				if (indiceFrame > 0) {

					indiceFrame--;

					frameAnterior = Animator.lista.m.get(indiceFrame);

					Animator.lista.m.set(indiceFrame, Animator.lista.m.get(++indiceFrame));

					Animator.lista.m.set(indiceFrame, frameAnterior);

					indiceFrame--;

				}

				Animator.lista.setSelectedIndex(indiceFrame);

			}
		});

		ButtonRoundedWithImage btnNewButton_2 = new ButtonRoundedWithImage("> Next Frame");

		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 14));

		btnNewButton_2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				indiceFrame = Animator.lista.getSelectedIndex();

				if (indiceFrame < Animator.lista.m.size() - 1) {

					indiceFrame++;

					frameAnterior = Animator.lista.m.get(indiceFrame);

					Animator.lista.m.set(indiceFrame, Animator.lista.m.get(--indiceFrame));

					Animator.lista.m.set(indiceFrame, frameAnterior);

				}

				indiceFrame++;

				Animator.lista.setSelectedIndex(indiceFrame);

			}

		});

		ButtonRoundedWithImage btnNewButton_1_1 = new ButtonRoundedWithImage("Duplicate Frame");

		btnNewButton_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));

		btnNewButton_1_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				try {

					indice = Animator.lista.getSelectedIndex();

					if (indice < 0 && !Animator.lista.m.isEmpty()) {

						indice = 0;

					}

					Animator.animator.addGifFrame(Animator.lista.getGifFrames().get(indice));

				}

				catch (Exception e1) {

				}

			}

		});

		sizeFont.setMinValor(1);

		sizeFont.setLabelText("Size");

		sizeFont.getEditor().setFont(new Font("Dialog", Font.PLAIN, 14));

		fuente.setFont(new Font("Tahoma", Font.PLAIN, 16));

		center = new JCheckBoxCustom("Center", SwingConstants.CENTER);

		center.setFont(new Font("Tahoma", Font.PLAIN, 16));

		centerSpace.setValor(40);

		centerSpace.setMinValor(1);

		centerSpace.setLabelText("Center Space");

		centerSpace.getEditor().setFont(new Font("Dialog", Font.PLAIN, 14));

		ButtonRoundedWithImage btnNewButton_1_2 = new ButtonRoundedWithImage("Delete All Left");

		btnNewButton_1_2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				borrarIzquierda();

			}

		});

		btnNewButton_1_2.setFont(new Font("Tahoma", Font.PLAIN, 14));

		ButtonRoundedWithImage btnNewButton_1_2_1 = new ButtonRoundedWithImage("< Back Frame");

		btnNewButton_1_2_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				borrarDerecha();

			}

		});

		btnNewButton_1_2_1.setText("Delete All Right");

		btnNewButton_1_2_1.setFont(new Font("Tahoma", Font.PLAIN, 14));

		ButtonRoundedWithImage btnNewButton_1_2_1_1 = new ButtonRoundedWithImage("< Back Frame");

		btnNewButton_1_2_1_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				borrarIzquierda();

				borrarDerecha();

			}

		});

		btnNewButton_1_2_1_1.setText("Delete Everything But The Current Frame");

		btnNewButton_1_2_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));

		GroupLayout layout = new GroupLayout(this);

		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
				.addContainerGap()
				.addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
						.addComponent(fps, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addGap(18)
										.addComponent(todas, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblNewLabel_2)
										.addGap(18)
										.addComponent(actual, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(lblNewLabel_3)
										.addGap(18)
										.addComponent(recorte, GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
										.addGap(29).addComponent(btnrndwthmgCrop_1, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(layout.createSequentialGroup().addGap(70)
										.addComponent(fuente, GroupLayout.PREFERRED_SIZE, 135,
												GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(sizeFont, GroupLayout.PREFERRED_SIZE, 70,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(btnNewButton_1_2, GroupLayout.PREFERRED_SIZE, 117,
												GroupLayout.PREFERRED_SIZE)
										.addGap(14)
										.addComponent(btnNewButton_1_2_1, GroupLayout.PREFERRED_SIZE, 143,
												GroupLayout.PREFERRED_SIZE)
										.addGap(18).addComponent(btnNewButton_1_2_1_1, GroupLayout.DEFAULT_SIZE, 300,
												Short.MAX_VALUE))))
						.addGroup(layout.createSequentialGroup()
								.addComponent(texto, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(center, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(centerSpace, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(btnNewButton_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(btnNewButton_1_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGap(14)
								.addComponent(btnNewButton_2, GroupLayout.PREFERRED_SIZE, 126,
										GroupLayout.PREFERRED_SIZE)
								.addGap(18).addComponent(btnCreateGif, GroupLayout.PREFERRED_SIZE, 97,
										GroupLayout.PREFERRED_SIZE)))
				.addGap(51)).addComponent(imageScrollPane, GroupLayout.DEFAULT_SIZE, 1042, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.TRAILING)
				.addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addGap(33).addComponent(actual,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(layout.createSequentialGroup().addGap(11).addGroup(layout
								.createParallelGroup(Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addGap(22)
										.addGroup(layout.createParallelGroup(Alignment.LEADING)
												.addComponent(todas, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(fps, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)))
								.addGroup(layout.createParallelGroup(Alignment.TRAILING)
										.addComponent(btnrndwthmgCrop_1, GroupLayout.PREFERRED_SIZE, 61,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(lblNewLabel_2).addComponent(lblNewLabel_3).addComponent(recorte,
												GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)))))
						.addGap(18)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
										.addGroup(layout.createParallelGroup(Alignment.BASELINE)
												.addComponent(btnNewButton_1_2, 0, 0, Short.MAX_VALUE)
												.addComponent(btnNewButton_1_2_1, GroupLayout.PREFERRED_SIZE, 50,
														GroupLayout.PREFERRED_SIZE))
										.addGroup(layout.createParallelGroup(Alignment.BASELINE)
												.addComponent(sizeFont, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(
														fuente, GroupLayout.PREFERRED_SIZE, 34,
														GroupLayout.PREFERRED_SIZE)))
								.addComponent(
										btnNewButton_1_2_1_1, GroupLayout.PREFERRED_SIZE, 50,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(texto, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
								.addGroup(layout.createSequentialGroup().addGap(15)
										.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
												.addComponent(center, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(centerSpace, 0, 0, Short.MAX_VALUE)
												.addGroup(layout.createParallelGroup(Alignment.BASELINE)
														.addComponent(btnCreateGif, GroupLayout.DEFAULT_SIZE, 55,
																Short.MAX_VALUE)
														.addComponent(btnNewButton_2, GroupLayout.PREFERRED_SIZE, 42,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 42,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 50,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(btnNewButton_1_1, GroupLayout.PREFERRED_SIZE, 42,
																GroupLayout.PREFERRED_SIZE)))))
						.addGap(38)
						.addComponent(imageScrollPane, GroupLayout.PREFERRED_SIZE, 276, GroupLayout.PREFERRED_SIZE)));

		this.setLayout(layout);

		try {

			new UtilDragAndDrop(imagePanel, imagen.dragBorder, true, new UtilDragAndDrop.Listener() {

				public void filesDropped(java.io.File[] files) {

					LinkedList<String> carpetasSeleccion = new LinkedList<String>();

					try {

						LinkedList<File> archivos = new LinkedList<File>();

						for (int i = 0; i < files.length; i++) {

							ruta = files[i].getAbsolutePath();

							if (new File(ruta).isDirectory()) {

								carpetasSeleccion = Utilidades.directorio(ruta + Animator.getSeparador(), "images",
										true, true);

								for (int x = 0; x < carpetasSeleccion.size(); x++) {

									listaArchivos.add(carpetasSeleccion.get(x));

									archivos.add(new File(carpetasSeleccion.get(x)));

								}

							}

							if (ruta.endsWith(".gif")) {

								indice = Animator.lista.m.size();

								ButtonPanel.archivoGif = ruta;

								loadGifImage(ButtonPanel.archivoGif);

								extraerFrames();

							}

							else {

								listaArchivos.add(ruta);

								archivos.add(new File(ruta));

							}

						}

						Collections.sort(archivos);

						ButtonPanel.addImages(false, archivos);

						ponerDimensionesFrame(Animator.animator.getGifFrames().get(0));

					}

					catch (Exception e) {

						e.printStackTrace();

					}

				}

			});

			imagePanel.add(imagen, BorderLayout.CENTER);

		}

		catch (Exception e1) {

			e1.printStackTrace();

		}

	}

	public void setGifFrame(GifFrame frame) {

		try {

			if (frame != null) {

				ponerDimensionesFrame(frame);

			}

			else {

				imagen.setIcon(null);

			}

		}

		catch (Exception e) {

			e.printStackTrace();

		}

	}

	public static void ponerDimensionesFrame(GifFrame frame) {

		Image img = frame.getImage();

		int ancho = frame.getImage().getWidth();

		int alto = frame.getImage().getHeight();

		if (alto > 270) {

			ancho = (270 * ancho) / alto;

			alto = 270;

		}

		Image resizedImage = img.getScaledInstance(ancho, alto, 0);

		imagen.setIcon(new ImageIcon(resizedImage));

		sizeImage = Integer.toString(ancho) + " x " + Integer.toString(alto);

		recorte.setText(sizeImage);

	}
}
