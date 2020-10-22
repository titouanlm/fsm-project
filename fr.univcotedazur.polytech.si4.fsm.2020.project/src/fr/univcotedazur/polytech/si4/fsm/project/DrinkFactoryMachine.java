package fr.univcotedazur.polytech.si4.fsm.project;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import fr.univcotedazur.polytech.si4.fsm.project.defaultsm.DefaultSMStatemachine;

public class DrinkFactoryMachine extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2030629304432075314L;
	private JPanel contentPane;
	/**
	 * @wbp.nonvisual location=311,475
	 */
	// private final ImageIcon imageIcon = new ImageIcon();

	protected DefaultSMStatemachine theDFM;
	protected JLabel messagesToUser;
	protected JLabel messagesToUser2;
	protected JLabel messagesToUser3;
	protected boolean machineSurEcoute = true; // (quand on prépare un café, on ne peut donner aucune commande --> point 13)
	protected JLabel labelForPictures;
	protected JSlider sugarSlider;
	protected JSlider sizeSlider;
	protected JSlider temperatureSlider;
	protected Beverage beverageChoice = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DrinkFactoryMachine frame = new DrinkFactoryMachine();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void enoughMoney() {
		if (theDFM != null && this.beverageChoice != null ) {
			if (this.beverageChoice.getPrice() <= theDFM.getSolde()) {
				theDFM.setEnoughMoney(true);
			}else {
				theDFM.setEnoughMoney(false);
			}
		}
	}

	/**
	 * Create the frame.
	 */
	public DrinkFactoryMachine() {
		theDFM = new DefaultSMStatemachine();
		TimerService timer = new TimerService();
		theDFM.setTimer(timer);
		theDFM.init();
		theDFM.enter();
		theDFM.getSCInterface().getListeners().add(new DrinkFactoryMachineInterfaceImplementation(this));		
		
		setForeground(Color.WHITE);
		setFont(new Font("Cantarell", Font.BOLD, 22));
		setBackground(Color.DARK_GRAY);
		setTitle("Drinking Factory Machine");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 650);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		messagesToUser = new JLabel("<html> Veuillez insérer de l'argent <br> Solde : " + this.theDFM.getSolde() + "€");
		messagesToUser.setForeground(Color.WHITE);
		messagesToUser.setHorizontalAlignment(SwingConstants.LEFT);
		messagesToUser.setVerticalAlignment(SwingConstants.TOP);
		messagesToUser.setToolTipText("message to the user");
		messagesToUser.setBackground(Color.WHITE);
		messagesToUser.setBounds(126, 34, 165, 175);
		contentPane.add(messagesToUser);

		messagesToUser2 = new JLabel("");
		messagesToUser2.setForeground(Color.WHITE);
		messagesToUser2.setHorizontalAlignment(SwingConstants.LEFT);
		messagesToUser2.setVerticalAlignment(SwingConstants.TOP);
		messagesToUser2.setToolTipText("message to the user 2");
		messagesToUser2.setBackground(Color.WHITE);
		messagesToUser2.setBounds(126, 108, 165, 175);
		contentPane.add(messagesToUser2);
		
		
		messagesToUser3 = new JLabel("");
		messagesToUser3.setForeground(Color.WHITE);
		messagesToUser3.setHorizontalAlignment(SwingConstants.LEFT);
		messagesToUser3.setVerticalAlignment(SwingConstants.TOP);
		messagesToUser3.setToolTipText("message to the user 3");
		messagesToUser3.setBackground(Color.WHITE);
		messagesToUser3.setBounds(126, 145, 165, 175);
		contentPane.add(messagesToUser3);
		
		
		JLabel lblCoins = new JLabel("Coins");
		lblCoins.setForeground(Color.WHITE);
		lblCoins.setHorizontalAlignment(SwingConstants.CENTER);
		lblCoins.setBounds(538, 12, 44, 15);
		contentPane.add(lblCoins);

		JButton coffeeButton = new JButton("Coffee");
		coffeeButton.setForeground(Color.BLACK);
		coffeeButton.setBackground(Color.DARK_GRAY);
		coffeeButton.setBounds(12, 34, 96, 25);
		contentPane.add(coffeeButton);

		JButton expressoButton = new JButton("Expresso");
		expressoButton.setForeground(Color.BLACK);
		expressoButton.setBackground(Color.DARK_GRAY);
		expressoButton.setBounds(12, 71, 96, 25);
		contentPane.add(expressoButton);

		JButton teaButton = new JButton("Tea");
		teaButton.setForeground(Color.BLACK);
		teaButton.setBackground(Color.DARK_GRAY);
		teaButton.setBounds(12, 108, 96, 25);
		contentPane.add(teaButton);

		JButton soupButton = new JButton("Soup");
		soupButton.setForeground(Color.BLACK);
		soupButton.setBackground(Color.DARK_GRAY);
		soupButton.setBounds(12, 145, 96, 25);
		contentPane.add(soupButton);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setValue(10);
		progressBar.setForeground(Color.LIGHT_GRAY);
		progressBar.setBackground(Color.DARK_GRAY);
		progressBar.setBounds(12, 254, 622, 26);
		contentPane.add(progressBar);

		sugarSlider = new JSlider();
		sugarSlider.setValue(1);
		sugarSlider.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		sugarSlider.setBackground(Color.DARK_GRAY);
		sugarSlider.setForeground(Color.BLACK);
		sugarSlider.setPaintTicks(true);
		sugarSlider.setMinorTickSpacing(1);
		sugarSlider.setMajorTickSpacing(1);
		sugarSlider.setMaximum(5);
		sugarSlider.setBounds(301, 51, 200, 36);
		contentPane.add(sugarSlider);

		sizeSlider = new JSlider();
		sizeSlider.setPaintTicks(true);
		sizeSlider.setValue(1);
		sizeSlider.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		sizeSlider.setBackground(Color.DARK_GRAY);
		sizeSlider.setForeground(Color.BLACK);
		sizeSlider.setMinorTickSpacing(1);
		sizeSlider.setMaximum(2);
		sizeSlider.setMajorTickSpacing(1);
		sizeSlider.setBounds(301, 125, 200, 36);
		contentPane.add(sizeSlider);

		temperatureSlider = new JSlider();
		temperatureSlider.setPaintLabels(true);
		temperatureSlider.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		temperatureSlider.setValue(2);
		temperatureSlider.setBackground(Color.DARK_GRAY);
		temperatureSlider.setForeground(Color.BLACK);
		temperatureSlider.setPaintTicks(true);
		temperatureSlider.setMajorTickSpacing(1);
		temperatureSlider.setMaximum(3);
		temperatureSlider.setBounds(301, 188, 200, 54);

		Hashtable<Integer, JLabel> temperatureTable = new Hashtable<Integer, JLabel>();
		temperatureTable.put(0, new JLabel("20°C"));
		temperatureTable.put(1, new JLabel("35°C"));
		temperatureTable.put(2, new JLabel("60°C"));
		temperatureTable.put(3, new JLabel("85°C"));
		for (JLabel l : temperatureTable.values()) {
			l.setForeground(Color.WHITE);
		}
		temperatureSlider.setLabelTable(temperatureTable);

		contentPane.add(temperatureSlider);

		JButton icedTeaButton = new JButton("Iced Tea");
		icedTeaButton.setForeground(Color.BLACK);
		icedTeaButton.setBackground(Color.DARK_GRAY);
		icedTeaButton.setBounds(12, 182, 96, 25);
		contentPane.add(icedTeaButton);

		JLabel lblSugar = new JLabel("Sugar");
		lblSugar.setForeground(Color.WHITE);
		lblSugar.setBackground(Color.DARK_GRAY);
		lblSugar.setHorizontalAlignment(SwingConstants.CENTER);
		lblSugar.setBounds(380, 34, 44, 15);
		contentPane.add(lblSugar);

		JLabel lblSize = new JLabel("Size");
		lblSize.setForeground(Color.WHITE);
		lblSize.setBackground(Color.DARK_GRAY);
		lblSize.setHorizontalAlignment(SwingConstants.CENTER);
		lblSize.setBounds(380, 113, 44, 15);
		contentPane.add(lblSize);

		JLabel lblTemperature = new JLabel("Temperature");
		lblTemperature.setForeground(Color.WHITE);
		lblTemperature.setBackground(Color.DARK_GRAY);
		lblTemperature.setHorizontalAlignment(SwingConstants.CENTER);
		lblTemperature.setBounds(363, 173, 96, 15);
		contentPane.add(lblTemperature);

		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		lblCoins.setLabelFor(panel);
		panel.setBounds(538, 25, 96, 97);
		contentPane.add(panel);

		JButton money50centsButton = new JButton("0.50 €");
		money50centsButton.setForeground(Color.BLACK);
		money50centsButton.setBackground(Color.DARK_GRAY);
		panel.add(money50centsButton);

		JButton money25centsButton = new JButton("0.25 €");
		money25centsButton.setForeground(Color.BLACK);
		money25centsButton.setBackground(Color.DARK_GRAY);
		panel.add(money25centsButton);

		JButton money10centsButton = new JButton("0.10 €");
		money10centsButton.setForeground(Color.BLACK);
		money10centsButton.setBackground(Color.DARK_GRAY);
		panel.add(money10centsButton);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.DARK_GRAY);
		panel_1.setBounds(538, 154, 96, 40);
		contentPane.add(panel_1);

		JButton nfcBiiiipButton = new JButton("biiip");
		nfcBiiiipButton.setForeground(Color.BLACK);
		nfcBiiiipButton.setBackground(Color.DARK_GRAY);
		panel_1.add(nfcBiiiipButton);

		JLabel lblNfc = new JLabel("NFC");
		lblNfc.setForeground(Color.WHITE);
		lblNfc.setHorizontalAlignment(SwingConstants.CENTER);
		lblNfc.setBounds(541, 139, 41, 15);
		contentPane.add(lblNfc);

		JSeparator separator = new JSeparator();
		separator.setBounds(12, 292, 622, 15);
		contentPane.add(separator);

		JButton addCupButton = new JButton("Add cup");
		addCupButton.setForeground(Color.BLACK);
		addCupButton.setBackground(Color.DARK_GRAY);
		addCupButton.setBounds(45, 336, 96, 25);
		contentPane.add(addCupButton);

		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("./picts/vide2.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		labelForPictures = new JLabel(new ImageIcon(myPicture));
		labelForPictures.setBounds(175, 319, 286, 260);
		contentPane.add(labelForPictures);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.DARK_GRAY);
		panel_2.setBounds(538, 217, 96, 33);
		contentPane.add(panel_2);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setForeground(Color.BLACK);
		cancelButton.setBackground(Color.DARK_GRAY);
		panel_2.add(cancelButton);

		// listeners
		addCupButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				BufferedImage myPicture = null;
				try {
					myPicture = ImageIO.read(new File("./picts/ownCup.jpg"));
				} catch (IOException ee) {
					ee.printStackTrace();
				}
				labelForPictures.setIcon(new ImageIcon(myPicture));
			}
		});
		
		nfcBiiiipButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				theDFM.raiseBipButton();
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				theDFM.raiseCancelButton();
			}
		});
		
		money50centsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				theDFM.setSolde(theDFM.getSolde()+0.5);
				theDFM.raiseMoney50centsButton();
			}
		});
		
		money25centsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				theDFM.setSolde(theDFM.getSolde()+0.25);
				theDFM.raiseMoney25centsButton();
			}
		});
		
		money10centsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				theDFM.setSolde(theDFM.getSolde()+0.1);
				theDFM.raiseMoney10centsButton();
			}
		});
		
		coffeeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				if (machineSurEcoute) {
					beverageChoice = new Coffee();
					theDFM.raiseCoffeeButton();
					}
				}
		});
		
		expressoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				if (machineSurEcoute) {
					beverageChoice = new Expresso();
					theDFM.raiseExpressoButton();
					}
				}
		});
		
		teaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				if (machineSurEcoute) {
					beverageChoice = new Tea();
					theDFM.raiseTeaButton();
					}
				}
		});
		
		/*soupButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				if (machineSurEcoute) {
					beveragePrice = 1.0;
					messagesToUser.setText("<html> Vous avez choisi une soupe.");
					theDFM.raiseSoupButton();
					}
				}
		});
		
		icedTeaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				beveragePrice = 1.3;
				theDFM.raiseIcedTeaButton();
				}
		});*/
	}
	
}
