package fr.univcotedazur.polytech.si4.fsm.project;

import java.awt.Color;

import java.awt.EventQueue;
import java.awt.FlowLayout;
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
import javax.swing.JCheckBox;
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
	protected JLabel options;
	protected JLabel labelForPictures;
	protected JPanel panelCofExpTeaOptions;
	protected JPanel panelSoupOptions;
	protected JSlider sugarOrSpicySlider;
	protected JSlider sizeSlider;
	protected JSlider temperatureSlider;
	protected Beverage beverageChoice = null;
	protected JProgressBar progressBar;
	protected JLabel lblSugarOrSpicy;
	protected JCheckBox checkboxMilk;
	protected JCheckBox checkboxMapleSyrup;
	protected JCheckBox checkboxVanilla;
	protected JCheckBox checkboxCrouton;
	protected double beveragePriceAfterDiscount = 0.;
	protected boolean delayBlueCardPayment = false;
	private Thread t;
	private Thread t1;
	private Thread t2;
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
	
	public void enoughMoneyClassicBeverage() {
		if (theDFM != null && this.beverageChoice != null ) {
			if (this.beveragePriceAfterDiscount <= theDFM.getSolde()) {
				theDFM.setEnoughMoney(true);
			}else {
				theDFM.setEnoughMoney(false);
			}
		}
	}
	
	public void enoughMoneyIcedTeaBeverage() {
		if (theDFM != null && this.beverageChoice != null && this.beverageChoice.getName() == "thé glacé") {
			if ( (this.beveragePriceAfterDiscount+0.25) <= theDFM.getSolde() && sizeSlider.getValue() == 1) {
				theDFM.setEnoughMoney(true);
			}
			else if (this.beveragePriceAfterDiscount <= theDFM.getSolde() && sizeSlider.getValue() == 0){
				theDFM.setEnoughMoney(true);
			}
			else {
				theDFM.setEnoughMoney(false);
			}
		}
	}
	
	public double roundValue(double initialValue) {
		double valueCorrection1 = Math.round((initialValue)*100);
		double valueCorrection2 = valueCorrection1/100;
		return valueCorrection2;
	}
	
	public int getTemperatureSelected() {
		int temperatureSelected = 0;
		switch(temperatureSlider.getValue()) {
		case 0:
			temperatureSelected = 20;
			break;
		case 1:
			temperatureSelected = 35;
			break;
		case 2:
			temperatureSelected = 60;
			break;
		default:
			temperatureSelected = 85;
		}
		return temperatureSelected;
	}
	
	public double chooseRightOrLeft() {
		double timeLeft = (getTemperatureSelected() * 0.1);
		if(timeLeft > 3.0 || this.theDFM.getOwnCup()) {
			return  timeLeft;
		}else {
			return 3.0;
		}
	}
	
	public double chooseRightOrLeftExpresso() {
		double timeLeft = (getTemperatureSelected() * 0.1);
		double timeRight = 3.0 + ((sizeSlider.getValue()+1)*1.5);
		if(timeLeft > timeRight) {
			return  timeLeft;
		}else {
			return timeRight ;
		}
	}
	
	public double chooseRightOrLeftSoup() {
		double result =-0.2;
		
		if(this.theDFM.getOwnCup()) {
			result+= 1.5;
		}else {
			result+= 3.0;
		}
		
		double timeLeft = (getTemperatureSelected() * 0.1);
		if(timeLeft > 4.5) {
			result+=  timeLeft;
		}else {
			result+= 4.5;
		}
		return result;
	}
	
	private double addOptions() {
		double result =0;
		if(checkboxMilk.isSelected()) {
			result+= 2;	
		}
		
		if(checkboxVanilla.isSelected()) {
			result += 5;
		}
		
		if(checkboxCrouton.isSelected() && sizeSlider.getValue() == 0) {
			result += 2;
		}
		return result;
	}
	
	public double timePreparation(Beverage beverage) { // le temps se calcul en fonction de la machine à état
		double timePreparation = 0; 
		
		switch(beverage.getName()) {
		case "café":
			timePreparation += 2.3 + chooseRightOrLeft();
			break;
		case "thé":
			timePreparation += 7.3 + chooseRightOrLeft(); 
			break;
		case "expresso":
			timePreparation += 3.8 + chooseRightOrLeftExpresso(); 
			break;
		case "soupe":
			timePreparation += chooseRightOrLeftSoup(); 
			break;
		}
		
		timePreparation += (sizeSlider.getValue()+1)*3;
		timePreparation += addOptions();
		
		return timePreparation;
	}
	
	

	public void progressBarStart() {
		
		long temps = (long)(timePreparation(beverageChoice)*1000);		
		Runnable r = new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					try {
						Thread.sleep(temps/100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (progressBar.getValue() != 100) {
						progressBar.setValue(progressBar.getValue()+1);
					}
					else break;
				}
				
			}
		};
		t1 = new Thread(r);
		t1.start();
		
	}
	
	public void temperatureClassicBeverage() {
		contentPane.remove(temperatureSlider);
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
		setContentPane(contentPane);
	}
	
	public void icedTeaTemperature() {
		contentPane.remove(temperatureSlider);
		Hashtable<Integer, JLabel> temperatureTable = new Hashtable<Integer, JLabel>();
		temperatureTable.put(0, new JLabel("1°C"));
		temperatureTable.put(1, new JLabel("3°C"));
		temperatureTable.put(2, new JLabel("6°C"));
		temperatureTable.put(3, new JLabel("9°C"));
		for (JLabel l : temperatureTable.values()) {
			l.setForeground(Color.WHITE);
		}
		temperatureSlider.setLabelTable(temperatureTable);
		contentPane.add(temperatureSlider);
		setContentPane(contentPane);
	}
	
	public void sugarClassicBeverage() {		
		lblSugarOrSpicy.setText("Sugar");
		contentPane.add(lblSugarOrSpicy);
		sugarOrSpicySlider.setValue(1);
	}
	
	public void spicySoupBeverage() {
		lblSugarOrSpicy.setText("Spicy");
		contentPane.add(lblSugarOrSpicy);
		sugarOrSpicySlider.setValue(0);
	}
	
	public void classicSizeBeverage() {
		contentPane.remove(sizeSlider);;
		sizeSlider = new JSlider();
		sizeSlider.setPaintLabels(true);
		sizeSlider.setValue(1);
		sizeSlider.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		sizeSlider.setBackground(Color.DARK_GRAY);
		sizeSlider.setForeground(Color.BLACK);
		sizeSlider.setPaintTicks(true);
		sizeSlider.setMinorTickSpacing(1);
		sizeSlider.setMaximum(2);
		sizeSlider.setMajorTickSpacing(1);
		sizeSlider.setBounds(301, 115, 200, 46);
				
		Hashtable<Integer, JLabel> sizeTable = new Hashtable<Integer, JLabel>();
		sizeTable.put(0, new JLabel("Short"));
		sizeTable.put(1, new JLabel("Normal"));
		sizeTable.put(2, new JLabel("Long"));
		for (JLabel l : sizeTable.values()) {
			l.setForeground(Color.WHITE);
		}
		sizeSlider.setLabelTable(sizeTable);
		contentPane.add(sizeSlider);
		setContentPane(contentPane);
	}
	
	public void icedTeaSizeBeverage() {
		contentPane.remove(sizeSlider);;
		sizeSlider = new JSlider();
		sizeSlider.setPaintLabels(true);
		sizeSlider.setValue(0);
		sizeSlider.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		sizeSlider.setBackground(Color.DARK_GRAY);
		sizeSlider.setForeground(Color.BLACK);
		sizeSlider.setPaintTicks(true);
		sizeSlider.setMinorTickSpacing(1);
		sizeSlider.setMaximum(1);
		sizeSlider.setMajorTickSpacing(1);
		sizeSlider.setBounds(301, 115, 200, 46);
				
		Hashtable<Integer, JLabel> sizeTable = new Hashtable<Integer, JLabel>();
		sizeTable.put(0, new JLabel("Normal"));
		sizeTable.put(1, new JLabel("Long"));
		for (JLabel l : sizeTable.values()) {
			l.setForeground(Color.WHITE);
		}
		sizeSlider.setLabelTable(sizeTable);
		contentPane.add(sizeSlider);
		setContentPane(contentPane);
	}
	
	public void updatePanelCofExpOption() {
		panelCofExpTeaOptions.add(checkboxVanilla);
		contentPane.add(panelCofExpTeaOptions);	
	}
	
	public void updatePanelTeaOption() {
		panelCofExpTeaOptions.remove(checkboxVanilla);
		contentPane.add(panelCofExpTeaOptions);	
	}
	

	public void updatePanelSoupOption() {
		contentPane.add(panelSoupOptions);
	}

	public void resetPanelOption() {
		
		contentPane.remove(panelCofExpTeaOptions);
		contentPane.remove(panelSoupOptions);
		checkboxMilk.setSelected(false);
		theDFM.setMilkOption(false);
		checkboxVanilla.setSelected(false);
		theDFM.setVanillaOption(false);
		checkboxMapleSyrup.setSelected(false);
		theDFM.setMapleSyrupOption(false);
		checkboxCrouton.setSelected(false);
		theDFM.setCroutonOption(false);
		
		contentPane.revalidate();
		contentPane.repaint();
	}
	
	/**
	 * Fonctionnement de la méthode ci-dessous : 
	 * 
	 * 		Lorsqu'une soupe est sélectionnée, le paiement ne s'effectue pas tant que l'utilisateur n'a pas fait d'action 
	 * 		par rapport au slider.
	 * 
	 * 		Cela répond à l'exigence du client. Une action est ici, poser PUIS retirer le curseur de sa souris du slider.
	 * 		Une fois que le curseur de la souris quitte la zone du slider, la préparation de la soupe se lance.
	 * 		
	 * 		Nous pouvons imaginer la même chose dans la vie courante avec un slider qui permet de choisir l'intensité du choix 
	 * 		d'épice. Et tant que le client ne touche pas le slider, rien ne se passe.
	 * 
	 */
	public void onWaitingChangingSpicySlider() {
		
		Runnable r = new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					
					if (beverageChoice.getName() == "soupe" && sugarOrSpicySlider.getMousePosition()!= null) {
						while(true) {		
							if (sugarOrSpicySlider.getMousePosition()== null && !delayBlueCardPayment) {
								enoughMoneyClassicBeverage();
								break;
							}
							else if (sugarOrSpicySlider.getMousePosition()== null && delayBlueCardPayment){
								theDFM.setPaymentCard(true);
								break;
							}
						}
					}
					if (beverageChoice.getName() != "soupe") break;
					if (theDFM.getPaymentDone() == true) break;
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
		};
		t2 = new Thread(r);
		t2.start();
		
		
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
		
		
		Runnable r = new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					theDFM.runCycle();
					enoughMoneyIcedTeaBeverage();
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
		};
		t = new Thread(r);
		t.start();
		
		theDFM.setOnWire(true);
		theDFM.setOwnCup(false);
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

		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setValue(0);
		progressBar.setForeground(Color.LIGHT_GRAY);
		progressBar.setBackground(Color.DARK_GRAY);
		progressBar.setBounds(12, 254, 622, 26);
		contentPane.add(progressBar);

		sugarOrSpicySlider = new JSlider();
		sugarOrSpicySlider.setValue(1);
		sugarOrSpicySlider.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		sugarOrSpicySlider.setBackground(Color.DARK_GRAY);
		sugarOrSpicySlider.setForeground(Color.BLACK);
		sugarOrSpicySlider.setPaintTicks(true);
		sugarOrSpicySlider.setPaintLabels(true);
		sugarOrSpicySlider.setMinorTickSpacing(1);
		sugarOrSpicySlider.setMajorTickSpacing(1);
		sugarOrSpicySlider.setMaximum(5);
		sugarOrSpicySlider.setBounds(301, 41, 200, 46);
		contentPane.add(sugarOrSpicySlider);
		
		Hashtable<Integer, JLabel> doseTable = new Hashtable<Integer, JLabel>();
		doseTable.put(0, new JLabel("0"));
		doseTable.put(1, new JLabel("1"));
		doseTable.put(2, new JLabel("2"));
		doseTable.put(3, new JLabel("3"));
		doseTable.put(4, new JLabel("4"));
		doseTable.put(5, new JLabel("5"));
		for (JLabel l : doseTable.values()) {
			l.setForeground(Color.WHITE);
		}
		sugarOrSpicySlider.setLabelTable(doseTable);

		sizeSlider = new JSlider();
		sizeSlider.setPaintLabels(true);
		sizeSlider.setValue(1);
		sizeSlider.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		sizeSlider.setBackground(Color.DARK_GRAY);
		sizeSlider.setForeground(Color.BLACK);
		sizeSlider.setPaintTicks(true);
		sizeSlider.setMinorTickSpacing(1);
		sizeSlider.setMaximum(2);
		sizeSlider.setMajorTickSpacing(1);
		sizeSlider.setBounds(301, 115, 200, 46);
				
		Hashtable<Integer, JLabel> sizeTable = new Hashtable<Integer, JLabel>();
		sizeTable.put(0, new JLabel("Short"));
		sizeTable.put(1, new JLabel("Normal"));
		sizeTable.put(2, new JLabel("Long"));
		for (JLabel l : sizeTable.values()) {
			l.setForeground(Color.WHITE);
		}
		sizeSlider.setLabelTable(sizeTable);
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

		lblSugarOrSpicy = new JLabel();
		lblSugarOrSpicy.setForeground(Color.WHITE);
		lblSugarOrSpicy.setBackground(Color.DARK_GRAY);
		lblSugarOrSpicy.setHorizontalAlignment(SwingConstants.CENTER);
		lblSugarOrSpicy.setBounds(380, 24, 44, 15);
		this.sugarClassicBeverage();
		 
		JLabel lblSize = new JLabel("Size");
		lblSize.setForeground(Color.WHITE);
		lblSize.setBackground(Color.DARK_GRAY);
		lblSize.setHorizontalAlignment(SwingConstants.CENTER);
		lblSize.setBounds(380, 96, 44, 15);
		contentPane.add(lblSize);

		JLabel lblTemperature = new JLabel("Temperature");
		lblTemperature.setForeground(Color.WHITE);
		lblTemperature.setBackground(Color.DARK_GRAY);
		lblTemperature.setHorizontalAlignment(SwingConstants.CENTER);
		lblTemperature.setBounds(355, 170, 96, 15);
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

		JButton money20centsButton = new JButton("0.20 €");
		money20centsButton.setForeground(Color.BLACK);
		money20centsButton.setBackground(Color.DARK_GRAY);
		panel.add(money20centsButton);

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
		labelForPictures.setBounds(255, 319, 286, 260);
		contentPane.add(labelForPictures);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.DARK_GRAY);
		panel_2.setBounds(538, 217, 96, 33);
		contentPane.add(panel_2);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setForeground(Color.BLACK);
		cancelButton.setBackground(Color.DARK_GRAY);
		panel_2.add(cancelButton);

		
		//OPTIONS
	
		options = new JLabel("Options :");
		options.setForeground(Color.WHITE);
		options.setHorizontalAlignment(SwingConstants.LEFT);
		options.setVerticalAlignment(SwingConstants.TOP);
		options.setToolTipText("message to the user 3");
		options.setBackground(Color.WHITE);
		options.setBounds(35, 385, 105, 15);
		contentPane.add(options);
		
		panelCofExpTeaOptions = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelCofExpTeaOptions.setBackground(Color.DARK_GRAY);
		panelCofExpTeaOptions.setBounds(35, 405, 225, 150);
		
		checkboxMilk = new JCheckBox("Nuage de lait (+0.10€)");
		checkboxMilk.setForeground(Color.WHITE);
		checkboxMilk.setBackground(Color.DARK_GRAY);
		panelCofExpTeaOptions.add(checkboxMilk);
		
		checkboxMapleSyrup = new JCheckBox("Sirop d'érable (+0.10€)");
		checkboxMapleSyrup.setForeground(Color.WHITE);
		checkboxMapleSyrup.setBackground(Color.DARK_GRAY);
		panelCofExpTeaOptions.add(checkboxMapleSyrup);	
		
		checkboxVanilla = new JCheckBox("Glace vanille mixée (+0.40€)");
		checkboxVanilla.setForeground(Color.WHITE);
		checkboxVanilla.setBackground(Color.DARK_GRAY);	
			
		panelSoupOptions = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelSoupOptions.setBackground(Color.DARK_GRAY);
		panelSoupOptions.setBounds(35, 405, 225, 150);
		
		checkboxCrouton = new JCheckBox("Croutons (+0.30€)");
		checkboxCrouton.setForeground(Color.WHITE);
		checkboxCrouton.setBackground(Color.DARK_GRAY);
		panelSoupOptions.add(checkboxCrouton);	
		
		// listeners
		
		checkboxMilk.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent event) {
		    	if(checkboxMilk.isSelected()) {
					theDFM.setMilkOption(true);
				}else {
					theDFM.setMilkOption(false);
				}
				theDFM.raiseCheckbMilk();
		    }
		});
		
		
		checkboxVanilla.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent event) {
		    	if(checkboxVanilla.isSelected()) {
					theDFM.setVanillaOption(true);
				}else {
					theDFM.setVanillaOption(false);
				}
				theDFM.raiseCheckbVanilla();
		    }
		});
		
		checkboxMapleSyrup.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent event) {
		    	if(checkboxMapleSyrup.isSelected()) {
					theDFM.setMapleSyrupOption(true);
				}else {
					theDFM.setMapleSyrupOption(false);
				}
				theDFM.raiseCheckbMapleSyrup();
		    }
		});
		
		checkboxCrouton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent event) {
		    	if(checkboxCrouton.isSelected()) {
					theDFM.setCroutonOption(true);
				}else {
					theDFM.setCroutonOption(false);
				}
				theDFM.raiseCheckbCrouton();
		    }
		});
		
		addCupButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(theDFM.getOnWire()) {
					BufferedImage myPicture = null;
					try {
						myPicture = ImageIO.read(new File("./picts/ownCup.jpg"));
					} catch (IOException ee) {
						ee.printStackTrace();
					}
					theDFM.setOwnCup(true);
					labelForPictures.setIcon(new ImageIcon(myPicture));
					theDFM.raiseAddCupButton();
				}
				
			}
		});
		
		nfcBiiiipButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (theDFM.getOnWire()) {
					if (beverageChoice != null && beverageChoice.getName() == "soupe" && sugarOrSpicySlider.getValue() !=0) {
						theDFM.setPaymentCard(true);
					}
					else if (beverageChoice != null && beverageChoice.getName() == "soupe") {
						delayBlueCardPayment = true;
						}
					theDFM.raiseBipButton();
				}
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
				if (theDFM.getOnWire()) {
					theDFM.setSolde(roundValue(theDFM.getSolde()+0.5));
					theDFM.raiseMoney50centsButton();
				}
			}
		});
		
		money20centsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (theDFM.getOnWire()) {
					theDFM.setSolde(roundValue(theDFM.getSolde()+0.20));
					theDFM.raiseMoney20centsButton();
				}
			}
		});
		
		money10centsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (theDFM.getOnWire()) {
					theDFM.setSolde(roundValue(theDFM.getSolde()+0.1));
					theDFM.raiseMoney10centsButton();
				}
			}
		});
		
		coffeeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				if (theDFM.getOnWire()) {
					resetPanelOption();
					temperatureClassicBeverage();
					sugarClassicBeverage();
					classicSizeBeverage();
					updatePanelCofExpOption();
					beverageChoice = new Coffee();
					theDFM.raiseCoffeeButton();
					}
				}
		});
		
		expressoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				if (theDFM.getOnWire()) {
					resetPanelOption();
					temperatureClassicBeverage();
					sugarClassicBeverage();
					classicSizeBeverage();
					updatePanelCofExpOption();
					beverageChoice = new Expresso();
					theDFM.raiseExpressoButton();
					}
				}
		});
		
		teaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				if (theDFM.getOnWire()) {
					resetPanelOption();
					temperatureClassicBeverage();
					sugarClassicBeverage();
					classicSizeBeverage();
					updatePanelTeaOption();
					beverageChoice = new Tea();
					theDFM.raiseTeaButton();
					}
				}
		});
		
		soupButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				if (theDFM.getOnWire()) {
					if (theDFM.getPaymentCard() == true) {
						delayBlueCardPayment = true;
						theDFM.setPaymentCard(false);
						}
					resetPanelOption();
					temperatureClassicBeverage();
					spicySoupBeverage();
					classicSizeBeverage();
					updatePanelSoupOption();
					beverageChoice = new Soup();
					theDFM.raiseSoupButton();
				}
			}
		});
		
		icedTeaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				if (theDFM.getOnWire()) {
					resetPanelOption();
					icedTeaTemperature();
					sugarClassicBeverage();
					icedTeaSizeBeverage();
					beverageChoice = new IcedTea();
					theDFM.raiseTeaButton();
					}
				}
		});
	}
	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		t.destroy();
		t1.destroy();
		t2.destroy();
	}
	
}