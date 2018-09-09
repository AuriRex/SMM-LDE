package me.auri.core;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class LvlViewer implements ActionListener {

	private JFrame frame;
	
	private JTextField txt;
	private JTextField tilesetSelectText;
	private JTextField textOB;
	
	private JButton update;
	private JButton tilesetSelect;
	private JButton setOB;
	
	private DrawArea panel;
	
	JScrollPane scrollPane = null;
	
	public LvlViewer(JInternalFrameX frm, String title, SMMLevel lvl) {

		frame = new JFrame(title);
		
		frame.setIconImage(Core.hand.getImage());
		
		frame.setLocationRelativeTo(frm);
		
		frame.setSize(800, 600);
		
		JPanel left = new JPanel(new BorderLayout());
		
		JPanel upl = new JPanel(new GridLayout(3,2));
		
		update = new JButton("Hide ID!");
		update.addActionListener(this);
		
		tilesetSelect = new JButton("Tileset!");
		tilesetSelect.addActionListener(this);
		
		setOB = new JButton("Scale! (80=def)");
		setOB.addActionListener(this);
		
		txt = new JTextField();
		tilesetSelectText = new JTextField();
		textOB = new JTextField();
		//txt.setSize(60, 20);
		
		left.add(upl, BorderLayout.NORTH);
		
		upl.add(txt);
		upl.add(update);
		upl.add(tilesetSelectText);
		upl.add(tilesetSelect);
		upl.add(textOB);
		upl.add(setOB);
		
		JPanel main = new JPanel(new BorderLayout());
		
		panel = new DrawArea(frame, lvl, this);
		
		scrollPane = new JScrollPane (panel, 
	            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
	            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.getHorizontalScrollBar().setUnitIncrement(32);
		
		
		main.add(scrollPane, BorderLayout.CENTER);
		main.add(left, BorderLayout.WEST);
		
		frame.add(main);
		
		
		
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {

		    	frm.enableButton();
		    	frame.dispose();
		    	
		    }
		});
		
		frame.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(update)) {
			
			panel.doNotRender(Integer.parseInt(txt.getText()));
			panel.repaint();
			
		}
		if(e.getSource().equals(setOB)) {
			try {
				panel.setOneBlock(Integer.parseInt(textOB.getText()));
				panel.repaint();
			}catch(Exception ex) {
			}
		}
		if(e.getSource().equals(tilesetSelect)) {
			try {
				panel.setTileSet(Core.tilesetList.get(Integer.parseInt(tilesetSelectText.getText())));
				panel.repaint();
			}catch(Exception ex) {
			}
			
		}
	}
	
	
	
}
