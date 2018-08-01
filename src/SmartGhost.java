import java.awt.*;
import java.util.Random;

public class SmartGhost extends Rectangle{
	
	// 랜덤모드 <-> 스마트모드
	private int random = 0;
	private int smart = 1;
	private int find_path = 2;	// 방향 찾기 모드
	
	private int state = smart;
	
	private int right = 0;
	private int left = 1;
	private int up = 2;
	private int down = 3;
	
	private int direction = -1;
	
	public Random randomMaker;
	
	private int time = 0;
	
	private int changingTime = 60 * 5;
	
	private int speed = 1;
	
	private int lastDirection = -1;
	
	private int imageIndex = 0;
	
	public SmartGhost(int x, int y) {
		randomMaker = new Random();
		setBounds(x, y, 32, 32);
		direction = randomMaker.nextInt(4);
	}
	
	public void tick() {
		// 랜덤으로 방향 생성하기 -> 일반 유령과 같은 동작
		imageIndex = 0;
		// 랜덤모드
		if(state == random) {
			if(direction == right) {
				if(canMove(x + speed, y)) {
					x=x+speed;
					imageIndex=0;
					lastDirection=right;
				}
				else {
					direction = randomMaker.nextInt(4);
				}
			}
			else if(direction == left) {
				if(canMove(x - speed, y)) {
					x=x-speed;
					imageIndex=1;
					lastDirection=left;
				}
				else {
					direction = randomMaker.nextInt(4);
				}
			}
			else if(direction == up) {
				if(canMove(x, y - speed)) {
					y=y-speed;
					imageIndex=2;
					lastDirection=up;
				}
				else {
					direction = randomMaker.nextInt(4);
				}
			}
			else if(direction == down) {
				if(canMove(x, y + speed)) {
					y=y+speed;
					imageIndex=3;
					lastDirection=down;
				}
				else {
					direction = randomMaker.nextInt(4);
				}
			}
			
			time++;
			
			if(time == changingTime) {
				state = smart;
				time = 0;
			}
		}
		
		// 스마트모드
		else if(state == smart) {
			// follow the player!
			imageIndex = 1;
			boolean move = false;
			
			if(x < Game.pacman.x) {
				if(canMove(x + speed, y)) {
					x=x+speed;
					imageIndex=4;
					move = true;
					lastDirection = right;
				}
			}
			
			if(x > Game.pacman.x) {
				if(canMove(x - speed, y)) {
					x=x-speed;
					imageIndex=5;
					move = true;
					lastDirection = left;
				}
			}
			
			if(y < Game.pacman.y) {
				if(canMove(x, y + speed)) {
					y=y+speed;
					imageIndex=7;
					move = true;
					lastDirection = down;
				}
			}
			
			if(y > Game.pacman.y) {
				if(canMove(x, y - speed)) {
					y=y-speed;
					imageIndex=6;
					move = true;
					lastDirection = up;
				}
			}
			
			if(x == Game.pacman.x && y == Game.pacman.y) { // 팩맨을 찾음
				move = true;
			}
			
			if(!move) {
				state = find_path;
			}
			
			time++;
			
			// 5초마다 모드 변경
			if(time == changingTime) {
				state = random;
				time = 0;
			}
		}
		
		// 방향 찾기
		else if(state == find_path) {
			imageIndex = 4;
			if(lastDirection == right) {
				if(y < Game.pacman.y) {
					if(canMove(x, y + speed)) {
						y=y+speed;
						imageIndex=7;
						state = smart;
						lastDirection = up;
					}							
				}
				else {
					if(canMove(x, y - speed)) {
						y=y-speed;
						imageIndex=6;
						state = smart;
						lastDirection = down;
					}
				}
				if(canMove(x + speed, y)) {
					x=x+speed;
					imageIndex=4;
					lastDirection = right;
				}
				else {
					lastDirection=randomMaker.nextInt(4);
				}
			}
			else if(lastDirection == left) {
				if(y < Game.pacman.y) {
					if(canMove(x, y + speed)) {
						y=y+speed;
						imageIndex=7;
						state = smart;
						lastDirection = down;
					}							
				}
				else {
					if(canMove(x, y - speed)) {
						y=y-speed;
						imageIndex=6;
						state = smart;
						lastDirection = up;
						
					}
				}
				if(canMove(x - speed, y)) {
					x=x-speed;
					imageIndex=5;
					lastDirection = left;
				}
				else {
					lastDirection=randomMaker.nextInt(4);
				}
			}
			else if(lastDirection == up) {
				if(x < Game.pacman.x) {
					if(canMove(x + speed, y)) {
						x=x+speed;
						imageIndex=4;
						state = smart;
						lastDirection = right;
					}							
				}
				else {
					if(canMove(x - speed, y)) {
						x=x-speed;
						imageIndex=5;
						state = smart;
						lastDirection = left;
					}
				}
				if(canMove(x, y - speed)) {
					y=y-speed;
					imageIndex=6;
					lastDirection = up;
				}
				else {
					lastDirection=randomMaker.nextInt(4);
				}
			}
			else if(lastDirection == down) {
				if(x < Game.pacman.x) {
					if(canMove(x + speed, y)) {
						x=x+speed;
						imageIndex=4;
						state = smart;
						lastDirection = right;
					}							
				}
				else {
					if(canMove(x - speed, y)) {
						x=x-speed;
						imageIndex=5;
						state = smart;
						lastDirection = left;
					}
				}
				if(canMove(x, y + speed)) {
					y=y+speed;
					imageIndex=7;
					lastDirection = down;
				}	
				else {
					lastDirection=randomMaker.nextInt(4);
				}
			}
			
			time++;
			
			// 5초마다 모드 변경
			if(time == changingTime) {
				state = random;
				time = 0;
			}
		}
	}
	
	// 이동가능여부판단
	private boolean canMove(int next_x, int next_y) {
		
		Rectangle bounds = new Rectangle(next_x, next_y, width, height);
		Map map = Game.map;
		
		for(int i=0;i<map.tiles.length;i++) {
			for(int j=0;j<map.tiles[0].length;j++) {
				if(map.tiles[i][j]!=null) {
					if(bounds.intersects(map.tiles[i][j])) {
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	public void render(Graphics g) {
		g.drawImage(Character.smartGhost[imageIndex], x, y, width, height, null);
	}
}
