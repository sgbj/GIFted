package gif;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TooManyListenersException;

import javax.swing.DefaultListCellRenderer;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import utils.DragAndDrop;
import utils.Metodos;

@SuppressWarnings("all")

public class Config extends javax.swing.JFrame implements ActionListener, ChangeListener {

	private JTextField escala;
	private JTextField perdida;

	private JTextField delay;

	private JTextField optimize;

	private JTextField colores;

	private JTextField ancho;

	private JTextField alto;

	private JTextField loopCount;

	private JComboBox bn;

	private JRadioButton fit, touch, horizontal, vertical;

	private JComboBox rotacion;

	private JComboBox merge;

	private JCheckBox flip;

	private JTextField sizeCrop;

	private JTextField espacioTop;

	private JTextField espacioLeft;

	private JCheckBox crop;

	public Config() {

		setIconImage(Toolkit.getDefaultToolkit().getImage(Config.class.getResource("/images/settings.png")));

		setTitle("Gif Animator - Config");

		setType(Type.UTILITY);

		initComponents();

		leer();

		this.setVisible(true);

	}

	private void reset() {

		try {

			colores.setText("128");

			optimize.setText("0");

			escala.setText("1");

			perdida.setText("0");

			delay.setText("0");

			loopCount.setText("0");

			ancho.setText("");

			alto.setText("");

			espacioLeft.setText("0");

			espacioTop.setText("0");

			guardarDatos();

		}

		catch (Exception e) {
			
		}

	}

	private void leer() {

		try {

			ButtonPanel.setLectura(Metodos.leerFicheroArray("ConfigEasyGifCreator.txt", 19));

			colores.setText(ButtonPanel.getLectura()[0]);

			optimize.setText(ButtonPanel.getLectura()[1]);

			escala.setText(ButtonPanel.getLectura()[2]);

			perdida.setText(ButtonPanel.getLectura()[3]);

			delay.setText(ButtonPanel.getLectura()[4]);

			loopCount.setText(ButtonPanel.getLectura()[5]);

			ancho.setText(ButtonPanel.getLectura()[6]);

			alto.setText(ButtonPanel.getLectura()[7]);

			if (ButtonPanel.getLectura()[8].equals("1")) {

				fit.setSelected(true);

			}

			else {
				fit.setSelected(false);
			}

			if (ButtonPanel.getLectura()[9].equals("1")) {

				touch.setSelected(true);

			}

			else {
				touch.setSelected(false);
			}

			rotacion.setSelectedItem(ButtonPanel.getLectura()[10]);

			merge.setSelectedItem(ButtonPanel.getLectura()[11]);

			if (ButtonPanel.getLectura()[12].equals("1")) {

				flip.setSelected(true);

			}

			else {
				flip.setSelected(false);
			}

			if (ButtonPanel.getLectura()[13].equals("horizontal")) {

				horizontal.setSelected(true);

			}

			if (ButtonPanel.getLectura()[13].equals("vertical")) {
				vertical.setSelected(true);
			}

			bn.setSelectedItem(ButtonPanel.getLectura()[14]);

			if (ButtonPanel.getLectura()[15].equals("1")) {

				crop.setSelected(true);

			}

			else {
				crop.setSelected(false);
			}

			sizeCrop.setText(ButtonPanel.getLectura()[16]);

			espacioLeft.setText(ButtonPanel.getLectura()[17]);

			espacioTop.setText(ButtonPanel.getLectura()[18]);

		}

		catch (Exception e) {

		}

	}

