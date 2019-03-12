package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Observable;

import common.LevelData;
import controller.server.ClientHandler;
import db.Score;

/** Command Line Interface */
public class CLI extends Observable implements IView,ClientHandler{

	//Variable Declaration
	private BufferedReader reader;
	private PrintWriter writer;
	private Displayer displayer;
	private OutputStream os;
	private InputStream is;
	private String exitString;
	private String shutDownString;
	
	
	/**@return The current OutputStream. */
	public OutputStream getOs() { return os; }

	/** @return The current InputStream.*/
	public InputStream getIs() { return is; }

	/** @param InputStream to set */
	public void setIs(InputStream is) {
		this.is = is;
		reader = new BufferedReader(new InputStreamReader(is));
	}

	/** @param OutputStream to set */
	public void setOs(OutputStream os) {
		this.os = os;
		writer = new PrintWriter(os);
	}

	
	

	/** Class constructor when given paramters */
	public CLI(OutputStream os, InputStream is) {
		this.os=os;
		reader = new BufferedReader(new InputStreamReader(is));
		writer = new PrintWriter(os);
		shutDownString = "Exit";
		exitString = "bye";
		displayer = new CLDisplayer(os);
	}


	@Override
	public void displayLevel(LevelData levelData,Integer steps) {
		try {
			displayer.display(levelData, os);
			
		} catch (IOException e) {
			System.out.println("Error displaying data, exitting now.");
			System.exit(0);
		}
		
	}



	public void start() {
	
		
		displayer= new CLDisplayer(os);

		Thread t=new Thread(new Runnable() {
			
			@Override
			public void run() {
				String commandLine = "";
				do {
					try {
					
						commandLine = reader.readLine();
						
					} catch (IOException e) {
								commandLine="bye";
							}
						String[] arr = commandLine.split(" ");
						LinkedList<String> params = new LinkedList<String>();

						for (String param : arr) {
							params.add(param);
						}
						
						setChanged();
						notifyObservers(params);
						
					}while(!exitString.equals(commandLine) && !shutDownString.equals(commandLine)  );
				writer.flush();
				writer.println("exiting");
				writer.flush();
			}
		});
		t.start();
		setChanged();

	
		try {
			t.join();
		} catch (Exception e) {
			System.exit(0);
			
		}
		
	}

	@Override
	public void displayMessage(LinkedList<String> params) {
		String msg = String.join(" ", params);
		writer.println(msg);	
		writer.flush();
		
	}

	@Override
	public void handleClient(InputStream is, OutputStream os) {
		this.is=is;
		this.os=os;
		this.reader=new BufferedReader(new InputStreamReader(is));
		this.writer= new PrintWriter(os);
		start();
		
	}

	@Override
	public void updateTime(int seconds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void userWin() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setScores(Score[] scores) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSolution(String[] actions) {
		// TODO Auto-generated method stub
		
	}	
}