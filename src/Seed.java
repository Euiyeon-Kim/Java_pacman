import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Seed extends Rectangle{
	
	public Seed(int x, int y) {
		setBounds(x+12, y+12, 8, 8);
	}
	
	public void render(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect(x, y, width, height);
	}
	
}
