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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;


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
import javax.swing.JTextField;
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
	protected JLabel messagesToUserOption1;
	protected JLabel messagesToUserOption2;
	protected JLabel messagesToUserOption3;
	protected JLabel messagesToProviderStock;
	protected JLabel labelForPictures;
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
	protected Supply supply;
	protected JTextField textField;
	protected JLabel label1;
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
	
	public void addHashMapToFile(HashMap<String, List<Double>> hmap) {
		try
        {
           FileOutputStream fos = new FileOutputStream("CustomerDiscountInformation.txt");
           ObjectOutputStream oos = new ObjectOutputStream(fos);
           oos.writeObject(hmap);
           oos.close();
           fos.close();
        }
        catch(IOException ioe)
         {
               ioe.printStackTrace();
         }
	}
	
	public void createFile(String name, double finalBeveragePrice) {
		HashMap<String, List<Double>> hmap = new HashMap<String, List<Double>>();
        List<Double> newListInfoCard = new ArrayList<>();
        newListInfoCard.add(finalBeveragePrice);
		hmap.put(name, newListInfoCard);
		addHashMapToFile(hmap);
		
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, List<Double>> retrieveHashInfoCard() {
		HashMap<String, List<Double>> hmap = new HashMap<String, List<Double>>();
		try {
			FileInputStream fis = new FileInputStream("CustomerDiscountInformation.txt");
			ObjectInputStream ois = new ObjectInputStream(fis);
			hmap = (HashMap<String, List<Double>>) ois.readObject();
	        ois.close();
	        fis.close();
		} 
		catch(IOException ioe)
	      {
	         ioe.printStackTrace();
	      }
	      catch(ClassNotFoundException c)
	      {
	         c.printStackTrace();
	      }
		return hmap;
	}
	
	public void addHashInfoCard(String name, double finalBeveragePrice) {
		File newFile = new File("CustomerDiscountInformation.txt");
		if (newFile.length() == 0) {
			createFile(name, finalBeveragePrice);
			return;
		}
		HashMap<String, List<Double>> hmap = retrieveHashInfoCard();
		if (hmap == null) {
			messagesToUser.setText("<html> Machine défectueuse sur les réductions <br> merci de contacter le personnel");
			return;
		}
		List<Double> newListHashInfo;
		if (hmap.get(name) == null) {
			newListHashInfo = new ArrayList<>(); 
			newListHashInfo.add(finalBeveragePrice);
			hmap.put(name,newListHashInfo);
		}
		else {
			newListHashInfo = hmap.get(name);
			newListHashInfo.add(finalBeveragePrice);
			hmap.put(name,newListHashInfo);
		}
		
		addHashMapToFile(hmap);
   }
	
	@SuppressWarnings("unchecked")
	public boolean verifyCustomerDiscount(String name) { 
		HashMap<String, List<Double>> hmap = null;
	      try
	      {
	         FileInputStream fis = new FileInputStream("CustomerDiscountInformation.txt");
	         ObjectInputStream ois = new ObjectInputStream(fis);
	         hmap = (HashMap<String, List<Double>>) ois.readObject();
	         ois.close();
	         fis.close();
	      }
	      catch(IOException ioe)
	      {
	         ioe.printStackTrace();
	         return false;
	      }
	      catch(ClassNotFoundException c)
	      {
	         c.printStackTrace();
	         return false;
	      }
	      if (hmap.get(name) != null && hmap.get(name).size() == 11) { 
	    	  List<Double> newList = hmap.get(name);
	    	  double discountPrice = doubleListAverageValueWithoutLastValue(newList);
	    	  if (discountPrice >= beveragePriceAfterDiscount) {
	    		  beveragePriceAfterDiscount = 0.0;
	    	  }
	    	  else {
	    		  beveragePriceAfterDiscount -= discountPrice;
	    		  roundValue(beveragePriceAfterDiscount);
	    	  }
	    	  for (int i = 0 ; i < 11 ; i++) {
	    		  newList.remove(0);
	    	  }
	    	  hmap.remove(name);
	    	  hmap.put(name, newList);
	    	  addHashMapToFile(hmap);
	
	    	  return true; 
	      }
	    
		return false;

	}
	
	public double doubleListAverageValueWithoutLastValue(List<Double> list){
		double moyenne = 0.0;
		if (list != null) {
			for (int i=0;i<list.size()-1;i++) {
				moyenne+=list.get(i);
			}
			return (moyenne/(list.size()-1));
		}
		else return 0.0;
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
	
	public int getTemperatureHotBeverageSelected() {
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
	
	public int getTemperatureColdBeverageSelected() {
		int temperatureSelected = 0;
		switch(temperatureSlider.getValue()) {
		case 0:
			temperatureSelected = 1;
			break;
		case 1:
			temperatureSelected = 4;
			break;
		case 2:
			temperatureSelected = 7;
			break;
		default:
			temperatureSelected = 10;
		}
		return temperatureSelected;
	}
	
	public double chooseLeftOrRightPathsOverAllCoffeeSteps() {
		double timePreparation = 0.0 ;
		timePreparation += chooseLeftOrRightPathOfFirstStepCoffeePreparation();
		timePreparation += chooseLeftOrRightPathOfSecondStepCoffeePreparation();
		timePreparation += chooseLeftOrRightPathOfThirdStepCoffeePreparation();
		timePreparation += addOptions();
		return timePreparation;
	}
	
	public double chooseLeftOrRightPathOfFirstStepCoffeePreparation() {
		double timePreparationLeft = 1.5 ;
		double timePreparationRight = 2.5;
		if (timePreparationLeft > timePreparationRight) {
			return timePreparationLeft;
		}
		else {
			return timePreparationRight;
		}
	}
	

	public double chooseLeftOrRightPathOfSecondStepCoffeePreparation() {
		double timePreparationLeft = getTemperatureHotBeverageSelected() * 0.15 ;
		double timePreparationRight = 0.0 ;
		if (!this.theDFM.getOwnCup()) {
			timePreparationRight+=3.0;
		}
		if (timePreparationLeft > timePreparationRight) {
			return timePreparationLeft;
		}
		else {
			return timePreparationRight;
		}
	}
	
	public double chooseLeftOrRightPathOfThirdStepCoffeePreparation() {
		double timePreparationLeft = (sizeSlider.getValue()+1)*1.5 ;
		double timePreparationRight = 3.0;
		if (timePreparationLeft > timePreparationRight) {
			return timePreparationLeft;
		}
		else {
			return timePreparationRight;
		}
	}
	
	public double chooseLeftOrRightPathsOverAllExpressoSteps() {
		double timePreparation = 0.0 ;
		timePreparation += chooseLeftOrRightPathOfFirstStepExpressoPreparation();
		timePreparation += chooseLeftOrRightPathOfSecondStepExpressoPreparation();
		timePreparation += chooseLeftOrRightPathOfThirdStepExpressoPreparation();
		timePreparation += addOptions();
		return timePreparation;
	}
	
	public double chooseLeftOrRightPathOfFirstStepExpressoPreparation() {
		double timePreparationLeft = 1.5 ;
		double timePreparationRight = 4.0;
		if (timePreparationLeft > timePreparationRight) {
			return timePreparationLeft;
		}
		else {
			return timePreparationRight;
		}
	}
	
	public double chooseLeftOrRightPathOfSecondStepExpressoPreparation() {
		double timePreparationLeft = getTemperatureHotBeverageSelected() * 0.15 ;
		double timePreparationRight = 0.0 ;
		if (!this.theDFM.getOwnCup()) {
			timePreparationRight+=3.0;
		}
		timePreparationRight += (sizeSlider.getValue()+1)*2;
		if (timePreparationLeft > timePreparationRight) {
			return timePreparationLeft;
		}
		else {
			return timePreparationRight;
		}
	}
	
	public double chooseLeftOrRightPathOfThirdStepExpressoPreparation() {
		double timePreparationLeft = (sizeSlider.getValue()+1)*1.5 ;
		double timePreparationRight = 3.0;
		if (timePreparationLeft > timePreparationRight) {
			return timePreparationLeft;
		}
		else {
			return timePreparationRight;
		}
	}
	
	public double chooseLeftOrRightPathsOverAllTeaSteps() {
		double timePreparation = 0.0 ;
		timePreparation += chooseLeftOrRightPathOfFirstStepTeaPreparation();
		timePreparation += chooseLeftOrRightPathOfSecondStepTeaPreparation();
		timePreparation += chooseLeftOrRightPathOfThirdStepTeaPreparation();
		timePreparation += timeOfFourthStepTeaPreparation();
		timePreparation += addOptions();
		return timePreparation;
	}
	
	public double chooseLeftOrRightPathOfFirstStepTeaPreparation() {
		double timePreparationLeft = 1.5 ;
		double timePreparationRight = 2.5;
		if (timePreparationLeft > timePreparationRight) {
			return timePreparationLeft;
		}
		else {
			return timePreparationRight;
		}
	}
	
	public double chooseLeftOrRightPathOfSecondStepTeaPreparation() {
		double timePreparationLeft = getTemperatureHotBeverageSelected() * 0.15 ;
		double timePreparationRight = 0.0 ;
		if (!this.theDFM.getOwnCup()) {
			timePreparationRight+=3.0;
		}
		if (timePreparationLeft > timePreparationRight) {
			return timePreparationLeft;
		}
		else {
			return timePreparationRight;
		}
	}
	
	public double chooseLeftOrRightPathOfThirdStepTeaPreparation() {
		double timePreparationLeft = (sizeSlider.getValue()+1)*1.5 ;
		double timePreparationRight = 3.0;
		if (timePreparationLeft > timePreparationRight) {
			return timePreparationLeft;
		}
		else {
			return timePreparationRight;
		}
	}
	
	public double timeOfFourthStepTeaPreparation() {
		return 5.0;			
	}
	
	public double chooseLeftOrRightPathsOverAllSoupSteps() {
		double timePreparation = 0.0 ;
		timePreparation += chooseLeftOrRightPathOfFirstStepSoupPreparation();
		timePreparation += chooseLeftOrRightPathOfSecondStepSoupPreparation();
		timePreparation += chooseLeftOrRightPathOfThirdStepSoupPreparation();
		timePreparation += addOptions();
		return timePreparation;
	}
	
	public double chooseLeftOrRightPathOfFirstStepSoupPreparation() {
		double timePreparationLeft = 1.5;
		double timePreparationRight = 0.0;
		if (!this.theDFM.getOwnCup()) {
			timePreparationRight+=3.0;
		}
		if (timePreparationLeft > timePreparationRight) {
			return timePreparationLeft;
		}
		else {
			return timePreparationRight;
		}
	}

	public double chooseLeftOrRightPathOfSecondStepSoupPreparation() {
		double timePreparationLeft = getTemperatureHotBeverageSelected() * 0.15 ;
		double timePreparationRight = 2.5 ;
		if (timePreparationLeft > timePreparationRight) {
			return timePreparationLeft;
		}
		else {
			return timePreparationRight;
		}
	}
	
	public double chooseLeftOrRightPathOfThirdStepSoupPreparation() {
		double timePreparationLeft = (sizeSlider.getValue()+1)*1.5 ;
		double timePreparationRight = 0.0;
		if (timePreparationLeft > timePreparationRight) {
			return timePreparationLeft;
		}
		else {
			return timePreparationRight;
		}
	}
	
	public double chooseLeftOrRightPathsOverAllIcedTeaSteps() {
		double timePreparation = 0.0;
		timePreparation += chooseLeftOrRightPathOfFirstStepIcedTeaPreparation();
		timePreparation += chooseLeftOrRightPathOfSecondStepIcedTeaPreparation();
		timePreparation += chooseLeftOrRightPathOfThirdStepIcedTeaPreparation();
		timePreparation += timeOfFourthStepIcedTeaPreparation();
		timePreparation += timeOfClosingDoor();
		timePreparation += timeOfLiquidNitrogenInjection();
		timePreparation += timeOfOpeningDoor();
		timePreparation += addOptions();
		return timePreparation;
	}
	
	public double chooseLeftOrRightPathOfFirstStepIcedTeaPreparation() {
		double timePreparationLeft = 1.5 ;
		double timePreparationRight = 2.5;
		if (timePreparationLeft > timePreparationRight) {
			return timePreparationLeft;
		}
		else {
			return timePreparationRight;
		}
	}
	
	public double chooseLeftOrRightPathOfSecondStepIcedTeaPreparation() {
		double timePreparationLeft = getTemperatureHotBeverageSelected() * 0.15 ;
		double timePreparationRight = 0.0 ;
		if (!this.theDFM.getOwnCup()) {
			timePreparationRight+=3.0;
		}
		if (timePreparationLeft > timePreparationRight) {
			return timePreparationLeft;
		}
		else {
			return timePreparationRight;
		}
	}
	
	public double chooseLeftOrRightPathOfThirdStepIcedTeaPreparation() {
		double timePreparationLeft = (sizeSlider.getValue()+1)*1.5 ;
		double timePreparationRight = 3.0;
		if (timePreparationLeft > timePreparationRight) {
			return timePreparationLeft;
		}
		else {
			return timePreparationRight;
		}
	}
	
	public double timeOfFourthStepIcedTeaPreparation() {
		return 5.0;			
	}
	
	public double timeOfClosingDoor() {
		return 4.0;
	}
	
	public double timeOfLiquidNitrogenInjection() {
		return ((-1/3)*getTemperatureColdBeverageSelected())+20;
	}
	
	public double timeOfOpeningDoor() {
		return 4.0;
	}
	
	public double timePreparation(Beverage beverage) { // le temps se calcul en fonction de la machine à état
		double timePreparation = 0; 
		
		switch(beverage.getName()) {
		case "café":
			timePreparation += chooseLeftOrRightPathsOverAllCoffeeSteps();
			break;
		case "expresso":
			timePreparation += chooseLeftOrRightPathsOverAllExpressoSteps();
			break;
		case "thé":
			timePreparation += chooseLeftOrRightPathsOverAllTeaSteps();
			break;
		case "soupe":
			timePreparation += chooseLeftOrRightPathsOverAllSoupSteps();
			break;
		case "thé glacé":
			timePreparation += chooseLeftOrRightPathsOverAllIcedTeaSteps();
		}
		
		return timePreparation;
	}
	
	private double addOptions() {
		double result = 0.0;
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
	
	

	public void progressBarStart() { // Les threads.sleep sont très mauvais, comportement chaotique de la barre de progression
		
		long temps = (long)(timePreparation(beverageChoice)*10);
		Runnable r = new Runnable() {
			
			@Override
			public void run() {
				long start = System.currentTimeMillis();
				while(true) {
					try {
						Thread.sleep(temps); 
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (progressBar.getValue() != 100) {
						progressBar.setValue(progressBar.getValue()+1);
					}
					else {
						break;
					}
				}
				/*messagesToUser.setText("<html> temps passé : " + (System.currentTimeMillis()-start) + 
						"<br> temps normal : " + temps);*/
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
		temperatureTable.put(1, new JLabel("4°C"));
		temperatureTable.put(2, new JLabel("7°C"));
		temperatureTable.put(3, new JLabel("10°C"));
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
		if(supply.enoughMilkDose()) {
			contentPane.add(checkboxMilk);
		}else {
			messagesToUserOption1.setText("Nuage de lait indisponible.");
		}
		
		if(supply.enoughMapleSyrupDose()) {
			contentPane.add(checkboxMapleSyrup);
		}else {
			messagesToUserOption2.setText("Sirop d'érable indisponible.");
		}
		
		if(supply.enoughVanillaDose()) {
			contentPane.add(checkboxVanilla);
		}else {
			messagesToUserOption3.setText("Glace vanille indisponible.");
		}
		
		
	}
	
	public void updatePanelTeaOption() {		
		if(supply.enoughMilkDose()) {
			contentPane.add(checkboxMilk);
		}else {
			messagesToUserOption1.setText("Nuage de lait indisponible.");
		}
		
		if(supply.enoughMapleSyrupDose()) {
			contentPane.add(checkboxMapleSyrup);
		}else {
			messagesToUserOption2.setText("Sirop d'érable indisponible.");
		}
		
	}
	
	public void updatePanelSoupOption() {
		if(supply.enoughCroutonDose()) {
			contentPane.add(checkboxCrouton);
		}else {
			messagesToUserOption1.setText("Croutons indisponible.");
		}
	}
	
	
	public void updatePanelIcedTeaOption() {
		if(supply.enoughMapleSyrupDose()) {
			contentPane.add(checkboxMapleSyrup);
		}else {
			messagesToUserOption2.setText("Sirop d'érable indisponible.");
		}
	}
	

	public void resetPanelOption() {
		contentPane.remove(checkboxMilk);
		contentPane.remove(checkboxMapleSyrup);
		contentPane.remove(checkboxVanilla);
		contentPane.remove(checkboxCrouton);

		checkboxMilk.setSelected(false);
		theDFM.setMilkOption(false);
		
		checkboxVanilla.setSelected(false);
		theDFM.setVanillaOption(false);
		
		checkboxMapleSyrup.setSelected(false);
		theDFM.setMapleSyrupOption(false);
		
		checkboxCrouton.setSelected(false);
		theDFM.setCroutonOption(false);
		
		messagesToUserOption1.setText("");
		messagesToUserOption2.setText("");
		messagesToUserOption3.setText("");
		
		contentPane.revalidate();
		contentPane.repaint();
	}
	
	
	public void updateStock() {
		messagesToProviderStock.setText(supply.toString());
	}
	
	
	public void updatePicture() {
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("./picts/vide2.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.labelForPictures.setIcon(new ImageIcon(myPicture));
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
		
		this.supply = new Supply();
		
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
		setBounds(100, 100, 840, 650);
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
		
		textField = new JTextField();
	    textField.setDocument(new JTextFieldLimit(16));
	    setVisible(true);
	    textField.setBounds(510, 190, 120, 20);
	    contentPane.add(textField);
		
		
		messagesToUserOption1 = new JLabel("");
		messagesToUserOption1.setForeground(Color.RED);
		messagesToUserOption1.setHorizontalAlignment(SwingConstants.LEFT);
		messagesToUserOption1.setVerticalAlignment(SwingConstants.TOP);
		messagesToUserOption1.setBackground(Color.WHITE);
		messagesToUserOption1.setBounds(42, 361, 225, 150);
		contentPane.add(messagesToUserOption1);
		
		messagesToUserOption2 = new JLabel("");
		messagesToUserOption2.setForeground(Color.RED);
		messagesToUserOption2.setHorizontalAlignment(SwingConstants.LEFT);
		messagesToUserOption2.setVerticalAlignment(SwingConstants.TOP);
		messagesToUserOption2.setBounds(42, 386, 225, 150);
		contentPane.add(messagesToUserOption2);
		
		messagesToUserOption3 = new JLabel("");
		messagesToUserOption3.setForeground(Color.RED);
		messagesToUserOption3.setHorizontalAlignment(SwingConstants.LEFT);
		messagesToUserOption3.setVerticalAlignment(SwingConstants.TOP);
		messagesToUserOption3.setBounds(42, 411, 225, 150);
		contentPane.add(messagesToUserOption3);
		
		
		
		messagesToProviderStock = new JLabel(supply.toString());
		messagesToProviderStock.setForeground(Color.WHITE);
		messagesToProviderStock.setHorizontalAlignment(SwingConstants.LEFT);
		messagesToProviderStock.setVerticalAlignment(SwingConstants.TOP);
		messagesToProviderStock.setBackground(Color.WHITE);
		messagesToProviderStock.setBounds(675, 12, 225, 600);
		contentPane.add(messagesToProviderStock);
		
		
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
		separator.setBounds(12, 292, 645, 15);
		contentPane.add(separator);
		
		JSeparator separator2 = new JSeparator();
		separator2.setBounds(650, 12, 15, 600);
		separator2.setOrientation(JSeparator.VERTICAL);
		contentPane.add(separator2);

		JButton addCupButton = new JButton("Add cup");
		addCupButton.setForeground(Color.BLACK);
		addCupButton.setBackground(Color.DARK_GRAY);
		addCupButton.setBounds(538, 336, 96, 25);
		contentPane.add(addCupButton);

		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("./picts/vide2.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.labelForPictures = new JLabel(new ImageIcon(myPicture));
		this.labelForPictures.setBounds(255, 336, 286, 260);
		
		contentPane.add(this.labelForPictures);	
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.DARK_GRAY);
		panel_2.setBounds(538, 217, 96, 33);
		contentPane.add(panel_2);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setForeground(Color.BLACK);
		cancelButton.setBackground(Color.DARK_GRAY);
		panel_2.add(cancelButton);

		
		//OPTIONS
	
		JLabel options = new JLabel("Options");
		options.setForeground(Color.WHITE);
		options.setHorizontalAlignment(SwingConstants.LEFT);
		options.setVerticalAlignment(SwingConstants.TOP);
		options.setBackground(Color.WHITE);
		options.setBounds(35, 336, 105, 15);
		contentPane.add(options);
		
		checkboxMilk = new JCheckBox("Nuage de lait (+0.10€)");
		checkboxMilk.setForeground(Color.WHITE);
		checkboxMilk.setBackground(Color.DARK_GRAY);
		checkboxMilk.setBounds(35, 361, 200, 20);
				
		checkboxMapleSyrup = new JCheckBox("Sirop d'érable (+0.10€)");
		checkboxMapleSyrup.setForeground(Color.WHITE);
		checkboxMapleSyrup.setBackground(Color.DARK_GRAY);
		checkboxMapleSyrup.setBounds(35, 386, 200, 20);
		
		checkboxVanilla = new JCheckBox("Glace vanille mixée (+0.40€)");
		checkboxVanilla.setForeground(Color.WHITE);
		checkboxVanilla.setBackground(Color.DARK_GRAY);	
		checkboxVanilla.setBounds(35, 411, 200, 20);
		
		checkboxCrouton = new JCheckBox("Croutons (+0.30€)");
		checkboxCrouton.setForeground(Color.WHITE);
		checkboxCrouton.setBackground(Color.DARK_GRAY);
		checkboxCrouton.setBounds(35, 361, 200, 20);
		
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
				if(theDFM.getOnWire()) {
					resetPanelOption();
					if (supply.enoughCoffeeDose() &&  supply.enoughSugarDose() && (supply.enoughGoblet() || theDFM.getOwnCup())) {
						temperatureClassicBeverage();
						sugarClassicBeverage();
						classicSizeBeverage();
						updatePanelCofExpOption();
						beverageChoice = new Coffee();
						theDFM.raiseCoffeeButton();
					}else {
						messagesToUser.setText("<html> Désolé le café est indisponible pour le moment." );
					}
				}
			}
		});
		
		expressoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				if(theDFM.getOnWire()) {
					resetPanelOption();
					if (supply.enoughGrain() &&  supply.enoughSugarDose() && (supply.enoughGoblet() || theDFM.getOwnCup())) {
						resetPanelOption();
						temperatureClassicBeverage();
						sugarClassicBeverage();
						classicSizeBeverage();
						updatePanelCofExpOption();
						beverageChoice = new Expresso();
						theDFM.raiseExpressoButton();
					}else {
						messagesToUser.setText("<html> Désolé l'expresso est indisponible pour le moment." );
					}
				}
			}
		});
		
		teaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				if(theDFM.getOnWire()) {
					resetPanelOption();
					if (supply.enoughTeaBag() &&  supply.enoughSugarDose() && (supply.enoughGoblet() || theDFM.getOwnCup())) {
						temperatureClassicBeverage();
						sugarClassicBeverage();
						classicSizeBeverage();
						updatePanelTeaOption();
						beverageChoice = new Tea();
						theDFM.raiseTeaButton();
					}else{
						messagesToUser.setText("<html> Désolé le thé est indisponible pour le moment." );
					}
				}
			}
		});
		
		soupButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				if(theDFM.getOnWire()) {
					resetPanelOption();
					if (supply.enoughSoupDose() &&  supply.enoughSpicyDose() && (supply.enoughGoblet() || theDFM.getOwnCup())) {
						if (theDFM.getPaymentCard() == true) {
							delayBlueCardPayment = true;
							theDFM.setPaymentCard(false);
							}
						temperatureClassicBeverage();
						spicySoupBeverage();
						classicSizeBeverage();
						updatePanelSoupOption();
						beverageChoice = new Soup();
						theDFM.raiseSoupButton();
					}else{
						messagesToUser.setText("<html> Désolé la soupe est indisponible pour le moment." );
					}
				}
			}
		});
		
		icedTeaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				if (theDFM.getOnWire()) {
					resetPanelOption();
					if (supply.enoughTeaBag() &&  supply.enoughSugarDose() && supply.enoughNitrogenDose() && (supply.enoughGoblet() || theDFM.getOwnCup())) {
						icedTeaTemperature();
						sugarClassicBeverage();
						icedTeaSizeBeverage();
						updatePanelIcedTeaOption();
						beverageChoice = new IcedTea();
						theDFM.raiseTeaButton();
					}else {
						messagesToUser.setText("<html> Désolé l'iced tea est indisponible pour le moment." );
					}
				}
			}
		});
	}
	

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		/*t.destroy();
		t1.destroy();
		t2.destroy();*/
	}
	
}