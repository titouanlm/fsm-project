package fr.univcotedazur.polytech.si4.fsm.project;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.swing.JSlider;

public interface Calculator {
	
	static double doubleListAverageValue(List<Double> list){
		double moyenne = 0.0;
		if (list != null) {
			for (int i=0;i<list.size()-1;i++) {
				moyenne+=list.get(i);
			}
			return (moyenne/(list.size()-1));
		}
		else return 0.0;
	}
	
	static double roundValue(double value) {
        int places = 2;

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
	
	static int getTemperatureHotBeverageSelected(JSlider temperatureSlider) {
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
	
	static int getTemperatureColdBeverageSelected(JSlider temperatureSlider) {
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
	
	static double chooseLeftOrRightPathsOverAllCoffeeSteps(DrinkFactoryMachine dfm) {
		double timePreparation = 0.0 ;
		timePreparation += chooseLeftOrRightPathOfFirstStepCoffeePreparation();
		timePreparation += chooseLeftOrRightPathOfSecondStepCoffeePreparation(dfm.temperatureSlider, dfm.theDFM.getOwnCup());
		timePreparation += chooseLeftOrRightPathOfThirdStepCoffeePreparation(dfm.sizeSlider);
		timePreparation += addOptions(dfm.sizeSlider,dfm.checkboxMilk.isSelected(), dfm.checkboxVanilla.isSelected(), dfm.checkboxCrouton.isSelected());
		return timePreparation;
	}
	
	static double chooseLeftOrRightPathOfFirstStepCoffeePreparation() {
		double timePreparationLeft = 1.5 ;
		double timePreparationRight = 2.5;
		if (timePreparationLeft > timePreparationRight) {
			return timePreparationLeft;
		}
		else {
			return timePreparationRight;
		}
	}
	

	static double chooseLeftOrRightPathOfSecondStepCoffeePreparation(JSlider temperatureSlider, boolean ownCup) {
		double timePreparationLeft = Calculator.getTemperatureHotBeverageSelected(temperatureSlider) * 0.15 ;
		double timePreparationRight = 0.0 ;
		if (!ownCup) {
			timePreparationRight+=3.0;
		}
		if (timePreparationLeft > timePreparationRight) {
			return timePreparationLeft;
		}
		else {
			return timePreparationRight;
		}
	}
	
	static double chooseLeftOrRightPathOfThirdStepCoffeePreparation(JSlider sizeSlider) {
		double timePreparationLeft = (sizeSlider.getValue()+1)*1.5 ;
		double timePreparationRight = 3.0;
		if (timePreparationLeft > timePreparationRight) {
			return timePreparationLeft;
		}
		else {
			return timePreparationRight;
		}
	}
	
	static double addOptions(JSlider sizeSlider, boolean isCheckboxMilkSelected, boolean isCheckboxVanillaSelected, boolean isCheckboxCroutonSelected) {
		double result = 0.0;
		if(isCheckboxMilkSelected) {
			result+= 2.0;	
		}
		
		if(isCheckboxVanillaSelected) {
			result += 5.0;
		}
		
		if(isCheckboxCroutonSelected) {
			result += 2.0;
		}
		return result;
	}
	
	static double chooseLeftOrRightPathsOverAllExpressoSteps(DrinkFactoryMachine dfm) {
		double timePreparation = 0.0 ;
		timePreparation += chooseLeftOrRightPathOfFirstStepExpressoPreparation();
		timePreparation += chooseLeftOrRightPathOfSecondStepExpressoPreparation(dfm.sizeSlider,dfm.temperatureSlider,dfm.theDFM.getOwnCup());
		timePreparation += chooseLeftOrRightPathOfThirdStepExpressoPreparation(dfm.sizeSlider);
		timePreparation += addOptions(dfm.sizeSlider, dfm.checkboxMilk.isSelected(), dfm.checkboxVanilla.isSelected(), dfm.checkboxCrouton.isSelected());
		return timePreparation;
	}
	
	static double chooseLeftOrRightPathOfFirstStepExpressoPreparation() {
		double timePreparationLeft = 1.5 ;
		double timePreparationRight = 4.0;
		if (timePreparationLeft > timePreparationRight) {
			return timePreparationLeft;
		}
		else {
			return timePreparationRight;
		}
	}
	
	static double chooseLeftOrRightPathOfSecondStepExpressoPreparation(JSlider sizeSlider, JSlider temperatureSlider, boolean ownCup) {
		double timePreparationLeft = Calculator.getTemperatureHotBeverageSelected(temperatureSlider) * 0.15 ;
		double timePreparationRight = 0.0 ;
		if (!ownCup) {
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
	
	static double chooseLeftOrRightPathOfThirdStepExpressoPreparation(JSlider sizeSlider) {
		double timePreparationLeft = (sizeSlider.getValue()+1)*1.5 ;
		double timePreparationRight = 3.0;
		if (timePreparationLeft > timePreparationRight) {
			return timePreparationLeft;
		}
		else {
			return timePreparationRight;
		}
	}
	
	static double chooseLeftOrRightPathsOverAllTeaSteps(DrinkFactoryMachine dfm) {
		double timePreparation = 0.0 ;
		timePreparation += chooseLeftOrRightPathOfFirstStepTeaPreparation();
		timePreparation += chooseLeftOrRightPathOfSecondStepTeaPreparation(dfm.temperatureSlider, dfm.theDFM.getOwnCup());
		timePreparation += chooseLeftOrRightPathOfThirdStepTeaPreparation(dfm.sizeSlider);
		timePreparation += timeOfFourthStepTeaPreparation();
		timePreparation += addOptions(dfm.sizeSlider,dfm.checkboxMilk.isSelected(), dfm.checkboxVanilla.isSelected(), dfm.checkboxCrouton.isSelected());
		return timePreparation;
	}
	
	static double chooseLeftOrRightPathOfFirstStepTeaPreparation() {
		double timePreparationLeft = 1.5 ;
		double timePreparationRight = 2.5;
		if (timePreparationLeft > timePreparationRight) {
			return timePreparationLeft;
		}
		else {
			return timePreparationRight;
		}
	}
	
	static double chooseLeftOrRightPathOfSecondStepTeaPreparation(JSlider temperatureSlider, boolean ownCup) {
		double timePreparationLeft = Calculator.getTemperatureHotBeverageSelected(temperatureSlider) * 0.15 ;
		double timePreparationRight = 0.0 ;
		if (!ownCup) {
			timePreparationRight+=3.0;
		}
		if (timePreparationLeft > timePreparationRight) {
			return timePreparationLeft;
		}
		else {
			return timePreparationRight;
		}
	}
	
	static double chooseLeftOrRightPathOfThirdStepTeaPreparation(JSlider sizeSlider) {
		double timePreparationLeft = (sizeSlider.getValue()+1)*1.5 ;
		double timePreparationRight = 3.0;
		if (timePreparationLeft > timePreparationRight) {
			return timePreparationLeft;
		}
		else {
			return timePreparationRight;
		}
	}
	
	static double timeOfFourthStepTeaPreparation() {
		return 5.0;			
	}
	
	static double chooseLeftOrRightPathsOverAllSoupSteps(DrinkFactoryMachine dfm) {
		double timePreparation = 0.0 ;
		timePreparation += chooseLeftOrRightPathOfFirstStepSoupPreparation(dfm.theDFM.getOwnCup());
		timePreparation += chooseLeftOrRightPathOfSecondStepSoupPreparation(dfm.temperatureSlider);
		timePreparation += chooseLeftOrRightPathOfThirdStepSoupPreparation(dfm.sizeSlider);
		timePreparation += addOptions(dfm.sizeSlider,dfm.checkboxMilk.isSelected(), dfm.checkboxVanilla.isSelected(), dfm.checkboxCrouton.isSelected());
		return timePreparation;
	}
	
	static double chooseLeftOrRightPathOfFirstStepSoupPreparation(boolean ownCup) {
		double timePreparationLeft = 1.5;
		double timePreparationRight = 0.0;
		if (!ownCup) {
			timePreparationRight+=3.0;
		}
		if (timePreparationLeft > timePreparationRight) {
			return timePreparationLeft;
		}
		else {
			return timePreparationRight;
		}
	}

	static double chooseLeftOrRightPathOfSecondStepSoupPreparation(JSlider temperatureSlider) {
		double timePreparationLeft = Calculator.getTemperatureHotBeverageSelected(temperatureSlider) * 0.15 ;
		double timePreparationRight = 2.5 + 2 ;
		if (timePreparationLeft > timePreparationRight) {
			return timePreparationLeft;
		}
		else {
			return timePreparationRight;
		}
	}
	
	static double chooseLeftOrRightPathOfThirdStepSoupPreparation(JSlider sizeSlider) {
		double timePreparationLeft = (sizeSlider.getValue()+1)*1.5 ;
		double timePreparationRight = 0.0;
		if (timePreparationLeft > timePreparationRight) {
			return timePreparationLeft;
		}
		else {
			return timePreparationRight;
		}
	}
	
	static double chooseLeftOrRightPathsOverAllIcedTeaSteps(DrinkFactoryMachine dfm) {
		double timePreparation = 0.0;
		timePreparation += chooseLeftOrRightPathOfFirstStepIcedTeaPreparation();
		timePreparation += chooseLeftOrRightPathOfSecondStepIcedTeaPreparation(dfm.temperatureSlider, dfm.theDFM.getOwnCup());
		timePreparation += chooseLeftOrRightPathOfThirdStepIcedTeaPreparation(dfm.sizeSlider);
		timePreparation += timeOfFourthStepIcedTeaPreparation();
		timePreparation += timeOfClosingDoor();
		timePreparation += timeOfLiquidNitrogenInjection(dfm.temperatureSlider);
		timePreparation += timeOfOpeningDoor();
		timePreparation += addOptions(dfm.sizeSlider,dfm.checkboxMilk.isSelected(), dfm.checkboxVanilla.isSelected(), dfm.checkboxCrouton.isSelected());
		return timePreparation;
	}
	
	static double chooseLeftOrRightPathOfFirstStepIcedTeaPreparation() {
		double timePreparationLeft = 1.5 ;
		double timePreparationRight = 2.5;
		if (timePreparationLeft > timePreparationRight) {
			return timePreparationLeft;
		}
		else {
			return timePreparationRight;
		}
	}
	
	static double chooseLeftOrRightPathOfSecondStepIcedTeaPreparation(JSlider temperatureSlider, boolean ownCup) {
		double timePreparationLeft = Calculator.getTemperatureHotBeverageSelected(temperatureSlider) * 0.15 ;
		double timePreparationRight = 0.0 ;
		if (!ownCup) {
			timePreparationRight+=3.0;
		}
		if (timePreparationLeft > timePreparationRight) {
			return timePreparationLeft;
		}
		else {
			return timePreparationRight;
		}
	}
	
	static double chooseLeftOrRightPathOfThirdStepIcedTeaPreparation(JSlider sizeSlider) {
		double timePreparationLeft = (sizeSlider.getValue()+1)*1.5 ;
		double timePreparationRight = 3.0;
		if (timePreparationLeft > timePreparationRight) {
			return timePreparationLeft;
		}
		else {
			return timePreparationRight;
		}
	}
	
	static double timeOfFourthStepIcedTeaPreparation() {
		return 5.0;			
	}
	
	static double timeOfClosingDoor() {
		return 4.0;
	}
	
	static double timeOfLiquidNitrogenInjection(JSlider temperatureSlider) {
		return ((-1/3)*Calculator.getTemperatureColdBeverageSelected(temperatureSlider))+10;
	}
	
	static double timeOfOpeningDoor() {
		return 4.0;
	}
	
	static double timePreparation(DrinkFactoryMachine dfm) { // le temps se calcul en fonction de la machine à état
		double timePreparation = 0; 
		
		switch(dfm.beverageChoice.getName()) {
		case "café":
			timePreparation += chooseLeftOrRightPathsOverAllCoffeeSteps(dfm);
			break;
		case "expresso":
			timePreparation += chooseLeftOrRightPathsOverAllExpressoSteps(dfm);
			break;
		case "thé":
			timePreparation += chooseLeftOrRightPathsOverAllTeaSteps(dfm);
			break;
		case "soupe":
			timePreparation += chooseLeftOrRightPathsOverAllSoupSteps(dfm);
			break;
		case "thé glacé":
			timePreparation += chooseLeftOrRightPathsOverAllIcedTeaSteps(dfm);
		}
		
		return timePreparation;
	}

}
