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
		
		private boolean resetSolde;
		
		
		public boolean isRaisedResetSolde() {
			synchronized(DefaultSMStatemachine.this) {
				return resetSolde;
			}
		}
		
		protected void raiseResetSolde() {
			synchronized(DefaultSMStatemachine.this) {
				resetSolde = true;
				for (SCInterfaceListener listener : listeners) {
					listener.onResetSoldeRaised();
				}
			}
		}
		
		protected void clearEvents() {
			money50centsButton = false;
			money25centsButton = false;
			money10centsButton = false;
		}
		protected void clearOutEvents() {
		
		updateSolde = false;
		resetSolde = false;
		}
		
	}
	
	
	protected SCInterfaceImpl sCInterface;
	
	private boolean initialized = false;
	
	public enum State {
		main_region_Waiting,
		main_region_Payment,
		$NullState$
	};
	
	private final State[] stateVector = new State[1];
	
	private int nextStateIndex;
	
	private ITimer timer;
	
	private final boolean[] timeEvents = new boolean[1];
	
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
		for (int i = 0; i < 1; i++) {
			stateVector[i] = State.$NullState$;
		}
		clearEvents();
		clearOutEvents();
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
				case main_region_Payment:
					main_region_Payment_react(true);
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
	}
	
	/**
	 * @see IStatemachine#isActive()
	 */
	public synchronized boolean isActive() {
		return stateVector[0] != State.$NullState$;
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
		case main_region_Payment:
			return stateVector[0] == State.main_region_Payment;
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
	
	public synchronized boolean isRaisedUpdateSolde() {
		return sCInterface.isRaisedUpdateSolde();
	}
	
	public synchronized boolean isRaisedResetSolde() {
		return sCInterface.isRaisedResetSolde();
	}
	
	/* Entry action for state 'Payment'. */
	private void entryAction_main_region_Payment() {
		timer.setTimer(this, 0, 5000, false);
	}
	
	/* Exit action for state 'Payment'. */
	private void exitAction_main_region_Payment() {
		timer.unsetTimer(this, 0);
	}
	
	/* 'default' enter sequence for state Waiting */
	private void enterSequence_main_region_Waiting_default() {
		nextStateIndex = 0;
		stateVector[0] = State.main_region_Waiting;
	}
	
	/* 'default' enter sequence for state Payment */
	private void enterSequence_main_region_Payment_default() {
		entryAction_main_region_Payment();
		nextStateIndex = 0;
		stateVector[0] = State.main_region_Payment;
	}
	
	/* 'default' enter sequence for region main region */
	private void enterSequence_main_region_default() {
		react_main_region__entry_Default();
	}
	
	/* Default exit sequence for state Waiting */
	private void exitSequence_main_region_Waiting() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state Payment */
	private void exitSequence_main_region_Payment() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_main_region_Payment();
	}
	
	/* Default exit sequence for region main region */
	private void exitSequence_main_region() {
		switch (stateVector[0]) {
		case main_region_Waiting:
			exitSequence_main_region_Waiting();
			break;
		case main_region_Payment:
			exitSequence_main_region_Payment();
			break;
		default:
			break;
		}
	}
	
	/* Default react sequence for initial entry  */
	private void react_main_region__entry_Default() {
		enterSequence_main_region_Waiting_default();
	}
	
	private boolean react() {
		return false;
	}
	
	private boolean main_region_Waiting_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if ((sCInterface.money50centsButton || (sCInterface.money25centsButton || sCInterface.money10centsButton))) {
				exitSequence_main_region_Waiting();
				sCInterface.raiseUpdateSolde();
				
				enterSequence_main_region_Payment_default();
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
	
	private boolean main_region_Payment_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if ((sCInterface.money50centsButton || (sCInterface.money25centsButton || sCInterface.money10centsButton))) {
				exitSequence_main_region_Payment();
				sCInterface.raiseUpdateSolde();
				
				enterSequence_main_region_Payment_default();
				react();
			} else {
				if (timeEvents[0]) {
					exitSequence_main_region_Payment();
					sCInterface.raiseResetSolde();
					
					enterSequence_main_region_Waiting_default();
					react();
				} else {
					did_transition = false;
				}
			}
		}
		if (did_transition==false) {
			did_transition = react();
		}
		return did_transition;
	}
	
}
