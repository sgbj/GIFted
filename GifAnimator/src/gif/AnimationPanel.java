package gif;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class AnimationPanel extends JPanel {
    private List<GifFrame> frames;
    private boolean loop;
    private int index;

    public AnimationPanel(final List<GifFrame> frames, final boolean loop) {
        this.frames = frames;
        this.loop = loop;

        new Thread(new Runnable() {
            public void run() {
                do {
                    index = 0;
                    for (int i = 0; i < frames.size(); i++) {
                        index = i;
                        GifFrame frame = frames.get(index);
                        setPreferredSize(new Dimension(frame.getImage().getWidth(),
                                frame.getImage().getHeight()));
                        repaint();
                        try {
                            Thread.sleep(frame.getDelay());
                        } catch (InterruptedException ex) {
                            JOptionPane.showMessageDialog(AnimationPanel.this, ex, "Exception",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } while (loop);
            }
        }).start();
    }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(frames.get(index).getImage(), 0, 0, this);
    }
}
