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
		if (this.dfm.theDFM.getOnWire()) {
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
		this.dfm.messagesToUser.setText("<html> Veuillez récupérer votre boisson svp. <br>");
		try {
			this.dfm.labelForPictures.setIcon(new ImageIcon(ImageIO.read(new File("./picts/ownCup.jpg"))));
		} catch (IOException ee) {
			ee.printStackTrace();
		}	
		this.dfm.messagesToUser2.setText("");
		this.dfm.messagesToUser3.setText("");
	}
	
	@Override
	public void onTakeChangeRaised() {
		if (this.dfm.theDFM.getSolde() > 0.0) {
			if (!this.dfm.theDFM.getPaymentCard()) {
				this.dfm.messagesToUser.setText("<html>Argent à récuperer : <br>" + this.dfm.theDFM.getSolde() + " €");
				this.dfm.theDFM.setSolde(0.0);	
			}
		}
		this.dfm.theDFM.setOnWire(true); 
	}

	@Override
	public void onCleaningMachineRaised() {
		try {
			this.dfm.labelForPictures.setIcon(new ImageIcon(ImageIO.read(new File("./picts/vide2.jpg"))));
		} catch (IOException ee) {
			ee.printStackTrace();
		}
		this.dfm.messagesToUser.setText("<html> Attendez, votre machine est en cours de nettoyage.");	
	}

	@Override
	public void onMachineReadyRaised() {
		this.dfm.messagesToUser.setText("<html> Veuillez insérer de l'argent <br> Solde : " + this.dfm.theDFM.getSolde() + "€");
		this.dfm.theDFM.setOnWire(true); 
		this.dfm.theDFM.setPaymentDone(false);
		this.dfm.theDFM.setEnoughMoney(false);
		this.dfm.theDFM.setPaymentCard(false);
		this.dfm.beverageChoice =null;
		this.dfm.progressBar.setValue(0);
		this.dfm.numberTaskDone=0;
		onResetSlidersRaised();
	}
	
	@Override
	public void onBeveragePreparationRaised() {
		this.dfm.messagesToUser.setText("<html> Votre " + this.dfm.beverageChoice.getName() + " est en cours de préparation ... ");
		this.dfm.theDFM.setBeverageSelected(this.dfm.beverageChoice.getName());
		
		int temperatureSelected = 0;
		switch(this.dfm.temperatureSlider.getValue()) {
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
		this.dfm.theDFM.setTemperatureSelected(temperatureSelected);
		this.dfm.theDFM.setSizeSelected(this.dfm.sizeSlider.getValue()+1);
		this.dfm.calculateTimePreparation();
	
		this.dfm.theDFM.setOnWire(false); 
		}

	@Override
	public void onCancelPreparationRaised() {
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
		this.dfm.messagesToUser.setText(this.dfm.messagesToUser.getText()+"<html> <br> Paiement autorisé.");
		if(!this.dfm.theDFM.getPaymentCard()) {
			this.dfm.theDFM.setSolde(this.dfm.roundValue(this.dfm.theDFM.getSolde()-this.dfm.beverageChoice.getPrice()));
		}
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
		this.dfm.messagesToUser2.setText("<html> Attente de la température de l'eau adéquate... ");
		
	}

	@Override
	public void onFlowWaterRaised() {
		this.dfm.messagesToUser2.setText("<html> Ecoulement de l'eau...");
	}

	@Override
	public void onAddSugarRaised() {
		this.dfm.messagesToUser3.setText("<html> Ajout du sucre (" +  this.dfm.sugarSlider.getValue() +" doses)...");
	}

	@Override
	public void onFlowWaterOKRaised() {
		this.dfm.messagesToUser2.setText("<html> Ecoulement de l'eau... ✓");
		onUpdateProgressBarRaised();
	}

	@Override
	public void onSugarOKRaised() {
		this.dfm.messagesToUser3.setText("<html> Ajout du sucre (" +  this.dfm.sugarSlider.getValue() +" doses)... ✓");
		onUpdateProgressBarRaised();
	}

	@Override
	public void onGrainPackingOKRaised() {
		this.dfm.messagesToUser3.setText("<html> Tassage des grains... ✓");	
		onUpdateProgressBarRaised();
	}

	@Override
	public void onWaterTempOKRaised() {
		this.dfm.messagesToUser2.setText("<html> Attente de la température de l'eau adéquate... ✓");
		onUpdateProgressBarRaised();
	}

	@Override
	public void onWaterHeatingOKRaised() {
		this.dfm.messagesToUser2.setText("<html> Démarrage du chauffage de l'eau... ✓");
		onUpdateProgressBarRaised();
	}

	@Override
	public void onTeaBagOKRaised() {
		this.dfm.messagesToUser3.setText("<html> Récupération et positionnement d’un sachet... ✓");	
		onUpdateProgressBarRaised();
	}

	@Override
	public void onPodOKRaised() {
		this.dfm.messagesToUser3.setText("<html> Récupération et positionnement d’une dosette... ✓");
		onUpdateProgressBarRaised();
	}

	@Override
	public void onGrainCrushingOKRaised() {
		this.dfm.messagesToUser3.setText("<html> Broyage des grains... ✓");
		onUpdateProgressBarRaised();
	}

	@Override
	public void onCupOKRaised() {
		this.dfm.messagesToUser3.setText("<html> Positionnement du gobelet... ✓");	
		onUpdateProgressBarRaised();
	}

	@Override
	public void onWaitingInfusionRaised() {
		this.dfm.messagesToUser3.setText("<html> Attente de l'infusion... ");
	}

	@Override
	public void onRemoveTeaBagRaised() {
		this.dfm.messagesToUser3.setText("<html> Retrait du sachet... ");
	}

	@Override
	public void onUpdateProgressBarRaised() {
		if((this.dfm.totalNumberTask-1) == this.dfm.numberTaskDone) {
			this.dfm.progressBar.setValue(this.dfm.progressBar.getValue()+ (100-this.dfm.progressBar.getValue()));
		}else {
			this.dfm.progressBar.setValue(this.dfm.progressBar.getValue()+(int)(100/this.dfm.totalNumberTask));
			this.dfm.numberTaskDone++;
		}
	}

	@Override
	public void onInfusionOKRaised() {
		onUpdateProgressBarRaised();		
	}

	@Override
	public void onRemoveTeaBagOKRaised() {
		onUpdateProgressBarRaised();		
	}

}
