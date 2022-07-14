package gif;

import java.awt.Color;
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
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import checkbox.JCheckBoxCustom;
import combo_suggestion.ComboBoxSuggestion;
import drag_and_drop.UtilDragAndDrop;
import radio_button.RadioButtonCustom;
import roundedButtonsWithImage.ButtonRoundedWithImage;
import spinner.Spinner;
import textfield.TextField;
import utils.Utilidades;

@SuppressWarnings("all")

public class Config extends javax.swing.JFrame implements ActionListener, ChangeListener {

	private JTextField escala;

	private Spinner perdida;

	private Spinner delay;

	private Spinner optimize;

	private Spinner colores;

	private Spinner ancho;

	private Spinner alto;

	private Spinner loopCount;

	private ComboBoxSuggestion bn;

	private JCheckBoxCustom fit, touch;

	RadioButtonCustom horizontal;

	RadioButtonCustom vertical;

	private ComboBoxSuggestion rotacion;

	private ComboBoxSuggestion merge;

	private JCheckBoxCustom flip;

	private TextField sizeCrop;

	private Spinner espacioTop;

	private Spinner espacioLeft;

	private JCheckBox crop;

	public Config() {

		setAlwaysOnTop(true);

		setIconImage(Toolkit.getDefaultToolkit().getImage(Config.class.getResource("/images/settings.png")));

		setTitle("Gif Animator - Config");

		initComponents();

		leer();

		this.setVisible(true);

	}

	private void reset() {

		try {

			colores.setValor(128);

			optimize.setValor(0);

			escala.setText("");

			perdida.setValor(0);

			delay.setValor(0);

			loopCount.setValor(0);

			ancho.setValor(0);

			alto.setValor(0);

			espacioLeft.setValor(0);

			espacioTop.setValor(0);

			guardarDatos();

		}

		catch (Exception e) {

		}

	}

	private void leer() {

		try {

			ButtonPanel.setLectura(Utilidades.leerFicheroArray("ConfigEasyGifCreator.txt", 19));

			colores.setValor(Integer.parseInt(ButtonPanel.getLectura()[0]));

			optimize.setValor(Integer.parseInt(ButtonPanel.getLectura()[1]));

			escala.setText(ButtonPanel.getLectura()[2]);

			perdida.setValor(Integer.parseInt(ButtonPanel.getLectura()[3]));

			delay.setValor(Integer.parseInt(ButtonPanel.getLectura()[4]));

			loopCount.setValor(Integer.parseInt(ButtonPanel.getLectura()[5]));

			ancho.setValor(Integer.parseInt(ButtonPanel.getLectura()[6]));

			alto.setValor(Integer.parseInt(ButtonPanel.getLectura()[7]));

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

			espacioLeft.setValor(Integer.parseInt(ButtonPanel.getLectura()[17]));

			espacioTop.setValor(Integer.parseInt(ButtonPanel.getLectura()[18]));

		}

		catch (Exception e) {

		}

	}

