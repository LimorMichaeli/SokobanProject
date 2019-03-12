package controller;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import controller.commands.ICommand;


public class Controller{

	//Variable Declaration 
	private volatile boolean stop;
	
	private BlockingQueue<ICommand> queue;
	public Controller() {queue = new ArrayBlockingQueue<ICommand>(10);}

	/** Inserting a command into the queue */
	public void insertCommand(ICommand C) {
		if(C != null)
			try { queue.put(C); } 
			catch (InterruptedException e) { 
					System.out.println("Error executing the command, exitting now.\n");
					System.exit(0);
					}
	}

	/** Starting to pull commands from the queue and execute them */
    public void start() {Thread thread = new Thread(new Runnable() {
    	@Override
    	public void run() {
    			while (!stop) {
    					try {
    						ICommand cmd = queue.poll(1, TimeUnit.SECONDS);
    						if (cmd != null)
    							cmd.execute();		
    					
    					} catch (InterruptedException e) {
    						System.out.println("Error executing the command, exitting now.\n");
    						System.exit(0);	}
			}
		}
	});
thread.start();
}

    /** Stopping the commands executing */
    public void stop() { stop = true; }
  }