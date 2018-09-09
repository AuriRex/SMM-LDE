package me.auri.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;

import javax.swing.JFrame;
import javax.swing.JPanel;

import me.auri.util.TileBuffer;
import me.auri.util.TileSet;

public class DrawArea extends JPanel implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long timestamp = 0;
	
	private SMMLevel lvl;

	public boolean dbg = false;

	JFrame frame;

	String title = "";

	public final Color BACKGROUND = new Color(200, 200, 255);
	
	private LvlViewer lv = null;
	
	public DrawArea(JFrame frame, SMMLevel lvl, LvlViewer cl) {
		this.frame = frame;
		title = frame.getTitle();
		this.lv = cl;
		this.lvl = lvl;
		setupPanel();
		this.addMouseListener(this);
		setBackground(BACKGROUND);
	}

	private void setupPanel() {
		setPreferredSize(new Dimension(oneBlock*240, oneBlock*27));
		if(lv.scrollPane != null) {
			lv.scrollPane.getViewport().revalidate();
			lv.scrollPane.getViewport().repaint();
			lv.scrollPane.revalidate();
			lv.scrollPane.repaint();
		}
	}
	
	private int notren = 0; // Block ID to not render

	private TileBuffer buffer;
	
	private int oneBlock = 80;
	
	private TileSet ts = null;
	
	private String tmpStr = "";
	
	private boolean renderRect = true;
	public boolean renderCastleBridge = true;
	public boolean enableCulling16 = true;
	
	public void setOneBlock(int one) {
		this.oneBlock = one;
		setupPanel();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		timestamp = System.nanoTime();

		if(ts == null)
			ts = Core.tilesetList.get(0);
		
		if(buffer == null)
			recompileBuffer();
		
		boolean render = true;
		long bblx = 0;
		long bbrx = 0;
		
		if(enableCulling16) {
			bblx = (long) ((lv.scrollPane.getViewport().getViewPosition().getX()/oneBlock)-16.5f) * 160;
			bbrx = (long) (((lv.scrollPane.getViewport().getViewPosition().getX()+lv.scrollPane.getViewport().getWidth())  /oneBlock)+2.5f) * 160;
		}
		
		for (int i = 1; i < 2600; i++) {
			render = true;
			renderRect = true;
			
			Obj_t obj = lvl.getObj(i);

			if(enableCulling16) {
				if(obj.getXcoord()+obj.getWidth()*oneBlock < bblx || obj.getXcoord() > bbrx) {
					System.out.println("C_#16-DontRender#"+i);
					render = false;
				}
			}
			int id = obj.getcObjType();
			boolean[] flags = lvl.getFlags(obj);

			if ((int) obj.getXcoord() == 0) {
				if ((int) obj.getYcoord() == 0) {
					render = false;
				}
			}

			if (obj.getObjType() == notren) {
				render = false;
			}

			int x = (int) (((obj.getXcoord() / 2)  ) / (80.0/oneBlock)) - oneBlock/2;
			int y = (int) ((obj.getYcoord() / 2) / (80.0/oneBlock)) - oneBlock/2;

			int w = obj.getWidth() * oneBlock;
			int h = obj.getHeight() * oneBlock;
			
			if (render) {
				// Render Stuff
				System.out.println("Drawing#" + i + " " + obj.getXcoord() + " - " + obj.getYcoord() + " - " + obj.getObjType());

				
				
				Graphics2D g2 = (Graphics2D) g;

				Insets insets = getInsets();
				
				int he = getHeight() - insets.top - insets.bottom;

				AffineTransform oldAT = g2.getTransform();
				
				try {
					// Move the origin to bottom-left, flip y axis
					// g2 == Inverted on y axis (coords & height)
					g2.scale(1.0, -1.0);
					g2.translate(0, -he - insets.top);

				} finally {
					// restore
					y = flip(y);
					
					g2.setTransform(oldAT);
					String txt = "";
					g2.setColor(Color.WHITE);
					// ################## ENTITIES ##################
					if (obj.getObjType() == SMMObject.SPRING.ordinal()) {
						txt = "Spring";
						g2.drawString(txt, x+w-(txt.length()*7)+ 5, y - 5);
					}
					
					if (obj.getObjType() == SMMObject.POWBLOCK.ordinal()) {
						txt = "Pow";
						g2.drawString(txt, x+w-(txt.length()*8)+ 5, y - 5);
					}
					if (obj.getObjType() == SMMObject.COIN.ordinal()) {
						txt = "Coin";
						g2.drawString(txt, x+w-(txt.length()*7)+ 5, y - 5);
						if(flags[29]) {
							g2.drawImage(buffer.getTile(256) , x, y-oneBlock, oneBlock, oneBlock, null);
						}else
							g2.drawImage(buffer.getTile(7) , x, y-oneBlock, oneBlock, oneBlock, null);
					}
					if (obj.getObjType() == SMMObject.GRINDER.ordinal()) {
						x = x - oneBlock;
						if(flags[29])
							txt = "Bumper";
						else
							txt = "Grinder";
						g2.drawString(txt, x+w-(txt.length()*7)+ 5, y - 5);
					}
					if (obj.getObjType() == SMMObject.ARROWSIGN.ordinal()) {
						x = x - oneBlock/2;
						if(flags[29])
							txt = "Check-Point";
						else
							txt = "Arrow-Sign";
						g2.drawString(txt, x+w-(txt.length()*6)+ 5, y - 5);
					}
					if (obj.getObjType() == SMMObject.ONEWAYGATE.ordinal()) {
						x = x - oneBlock/2;
						
						txt = "One-Way-Gate";
						g2.drawString(txt, x+w-(txt.length()*7)+ 5, y - 5);
					}
					
					// ################## BLOCKS ##################
					if(obj.getcObjType() == SMMObject.GROUNDBLOCK.ordinal()) {						
						g2.drawImage(buffer.getTile((int)((long)184+obj.getExObjData())) , x, y-oneBlock, oneBlock, oneBlock, null);
						renderRect = false;
					}
					if(obj.getcObjType() == SMMObject.BRICKBLOCK.ordinal()) {
						g2.drawImage(buffer.getTile(1) , x, y-oneBlock, oneBlock, oneBlock, null);
						renderRect = false;
					}
					if(obj.getcObjType() == SMMObject.QUESTIONBLOCK.ordinal()) {
						g2.drawImage(buffer.getTile(2) , x, y-oneBlock, oneBlock, oneBlock, null);
						renderRect = false;
					}
					if(obj.getcObjType() == SMMObject.WOODBLOCK.ordinal()) {
						g2.drawImage(buffer.getTile(6) , x, y-oneBlock, oneBlock, oneBlock, null);
						renderRect = false;
					}
					if(obj.getcObjType() == SMMObject.SPIKEBLOCK.ordinal()) {
						g2.drawImage(buffer.getTile(66) , x, y-oneBlock, oneBlock, oneBlock, null);
						//renderRect = false;
					}
					if(obj.getcObjType() == SMMObject.ICEBLOCK.ordinal()) {
						g2.drawImage(buffer.getTile(120) , x, y-oneBlock, oneBlock, oneBlock, null);
						renderRect = false;
					}
					if(obj.getcObjType() == SMMObject.NOTEBLOCK.ordinal()) {
						g2.drawImage(buffer.getTile(4) , x, y-oneBlock, oneBlock, oneBlock, null);
						renderRect = false;
					}if(obj.getcObjType() == SMMObject.DONUTPLATFORM.ordinal()) {
						g2.drawImage(buffer.getTile(54) , x, y-oneBlock, oneBlock, oneBlock, null);
						renderRect = false;
					}
					if(obj.getcObjType() == SMMObject.CLOUDBLOCK.ordinal()) {
						g2.drawImage(buffer.getTile(102) , x, y-oneBlock, oneBlock, oneBlock, null);
						renderRect = false;
					}
					if(obj.getcObjType() == SMMObject.INVISIBLEBLOCK.ordinal()) {
						g2.drawImage(buffer.getTile(3) , x, y-oneBlock, oneBlock, oneBlock, null);
						renderRect = false;
					}
					
					// ################## MULTIBLOCKS ##################
					
					if (obj.getObjType() == SMMObject.PIPE.ordinal()) {
						
						if (flags[26]) {
							// Is Pipe reversed ?
							if (flags[25]) {
								// Normal Pipe (DOWN)
								y = y + h - oneBlock;
								x = x - oneBlock;
								for(int i_ = 0; i_ < h/oneBlock; i_++) {
									if(i_ == 0) {
										g2.drawImage(buffer.getTile(46) , x, y-oneBlock*(i_+1), oneBlock, oneBlock, null);
										g2.drawImage(buffer.getTile(47) , x+oneBlock, y-oneBlock*(i_+1), oneBlock, oneBlock, null);
									} else {
										g2.drawImage(buffer.getTile(30) , x, y-oneBlock*(i_+1), oneBlock, oneBlock, null);
										g2.drawImage(buffer.getTile(31) , x+oneBlock, y-oneBlock*(i_+1), oneBlock, oneBlock, null);
									}
								}
							} else {
								// 90 dg CCW (LEFT)
								int tmp = w;
								w = h;
								h = tmp;
								x = x - w + oneBlock;
								for(int i_ = 0; i_ < w/oneBlock; i_++) {
									if(i_ == 0) {
										g2.drawImage(buffer.getTile(11) , x+oneBlock*(i_), y-oneBlock*2, oneBlock, oneBlock, null);
										g2.drawImage(buffer.getTile(27) , x+oneBlock*(i_), y-oneBlock, oneBlock, oneBlock, null);
									} else {
										g2.drawImage(buffer.getTile(12) , x+oneBlock*(i_), y-oneBlock*2, oneBlock, oneBlock, null);
										g2.drawImage(buffer.getTile(28) , x+oneBlock*(i_), y-oneBlock, oneBlock, oneBlock, null);
									}
								}
							}

						}else {
							
							if (flags[25]) {
								// Normal Pipe (UP)
								for(int i_ = 0; i_ < h/oneBlock; i_++) {
									if(i_ == h/oneBlock - 1) {
										g2.drawImage(buffer.getTile(14) , x, y-oneBlock*(i_+1), oneBlock, oneBlock, null);
										g2.drawImage(buffer.getTile(15) , x+oneBlock, y-oneBlock*(i_+1), oneBlock, oneBlock, null);
									} else {
										g2.drawImage(buffer.getTile(30) , x, y-oneBlock*(i_+1), oneBlock, oneBlock, null);
										g2.drawImage(buffer.getTile(31) , x+oneBlock, y-oneBlock*(i_+1), oneBlock, oneBlock, null);
									}
								}
							}else {
								// 90 dg CW (RIGHT)
								int tmp = w;
								w = h;
								h = tmp;
								y = y + oneBlock;
								
								for(int i_ = 0; i_ < w/oneBlock; i_++) {
									if(i_ == w/oneBlock - 1) {
										g2.drawImage(buffer.getTile(13) , x+oneBlock*(i_), y-oneBlock*2, oneBlock, oneBlock, null);
										g2.drawImage(buffer.getTile(29) , x+oneBlock*(i_), y-oneBlock, oneBlock, oneBlock, null);
									} else {
										g2.drawImage(buffer.getTile(12) , x+oneBlock*(i_), y-oneBlock*2, oneBlock, oneBlock, null);
										g2.drawImage(buffer.getTile(28) , x+oneBlock*(i_), y-oneBlock, oneBlock, oneBlock, null);
									}
								}
								
							}
						}

					}
					
					
					
					if(obj.getcObjType() == SMMObject.EDITSTARTARROWBOARD.ordinal()) {
						x = x - oneBlock;
						y = y - oneBlock/2;
					}
					
					if(obj.getcObjType() == SMMObject.EDITGROUNDSTART.ordinal()) {
						x = x - oneBlock*3;
						
						for(int i_ = 0; i_ < w/oneBlock; i_++) {
							if(i_ == w/oneBlock - 1) {
								g2.drawImage(buffer.getTile(122) , x+oneBlock*i_, y-h, oneBlock, oneBlock, null); // Corner T+R
							}else {
								g2.drawImage(buffer.getTile(121) , x+oneBlock*i_, y-h, oneBlock, oneBlock, null);// Top
							}
						}
						for(int j_ = 1; j_ < (h/oneBlock) ; j_++)
							for(int i_ = 0; i_ < w/oneBlock; i_++) {
								if(i_ == w/oneBlock - 1) {
									g2.drawImage(buffer.getTile(138) , x+oneBlock*i_, y-oneBlock*j_, oneBlock, oneBlock, null); // Corner T+R
								}else {
									g2.drawImage(buffer.getTile(137) , x+oneBlock*i_, y-oneBlock*j_, oneBlock, oneBlock, null);// Top
								}
							}
						
						renderRect = false;
					}
					
					if(obj.getcObjType() == SMMObject.EDITGROUNDGOAL.ordinal()) {
						
						for(int i_ = 0; i_ < w/oneBlock; i_++) {
							if(i_ == 0) {
								g2.drawImage(buffer.getTile(123) , x+oneBlock*i_, y-h, oneBlock, oneBlock, null); // Corner T+R
							}else {
								g2.drawImage(buffer.getTile(124) , x+oneBlock*i_, y-h, oneBlock, oneBlock, null);// Top
							}
						}
						for(int j_ = 1; j_ < (h/oneBlock) ; j_++)
							for(int i_ = 0; i_ < w/oneBlock; i_++) {
								if(i_ == 0) {
									g2.drawImage(buffer.getTile(139) , x+oneBlock*i_, y-oneBlock*j_, oneBlock, oneBlock, null); // Corner T+R
								}else {
									g2.drawImage(buffer.getTile(140) , x+oneBlock*i_, y-oneBlock*j_, oneBlock, oneBlock, null);// Top
								}
							}
						
						renderRect = false;
					}
					
					if(obj.getcObjType() == SMMObject.VINE.ordinal()) {

						for(int i_ = 0; i_ <= (h/w); i_++) {
							if(i_ == 0) {
								g2.drawImage(buffer.getTile(125) , x, y-oneBlock, oneBlock, oneBlock, null); // Bottom
							}else if(i_ == (h/w)) {
								g2.drawImage(buffer.getTile(127) , x, y-(oneBlock*(i_)), oneBlock, oneBlock, null);// Top
							}else if (i_ < (h/w)-1 & i_ > 0){
								g2.drawImage(buffer.getTile(126) , x, y-(oneBlock*(i_+1)), oneBlock, oneBlock, null);// Mid
							}
						}
						
						//renderRect = false;
					}
					if(obj.getcObjType() == SMMObject.CONVEYORBELT.ordinal()) {
						//tile = Core.tilesetList.get(21).tiles[178]; // 224 - tile7  //15-SMW
						for(int i_ = 0; i_ <= (w/h); i_++) {
							if(i_ == 0) {
								g2.drawImage(buffer.getTile(8) , x, y-oneBlock, oneBlock, oneBlock, null); // Left
							}else if(i_ == (w/h)) {
								g2.drawImage(buffer.getTile(10) , x+(oneBlock*(i_-1)), y-oneBlock, oneBlock, oneBlock, null);// Right
							}else if (i_ < (w/h)-1 & i_ > 0){
								g2.drawImage(buffer.getTile(9) , x+(oneBlock*(i_)), y-oneBlock, oneBlock, oneBlock, null);// Mid
							}
						}
						
						//renderRect = false;
					}
					
					if(obj.getcObjType() == SMMObject.EDITCASTLEBRIDGE.ordinal() & renderCastleBridge ) {
						for(int i_ = 0; i_ < (w/oneBlock); i_++) {
							g2.drawImage(buffer.getTile(255) , x+oneBlock*i_, y-oneBlock, oneBlock, oneBlock, null);
						}
						renderRect = false;
					}
					
					// ############################################################################
					
					if(Core.debug) {
						// Paint tileset
						int _x = 0, _y = 0;
						for(Object _tile : buffer.get()) {
							java.awt.Image tile_ = (java.awt.Image) _tile;
							g2.drawImage(tile_,_x*tile_.getWidth(null),_y*tile_.getHeight(null),tile_.getWidth(null),tile_.getHeight(null),null);
							_x++;
							if(_x == 16) {
								_x = 0;
								_y++;
							}
						}
					}
					
					if(renderRect) {
						g.setColor(Color.GRAY);
						drawRect(g, x, y, w, -h);
						g2.setColor(Color.BLACK);
						g2.drawString("" + id, x + 5, y - 5);
						g2.setColor(Color.WHITE);
						g2.drawString("" + id, x + 4, y - 6);
					}
					
				}

			}
			
		}
		
		System.out.println("bblx: "+bblx);
		System.out.println("bbrx: "+bbrx);
		
		if (dbg) {
			// Debug Grid TODO re-purpose debug Grid To Screen Grid
			g.setColor(Color.RED);
			for (int i = 0; i < 2180 / 8; i++) {

				g.drawLine(i * 80, 0, i * 80, 2180);

			}

			for (int i = 0; i < 19200 / 8; i++) {

				g.drawLine(0, i * 80, 19200, i * 80);

			}
		}

		// g.drawString("BLAH", 20, 20);
		// g.drawRect(200, 200, 200, 200);
		System.out.println("DrawTime: "+((System.nanoTime()-timestamp)/1000000)+"ms");
	}

	public void setTileSet(TileSet tise) {
		if(tise == null)
			return;
		this.ts = tise;
		recompileBuffer();
	}
	
	private void recompileBuffer() {
		tmpStr = frame.getTitle();
		frame.setTitle("Resizeing Images...");
		buffer = new TileBuffer(ts, ts.tiles[0].width,ts.tiles[0].height);
		frame.setTitle(tmpStr);
	}

	protected void doNotRender(int id) {
		this.notren = id;
	}

	public void fillRect(Graphics gc, int x, int y, int width, int height) {
		if (width < 0) {
			x += width;
			width = -width;
		}
		if (height < 0) {
			y += height;
			height = -height;
		}
		gc.fillRect(x, y, width, height);
	}

	public void drawRect(Graphics gc, int x, int y, int width, int height) {
		if (width < 0) {
			x += width;
			width = -width;
		}
		if (height < 0) {
			y += height;
			height = -height;
		}
		gc.drawRect(x, y, width, height);
	}

	private int flip(int Y) {

		int max = (oneBlock*27) - oneBlock/2;

		int tmp = max / 2;

		Y = ((Y - tmp) * (-1)) + tmp;

		return Y + oneBlock/2;

	}

	/*
	private int unflip(int Y) {

		int max = (oneBlock*27);

		int tmp = max / 2;

		Y = tmp + (Y - (int) (max * 1.5f));

		return Y;

	}*/

	/*
	private int getCoord(int c) {

		//c = c / 2;

		int tmp = c % oneBlock;

		c = c - tmp;

		return c;

	}*/

	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}
	
	@Override
	public void mouseReleased(MouseEvent e) {

		/*
		int x = getCoord((e.getX() + oneBlock));
		int y = neg(getCoord(-flip(e.getY() - oneBlock)));
		
		int x_ = (int) (((x)  ) * (80.0/oneBlock));
		int y_ = (int) ((y) * (80.0/oneBlock));

		System.out.println("Raw: X:" + e.getX() + " - Y:" + e.getY());

		System.out.println(x_ + " - " + y_);
		frame.setTitle(title + " - " + x + " - " + y);
		*/
		
		//Rectangle r = getBounds();
		//Rectangle rs = lv.scrollPane.getViewport().getBounds();

		//System.out.println();
		//System.out.println(lv.scrollPane.getViewport().getViewPosition().getY());
		//System.out.println("X: "+r.getX()+" - "+(r.getX()+rs.getWidth()));
		//System.out.println("Y: "+r.getY()+" - "+(r.getY()+rs.getHeight()));
		
		//x = x / 2;
		//y = y / 2;
		
		//System.out.println(x + " - " + y);

		//Obj_t obj = lvl.getByCoord(x_, y_);

		//if (obj == null)
		//	return;

		//System.out.println("OBJECT: "+obj.getcObjType());

	}

}
