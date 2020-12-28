
package gif;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import utils.Metodos;

public class GifFramePanel extends JPanel {
	private Animator animator;
	static GifFrame frame;
	public static JLabel image;
	private JPanel imagePanel;
	private JScrollPane imageScrollPane;
	private JCheckBox loop;
	private JButton preview;
	static JLabel size;
	public static JTextField fps;
	private JTextField texto;

	public GifFramePanel(Animator animator) {

		this.animator = animator;

		initComponents();

	}

	private void initComponents() {

		imageScrollPane = new JScrollPane();

		imagePanel = new JPanel();

		image = new JLabel();

		preview = new JButton();

		preview.setIcon(new ImageIcon(GifFramePanel.class.getResource("/images/view.png")));

		loop = new JCheckBox();

		loop.setFont(new Font("Dialog", Font.PLAIN, 20));

		imagePanel.setLayout(new java.awt.BorderLayout());

		image.setHorizontalAlignment(SwingConstants.CENTER);
		imagePanel.add(image, java.awt.BorderLayout.CENTER);

		imageScrollPane.setViewportView(imagePanel);
		preview.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				previewActionPerformed(evt);
			}
		});

		loop.setSelected(true);
		loop.setText("Loop Continuously");
		size = new JLabel();
		size.setFont(new Font("Dialog", Font.PLAIN, 20));
		size.setHorizontalAlignment(SwingConstants.CENTER);

		fps = new JTextField();

		fps.addKeyListener(new KeyAdapter() {

			@Override

			public void keyReleased(KeyEvent e) {

				try {

					String texto = fps.getText();

					if (texto.isEmpty() || Integer.parseInt(texto) <= 0) {
						fps.setText("10");
					}

				}

				catch (Exception e1) {

					fps.setText("10");
				}

			}

		});

		fps.setText("10");

		fps.setHorizontalAlignment(SwingConstants.CENTER);

		fps.setFont(new Font("Dialog", Font.PLAIN, 18));

		fps.setColumns(10);

		JLabel lblNewLabel = new JLabel("FPS");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

		lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 20));

		JButton preview_1 = new JButton();

		preview_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				if (!Animator.lista.m.isEmpty()) {

					int resp = JOptionPane.showConfirmDialog(null, "¿Quieres borrar todos los frames?");

					if (resp == 0) {
						Animator.lista.m.clear();
					}

				}

			}

		});

		preview_1.setIcon(new ImageIcon(GifFramePanel.class.getResource("/images/clean.png")));

		texto = new JTextField();
		texto.setHorizontalAlignment(SwingConstants.CENTER);
		texto.setFont(new Font("Dialog", Font.PLAIN, 16));
		texto.setColumns(10);

		JButton btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon(GifFramePanel.class.getResource("/gif/resource/pressed.png")));

		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				String dato = texto.getText().trim();

				dato = Metodos.eliminarEspacios(dato);

				if (!dato.isEmpty()) {

					GifFrame[] frames = new GifFrame[] { Animator.createDemoGifFrame(46, 46, dato, 200) };

					Animator.animator.addGifFrame(frames[0]);
				}

			}

		});

		GroupLayout layout = new GroupLayout(this);
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
						.addGap(21).addComponent(preview, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
						.addGap(18).addComponent(preview_1, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
						.addGap(18).addComponent(texto, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
								.addComponent(fps, GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)))
						.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(imageScrollPane,
								GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)))
				.addContainerGap())
				.addGroup(layout.createSequentialGroup().addContainerGap(171, Short.MAX_VALUE).addComponent(loop)
						.addGap(157))
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addComponent(size, GroupLayout.PREFERRED_SIZE, 514, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
				.addContainerGap().addComponent(loop).addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(preview, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
								.addComponent(preview_1, GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE))
						.addGap(31))
						.addGroup(Alignment.TRAILING,
								layout.createSequentialGroup().addComponent(lblNewLabel).addGap(18)
										.addComponent(fps, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addGap(7)))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(size, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED).addComponent(imageScrollPane, GroupLayout.PREFERRED_SIZE,
						476, GroupLayout.PREFERRED_SIZE)
				.addContainerGap()).addGroup(
						layout.createSequentialGroup().addGap(58)
								.addGroup(
										layout.createParallelGroup(Alignment.LEADING)
												.addGroup(layout.createSequentialGroup()
														.addComponent(texto, GroupLayout.PREFERRED_SIZE, 71,
																GroupLayout.PREFERRED_SIZE)
														.addContainerGap())
												.addGroup(layout
														.createSequentialGroup().addComponent(btnNewButton,
																GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
														.addGap(528)))));
		this.setLayout(layout);
	}

	private void previewActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_previewActionPerformed

		fps.setEditable(false);

		if (Animator.lista.m.size() > 0) {

			final JDialog d = new JDialog((JFrame) null, "Animation");

			d.getContentPane().add(new AnimationPanel(animator.getGifFrames(), loop()));

			JPanel p = new JPanel();

			d.setType(Type.POPUP);

			JButton b = new JButton("OK");

			b.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					d.dispose();
				}

			});

			p.add(b);

			d.getContentPane().add(p, BorderLayout.SOUTH);

			d.pack();

			d.setLocationRelativeTo(this);

			d.setVisible(true);

		}

		fps.setEditable(true);

	}

	public void setGifFrame(GifFrame frame) {

		GifFramePanel.frame = frame;

		if (frame != null) {

			image.setIcon(new ImageIcon(frame.getImage()));

			size.setText(Integer.toString(frame.getImage().getWidth()) + " x "
					+ Integer.toString(frame.getImage().getHeight()));

		} else {
			image.setIcon(null);
			size.setText("");

		}
	}

	public boolean loop() {
		return loop.isSelected();
	}
}
