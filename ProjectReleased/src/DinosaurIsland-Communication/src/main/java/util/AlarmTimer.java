/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package util;

/**
 * <b>Overview</b><br>
 * <p>
 * Description of the type.
 * This state information includes:
 * <ul>
 * <li>Element 1
 * <li>Element 2
 * <li>The current element implementation 
 *     (see <a href="#setXORMode">setXORMode</a>)
 * </ul>
 * </p>
 *
 * <b>Responsibilities</b><br>
 * <p>
 * Other description in a separate paragraph.
 * </p>
 *
 * <b>Collaborators</b><br>
 * <p>
 * Here write about classes 
 * </p>
 * 
 * @author	RAA
 * @version 1.0
 * @since	25/giu/2011@16.35.51
 *
 */
public abstract class AlarmTimer extends Thread {
	
	private int interval;
	private int updatePeriod;
	private boolean isRunning;
	private boolean toBeStoppedImmediately;
	
	public AlarmTimer(int theInterval, int theUpdatePeriod) {
		interval = theInterval;
		toBeStoppedImmediately = false;
		isRunning = false;
		updatePeriod = theUpdatePeriod;
	}
	
	public AlarmTimer(int theInterval) {
		this(theInterval, 1000);
	}
	

	
	public void run() {
		
		while (true) {
			
			while (isRunning) {
				
				while (interval > 0 && isRunning) {
					try {
						sleep(updatePeriod);
					} catch (InterruptedException e) {}

					
					interval -= updatePeriod;
				}
				
				if (isRunning) {
					isRunning = false;
					actionToBePerformed();
				}
			}
			
			try {
				sleep(50);
			} catch (InterruptedException e) {}

			if (toBeStoppedImmediately) {
				return;
			}
			
		}
		
	}
	
	public final void setNewInterval(int theNewInterval) {
		setInterval(theNewInterval);
		play();
	}

	public final void reset() {
		isRunning = false;
		interval = 0;
		interrupt();
	}
	
	public final void play() {
		isRunning = true;
		interrupt();
	}
	
	public final void stopForever() {
		toBeStoppedImmediately = true;
		isRunning = false;
		interrupt();
	}
	
	abstract public void actionToBePerformed();
	
	/**
	 * @return the interval
	 */
	final public int getInterval() {
		return interval;
	}

	/**
	 * @param interval the interval to set
	 */
	final public void setInterval(int theInterval) {
		interval = theInterval;
	}	
}
