package fr.univcotedazur.polytech.si4.fsm.project;

//import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import fr.univcotedazur.polytech.si4.fsm.project.defaultsm.IDefaultSMStatemachine.SCInterfaceListener;

public class DrinkFactoryMachineInterfaceImplementation implements SCInterfaceListener {

	DrinkFactoryMachine dfm;
	
	public DrinkFactoryMachineInterfaceImplementation(DrinkFactoryMachine dfm) {
		this.dfm = dfm;
	}
	
	@Override
	public void onUpdateSoldeRaised() {
		if (this.dfm.machineSurEcoute) {
			if(this.dfm.beverageChoice == null) {
				this.dfm.messagesToUser.setText("Solde : " + this.dfm.theDFM.getSolde() + " €" );
				this.dfm.enoughMoney();
			}else {
				onBeverageChoiceRaised();
			}
			
		}
	}

	@Override
	public void onTakeBeverageRaised() {
		this.dfm.messagesToUser.setText("<html> Veuillez prendre votre boisson svp. <br>");
		if (this.dfm.theDFM.getSolde() > 0.0) {
			if (!this.dfm.theDFM.getPaymentCard()) {
				this.dfm.messagesToUser.setText("<html>Argent à récuperer : <br>" + this.dfm.theDFM.getSolde() + " €");
				this.dfm.theDFM.setSolde(0.0);	
			}
		}
		
		try {
			this.dfm.labelForPictures.setIcon(new ImageIcon(ImageIO.read(new File("./picts/ownCup.jpg"))));
		} catch (IOException ee) {
			ee.printStackTrace();
		}	
		this.dfm.messagesToUser2.setText("");
		this.dfm.messagesToUser3.setText("");
	}

	@Override
	public void onCleaningMachineRaised() {
		try {
			this.dfm.labelForPictures.setIcon(new ImageIcon(ImageIO.read(new File("./picts/vide2.jpg"))));
		} catch (IOException ee) {
			ee.printStackTrace();
		}
		this.dfm.messagesToUser.setText("<html> Attendez, votre machine est en cours de nettoyage.");	
		onResetSlidersRaised();
	}

	@Override
	public void onMachineReadyRaised() {
		this.dfm.messagesToUser.setText("<html> Veuillez insérer de l'argent <br> Solde : " + this.dfm.theDFM.getSolde() + "€");
		this.dfm.machineSurEcoute = true;
		this.dfm.theDFM.setPaymentDone(false);
		this.dfm.theDFM.setEnoughMoney(false);
		this.dfm.theDFM.setPaymentCard(false);
		this.dfm.beverageChoice =null;
	}
	
	@Override
	public void onBeveragePreparationRaised() {
		this.dfm.messagesToUser.setText("<html> Votre " + this.dfm.beverageChoice.getName() + " est en cours de préparation ... ");
		this.dfm.machineSurEcoute = false;
	}

	@Override
	public void onCancelPreparationRaised() {
		this.dfm.machineSurEcoute = true;
		this.dfm.messagesToUser.setText("<html> Vous n'avez rien choisi, n'oubliez pas de récupérer vos "  + this.dfm.theDFM.getSolde() + "€. <br> Au revoir.");		
		this.dfm.theDFM.setSolde(0.0);
		onResetSlidersRaised();
	}

	@Override
	public void onPayByNFCRaised() {
		this.dfm.theDFM.setPaymentCard(true);
		this.dfm.messagesToUser.setText("<html> Veuillez choisir votre <br> boisson.");
	}

	@Override
	public void onCancelTransactionRaised() {
		this.dfm.messagesToUser.setText("<html> Vous n'avez rien choisi, Transaction annulée. <br> Vous n'avez pas été débité. <br> Au revoir.");
		this.dfm.theDFM.setPaymentCard(false);
	}

	@Override
	public void onResetSlidersRaised() {
		this.dfm.sugarSlider.setValue(1);
		this.dfm.temperatureSlider.setValue(2);
		this.dfm.sizeSlider.setValue(1);
	}

	@Override
	public void onValidatePaymentRaised() {
		this.dfm.messagesToUser.setText("<html> Paiement autorisé.");
		if(!this.dfm.theDFM.getPaymentCard()) {
			this.dfm.theDFM.setSolde(this.dfm.roundValue(this.dfm.theDFM.getSolde()-this.dfm.beverageChoice.getPrice()));
		}
		this.dfm.theDFM.setBeverageSelected(this.dfm.beverageChoice.getName());
		this.dfm.theDFM.setPaymentDone(true);
	}

	@Override
	public void onBeverageChoiceRaised() {
		this.dfm.messagesToUser.setText("<html> Vous avez choisis "+ this.dfm.beverageChoice.getName() + 
				"<br> Prix : " + this.dfm.beverageChoice.getPrice() + "€." +
				"<br> Votre solde : " + this.dfm.theDFM.getSolde() + "€.");
		this.dfm.enoughMoney();
	}

	@Override
	public void onTurnOnWaterHeatingRaised() {
		this.dfm.messagesToUser2.setText("<html> Démarrage du chauffage de l'eau...");
	}

	@Override
	public void onPlacePodRaised() {
		this.dfm.messagesToUser3.setText("<html> Récupération et positionnement d’une dosette...");
		
	}

	@Override
	public void onPlaceTeaBagRaised() {
		this.dfm.messagesToUser3.setText("<html> Récupération et positionnement d’un sachet...");		
	}

	@Override
	public void onGrindGrainsRaised() {
		this.dfm.messagesToUser3.setText("<html> Broyage des grains...");		
	}

	@Override
	public void onPlaceCupRaised() {
		this.dfm.messagesToUser3.setText("<html> Positionnement du gobelet...");	
		
	}

	@Override
	public void onPackGrainsRaised() {
		this.dfm.messagesToUser3.setText("<html> Tassage des grains...");	
		
	}

	@Override
	public void onWaitTemperatureRaised() {
		this.dfm.messagesToUser2.setText("<html> Attente de la température adéquate...");
		
	}

}
