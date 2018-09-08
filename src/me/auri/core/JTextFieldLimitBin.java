package me.auri.core;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class JTextFieldLimitBin extends PlainDocument {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int limit;

	JTextFieldLimitBin(int limit) {
		super();
		this.limit = limit;
	}

	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		if (str == null)
			return;
		
		for(int i = 0; i < str.length(); i++) {
			
			if(str.charAt(i) == '1') {
				
			}else if(str.charAt(i) == '0') {
				
			}else {
				return;
			}
			
		}

		if ((getLength() + str.length()) <= limit) {
			super.insertString(offset, str, attr);
		}
	}
}
