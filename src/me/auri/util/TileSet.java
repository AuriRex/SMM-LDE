package me.auri.util;

public class TileSet {

	final int x, y;
	
	int tilesx;
	int tilesy;
	
	public Image[] tiles;
	
	private long timestamp = 0;
	
	public TileSet(Image img, int x, int y, boolean dbg) {
		this.x = x;
		this.y = y;
		
		tilesx = img.width/x;
		tilesy = img.height/y;
		
		tiles = new Image[tilesx*tilesy];
		if(dbg)
			timestamp = System.nanoTime();
		int tmp = 0;
		int tmpy = 0;
		int t_ = 0;
		int tc = 0;
		for(int t = 0; t < (tilesx*tilesy); t++) {
			
			if(tmp == img.width-x) {
				
				
				tmp = 0;
				t_++;
				tc = 0;
			}
			
			
			tmp = tc*x;
			tc++;
			tmpy = t_*y;
			
			
			int[] pixels = new int[x*y];
			int ci = 0;
			//System.out.println("tmpy: "+tmpy+ " - tmpx: "+tmp);
			for(int iy = tmpy; iy < tmpy+y; iy++) {
				for(int ix = tmp; ix < tmp+x; ix++) {
					pixels[ci] = img.pixels[(iy*(img.width))+ix];
					ci++;
				}
			}
			tiles[t] = new Image(x,y,pixels);
		}
		
		if(dbg) {
			// Print time spent calculating
			long ts2 = (System.nanoTime()-timestamp)/ 1000000;
			
			System.out.println("  # "+ts2+"ms");
		}
		
	}
	
}
