package net.plixo.lodestone.client.ui.resource.resource;

import net.plixo.lodestone.client.ui.UGui;
import net.plixo.lodestone.client.ui.elements.canvas.UICanvas;
import net.plixo.lodestone.client.ui.elements.other.UIButton;
import net.plixo.lodestone.client.util.UColor;
import net.plixo.lodestone.client.util.io.SaveUtil;
import net.plixo.lodestone.client.util.UMath;
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

    File file;
    //for checking if the window is open
    JFrame lastFrame;


    public UIFileChooser() {
        setColor(0);
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {

        drawDefault( UColor.getBackground(0.3f));
        drawHoverEffect();


        if (file != null) {
            String toStr = file.getName();

            String newStr = UMath.displayTrim(toStr, x + width - height - 22);
            if (!toStr.equals(newStr)) {
                newStr += "...";
            }

            UGui.drawString(newStr, x + 4, y + height / 2, UColor.getTextColor());
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
        UIButton button = new UIButton();
        button.setAction(() -> {
            if (lastFrame != null && lastFrame.isVisible()) {
                return;
            }
            JFrame frame = new JFrame("choose wisely...");
            lastFrame = frame;
            frame.setSize(600, 500);
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            JFileChooser chooser = new JFileChooser();



            String[] names = new String[SaveUtil.FileFormat.values().length-1];
            int index = 0;
            for (SaveUtil.FileFormat value : SaveUtil.FileFormat.values()) {
                if(value != SaveUtil.FileFormat.META) {
                names[index] = value.format;
                index += 1;
                }
            }

            chooser.setFileFilter(new FileNameExtensionFilter("Supported",names));

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
        });
        button.setDisplayName(">");
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
