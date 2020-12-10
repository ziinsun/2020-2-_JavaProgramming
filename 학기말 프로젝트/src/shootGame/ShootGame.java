package shootGame;

import java.awt.Canvas; 
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ShootGame {
	JFrame frame;
	int width = 800;
	int height = 600;
	CT canvas = new CT();

	Shot shot;

	ArrayList<Enemy> enemys = new ArrayList<>();
	ArrayList<Shot> shots = new ArrayList<Shot>();
	
	Image dog, bond, slime;

//	Character hero = new Character(5, 5);

	int speed = 10;

	int x = 50;
	int y = 250;

	int xDirection = 0;
	int yDirection = 0;

	boolean attack = false;
	boolean start = false, end = false;

	public ShootGame() {
		frame = new JFrame("슈팅게임");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, height);

		frame.getContentPane().add(new GamePanel());
		frame.addKeyListener(new MyKeyListener());

		frame.setVisible(true);

	}

	public void play() {
		int enCnt = 0;
		int sCnt = 0;

		while (true) {
			if (start == true) {
				x += xDirection;
				y += yDirection;

				frame.repaint();
				canvas.repaint();
				crash();

				if (enCnt > 3000) {
					enCreate();
					enCnt = 0;
				}

				if (sCnt > 100) {
					shot();
					sCnt = 0;
				}
			}
			enCnt += 10;
			sCnt += 10;

			try {
				Thread.sleep(speed);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}
	} // play

	public void enCreate() {
		for (int i = 0; i < 5; i++) {
			double rx = width - 100 + Math.random() * 60;
			double ry = Math.random() * (height - 30);
			Enemy en = new Enemy((int) rx, (int) ry);
			enemys.add(en);
		}

	}

	public void shot() {
		if (attack) {
			if (shots.size() < 100) {
				Shot s = new Shot(this.x, this.y);
				shots.add(s);
			}
		}
	}

	public void crash() {
		Polygon p = null;
		for (int i = 0; i < shots.size(); i++) {
			Shot s = (Shot) shots.get(i);
			for (int j = 0; j < enemys.size(); j++) {
				Enemy e = (Enemy) enemys.get(j);
				int[] xpoints = { s.x, (s.x + s.width), (s.x + s.width), s.x };
				int[] ypoints = { s.y, s.y, (s.y + s.height), (s.y + s.height) };
				p = new Polygon(xpoints, ypoints, 4);
				if (p.intersects((double) e.x, (double) e.y, (double) e.width, (double) e.height)) {
					shots.remove(i);
					enemys.remove(j);
				}

			}
		}
		for (int i = 0; i < enemys.size(); i++) {
			Enemy e = (Enemy) enemys.get(i);
			int[] xpoints = { x, (x + 30), (x + 30), x };
			int[] ypoints = { y, y, (y + 30), (y + 30) };
			p = new Polygon(xpoints, ypoints, 4);
			if (p.intersects((double) e.x, (double) e.y, (double) e.width, (double) e.height)) {
//				shots.remove(i);
				start = false;
				end = true;
			}
		}
	}

	private class GamePanel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(dog, x, y, 80, 80, this);
			
			for (int i = 0; i < enemys.size(); i++) {
				Enemy e = (Enemy) enemys.get(i);
				g.drawImage(slime, e.x, e.y, e.width, e.height, this);
				if (e.x <= 10)
					enemys.remove(i);
				e.moveEn();
			}

			for (int i = 0; i < shots.size(); i++) {
				Shot s = (Shot) shots.get(i);
				g.drawImage(bond, s.x, s.y, s.width, s.height, this);
				if (s.x >= width - 10)
					shots.remove(i);
				s.moveShot();
			}

			if (end == true) {
				g.drawString("GAME OVER", 320, 250);
			}

		}
	} // JPanel

	private class MyKeyListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				yDirection = -2;
				xDirection = 0;
				break;
			case KeyEvent.VK_DOWN:
				yDirection = 2;
				xDirection = 0;
				break;
			case KeyEvent.VK_LEFT:
				xDirection = -2;
				yDirection = 0;
				break;
			case KeyEvent.VK_RIGHT:
				xDirection = 2;
				yDirection = 0;
				break;
			case KeyEvent.VK_SPACE:
				attack = true;
				break;
			case KeyEvent.VK_ENTER:
				start = true;
				end = false;
				break;
			}

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				yDirection = 0;
				xDirection = 0;
				break;
			case KeyEvent.VK_DOWN:
				yDirection = 0;
				xDirection = 0;
				break;
			case KeyEvent.VK_LEFT:
				xDirection = 0;
				yDirection = 0;
				break;
			case KeyEvent.VK_RIGHT:
				xDirection = 0;
				yDirection = 0;
				break;
			case KeyEvent.VK_SPACE:
				attack = false;
				break;
			}
			

		}

	} // key

	private class CT extends Canvas {
		public CT() {
			dog = Toolkit.getDefaultToolkit().getImage("src/image/개.png");
			bond = Toolkit.getDefaultToolkit().getImage("src/image/뼈.png");
			slime = Toolkit.getDefaultToolkit().getImage("src/image/슬라임.png");
		}
	} // canvas
}
