package gif;

import java.awt.image.BufferedImage;

public class GifFrame {

	private BufferedImage image;

	public GifFrame(BufferedImage image, long delay) {

		this.image = image;

	}

	public BufferedImage getImage() {

		return image;

	}

	public void setImage(BufferedImage image) {

		this.image = image;

	}

}
