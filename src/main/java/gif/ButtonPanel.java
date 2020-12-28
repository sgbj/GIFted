
package gif;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import utils.Metodos;

public class ButtonPanel extends javax.swing.JPanel {

	private Animator animator;

	public ButtonPanel(Animator animator) {

		this.animator = animator;

		initComponents();

	}

	private void initComponents() {

		open = new javax.swing.JButton();

		open.setIcon(new ImageIcon(ButtonPanel.class.getResource("/images/abrir.png")));

		open.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				openActionPerformed(evt, false);
			}

		});

		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		add(open);

		save = new javax.swing.JButton();

		save.setIcon(new ImageIcon(ButtonPanel.class.getResource("/images/save.png")));

		save.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveActionPerformed(evt);
			}

		});

		JButton open_1 = new JButton();

		open_1.setIcon(new ImageIcon(ButtonPanel.class.getResource("/images/folder.png")));

		open_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				openActionPerformed(arg0, true);
			}

		});

		add(open_1);

		add(save);

	}

	private void openActionPerformed(java.awt.event.ActionEvent evt, boolean carpeta) {

		JFileChooser fc = new JFileChooser();

		int vueltas = 1;

		LinkedList<File> files = new LinkedList<File>();

		if (carpeta) {

			fc.setCurrentDirectory(new java.io.File("."));

			fc.setDialogTitle("choosertitle");

			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

			fc.setAcceptAllFileFilterUsed(false);

		}

		int o = fc.showOpenDialog(getParent());

		if (o == JFileChooser.APPROVE_OPTION) {

			if (!carpeta) {
				files.add(fc.getSelectedFile());
			}

			else {

				LinkedList<String> archivos = new LinkedList<String>();

				archivos = Metodos.directorio(fc.getSelectedFile().toString() + "/", ".", true, true, false);

				for (int i = 0; i < archivos.size(); i++) {

					files.add(new File(archivos.get(i)));

				}

				vueltas = files.size();

			}

			try {

				for (int i = 0; i < vueltas; i++) {

					if (files.get(i).getName().endsWith("gif")) {

						List<GifFrame> frames = Gif.read(files.get(i));

						for (GifFrame frame : frames) {
							animator.addGifFrame(frame);
						}

					}

					else {

						BufferedImage image = ImageIO.read(files.get(i));

						animator.addGifFrame(new GifFrame(image, 500));
					}

				}

			}

			catch (Exception ex) {
				
			}

		}

	}

	private void saveActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_saveActionPerformed

		JFileChooser fc = new JFileChooser();

		fc.setSelectedFile(new File("*.gif"));

		fc.setFileFilter(new FileFilter() {

			@Override
			public boolean accept(File f) {
				return f.getName().toLowerCase().endsWith(".gif");
			}

			@Override
			public String getDescription() {
				return "GIF (*.gif)";
			}

		});

		int o = fc.showSaveDialog(getParent());

		if (o == JFileChooser.APPROVE_OPTION) {

			File file = fc.getSelectedFile();

			try {
				Gif.write(animator.getGifFrames(), animator.loop(), file);

			}

			catch (Exception ex) {
				JOptionPane.showMessageDialog(this, ex, "Exception", JOptionPane.ERROR_MESSAGE);
			}

		}

	}

	private javax.swing.JButton open;

	private javax.swing.JButton save;

}
