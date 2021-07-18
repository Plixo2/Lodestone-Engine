package net.plixo.paper.client.ui.elements.values;

import net.plixo.paper.client.ui.elements.canvas.UICanvas;
import net.plixo.paper.client.ui.elements.clickable.UIButton;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import net.plixo.paper.client.util.SaveUtil;
import net.plixo.paper.client.util.Util;
import org.plixo.jrcos.Mapping;

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
public class UIFileChooser extends UICanvas {

    //JFileChooser is multi threaded.. i think
    volatile File file;
    //for checking if the window is open
    JFrame lastFrame;


    public UIFileChooser() {
        setColor(0);
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

    //returns the File
    public File getFile() {
        if(file == null) {
            Mapping.IObjectValue<File> iObjectValue = (Mapping.IObjectValue<File>) Mapping.primitives.get(File.class);
            return iObjectValue.getDefault();
        }
        return new File(file.getAbsolutePath());
    }


    //set dimensions for the choose button
    @Override
    public void setDimensions(float x, float y, float width, float height) {
        UIFileChooser instance = this;
        UIButton button = new UIButton() {
            @Override
            public void actionPerformed() {
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
                        instance.file = chooser.getSelectedFile();
                    } else if (e.getActionCommand().equals(JFileChooser.CANCEL_SELECTION)) {
                        closeFrame.run();
                        instance.file = null;
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
        };
        button.displayName = ">";
        button.setRoundness(2);
        button.setDimensions(width - height, 0, height, height);
        clear();
        add(button);
        super.setDimensions(x, y, width, height);
    }

    //set current file
    public void setFile(File file) {
        this.file = file;
    }

}
