
//package FlowSkeleton;
import java.io.File;
import java.awt.image.*;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Locale;

/**
 * Terrain class no constructor class
 */
public class Terrain {

	float [][] height; // regular grid of height values
	int dimx, dimy; // data dimensions
	BufferedImage img; // greyscale image for displaying the terrain top-down

	ArrayList<Integer> permute;	// permuted list of integers in range [0, dimx*dimy)

	/**
	 * 	overall number of elements in the height grid
	 * @return number of elements
	 */
	int dim(){
		return dimx*dimy;
	}
	/**
	 * get x-dimensions (number of columns)
	 */
	int getDimX(){
		return dimx;
	}
	/**
	 * get y-dimensions (number of rows)
	 */
	int getDimY(){
		return dimy;
	}

	/**
	 *
	 * @return greyscale image of terrain
	 */

	public BufferedImage getImage() {
		  return img;
	}

	/**
	 * // convert linear position into 2D location in grid
	 * @param pos liner index
	 * @param ind array of size 2 to store coordinates in
	 */
	void locate(int pos, int [] ind)
	{
		ind[0] = (int) pos / dimy; // x
		ind[1] = pos % dimy; // y	
	}

	/**
	 * return the floating elevation value
	 * @param x coordinate x
	 * @param y coordinate y
	 * @return floating value of terrain point elevation
	 */
	public float getHeight(int x,int y){
		return (this.height[y][x]);
	}

	/**
	 *  convert height values to greyscale colour and populate an image
	 */
	void deriveImage()
	{
		img = new BufferedImage(dimx, dimy, BufferedImage.TYPE_INT_ARGB);
		float maxh = -10000.0f, minh = 10000.0f;
		
		// determine range of heights
		for(int x=0; x < dimx; x++)
			for(int y=0; y < dimy; y++) {
				float h = height[x][y];
				if(h > maxh)
					maxh = h;
				if(h < minh)
					minh = h;
			}
		
		for(int x=0; x < dimx; x++)
			for(int y=0; y < dimy; y++) {
				 // find normalized height value in range
				 float val = (height[x][y] - minh) / (maxh - minh);
				 Color col = new Color(val, val, val, 1.0f);
				 img.setRGB(x, y, col.getRGB());
			}
	}

	/**
	 * 	 generate a permuted list of linear index positions to allow a random
	 * 	 traversal over the terrain
	 */
	void genPermute() {
		permute = new ArrayList<Integer>();
		for(int idx = 0; idx < dim(); idx++)
			permute.add(idx);
		java.util.Collections.shuffle(permute);
	}

	/**
	 * // find permuted 2D location from a linear index in the
	 * 	// range [0, dimx*dimy)
	 * @param i liner index
	 * @param loc array of size two to store coordinates of point
	 */
	void getPermute(int i, int [] loc) {
		locate(permute.get(i), loc);
	}

	/**
	 * 	// read in terrain from file
	 * @param fileName of text file
	 */
	void readData(String fileName){ 
		try{ 
			Scanner sc = new Scanner(new File(fileName));
         sc.useLocale(Locale.US);
			
			// read grid dimensions
			// x and y correspond to columns and rows, respectively.
			// Using image coordinate system where top left is (0, 0).
			dimy = sc.nextInt(); 
			dimx = sc.nextInt();
			
			// populate height grid
			height = new float[dimx][dimy];
			
			for(int y = 0; y < dimy; y++){
				for(int x = 0; x < dimx; x++)	
					height[y][x] = sc.nextFloat();
				}
				
			sc.close(); 
			
			// create randomly permuted list of indices for traversal 
			genPermute(); 
			
			// generate greyscale heightfield image
			deriveImage();
		} 
		catch (IOException e){ 
			System.out.println("Unable to open input file "+fileName);
			e.printStackTrace();
		}
		catch (java.util.InputMismatchException e){ 
			System.out.println("Malformed input file "+fileName);
			e.printStackTrace();
		}
	}
}