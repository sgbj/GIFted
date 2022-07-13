package gif;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.List;

import javax.swing.JPanel;

public class AnimationPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private List<GifFrame> frames;

	private int index;

	public AnimationPanel(final List<GifFrame> frames, boolean loop, boolean reverse) {

		setBackground(Color.WHITE);

		this.frames = frames;

		new Thread(new Runnable() {

			public void run() {

				do {

					index = 0;

					if (reverse) {

						index = frames.size();

						index--;

					}

					for (int i = 0; i < frames.size(); i++) {

						repaint();

						try {

							Thread.sleep(Long.parseLong(GifFramePanel.fps.getValor() + "0"));

						}

						catch (Exception ex) {

							try {

								Thread.sleep(10L);

								GifFramePanel.fps.setValor(10);

							}

							catch (InterruptedException e) {
								//
							}

						}

						if (reverse) {

							index--;

						}

						else {

							index++;

						}

					}

				}

				while (loop);

			}

		}).start();

	}

	@Override

	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		Image img = frames.get(index).getImage();

		int ancho = frames.get(index).getImage().getWidth();

		int alto = frames.get(index).getImage().getHeight();

		if (alto > 560) {

			ancho = (560 * ancho) / alto;

			alto = 560;

		}

		if (ancho > 586) {

			ancho = 586;

		}

		Image resizedImage = img.getScaledInstance(ancho, alto, 0);

		g.drawImage(resizedImage, 0, 0, this);

	}

}
