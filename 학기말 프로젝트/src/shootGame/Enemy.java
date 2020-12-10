package shootGame;

public class Enemy {
	int x;
	int y;
	int width=50;
	int height=50;
	
	public Enemy(int x, int y) {
		this.x=x;
		this.y=y;
	}
	
	public void moveEn() {
		x--;
	}
}
