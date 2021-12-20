package gif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.TooManyListenersException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import utils.DragAndDrop;
import utils.Metodos;

public class Animator {

	static Animator animator;

	static GifFrameList lista;

	static String os = System.getProperty("os.name");

	static String separador = Metodos.saberSeparador(os);

	LinkedList<String> archivos = new LinkedList<String>();

	public static String getSeparador() {
		return separador;
	}

	private static GifFramePanel panel;

	public static String getOs() {
		return os;
	}

	public static void saberSize() {

		GifFrame frame = (GifFrame) lista.getGifFrames().get(0);

		panel.setGifFrame(frame);

	}

	public Animator(GifFrame[] frames) throws IOException {

		final JFrame f = new JFrame("Gif Animator");

		f.setIconImage(Toolkit.getDefaultToolkit().getImage(Animator.class.getResource("/images/ico.png")));

		f.setResizable(false);

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ButtonPanel buttonPanel = new ButtonPanel(this);

		buttonPanel.setSize(10, 10);

		f.getContentPane().add(buttonPanel, BorderLayout.NORTH);

		panel = new GifFramePanel(this);

		panel.setSize(10, 10);

		f.getContentPane().add(panel);

		JPanel listPanel = new JPanel(new BorderLayout());

		JScrollPane listScrollPane = new JScrollPane();

		if (frames != null) {

			lista = new GifFrameList(frames);

			lista.m.remove(0);

			listScrollPane = new JScrollPane(lista, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
					JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

			int inset = listScrollPane.getHorizontalScrollBar().getHeight();

			lista.setBorder(BorderFactory.createEmptyBorder(0, 0, inset, 0));

			lista.addListSelectionListener(new ListSelectionListener() {

				public void valueChanged(ListSelectionEvent e) {

					GifFrame frame = (GifFrame) lista.getSelectedValue();

					panel.setGifFrame(frame);

				}

			});

			lista.setSelectedIndex(0);

		}

		listPanel.add(listScrollPane, BorderLayout.CENTER);

		f.getContentPane().add(listPanel, BorderLayout.SOUTH);

		f.pack();

		f.setLocationRelativeTo(null);

		f.setVisible(true);

		javax.swing.border.TitledBorder dragBorder = new javax.swing.border.TitledBorder("");

		try {

			new DragAndDrop(listScrollPane, dragBorder, true, new DragAndDrop.Listener() {

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

						File[] resultado = new File[archivos.size()];

						for (int i = 0; i < archivos.size(); i++) {
							resultado[i] = archivos.get(i);
						}

						ButtonPanel.addImages(false, resultado);

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

	public void addGifFrame(GifFrame frame) {
		lista.addGifFrame(frame);
	}

	public List<GifFrame> getGifFrames() {
		return lista.getGifFrames();
	}

	static GifFrame createDemoGifFrame(int w, int h, String str, int delay) {

		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

		Graphics2D g = image.createGraphics();

		g.setColor(Color.WHITE);

		g.fillRect(0, 0, w, h);

		Rectangle2D bounds = g.getFontMetrics().getStringBounds(str, g);

		g.setColor(Color.BLACK);

		g.drawString(str, (int) ((w / 2) - (bounds.getWidth() / 2)), (int) ((h / 2) + (bounds.getHeight() / 2)));

		g.dispose();

		return new GifFrame(image, delay);

	}

	public static void iniciar() {

		try {

			final GifFrame[] frames = new GifFrame[] { null };

			animator = new Animator(frames);

		}

		catch (Exception ex) {

		}

	}

	public static void main(String[] args) {

		iniciar();

	}

}