	public void guardarDatos() throws IOException {

		try {

			int color = colores.getValor();

			int optimizar = optimize.getValor();

			String scala = escala.getText();

			int lossy = perdida.getValor();

			int retardo = delay.getValor();

			int loop = loopCount.getValor();

			int width = ancho.getValor();

			int height = alto.getValor();

			int spaceLeft = espacioLeft.getValor();

			int spaceTop = espacioTop.getValor();

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

			fS.write(sizeCrop.getText());

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

		lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 16));

		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);

		lblNewLabel.setIcon(new ImageIcon(Config.class.getResource("/images/color.png")));

		merge = new ComboBoxSuggestion();

		merge.addItem("None");

		merge.addItem("Merge");

		merge.addItem("Interlace");

		merge.setRenderer(listRenderer);

		merge.setFont(new Font("Dialog", Font.PLAIN, 20));

		ButtonRoundedWithImage btnNewButton = new ButtonRoundedWithImage("");

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
		escala.setFont(new Font("Tahoma", Font.PLAIN, 16));
		escala.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblNewLabel_2 = new JLabel(" 0  -");

		lblNewLabel_2.setFont(new Font("Dialog", Font.PLAIN, 20));

		JLabel lblNewLabel_3 = new JLabel("Scale");

		lblNewLabel_3.setFont(new Font("Dialog", Font.PLAIN, 16));

		lblNewLabel_3.setIcon(new ImageIcon(Config.class.getResource("/images/regla.png")));

		JLabel lblNewLabel_4 = new JLabel("Delay");

		lblNewLabel_4.setFont(new Font("Dialog", Font.PLAIN, 16));

		lblNewLabel_4.setIcon(new ImageIcon(Config.class.getResource("/images/delay.png")));

		perdida = new Spinner();

		perdida.getEditor().setFont(new Font("Dialog", Font.PLAIN, 16));

		delay = new Spinner();

		delay.getEditor().setFont(new Font("Dialog", Font.PLAIN, 16));

		JLabel lblNewLabel_5 = new JLabel("Lossy");

		lblNewLabel_5.setIcon(new ImageIcon(Config.class.getResource("/images/lossy.png")));

		lblNewLabel_5.setFont(new Font("Dialog", Font.PLAIN, 20));

		optimize = new Spinner();

		optimize.getEditor().setFont(new Font("Dialog", Font.PLAIN, 16));

		rotacion = new ComboBoxSuggestion();

		listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);

		rotacion.setRenderer(listRenderer);

		setResizable(false);

		rotacion.setFont(new Font("Dialog", Font.PLAIN, 16));

		rotacion.addItem("None");

		rotacion.addItem("90");

		rotacion.addItem("180");

		rotacion.addItem("270");

		JCheckBox lblNewLabel_6 = new JCheckBox("Rotate");
		lblNewLabel_6.setSelected(true);

		lblNewLabel_6.setFont(new Font("Dialog", Font.PLAIN, 16));

		lblNewLabel_6.setIcon(new ImageIcon(Config.class.getResource("/images/rotate_180.png")));

		JTextArea imagenes = new JTextArea();

		imagenes.setText("Drag and Drop here");

		imagenes.setForeground(SystemColor.desktop);

		imagenes.setFont(new Font("Tahoma", Font.PLAIN, 26));

		imagenes.setEditable(false);

		imagenes.setBackground(Color.WHITE);

		colores = new Spinner();

		colores.getEditor().setFont(new Font("Dialog", Font.PLAIN, 16));

		JLabel lblNewLabel_8 = new JLabel("Optimize");

		lblNewLabel_8.setFont(new Font("Dialog", Font.PLAIN, 16));

		lblNewLabel_8.setIcon(new ImageIcon(Config.class.getResource("/images/utilities.png")));

		bn = new ComboBoxSuggestion();

		bn.addItem("None");

		bn.addItem("Black And White");

		bn.addItem("Gray");

		bn.addItem("Web");

		bn.setFont(new Font("Dialog", Font.PLAIN, 19));

		bn.setRenderer(listRenderer);

		JLabel lblNewLabel_9 = new JLabel("");

		lblNewLabel_9.setIcon(new ImageIcon(Config.class.getResource("/images/width.png")));

		ancho = new Spinner();

		ancho.getEditor().setFont(new Font("Dialog", Font.PLAIN, 16));

		JLabel lblNewLabel_9_1 = new JLabel("");

		lblNewLabel_9_1.setIcon(new ImageIcon(Config.class.getResource("/images/height.png")));

		alto = new Spinner();

		alto.getEditor().setFont(new Font("Dialog", Font.PLAIN, 16));

		JLabel resize = new JLabel(" Resize");

		resize.setHorizontalAlignment(SwingConstants.CENTER);

		resize.setFont(new Font("Dialog", Font.PLAIN, 16));

		resize.setIcon(new ImageIcon(Config.class.getResource("/images/resize.png")));

		JLabel lblNewLabel_11 = new JLabel("");

		lblNewLabel_11.setHorizontalAlignment(SwingConstants.CENTER);

		lblNewLabel_11.setIcon(new ImageIcon(Config.class.getResource("/images/30-07-2018 1-07-31.png")));

		fit = new JCheckBoxCustom("Fit", SwingConstants.LEFT);

		fit.addMouseListener(new MouseAdapter() {

			@Override

			public void mousePressed(MouseEvent e) {

				if (!fit.isSelected()) {

					touch.setSelected(false);

				}

			}

		});

		fit.setHorizontalAlignment(SwingConstants.CENTER);

		fit.setFont(new Font("Dialog", Font.PLAIN, 16));

		touch = new JCheckBoxCustom("Touch", SwingConstants.CENTER);

		touch.addMouseListener(new MouseAdapter() {

			@Override

			public void mousePressed(MouseEvent e) {

				if (!touch.isSelected()) {

					fit.setSelected(false);

				}

			}

		});

		touch.setHorizontalAlignment(SwingConstants.CENTER);

		touch.setFont(new Font("Dialog", Font.PLAIN, 16));

		JLabel lblNewLabel_12 = new JLabel("Loop Count");

		lblNewLabel_12.setIcon(new ImageIcon(Config.class.getResource("/images/loop.png")));

		lblNewLabel_12.setFont(new Font("Dialog", Font.PLAIN, 16));

		loopCount = new Spinner();

		loopCount.getEditor().setFont(new Font("Dialog", Font.PLAIN, 16));

		ButtonRoundedWithImage btnNewButton_1 = new ButtonRoundedWithImage("");

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

		horizontal = new RadioButtonCustom("Horizontal");

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

		horizontal.setFont(new Font("Dialog", Font.PLAIN, 16));

		vertical = new RadioButtonCustom("Vertical");

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

		JLabel lblNewLabel_15 = new JLabel("");

		lblNewLabel_15.setIcon(new ImageIcon(Config.class.getResource("/images/flip_h.png")));

		JLabel lblNewLabel_16 = new JLabel("");

		lblNewLabel_16.setIcon(new ImageIcon(Config.class.getResource("/images/flip_v.png")));

		flip = new JCheckBoxCustom("Flip", SwingConstants.CENTER);

		flip.setVerticalAlignment(SwingConstants.BOTTOM);

		flip.setHorizontalAlignment(SwingConstants.CENTER);

		flip.setFont(new Font("Dialog", Font.PLAIN, 20));

		JLabel lblNewLabel_7 = new JLabel("Join");

		lblNewLabel_7.setFont(new Font("Dialog", Font.PLAIN, 16));

		lblNewLabel_7.setIcon(new ImageIcon(Config.class.getResource("/images/merge.png")));

		crop = new JCheckBoxCustom("Crop", SwingConstants.LEFT);

		crop.setFont(new Font("Dialog", Font.PLAIN, 16));

		sizeCrop = new TextField();

		sizeCrop.setFont(new Font("Dialog", Font.PLAIN, 16));

		espacioTop = new Spinner();

		espacioTop.getEditor().setFont(new Font("Dialog", Font.PLAIN, 16));

		espacioLeft = new Spinner();

		espacioLeft.getEditor().setFont(new Font("Dialog", Font.PLAIN, 16));

		JLabel textCrop = new JLabel("Width x Height");

		textCrop.setFont(new Font("Dialog", Font.PLAIN, 16));

		textCrop.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblNewLabel_13 = new JLabel("Left space");

		lblNewLabel_13.setFont(new Font("Dialog", Font.PLAIN, 16));

		lblNewLabel_13.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblNewLabel_13_1 = new JLabel("Top space");

		lblNewLabel_13_1.setFont(new Font("Dialog", Font.PLAIN, 16));

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
								.addGroup(layout.createParallelGroup(Alignment.TRAILING).addGroup(layout
										.createSequentialGroup()
										.addGroup(layout.createParallelGroup(Alignment.LEADING)
												.addGroup(layout.createSequentialGroup()
														.addComponent(crop, GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addGap(18))
												.addGroup(layout.createSequentialGroup().addGap(12)
														.addComponent(lblNewLabel_17)
														.addPreferredGap(ComponentPlacement.RELATED)))
										.addGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(textCrop)
												.addComponent(sizeCrop, 116, 116, 116))
										.addGap(17))
										.addGroup(layout.createSequentialGroup().addGap(5)
												.addGroup(layout.createParallelGroup(Alignment.LEADING)
														.addComponent(lblNewLabel_12).addComponent(lblNewLabel_6)
														.addComponent(lblNewLabel_4).addComponent(lblNewLabel_8)
														.addComponent(lblNewLabel_3).addComponent(lblNewLabel_5))))
								.addGap(55))
						.addComponent(lblNewLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(Alignment.TRAILING)
						.addComponent(escala, GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
						.addGroup(layout.createSequentialGroup().addComponent(espacioTop, 0, 0, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED))
						.addComponent(lblNewLabel_13_1, GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
						.addComponent(delay, GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
						.addComponent(colores, GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
						.addComponent(lblNewLabel_13, GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
						.addComponent(optimize, GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
						.addComponent(perdida, GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
						.addComponent(espacioLeft, GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
						.addComponent(rotacion, GroupLayout.PREFERRED_SIZE, 91, Short.MAX_VALUE)
						.addGroup(layout.createSequentialGroup()
								.addComponent(loopCount, GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)))
				.addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(Alignment.TRAILING).addComponent(lblNewLabel_15)
								.addGroup(layout.createSequentialGroup()
										.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 38,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(lblNewLabel_1)
										.addPreferredGap(ComponentPlacement.RELATED))
								.addComponent(lblNewLabel_9))
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(layout
												.createParallelGroup(Alignment.LEADING).addGroup(layout
														.createSequentialGroup().addGroup(layout
																.createParallelGroup(Alignment.LEADING)
																.addGroup(layout.createSequentialGroup().addGap(6)
																		.addComponent(fit, GroupLayout.DEFAULT_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addGap(87))
																.addGroup(layout
																		.createSequentialGroup().addGap(12)
																		.addComponent(ancho, GroupLayout.PREFERRED_SIZE,
																				96, GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(ComponentPlacement.RELATED)))
														.addGroup(layout.createParallelGroup(Alignment.LEADING)
																.addGroup(layout.createSequentialGroup()
																		.addComponent(lblNewLabel_9_1,
																				GroupLayout.PREFERRED_SIZE, 33,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(ComponentPlacement.UNRELATED)
																		.addComponent(alto, GroupLayout.PREFERRED_SIZE,
																				96, GroupLayout.PREFERRED_SIZE))
																.addComponent(
																		touch, GroupLayout.PREFERRED_SIZE, 187,
																		GroupLayout.PREFERRED_SIZE))
														.addGap(311))
												.addGroup(layout.createSequentialGroup().addGap(15).addGroup(layout
														.createParallelGroup(Alignment.TRAILING)
														.addGroup(layout.createSequentialGroup().addGroup(
																layout.createParallelGroup(Alignment.LEADING)
																		.addGroup(layout.createSequentialGroup()
																				.addComponent(imagenes,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE)
																				.addGap(18).addComponent(btnNewButton_1,
																						GroupLayout.PREFERRED_SIZE, 58,
																						GroupLayout.PREFERRED_SIZE))
																		.addGroup(layout
																				.createSequentialGroup()
																				.addComponent(lblNewLabel_7)
																				.addPreferredGap(
																						ComponentPlacement.RELATED)
																				.addComponent(merge,
																						GroupLayout.PREFERRED_SIZE, 168,
																						GroupLayout.PREFERRED_SIZE)))
																.addPreferredGap(ComponentPlacement.RELATED))
														.addGroup(layout.createSequentialGroup().addGroup(layout
																.createParallelGroup(Alignment.TRAILING)
																.addComponent(
																		flip, GroupLayout.PREFERRED_SIZE, 154,
																		GroupLayout.PREFERRED_SIZE)
																.addGroup(layout.createSequentialGroup()
																		.addComponent(horizontal,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(47).addComponent(lblNewLabel_16)))
																.addGap(18)
																.addComponent(vertical, GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE)))
														.addPreferredGap(ComponentPlacement.RELATED, 20,
																Short.MAX_VALUE))))
								.addGroup(layout.createSequentialGroup().addGap(43)
										.addComponent(bn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addGap(59).addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 54,
												GroupLayout.PREFERRED_SIZE))))
						.addGroup(layout.createSequentialGroup().addGap(186).addComponent(lblNewLabel_11))
						.addGroup(layout.createSequentialGroup().addGap(106).addComponent(resize,
								GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel)
								.addComponent(colores, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_7).addComponent(merge, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(layout
								.createParallelGroup(Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(textCrop))
								.addGroup(layout.createSequentialGroup().addGap(25).addComponent(lblNewLabel_13)))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(layout
								.createParallelGroup(Alignment.LEADING)
								.addGroup(layout
										.createSequentialGroup().addComponent(lblNewLabel_17).addGap(22)
										.addComponent(crop, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
								.addGroup(layout.createSequentialGroup().addGap(12).addGroup(layout
										.createParallelGroup(Alignment.LEADING)
										.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
												.addComponent(btnNewButton_1, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
												.addComponent(imagenes, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 60,
														Short.MAX_VALUE))
										.addComponent(espacioLeft, GroupLayout.PREFERRED_SIZE, 52,
												GroupLayout.PREFERRED_SIZE))
										.addGap(18).addComponent(lblNewLabel_13_1)
										.addGroup(layout.createParallelGroup(Alignment.LEADING)
												.addGroup(layout.createSequentialGroup().addGap(25).addComponent(flip,
														GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
												.addGroup(
														layout.createSequentialGroup().addGap(18).addComponent(
																espacioTop, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
								.addComponent(sizeCrop, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE))
						.addGap(2)
						.addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
										.addGroup(layout.createParallelGroup(Alignment.TRAILING)
												.addComponent(horizontal, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(lblNewLabel_15).addComponent(
														vertical, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
										.addGroup(
												layout.createSequentialGroup().addGap(11).addComponent(lblNewLabel_16)))
								.addGap(24))
								.addGroup(layout.createSequentialGroup().addGap(18)
										.addGroup(layout.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblNewLabel_8).addComponent(optimize,
														GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED)))
						.addGap(5)
						.addGroup(layout.createParallelGroup(Alignment.TRAILING)
								.addGroup(layout.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 58,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(escala, GroupLayout.PREFERRED_SIZE, 36,
												GroupLayout.PREFERRED_SIZE))
								.addComponent(resize))
						.addPreferredGap(ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
						.addGroup(
								layout.createParallelGroup(Alignment.TRAILING)
										.addGroup(layout.createParallelGroup(Alignment.TRAILING)
												.addGroup(layout.createParallelGroup(Alignment.BASELINE)
														.addComponent(lblNewLabel_4, GroupLayout.PREFERRED_SIZE, 48,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(delay, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addComponent(
														lblNewLabel_9, GroupLayout.PREFERRED_SIZE, 39,
														GroupLayout.PREFERRED_SIZE)
												.addGroup(layout
														.createParallelGroup(Alignment.LEADING, false)
														.addComponent(alto, Alignment.TRAILING, 0, 0, Short.MAX_VALUE)
														.addComponent(lblNewLabel_9_1, Alignment.TRAILING,
																GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(ancho, Alignment.TRAILING,
																GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)))
										.addGroup(layout.createSequentialGroup()
												.addGroup(layout.createParallelGroup(Alignment.BASELINE)
														.addComponent(perdida, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(fit, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(touch, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(lblNewLabel_5))
												.addGap(78)))
						.addPreferredGap(ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
						.addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblNewLabel_11)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
										.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 55,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(bn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)))
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblNewLabel_6).addComponent(rotacion,
														GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
										.addGap(18)
										.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
												.addGroup(layout.createSequentialGroup()
														.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addGap(17))
												.addGroup(layout.createParallelGroup(Alignment.BASELINE)
														.addComponent(lblNewLabel_12, GroupLayout.PREFERRED_SIZE, 49,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(loopCount, GroupLayout.PREFERRED_SIZE, 49,
																GroupLayout.PREFERRED_SIZE))
												.addComponent(lblNewLabel_2, Alignment.LEADING))))
						.addContainerGap()));

		getContentPane().setLayout(layout);

		setSize(new Dimension(879, 813));

		setLocationRelativeTo(null);

		javax.swing.border.TitledBorder dragBorder = new javax.swing.border.TitledBorder("Drop 'em");

		try {

			new UtilDragAndDrop(imagenes, dragBorder, true, new UtilDragAndDrop.Listener() {

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
