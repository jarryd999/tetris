
public class Piece {
	int[][] coords;
	
	public Piece(int[][] coords){
		this.coords = coords;
	}
	
	public void moveDown(){
		//loop for each coordinate
		for (int i = 0; i < 4; i++){
			this.coords[i][1]++;
		}
	}
}

