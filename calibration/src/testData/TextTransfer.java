/**
 * Package IHM contents the interface of our application
 */
package testData;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

/**
 * For the copy and paste
 * 
 * @author Guillaume
 * 
 */
public class TextTransfer implements ClipboardOwner {
	public void lostOwnership(Clipboard aClipboard, Transferable aContents) {
		// do nothing
	}

	/**
	 * Copy the aString
	 * 
	 * @param aString
	 */
	public void setClipboardContents(String aString) {
		StringSelection stringSelection = new StringSelection(aString);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, this);
	}
}
