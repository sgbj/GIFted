package gif;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;

public final class Gif {

	public static void write(List<GifFrame> frames, boolean loopContinuously, File file)
			throws IIOInvalidTreeException, IOException {

		try {

			AnimatedGifEncoder e = new AnimatedGifEncoder();

			int contador = 0;

			Animator.animator.getGifFrames().get(0);

			for (BufferedImage frame : Animator.archivos) {

				contador++;

				if (contador == 1) {

					e.start("C:\\Users\\Yeah\\Desktop\\salida_out_Test.gif");

					e.setRepeat(0);

					e.setDelay(GifFramePanel.fps.getValor());

				}

				e.addFrame(frame);

			}

			e.finish();

			Animator.archivos.clear();

			Animator.lista.m.clear();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static List<GifFrame> read(File file) throws IOException {

		ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();

		reader.setInput(ImageIO.createImageInputStream(file));

		int numImages = reader.getNumImages(true);

		List<GifFrame> frames = new ArrayList<GifFrame>();

		for (int i = 0; i < --numImages; i++) {

			BufferedImage image = reader.read(i);

			IIOMetadata metadata = reader.getImageMetadata(i);

			String fmt = metadata.getNativeMetadataFormatName();

			IIOMetadataNode root = (IIOMetadataNode) metadata.getAsTree(fmt);

			IIOMetadataNode gce = getNode(root, "GraphicControlExtension");

			String delayTime = gce.getAttribute("delayTime");

			long delay = 500;

			if (!delayTime.isEmpty()) {

				delay = Long.parseLong(delayTime);
			}

			frames.add(new GifFrame(image, delay * 10));

		}

		return frames;

	}

	private static IIOMetadataNode getNode(IIOMetadataNode rootNode, String nodeName) {

		int nNodes = rootNode.getLength();

		for (int i = 0; i < nNodes; i++) {

			if (rootNode.item(i).getNodeName().compareToIgnoreCase(nodeName) == 0) {
				return ((IIOMetadataNode) rootNode.item(i));
			}
		}

		IIOMetadataNode node = new IIOMetadataNode(nodeName);

		rootNode.appendChild(node);

		return (node);

	}

}
