/** Generated by YAKINDU Statechart Tools code generator. */
package fr.univcotedazur.polytech.si4.fsm.project.defaultsm;

import fr.univcotedazur.polytech.si4.fsm.project.IStatemachine;
import fr.univcotedazur.polytech.si4.fsm.project.ITimerCallback;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public interface IDefaultSMStatemachine extends ITimerCallback,IStatemachine {
	public interface SCInterface {
	
		public void raiseMoney50centsButton();
		
		public void raiseMoney25centsButton();
		
		public void raiseMoney10centsButton();
		
		public void raiseCoffeeButton();
		
		public void raiseTeaButton();
		
		public void raiseExpressoButton();
		
		public void raiseSoupButton();
		
		public void raiseBipButton();
		
		public void raiseCancelButton();
		
		public void raiseIcedTeaButton();
		
		public boolean isRaisedUpdateSolde();
		
		public boolean isRaisedTakeBeverage();
		
		public boolean isRaisedCleaningMachine();
		
		public boolean isRaisedMachineReady();
		
		public boolean isRaisedBeveragePreparation();
		
		public boolean isRaisedBeverageChoice();
		
		public boolean isRaisedCancelPreparation();
		
		public boolean isRaisedTimePreparation();
		
		public boolean isRaisedPayByNFC();
		
		public boolean isRaisedCancelTransaction();
		
		public boolean isRaisedResetSliders();
		
		public boolean isRaisedValidatePayment();
		
		public double getSolde();
		
		public void setSolde(double value);
		
		public boolean getPaymentCard();
		
		public void setPaymentCard(boolean value);
		
		public boolean getPaymentDone();
		
		public void setPaymentDone(boolean value);
		
		public boolean getEnoughMoney();
		
		public void setEnoughMoney(boolean value);
		
	public List<SCInterfaceListener> getListeners();
	}
	
	public interface SCInterfaceListener {
	
		public void onUpdateSoldeRaised();
		public void onTakeBeverageRaised();
		public void onCleaningMachineRaised();
		public void onMachineReadyRaised();
		public void onBeveragePreparationRaised();
		public void onBeverageChoiceRaised();
		public void onCancelPreparationRaised();
		public void onTimePreparationRaised();
		public void onPayByNFCRaised();
		public void onCancelTransactionRaised();
		public void onResetSlidersRaised();
		public void onValidatePaymentRaised();
		}
	
	public SCInterface getSCInterface();
	
}
