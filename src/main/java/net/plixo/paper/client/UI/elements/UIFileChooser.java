package net.plixo.paper.client.UI.elements;

import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import net.plixo.paper.client.util.SaveUtil;
import net.plixo.paper.client.util.Util;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

/**
 * for displaying and choosing a file with {@code JFileChooser}
 **/
public class UIFileChooser extends UIMultiButton {

    //JFileChooser is multi threaded.. i think
    volatile File file;
    //for checking if the window is open
    JFrame lastFrame;

    public UIFileChooser(int id) {
        super(id, new UIButton(0));
        others[0].displayName = ">";
        others[0].setRoundness(2);
    }


    @Override
    public void drawScreen(float mouseX, float mouseY) {

        Gui.drawRoundedRect(x, y, x + width, y + height, roundness, ColorLib.getBackground(0.3f));
        int color = ColorLib.interpolateColorAlpha(0x00000000, 0x23000000, hoverProgress / 100f);
        Gui.drawRoundedRect(x, y, x + width, y + height, roundness, color);


        if (file != null) {
            String toStr = file.getName();

            String newStr = Util.displayTrim(toStr, x + width - height - 22);
            if (!toStr.equals(newStr)) {
                newStr += "...";
            }

            Gui.drawString(newStr, x + 4, y + height / 2, textColor);
        }
        super.drawScreen(mouseX, mouseY);
    }

    //returns the File with existence test
    public File getFile() {
        if (file != null && !file.exists()) {
            return null;
        }
        return file;
    }

    //choose button pressed
    @Override
    public void otherButton(int id) {

        //returns if the window is still open
        if (lastFrame != null && lastFrame.isVisible()) {
            return;
        }
        JFrame frame = new JFrame("choose wisely...");
        lastFrame = frame;
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter Code = new FileNameExtensionFilter("Javascript", SaveUtil.FileFormat.Code.format);
        FileNameExtensionFilter Hud = new FileNameExtensionFilter("Hud", SaveUtil.FileFormat.Hud.format);
        FileNameExtensionFilter other = new FileNameExtensionFilter("JSON", SaveUtil.FileFormat.Other.format);
        FileNameExtensionFilter Visual = new FileNameExtensionFilter("VisualScript", SaveUtil.FileFormat.VisualScript.format);
        FileNameExtensionFilter Model = new FileNameExtensionFilter("Model", SaveUtil.FileFormat.Model.format);

        chooser.addChoosableFileFilter(Code);
        chooser.addChoosableFileFilter(Hud);
        chooser.addChoosableFileFilter(other);
        chooser.addChoosableFileFilter(Visual);
        chooser.addChoosableFileFilter(Model);

        chooser.setCurrentDirectory(SaveUtil.getFolderFromName(""));
        frame.add(chooser, BorderLayout.CENTER);

        Runnable closeFrame = () -> {
            frame.setVisible(false);
            frame.dispose();
        };

        chooser.addActionListener((ActionEvent e) -> {
            if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
                closeFrame.run();
                file = chooser.getSelectedFile();
            } else if (e.getActionCommand().equals(JFileChooser.CANCEL_SELECTION)) {
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



    //set dimensions for the choose button
    @Override
    public void setDimensions(float x, float y, float width, float height) {
        others[0].setDimensions(width - height, 0, height, height);
        super.setDimensions(x, y, width, height);
    }

    //set current file
    public void setFile(File file) {
        this.file = file;
    }

}
