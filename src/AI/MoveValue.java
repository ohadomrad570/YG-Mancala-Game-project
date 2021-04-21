package AI;

// This class constitutes a node in the parsing tree
public class MoveValue {
	private int value; // the node value
	private int pit;  // the chosen pit that lead to the board state
	
	public MoveValue(int value, int pit){
		this.value = value;
		this.pit = pit;
	}
	public void setValue(int value){
		this.value = value;
	}
	
	public int getValue(){
		return this.value;
	}
	
	public void setPit(int pit){
		this.pit = pit;
	}
	
	public int getPit(){
		return this.pit;
	}
}
