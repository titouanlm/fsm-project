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
			this.dfm.messagesToUser.setText("Solde : " + this.dfm.theDFM.getSolde() + "€" );
		}
	}

	@Override
	public void onResetSoldeRaised() {
		this.dfm.messagesToUser.setText("<html> Vous n'avez rien choisi. <br> Récupérez votre monnaie. <br>  Solde : " + this.dfm.theDFM.getSolde() + "€" );
		this.dfm.theDFM.setSolde(0.0);
	}

	@Override
	public void onTakeBeverageRaised() {
		if (this.dfm.theDFM.getSolde() == 0.0) this.dfm.messagesToUser.setText("<html> Veuillez prendre votre boisson svp.");
		else {
			this.dfm.messagesToUser.setText("<html> Veuillez prendre votre boisson svp. <br> N'oubliez pas de récupérer votre argent.");
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
		}

	@Override
	public void onMachineReadyRaised() {
		this.dfm.messagesToUser.setText("<html> Veuillez insérer de l'argent <br> Solde : " + this.dfm.theDFM.getSolde() + "€");
		this.dfm.machineSurEcoute = true;
	}
	
	@Override
	public void onMakeCoffeeRaised() {
		if (this.dfm.machineSurEcoute) {
			this.dfm.machineSurEcoute = false;
			if (this.dfm.theDFM.getSolde() >= 0.35) this.dfm.messagesToUser.setText("<html> Votre café est en cours de préparation ... ");
			else this.dfm.messagesToUser.setText("<html> Veuillez insérer l'argent nécéssaire pour la préparation de votre café");
		}
		
	}

	@Override
	public void onMakeTeaRaised() {
		if (this.dfm.machineSurEcoute) {
			this.dfm.machineSurEcoute = false;
			if (this.dfm.theDFM.getSolde() >= 0.4) this.dfm.messagesToUser.setText("<html> Votre thé est en cours de préparation ... ");
			else this.dfm.messagesToUser.setText("<html> Veuillez insérer l'argent nécéssaire pour la préparation de votre thé");
		}
	}

	@Override
	public void onMakeExpressoRaised() {
		if (this.dfm.machineSurEcoute) {
			this.dfm.machineSurEcoute = false;
			if (this.dfm.theDFM.getSolde() >= 0.5) this.dfm.messagesToUser.setText("<html> Votre expresso est en cours de préparation ... ");
			else this.dfm.messagesToUser.setText("<html> Veuillez insérer l'argent nécéssaire pour la préparation de votre expresso");
		}
	}

	@Override
	public void onMakeSoupRaised() {
		if (this.dfm.machineSurEcoute) {
			this.dfm.machineSurEcoute = false;
			if (this.dfm.theDFM.getSolde() >= 1.0) this.dfm.messagesToUser.setText("<html> Votre soupe est en cours de préparation ... ");
			else this.dfm.messagesToUser.setText("<html> Veuillez insérer l'argent nécéssaire pour la préparation de votre soupe");
			}
		
	}

	@Override
	public void onMakeIcedTeaRaised() {
		if (this.dfm.machineSurEcoute) {
			this.dfm.machineSurEcoute = false;
			if (this.dfm.theDFM.getSolde() >= 1.3) this.dfm.messagesToUser.setText("<html> Votre thé glacé est en cours de préparation ... ");
			else this.dfm.messagesToUser.setText("<html> Veuillez insérer l'argent nécéssaire pour la préparation de votre thé glacé");
		}
		
	}

	@Override
	public void onCancelPreparationRaised() {
		this.dfm.machineSurEcoute = true;
		this.dfm.messagesToUser.setText("<html> Vous n'avez rien choisi, n'oubliez pas de reprendre votre argent. <br> Au revoir.");		
		this.dfm.theDFM.setSolde(0.0);
	}

}
