
package gif;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.TooManyListenersException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;

import roundedButtonsWithImage.ButtonRoundedWithImage;
import spinner.Spinner;
import utils.DragAndDrop;
import utils.Metodos;

public class GifFramePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private Animator animator;

	static GifFrame frame;

	public static JLabel image;

	private JPanel imagePanel;

	private JScrollPane imageScrollPane;

	public static Spinner fps;

	private JTextField texto;

	public static JTextField recorte;

	public static String sizeImage;

	private JRadioButton actual;

	private JRadioButton todas;

	public static String ruta;

	public static String nombreGif;

	private ButtonRoundedWithImage resize;

	private JLabel lblNewLabel_2;

	private JLabel lblNewLabel_3;

	private JLabel lblNewLabel;

	public GifFramePanel(Animator animator) {
		setBackground(Color.WHITE);

		this.animator = animator;

		initComponents();

	}

	public void loadGifImage(String path) {

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

			if (GifDef.mFrameImageList.size() > 0) {

				@SuppressWarnings("unused")

				BufferedImage frame = GifDef.mFrameImageList.elementAt(0);

			}

		}

		catch (Exception e) {

		}

	}

	public void actualizarRuta() {

		this.ruta = ButtonPanel.archivoGif.substring(0,
				ButtonPanel.archivoGif.lastIndexOf(Animator.getSeparador()) + 1);

		this.nombreGif = ButtonPanel.archivoGif.substring(
				ButtonPanel.archivoGif.lastIndexOf(Animator.getSeparador()) + 1, ButtonPanel.archivoGif.length() - 4);

	}

	public void extraerFrames(String path, boolean mensaje) {

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

			for (int i = 0; i < size; i++) {

				frame = GifDef.mFrameImageList.elementAt(i);

				BufferedImage bfimg = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);

				Graphics gg = bfimg.getGraphics();

				gg.drawImage(frame, 0, 0, null);

				File pngfile = new File(ruta + "output" + Animator.getSeparador() + nombreGif + Animator.getSeparador()
						+ path.substring(path.lastIndexOf(Animator.getSeparador()) + 1, path.length() - 4) + "_" + i
						+ ".png");

				ImageIO.write(bfimg, "png", pngfile);

			}

			if (mensaje) {

				Metodos.mensaje("GIF TO FRAME SUCCESFUL", 2, true);

			}

		}

		catch (Exception e) {

		}

	}

	public void redimensionarPNG(String path) {

		try {

			if (path != null) {

				loadGifImage(path);

				extraerFrames(ButtonPanel.archivoGif, false);

				String medidasImagen = Metodos.eliminarEspacios(recorte.getText(), false);

				int width = Integer.parseInt(
						Metodos.eliminarEspacios(medidasImagen.substring(0, medidasImagen.indexOf("x")), true));

				int height = Integer.parseInt(Metodos.eliminarEspacios(
						medidasImagen.substring(medidasImagen.indexOf("x") + 1, medidasImagen.length()), true));

				if (width > 0 && height > 0) {

					int indice = 0;

					int vueltas = GifDef.mFrameImageList.size();

					LinkedList<String> images = new LinkedList<String>();

					for (int i = 0; i < vueltas; i++) {
						images.add(path.substring(path.lastIndexOf(Animator.getSeparador()) + 1, path.length() - 4)
								+ "_" + i + ".png");
					}

					if (actual.isSelected()) {

						indice = Animator.lista.getSelectedIndex();

						vueltas = indice;

						vueltas++;

					}

					for (int i = indice; i < vueltas; i++) {

						Metodos.resizeImage(
								ruta + "output" + Animator.getSeparador() + nombreGif + Animator.getSeparador()
										+ images.get(i),
								ruta + "Resized" + Animator.getSeparador() + nombreGif + Animator.getSeparador()
										+ "test_" + i + ".png",
								width, height);

					}

					Metodos.eliminarArchivos(
							ruta + "output" + Animator.getSeparador() + nombreGif + Animator.getSeparador());

				}

			}

			Metodos.mensaje("Resize finished", 2, true);

		}

		catch (Exception e) {
			//
		}

	}

	private void initComponents() {

		imageScrollPane = new JScrollPane();

		imagePanel = new JPanel();

		image = new JLabel();

		image.setBackground(Color.LIGHT_GRAY);

		imagePanel.setLayout(new java.awt.BorderLayout());

		image.setHorizontalAlignment(SwingConstants.CENTER);

		imagePanel.add(image, java.awt.BorderLayout.CENTER);

		imageScrollPane.setViewportView(imagePanel);

		fps = new Spinner();

		fps.setMinValor(1);

		fps.setLabelText("Delay");

		fps.setFont(new Font("Dialog", Font.PLAIN, 18));

		fps.getEditor().addKeyListener(new KeyAdapter() {

			@Override

			public void keyReleased(KeyEvent e) {

				try {

					fps.ponerFiltro();

				} catch (Exception e1) {

				}

			}

		});

		texto = new JTextField();

		texto.setHorizontalAlignment(SwingConstants.CENTER);

		texto.setFont(new Font("Dialog", Font.PLAIN, 16));

		texto.setColumns(10);

		JButton btnNewButton = new JButton("Add Frame");
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));

		btnNewButton.setIcon(null);

		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				String dato = texto.getText().trim();

				dato = Metodos.eliminarEspacios(dato, false);

				if (!dato.isEmpty()) {

					GifFrame[] frames = new GifFrame[] { Animator.createDemoGifFrame(46, 46, dato, 200) };

					Animator.animator.addGifFrame(frames[0]);

				}

			}

		});

		ButtonRoundedWithImage btnNewButton_1 = new ButtonRoundedWithImage();
		btnNewButton_1.setText("Gif To Frames");

		btnNewButton_1.setIcon(new ImageIcon(GifFramePanel.class.getResource("/images/extractframes.png")));

		btnNewButton_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (ButtonPanel.archivoGif != null) {

					try {

						loadGifImage(ButtonPanel.archivoGif);

						extraerFrames(ButtonPanel.archivoGif, true);

					}

					catch (Exception e1) {

						Metodos.mensaje("Error", 1, false);

					}

				}

			}

		});

		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 14));

		recorte = new JTextField();

		recorte.setFont(new Font("Tahoma", Font.PLAIN, 16));

		recorte.setHorizontalAlignment(SwingConstants.CENTER);

		recorte.setColumns(10);

		actual = new JRadioButton("Current Frame");

		actual.setFont(new Font("Tahoma", Font.PLAIN, 14));

		todas = new JRadioButton("All Frames");

		todas.setSelected(true);

		todas.setFont(new Font("Tahoma", Font.PLAIN, 14));

		ButtonGroup bg1 = new ButtonGroup();

		bg1.add(actual);

		bg1.add(todas);

		resize = new ButtonRoundedWithImage();

		resize.setFont(new Font("Tahoma", Font.PLAIN, 14));

		resize.setText("Resize Frame");

		resize.setIcon(new ImageIcon(GifFramePanel.class.getResource("/images/resize.png")));

		resize.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (ButtonPanel.archivoGif != null) {

					try {

						redimensionarPNG(ButtonPanel.archivoGif);

					}

					catch (Exception e1) {

						Metodos.mensaje("Error", 1, false);
					}

				}

			}

		});

		lblNewLabel_2 = new JLabel("");

		lblNewLabel_2.setIcon(new ImageIcon(GifFramePanel.class.getResource("/images/multiple.png")));

		lblNewLabel_3 = new JLabel("");

		lblNewLabel_3.setIcon(new ImageIcon(GifFramePanel.class.getResource("/images/simple.png")));

		ButtonRoundedWithImage btnNewButton_2 = new ButtonRoundedWithImage();

		btnNewButton_2.setText("Open Images");

		btnNewButton_2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				try {

					Metodos.abrirCarpeta(ruta + "Resized" + Animator.getSeparador() + nombreGif);

				}

				catch (Exception e1) {

				}

			}

		});

		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 16));

		btnNewButton_2.setIcon(new ImageIcon(GifFramePanel.class.getResource("/images/folder.png")));

		ButtonRoundedWithImage btnNewButton_3 = new ButtonRoundedWithImage();

		btnNewButton_3.setText("Open Frames Folder");

		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					Metodos.abrirCarpeta(ruta + "output" + Animator.getSeparador() + nombreGif);
				} catch (Exception e1) {
					Metodos.mensaje("Error", 1, false);
				}
			}
		});
		btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton_3.setIcon(new ImageIcon(GifFramePanel.class.getResource("/images/folder.png")));

		lblNewLabel = new JLabel("String To Frame");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JButton btnCreateGif = new JButton("Create GIF");
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

							Gif.write(animator.getGifFrames(), ButtonPanel.loop.isSelected(), file);

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
		btnCreateGif.setIcon(new ImageIcon(GifFramePanel.class.getResource("/images/save.png")));
		btnCreateGif.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCreateGif.setBackground(Color.WHITE);

		GroupLayout layout = new GroupLayout(this);
		layout.setHorizontalGroup(
				layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addGap(12).addComponent(lblNewLabel)).addGroup(
										layout.createSequentialGroup().addContainerGap().addComponent(fps,
												GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(layout
										.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
												.addGap(12).addComponent(todas)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(lblNewLabel_2).addGap(18).addComponent(actual).addGap(18)
												.addComponent(lblNewLabel_3).addGap(18)
												.addComponent(recorte, GroupLayout.PREFERRED_SIZE, 99,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(resize, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGap(18).addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE,
														174, GroupLayout.PREFERRED_SIZE))
										.addGroup(layout.createSequentialGroup()
												.addComponent(texto, GroupLayout.PREFERRED_SIZE, 116,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 101,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(btnCreateGif, GroupLayout.PREFERRED_SIZE, 149,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(btnNewButton_2, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(btnNewButton_3, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.addContainerGap())
						.addComponent(imageScrollPane, GroupLayout.DEFAULT_SIZE, 973, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.TRAILING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(Alignment.TRAILING).addGroup(layout.createSequentialGroup()
								.addComponent(fps, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
								.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE))
								.addGroup(layout.createSequentialGroup().addGroup(layout
										.createParallelGroup(Alignment.LEADING)
										.addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout
												.createParallelGroup(Alignment.LEADING)
												.addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout
														.createSequentialGroup().addGap(3)
														.addGroup(layout.createParallelGroup(Alignment.TRAILING)
																.addComponent(
																		recorte, GroupLayout.DEFAULT_SIZE, 56,
																		Short.MAX_VALUE)
																.addGroup(layout.createParallelGroup(Alignment.BASELINE)
																		.addComponent(resize,
																				GroupLayout.PREFERRED_SIZE, 53,
																				GroupLayout.PREFERRED_SIZE)
																		.addComponent(btnNewButton_1,
																				GroupLayout.PREFERRED_SIZE, 56,
																				Short.MAX_VALUE)))
														.addGap(33))
														.addGroup(layout.createSequentialGroup()
																.addComponent(lblNewLabel_2)
																.addPreferredGap(ComponentPlacement.RELATED)))
												.addGroup(layout.createSequentialGroup().addComponent(todas)
														.addPreferredGap(ComponentPlacement.RELATED)))
												.addGroup(layout.createSequentialGroup().addComponent(actual)
														.addPreferredGap(ComponentPlacement.RELATED)))
										.addGroup(layout.createSequentialGroup().addComponent(lblNewLabel_3)
												.addPreferredGap(ComponentPlacement.RELATED)))
										.addGroup(layout.createParallelGroup(Alignment.TRAILING)
												.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
														.addComponent(btnNewButton_2, 0, 0, Short.MAX_VALUE)
														.addGroup(layout.createParallelGroup(Alignment.BASELINE)
																.addComponent(texto, GroupLayout.PREFERRED_SIZE, 39,
																		GroupLayout.PREFERRED_SIZE)
																.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE,
																		40, Short.MAX_VALUE)
																.addComponent(btnCreateGif, GroupLayout.PREFERRED_SIZE,
																		40, Short.MAX_VALUE)))
												.addComponent(btnNewButton_3, 0, 0, Short.MAX_VALUE))))
						.addGap(18)
						.addComponent(imageScrollPane, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)));
		this.setLayout(layout);

		javax.swing.border.TitledBorder dragBorder = new javax.swing.border.TitledBorder("");

		try {

			new DragAndDrop(image, dragBorder, true, new DragAndDrop.Listener() {

				@SuppressWarnings("null")

				public void filesDropped(java.io.File[] files) {

					LinkedList<String> carpetasSeleccion = new LinkedList<String>();

					try {

						LinkedList<File> archivos = new LinkedList<File>();

						String ruta;

						for (int i = 0; i < files.length; i++) {

							ruta = files[i].getAbsolutePath();

							if (!files[i].getAbsolutePath().contains(".")) {

								carpetasSeleccion = Metodos.directorio(ruta + Animator.getSeparador(), "images", true,
										true);

								for (int x = 0; x < carpetasSeleccion.size(); x++) {

									archivos.add(new File(carpetasSeleccion.get(x)));

								}

							}

							else {

								archivos.add(new File(ruta));
							}

						}

						Collections.sort(archivos);

						ButtonPanel.addImages(false, archivos);

					}

					catch (Exception e) {

					}

				}

			});

		}

		catch (TooManyListenersException e1) {
			Metodos.mensaje("Error al mover los archivos", 1, false);
		}

	}

	public void setGifFrame(GifFrame frame) {

		try {

			GifFramePanel.frame = frame;

			if (frame != null) {

				image.setIcon(new ImageIcon(frame.getImage()));

				sizeImage = Integer.toString(frame.getImage().getWidth()) + " x "
						+ Integer.toString(frame.getImage().getHeight());

				recorte.setText(sizeImage);

			}

			else {

				image.setIcon(null);

			}
		}

		catch (Exception e) {

		}

	}
}
