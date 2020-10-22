package fr.univcotedazur.polytech.si4.fsm.project;

public abstract class Beverage {
	protected double price;
	protected String name;
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
