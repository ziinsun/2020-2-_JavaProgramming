package shootGame;

public class Shot {
	int x;
	int y;
	int width=30;
	int height=30;
	
	public Shot(int x, int y) {
		this.x=x;
		this.y=y;
	}
	
	public void moveShot() {
		x++;
	}
}
