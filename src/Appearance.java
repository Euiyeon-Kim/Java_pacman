import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Appearance { // res폴더에 있는 appearance 파일을 읽어옴
	
	private BufferedImage appearance;
	
	public Appearance(String path) {
		try {
			appearance = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public BufferedImage getAppearance(int x, int y) {
		return appearance.getSubimage(x, y, 32, 32);
	}
	
}
