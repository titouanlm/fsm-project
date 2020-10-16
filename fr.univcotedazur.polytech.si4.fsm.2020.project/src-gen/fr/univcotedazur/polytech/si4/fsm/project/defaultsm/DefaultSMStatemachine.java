/** Generated by YAKINDU Statechart Tools code generator. */
package fr.univcotedazur.polytech.si4.fsm.project.defaultsm;

import fr.univcotedazur.polytech.si4.fsm.project.ITimer;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DefaultSMStatemachine implements IDefaultSMStatemachine {
	protected class SCInterfaceImpl implements SCInterface {
	
		private List<SCInterfaceListener> listeners = new LinkedList<SCInterfaceListener>();
		
		public List<SCInterfaceListener> getListeners() {
			return listeners;
		}
		private boolean money50centsButton;
		
		
		public void raiseMoney50centsButton() {
			synchronized(DefaultSMStatemachine.this) {
				inEventQueue.add(
					new Runnable() {
						@Override
						public void run() {
							money50centsButton = true;
							singleCycle();
						}
					}
				);
				runCycle();
			}
		}
		
		private boolean money25centsButton;
		
		
		public void raiseMoney25centsButton() {
			synchronized(DefaultSMStatemachine.this) {
				inEventQueue.add(
					new Runnable() {
						@Override
						public void run() {
							money25centsButton = true;
							singleCycle();
						}
					}
				);
				runCycle();
			}
		}
		
		private boolean money10centsButton;
		
		
		public void raiseMoney10centsButton() {
			synchronized(DefaultSMStatemachine.this) {
				inEventQueue.add(
					new Runnable() {
						@Override
						public void run() {
							money10centsButton = true;
							singleCycle();
						}
					}
				);
				runCycle();
			}
		}
		
		private boolean coffeeButton;
		
		
		public void raiseCoffeeButton() {
			synchronized(DefaultSMStatemachine.this) {
				inEventQueue.add(
					new Runnable() {
						@Override
						public void run() {
							coffeeButton = true;
							singleCycle();
						}
					}
				);
				runCycle();
			}
		}
		
		private boolean teaButton;
		
		
		public void raiseTeaButton() {
			synchronized(DefaultSMStatemachine.this) {
				inEventQueue.add(
					new Runnable() {
						@Override
						public void run() {
							teaButton = true;
							singleCycle();
						}
					}
				);
				runCycle();
			}
		}
		
		private boolean expressoButton;
		
		
		public void raiseExpressoButton() {
			synchronized(DefaultSMStatemachine.this) {
				inEventQueue.add(
					new Runnable() {
						@Override
						public void run() {
							expressoButton = true;
							singleCycle();
						}
					}
				);
				runCycle();
			}
		}
		
		private boolean soupButton;
		
		
		public void raiseSoupButton() {
			synchronized(DefaultSMStatemachine.this) {
				inEventQueue.add(
					new Runnable() {
						@Override
						public void run() {
							soupButton = true;
							singleCycle();
						}
					}
				);
				runCycle();
			}
		}
		
		private boolean bipButton;
		
		
		public void raiseBipButton() {
			synchronized(DefaultSMStatemachine.this) {
				inEventQueue.add(
					new Runnable() {
						@Override
						public void run() {
							bipButton = true;
							singleCycle();
						}
					}
				);
				runCycle();
			}
		}
		
		private boolean cancelButton;
		
		
		public void raiseCancelButton() {
			synchronized(DefaultSMStatemachine.this) {
				inEventQueue.add(
					new Runnable() {
						@Override
						public void run() {
							cancelButton = true;
							singleCycle();
						}
					}
				);
				runCycle();
			}
		}
		
		private boolean icedTeaButton;
		
		
		public void raiseIcedTeaButton() {
			synchronized(DefaultSMStatemachine.this) {
				inEventQueue.add(
					new Runnable() {
						@Override
						public void run() {
							icedTeaButton = true;
							singleCycle();
						}
					}
				);
				runCycle();
			}
		}
		
		private boolean updateSolde;
		
		
		public boolean isRaisedUpdateSolde() {
			synchronized(DefaultSMStatemachine.this) {
				return updateSolde;
			}
		}
		
		protected void raiseUpdateSolde() {
			synchronized(DefaultSMStatemachine.this) {
				updateSolde = true;
				for (SCInterfaceListener listener : listeners) {
					listener.onUpdateSoldeRaised();
				}
			}
		}
		
		private boolean takeBeverage;
		
		
		public boolean isRaisedTakeBeverage() {
			synchronized(DefaultSMStatemachine.this) {
				return takeBeverage;
			}
		}
		
		protected void raiseTakeBeverage() {
			synchronized(DefaultSMStatemachine.this) {
				takeBeverage = true;
				for (SCInterfaceListener listener : listeners) {
					listener.onTakeBeverageRaised();
				}
			}
		}
		
		private boolean cleaningMachine;
		
		
		public boolean isRaisedCleaningMachine() {
			synchronized(DefaultSMStatemachine.this) {
				return cleaningMachine;
			}
		}
		
		protected void raiseCleaningMachine() {
			synchronized(DefaultSMStatemachine.this) {
				cleaningMachine = true;
				for (SCInterfaceListener listener : listeners) {
					listener.onCleaningMachineRaised();
				}
			}
		}
		
		private boolean machineReady;
		
		
		public boolean isRaisedMachineReady() {
			synchronized(DefaultSMStatemachine.this) {
				return machineReady;
			}
		}
		
		protected void raiseMachineReady() {
			synchronized(DefaultSMStatemachine.this) {
				machineReady = true;
				for (SCInterfaceListener listener : listeners) {
					listener.onMachineReadyRaised();
				}
			}
		}
		
		private boolean beveragePreparation;
		
		
		public boolean isRaisedBeveragePreparation() {
			synchronized(DefaultSMStatemachine.this) {
				return beveragePreparation;
			}
		}
		
		protected void raiseBeveragePreparation() {
			synchronized(DefaultSMStatemachine.this) {
				beveragePreparation = true;
				for (SCInterfaceListener listener : listeners) {
					listener.onBeveragePreparationRaised();
				}
			}
		}
		
		private boolean beverageChoice;
		
		
		public boolean isRaisedBeverageChoice() {
			synchronized(DefaultSMStatemachine.this) {
				return beverageChoice;
			}
		}
		
		protected void raiseBeverageChoice() {
			synchronized(DefaultSMStatemachine.this) {
				beverageChoice = true;
				for (SCInterfaceListener listener : listeners) {
					listener.onBeverageChoiceRaised();
				}
			}
		}
		
		private boolean cancelPreparation;
		
		
		public boolean isRaisedCancelPreparation() {
			synchronized(DefaultSMStatemachine.this) {
				return cancelPreparation;
			}
		}
		
		protected void raiseCancelPreparation() {
			synchronized(DefaultSMStatemachine.this) {
				cancelPreparation = true;
				for (SCInterfaceListener listener : listeners) {
					listener.onCancelPreparationRaised();
				}
			}
		}
		
		private boolean timePreparation;
		
		
		public boolean isRaisedTimePreparation() {
			synchronized(DefaultSMStatemachine.this) {
				return timePreparation;
			}
		}
		
		protected void raiseTimePreparation() {
			synchronized(DefaultSMStatemachine.this) {
				timePreparation = true;
				for (SCInterfaceListener listener : listeners) {
					listener.onTimePreparationRaised();
				}
			}
		}
		
		private boolean payByNFC;
		
		
		public boolean isRaisedPayByNFC() {
			synchronized(DefaultSMStatemachine.this) {
				return payByNFC;
			}
		}
		
		protected void raisePayByNFC() {
			synchronized(DefaultSMStatemachine.this) {
				payByNFC = true;
				for (SCInterfaceListener listener : listeners) {
					listener.onPayByNFCRaised();
				}
			}
		}
		
		private boolean cancelTransaction;
		
		
		public boolean isRaisedCancelTransaction() {
			synchronized(DefaultSMStatemachine.this) {
				return cancelTransaction;
			}
		}
		
		protected void raiseCancelTransaction() {
			synchronized(DefaultSMStatemachine.this) {
				cancelTransaction = true;
				for (SCInterfaceListener listener : listeners) {
					listener.onCancelTransactionRaised();
				}
			}
		}
		
		private boolean resetSliders;
		
		
		public boolean isRaisedResetSliders() {
			synchronized(DefaultSMStatemachine.this) {
				return resetSliders;
			}
		}
		
		protected void raiseResetSliders() {
			synchronized(DefaultSMStatemachine.this) {
				resetSliders = true;
				for (SCInterfaceListener listener : listeners) {
					listener.onResetSlidersRaised();
				}
			}
		}
		
		private boolean validatePayment;
		
		
		public boolean isRaisedValidatePayment() {
			synchronized(DefaultSMStatemachine.this) {
				return validatePayment;
			}
		}
		
		protected void raiseValidatePayment() {
			synchronized(DefaultSMStatemachine.this) {
				validatePayment = true;
				for (SCInterfaceListener listener : listeners) {
					listener.onValidatePaymentRaised();
				}
			}
		}
		
		private double solde;
		
		public synchronized double getSolde() {
			synchronized(DefaultSMStatemachine.this) {
				return solde;
			}
		}
		
		public void setSolde(double value) {
			synchronized(DefaultSMStatemachine.this) {
				this.solde = value;
			}
		}
		
		private boolean paymentCard;
		
		public synchronized boolean getPaymentCard() {
			synchronized(DefaultSMStatemachine.this) {
				return paymentCard;
			}
		}
		
		public void setPaymentCard(boolean value) {
			synchronized(DefaultSMStatemachine.this) {
				this.paymentCard = value;
			}
		}
		
		private boolean paymentDone;
		
		public synchronized boolean getPaymentDone() {
			synchronized(DefaultSMStatemachine.this) {
				return paymentDone;
			}
		}
		
		public void setPaymentDone(boolean value) {
			synchronized(DefaultSMStatemachine.this) {
				this.paymentDone = value;
			}
		}
		
		private boolean enoughMoney;
		
		public synchronized boolean getEnoughMoney() {
			synchronized(DefaultSMStatemachine.this) {
				return enoughMoney;
			}
		}
		
		public void setEnoughMoney(boolean value) {
			synchronized(DefaultSMStatemachine.this) {
				this.enoughMoney = value;
			}
		}
		
		protected void clearEvents() {
			money50centsButton = false;
			money25centsButton = false;
			money10centsButton = false;
			coffeeButton = false;
			teaButton = false;
			expressoButton = false;
			soupButton = false;
			bipButton = false;
			cancelButton = false;
			icedTeaButton = false;
		}
		protected void clearOutEvents() {
		
		updateSolde = false;
		takeBeverage = false;
		cleaningMachine = false;
		machineReady = false;
		beveragePreparation = false;
		beverageChoice = false;
		cancelPreparation = false;
		timePreparation = false;
		payByNFC = false;
		cancelTransaction = false;
		resetSliders = false;
		validatePayment = false;
		}
		
	}
	
	
	protected SCInterfaceImpl sCInterface;
	
	private boolean initialized = false;
	
	public enum State {
		main_region_Waiting,
		main_region_Take_beverage,
		main_region_Cleaning,
		main_region_Payment_Done,
		main_region_Beverage_Preparation,
		main_region_Beverage_Choice,
		paymentByCoins_PaymentByCoins,
		paymentByCoins_WaitingCoins,
		paymentByCoins_ReturnCoins,
		paymentByNFC_WaitingNFC,
		paymentByNFC_PaymentByNFC,
		paymentByNFC_CancelTransaction,
		$NullState$
	};
	
	private final State[] stateVector = new State[3];
	
	private int nextStateIndex;
	
	private ITimer timer;
	
	private final boolean[] timeEvents = new boolean[12];
	
	private BlockingQueue<Runnable> inEventQueue = new LinkedBlockingQueue<Runnable>();
	private boolean isRunningCycle = false;
	public DefaultSMStatemachine() {
		sCInterface = new SCInterfaceImpl();
	}
	
	public synchronized void init() {
		this.initialized = true;
		if (timer == null) {
			throw new IllegalStateException("timer not set.");
		}
		for (int i = 0; i < 3; i++) {
			stateVector[i] = State.$NullState$;
		}
		clearEvents();
		clearOutEvents();
		sCInterface.setSolde(0.0);
		
		sCInterface.setPaymentCard(false);
		
		sCInterface.setPaymentDone(false);
		
		sCInterface.setEnoughMoney(false);
	}
	
	public synchronized void enter() {
		if (!initialized) {
			throw new IllegalStateException(
				"The state machine needs to be initialized first by calling the init() function."
			);
		}
		if (timer == null) {
			throw new IllegalStateException("timer not set.");
		}
		enterSequence_main_region_default();
		enterSequence_PaymentByCoins_default();
		enterSequence_PaymentByNFC_default();
	}
	
	public synchronized void runCycle() {
		if (!initialized)
			throw new IllegalStateException(
					"The state machine needs to be initialized first by calling the init() function.");
		
		if (isRunningCycle) {
			return;
		}
		isRunningCycle = true;
		
		clearOutEvents();
	
		Runnable task = getNextEvent();
		if (task == null) {
			task = getDefaultEvent();
		}
		
		while (task != null) {
			task.run();
			clearEvents();
			task = getNextEvent();
		}
		
		isRunningCycle = false;
	}
	
	protected synchronized void singleCycle() {
		for (nextStateIndex = 0; nextStateIndex < stateVector.length; nextStateIndex++) {
			switch (stateVector[nextStateIndex]) {
				case main_region_Waiting:
					main_region_Waiting_react(true);
					break;
				case main_region_Take_beverage:
					main_region_Take_beverage_react(true);
					break;
				case main_region_Cleaning:
					main_region_Cleaning_react(true);
					break;
				case main_region_Payment_Done:
					main_region_Payment_Done_react(true);
					break;
				case main_region_Beverage_Preparation:
					main_region_Beverage_Preparation_react(true);
					break;
				case main_region_Beverage_Choice:
					main_region_Beverage_Choice_react(true);
					break;
				case paymentByCoins_PaymentByCoins:
					paymentByCoins_PaymentByCoins_react(true);
					break;
				case paymentByCoins_WaitingCoins:
					paymentByCoins_WaitingCoins_react(true);
					break;
				case paymentByCoins_ReturnCoins:
					paymentByCoins_ReturnCoins_react(true);
					break;
				case paymentByNFC_WaitingNFC:
					paymentByNFC_WaitingNFC_react(true);
					break;
				case paymentByNFC_PaymentByNFC:
					paymentByNFC_PaymentByNFC_react(true);
					break;
				case paymentByNFC_CancelTransaction:
					paymentByNFC_CancelTransaction_react(true);
					break;
			default:
				// $NullState$
			}
		}
	}
	
	protected Runnable getNextEvent() {
		if(!inEventQueue.isEmpty()) {
			return inEventQueue.poll();
		}
		return null;
	}
	
	protected Runnable getDefaultEvent() {
		return new Runnable() {
			@Override
			public void run() {
				singleCycle();
			}
		};
	}
	
	public synchronized void exit() {
		exitSequence_main_region();
		exitSequence_PaymentByCoins();
		exitSequence_PaymentByNFC();
	}
	
	/**
	 * @see IStatemachine#isActive()
	 */
	public synchronized boolean isActive() {
		return stateVector[0] != State.$NullState$||stateVector[1] != State.$NullState$||stateVector[2] != State.$NullState$;
	}
	
	/** 
	* Always returns 'false' since this state machine can never become final.
	*
	* @see IStatemachine#isFinal()
	*/
	public synchronized boolean isFinal() {
		return false;
	}
	/**
	* This method resets the incoming events (time events included).
	*/
	protected void clearEvents() {
		sCInterface.clearEvents();
		for (int i=0; i<timeEvents.length; i++) {
			timeEvents[i] = false;
		}
	}
	
	/**
	* This method resets the outgoing events.
	*/
	protected void clearOutEvents() {
		sCInterface.clearOutEvents();
	}
	
	/**
	* Returns true if the given state is currently active otherwise false.
	*/
	public synchronized boolean isStateActive(State state) {
	
		switch (state) {
		case main_region_Waiting:
			return stateVector[0] == State.main_region_Waiting;
		case main_region_Take_beverage:
			return stateVector[0] == State.main_region_Take_beverage;
		case main_region_Cleaning:
			return stateVector[0] == State.main_region_Cleaning;
		case main_region_Payment_Done:
			return stateVector[0] == State.main_region_Payment_Done;
		case main_region_Beverage_Preparation:
			return stateVector[0] == State.main_region_Beverage_Preparation;
		case main_region_Beverage_Choice:
			return stateVector[0] == State.main_region_Beverage_Choice;
		case paymentByCoins_PaymentByCoins:
			return stateVector[1] == State.paymentByCoins_PaymentByCoins;
		case paymentByCoins_WaitingCoins:
			return stateVector[1] == State.paymentByCoins_WaitingCoins;
		case paymentByCoins_ReturnCoins:
			return stateVector[1] == State.paymentByCoins_ReturnCoins;
		case paymentByNFC_WaitingNFC:
			return stateVector[2] == State.paymentByNFC_WaitingNFC;
		case paymentByNFC_PaymentByNFC:
			return stateVector[2] == State.paymentByNFC_PaymentByNFC;
		case paymentByNFC_CancelTransaction:
			return stateVector[2] == State.paymentByNFC_CancelTransaction;
		default:
			return false;
		}
	}
	
	/**
	* Set the {@link ITimer} for the state machine. It must be set
	* externally on a timed state machine before a run cycle can be correctly
	* executed.
	* 
	* @param timer
	*/
	public synchronized void setTimer(ITimer timer) {
		this.timer = timer;
	}
	
	/**
	* Returns the currently used timer.
	* 
	* @return {@link ITimer}
	*/
	public ITimer getTimer() {
		return timer;
	}
	
	public synchronized void timeElapsed(int eventID) {
		inEventQueue.add(new Runnable() {
			@Override
			public void run() {
				timeEvents[eventID] = true;
				singleCycle();
			}
		});
		runCycle();
	}
	
	public SCInterface getSCInterface() {
		return sCInterface;
	}
	
	public synchronized void raiseMoney50centsButton() {
		sCInterface.raiseMoney50centsButton();
	}
	
	public synchronized void raiseMoney25centsButton() {
		sCInterface.raiseMoney25centsButton();
	}
	
	public synchronized void raiseMoney10centsButton() {
		sCInterface.raiseMoney10centsButton();
	}
	
	public synchronized void raiseCoffeeButton() {
		sCInterface.raiseCoffeeButton();
	}
	
	public synchronized void raiseTeaButton() {
		sCInterface.raiseTeaButton();
	}
	
	public synchronized void raiseExpressoButton() {
		sCInterface.raiseExpressoButton();
	}
	
	public synchronized void raiseSoupButton() {
		sCInterface.raiseSoupButton();
	}
	
	public synchronized void raiseBipButton() {
		sCInterface.raiseBipButton();
	}
	
	public synchronized void raiseCancelButton() {
		sCInterface.raiseCancelButton();
	}
	
	public synchronized void raiseIcedTeaButton() {
		sCInterface.raiseIcedTeaButton();
	}
	
	public synchronized boolean isRaisedUpdateSolde() {
		return sCInterface.isRaisedUpdateSolde();
	}
	
	public synchronized boolean isRaisedTakeBeverage() {
		return sCInterface.isRaisedTakeBeverage();
	}
	
	public synchronized boolean isRaisedCleaningMachine() {
		return sCInterface.isRaisedCleaningMachine();
	}
	
	public synchronized boolean isRaisedMachineReady() {
		return sCInterface.isRaisedMachineReady();
	}
	
	public synchronized boolean isRaisedBeveragePreparation() {
		return sCInterface.isRaisedBeveragePreparation();
	}
	
	public synchronized boolean isRaisedBeverageChoice() {
		return sCInterface.isRaisedBeverageChoice();
	}
	
	public synchronized boolean isRaisedCancelPreparation() {
		return sCInterface.isRaisedCancelPreparation();
	}
	
	public synchronized boolean isRaisedTimePreparation() {
		return sCInterface.isRaisedTimePreparation();
	}
	
	public synchronized boolean isRaisedPayByNFC() {
		return sCInterface.isRaisedPayByNFC();
	}
	
	public synchronized boolean isRaisedCancelTransaction() {
		return sCInterface.isRaisedCancelTransaction();
	}
	
	public synchronized boolean isRaisedResetSliders() {
		return sCInterface.isRaisedResetSliders();
	}
	
	public synchronized boolean isRaisedValidatePayment() {
		return sCInterface.isRaisedValidatePayment();
	}
	
	public synchronized double getSolde() {
		return sCInterface.getSolde();
	}
	
	public synchronized void setSolde(double value) {
		sCInterface.setSolde(value);
	}
	
	public synchronized boolean getPaymentCard() {
		return sCInterface.getPaymentCard();
	}
	
	public synchronized void setPaymentCard(boolean value) {
		sCInterface.setPaymentCard(value);
	}
	
	public synchronized boolean getPaymentDone() {
		return sCInterface.getPaymentDone();
	}
	
	public synchronized void setPaymentDone(boolean value) {
		sCInterface.setPaymentDone(value);
	}
	
	public synchronized boolean getEnoughMoney() {
		return sCInterface.getEnoughMoney();
	}
	
	public synchronized void setEnoughMoney(boolean value) {
		sCInterface.setEnoughMoney(value);
	}
	
	/* Entry action for state 'Waiting'. */
	private void entryAction_main_region_Waiting() {
		sCInterface.raiseMachineReady();
	}
	
	/* Entry action for state 'Take beverage'. */
	private void entryAction_main_region_Take_beverage() {
		timer.setTimer(this, 0, 5000, false);
	}
	
	/* Entry action for state 'Cleaning'. */
	private void entryAction_main_region_Cleaning() {
		timer.setTimer(this, 1, 5000, false);
	}
	
	/* Entry action for state 'Payment Done'. */
	private void entryAction_main_region_Payment_Done() {
		timer.setTimer(this, 2, 1, false);
		
		sCInterface.raiseValidatePayment();
	}
	
	/* Entry action for state 'Beverage Preparation'. */
	private void entryAction_main_region_Beverage_Preparation() {
		timer.setTimer(this, 3, 1000, false);
	}
	
	/* Entry action for state 'Beverage Choice'. */
	private void entryAction_main_region_Beverage_Choice() {
		timer.setTimer(this, 4, 1, true);
		
		timer.setTimer(this, 5, (45 * 1000), false);
	}
	
	/* Entry action for state 'PaymentByCoins'. */
	private void entryAction_PaymentByCoins_PaymentByCoins() {
		timer.setTimer(this, 6, (5 * 1000), false);
		
		timer.setTimer(this, 7, 1, true);
	}
	
	/* Entry action for state 'ReturnCoins'. */
	private void entryAction_PaymentByCoins_ReturnCoins() {
		timer.setTimer(this, 8, (5 * 1000), false);
	}
	
	/* Entry action for state 'PaymentByNFC'. */
	private void entryAction_PaymentByNFC_PaymentByNFC() {
		timer.setTimer(this, 9, (5 * 1000), false);
		
		timer.setTimer(this, 10, 1, true);
	}
	
	/* Entry action for state 'CancelTransaction'. */
	private void entryAction_PaymentByNFC_CancelTransaction() {
		timer.setTimer(this, 11, (5 * 1000), false);
	}
	
	/* Exit action for state 'Take beverage'. */
	private void exitAction_main_region_Take_beverage() {
		timer.unsetTimer(this, 0);
	}
	
	/* Exit action for state 'Cleaning'. */
	private void exitAction_main_region_Cleaning() {
		timer.unsetTimer(this, 1);
	}
	
	/* Exit action for state 'Payment Done'. */
	private void exitAction_main_region_Payment_Done() {
		timer.unsetTimer(this, 2);
	}
	
	/* Exit action for state 'Beverage Preparation'. */
	private void exitAction_main_region_Beverage_Preparation() {
		timer.unsetTimer(this, 3);
	}
	
	/* Exit action for state 'Beverage Choice'. */
	private void exitAction_main_region_Beverage_Choice() {
		timer.unsetTimer(this, 4);
		
		timer.unsetTimer(this, 5);
	}
	
	/* Exit action for state 'PaymentByCoins'. */
	private void exitAction_PaymentByCoins_PaymentByCoins() {
		timer.unsetTimer(this, 6);
		
		timer.unsetTimer(this, 7);
	}
	
	/* Exit action for state 'ReturnCoins'. */
	private void exitAction_PaymentByCoins_ReturnCoins() {
		timer.unsetTimer(this, 8);
	}
	
	/* Exit action for state 'PaymentByNFC'. */
	private void exitAction_PaymentByNFC_PaymentByNFC() {
		timer.unsetTimer(this, 9);
		
		timer.unsetTimer(this, 10);
	}
	
	/* Exit action for state 'CancelTransaction'. */
	private void exitAction_PaymentByNFC_CancelTransaction() {
		timer.unsetTimer(this, 11);
	}
	
	/* 'default' enter sequence for state Waiting */
	private void enterSequence_main_region_Waiting_default() {
		entryAction_main_region_Waiting();
		nextStateIndex = 0;
		stateVector[0] = State.main_region_Waiting;
	}
	
	/* 'default' enter sequence for state Take beverage */
	private void enterSequence_main_region_Take_beverage_default() {
		entryAction_main_region_Take_beverage();
		nextStateIndex = 0;
		stateVector[0] = State.main_region_Take_beverage;
	}
	
	/* 'default' enter sequence for state Cleaning */
	private void enterSequence_main_region_Cleaning_default() {
		entryAction_main_region_Cleaning();
		nextStateIndex = 0;
		stateVector[0] = State.main_region_Cleaning;
	}
	
	/* 'default' enter sequence for state Payment Done */
	private void enterSequence_main_region_Payment_Done_default() {
		entryAction_main_region_Payment_Done();
		nextStateIndex = 0;
		stateVector[0] = State.main_region_Payment_Done;
	}
	
	/* 'default' enter sequence for state Beverage Preparation */
	private void enterSequence_main_region_Beverage_Preparation_default() {
		entryAction_main_region_Beverage_Preparation();
		nextStateIndex = 0;
		stateVector[0] = State.main_region_Beverage_Preparation;
	}
	
	/* 'default' enter sequence for state Beverage Choice */
	private void enterSequence_main_region_Beverage_Choice_default() {
		entryAction_main_region_Beverage_Choice();
		nextStateIndex = 0;
		stateVector[0] = State.main_region_Beverage_Choice;
	}
	
	/* 'default' enter sequence for state PaymentByCoins */
	private void enterSequence_PaymentByCoins_PaymentByCoins_default() {
		entryAction_PaymentByCoins_PaymentByCoins();
		nextStateIndex = 1;
		stateVector[1] = State.paymentByCoins_PaymentByCoins;
	}
	
	/* 'default' enter sequence for state WaitingCoins */
	private void enterSequence_PaymentByCoins_WaitingCoins_default() {
		nextStateIndex = 1;
		stateVector[1] = State.paymentByCoins_WaitingCoins;
	}
	
	/* 'default' enter sequence for state ReturnCoins */
	private void enterSequence_PaymentByCoins_ReturnCoins_default() {
		entryAction_PaymentByCoins_ReturnCoins();
		nextStateIndex = 1;
		stateVector[1] = State.paymentByCoins_ReturnCoins;
	}
	
	/* 'default' enter sequence for state WaitingNFC */
	private void enterSequence_PaymentByNFC_WaitingNFC_default() {
		nextStateIndex = 2;
		stateVector[2] = State.paymentByNFC_WaitingNFC;
	}
	
	/* 'default' enter sequence for state PaymentByNFC */
	private void enterSequence_PaymentByNFC_PaymentByNFC_default() {
		entryAction_PaymentByNFC_PaymentByNFC();
		nextStateIndex = 2;
		stateVector[2] = State.paymentByNFC_PaymentByNFC;
	}
	
	/* 'default' enter sequence for state CancelTransaction */
	private void enterSequence_PaymentByNFC_CancelTransaction_default() {
		entryAction_PaymentByNFC_CancelTransaction();
		nextStateIndex = 2;
		stateVector[2] = State.paymentByNFC_CancelTransaction;
	}
	
	/* 'default' enter sequence for region main region */
	private void enterSequence_main_region_default() {
		react_main_region__entry_Default();
	}
	
	/* 'default' enter sequence for region PaymentByCoins */
	private void enterSequence_PaymentByCoins_default() {
		react_PaymentByCoins__entry_Default();
	}
	
	/* 'default' enter sequence for region PaymentByNFC */
	private void enterSequence_PaymentByNFC_default() {
		react_PaymentByNFC__entry_Default();
	}
	
	/* Default exit sequence for state Waiting */
	private void exitSequence_main_region_Waiting() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state Take beverage */
	private void exitSequence_main_region_Take_beverage() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_main_region_Take_beverage();
	}
	
	/* Default exit sequence for state Cleaning */
	private void exitSequence_main_region_Cleaning() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_main_region_Cleaning();
	}
	
	/* Default exit sequence for state Payment Done */
	private void exitSequence_main_region_Payment_Done() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_main_region_Payment_Done();
	}
	
	/* Default exit sequence for state Beverage Preparation */
	private void exitSequence_main_region_Beverage_Preparation() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_main_region_Beverage_Preparation();
	}
	
	/* Default exit sequence for state Beverage Choice */
	private void exitSequence_main_region_Beverage_Choice() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_main_region_Beverage_Choice();
	}
	
	/* Default exit sequence for state PaymentByCoins */
	private void exitSequence_PaymentByCoins_PaymentByCoins() {
		nextStateIndex = 1;
		stateVector[1] = State.$NullState$;
		
		exitAction_PaymentByCoins_PaymentByCoins();
	}
	
	/* Default exit sequence for state WaitingCoins */
	private void exitSequence_PaymentByCoins_WaitingCoins() {
		nextStateIndex = 1;
		stateVector[1] = State.$NullState$;
	}
	
	/* Default exit sequence for state ReturnCoins */
	private void exitSequence_PaymentByCoins_ReturnCoins() {
		nextStateIndex = 1;
		stateVector[1] = State.$NullState$;
		
		exitAction_PaymentByCoins_ReturnCoins();
	}
	
	/* Default exit sequence for state WaitingNFC */
	private void exitSequence_PaymentByNFC_WaitingNFC() {
		nextStateIndex = 2;
		stateVector[2] = State.$NullState$;
	}
	
	/* Default exit sequence for state PaymentByNFC */
	private void exitSequence_PaymentByNFC_PaymentByNFC() {
		nextStateIndex = 2;
		stateVector[2] = State.$NullState$;
		
		exitAction_PaymentByNFC_PaymentByNFC();
	}
	
	/* Default exit sequence for state CancelTransaction */
	private void exitSequence_PaymentByNFC_CancelTransaction() {
		nextStateIndex = 2;
		stateVector[2] = State.$NullState$;
		
		exitAction_PaymentByNFC_CancelTransaction();
	}
	
	/* Default exit sequence for region main region */
	private void exitSequence_main_region() {
		switch (stateVector[0]) {
		case main_region_Waiting:
			exitSequence_main_region_Waiting();
			break;
		case main_region_Take_beverage:
			exitSequence_main_region_Take_beverage();
			break;
		case main_region_Cleaning:
			exitSequence_main_region_Cleaning();
			break;
		case main_region_Payment_Done:
			exitSequence_main_region_Payment_Done();
			break;
		case main_region_Beverage_Preparation:
			exitSequence_main_region_Beverage_Preparation();
			break;
		case main_region_Beverage_Choice:
			exitSequence_main_region_Beverage_Choice();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region PaymentByCoins */
	private void exitSequence_PaymentByCoins() {
		switch (stateVector[1]) {
		case paymentByCoins_PaymentByCoins:
			exitSequence_PaymentByCoins_PaymentByCoins();
			break;
		case paymentByCoins_WaitingCoins:
			exitSequence_PaymentByCoins_WaitingCoins();
			break;
		case paymentByCoins_ReturnCoins:
			exitSequence_PaymentByCoins_ReturnCoins();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region PaymentByNFC */
	private void exitSequence_PaymentByNFC() {
		switch (stateVector[2]) {
		case paymentByNFC_WaitingNFC:
			exitSequence_PaymentByNFC_WaitingNFC();
			break;
		case paymentByNFC_PaymentByNFC:
			exitSequence_PaymentByNFC_PaymentByNFC();
			break;
		case paymentByNFC_CancelTransaction:
			exitSequence_PaymentByNFC_CancelTransaction();
			break;
		default:
			break;
		}
	}
	
	/* Default react sequence for initial entry  */
	private void react_main_region__entry_Default() {
		enterSequence_main_region_Waiting_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react_PaymentByCoins__entry_Default() {
		enterSequence_PaymentByCoins_WaitingCoins_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react_PaymentByNFC__entry_Default() {
		enterSequence_PaymentByNFC_WaitingNFC_default();
	}
	
	private boolean react() {
		return false;
	}
	
	private boolean main_region_Waiting_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if ((sCInterface.coffeeButton || (sCInterface.expressoButton || (sCInterface.soupButton || (sCInterface.icedTeaButton || sCInterface.teaButton))))) {
				exitSequence_main_region_Waiting();
				sCInterface.raiseBeverageChoice();
				
				enterSequence_main_region_Beverage_Choice_default();
			} else {
				did_transition = false;
			}
		}
		return did_transition;
	}
	
	private boolean main_region_Take_beverage_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (timeEvents[0]) {
				exitSequence_main_region_Take_beverage();
				sCInterface.raiseCleaningMachine();
				
				enterSequence_main_region_Cleaning_default();
			} else {
				did_transition = false;
			}
		}
		return did_transition;
	}
	
	private boolean main_region_Cleaning_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (timeEvents[1]) {
				exitSequence_main_region_Cleaning();
				enterSequence_main_region_Waiting_default();
			} else {
				did_transition = false;
			}
		}
		return did_transition;
	}
	
	private boolean main_region_Payment_Done_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (timeEvents[2]) {
				exitSequence_main_region_Payment_Done();
				sCInterface.raiseTimePreparation();
				
				enterSequence_main_region_Beverage_Preparation_default();
			} else {
				did_transition = false;
			}
		}
		return did_transition;
	}
	
	private boolean main_region_Beverage_Preparation_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (timeEvents[3]) {
				exitSequence_main_region_Beverage_Preparation();
				sCInterface.raiseTakeBeverage();
				
				enterSequence_main_region_Take_beverage_default();
			} else {
				did_transition = false;
			}
		}
		return did_transition;
	}
	
	private boolean main_region_Beverage_Choice_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (((timeEvents[4]) && (((sCInterface.getPaymentCard() || sCInterface.getEnoughMoney()))==true))) {
				exitSequence_main_region_Beverage_Choice();
				sCInterface.raiseBeveragePreparation();
				
				enterSequence_main_region_Payment_Done_default();
			} else {
				if (sCInterface.cancelButton) {
					exitSequence_main_region_Beverage_Choice();
					sCInterface.raiseResetSliders();
					
					enterSequence_main_region_Waiting_default();
				} else {
					if (timeEvents[5]) {
						exitSequence_main_region_Beverage_Choice();
						enterSequence_main_region_Waiting_default();
					} else {
						if ((sCInterface.coffeeButton || (sCInterface.expressoButton || (sCInterface.soupButton || (sCInterface.icedTeaButton || sCInterface.teaButton))))) {
							exitSequence_main_region_Beverage_Choice();
							sCInterface.raiseBeverageChoice();
							
							enterSequence_main_region_Beverage_Choice_default();
						} else {
							did_transition = false;
						}
					}
				}
			}
		}
		return did_transition;
	}
	
	private boolean paymentByCoins_PaymentByCoins_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if ((sCInterface.money50centsButton || (sCInterface.money25centsButton || sCInterface.money10centsButton))) {
				exitSequence_PaymentByCoins_PaymentByCoins();
				sCInterface.raiseUpdateSolde();
				
				enterSequence_PaymentByCoins_PaymentByCoins_default();
			} else {
				if (timeEvents[6]) {
					exitSequence_PaymentByCoins_PaymentByCoins();
					sCInterface.raiseCancelPreparation();
					
					enterSequence_PaymentByCoins_ReturnCoins_default();
				} else {
					if (sCInterface.cancelButton) {
						exitSequence_PaymentByCoins_PaymentByCoins();
						sCInterface.raiseCancelPreparation();
						
						enterSequence_PaymentByCoins_ReturnCoins_default();
					} else {
						if (((timeEvents[7]) && (sCInterface.getPaymentDone()==true))) {
							exitSequence_PaymentByCoins_PaymentByCoins();
							enterSequence_PaymentByCoins_WaitingCoins_default();
						} else {
							did_transition = false;
						}
					}
				}
			}
		}
		return did_transition;
	}
	
	private boolean paymentByCoins_WaitingCoins_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if ((sCInterface.money50centsButton || (sCInterface.money25centsButton || sCInterface.money10centsButton))) {
				exitSequence_PaymentByCoins_WaitingCoins();
				sCInterface.raiseUpdateSolde();
				
				enterSequence_PaymentByCoins_PaymentByCoins_default();
			} else {
				did_transition = false;
			}
		}
		return did_transition;
	}
	
	private boolean paymentByCoins_ReturnCoins_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (timeEvents[8]) {
				exitSequence_PaymentByCoins_ReturnCoins();
				sCInterface.raiseMachineReady();
				
				enterSequence_PaymentByCoins_WaitingCoins_default();
			} else {
				did_transition = false;
			}
		}
		return did_transition;
	}
	
	private boolean paymentByNFC_WaitingNFC_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (sCInterface.bipButton) {
				exitSequence_PaymentByNFC_WaitingNFC();
				sCInterface.raisePayByNFC();
				
				enterSequence_PaymentByNFC_PaymentByNFC_default();
				react();
			} else {
				did_transition = false;
			}
		}
		if (did_transition==false) {
			did_transition = react();
		}
		return did_transition;
	}
	
	private boolean paymentByNFC_PaymentByNFC_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (timeEvents[9]) {
				exitSequence_PaymentByNFC_PaymentByNFC();
				sCInterface.raiseCancelTransaction();
				
				enterSequence_PaymentByNFC_CancelTransaction_default();
				react();
			} else {
				if (sCInterface.cancelButton) {
					exitSequence_PaymentByNFC_PaymentByNFC();
					sCInterface.raiseCancelTransaction();
					
					enterSequence_PaymentByNFC_CancelTransaction_default();
					react();
				} else {
					if (((timeEvents[10]) && (sCInterface.getPaymentDone()==true))) {
						exitSequence_PaymentByNFC_PaymentByNFC();
						enterSequence_PaymentByNFC_WaitingNFC_default();
						react();
					} else {
						did_transition = false;
					}
				}
			}
		}
		if (did_transition==false) {
			did_transition = react();
		}
		return did_transition;
	}
	
	private boolean paymentByNFC_CancelTransaction_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (timeEvents[11]) {
				exitSequence_PaymentByNFC_CancelTransaction();
				sCInterface.raiseMachineReady();
				
				enterSequence_PaymentByNFC_WaitingNFC_default();
				react();
			} else {
				did_transition = false;
			}
		}
		if (did_transition==false) {
			did_transition = react();
		}
		return did_transition;
	}
	
}
