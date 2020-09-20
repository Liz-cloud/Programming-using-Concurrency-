
package FlowSkeleton;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import javax.swing.JPanel;
import java.util.List;
import java.util.Iterator;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.ReentrantLock;

public class FlowPanel extends JPanel implements Runnable {
	Producer[] producers=new Producer[4];
	Thread[] threads = new Thread[4];
	volatile AtomicBoolean running=new AtomicBoolean();
	volatile AtomicBoolean end=new AtomicBoolean();
	Water water;
	FlowPanel(Water w){
		this.water=w;
	}
		// responsible for painting the terrain and water
	// as images
	@Override
	protected void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		super.paintComponent(g);
		// draw the landscape in greyscale as an image
		if (water.t.getImage() != null) {
			g.drawImage(water.t.getImage(), 0, 0, null);
			g.drawImage(water.getImg(), 0, 0, null);
		}
	}

	public void run() {
		for(int num=0;num<4;num++){
			producers[num]=new Producer(water,num);
			threads[num]=new Thread(producers[num]);
		}
		while (!end.get()){
			if(running.get()){
				synchronized (this){
					for (int t=0;t<4;t++){
						threads[t].run();
					}
					try {
						for(int i=0;i<4;i++){
							threads[i].join();
						}
					}catch (Exception e){}
				}
				this.repaint();
			}
		}
	}
}


