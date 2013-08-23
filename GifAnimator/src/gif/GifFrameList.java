package gif;

import java.awt.*;
import java.awt.datatransfer.*;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.plaf.basic.BasicListUI;

import java.io.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.imageio.ImageIO;
import java.util.List;

public class GifFrameList extends JList {

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
                    DefaultListModel m = (DefaultListModel) getModel();
                    for (int i = indices.length - 1; i >= 0; i--) {
                        m.remove(indices[i]);
                    }
                }
            }
        });
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        d.height = getFixedCellHeight();
        return d;
    }

    public static final DefaultListModel createDefaultListModel(Object[] listData) {
        DefaultListModel m = new DefaultListModel();
        for (Object o : listData) {
            m.addElement(o);
        }
        return m;
    }

    public void addGifFrame(GifFrame frame) {
        DefaultListModel m = (DefaultListModel) getModel();
        m.addElement(frame);
    }

    public List<GifFrame> getGifFrames() {
        DefaultListModel m = (DefaultListModel) getModel();
        Object[] a = m.toArray();
        List<GifFrame> frames = new ArrayList<GifFrame>();
        for (Object o : a) {
            frames.add((GifFrame) o);
        }
        return frames;
    }

    private static class GifFrameTransferHandler extends TransferHandler {

        @Override
        public int getSourceActions(JComponent c) {
            return TransferHandler.COPY_OR_MOVE;
        }

        @Override
        protected Transferable createTransferable(JComponent c) {
            JList list = (JList) c;
            final Object[] values = list.getSelectedValues();
            return new GifFrameSelection(values);
        }

        @Override
        public boolean canImport(TransferSupport support) {
            if (!support.isDataFlavorSupported(GifFrameSelection.GIF_FRAME_FLAVOR) &&
                    !support.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
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
            JList list = (JList) support.getComponent();

            int index = list.getUI().locationToIndex(list,
                    support.getDropLocation().getDropPoint());
            Rectangle rect = list.getCellBounds(index, index);
            int size = list.getModel().getSize();
            if (index == size - 1) {
                if (rect == null) {
                    index = 0;
                } else {
                    if (support.getDropLocation().getDropPoint().getX() >
                            rect.getX() + rect.getWidth()) {
                        index = size;
                    }
                }
            }
            DefaultListModel m = (DefaultListModel) list.getModel();

            if (support.isDataFlavorSupported(GifFrameSelection.GIF_FRAME_FLAVOR)) {
                Object[] data = null;

                try {
                    data = (Object[]) t.getTransferData(
                            GifFrameSelection.GIF_FRAME_FLAVOR);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(list, ex, "Exception",
                            JOptionPane.ERROR_MESSAGE);
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
                List data = null;

                try {
                    data = (List) t.getTransferData(
                            DataFlavor.javaFileListFlavor);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(list, ex, "Exception",
                            JOptionPane.ERROR_MESSAGE);
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
                        } else {
                            m.add(index, new GifFrame(ImageIO.read(file), 500));
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(list, ex, "Exception",
                                JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                }
            }
            return true;
        }
    }

    private static class GifFrameSelection implements Transferable {

        public static final DataFlavor GIF_FRAME_FLAVOR =
                new DataFlavor(GifFrame.class, "Gif Frame");
        private final Object[] transferData;

        public GifFrameSelection(Object[] transferData) {
            this.transferData = transferData;
        }

        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{GIF_FRAME_FLAVOR};
        }

        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return GIF_FRAME_FLAVOR.equals(flavor);
        }

        public Object getTransferData(DataFlavor flavor)
                throws UnsupportedFlavorException, IOException {
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
                    rect = new Rectangle(0, 0, list.getFixedCellWidth(),
                            list.getFixedCellHeight());
                } else {
                    if (loc.getDropPoint().getX() > rect.getX() + rect.getWidth()) {
                        last = true;
                    }
                }
            }
            g.setColor(dropLineColor);
            g.fillRect(last ? rect.x + rect.width + 1 : rect.x - 1, rect.y,
                    2, rect.height);
        }
    }

    private static class GifFrameListCellRenderer
            extends JPanel implements ListCellRenderer {

        private BufferedImage normal = read(getClass().getResource("resource/normal.png"));
        private BufferedImage pressed = read(getClass().getResource("resource/pressed.png"));
        private GifFrame frame;
        private boolean selected;

        private BufferedImage read(URL input) {
            BufferedImage image = null;
            try {
                image = ImageIO.read(input);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex, "Exception",
                        JOptionPane.ERROR_MESSAGE);
            }
            return image;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (frame != null) {
                g.drawImage(frame.getImage(), 7, 7, 46, 46, this);
            }
            g.drawImage(selected ? pressed : normal, 0, 0, 58, 58, this);
        }

        public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            frame = (GifFrame) value;
            selected = isSelected;
            repaint();
            return this;
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(normal.getWidth(), normal.getHeight());
        }
    }
}
