package fr.univcotedazur.polytech.si4.fsm.project;

import fr.univcotedazur.polytech.si4.fsm.project.defaultsm.IDefaultSMStatemachine.SCInterfaceListener;

public class DrinkFactoryMachineInterfaceImplementation implements SCInterfaceListener {

	DrinkFactoryMachine dfm;
	
	public DrinkFactoryMachineInterfaceImplementation(DrinkFactoryMachine dfm) {
		this.dfm = dfm;
	}
	
	@Override
	public void onUpdateSoldeRaised() {
		this.dfm.messagesToUser.setText("Solde : " + this.dfm.solde + "€" );
	}

	@Override
	public void onResetSoldeRaised() {
		this.dfm.solde = 0.0;		
		this.dfm.messagesToUser.setText("<html> Vous n'avez rien choisi. <br> Récupérez votre monnaie. <br>  Solde : " + this.dfm.solde + "€" );
	}

}