	public void guardarDatos() throws IOException {

		try {

			int color = (int) Metodos.saberNumero(colores.getText().trim());

			int optimizar = (int) Metodos.saberNumero(optimize.getText().trim());

			String scala = escala.getText().trim();

			int lossy = (int) Metodos.saberNumero(perdida.getText().trim());

			int retardo = (int) Metodos.saberNumero(delay.getText().trim());

			int loop = (int) Metodos.saberNumero(loopCount.getText().trim());

			int width = (int) Metodos.saberNumero(ancho.getText().trim());

			int height = (int) Metodos.saberNumero(alto.getText().trim());

			int spaceLeft = (int) Metodos.saberNumero(espacioLeft.getText().trim());

			int spaceTop = (int) Metodos.saberNumero(espacioTop.getText().trim());

			FileWriter flS = new FileWriter("ConfigEasyGifCreator.txt");

			BufferedWriter fS = new BufferedWriter(flS);

			String cadena = "";

			fS.write(cadena + color);

			fS.newLine();

			fS.write(cadena + optimizar);

			fS.newLine();

			fS.write(cadena + scala);

			fS.newLine();

			fS.write(cadena + lossy);

			fS.newLine();

			fS.write(cadena + retardo);

			fS.newLine();

			fS.write(cadena + loop);

			fS.newLine();

			fS.write(cadena + width);

			fS.newLine();

			fS.write(cadena + height);

			fS.newLine();

			fS.write(booleanoATexto(fit.isSelected()));

			fS.newLine();

			fS.write(booleanoATexto(touch.isSelected()));

			fS.newLine();

			fS.write(rotacion.getSelectedItem().toString());

			fS.newLine();

			fS.write(merge.getSelectedItem().toString());

			fS.newLine();

			fS.write(booleanoATexto(flip.isSelected()));

			fS.newLine();

			if (horizontal.isSelected()) {
				fS.write("horizontal");
			}

			else {
				fS.write("vertical");
			}

			fS.newLine();

			fS.write(bn.getSelectedItem().toString());

			fS.newLine();

			fS.write(booleanoATexto(crop.isSelected()));

			fS.newLine();

			fS.write(sizeCrop.getText().trim());

			fS.newLine();

			fS.write(cadena + spaceLeft);

			fS.newLine();

			fS.write(cadena + spaceTop);

			fS.close();

			flS.close();

			dispose();

			leer();

		}

		catch (Exception e) {

			reset();

		}

	}

	private String booleanoATexto(boolean selected) {

		String resultado = "0";

		if (selected) {
			resultado = "1";
		}

		return resultado;

	}

