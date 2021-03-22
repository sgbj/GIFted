
package gif;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import utils.Metodos;

public class GifFramePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private Animator animator;

	static GifFrame frame;

	public static JLabel image;

	private JPanel imagePanel;

	private JScrollPane imageScrollPane;

	private JButton preview;

	public static JTextField fps;

	private JTextField texto;

	private JTextField recorte;

	public static String sizeImage;

	private JRadioButton actual;

	private JRadioButton todas;

	public String ruta;

	public String nombreGif;

	private JButton resize;

	private JLabel lblNewLabel_2;

	private JLabel lblNewLabel_3;

	public GifFramePanel(Animator animator) {

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

				String medidasImagen = Metodos.eliminarEspacios(recorte.getText(), true);

				int width = Integer.parseInt(medidasImagen.substring(0, medidasImagen.indexOf("x")));

				int height = Integer
						.parseInt(medidasImagen.substring(medidasImagen.indexOf("x") + 1, medidasImagen.length()));

				if (width > 0 && height > 0) {

					int indice = 0;

					int vueltas = GifDef.mFrameImageList.size();

					if (actual.isSelected()) {

						indice -= ButtonPanel.archivos;

						vueltas = indice;

						vueltas++;

					}

					if (indice >= 0) {

						LinkedList<String> imagenes = new LinkedList<String>();

						for (int i = 0; i < vueltas; i++) {
							imagenes.add(
									path.substring(path.lastIndexOf(Animator.getSeparador()) + 1, path.length() - 4)
											+ "_" + i + ".png");
						}

						for (int i = indice; i < vueltas; i++) {

							Metodos.resizeImage(
									ruta + "output" + Animator.getSeparador() + nombreGif + Animator.getSeparador()
											+ imagenes.get(i),
									ruta + "Resized" + Animator.getSeparador() + nombreGif + Animator.getSeparador()
											+ "test_" + i + ".png",
									width, height);

						}

						Metodos.eliminarArchivos(
								ruta + "output" + Animator.getSeparador() + nombreGif + Animator.getSeparador());

					}

				}

			}

			Metodos.mensaje("Resize finished", 2, true);

		}

		catch (Exception e) {

		}

	}

	private void initComponents() {

		imageScrollPane = new JScrollPane();

		imagePanel = new JPanel();

		image = new JLabel();

		preview = new JButton();

		preview.setIcon(new ImageIcon(GifFramePanel.class.getResource("/images/view.png")));

		imagePanel.setLayout(new java.awt.BorderLayout());

		image.setHorizontalAlignment(SwingConstants.CENTER);
		imagePanel.add(image, java.awt.BorderLayout.CENTER);

		imageScrollPane.setViewportView(imagePanel);

		preview.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {

				previewActionPerformed(evt);

			}

		});

		fps = new JTextField();

		fps.addKeyListener(new KeyAdapter() {

			@Override

			public void keyReleased(KeyEvent e) {

				try {

					String texto = fps.getText();

					if (texto.isEmpty() || Integer.parseInt(texto) <= 0) {
						fps.setText("10");
					}

				}

				catch (Exception e1) {

					fps.setText("10");
				}

			}

		});

		fps.setText("10");

		fps.setHorizontalAlignment(SwingConstants.CENTER);

		fps.setFont(new Font("Dialog", Font.PLAIN, 18));

		fps.setColumns(10);

		JLabel lblNewLabel = new JLabel("FPS");
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

		lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 18));

		JButton preview_1 = new JButton();

		preview_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				if (!Animator.lista.m.isEmpty()) {

					int resp = JOptionPane.showConfirmDialog(null, "¿Quieres borrar todos los frames?", "Clear Frames",
							JOptionPane.YES_NO_OPTION);

					if (resp == 0) {
						Animator.lista.m.clear();
					}

				}

			}

		});

		preview_1.setIcon(new ImageIcon(GifFramePanel.class.getResource("/images/clean.png")));

		texto = new JTextField();

		texto.setHorizontalAlignment(SwingConstants.CENTER);

		texto.setFont(new Font("Dialog", Font.PLAIN, 16));

		texto.setColumns(10);

		JButton btnNewButton = new JButton("");

		btnNewButton.setIcon(new ImageIcon(GifFramePanel.class.getResource("/gif/resource/pressed.png")));

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

		JButton btnNewButton_1 = new JButton("");

		btnNewButton_1.setIcon(new ImageIcon(GifFramePanel.class.getResource("/images/extractframes.png")));

		btnNewButton_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (ButtonPanel.archivoGif != null) {

					try {

						loadGifImage(ButtonPanel.archivoGif);

						extraerFrames(ButtonPanel.archivoGif, true);

					}

					catch (Exception e1) {

						Metodos.mensaje("Error", 1, true);
					}

				}

			}

		});

		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 16));

		recorte = new JTextField();

		recorte.setFont(new Font("Tahoma", Font.PLAIN, 16));

		recorte.setHorizontalAlignment(SwingConstants.CENTER);

		recorte.setColumns(10);

		actual = new JRadioButton("");

		actual.setFont(new Font("Tahoma", Font.PLAIN, 14));

		todas = new JRadioButton("");

		todas.setSelected(true);

		todas.setFont(new Font("Tahoma", Font.PLAIN, 14));

		ButtonGroup bg1 = new ButtonGroup();

		bg1.add(actual);

		bg1.add(todas);

		resize = new JButton("");

		resize.setIcon(new ImageIcon(GifFramePanel.class.getResource("/images/resize.png")));

		resize.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (ButtonPanel.archivoGif != null) {

					try {

						redimensionarPNG(ButtonPanel.archivoGif);

					}

					catch (Exception e1) {

						Metodos.mensaje("Error", 1, true);
					}

				}

			}

		});

		lblNewLabel_2 = new JLabel("");

		lblNewLabel_2.setIcon(new ImageIcon(GifFramePanel.class.getResource("/images/multiple.png")));

		lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(GifFramePanel.class.getResource("/images/simple.png")));

		JButton btnNewButton_2 = new JButton("Resized Images");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Metodos.abrirCarpeta(ruta + "Resized" + Animator.getSeparador() + nombreGif);
				} catch (Exception e1) {
					Metodos.mensaje("Error", 1, true);
				}
			}
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton_2.setIcon(new ImageIcon(GifFramePanel.class.getResource("/images/folder.png")));

		JButton btnNewButton_3 = new JButton("Extract Frames Folder");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Metodos.abrirCarpeta(ruta + "output" + Animator.getSeparador() + nombreGif);
				} catch (Exception e1) {
					Metodos.mensaje("Error", 1, true);
				}
			}
		});
		btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton_3.setIcon(new ImageIcon(GifFramePanel.class.getResource("/images/folder.png")));

		GroupLayout layout = new GroupLayout(this);
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.TRAILING).addGroup(layout.createSequentialGroup()
				.addGap(18)
				.addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
						.addComponent(preview_1, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE).addGap(18)
						.addComponent(preview, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE).addGap(18))
						.addGroup(layout.createSequentialGroup().addComponent(texto, 0, 0, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)))
				.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(fps, GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
						.addGroup(layout.createSequentialGroup().addGap(10).addComponent(btnNewButton,
								GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGap(23)
				.addGroup(layout.createParallelGroup(Alignment.TRAILING)
						.addGroup(layout.createSequentialGroup().addComponent(todas)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblNewLabel_2).addGap(9)
								.addComponent(actual).addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblNewLabel_3).addGap(10).addComponent(recorte, 152, 152, 152).addGap(18)
								.addComponent(resize, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnNewButton_1,
										GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
						.addGroup(layout.createSequentialGroup().addComponent(btnNewButton_2).addGap(33)
								.addComponent(btnNewButton_3, GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)))
				.addGap(92)).addComponent(imageScrollPane, GroupLayout.DEFAULT_SIZE, 773, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addContainerGap()
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
										.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
												.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(preview_1)
												.addComponent(preview, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addGroup(layout.createSequentialGroup().addComponent(lblNewLabel)
														.addPreferredGap(ComponentPlacement.RELATED).addComponent(fps,
																GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)))
										.addGroup(layout.createParallelGroup(Alignment.TRAILING)
												.addComponent(btnNewButton_1, Alignment.LEADING,
														GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
												.addComponent(resize, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 60,
														Short.MAX_VALUE)
												.addGroup(Alignment.LEADING,
														layout.createSequentialGroup().addGap(10).addComponent(recorte,
																GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
												.addComponent(lblNewLabel_3, Alignment.LEADING,
														GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)))
								.addGap(18))
						.addGroup(layout.createSequentialGroup().addGap(27).addComponent(actual)
								.addPreferredGap(ComponentPlacement.RELATED)))
						.addGroup(layout.createSequentialGroup().addGap(28).addComponent(todas)
								.addPreferredGap(ComponentPlacement.RELATED)))
				.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(btnNewButton, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 55, Short.MAX_VALUE)
						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnNewButton_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(btnNewButton_3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE))
						.addComponent(texto, Alignment.LEADING))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(imageScrollPane, GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)));
		this.setLayout(layout);
	}

	private void previewActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_previewActionPerformed

		fps.setEditable(false);

		if (Animator.lista.m.size() > 0) {

			final JDialog d = new JDialog((JFrame) null, "Animation");

			d.setType(Type.UTILITY);

			d.addKeyListener(new KeyAdapter() {

				@Override

				public void keyPressed(KeyEvent e) {

					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						d.dispose();
					}
				}

			});

			d.getContentPane().add(new AnimationPanel(animator.getGifFrames(), ButtonPanel.loop.isSelected()));

			d.pack();

			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

			int height = screenSize.height;

			int width = screenSize.width;

			d.setSize(width - 256, height - 304);

			d.setLocationRelativeTo(null);

			d.setVisible(true);

		}

		fps.setEditable(true);

	}

	public void setGifFrame(GifFrame frame) {

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
}
