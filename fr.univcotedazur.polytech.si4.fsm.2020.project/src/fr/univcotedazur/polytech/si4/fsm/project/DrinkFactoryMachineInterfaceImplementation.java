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
			this.dfm.messagesToUser.setText("Solde : " + this.dfm.theDFM.getSolde() + " €" );
		}
	}

	@Override
	public void onResetSoldeRaised() {
		this.dfm.messagesToUser.setText("<html> Vous n'avez rien choisi. <br> Récupérez votre monnaie. <br>  Solde : " + this.dfm.theDFM.getSolde() + "€" );
		this.dfm.theDFM.setSolde(0.0);
		this.dfm.resetSliders();
	}

	@Override
	public void onTakeBeverageRaised() {
		if (this.dfm.theDFM.getSolde() == 0.0) this.dfm.messagesToUser.setText("<html> Veuillez prendre votre boisson svp.");
		else {
			this.dfm.messagesToUser.setText("<html> Veuillez prendre votre boisson svp. <br>");
			if (!this.dfm.theDFM.getPaymentCard()) {
				this.dfm.messagesToUser.setText("<html>Argent à récuperer : <br>" + Math.round(this.dfm.theDFM.getSolde()*100) + " centimes");
			}
			this.dfm.theDFM.setSolde(0.0);		}
		try {
			this.dfm.labelForPictures.setIcon(new ImageIcon(ImageIO.read(new File("./picts/ownCup.jpg"))));
		} catch (IOException ee) {
			ee.printStackTrace();
		}	
	}

	@Override
	public void onCleaningMachineRaised() {
		try {
			this.dfm.labelForPictures.setIcon(new ImageIcon(ImageIO.read(new File("./picts/vide2.jpg"))));
		} catch (IOException ee) {
			ee.printStackTrace();
		}
		this.dfm.messagesToUser.setText("<html> Attendez, votre machine est en cours de nettoyage.");	
		this.dfm.resetSliders();
	}

	@Override
	public void onMachineReadyRaised() {
		this.dfm.messagesToUser.setText("<html> Veuillez insérer de l'argent <br> Solde : " + this.dfm.theDFM.getSolde() + "€");
		this.dfm.machineSurEcoute = true;
		this.dfm.theDFM.setPaymentCard(false);
	}
	
	@Override
	public void onAskCoffeeRaised() {
		if (this.dfm.machineSurEcoute) {
			if (this.dfm.theDFM.getSolde() < 0.35) {
				this.dfm.messagesToUser.setText("<html> Veuillez insérer l'argent nécéssaire pour la préparation de votre café");
				this.dfm.messagesToUser.setText(Math.round((0.35 - this.dfm.theDFM.getSolde())*100) + " centimes manquants");
			}
		}
	}
	
	@Override
	public void onMakeCoffeeRaised() {
		this.dfm.messagesToUser.setText("<html> Votre café est en cours de préparation ... ");
		this.dfm.theDFM.setSolde(this.dfm.theDFM.getSolde()-0.35);
	}
	
	@Override
	public void onAskTeaRaised() {
		if (this.dfm.machineSurEcoute) {
			if (this.dfm.theDFM.getSolde() < 0.4) {
				this.dfm.messagesToUser.setText("<html> Veuillez insérer l'argent nécéssaire pour la préparation de votre thé <br>");
				this.dfm.messagesToUser.setText(Math.round((0.4 - this.dfm.theDFM.getSolde())*100) + " centimes manquants");
			}
		}
	}

	@Override
	public void onMakeTeaRaised() {
		this.dfm.messagesToUser.setText("<html> Votre thé est en cours de préparation ... ");
		this.dfm.theDFM.setSolde(this.dfm.theDFM.getSolde()-0.40);
	}
	
	@Override
	public void onAskExpressoRaised() {
		if (this.dfm.machineSurEcoute) {
			if (this.dfm.theDFM.getSolde() < 0.5) {
				this.dfm.messagesToUser.setText("<html> Veuillez insérer l'argent nécéssaire pour la préparation de votre expresso");
				this.dfm.messagesToUser.setText(Math.round((0.5 - this.dfm.theDFM.getSolde())*100) + " centimes manquants");			}
		}
	}

	@Override
	public void onMakeExpressoRaised() {
		this.dfm.messagesToUser.setText("<html> Votre expresso est en cours de préparation ... ");
		this.dfm.theDFM.setSolde(this.dfm.theDFM.getSolde()-0.50);
	}
	
	@Override
	public void onAskSoupRaised() {
		if (this.dfm.machineSurEcoute) {
			if (this.dfm.theDFM.getSolde() < 1.0) {
				this.dfm.messagesToUser.setText("<html> Veuillez insérer l'argent nécéssaire pour la préparation de votre soupe");
				this.dfm.messagesToUser.setText(Math.round((1.0 - this.dfm.theDFM.getSolde())*100) + " centimes manquants");
			}
		}
	}

	@Override
	public void onMakeSoupRaised() {
		this.dfm.messagesToUser.setText("<html> Votre soupe est en cours de préparation ... ");
		this.dfm.theDFM.setSolde(this.dfm.theDFM.getSolde()-1.0);
	}
	
	@Override
	public void onAskIcedTeaRaised() {
		if (this.dfm.machineSurEcoute) {
			if (this.dfm.theDFM.getSolde() < 1.3) {
				this.dfm.messagesToUser.setText("<html> Veuillez insérer l'argent nécéssaire pour la préparation de votre thé glacé");
				this.dfm.messagesToUser.setText(Math.round((1.3 - this.dfm.theDFM.getSolde())*100) + " centimes manquants");
			}
		}
		
	}

	@Override
	public void onMakeIcedTeaRaised() {
		this.dfm.messagesToUser.setText("<html> Votre thé glacé est en cours de préparation ... ");
		this.dfm.theDFM.setSolde(this.dfm.theDFM.getSolde()-1.3);
	}

	@Override
	public void onCancelPreparationRaised() {
		this.dfm.machineSurEcoute = true;
		this.dfm.messagesToUser.setText("<html> Vous n'avez rien choisi, n'oubliez pas de reprendre votre argent. <br> Au revoir.");		
		this.dfm.theDFM.setSolde(0.0);
	}

	@Override
	public void onTimePreparationRaised() {
		this.dfm.machineSurEcoute = false;
	}

}