	private void initComponents() {

		DefaultListCellRenderer listRenderer;

		listRenderer = new DefaultListCellRenderer();

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

		setResizable(false);

		JLabel lblNewLabel = new JLabel(" Number of colors");

		lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 18));

		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);

		lblNewLabel.setIcon(new ImageIcon(Config.class.getResource("/images/color.png")));

		merge = new JComboBox();

		merge.addItem("None");

		merge.addItem("Merge");

		merge.addItem("Interlace");

		merge.setRenderer(listRenderer);

		merge.setFont(new Font("Dialog", Font.PLAIN, 20));

		JButton btnNewButton = new JButton("");

		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				try {
					guardarDatos();
				}

				catch (Exception e) {
					
				}

			}

		});

		btnNewButton.setIcon(new ImageIcon(Config.class.getResource("/images/guardar.png")));

		escala = new JTextField();

		escala.setFont(new Font("Dialog", Font.PLAIN, 18));

		escala.setHorizontalAlignment(SwingConstants.CENTER);

		escala.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel(" 0  -");

		lblNewLabel_2.setFont(new Font("Dialog", Font.PLAIN, 20));

		JLabel lblNewLabel_3 = new JLabel("Scale");

		lblNewLabel_3.setFont(new Font("Dialog", Font.PLAIN, 18));

		lblNewLabel_3.setIcon(new ImageIcon(Config.class.getResource("/images/regla.png")));

		JLabel lblNewLabel_4 = new JLabel("Delay");

		lblNewLabel_4.setFont(new Font("Dialog", Font.PLAIN, 18));

		lblNewLabel_4.setIcon(new ImageIcon(Config.class.getResource("/images/delay.png")));

		perdida = new JTextField();
		perdida.setFont(new Font("Dialog", Font.PLAIN, 18));

		perdida.setHorizontalAlignment(SwingConstants.CENTER);

		perdida.setColumns(10);

		delay = new JTextField();
		delay.setFont(new Font("Dialog", Font.PLAIN, 18));

		delay.setHorizontalAlignment(SwingConstants.CENTER);

		delay.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("Lossy");

		lblNewLabel_5.setIcon(new ImageIcon(Config.class.getResource("/images/lossy.png")));

		lblNewLabel_5.setFont(new Font("Dialog", Font.PLAIN, 20));

		optimize = new JTextField();
		optimize.setFont(new Font("Dialog", Font.PLAIN, 18));

		optimize.setHorizontalAlignment(SwingConstants.CENTER);

		optimize.setColumns(10);

		rotacion = new JComboBox();

		listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);

		rotacion.setRenderer(listRenderer);

		setResizable(false);

		rotacion.setFont(new Font("Dialog", Font.PLAIN, 18));

		rotacion.addItem("None");

		rotacion.addItem("90");

		rotacion.addItem("180");

		rotacion.addItem("270");

		JCheckBox lblNewLabel_6 = new JCheckBox("Rotate");
		lblNewLabel_6.setSelected(true);

		lblNewLabel_6.setFont(new Font("Dialog", Font.PLAIN, 18));

		lblNewLabel_6.setIcon(new ImageIcon(Config.class.getResource("/images/rotate_180.png")));

		JTextArea imagenes = new JTextArea();

		imagenes.setText("Drag and Drop here");

		imagenes.setForeground(SystemColor.desktop);

		imagenes.setFont(new Font("Tahoma", Font.PLAIN, 26));

		imagenes.setEditable(false);

		imagenes.setBackground(SystemColor.windowBorder);

		colores = new JTextField();

		colores.setFont(new Font("Dialog", Font.PLAIN, 18));

		colores.setHorizontalAlignment(SwingConstants.CENTER);

		colores.setColumns(10);

		JLabel lblNewLabel_8 = new JLabel("Optimize");

		lblNewLabel_8.setFont(new Font("Dialog", Font.PLAIN, 18));

		lblNewLabel_8.setIcon(new ImageIcon(Config.class.getResource("/images/utilities.png")));

		bn = new JComboBox();

		bn.addItem("None");

		bn.addItem("Black And White");

		bn.addItem("Gray");

		bn.addItem("Web");

		bn.setFont(new Font("Dialog", Font.PLAIN, 19));

		bn.setRenderer(listRenderer);

		JLabel lblNewLabel_9 = new JLabel("");

		lblNewLabel_9.setIcon(new ImageIcon(Config.class.getResource("/images/width.png")));

		ancho = new JTextField();

		ancho.setHorizontalAlignment(SwingConstants.CENTER);

		ancho.setFont(new Font("Dialog", Font.PLAIN, 18));

		ancho.setColumns(10);

		JLabel lblNewLabel_9_1 = new JLabel("");
		lblNewLabel_9_1.setIcon(new ImageIcon(Config.class.getResource("/images/height.png")));

		alto = new JTextField();

		alto.setHorizontalAlignment(SwingConstants.CENTER);

		alto.setFont(new Font("Dialog", Font.PLAIN, 18));

		alto.setColumns(10);

		JLabel resize = new JLabel(" Resize");
		resize.setHorizontalAlignment(SwingConstants.CENTER);

		resize.setFont(new Font("Dialog", Font.PLAIN, 18));

		resize.setIcon(new ImageIcon(Config.class.getResource("/images/resize.png")));

		JLabel lblNewLabel_11 = new JLabel("");
		lblNewLabel_11.setHorizontalAlignment(SwingConstants.CENTER);

		lblNewLabel_11.setIcon(new ImageIcon(Config.class.getResource("/images/30-07-2018 1-07-31.png")));

		fit = new JRadioButton("Fit");
		fit.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (!fit.isSelected()) {
					touch.setSelected(false);
				}
			}
		});

		fit.setHorizontalAlignment(SwingConstants.CENTER);

		fit.setFont(new Font("Dialog", Font.PLAIN, 18));

		touch = new JRadioButton("Touch");
		touch.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (!touch.isSelected()) {
					fit.setSelected(false);
				}
			}
		});

		touch.setHorizontalAlignment(SwingConstants.CENTER);

		touch.setFont(new Font("Dialog", Font.PLAIN, 18));

		JLabel lblNewLabel_12 = new JLabel("Loop Count");

		lblNewLabel_12.setIcon(new ImageIcon(Config.class.getResource("/images/loop.png")));

		lblNewLabel_12.setFont(new Font("Dialog", Font.PLAIN, 18));

		loopCount = new JTextField();
		loopCount.setFont(new Font("Dialog", Font.PLAIN, 18));

		loopCount.setHorizontalAlignment(SwingConstants.CENTER);

		loopCount.setColumns(10);

		JButton btnNewButton_1 = new JButton("");

		btnNewButton_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				if (merge.getSelectedItem().toString().equals("Merge")) {

					ButtonPanel.listaImagenes.clear();

				}

				if (merge.getSelectedItem().toString().equals("Interlace")) {

					ButtonPanel.listaImagenesInterlace.clear();

				}

			}

		});

		btnNewButton_1.setIcon(new ImageIcon(Config.class.getResource("/images/clean.png")));

		horizontal = new JRadioButton("Horizontal");

		horizontal.addMouseListener(new MouseAdapter() {

			@Override

			public void mousePressed(MouseEvent e) {

				if (!horizontal.isSelected()) {
					vertical.setSelected(false);
				}

				else {

					if (!vertical.isSelected()) {
						horizontal.setSelected(false);
					}

				}

			}

		});

		horizontal.setFont(new Font("Dialog", Font.PLAIN, 18));

		vertical = new JRadioButton("Vertical");

		vertical.addMouseListener(new MouseAdapter() {

			@Override

			public void mousePressed(MouseEvent e) {

				if (!vertical.isSelected()) {
					horizontal.setSelected(false);
				}

				else {

					if (!horizontal.isSelected()) {
						vertical.setSelected(false);
					}

				}

			}

		});

		vertical.setFont(new Font("Dialog", Font.PLAIN, 19));

		JLabel lblNewLabel_14 = new JLabel("");

		JLabel lblNewLabel_15 = new JLabel("");

		lblNewLabel_15.setIcon(new ImageIcon(Config.class.getResource("/images/flip_h.png")));

		JLabel lblNewLabel_16 = new JLabel("");

		lblNewLabel_16.setIcon(new ImageIcon(Config.class.getResource("/images/flip_v.png")));

		flip = new JCheckBox("Flip");
		flip.setVerticalAlignment(SwingConstants.BOTTOM);
		flip.setHorizontalAlignment(SwingConstants.CENTER);

		flip.setFont(new Font("Dialog", Font.PLAIN, 20));

		JLabel lblNewLabel_7 = new JLabel("Join");
		lblNewLabel_7.setFont(new Font("Dialog", Font.PLAIN, 18));
		lblNewLabel_7.setIcon(new ImageIcon(Config.class.getResource("/images/merge.png")));

		crop = new JCheckBox("Crop");
		crop.setFont(new Font("Dialog", Font.PLAIN, 18));

		sizeCrop = new JTextField();
		sizeCrop.setFont(new Font("Dialog", Font.PLAIN, 18));
		sizeCrop.setHorizontalAlignment(SwingConstants.CENTER);
		sizeCrop.setColumns(10);

		espacioTop = new JTextField();
		espacioTop.setHorizontalAlignment(SwingConstants.CENTER);
		espacioTop.setFont(new Font("Dialog", Font.PLAIN, 18));
		espacioTop.setColumns(10);

		espacioLeft = new JTextField();
		espacioLeft.setHorizontalAlignment(SwingConstants.CENTER);
		espacioLeft.setFont(new Font("Dialog", Font.PLAIN, 18));
		espacioLeft.setColumns(10);

		JLabel textCrop = new JLabel("Width x Height");
		textCrop.setFont(new Font("Dialog", Font.PLAIN, 18));
		textCrop.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblNewLabel_13 = new JLabel("Left space");
		lblNewLabel_13.setFont(new Font("Dialog", Font.PLAIN, 18));
		lblNewLabel_13.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblNewLabel_13_1 = new JLabel("Top space");
		lblNewLabel_13_1.setFont(new Font("Dialog", Font.PLAIN, 18));
		lblNewLabel_13_1.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Config.class.getResource("/images/infinity.png")));

		JLabel lblNewLabel_17 = new JLabel("");
		lblNewLabel_17.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_17.setIcon(new ImageIcon(Config.class.getResource("/images/extractframes.png")));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.TRAILING).addGroup(layout.createSequentialGroup()
				.addGap(26).addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addGap(7)
								.addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout
										.createSequentialGroup()
										.addGroup(layout.createParallelGroup(Alignment.LEADING)
												.addGroup(layout.createSequentialGroup()
														.addComponent(crop, GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addGap(18))
												.addGroup(layout.createSequentialGroup().addGap(12)
														.addComponent(lblNewLabel_17)
														.addPreferredGap(ComponentPlacement.RELATED)))
										.addGroup(layout.createParallelGroup(Alignment.LEADING)
												.addComponent(textCrop, GroupLayout.PREFERRED_SIZE, 163,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(sizeCrop, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
										.addGroup(layout.createSequentialGroup().addGap(5)
												.addGroup(layout.createParallelGroup(Alignment.LEADING)
														.addComponent(lblNewLabel_12).addComponent(lblNewLabel_6)
														.addComponent(lblNewLabel_4).addComponent(lblNewLabel_5)
														.addComponent(lblNewLabel_8).addComponent(lblNewLabel_3))))
								.addGap(55))
						.addComponent(lblNewLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE))
				.addPreferredGap(
						ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addComponent(lblNewLabel_13_1, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED))
						.addComponent(colores, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
						.addComponent(lblNewLabel_13, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
						.addComponent(loopCount, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
						.addComponent(delay, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
						.addComponent(rotacion, 0, 126, Short.MAX_VALUE)
						.addComponent(espacioLeft, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
						.addComponent(espacioTop, 0, 0, Short.MAX_VALUE)
						.addComponent(optimize, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
						.addComponent(escala, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
						.addComponent(perdida, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE))
				.addGroup(layout
						.createParallelGroup(
								Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(Alignment.TRAILING).addComponent(lblNewLabel_15)
										.addGroup(layout.createSequentialGroup()
												.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 38,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(lblNewLabel_1))
										.addComponent(lblNewLabel_9))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
										.addGroup(layout.createSequentialGroup().addGroup(layout
												.createParallelGroup(Alignment.LEADING)
												.addGroup(layout.createSequentialGroup().addGap(6)
														.addComponent(fit, GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addGap(87))
												.addGroup(layout.createSequentialGroup().addGap(12)
														.addComponent(ancho, GroupLayout.PREFERRED_SIZE, 96,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)))
												.addGroup(layout.createParallelGroup(Alignment.TRAILING).addGroup(layout
														.createSequentialGroup()
														.addComponent(lblNewLabel_9_1, GroupLayout.PREFERRED_SIZE, 33,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.UNRELATED)
														.addComponent(alto, GroupLayout.PREFERRED_SIZE, 96,
																GroupLayout.PREFERRED_SIZE))
														.addComponent(
																touch, GroupLayout.PREFERRED_SIZE, 149,
																GroupLayout.PREFERRED_SIZE))
												.addGap(205).addComponent(lblNewLabel_14).addGap(154))
										.addGroup(layout.createSequentialGroup().addGroup(layout
												.createParallelGroup(Alignment.TRAILING)
												.addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout
														.createSequentialGroup()
														.addComponent(imagenes, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addGap(18).addComponent(btnNewButton_1,
																GroupLayout.PREFERRED_SIZE, 58,
																GroupLayout.PREFERRED_SIZE))
														.addGroup(layout.createSequentialGroup()
																.addComponent(lblNewLabel_7)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(merge, GroupLayout.PREFERRED_SIZE, 168,
																		GroupLayout.PREFERRED_SIZE)))
												.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 54,
														GroupLayout.PREFERRED_SIZE)
												.addGroup(layout.createSequentialGroup().addComponent(horizontal)
														.addGap(47).addComponent(lblNewLabel_16).addGap(18)
														.addComponent(vertical))
												.addGroup(layout.createSequentialGroup()
														.addComponent(flip, GroupLayout.PREFERRED_SIZE, 293,
																GroupLayout.PREFERRED_SIZE)
														.addGap(32)))
												.addPreferredGap(ComponentPlacement.RELATED, 169, Short.MAX_VALUE))))
						.addGroup(layout.createSequentialGroup().addGap(106).addComponent(resize,
								GroupLayout.PREFERRED_SIZE, 269, GroupLayout.PREFERRED_SIZE))
						.addGroup(layout.createSequentialGroup().addGap(132).addComponent(bn,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(layout.createSequentialGroup().addGap(186).addComponent(lblNewLabel_11)))
				.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel)
								.addComponent(colores, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_7).addComponent(merge, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(layout.createParallelGroup(Alignment.TRAILING).addComponent(lblNewLabel_13)
								.addComponent(textCrop))
						.addGap(18)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addComponent(lblNewLabel_17).addGap(22)
										.addComponent(crop))
								.addGroup(
										layout.createParallelGroup(Alignment.BASELINE)
												.addComponent(sizeCrop, GroupLayout.PREFERRED_SIZE, 134,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(lblNewLabel_13_1))
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(Alignment.TRAILING).addGroup(layout
												.createSequentialGroup()
												.addComponent(espacioLeft, GroupLayout.PREFERRED_SIZE, 34,
														GroupLayout.PREFERRED_SIZE)
												.addGap(35))
												.addGroup(layout.createSequentialGroup()
														.addGroup(layout.createParallelGroup(Alignment.LEADING)
																.addComponent(btnNewButton_1).addComponent(imagenes,
																		GroupLayout.PREFERRED_SIZE, 60,
																		GroupLayout.PREFERRED_SIZE))
														.addGap(18)))
										.addGap(20)
										.addGroup(
												layout.createParallelGroup(Alignment.BASELINE).addComponent(flip)
														.addComponent(espacioTop, GroupLayout.PREFERRED_SIZE, 36,
																GroupLayout.PREFERRED_SIZE))))
						.addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
										.addGroup(
												layout.createParallelGroup(Alignment.TRAILING).addComponent(horizontal)
														.addComponent(lblNewLabel_15).addComponent(vertical))
										.addGroup(
												layout.createSequentialGroup().addGap(11).addComponent(lblNewLabel_16)))
								.addGap(24))
								.addGroup(layout
										.createSequentialGroup().addGap(18)
										.addGroup(
												layout.createParallelGroup(Alignment.BASELINE)
														.addComponent(lblNewLabel_8)
														.addComponent(
																optimize, GroupLayout.PREFERRED_SIZE, 41,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED)))
						.addGroup(layout.createParallelGroup(Alignment.TRAILING)
								.addGroup(layout.createSequentialGroup().addGap(6)
										.addGroup(layout.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 58,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														escala, GroupLayout.PREFERRED_SIZE, 39,
														GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addGroup(layout.createParallelGroup(Alignment.TRAILING).addGroup(layout
												.createSequentialGroup().addComponent(lblNewLabel_5)
												.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addGroup(layout.createParallelGroup(Alignment.LEADING)
														.addGroup(layout.createSequentialGroup().addGap(50)
																.addComponent(lblNewLabel_14))
														.addGroup(layout.createSequentialGroup().addGap(18)
																.addGroup(layout.createParallelGroup(Alignment.TRAILING)
																		.addComponent(lblNewLabel_9_1)
																		.addComponent(alto, GroupLayout.PREFERRED_SIZE,
																				31, GroupLayout.PREFERRED_SIZE)
																		.addGroup(layout
																				.createParallelGroup(Alignment.BASELINE)
																				.addComponent(lblNewLabel_4,
																						GroupLayout.PREFERRED_SIZE, 48,
																						GroupLayout.PREFERRED_SIZE)
																				.addComponent(delay,
																						GroupLayout.PREFERRED_SIZE, 34,
																						GroupLayout.PREFERRED_SIZE))
																		.addComponent(lblNewLabel_9,
																				GroupLayout.PREFERRED_SIZE, 39,
																				GroupLayout.PREFERRED_SIZE)
																		.addComponent(ancho, GroupLayout.PREFERRED_SIZE,
																				31, GroupLayout.PREFERRED_SIZE)))))
												.addGroup(layout.createSequentialGroup()
														.addGroup(layout.createParallelGroup(Alignment.BASELINE)
																.addComponent(fit).addComponent(touch)
																.addComponent(perdida, GroupLayout.PREFERRED_SIZE, 36,
																		GroupLayout.PREFERRED_SIZE))
														.addGap(78)))
										.addGap(26)
										.addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout
												.createSequentialGroup()
												.addGroup(layout.createParallelGroup(Alignment.BASELINE)
														.addComponent(lblNewLabel_6).addComponent(rotacion,
																GroupLayout.PREFERRED_SIZE, 37,
																GroupLayout.PREFERRED_SIZE))
												.addGap(47)
												.addGroup(layout.createParallelGroup(Alignment.BASELINE)
														.addComponent(lblNewLabel_12, GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(loopCount, GroupLayout.PREFERRED_SIZE, 36,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(lblNewLabel_2)))
												.addGroup(layout.createSequentialGroup()
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(lblNewLabel_11)
														.addPreferredGap(ComponentPlacement.UNRELATED)
														.addComponent(bn, GroupLayout.PREFERRED_SIZE, 24,
																GroupLayout.PREFERRED_SIZE)
														.addGap(25)
														.addGroup(layout.createParallelGroup(Alignment.LEADING)
																.addComponent(btnNewButton).addComponent(lblNewLabel_1,
																		GroupLayout.DEFAULT_SIZE, 58,
																		Short.MAX_VALUE))))
										.addGap(37))
								.addGroup(layout.createSequentialGroup().addComponent(resize,
										GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE).addGap(321)))
						.addGap(48)));

		getContentPane().setLayout(layout);

		setSize(new Dimension(961, 795));

		setLocationRelativeTo(null);

		javax.swing.border.TitledBorder dragBorder = new javax.swing.border.TitledBorder("Drop 'em");

		try {

			new DragAndDrop(imagenes, dragBorder, rootPaneCheckingEnabled, new DragAndDrop.Listener() {

				public void filesDropped(java.io.File[] files) {

					try {

						if (!merge.getSelectedItem().toString().equals("None")) {

							String archivo = "";

							for (int i = 0; i < files.length; i++) {

								archivo = files[0].getAbsolutePath();

								if (archivo.endsWith(".gif")) {

									if (merge.getSelectedItem().toString().equals("Merge")) {

										ButtonPanel.listaImagenes.add(files[i].getAbsolutePath());

									}

									else {
										ButtonPanel.listaImagenesInterlace.add(files[i].getAbsolutePath());
									}

								}

							}

						}

					}

					catch (Exception e) {

					}

				}

			});

		}

		catch (TooManyListenersException e1) {

		}

	}

	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}
}
