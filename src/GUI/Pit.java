package GUI;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JPanel;
import myGame.Match;

// This class constitutes an abstract pit on the GUI

public abstract class Pit extends JPanel{
	 protected int numStones;
	 protected Match match;
	 protected BoardFormat format;
	protected Lock lock = new ReentrantLock();

	 public Pit(BoardFormat format)
	 {
		 this.format = format;
		 this.numStones = 0;	
	 }
		protected void refresh() {
			this.repaint();
		}
		
		public void addStone()
		{
			lock.lock();
			this.numStones++;
			lock.unlock();
			refresh();
		}
		
		public void addStones(int numStones)
		{
			lock.lock();
			this.numStones += numStones;
			lock.unlock();
			refresh();
		}

		public int getNumStones() {
			return numStones;
		}

		public void setNumStones(int numStones) {
			lock.lock();
			this.numStones = numStones;
			lock.unlock();
			refresh();
		}
		
		public int takeAll()
		{
			// This method return the number of stones in the pit and reset the number to zero
			int result = this.numStones;
			
			lock.lock();
			this.numStones = 0;
			lock.unlock();
			refresh();
			
			return result;
	    }
}