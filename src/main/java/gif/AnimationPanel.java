package gif;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

public class AnimationPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private List<GifFrame> frames;

	private int index;

	public AnimationPanel(final List<GifFrame> frames, final boolean loop) {

		this.frames = frames;

		new Thread(new Runnable() {

			public void run() {

				do {

					index = 0;

					for (int i = 0; i < frames.size(); i++) {

						index = i;

						GifFrame frame = frames.get(index);

						setPreferredSize(new Dimension(frame.getImage().getWidth(), frame.getImage().getHeight()));

						repaint();

						try {
							Thread.sleep(Long.parseLong(GifFramePanel.fps.getText() + "0"));
						}

						catch (Exception ex) {

							try {

								Thread.sleep(10L);

								GifFramePanel.fps.setText("10");

							} catch (InterruptedException e) {
								//
							}
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
