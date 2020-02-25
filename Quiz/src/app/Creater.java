package app;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class Creater {

    Creater(File file){
        if(!file.exists()){
            try {
                Files.createFile(Paths.get(file.getPath()));
                editFile();
            } catch (IOException e) {
                System.out.println("Ошибка создания файла в конструкторе Creater.");
            }
        }
    }

    JButton setButton(String btnName, int posX, int posY, int sizeW, int sizeH, String ico) {
        JButton button = new JButton(new ImageIcon(ico));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setName(btnName);
        button.setLocation(posX, posY);
        button.setSize(sizeW, sizeH);
        button.setIconTextGap(10);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        return button;
    }

    JLabel setLabel(String labelName, int posX, int posY, int sizeW, int sizeH, String fontName, int font, int fontSize, Color color){
        JLabel label = new JLabel(labelName);
        label.setLocation(posX, posY);
        label.setFont(new Font(fontName, font, fontSize));
        label.setForeground(color);
        label.setSize(sizeW, sizeH);
        return label;
    }

    JRadioButton setRadio(String radioText, String radioName, int posX, int posY){
        JRadioButton jrb = new JRadioButton(radioText);
        jrb.setName(radioName);
        jrb.setSize(225, 25);
        jrb.setFont(new Font("Comic Sans", Font.ITALIC, 16));
        jrb.setLocation(posX, posY);
        jrb.setBackground(Color.WHITE);
        jrb.setForeground(new Color(120, 200, 120));
        return jrb;
    }

    JTextField setField(String name, String text, int posX, int posY){
        JTextField jtf = new JTextField();
        jtf.setText(text);
        jtf.setName(name);
        jtf.setSize(200, 25);
        jtf.setLocation(posX, posY);
        jtf.setForeground(new Color(125,125,125));
        jtf.setFont(new Font("Cflibri", Font.BOLD, 12));
        return jtf;
    }

    void editFile(){
        try {
            FileWriter writer = new FileWriter("res/config.dll");

            for (int i = 1; i <= 5; i++) {
                writer.write("topic" + i + "=\n");
            }
            for (int i = 1; i <= 25; i++) {
                writer.write("b" + i + "_quest" + "=\n");
                writer.write("b" + i + "_v1" + "=\n");
                writer.write("b" + i + "_v2" + "=\n");
                writer.write("b" + i + "_v3" + "=\n");
                writer.write("b" + i + "_v4" + "=\n");
                writer.write("b" + i + "_answer" + "=\n");
            }

            writer.close();
        } catch(IOException e){ System.out.println("Ошибка в методе CreateFile()."); }
    }
}
