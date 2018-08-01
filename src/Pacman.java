import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Pacman extends Rectangle{ // ����� ������ ������ ���� �ϱ� ���� extends Rectangle
	
	public boolean right;
	public boolean left;
	public boolean up;
	public boolean down;
	private int speed = 3;
	private int imageIndex = 0;
	
	Score curScore;
	
	public Pacman(int x, int y) {
		curScore=Game.score;
		curScore.score=0;
		setBounds(x, y, 30, 30);
	}
	
	private boolean canMove(int next_x, int next_y) { // Ghost�� ���� �Լ� ���� ������ �������� ����
		
		Rectangle bounds = new Rectangle(next_x, next_y, width, height);
		Map map = Game.map;
		
		for(int i=0;i<map.tiles.length;i++){
			for(int j=0;j<map.tiles[0].length;j++){
				if(map.tiles[i][j]!=null){
					if(bounds.intersects(map.tiles[i][j])) {
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	public void tick() { // �� �������� ������ ������ �̹����� �ٲ�
		if(right&&canMove(x+speed, y)){
			x=x+speed;
			imageIndex = 0;
		}
		if(left&&canMove(x-speed, y)) {
			x=x-speed;
			imageIndex = 1;
		}
		if(up&&canMove(x, y-speed)) {
			y=y-speed;
			imageIndex = 2;
		}
		if(down&&canMove(x, y+speed)) {
			y=y+speed;
			imageIndex = 3;
		}
		
		Map map = Game.map;
		
		for(int i=0;i<map.seeds.size();i++){ // �Ѹǰ� seed�� ��ġ�� seed�� �����
			if(this.intersects(map.seeds.get(i))) {
				curScore.score+=10;
				map.seeds.remove(i);
				break;
			}
		}
		
		if(map.seeds.size()==0) {
			// win
			//seeds�� �� ����
			Game.STATE=Game.START;
			return;
		}
		
		for(int i=0;i<Game.map.Ghosts.size();i++){ 
			// lose
			// �Ϲ� ���ɿ��� ����
			Ghost temp = Game.map.Ghosts.get(i);
			if(temp.intersects(this)) {
				Game.STATE=Game.START;
				Game.DEAD = true;
				curScore.insertScore();
				return;
			}
		}
		for(int i=0;i<Game.map.SmartGhosts.size();i++){
			// lose
			// �ȶ��� ���ɿ��� ����
			SmartGhost temp=Game.map.SmartGhosts.get(i);
			if(temp.intersects(this)){
				Game.STATE=Game.START;
				Game.DEAD = true;
				curScore.insertScore();
				return;
			}
		}
	}
	
	public void render(Graphics g){
		g.drawImage(Character.pacman[imageIndex], x, y, width, height, null);
	}
}
