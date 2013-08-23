package gif;

import java.awt.image.BufferedImage;

public class GifFrame {
    private BufferedImage image;
    private long delay;

    public GifFrame(BufferedImage image, long delay) {
        this.image = image;
        this.delay = delay;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
