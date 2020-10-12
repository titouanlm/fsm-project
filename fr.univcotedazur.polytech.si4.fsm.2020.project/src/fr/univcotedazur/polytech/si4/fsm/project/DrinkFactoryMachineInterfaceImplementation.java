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
	public void onMakeCoffeeRaised() {
		if (this.dfm.machineSurEcoute) {
			this.dfm.machineSurEcoute = false;
			this.dfm.messagesToUser.setText("<html> Votre café est en cours de préparation ... ");
		}
		
	}

	@Override
	public void onTakeCoffeeRaised() {
		if (this.dfm.theDFM.getSolde() == 0.0) this.dfm.messagesToUser.setText("<html> Veuillez prendre votre café svp.");
		else {
			this.dfm.messagesToUser.setText("<html> Veuillez prendre votre café svp. <br> N'oubliez pas de récupérer votre argent.");
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
	public void onMakeTeaRaised() {
		if (this.dfm.machineSurEcoute) {
			this.dfm.machineSurEcoute = false;
			this.dfm.messagesToUser.setText("<html> Votre thé est en cours de préparation ... ");
			}
	}

	@Override
	public void onMakeExpressoRaised() {
		if (this.dfm.machineSurEcoute) {
			this.dfm.machineSurEcoute = false;
			this.dfm.messagesToUser.setText("<html> Votre expresso est en cours de préparation ... ");
			}
	}

	@Override
	public void onMakeSoupRaised() {
		if (this.dfm.machineSurEcoute) {
			this.dfm.machineSurEcoute = false;
			this.dfm.messagesToUser.setText("<html> Votre soupe est en cours de préparation ... ");
			}
		
	}

	@Override
	public void onMakeIcedTeaRaised() {
		if (this.dfm.machineSurEcoute) {
			this.dfm.machineSurEcoute = false;
			this.dfm.messagesToUser.setText("<html> Votre thé glacé est en cours de préparation ... ");
			}
		
	}

}
