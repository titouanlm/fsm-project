package fr.univcotedazur.polytech.si4.fsm.project;

import java.awt.image.BufferedImage;
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
				this.dfm.enoughMoneyClassicBeverage();
			}else {
				onBeverageChoiceRaised();
			}
			
		}
	}

	@Override
	public void onTakeBeverageRaised() {
		this.dfm.messagesToUser.setText("<html> Veuillez récupérer votre boisson svp. <br>");
		if(!this.dfm.theDFM.getOwnCup()) {
			BufferedImage myPicture = null;
			try {
				myPicture = ImageIO.read(new File("./picts/gobeletPolluant.jpg"));
			} catch (IOException ee) {
				ee.printStackTrace();
			}
			this.dfm.labelForPictures.setIcon(new ImageIcon(myPicture));
		}
		this.dfm.onListening=true;
		this.dfm.messagesToUser2.setText("");
		this.dfm.messagesToUser3.setText("");
	}
	
	@Override
	public void onTakeChangeRaised() {
		this.dfm.updatePicture();
	
		if (this.dfm.theDFM.getSolde() > 0.0) {
			if (!this.dfm.theDFM.getPaymentCard()) {
				this.dfm.messagesToUser.setText("<html>Argent à récuperer : <br>" + this.dfm.theDFM.getSolde() + " €");
				this.dfm.theDFM.setSolde(0.0);	
			}
		}else {
			this.dfm.messagesToUser.setText("<html>Pas de monnaie à rendre.");
		}
	}

	@Override
	public void onCleaningMachineRaised() {
		this.dfm.messagesToUser.setText("<html> Attendez, votre machine est en cours de nettoyage.");	
	}

	@Override
	public void onMachineReadyRaised() {
		this.dfm.messagesToUser.setText("<html> Veuillez insérer de l'argent <br> Solde : " + this.dfm.theDFM.getSolde() + "€");
		this.dfm.theDFM.setOnWire(true); 
		this.dfm.theDFM.setPaymentDone(false);
		this.dfm.theDFM.setEnoughMoney(false);
		this.dfm.theDFM.setPaymentCard(false);
		this.dfm.delayBlueCardPayment = false;
		this.dfm.theDFM.setOwnCup(false);
		this.dfm.theDFM.setIsBeverageTaken(false);
		this.dfm.abledSliders();
		this.dfm.beverageChoice =null;
		this.dfm.beveragePriceAfterDiscount= 0.;
		this.dfm.resetPanelOption();
		this.dfm.onListening = false;
		this.dfm.updateStock();
		this.dfm.updatePicture();
		this.dfm.textField.setText("");
		this.dfm.discountApplied=false;
		onResetSlidersRaised();
	}
	
	@Override
	public void onBeveragePreparationRaised() {
		this.dfm.messagesToUser.setText("<html> Votre " + this.dfm.beverageChoice.getName() + " est en cours de préparation ... ");
		this.dfm.progressBarStart();
		if (this.dfm.theDFM.getPaymentCard()) {
			if (!this.dfm.textField.getText().equals("") && !this.dfm.discountApplied) {
			WriteAndDecodeFile.addHashInfoCard(this.dfm.textField.getText(), this.dfm.beveragePriceAfterDiscount);
			}
		}
		this.dfm.textField.setText("");
	}
	
	@Override
	public void onValidatePaymentRaised() { 
		if (!this.dfm.textField.getText().equals("") && this.dfm.verifyCustomerDiscount(this.dfm.textField.getText())) {
			this.dfm.discountApplied=true;
			this.dfm.messagesToUser.setText("<html> Vous avez droit à une remise !"
					+ "<br> Votre boisson vous coutera : " + Calculator.roundValue(this.dfm.beveragePriceAfterDiscount) + "€.<br> cadeau de la maison :)");
		}
		else {
			this.dfm.messagesToUser.setText(this.dfm.messagesToUser.getText()+"<html> <br> Paiement autorisé.");
			if(!this.dfm.theDFM.getPaymentCard()) {
				this.dfm.theDFM.setSolde(Calculator.roundValue(this.dfm.theDFM.getSolde()-this.dfm.beveragePriceAfterDiscount));
			}
		}
		this.dfm.theDFM.setBeverageSelected(this.dfm.beverageChoice.getName());
		this.dfm.theDFM.setTemperatureSelected(Calculator.getTemperatureHotBeverageSelected(this.dfm.temperatureSlider));
		this.dfm.theDFM.setSizeSelected(this.dfm.sizeSlider.getValue()+1);
		this.dfm.theDFM.setOnWire(false);
		this.dfm.disabledSliders();
		this.dfm.disabledOptionsCheckBox();
		this.dfm.theDFM.setPaymentDone(true);
	}

	@Override
	public void onCancelPreparationRaised() {
		this.dfm.messagesToUser.setText("<html> Vous n'avez rien choisi, n'oubliez pas de récupérer vos "  + this.dfm.theDFM.getSolde() + "€. <br> Au revoir.");		
		this.dfm.theDFM.setSolde(0.0);
		onResetSlidersRaised();
	}

	@Override
	public void onPayByNFCRaised() {
		if (!this.dfm.delayBlueCardPayment) this.dfm.theDFM.setPaymentCard(true);
		else this.dfm.theDFM.setPaymentCard(false);
		if (this.dfm.beverageChoice == null) {
			this.dfm.messagesToUser.setText("<html> Veuillez choisir votre <br> boisson.");
		}
	}

	@Override
	public void onCancelTransactionRaised() {
		this.dfm.messagesToUser.setText("<html> Vous n'avez rien choisi, Transaction annulée. <br> Vous n'avez pas été débité. <br> Au revoir.");
		this.dfm.theDFM.setPaymentCard(false);
	}

	@Override
	public void onResetSlidersRaised() {
		this.dfm.sugarClassicBeverage();
		this.dfm.temperatureClassicBeverage();
		this.dfm.classicSizeBeverage();
		this.dfm.temperatureSlider.setValue(2);
		this.dfm.sizeSlider.setValue(1);
		this.dfm.progressBar.setValue(0);
	}

	@Override
	public void onBeverageChoiceRaised() {
		if(this.dfm.theDFM.getOwnCup()) {
			this.dfm.beveragePriceAfterDiscount =Calculator.roundValue(this.dfm.beverageChoice.getPrice()-0.1);
		}else {
			this.dfm.beveragePriceAfterDiscount = Calculator.roundValue(this.dfm.beverageChoice.getPrice());
		}
		
		//OPTIONS
		if(this.dfm.theDFM.getMilkOption()) {
			this.dfm.beveragePriceAfterDiscount = Calculator.roundValue(this.dfm.beveragePriceAfterDiscount+0.1);
		}
		
		if(this.dfm.theDFM.getVanillaOption()) {
			this.dfm.beveragePriceAfterDiscount = Calculator.roundValue(this.dfm.beveragePriceAfterDiscount+0.6);
		}
		
		if(this.dfm.theDFM.getMapleSyrupOption()) {
			this.dfm.beveragePriceAfterDiscount = Calculator.roundValue(this.dfm.beveragePriceAfterDiscount+0.1);
		}
		
		if(this.dfm.theDFM.getCroutonOption()) {
			this.dfm.beveragePriceAfterDiscount = Calculator.roundValue(this.dfm.beveragePriceAfterDiscount+0.3);
		}
		
		
		if (this.dfm.beverageChoice.getName() != "thé glacé") { 
			
			if (this.dfm.beverageChoice.getName() == "soupe") {
				if (this.dfm.sugarOrSpicySlider.getValue() == 0) {
					this.dfm.messagesToUser.setText("<html> Vous avez choisi "+ this.dfm.beverageChoice.getName() + 
							"<br> Prix : " + this.dfm.beveragePriceAfterDiscount + "€." +
							"<br> Votre solde : " + this.dfm.theDFM.getSolde() + "€." + 
							"<br> Veuillez choisir un niveau d'épice (peut être nul)." );
					this.dfm.onWaitingChangingSpicySlider();
				}
				else {
					this.dfm.messagesToUser.setText("<html> Vous avez choisi "+ this.dfm.beverageChoice.getName() + 
							"<br> Prix : " + this.dfm.beveragePriceAfterDiscount + "€." +
							"<br> Votre solde : " + this.dfm.theDFM.getSolde() + "€.");
					this.dfm.enoughMoneyClassicBeverage();
					
				} 
			}
			else {
				if (this.dfm.theDFM.getPaymentCard() != true) {
				this.dfm.messagesToUser.setText("<html> Vous avez choisi "+ this.dfm.beverageChoice.getName() + 
					"<br> Prix : " + this.dfm.beveragePriceAfterDiscount + "€." +
					"<br> Votre solde : " + this.dfm.theDFM.getSolde() + "€.");
				this.dfm.enoughMoneyClassicBeverage();
				}
				else {
					this.dfm.messagesToUser.setText("<html> Vous avez choisi "+ this.dfm.beverageChoice.getName() + 
							"<br> Prix : " + this.dfm.beveragePriceAfterDiscount);
				}
			}
		}
		else {
			if (this.dfm.theDFM.getPaymentCard() != true) {
				this.dfm.messagesToUser.setText("<html> Vous avez choisi "+ this.dfm.beverageChoice.getName() + 
						"<br> Prix : <br>" +"-"+ this.dfm.beveragePriceAfterDiscount + "<html>€ normal size <br> " 
						+ "-" + (this.dfm.beveragePriceAfterDiscount+0.25) + "€ long size " +
						"<br> Votre solde : " + this.dfm.theDFM.getSolde() + "€.");
				this.dfm.enoughMoneyIcedTeaBeverage();
			}
			else {
				this.dfm.messagesToUser.setText("<html> Vous avez choisi "+ this.dfm.beverageChoice.getName() + 
						"<br> Prix : <br>" +"-"+ this.dfm.beveragePriceAfterDiscount + "<html>€ normal size <br> " 
						+ "-" + (this.dfm.beveragePriceAfterDiscount+0.25) + "€ long size ");
			}
		}
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
		this.dfm.supply.consumeGrain(this.dfm.sizeSlider.getValue()+1);
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
		this.dfm.messagesToUser3.setText("<html> Ajout du sucre (" +  this.dfm.sugarOrSpicySlider.getValue() +" doses)...");
	}

	@Override
	public void onFlowWaterOKRaised() {
		this.dfm.messagesToUser2.setText("<html> Ecoulement de l'eau... ✓");
	}

	@Override
	public void onSugarOKRaised() {
		this.dfm.messagesToUser3.setText("<html> Ajout du sucre (" +  this.dfm.sugarOrSpicySlider.getValue() +" doses)... ✓");
		this.dfm.supply.consumeSugarDoses(this.dfm.sugarOrSpicySlider.getValue());
	}

	@Override
	public void onGrainPackingOKRaised() {
		this.dfm.messagesToUser3.setText("<html> Tassage des grains... ✓");
	}

	@Override
	public void onWaterTempOKRaised() {
		this.dfm.messagesToUser2.setText("<html> Attente de la température de l'eau adéquate... ✓");
	}

	@Override
	public void onWaterHeatingOKRaised() {
		this.dfm.messagesToUser2.setText("<html> Démarrage du chauffage de l'eau... ✓");
	}

	@Override
	public void onTeaBagOKRaised() {
		this.dfm.messagesToUser3.setText("<html> Récupération et positionnement d’un sachet... ✓");
		this.dfm.supply.consumeTeaBag();
	}

	@Override
	public void onPodOKRaised() {
		this.dfm.messagesToUser3.setText("<html> Récupération et positionnement d’une dosette... ✓");
		this.dfm.supply.consumeCoffeeDose();
	}

	@Override
	public void onGrainCrushingOKRaised() {
		this.dfm.messagesToUser3.setText("<html> Broyage des grains... ✓");
	}

	@Override
	public void onCupOKRaised() {
		this.dfm.messagesToUser3.setText("<html> Positionnement du gobelet... ✓");
		this.dfm.supply.consumeGoblet();
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
	public void onOwnCupOKRaised() {
		this.dfm.messagesToUser3.setText("<html> Utilisation de votre tasse... ✓");	
	}

	@Override
	public void onTakeSoupPodRaised() {
		this.dfm.messagesToUser3.setText("<html> Récupération et versement d’une dose de soupe... ");
	}

	@Override
	public void onSoupPodOKRaised() {
		this.dfm.messagesToUser3.setText("<html> Récupération et versement d’une dose de soupe... ✓");
		this.dfm.supply.consumeSoupDose();
	}

	@Override
	public void onAddSpicesRaised() {
		this.dfm.messagesToUser3.setText("<html> Ajout des épices (" +  this.dfm.sugarOrSpicySlider.getValue() +" doses)...");
	}

	@Override
	public void onSpicesOKRaised() {
		this.dfm.messagesToUser3.setText("<html> Ajout des épices (" +  this.dfm.sugarOrSpicySlider.getValue() +" doses)... ✓");
		this.dfm.supply.consumeSpicyDose(this.dfm.sugarOrSpicySlider.getValue());
	}

	@Override
	public void onPoorMilkRaised() {
		this.dfm.messagesToUser3.setText("<html> Ajout d'un nuage de lait...");		
	}

	@Override
	public void onPoorMilkOKRaised() {
		this.dfm.messagesToUser3.setText("<html> Ajout d'un nuage de lait...✓");
		this.dfm.supply.consumeMilkDose();
	}

	@Override
	public void onAddVanillaRaised() {
		this.dfm.messagesToUser3.setText("<html> Ajout d'une dose de glace vanille...");
	}

	@Override
	public void onVanillaOKRaised() {
		this.dfm.messagesToUser3.setText("<html> Ajout d'une dose de glace vanille...✓");
		this.dfm.supply.consumeVanillaDose();
	}

	@Override
	public void onMixBeverageRaised() {
		this.dfm.messagesToUser3.setText("<html> Mixage de la préparation...");
	}

	@Override
	public void onMixOKRaised() {
		this.dfm.messagesToUser3.setText("<html> Mixage de la préparation... ✓");
	}

	@Override
	public void onAddMapleSyrupRaised() {
		this.dfm.messagesToUser3.setText("<html> Ajout du sirop d'érable... ");
	}

	@Override
	public void onMapleSyrupOKRaised() {
		this.dfm.messagesToUser3.setText("<html> Ajout du sirop d'érable... ✓");
		this.dfm.supply.consumeMapleSyrupDose();
	}

	@Override
	public void onAddCroutonRaised() {
		this.dfm.messagesToUser3.setText("<html> Ajout des croutons...") ; 	
	}

	@Override
	public void onCroutonOKRaised() {
		this.dfm.messagesToUser3.setText("<html> Ajout des croutons... ✓") ; 	
		this.dfm.supply.consumeCroutonDose();
	}

	@Override
	public void onClosingDoorRaised() {
		this.dfm.messagesToUser2.setText("<html> Verouillage de la porte...");
		
	}

	@Override
	public void onClosingDoorOKRaised() {
		this.dfm.messagesToUser2.setText("<html> Verouillage de la porte... ✓");
		
	}

	@Override
	public void onLiquidNitrogenInjectionRaised() {
		this.dfm.messagesToUser3.setText("<html> Injection d'azote liquide...");
		
	}

	@Override
	public void onLiquidNitrogenInjectionOKRaised() {
		this.dfm.messagesToUser3.setText("<html> Injection d'azote liquide... ✓");
		this.dfm.supply.consumeNitrogenDose();
	}

	@Override
	public void onOpeningDoorRaised() {
		this.dfm.messagesToUser2.setText("<html> Déverouillage de la porte...");
		
	}

	@Override
	public void onOpeningDoorOKRaised() {
		this.dfm.messagesToUser2.setText("<html> Déverouillage de la porte... ✓");
		
	}

	@Override
	public void onCancelChoiceRaised() {
		onResetSlidersRaised();
		if (this.dfm.theDFM.getSolde() == 0.0 && !this.dfm.theDFM.getPaymentCard()) {
			this.dfm.messagesToUser.setText("Abandon de la préparation");
		}
	}

}


