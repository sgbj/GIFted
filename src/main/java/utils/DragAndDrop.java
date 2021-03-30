package utils;

import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetEvent;
import java.io.PrintStream;
import java.util.TooManyListenersException;

public class DragAndDrop {

	private java.awt.dnd.DropTargetListener dropListener;

	public DragAndDrop(final java.awt.Component c, final javax.swing.border.Border dragBorder, final boolean recursive,
			final Listener listener) throws TooManyListenersException {

		dropListener = new java.awt.dnd.DropTargetListener() {

			public void dragEnter(java.awt.dnd.DropTargetDragEvent evt) {

				if (isDragOk(evt)) {

					if (c instanceof javax.swing.JComponent) {
						javax.swing.JComponent jc = (javax.swing.JComponent) c;

						jc.setBorder(dragBorder);

					}

					evt.acceptDrag(java.awt.dnd.DnDConstants.ACTION_COPY);

				} else {
					evt.rejectDrag();

				}
			}

			public void drop(java.awt.dnd.DropTargetDropEvent evt) {

				try {
					java.awt.datatransfer.Transferable tr = evt.getTransferable();

					if (tr.isDataFlavorSupported(java.awt.datatransfer.DataFlavor.javaFileListFlavor)) {

						evt.acceptDrop(java.awt.dnd.DnDConstants.ACTION_COPY);

						java.util.List<?> fileList = (java.util.List<?>) tr
								.getTransferData(java.awt.datatransfer.DataFlavor.javaFileListFlavor);

						java.io.File[] filesTemp = new java.io.File[fileList.size()];
						fileList.toArray(filesTemp);
						final java.io.File[] files = filesTemp;

						if (listener != null) {
							listener.filesDropped(files);
						}
						evt.getDropTargetContext().dropComplete(true);

					}
				}

				catch (Exception io) {

				}

			}

			public void dragExit(DropTargetEvent dte) {
				//
			}

			public void dragOver(DropTargetDragEvent dtde) {
				//
			}

			public void dropActionChanged(DropTargetDragEvent dtde) {
				//
			}
		};

		PrintStream out = null;
		makeDropTarget(out, c, recursive);

	}

	private void makeDropTarget(final java.io.PrintStream out, final java.awt.Component c, boolean recursive)
			throws TooManyListenersException {

		final java.awt.dnd.DropTarget dt = new java.awt.dnd.DropTarget();

		dt.addDropTargetListener(dropListener);

		c.addHierarchyListener(new java.awt.event.HierarchyListener() {

			public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {

				java.awt.Component parent = c.getParent();

				if (parent == null) {

					c.setDropTarget(null);
				}

				else {
					new java.awt.dnd.DropTarget(c, dropListener);
				}

			}
		});
		if (c.getParent() != null)
			new java.awt.dnd.DropTarget(c, dropListener);

		if (recursive && (c instanceof java.awt.Container)) {

			java.awt.Container cont = (java.awt.Container) c;
			java.awt.Component[] comps = cont.getComponents();

			for (int i = 0; i < comps.length; i++) {
				makeDropTarget(out, comps[i], recursive);
			}
		}
	}

	private boolean isDragOk(final java.awt.dnd.DropTargetDragEvent evt) {

		java.awt.datatransfer.DataFlavor[] flavors = null;

		try {
			flavors = evt.getCurrentDataFlavors();
		} catch (Exception e) {
			return (false);
		}

		boolean ok = false;
		int i = 0;
		while (!ok && i < flavors.length) {
			if (flavors[i].equals(java.awt.datatransfer.DataFlavor.javaFileListFlavor))
				ok = true;
			i++;
		}

		return ok;
	}

	public interface Listener {
		public abstract void filesDropped(java.io.File[] files);
	}
}