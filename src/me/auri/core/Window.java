package me.auri.core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import me.auri.util.ReStore;

public class Window implements ActionListener, DropTargetListener, InternalFrameListener, TreeSelectionListener {

	private JFrame frame;

	private JDesktopPane deskPane;
	private DropTarget tg;

	// Menu
	private JMenuBar topMenu;
	// Menus
	private JMenu topMenuFile;
	private JMenu topMenuWindow;

	// MenuSubItems
	// - File
	private JMenuItem topMenuFileOpen;
	private JMenuItem topMenuFileExit;
	// - Window
	private JMenuItem topMenuWindowResetIF;

	// Variables
	private boolean unsaved = false;
	private Map<JInternalFrameX, Integer> IFLookup = new HashMap<JInternalFrameX, Integer>();
	private Map<Integer, JInternalFrameX> INLookup = new HashMap<Integer, JInternalFrameX>();
	
	JInternalFrame iFrame_About;
	
	public Window() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		try {
	         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	         UIManager.put("InternalFrame.activeTitleForeground", Color.BLACK);
	         UIManager.put("InternalFrame.inactiveTitleForeground", Color.DARK_GRAY);
	      }
	      catch(Exception e) {
	         e.printStackTrace();
	      }

		frame = new JFrame("SMM Level Data Editor");

		frame.setIconImage(Core.marioHead.getImage());
		
		frame.setSize(720, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout());

		// MENU - Create Objects
		topMenu = new JMenuBar();
		topMenuFile = new JMenu("File");
		topMenuWindow = new JMenu("Debug");
		topMenuFileOpen = new JMenuItem("Open");
		topMenuFileExit = new JMenuItem("Exit");
		topMenuWindowResetIF = new JMenuItem("Reset all Windows");

		// MENU - Add Sub Objects
		topMenuFile.add(topMenuFileOpen);
		topMenuFile.addSeparator();
		topMenuFile.add(topMenuFileExit);

		topMenuWindow.add(topMenuWindowResetIF);

		// MENU - Add Objects
		topMenu.add(topMenuFile);
		topMenu.add(topMenuWindow);

		// DESKPANE
		deskPane = new JDesktopPane();
		tg = new DropTarget(deskPane, DnDConstants.ACTION_COPY, this);
		deskPane.setBackground(Color.LIGHT_GRAY);
		deskPane.setDropTarget(tg);

		iFrame_About = new JInternalFrame("About", true, true, true, true);
		iFrame_About.setFrameIcon(Core.hand);
		iFrame_About.setLayout(new BorderLayout());
		
		JTextArea text = new JTextArea("\nSuper Mario Maker Level Data Editor\nAlpha Version!\nmade by AuriRex\nPlease Report bugs to me @Mario Making Mods Discord\n\nTo Save a Level: Click on the Tools Node on the Left Side and click [Save As]\n\nIt is recommended to make a Backup of your Levels (dont accidentally overwrite them!)\n\nSpecial Thanks to: https://github.com/Treeki/MarioUnmaker/blob/master/FormatNotes.md");
		text.setEditable(false);
		
		iFrame_About.add(text, BorderLayout.CENTER);
		
		deskPane.add(iFrame_About);
		iFrame_About.setSize(200, 200);
		iFrame_About.show();

		try {
			iFrame_About.setMaximum(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}

		// FRAME - Final
		frame.add(topMenu, BorderLayout.NORTH);
		frame.add(deskPane, BorderLayout.CENTER);

		// ACTION LISTENERS
		topMenuFileOpen.addActionListener(this);
		topMenuFileExit.addActionListener(this);
		topMenuWindowResetIF.addActionListener(this);

		// Set Frame Visible
		frame.setVisible(true);
		
		//new LvlViewer(this.frame, "Level Viewer: ", null);

	}

	public JInternalFrameX addIFrame(String title, SMMLevel level, int key) {
		JInternalFrameX iFrame = new JInternalFrameX(title, this, this, key, Core.levels.get(key));

		deskPane.add(iFrame);
		
		INLookup.put(key, iFrame);
		
		return iFrame;
		/*
		 * try { iFrame.setMaximum(true); } catch (PropertyVetoException e) {
		 * e.printStackTrace(); }
		 */
	}

	private void resetIFrames() {
		JInternalFrame[] frames = deskPane.getAllFrames();

		for (int i = 0; i < frames.length; i++) {
			frames[i].setLocation(0, 0);
		}
	}

