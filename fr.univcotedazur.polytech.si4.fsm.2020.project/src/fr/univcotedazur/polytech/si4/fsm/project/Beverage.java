package fr.univcotedazur.polytech.si4.fsm.project;

public enum Beverage {
	
	COFFEE(0.35,"café"), EXPRESSO(0.50,"expresso"), TEA(0.4,"thé"), SOUP(0.75,"soupe"), ICED_TEA(0.75,"thé glacé");
	
	protected double price;
	protected String name;
	
	private Beverage(double price, String name){
        this.price = price;
        this.name=name;
    }
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	
}