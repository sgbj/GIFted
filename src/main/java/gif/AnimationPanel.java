package gif;

import java.awt.Color;
import java.awt.Graphics;
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

		g.drawImage(frames.get(index).getImage(), 0, 0, this);

	}

}