	public static final String chooserPropPath = "assets/config/chooser.prop";
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(topMenuFileExit)) {

			if (unsaved) {
				System.out.println("Unfinished! - Unsaved changes, not exiting...");
			} else {
				System.exit(0);
			}

		}
		if (e.getSource().equals(topMenuFileOpen)) {
			JFileChooser chooser = new JFileChooser();
			
			try {
				Object[] o = ReStore.restoreOptions(chooserPropPath);
				Rectangle r = (Rectangle) o[0];
				if(r != null) {
					Dimension dim = chooser.getSize();
					dim.setSize(r.getWidth(),r.getHeight());
					chooser.setPreferredSize(dim);
					if(o[1] != null)
						chooser.setCurrentDirectory(new File((String) o[1]));
				}
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Mario Maker Level Format (.cdt)", "cdt");
			chooser.setFileFilter(filter);

			int ret = chooser.showOpenDialog(frame);

			String path = "";

			if (ret == JFileChooser.APPROVE_OPTION) {
				System.out.println("Opened a file.");
				File level = chooser.getSelectedFile();

				if (level.exists()) {

					if (level.isFile()) {

						path = level.getAbsolutePath();
						try {
							Rectangle r = chooser.getBounds();
							r.setLocation(chooser.getLocation());
							ReStore.storeOptions(r, chooserPropPath, chooser.getCurrentDirectory().getAbsolutePath());
						} catch (Exception e1) {
							System.out.println("Could not save Properties.");
							e1.printStackTrace();
						}
						chooser = null;

					} else {
						System.out.println("File is no File?!");
						return;
					}

				} else {
					System.out.println("File does not exist!");
					return;
				}

			} else if (ret == JFileChooser.CANCEL_OPTION) {
				System.out.println("Canceled Open!");
				return;
			} else {
				return;
			}
			
			if(iFrame_About != null)
				iFrame_About.dispose();
			
			// Load Level & Create internal Frame
			byte[] data = null;
			try {
				data = Core.loadFile(path);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			SMMLevel lvl = new SMMLevel(data);
			//lvl.loadObjects(lvl.getData());
			
			int key = Core.addLevel(0, lvl);
			
			IFLookup.put(addIFrame(lvl.title, lvl, key), key);
			
			return;
		}
		
		if (e.getSource().equals(topMenuWindowResetIF)) {
			resetIFrames();
			return;
		}
	}

	@Override
	public void dragEnter(DropTargetDragEvent e) {
		deskPane.setBackground(Color.GREEN);
	}

	@Override
	public void dragExit(DropTargetEvent e) {
		deskPane.setBackground(Color.LIGHT_GRAY);
	}

	@Override
	public void dragOver(DropTargetDragEvent e) {

	}

	@Override
	public void drop(DropTargetDropEvent e) {
		System.out.println("Droped!");
		deskPane.setBackground(Color.LIGHT_GRAY);
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent e) {

	}

	// Internal frame Listeners
	
	@Override
	public void internalFrameActivated(InternalFrameEvent arg0) {
		
	}

	@Override
	public void internalFrameClosed(InternalFrameEvent e) {
		
	
	}

	@Override
	public void internalFrameClosing(InternalFrameEvent e) {
		//IFLookup.values();
		JInternalFrame iFrame = e.getInternalFrame();
		int key = IFLookup.get(iFrame);
		IFLookup.remove(iFrame);
		Core.levels.remove(key);
		INLookup.remove(key);
		iFrame.dispose();
		System.out.println("IFrame Closed!");
		
	}

	@Override
	public void internalFrameDeactivated(InternalFrameEvent arg0) {
		
	}

	@Override
	public void internalFrameDeiconified(InternalFrameEvent arg0) {
		
	}

	@Override
	public void internalFrameIconified(InternalFrameEvent arg0) {
		
	}

	@Override
	public void internalFrameOpened(InternalFrameEvent arg0) {
		
	}

	// Tree Selection Listener
	
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		System.out.println(e.getPath().toString());
		Object[] path = e.getPath().getPath();
		
		int key = Integer.parseInt(path[0].toString());
		
		JInternalFrameX iFrame = INLookup.get(key);
		SMMLevel level = Core.levels.get(key);
		
		if(path.length>2) {
			if(path[1].toString().equals("Objects")) {
				
				int objNum = Integer.parseInt(path[2].toString().substring(7));
				
				Obj_t obj = level.getObj(objNum);
				
				//System.out.println("X: "+obj.getXcoord());
				//System.out.println("Y: "+obj.getYcoord());
				
				iFrame.showObj(obj, objNum);
				
			}else if(path[1].toString().equals("Effects")) {

				int efxNum = Integer.parseInt(path[2].toString().substring(7));
				
				Effect_t efx = level.getEffect(efxNum);
				
				iFrame.showEffect(efx, efxNum);
				
			}
		}else if(path.length > 1){
			
			if(path[1].toString().equals("Level")) {
				
				iFrame.showGeneral();
				
			}
			if(path[1].toString().equals("Tools")) {
				
				iFrame.showTools();
				
			}
			
		}
		
	}

}
