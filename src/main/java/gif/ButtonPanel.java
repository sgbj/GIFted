
package gif;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import utils.Metodos;

public class ButtonPanel extends javax.swing.JPanel {

	private static final long serialVersionUID = 1L;

	private static Animator animator;

	private javax.swing.JButton open;

	private javax.swing.JButton save;

	private JSeparator separator;

	private JSeparator separator_1;

	public static JCheckBox loop;

	public static String archivoGif;

	public static int archivos;

	private JLabel lblNewLabel;

	private JSeparator separator_2;

	private static boolean gif = false;

	static LinkedList<String> carpetasSeleccion = new LinkedList<String>();

	public ButtonPanel(Animator animator) {

		ButtonPanel.animator = animator;

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

		separator = new JSeparator();
		add(separator);

		add(open_1);

		separator_1 = new JSeparator();
		add(separator_1);

		add(save);

		separator_2 = new JSeparator();
		add(separator_2);

		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(ButtonPanel.class.getResource("/images/repeat.png")));
		add(lblNewLabel);

		loop = new JCheckBox();
		loop.setText("Loop");
		loop.setSelected(true);
		loop.setFont(new Font("Tahoma", Font.PLAIN, 22));
		add(loop);

	}

	private void openActionPerformed(java.awt.event.ActionEvent evt, boolean carpeta) {

		JFileChooser fc = new JFileChooser();

		fc.setDialogTitle("Multiple selection");

		fc.setMultiSelectionEnabled(true);

		fc.addChoosableFileFilter(new FileNameExtensionFilter("Images (jpg,png,gif)", "jpg", "png", "gif"));

		fc.addChoosableFileFilter(new FileNameExtensionFilter("Gif", "gif"));

		if (carpeta) {

			fc.setCurrentDirectory(new java.io.File("."));

			fc.setDialogTitle("Elige una carpeta");

			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

			fc.setAcceptAllFileFilterUsed(false);

		}

		fc.setAcceptAllFileFilterUsed(true);

		int o = fc.showOpenDialog(getParent());

		if (o == JFileChooser.APPROVE_OPTION) {

			addImages(carpeta, fc.getSelectedFiles());

		}

	}

	public static void addImages(boolean carpeta, File[] fc) {

		LinkedList<File> files = new LinkedList<File>();

		int vueltas = 1;

		if (!carpeta) {

			files.clear();

			Arrays.asList(fc).forEach(x -> {

				if (x.isFile()) {

					String extension = Metodos.extraerExtension(x.getAbsolutePath());

					if (extension.equals("jpg") || extension.equals("png") || extension.equals("gif")) {

						if (extension.equals("gif")) {

							archivoGif = x.getAbsolutePath();

							gif = true;

						}

						else {

							if (!gif) {
								archivos++;
							}

						}

						files.add(new File(x.getAbsolutePath()));

					}

				}

			});

		}

		else {

			files.clear();

			Arrays.asList(fc).forEach(x -> {

				if (x.isDirectory()) {

					carpetasSeleccion = Metodos.directorio(x.getAbsolutePath() + Animator.getSeparador(), "images",
							true, true);

					for (int i = 0; i < carpetasSeleccion.size(); i++) {

						files.add(new File(carpetasSeleccion.get(i)));

					}

				}

			});

		}

		try {

			vueltas = files.size();

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

	private void saveActionPerformed(java.awt.event.ActionEvent evt) {

		if (animator.getGifFrames().size() > 1) {

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
					Gif.write(animator.getGifFrames(), loop.isSelected(), file);

				}

				catch (Exception ex) {
					JOptionPane.showMessageDialog(this, ex, "Exception", JOptionPane.ERROR_MESSAGE);
				}

			}

		}
	}
}
