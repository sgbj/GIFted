package gif;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicListUI;

public class GifFrameList extends JList<Object> {

	private static final long serialVersionUID = 1L;

	DefaultListModel<Object> m = (DefaultListModel<Object>) getModel();

	private static LinkedList<File> archivos = new LinkedList<>();

	public GifFrameList(Object[] listData) {

		super(createDefaultListModel(listData));

		setLayoutOrientation(HORIZONTAL_WRAP);

		setVisibleRowCount(1);

		setDragEnabled(true);

		setDropMode(DropMode.INSERT);

		setTransferHandler(new GifFrameTransferHandler());

		UIManager.getDefaults().remove("List.dropLineColor");

		setUI(new GifFrameListUI());

		GifFrameListCellRenderer renderer = new GifFrameListCellRenderer();

		setCellRenderer(renderer);

		Dimension cellSize = renderer.getPreferredSize();

		setFixedCellWidth((int) cellSize.getWidth());

		setFixedCellHeight((int) cellSize.getHeight());

		addKeyListener(new KeyAdapter() {

			@Override

			public void keyReleased(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_DELETE) {

					int[] indices = getSelectedIndices();

					for (int i = indices.length - 1; i >= 0; i--) {

						m.remove(indices[i]);

						if (indices[i] < Animator.archivos.size()) {

							Animator.archivos.remove(indices[i]);

						}

					}

				}

			}

		});

	}

	@Override

	public Dimension getPreferredSize() {

		Dimension d = super.getPreferredSize();

		d.height = 80;

		return d;

	}

	public static final DefaultListModel<Object> createDefaultListModel(Object[] listData) {

		DefaultListModel<Object> m = new DefaultListModel<Object>();

		for (Object o : listData) {

			m.addElement(o);

		}

		return m;

	}

	public void addGifFrame(GifFrame frame) {

		m.addElement(frame);

	}

	public List<GifFrame> getGifFrames() {

		DefaultListModel<?> m = (DefaultListModel<?>) getModel();

		Object[] a = m.toArray();

		List<GifFrame> frames = new ArrayList<GifFrame>();

		for (Object o : a) {

			frames.add((GifFrame) o);

		}

		return frames;

	}

	private static class GifFrameTransferHandler extends TransferHandler {

		private static final long serialVersionUID = 1L;

		@Override

		public int getSourceActions(JComponent c) {

			return TransferHandler.COPY_OR_MOVE;

		}

		@Override
		protected Transferable createTransferable(JComponent c) {
			JList<?> list = (JList<?>) c;
			@SuppressWarnings("deprecation")
			final Object[] values = list.getSelectedValues();
			return new GifFrameSelection(values);
		}

		@Override
		public boolean canImport(TransferSupport support) {
			if (!support.isDataFlavorSupported(GifFrameSelection.GIF_FRAME_FLAVOR)
					&& !support.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
				return false;
			}
			support.getComponent().repaint();
			return true;
		}

		@Override
		public boolean importData(TransferSupport support) {
			if (!canImport(support) || !support.isDrop()) {
				return false;
			}

			Transferable t = support.getTransferable();
			@SuppressWarnings("unchecked")
			JList<Object> list = (JList<Object>) support.getComponent();

			int index = list.getUI().locationToIndex(list, support.getDropLocation().getDropPoint());
			Rectangle rect = list.getCellBounds(index, index);
			int size = list.getModel().getSize();
			if (index == size - 1) {
				if (rect == null) {
					index = 0;
				} else {
					if (support.getDropLocation().getDropPoint().getX() > rect.getX() + rect.getWidth()) {
						index = size;
					}
				}
			}
			DefaultListModel<Object> m = (DefaultListModel<Object>) list.getModel();

			if (support.isDataFlavorSupported(GifFrameSelection.GIF_FRAME_FLAVOR)) {
				Object[] data = null;

				try {
					data = (Object[]) t.getTransferData(GifFrameSelection.GIF_FRAME_FLAVOR);
				} catch (Exception ex) {

					return false;
				}

				int[] indices = list.getSelectedIndices();

				for (int i = 0; i < data.length; i++) {

					m.add(index + i, data[i]);

					for (int j = 0; j < indices.length; j++) {
						if (index < indices[j]) {
							indices[j]++;
						}
					}
				}

				if (support.getDropAction() != COPY) {
					for (int i = 0; i < indices.length; i++) {
						m.remove(indices[i] - i);
					}
				}
			} else if (support.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
				List<?> data = null;

				try {
					data = (List<?>) t.getTransferData(DataFlavor.javaFileListFlavor);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(list, ex, "Exception", JOptionPane.ERROR_MESSAGE);
					return false;
				}

				for (Object o : data) {

					File file = (File) o;

					try {

						if (file.getName().endsWith("gif")) {

							List<GifFrame> frames = Gif.read(file);

							Collections.reverse(frames);

							for (GifFrame frame : frames) {

								m.add(index, frame);

							}

						}

						else {

							archivos.add(file);

						}

					}

					catch (Exception ex) {

						JOptionPane.showMessageDialog(list, ex, "Exception", JOptionPane.ERROR_MESSAGE);

						return false;

					}

				}
				try {

					Collections.sort(archivos);

					int y = index;

					for (int i = 0; i < archivos.size(); i++) {

						m.add(y, new GifFrame(ImageIO.read(archivos.get(i)), 500));

						y++;

					}

				}

				catch (IOException e) {

					e.printStackTrace();

				}

				archivos.clear();

			}

			return true;

		}

	}

	private static class GifFrameSelection implements Transferable {

		public static final DataFlavor GIF_FRAME_FLAVOR = new DataFlavor(GifFrame.class, "Gif Frame");

		private final Object[] transferData;

		public GifFrameSelection(Object[] transferData) {

			this.transferData = transferData;

		}

		public DataFlavor[] getTransferDataFlavors() {

			return new DataFlavor[] { GIF_FRAME_FLAVOR };

		}

		public boolean isDataFlavorSupported(DataFlavor flavor) {

			return GIF_FRAME_FLAVOR.equals(flavor);

		}

		public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {

			if (!isDataFlavorSupported(flavor)) {

				throw new UnsupportedFlavorException(flavor);

			}

			return transferData;

		}

	}

	private static class GifFrameListUI extends BasicListUI {

		private Color dropLineColor = Color.CYAN;

		@Override

		public void paint(Graphics g, JComponent c) {

			super.paint(g, c);

			JList.DropLocation loc = list.getDropLocation();

			if (loc == null || !loc.isInsert()) {

				return;

			}

			int index = list.getUI().locationToIndex(list, loc.getDropPoint());

			Rectangle rect = list.getCellBounds(index, index);

			boolean last = false;

			int size = list.getModel().getSize();

			if (index == size - 1) {

				if (rect == null) {

					rect = new Rectangle(0, 0, list.getFixedCellWidth(), list.getFixedCellHeight());

				}

				else {

					if (loc.getDropPoint().getX() > rect.getX() + rect.getWidth()) {

						last = true;

					}

				}

			}

			g.setColor(dropLineColor);

			g.fillRect(last ? rect.x + rect.width + 1 : rect.x - 1, rect.y, 2, rect.height);

		}

	}

	private static class GifFrameListCellRenderer extends JPanel implements ListCellRenderer<Object> {

		private static final long serialVersionUID = 1L;

		private BufferedImage normal = read(getClass().getResource("resource/normal.png"));

		private BufferedImage pressed = read(getClass().getResource("resource/pressed.png"));

		private GifFrame frame;

		private boolean selected;

		private BufferedImage read(URL input) {

			BufferedImage image = null;

			try {

				image = ImageIO.read(input);

			}

			catch (Exception ex) {

				JOptionPane.showMessageDialog(this, ex, "Exception", JOptionPane.ERROR_MESSAGE);

			}

			return image;

		}

		@Override

		protected void paintComponent(Graphics g) {

			super.paintComponent(g);

			if (frame != null) {

				g.drawImage(frame.getImage(), 10, 12, 58, 58, this);
			}

			g.drawImage(selected ? pressed : normal, 10, 12, 58, 58, this);
		}

		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {

			frame = (GifFrame) value;

			selected = isSelected;

			repaint();

			return this;

		}

		@Override

		public Dimension getPreferredSize() {

			return new Dimension(80, 80);

		}

	}

}
