package pippin.controlBar;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;

public class ImageLoader {

	private final Hashtable<String, Image> imagesByName;
	private static final String[] imageList = {
			"binary", "binaryDown", "clearAll", "clearAllDown", "NEWff", "NEWffOn", "NEWplay", "NEWplayOn", "NEWplay", "NEWplayOn",
			"NEWrr", "NEWrrOn", "NEWrun", "NEWrunOn", "open", "openDown", "save", "saveAs", "saveAsDown", "saveDisabled", "saveDown", "NEWstop",
			"NEWstopOn", "symbolic", "symbolicDown", "animateOn", "animateOff"
	};

	public ImageLoader() {
		imagesByName = new Hashtable<>();
		try {
			for (String imageName : imageList) {
				URL imageURL = getClass().getResource("pippin/z__images/" + imageName + ".GIF");
				Image image = ImageIO.read(imageURL);
				if (image != null) {
					imagesByName.put(imageName, image);
				}
				image.getHeight(null);
			}
		} catch (IOException | IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	public Image getImage(String name) {
		Image image = imagesByName.get(name);
		if(image == null)
			System.out.println("ImageLoader.getImage: no such image \"" + name + "\" loaded");
		return image;
	}
}