package gif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import utils.Metodos;

public class Animator {

	static Animator animator;

	static GifFrameList lista;

	static String os = System.getProperty("os.name");

	static String separador = Metodos.saberSeparador(os);

	public static String getSeparador() {
		return separador;
	}

	private static GifFramePanel panel;

	public Animator(GifFrame[] frames) {

		final JFrame f = new JFrame("Gif Animator");

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

	}

	public void addGifFrame(GifFrame frame) {
		lista.addGifFrame(frame);
	}

	public boolean loop() {
		return panel.loop();
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

	public static void main(String[] args) {

		try {

			final GifFrame[] frames = new GifFrame[] { null };

			animator = new Animator(frames);

		}

		catch (Exception ex) {

		}

	}

}
