package fr.univcotedazur.polytech.si4.fsm.project;

public class Supply {
	
	private int coffeeDose;
	private double kgOfGrain;
	private int teaBag;
	private int sugarDose;
	private int soupDose;
	private int spicyDose;
	private int croutonDose;
	private int vanillaDose;
	private int milkDose;
	private int mapleSyrupDose;
	private int nitrogenDose;
	private int goblet;
	

	public Supply() {
		this.coffeeDose = 15;
		this.kgOfGrain = 0.74;
		this.teaBag = 5;
		this.soupDose = 2;
		this.sugarDose = 50;
		this.spicyDose = 50;
		this.croutonDose = 5;
		this.vanillaDose = 1;
		this.milkDose = 5;
		this.mapleSyrupDose = 5;
		this.nitrogenDose = 1;
		this.goblet =15;
	}


	public boolean enoughCoffeeDose() {
		return this.coffeeDose > 0;
	}

	public void consumeCoffeeDose() {
		if(this.coffeeDose > 0) {
			this.coffeeDose -= 1;
		}	
	}


	public boolean enoughGrain() {
		return this.kgOfGrain >= 0.15;
	}


	public void consumeGrain(int beverageHeight) {
		if(this.kgOfGrain >= (0.05*beverageHeight)) {
			this.kgOfGrain = Calculator.roundValue(this.kgOfGrain - (0.05*beverageHeight));
		}
	}


	public boolean enoughTeaBag() {
		return this.teaBag > 0;
	}


	public void consumeTeaBag() {
		if(this.teaBag > 0) {
			this.teaBag -=1 ; 
		}
	}


	public boolean enoughSugarDose() {
		return this.sugarDose >= 5;
	}


	public void consumeSugarDoses(int sugarDoses) {
		if(this.sugarDose >= sugarDoses) {
			this.sugarDose -= sugarDoses;
		}
	}


	public boolean enoughSoupDose() {
		return this.soupDose > 0;
	}


	public void consumeSoupDose() {
		if(this.soupDose > 0) {
			this.soupDose -= 1;
		}
	}


	public boolean enoughCroutonDose() {
		return this.croutonDose > 0;	}


	public void consumeCroutonDose() {
		if(this.croutonDose > 0) {
			this.croutonDose -= 1;
		}
	}


	public boolean enoughVanillaDose() {
		return this.vanillaDose > 0;	}


	public void consumeVanillaDose() {
		if(this.vanillaDose > 0) {
			this.vanillaDose -= 1;
		}
	}


	public boolean enoughMilkDose() {
		return this.milkDose > 0;	
	}


	public void consumeMilkDose() {
		if(this.milkDose > 0) {
			this.milkDose -= 1;
		}
	}


	public boolean enoughMapleSyrupDose() {
		return this.mapleSyrupDose > 0;	
	}


	public void consumeMapleSyrupDose() {
		if(this.mapleSyrupDose > 0) {
			this.mapleSyrupDose -= 1;
		}
	}


	public boolean enoughGoblet() {
		return this.goblet > 0;	
	}


	public void consumeGoblet() {
		if(this.goblet > 0) {
			this.goblet -= 1;
		}
	}


	public boolean enoughSpicyDose() {
		return this.spicyDose >= 5;	
	}


	public void consumeSpicyDose(int spicyDose) {
		if(this.spicyDose >= spicyDose) {
			this.spicyDose -= spicyDose;
		}
	}
	
	
	public boolean enoughNitrogenDose() {
		return this.nitrogenDose > 0;	
	}


	public void consumeNitrogenDose() {
		if(this.nitrogenDose >= 0) {
			this.nitrogenDose -= 1;
		}
	}

	@Override
	public String toString() {
		return "<html>Stock <br><br> Coffee Dose = " + coffeeDose + "<br> KG Of Grain = " + kgOfGrain + "<br> Tea Bag = " + teaBag + "<br> Sugar Dose = "
				+ sugarDose + "<br> Soup Dose = " + soupDose + "<br> Spicy Dose = " + spicyDose + "<br> Crouton Dose = " + croutonDose
				+ "<br> Vanilla Dose = " + vanillaDose + "<br> Milk Dose = " + milkDose + "<br> Maple Syrup Dose = " + mapleSyrupDose
				+ "<br> Nitrogen Dose = " + nitrogenDose + "<br> Number of Goblet = " + goblet;
	}
	
}
