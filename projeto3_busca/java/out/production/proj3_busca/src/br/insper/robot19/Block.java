package br.insper.robot19;

public class Block {
	
	public final int row;
	public final int col;
	public final BlockType type;
	private boolean visited;
	
	public Block(int i, int j, BlockType type) {
		this.row = i;
		this.col = j;
		this.type = type;
		this.visited = false;
	}

	public int getRow(){
		return row;
	}


	public int getCol(){
		return col;
	}

	public boolean getVisited(){return visited;}

	public void visit(){
		this.visited = true;
	}

	public double getHeuristica(Block end){
		if(this.type==BlockType.WALL){
			return 9999999;
		}else if(this.getVisited()){
			return 9999999;
		} else{
			int actualCol = this.getCol();
			int actualRow = this.getRow();
			int goalCol = end.getCol();
			int goalRow = end.getRow();

			double dist = Math.abs(actualCol-goalCol) + Math.abs(actualRow-goalRow);

			return dist;
		}
	}

	public  double getEstrelinha(Block end, float cost){
		double dist = this.getHeuristica(end) + cost;
		return dist;
	}
}
