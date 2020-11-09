package fr.univcotedazur.polytech.si4.fsm.project;

import java.awt.image.BufferedImage;
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
		this.dfm.messagesToUser2.setText("");
		this.dfm.messagesToUser3.setText("");
	}
	
	@Override
	public void onTakeChangeRaised() {
		try {
			this.dfm.labelForPictures.setIcon(new ImageIcon(ImageIO.read(new File("./picts/vide2.jpg"))));
		} catch (IOException ee) {
			ee.printStackTrace();
		}
		this.dfm.progressBar.setValue(0);
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
		this.dfm.theDFM.setOwnCup(false);
		this.dfm.beverageChoice =null;
		this.dfm.beveragePriceAfterDiscount= 0.;
		onResetSlidersRaised();
	}
	
	@Override
	public void onBeveragePreparationRaised() {
		this.dfm.messagesToUser.setText("<html> Votre " + this.dfm.beverageChoice.getName() + " est en cours de préparation ... ");
		this.dfm.theDFM.setBeverageSelected(this.dfm.beverageChoice.getName());
		this.dfm.theDFM.setTemperatureSelected(this.dfm.getTemperatureSelected());
		this.dfm.theDFM.setSizeSelected(this.dfm.sizeSlider.getValue()+1);
		this.dfm.theDFM.setOnWire(false); 
		this.dfm.progressBarStart();
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
		this.dfm.temperatureSlider.setValue(2);
		this.dfm.sizeSlider.setValue(1);
		this.dfm.progressBar.setValue(0);
	}

	@Override
	public void onValidatePaymentRaised() {
		this.dfm.messagesToUser.setText(this.dfm.messagesToUser.getText()+"<html> <br> Paiement autorisé.");
		if(!this.dfm.theDFM.getPaymentCard()) {
			this.dfm.theDFM.setSolde(this.dfm.roundValue(this.dfm.theDFM.getSolde()-this.dfm.beveragePriceAfterDiscount));
		}
		this.dfm.theDFM.setPaymentDone(true);
	}

	@Override
	public void onBeverageChoiceRaised() {
		if(this.dfm.theDFM.getOwnCup()) {
			this.dfm.beveragePriceAfterDiscount = this.dfm.roundValue(this.dfm.beverageChoice.getPrice()-0.1);
		}else {
			this.dfm.beveragePriceAfterDiscount = this.dfm.roundValue(this.dfm.beverageChoice.getPrice());
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
	}

	@Override
	public void onPodOKRaised() {
		this.dfm.messagesToUser3.setText("<html> Récupération et positionnement d’une dosette... ✓");
	}

	@Override
	public void onGrainCrushingOKRaised() {
		this.dfm.messagesToUser3.setText("<html> Broyage des grains... ✓");
	}

	@Override
	public void onCupOKRaised() {
		this.dfm.messagesToUser3.setText("<html> Positionnement du gobelet... ✓");	
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
	
	}

	@Override
	public void onAddSpicesRaised() {
		this.dfm.messagesToUser3.setText("<html> Ajout des épices (" +  this.dfm.sugarOrSpicySlider.getValue() +" doses)...");
	}

	@Override
	public void onSpicesOKRaised() {
		this.dfm.messagesToUser3.setText("<html> Ajout des épices (" +  this.dfm.sugarOrSpicySlider.getValue() +" doses)... ✓");		
	}

}
