package net.plixo.paper.client.UI.elements;

import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.SaveUtil;
import net.plixo.paper.client.util.Gui;
import net.plixo.paper.client.util.Util;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;


public class UIFileChooser extends UIMultiButton {

	volatile File file;
	JFrame lastFrame;

	public UIFileChooser(int id) {
		super(id, new UIButton(0));
		others[0].displayName = ">";
		others[0].setRoundness(2);
	}
	@Override
	public void draw(float mouseX, float mouseY) {

		Gui.drawRoundetRect(x, y, x + width, y + height, roundness, ColorLib.getBackground(0.3f));
		int color = ColorLib.interpolateColor(0x00000000, 0x23000000, hoverProgress / 100f);
		Gui.drawRoundetRect(x, y, x + width, y + height, roundness, color);

	

		if (file != null) {
			String toStr = file.getName();

			String newStr = Util.displayTrim(toStr, x + width - height - 22);
			if (!toStr.equals(newStr)) {
				newStr += "...";
			}

			Gui.drawString(newStr, x + 4, y + height / 2, textColor);
		}
		super.draw(mouseX, mouseY);
	}
	
	public File getFile() {
		return file;
	}

	@Override
	public void otherButton(int id) {

		if(lastFrame != null && lastFrame.isVisible()) {
			return;
		}
		JFrame frame = new JFrame("choose wisely...");
		lastFrame = frame;
		frame.setSize(600, 500);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		JFileChooser chooser = new JFileChooser();   
        FileNameExtensionFilter Code = new FileNameExtensionFilter("Javascript", SaveUtil.FileFormat.Code.format);
        FileNameExtensionFilter Hud = new FileNameExtensionFilter("Hud", SaveUtil.FileFormat.Hud.format );
        FileNameExtensionFilter other = new FileNameExtensionFilter("JSON", SaveUtil.FileFormat.Other.format);
        FileNameExtensionFilter Visual = new FileNameExtensionFilter("VisualScript", SaveUtil.FileFormat.VisualScript.format);

        chooser.addChoosableFileFilter(Code);
        chooser.addChoosableFileFilter(Hud);
        chooser.addChoosableFileFilter(other);
        chooser.addChoosableFileFilter(Visual);
		
		chooser.setCurrentDirectory(SaveUtil.getFolderFromName(""));
		frame.add(chooser, BorderLayout.CENTER);

		Runnable closeFrame = () -> {
			frame.setVisible(false);
			frame.dispose();
		};

		chooser.addActionListener((ActionEvent e) -> {
			if (e.getActionCommand() == JFileChooser.APPROVE_SELECTION) {
				closeFrame.run();
				File selectedFile = chooser.getSelectedFile();
				file = selectedFile;
			} else if (e.getActionCommand() == JFileChooser.CANCEL_SELECTION) {
				closeFrame.run();
				file = null;
			}
		});
		
		

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeFrame.run();
			}
		});
		
		frame.setVisible(true);

	}

	@Override
	public void setDimensions(float x, float y, float width, float height) {
		others[0].setDimensions(width - height, 0, height, height);
		super.setDimensions(x, y, width, height);
	}

	public void setFile(File file) {
		this.file = file;
	}

}
