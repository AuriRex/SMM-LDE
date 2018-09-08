package me.auri.core;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;

public class JInternalFrameX extends JInternalFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel switcher;
	
	// Tools
	private JPanel tools;
	private JButton saveFile;
	private JButton viewMap;
	private JButton exportOBJ;
	private JButton importOBJ;
	
	// Level
	private JPanel general;
	
	private JLabel Ltitle;
	private JLabel Lyear;
	private JLabel Lmonth;
	private JLabel Lday;
	private JLabel Lhour;
	private JLabel Lminute;
	
	private JLabel Lgamemode;
	private JLabel Ltheme;
	private JLabel Lautoscroll;
	private JLabel Ltimelimit;
	
	private JTextField Ttitle;
	private JTextField Tyear;
	private JTextField Tmonth;
	private JTextField Tday;
	private JTextField Thour;
	private JTextField Tminute;
	
	private JComboBox<String> Tgamemode;
	private JComboBox<String> Ttheme;
	private JComboBox<String> Tautoscroll;
	private JTextField Ttimelimit;
	
	// OBJ
	private JPanel info;
	
	private JLabel L1;
	private JLabel L2;
	private JLabel L3;
	private JLabel L4;
	private JLabel L5;
	private JLabel L6;
	private JLabel L7;
	private JLabel L8;
	private JLabel L9;
	private JLabel L10;
	private JLabel L11;
	private JLabel L12;
	private JLabel L13;
	private JLabel L14;
	
	private JTextField T1;
	private JTextField T2;
	private JTextField T3;
	private JTextField T4;
	private JTextField T5;
	private JTextField T6;
	private JTextField T7;
	private JTextField T8;
	private JTextField T9;
	private JTextField T10;
	private JTextField T11;
	private JTextField T12;
	private JTextField T13;
	private JTextField T14;
	
	// EFECTS
	private JPanel efx;
	
	private JLabel Le1;
	private JLabel Le2;
	private JLabel Le3;
	private JLabel Le4;
	private JLabel Le5;
	
	private JTextField Te1;
	private JTextField Te2;
	private JTextField Te3;
	private JTextField Te4;
	private JTextField Te5;
	
	
	JLabel saveGL;
	JButton saveGeneral;
	
	JLabel saveOBJL;
	JButton saveOBJ;
	
	JLabel saveEFXL;
	JButton saveEFX;
	
	private final int key;
	
	private int curOBJ = 0;
	private int curEFX = 0;
	
	public JInternalFrameX(String title, InternalFrameListener ifls, TreeSelectionListener tsl, int key, SMMLevel level) {
		super(title, true, true, true, true);
		
		this.key = key;
		
		setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
		addInternalFrameListener(ifls);
		setSize(300, 300);
		setFrameIcon(Core.marioHead);

		tools = new JPanel();
		saveFile = new JButton("Save As");
		tools.add(saveFile);
		viewMap = new JButton("View Level");
		tools.add(viewMap);
		exportOBJ = new JButton("Export Objects");
		tools.add(exportOBJ);
		importOBJ = new JButton("Import Objects");
		tools.add(importOBJ);
		
		saveGL = new JLabel("Save General Settings");
		saveGeneral = new JButton("Save");
		
		saveOBJL = new JLabel("Save this Object");
		saveOBJ = new JButton("Save");
		
		saveEFXL = new JLabel("Save this Effect");
		saveEFX = new JButton("Save");
		
		saveFile.addActionListener(this);
		viewMap.addActionListener(this);
		exportOBJ.addActionListener(this);
		importOBJ.addActionListener(this);
		
		saveGeneral.addActionListener(this);
		saveOBJ.addActionListener(this);
		saveEFX.addActionListener(this);
		
		JPanel all = new JPanel();
		all.setLayout(new BorderLayout());
		
		JPanel top = new JPanel();
		top.setLayout(new BorderLayout());
		
		switcher = new JPanel();
		switcher.setLayout(new CardLayout());
		
		general = new JPanel();
		general.setLayout(new GridLayout(11,2));
		
		Ltitle = new JLabel("Title");
		Lyear = new JLabel("Year");
		Lmonth = new JLabel("Month");
		Lday = new JLabel("Day");
		Lhour = new JLabel("Hour");
		Lminute = new JLabel("Minute");
		
		Lgamemode = new JLabel("Gamemode");
		Ltheme = new JLabel("Theme");
		Lautoscroll = new JLabel("Autoscroll");
		Ltimelimit = new JLabel("Timelimit");
		
		Ttitle = new JTextField();
		Ttitle.setDocument(new JTextFieldLimit(32));
		Ttitle.setText(level.title);
		Tyear = new JTextField(level.year+"");
		Tmonth = new JTextField(level.month+"");
		Tday = new JTextField(level.day+"");
		Thour = new JTextField(level.hour+"");
		Tminute = new JTextField(level.minute+"");
		
		String gamemodeItems[] = { SMMLevel.GAMEMODE_M1, SMMLevel.GAMEMODE_M3, SMMLevel.GAMEMODE_MW, SMMLevel.GAMEMODE_WU };
		String themeItems[] = { SMMLevel.THEME_OVERWORLD, SMMLevel.THEME_UNDERGROUND, SMMLevel.THEME_CASTLE, SMMLevel.THEME_AIRSHIP, SMMLevel.THEME_WATER, SMMLevel.THEME_GHOST };
		String autoscrollItems[] = { SMMLevel.AUTOSCROLL_NONE, SMMLevel.AUTOSCROLL_SLOW, SMMLevel.AUTOSCROLL_MEDIUM, SMMLevel.AUTOSCROLL_FAST };
		
		Tgamemode = new JComboBox<String>(gamemodeItems);
		Tgamemode.setSelectedItem(SMMLevel.convertGamemode(level.gamemode));
		Ttheme = new JComboBox<String>(themeItems);
		Ttheme.setSelectedItem(SMMLevel.convertTheme(level.theme));
		Tautoscroll = new JComboBox<String>(autoscrollItems);
		Tautoscroll.setSelectedItem(SMMLevel.convertAutoscroll(level.autoscroll));
		Ttimelimit = new JTextField(level.timelimit+"");
		
		general.add(saveGL);
		general.add(saveGeneral);
		
		general.add(Ltitle);
		general.add(Ttitle);
		
		general.add(Lyear);
		general.add(Tyear);
		
		general.add(Lmonth);
		general.add(Tmonth);

		general.add(Lday);
		general.add(Tday);

		general.add(Lhour);
		general.add(Thour);

		general.add(Lminute);
		general.add(Tminute);

		general.add(Lgamemode);
		general.add(Tgamemode);

		general.add(Ltheme);
		general.add(Ttheme);

		general.add(Lautoscroll);
		general.add(Tautoscroll);

		general.add(Ltimelimit);
		general.add(Ttimelimit);
		
		// OBJ
		info = new JPanel();
		info.setLayout(new GridLayout(15,2));
		
		L1 = new JLabel("X Coord");
		L2 = new JLabel("Z Coord");
		L3 = new JLabel("Y Coord");
		L4 = new JLabel("Width");
		L5 = new JLabel("Height");
		L6 = new JLabel("Object Flags");
		L7 = new JLabel("Child Object Flags");
		L8 = new JLabel("Extended Object Data");
		L9 = new JLabel("Object Type");
		L10 = new JLabel("Child Object Type");
		L11 = new JLabel("Link ID");
		L12 = new JLabel("Effect Index");
		L13 = new JLabel("Object Transformation");
		L14 = new JLabel("Child Object Transformation");
		
		T1 = new JTextField();
		T2 = new JTextField();
		T3 = new JTextField();
		T4 = new JTextField();
		T5 = new JTextField();
		T6 = new JTextField();
		T6 = new JTextField();
		T7 = new JTextField();
		T8 = new JTextField();
		T9 = new JTextField();
		T10 = new JTextField();
		T11 = new JTextField();
		T12 = new JTextField();
		T13 = new JTextField();
		T14 = new JTextField();
		
		//T1.setDocument(new JTextFieldLimit(16));
		//T2.setDocument(new JTextFieldLimit(16));
		//T3.setDocument(new JTextFieldLimit(16));
		//T4.setDocument(new JTextFieldLimit(16));
		//T5.setDocument(new JTextFieldLimit(16));
		T6.setDocument(new JTextFieldLimitBin(32));
		T7.setDocument(new JTextFieldLimitBin(32));
		T8.setDocument(new JTextFieldLimitBin(32));
		T9.setDocument(new JTextFieldLimitBin(8));
		T10.setDocument(new JTextFieldLimitBin(8));
		T11.setDocument(new JTextFieldLimitBin(16));
		T12.setDocument(new JTextFieldLimitBin(16));
		T13.setDocument(new JTextFieldLimitBin(8));
		T14.setDocument(new JTextFieldLimitBin(8));
		
		info.add(saveOBJL);
		info.add(saveOBJ);
		
		info.add(L1);
		info.add(T1);
		
		info.add(L2);
		info.add(T2);
		
		info.add(L3);
		info.add(T3);
		
		info.add(L4);
		info.add(T4);
		
		info.add(L5);
		info.add(T5);
		
		info.add(L6);
		info.add(T6);
		
		info.add(L7);
		info.add(T7);
		
		info.add(L8);
		info.add(T8);
		
		info.add(L9);
		info.add(T9);
		
		info.add(L10);
		info.add(T10);
		
		info.add(L11);
		info.add(T11);
		
		info.add(L12);
		info.add(T12);
		
		info.add(L13);
		info.add(T13);
		
		info.add(L14);
		info.add(T14);
		
		efx = new JPanel();
		efx.setLayout(new GridLayout(6,2));
		
		Le1 = new JLabel("Unknown 1");
		Le2 = new JLabel("Unknown 2");
		Le3 = new JLabel("Unknown 3");
		Le4 = new JLabel("Unknown 4");
		Le5 = new JLabel("Unknown 5");
		
		Te1 = new JTextField();
		Te2 = new JTextField();
		Te3 = new JTextField();
		Te4 = new JTextField();
		Te5 = new JTextField();
		
		Te1.setDocument(new JTextFieldLimitBin(8));
		Te2.setDocument(new JTextFieldLimitBin(8));
		Te3.setDocument(new JTextFieldLimitBin(8));
		Te4.setDocument(new JTextFieldLimitBin(8));
		Te5.setDocument(new JTextFieldLimitBin(8));
		
		efx.add(saveEFXL);
		efx.add(saveEFX);
		
		efx.add(Le1);
		efx.add(Te1);
		
		efx.add(Le2);
		efx.add(Te2);
		
		efx.add(Le3);
		efx.add(Te3);
		
		efx.add(Le4);
		efx.add(Te4);
		
		efx.add(Le5);
		efx.add(Te5);
		
		JPanel panel = new JPanel(new BorderLayout());
		JPanel treeMenu = new JPanel(new BorderLayout());
		
		JScrollPane scrollPane = new JScrollPane (treeMenu, 
	            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
	            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		
		switcher.add(general, "lvl");
		switcher.add(tools, "tls");
		switcher.add(info, "obj");
		switcher.add(efx, "efx");
		
		top.add(switcher, BorderLayout.CENTER);
		all.add(top,BorderLayout.NORTH);
		panel.add(all, BorderLayout.CENTER);
		
		//create the root node
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(""+key);
        //create the child nodes
        DefaultMutableTreeNode toolsNode = new DefaultMutableTreeNode("Tools");
        DefaultMutableTreeNode coreNode = new DefaultMutableTreeNode("Level");
        DefaultMutableTreeNode objNode = new DefaultMutableTreeNode("Objects");
        DefaultMutableTreeNode effectNode = new DefaultMutableTreeNode("Effects");
        
        
        //add the child nodes to the root node
        
        root.add(toolsNode);
        root.add(coreNode);
        root.add(objNode);
        root.add(effectNode);
        
        
        
        //create the tree by passing in the root node
        JTree tree = new JTree(root);
        
        tree.setBorder(new EmptyBorder( 3, 3, 3, 50 ));
        tree.setShowsRootHandles(true);
        tree.setRootVisible(false);
		
        tree.addTreeSelectionListener(tsl);
        
        treeMenu.add(tree, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.WEST);
        
		for(int i = 0; i < 2600; i++) {
			
			DefaultMutableTreeNode obj = new DefaultMutableTreeNode("Object#"+i);
			
			//obj.add(new DefaultMutableTreeNode("Test"));
			
			objNode.add(obj);
			
			//panel.add(new JLabel("Object#"+i+"Type:"));
			//JTextField txt = new JTextField();
			//txt.setDocument(new JTextFieldLimit(8));
			//txt.setText(level.getObj(i).getcObjType()+"");
			//tree.add(comp)
			//panel.add(txt);
			
		}
		
		for(int i = 0; i < 300; i++) {
			
			DefaultMutableTreeNode efx = new DefaultMutableTreeNode("Effect#"+i);
			
			effectNode.add(efx);
			
		}
		
		add(panel);
		
		show();
		
	}
	
	public void chnageValues() {
		
	}

	public void showGeneral() {
		
		CardLayout cl = (CardLayout)(switcher.getLayout());
	    cl.show(switcher, "lvl");
		
	}
	
	public void showObj(Obj_t obj, int objID) {
		
		curOBJ = objID;
		
		T1.setText(obj.getXcoord()+"");
		T2.setText(obj.getZcoord()+"");
		T3.setText(obj.getYcoord()+"");
		T4.setText(obj.getWidth()+"");
		T5.setText(obj.getHeight()+"");
		
		T6.setText(String.format("%32s", Long.toBinaryString(obj.getObjFlags() & 0xFFFFFFFF)).replace(' ', '0'));
		T7.setText(String.format("%32s", Long.toBinaryString(obj.getcObjFlags() & 0xFFFFFFFF)).replace(' ', '0'));
		T8.setText(String.format("%32s", Long.toBinaryString(obj.getExObjData() & 0xFFFFFFFF)).replace(' ', '0'));
		
		T9.setText(String.format("%8s", Integer.toBinaryString(obj.getObjType() & 0xFF)).replace(' ', '0'));
		T10.setText(String.format("%8s", Integer.toBinaryString(obj.getcObjType() & 0xFF)).replace(' ', '0'));
		
		T11.setText(String.format("%16s", Integer.toBinaryString(obj.getLinkID() & 0xFFFF)).replace(' ', '0'));
		T12.setText(String.format("%16s", Integer.toBinaryString(obj.getEfIndx() & 0xFFFF)).replace(' ', '0'));
		
		T13.setText(String.format("%8s", Integer.toBinaryString(obj.getUnknw_01() & 0xFF)).replace(' ', '0'));
		T14.setText(String.format("%8s", Integer.toBinaryString(obj.getcObjTransformID() & 0xFF)).replace(' ', '0'));
		
		CardLayout cl = (CardLayout)(switcher.getLayout());
	    cl.show(switcher, "obj");
		
	}
	
	public void showEffect(Effect_t efx, int efxID) {
		
		curEFX = efxID;
		
		Te1.setText(String.format("%8s", Integer.toBinaryString(efx.getUnknw_01() & 0xFF)).replace(' ', '0'));
		Te2.setText(String.format("%8s", Integer.toBinaryString(efx.getUnknw_02() & 0xFF)).replace(' ', '0'));
		Te3.setText(String.format("%8s", Integer.toBinaryString(efx.getUnknw_03() & 0xFF)).replace(' ', '0'));
		Te4.setText(String.format("%8s", Integer.toBinaryString(efx.getUnknw_04() & 0xFF)).replace(' ', '0'));
		Te5.setText(String.format("%8s", Integer.toBinaryString(efx.getUnknw_05() & 0xFF)).replace(' ', '0'));
		
		CardLayout cl = (CardLayout)(switcher.getLayout());
	    cl.show(switcher, "efx");
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(saveGeneral)) {
			System.out.println(this+" saveGeneral");
			
			SMMLevel lvl = Core.levels.get(key);
			
			lvl.year = Integer.parseInt(Tyear.getText());
			if(lvl.year > 65534) {
				lvl.year = 65535;
			}
			lvl.month = Short.parseShort(Tmonth.getText());
			if(lvl.month > 254) {
				lvl.month = 255;
			}
			lvl.day = Short.parseShort(Tday.getText());
			if(lvl.day > 254) {
				lvl.day = 255;
			}
			lvl.hour = Short.parseShort(Thour.getText());
			if(lvl.hour > 254) {
				lvl.hour = 255;
			}
			lvl.minute = Short.parseShort(Tminute.getText());
			if(lvl.minute > 254) {
				lvl.minute = 255;
			}
			
			lvl.title = Ttitle.getText();
			
			lvl.gamemode = SMMLevel.convertGamemode(Tgamemode.getSelectedItem().toString());
			lvl.theme = SMMLevel.convertTheme(Ttheme.getSelectedItem().toString());
			lvl.autoscroll = SMMLevel.convertAutoscroll(Tautoscroll.getSelectedItem().toString());
			lvl.timelimit = Integer.parseInt(Ttimelimit.getText());
			if(lvl.timelimit > 65534) {
				lvl.timelimit = 65535;
			}
			
			System.out.println("Saved...");
			
			return;
		}
		if(e.getSource().equals(saveOBJ)) {
			System.out.println(this+" saveOBJ");
			
			SMMLevel lvl = Core.levels.get(key);
			
			Obj_t obj = lvl.getObj(curOBJ);
			
			obj.setXcoord((int) Long.parseLong(T1.getText()));
			obj.setZcoord((int) Long.parseLong(T2.getText()));
			obj.setYcoord((short) Integer.parseInt(T3.getText()));
			obj.setWidth((byte) Short.parseShort(T4.getText()));
			obj.setHeight((byte) Short.parseShort(T5.getText()));
			
			obj.setObjFlags(Long.parseLong(T6.getText(), 2));
			obj.setcObjFlags(Long.parseLong(T7.getText(), 2));
			obj.setExObjData(Long.parseLong(T8.getText(), 2));
			
			obj.setObjType((byte) Short.parseShort(T9.getText(), 2));
			obj.setcObjType((byte) Short.parseShort(T10.getText(), 2));
			
			obj.setLinkID((short) Integer.parseInt(T11.getText(), 2));
			obj.setEfIndx((short) Integer.parseInt(T12.getText(), 2));
			
			obj.setUnknw_01((byte) Short.parseShort(T13.getText(), 2));
			obj.setcObjTransformID((byte) Short.parseShort(T14.getText(), 2));
			
			System.out.println("Saved...");
			
			return;
		}
		if(e.getSource().equals(saveEFX)) {
			System.out.println(this+" saveEFX");
			
			SMMLevel lvl = Core.levels.get(key);
			
			Effect_t efx = lvl.getEffect(curEFX);
			
			efx.setUnknw_01((short) Integer.parseInt(Te1.getText(), 2));
			efx.setUnknw_02((short) Integer.parseInt(Te2.getText(), 2));
			efx.setUnknw_03((short) Integer.parseInt(Te3.getText(), 2));
			efx.setUnknw_04((short) Integer.parseInt(Te4.getText(), 2));
			efx.setUnknw_05((short) Integer.parseInt(Te5.getText(), 2));
			
			System.out.println("Saved...");
			
			return;
		}
		
		if(e.getSource().equals(saveFile)) {
			
			SMMLevel lvl = Core.levels.get(key);
			
			byte[] data = lvl.saveAll(lvl.getData());
			
			save(lvl, data);
			
		}
		
		if(e.getSource().equals(viewMap)) {
			
			viewMap.setEnabled(false);
			new LvlViewer(this, "Level Viewer: "+this.getTitle(), Core.levels.get(key));
			
		}//exportOBJ
		
		if(e.getSource().equals(exportOBJ)) {
			
			// TODO
			
			exportOBJ(Core.levels.get(key));
			
		}
		if(e.getSource().equals(importOBJ)) {
			
			// TODO
			importOBJ(Core.levels.get(key));
			
			
		}
		
	}
	
	private void importOBJ(SMMLevel lvl) {
		
		JFileChooser chooser = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter("Mario Maker Level Objects (.cdto)", "cdto");
		chooser.setFileFilter(filter);

		//System.out.println(this.getParent().getParent().getParent());
		
		int ret = chooser.showOpenDialog(this.getParent().getParent().getParent());
		
		String path = "";
		
		if (ret == JFileChooser.APPROVE_OPTION) {
			System.out.println("File has been chosen.");
			File cdto = chooser.getSelectedFile();
			path = cdto.getAbsolutePath();
			
			int op = JOptionPane.showOptionDialog(chooser, "Are you sure that you want to import the Objects: \""+cdto.getName()+"\" ?", "Object Import", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.QUESTION_MESSAGE);
			
			if(op == 0) {
				
			}else {
				return;
			}
			
			/*File level = chooser.getSelectedFile();

			if(!level.getAbsolutePath().endsWith(".cdt")) {
				
				level = new File(level.getAbsolutePath()+".cdt");
				
			}
			
			if (!level.exists()) {
				
				path = level.getAbsolutePath();
				return;

			} else {
				
				int op = JOptionPane.showOptionDialog(chooser, "The File \""+level.getName()+"\" does already exist!\nDo you want to replace this File?", "Replace File?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.QUESTION_MESSAGE);
				
				if(op == 0) {
					
				}else {
					return;
				}
				
				path = level.getAbsolutePath();
				
				System.out.println("Overwriting File");
				
			}*/

		} else if (ret == JFileChooser.CANCEL_OPTION) {
			System.out.println("Canceled Import!");
			return;
		} else {
			return;
		}
		
		byte[] data = null;
		try {
			data = Core.loadFile(path);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		int offset = 0;
		
		for(int i = 0; i < 2600; i++) {
			
			Obj_t obj = lvl.getObj(i);
			
			System.out.println("Importing ... #"+i);
			
			obj.setXcoord((int) Long.parseLong(Core._u32_(offset+0, data),2));
			obj.setZcoord((int) Long.parseLong(Core._u32_(offset+4, data),2));
			obj.setYcoord((short) Integer.parseInt(Core._u16_(offset+8, data),2));
			obj.setWidth((byte) Short.parseShort(Core._u8_(offset+10, data),2));
			obj.setHeight((byte) Short.parseShort(Core._u8_(offset+11, data),2));
			
			obj.setObjFlags(Long.parseLong(Core._u32_(offset+12, data), 2));
			obj.setcObjFlags(Long.parseLong(Core._u32_(offset+16, data), 2));
			obj.setExObjData(Long.parseLong(Core._u32_(offset+20, data), 2));
			
			obj.setObjType((byte) Short.parseShort(Core._u8_(offset+24, data), 2));
			obj.setcObjType((byte) Short.parseShort(Core._u8_(offset+25, data), 2));
			
			obj.setLinkID((short) Integer.parseInt(Core._u16_(offset+26, data), 2));
			obj.setEfIndx((short) Integer.parseInt(Core._u16_(offset+28, data), 2));
			
			obj.setUnknw_01((byte) Short.parseShort(Core._u8_(offset+30, data), 2));
			obj.setcObjTransformID((byte) Short.parseShort(Core._u8_(offset+31, data), 2));
			
			offset = offset + Obj_t.TOTAL_OFFSET;
		}
		
		
	}
	
	private void exportOBJ(SMMLevel lvl) {
		byte[] data = new byte[2600*32];
		
		int offset = 0;
		
		for(int i = 0; i < 2600; i++) {
			
			Obj_t obj = lvl.getObj(i);
			
			Core.write32(offset+0, String.format("%32s", Long.toBinaryString(obj.getXcoord() & 0xFFFFFFFF)).replace(' ', '0'),data);
			Core.write32(offset+4, String.format("%32s", Long.toBinaryString(obj.getZcoord() & 0xFFFFFFFF)).replace(' ', '0'),data);
			
			Core.write16(offset+8, String.format("%16s", Long.toBinaryString(obj.getYcoord() & 0xFFFF)).replace(' ', '0'), data);
			Core.write8(offset+10, String.format("%8s", Long.toBinaryString(obj.getWidth() & 0xFF)).replace(' ', '0'), data);
			Core.write8(offset+11, String.format("%8s", Long.toBinaryString(obj.getHeight() & 0xFF)).replace(' ', '0'), data);
			
			Core.write32(offset+12, String.format("%32s", Long.toBinaryString(obj.getObjFlags() & 0xFFFFFFFF)).replace(' ', '0'),data);
			Core.write32(offset+16, String.format("%32s", Long.toBinaryString(obj.getcObjFlags() & 0xFFFFFFFF)).replace(' ', '0'),data);
			Core.write32(offset+20, String.format("%32s", Long.toBinaryString(obj.getExObjData() & 0xFFFFFFFF)).replace(' ', '0'),data);
			
			Core.write8(offset+24, String.format("%8s", Long.toBinaryString(obj.getObjType() & 0xFF)).replace(' ', '0'), data);
			Core.write8(offset+25, String.format("%8s", Long.toBinaryString(obj.getcObjType() & 0xFF)).replace(' ', '0'), data);

			Core.write16(offset+26, String.format("%16s", Long.toBinaryString(obj.getLinkID() & 0xFFFF)).replace(' ', '0'), data);
			Core.write16(offset+28, String.format("%16s", Long.toBinaryString(obj.getEfIndx() & 0xFFFF)).replace(' ', '0'), data);
			
			Core.write8(offset+30, String.format("%8s", Long.toBinaryString(obj.getUnknw_01() & 0xFF)).replace(' ', '0'), data);
			Core.write8(offset+31, String.format("%8s", Long.toBinaryString(obj.getcObjTransformID() & 0xFF)).replace(' ', '0'), data);

			offset = offset + Obj_t.TOTAL_OFFSET;
		}
		
		JFileChooser chooser = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter("Mario Maker Objects (.cdto)", "cdto");
		chooser.setFileFilter(filter);

		//System.out.println(this.getParent().getParent().getParent());
		
		int ret = chooser.showSaveDialog(this.getParent().getParent().getParent());
		
		String path = "";
		
		if (ret == JFileChooser.APPROVE_OPTION) {
			System.out.println("File has been chosen.");
			File level = chooser.getSelectedFile();

			if(!level.getAbsolutePath().endsWith(".cdto")) {
				
				level = new File(level.getAbsolutePath()+".cdto");
				
			}
			
			if (!level.exists()) {
				
				path = level.getAbsolutePath();
				chooser = null;

			} else {
				
				int op = JOptionPane.showOptionDialog(chooser, "The File \""+level.getName()+"\" does already exist!\nDo you want to replace this File?", "Replace File?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.QUESTION_MESSAGE);
				
				if(op == 0) {
					
				}else {
					return;
				}
				
				path = level.getAbsolutePath();
				
				System.out.println("Overwriting File");
				
			}

		} else if (ret == JFileChooser.CANCEL_OPTION) {
			System.out.println("Canceled Save!");
			return;
		} else {
			return;
		}
		
		
		
		try {
			Core.saveFile(data, path);
			JOptionPane.showMessageDialog(this.getParent().getParent(), "Export Complete!");
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.getParent().getParent(), "Export Failed!");
		}
		
	}
	
	private void save(SMMLevel lvl, byte[] data) {
		JFileChooser chooser = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter("Mario Maker Level Format (.cdt)", "cdt");
		chooser.setFileFilter(filter);

		//System.out.println(this.getParent().getParent().getParent());
		
		int ret = chooser.showSaveDialog(this.getParent().getParent().getParent());
		
		String path = "";
		
		if (ret == JFileChooser.APPROVE_OPTION) {
			System.out.println("File has been chosen.");
			File level = chooser.getSelectedFile();

			if(!level.getAbsolutePath().endsWith(".cdt")) {
				
				level = new File(level.getAbsolutePath()+".cdt");
				
			}
			
			if (!level.exists()) {
				
				path = level.getAbsolutePath();
				chooser = null;

			} else {
				
				int op = JOptionPane.showOptionDialog(chooser, "The File \""+level.getName()+"\" does already exist!\nDo you want to replace this File?", "Replace File?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.QUESTION_MESSAGE);
				
				if(op == 0) {
					
				}else {
					return;
				}
				
				path = level.getAbsolutePath();
				
				System.out.println("Overwriting File");
				
			}

		} else if (ret == JFileChooser.CANCEL_OPTION) {
			System.out.println("Canceled Save!");
			return;
		} else {
			return;
		}
		
		
		
		try {
			Core.saveFile(data, path);
			JOptionPane.showMessageDialog(this.getParent().getParent(), "Save Complete!");
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.getParent().getParent(), "Save Failed!");
		}
		
	}

	public void showTools() {
		CardLayout cl = (CardLayout)(switcher.getLayout());
	    cl.show(switcher, "tls");
	}

	public void enableButton() {
		viewMap.setEnabled(true);
	}
	
}
