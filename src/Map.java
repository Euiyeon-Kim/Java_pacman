import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Map {
	public int width; // 40
	public int height; //25
	
	public Tile[][] tiles;
	
	public List<Seed> seeds;
	public List<Ghost> Ghosts;
	public List<SmartGhost> SmartGhosts;
	
	private int location=30;
	
	public Map(String path) { // map(png)�� ����Ǿ� �ִ� ��θ� �����ڷ� ����
		try {
			seeds=new ArrayList<>();
			Ghosts=new ArrayList<>();
			SmartGhosts=new ArrayList<>();
			
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			this.width=map.getWidth();
			this.height=map.getHeight();
			
			int[] pixels = new int[width*height]; // ���伥���� �׷����� ��
			// pixels �迭�� map.png�� �ȼ��� ���� �Ϸ�ȭ(���ι�������) �ؼ� ������
			map.getRGB(0, 0, width, height, pixels, 0, width);
			
			tiles =new Tile[width][height]; // GUI�� ������ ��

			for(int i=0;i<width;i++) {
				for(int j=0;j<height;j++) {
					int val = pixels[i+(j*width)];
					if(val==0xFF000A7C) {
						// ��
						tiles[i][j]=new Tile(i*32+location, j*32+location);
					}
					else if(val==0xFFFFD800) {
						// �Ѹ�
						Game.pacman.x=i*32+location;
						Game.pacman.y=j*32+location;
					}
					else if(val==0xFFFF0000) {
						// �Ϲ� ����
						Ghosts.add(new Ghost(i*32+location, j*32+location));
					}
					else if(val==0xFF00FFFF) {
						// �ȶ��� ����
						SmartGhosts.add(new SmartGhost(i*32+location, j*32+location));
					}
					else{
						// ����
						seeds.add(new Seed(i*32+location, j*32+location));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void tick() { // map���� ���ɵ��� ���¸� �ٲٰ� ������ ��Ŵ
		for(int i=0;i<Ghosts.size();i++) {
			Ghosts.get(i).tick();
		}
		for(int i=0;i<SmartGhosts.size();i++) {
			SmartGhosts.get(i).tick();
		}
	}
	
	public void render(Graphics g) { // seeds�� tile�� ���°� �ٲ� �ʿ䰡 �����Ƿ� tick�Լ��� �������� ����
		for(int i=0;i<width;i++) {
			for(int j=0;j<height;j++) {
				if(tiles[i][j]!=null)
					tiles[i][j].render(g);
			}
		}
		
		for(int i=0;i<seeds.size();i++) {
			seeds.get(i).render(g);
		}
		for(int i=0;i<Ghosts.size();i++) {
			Ghosts.get(i).render(g);
		}
		for(int i=0;i<SmartGhosts.size();i++) {
			SmartGhosts.get(i).render(g);
		}
	}
}
