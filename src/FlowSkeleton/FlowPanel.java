
package FlowSkeleton;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import javax.swing.JPanel;
import java.util.List;
public class FlowPanel extends JPanel implements Runnable {

	Terrain land;
	private List<Water> blocks=new LinkedList<Water>();
	private BufferedImage grid=null;
	private BufferedImage water=null;

	FlowPanel(Terrain terrain) {
		land=terrain;
	}
	//add block of water of the mouseclick point in grid
	public void addBlock(Water w){
		blocks.add(w);
		this.repaint();
	}
	public void removeWater(){
		blocks.removeAll(this.blocks);
		this.repaint();
	}
	// responsible for painting the terrain and water
	// as images
	@Override
    protected void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		// draw the landscape in greyscale as an image
		if (land.getImage() != null) {
			g.drawImage(land.getImage(), 0, 0, null);
			for(Water b:blocks){
				b.draw(g);
			}
		}

	}

	public void run() {
		// display loop here
		// to do: this should be controlled by the GUI
		// to allow stopping and starting
	    repaint();
	}
}