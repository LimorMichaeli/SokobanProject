package view;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;

import common.LevelData;

/**
 * Command Line Displayer
 * Determines the behavior of displaying to the command line.
 * @see Displayer
 */

public class CLDisplayer implements Displayer {
	
	HashMap<String,Character> hm;
	
	private PrintWriter pwriter;
	private OutputStream os;
	
	public CLDisplayer() {}
	
	
	/** Class constructor. */
	public CLDisplayer(OutputStream os) { pwriter = new PrintWriter(os); }
	
	
	/**
	 * @return the os
	 */
	public OutputStream getOs() {
		return os;
	}


	/** @param os the os to set */
	public void setOs(OutputStream os) {
		this.os = os;
	}


	//Methods
	@Override
	public void display(LevelData levelData, OutputStream os) throws IOException{
		pwriter=new PrintWriter(os);
		if(os!=null && levelData!=null){	
			
			pwriter.println("Steps taken:" + levelData.getNumOfSteps());
			char map[][]=levelData.getBoard();
			
			for(char[] row:map){
				for(char c:row){
					pwriter.print(c);
					
				}
				pwriter.println("");
			}
			
		pwriter.flush();
	}
}



	
}