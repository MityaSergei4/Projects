package app;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

class Controller extends JPanel{
    private Creater creater = new Creater(new File("res/config.dll"));

    private int cmdA = 0;
    private int cmdB = 0;
    private int cmdC = 0;
    private int cmdAsk = 0;
    private String flag = null;

    private ArrayList<JButton> btnList = new ArrayList<>();
    private ArrayList<String> remList = new ArrayList<>();
    private ArrayList<JRadioButton> radioList = new ArrayList<>();
    private ArrayList<JTextField> fieldList = new ArrayList<>();

    private Map<String, String> map = new HashMap<>();

    Controller() {
        setMap();
        Menu();
        setLayout(null);
    }

    private BufferedImage image;

    public void paintComponent(Graphics g){
        super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
    }

    private void setBackground(String path){
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearPanel(){
        btnList.clear();
        radioList.clear();
        fieldList.clear();
        removeAll();
        repaint();
    }

    private void addPanel(String path){
        for (JButton pair : btnList) {
            add(pair);
        }
        ButtonGroup group = new ButtonGroup();
        for (JRadioButton pair : radioList) {
            group.add(pair);
            add(pair);
        }
        for(JTextField pair : fieldList){
            add(pair);
        }
        setBackground(path);
    }

    private void setMap(){
        try{

            BufferedReader reader = new BufferedReader(new FileReader("res/config.dll"));

            while(reader.ready()){
                String[] part = reader.readLine().split("=");
                String key = part[0].trim();
                String value;
                if(part.length < 2) {
                    value = "";
                } else {
                    value = part[1].trim();
                }
                map.put(key, value);
            }

            reader.close();

        } catch (FileNotFoundException e) {
            System.out.println("Файл конфигураций не найден.");
        } catch (IOException e) {
            System.out.println("Ошибка в конструкторе класса." + e);
        }
    }

    private String getValue(String key){
        String value = "";
        for(Map.Entry<String, String> pair : map.entrySet()){
            if(pair.getKey().equals(key)){
                value = pair.getValue();
            }
        }
        return value;
    }

    private void setValue(String key, String value){
        for(Map.Entry<String, String> pair : map.entrySet()){
            if(pair.getKey().equals(key)){
                pair.setValue(value);
            }
        }
    }

    private void saveMap(){
        FileWriter writer;
        try {
            writer = new FileWriter("res/config.dll");

            for(Map.Entry pair : map.entrySet()){
                writer.write(pair + "\n");
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Ошибка в записи метода saveMap().");
        }

    }

    private void Menu(){
        btnList.add(creater.setButton("Игра", 505, 260, 360, 60, "res/ingame.png"));
        btnList.add(creater.setButton("Настройки", 505, 360, 360, 60, "res/settings.png"));
        btnList.add(creater.setButton("X", 505, 460, 360, 60, "res/out.png"));
        add(creater.setLabel("викторина", 525, 10, 600, 150, "Serif", 2, 72, new Color(75, 200, 255)));
        for(JButton pair : btnList){
            pair.addActionListener(e -> {
                if(pair.getName().equals("X")){
                    System.exit(0);
                }
                if(pair.getName().equals("Игра")){
                    clearPanel();
                    Game();
                }
                if(pair.getName().equals("Настройки")){
                    clearPanel();
                    Settings();
                }
            });
        }
        addPanel("res/menu.jpg");
    }

    private void Game(){
        JButton btn;
        int posX = 380, posY = 20, btnCount = 1;
        for(int i = 1; i < 6; i++){
            for(int j = 0; j < 5; j++){
                btn = creater.setButton("b" + btnCount, posX, posY, 128, 60, "res/" + i + "00.png");
                btn.setEnabled(false);
                if(!remList.contains(btn.getName())){
                    btnList.add(btn);
                }
                posY += 100; btnCount++;
            }
            posX += 200; posY = 20;
        }

        posX = 40; posY = 40;
        for(int i = 1; i <= 5; i++){
            add(creater.setLabel(getValue("topic" + i), posX, posY, 400, 32, "Calibri", 1, 32, new Color(75, 200, 255)));
            posY += 100;
        }
        add(creater.setLabel("Баллов у команды 1: " + cmdA, 350, 550, 400, 32, "Calibri", 2, 32, new Color(250, 150, 100)));
        add(creater.setLabel("Баллов у команды 2: " + cmdB, 350, 620, 400, 32, "Calibri", 2, 32, new Color(250, 150, 100)));
        add(creater.setLabel("Баллов у команды 3: " + cmdC, 350, 690, 400, 32, "Calibri", 2, 32, new Color(250, 150, 100)));
        btnList.add(creater.setButton("cmd1", 910, 570, 400, 50, "res/cmd1.png"));
        btnList.add(creater.setButton("cmd2", 890, 640, 400, 50, "res/cmd2.png"));
        btnList.add(creater.setButton("cmd3", 870, 710, 400, 50, "res/cmd3.png"));
        btnList.add(creater.setButton("back", 50, 590, 200, 120, "res/back.png"));

        for(JButton pair : btnList){
            pair.addActionListener(e -> {
                for(int i = 1; i < 4; i++){
                    if(pair.getName().equals("cmd" + i)){
                        pair.setEnabled(false);
                        flag = "cmd" + i;
                        cmdAsk = i;
                        for(JButton par : btnList){
                            if(!par.getName().equals(flag)){
                                par.setEnabled(true);
                            }
                        }
                    }
                }
                for(int i = 0; i < btnList.size() +1; i++) {
                    if (pair.getName().equals("b" + i)) {
                        remList.add("b" + i);
                        clearPanel();
                        Quest(pair);
                        flag = null;
                    }
                }
                if(pair.getName().equals("back")){
                    clearPanel();
                    Menu();
                }
            });
        }

        addPanel("res/game.png");
    }

    private void Quest(JButton btn) {
        String name = btn.getName();
        File file = new File(btn.getIcon().toString());
        String s = file.getName();
        char[] r = s.toCharArray();
        StringBuilder rep = new StringBuilder();
        for(int i = 0; i < 3; i++){ rep.append(r[i]); }
        int cmdPoint = Integer.parseInt(String.valueOf(rep)); // Сократить

        add(creater.setLabel("<html>" + getValue(name + "_quest") + "</html>", 100, 100, 1100, 400, "Calibri", 3, 28, new Color(75, 200, 255)));
        radioList.add(creater.setRadio(getValue(name + "_v1"), "r1", 900, 600));
        radioList.add(creater.setRadio(getValue(name + "_v2"), "r2", 900, 640));
        radioList.add(creater.setRadio(getValue(name + "_v3"), "r3", 1200, 600));
        radioList.add(creater.setRadio(getValue(name + "_v4"), "r4", 1200, 640));
        add(creater.setLabel("Баллов у команды 1: " + cmdA, 460, 620, 400, 28, "Calibri", 3,24, new Color(120, 200, 120)));
        add(creater.setLabel("Баллов у команды 2: " + cmdB, 460, 670, 400, 28, "Calibri", 3,24, new Color(120, 200, 120)));
        add(creater.setLabel("Баллов у команды 3: " + cmdC, 460, 720, 400, 28, "Calibri", 3,24, new Color(120, 200, 120)));
        add(creater.setLabel("Отвечает команда: " + cmdAsk, 50, 620, 320, 30, "Calibri", 3, 30, new Color(120, 200, 120)));
        add(creater.setLabel("Возможные баллы: " + cmdPoint, 50, 680, 320, 30, "Calibri", 3, 30, new Color(120, 200, 120)));
        btnList.add(creater.setButton("Ok", 960, 690, 320, 60, "res/ok.png"));
        btnList.get(0).setEnabled(false);

        for(JRadioButton pair : radioList){
            pair.addActionListener(e -> btnList.get(0).setEnabled(true));
        }

        String answer = getValue(name + "_answer");

        btnList.get(0).addActionListener(e -> {
            int i;
            for (i = 0; i < radioList.size(); i++) {
                if(radioList.get(i).isSelected()){
                    if (radioList.get(i).getName().equals(answer)) {
                        int cmd = 0;
                        switch (cmdAsk){
                            case 1: cmdA += cmdPoint; cmd = 1;
                            break;
                            case 2: cmdB += cmdPoint; cmd = 2;
                            break;
                            case 3: cmdC += cmdPoint; cmd = 3;
                            break;
                        }
                        new Popup("<html><p>Правильно!</p> <p>Команда " + cmd + "</p> <p>Получила " + cmdPoint + " Очков</p></html>");
                    } else {
                        new Popup("Неправильно!");
                    }
                    clearPanel();
                    Game();
                }
            }
        });

        addPanel("res/quiz.png");
    }

    private void Settings(){
        btnList.add(creater.setButton("back", 50, 590, 200, 120, "res/back.png"));
        btnList.add(creater.setButton("reset", 940, 590, 400, 80, "res/res.png"));

        int posX = 380, posY = 20, btnCount = 1;
        for(int i = 1; i < 6; i++){
            for(int j = 0; j < 5; j++){
                btnList.add(creater.setButton("b" + btnCount, posX, posY, 128, 60, "res/" + i + "00.png"));
                posY += 100; btnCount++;
            }
            posX += 200; posY = 20;
        }
        for(JButton pair : btnList){
            pair.addActionListener(e -> {
                if(pair.getName().equals("back")){
                    clearPanel();
                    Menu();
                } else if(pair.getName().equals("reset")){
                    int result = JOptionPane.showConfirmDialog(this, "Вы уверены?", "Подтвердите действие", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if(result == JOptionPane.YES_OPTION){
                        creater.editFile();
                        setMap();
                    }
                    repaint();
                } else {
                    clearPanel();
                    Setup(pair.getName());
                }
            });
        }

        addPanel("res/setting.png");
    }

    private void Setup(String name){

        JTextArea area = new JTextArea();
        area.setText(getValue(name + "_quest"));
        area.setSize(1240, 360);
        area.setLocation(50, 150);
        area.setForeground(new Color(75, 200, 255));
        area.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 28));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        add(area);

        fieldList.add(creater.setField(name + "_v1" ,getValue(name + "_v1"), 900, 620));
        fieldList.add(creater.setField(name + "_v2" ,getValue(name + "_v2"),900, 680));
        fieldList.add(creater.setField(name + "_v3" ,getValue(name + "_v3"),1130, 620));
        fieldList.add(creater.setField(name + "_v4" ,getValue(name + "_v4"),1130, 680));

        btnList.add(creater.setButton("r1", 1105, 620, 18, 18, "res/mark.png"));
        btnList.add(creater.setButton("r2", 1105, 680, 18, 18, "res/mark.png"));
        btnList.add(creater.setButton("r3", 1336, 620, 18, 18, "res/mark.png"));
        btnList.add(creater.setButton("r4", 1336, 680, 18, 18, "res/mark.png"));
        btnList.add(creater.setButton("ok", 260, 690, 320, 60, "res/ok.png"));

        for(JButton pair : btnList) {
            pair.addActionListener(e ->{
                if(pair.getName().equals("ok")){
                    setValue(name + "_quest", area.getText());
                    for(JTextField fields : fieldList){
                        setValue(fields.getName(), fields.getText());
                    }
                    saveMap();
                    clearPanel();
                    new Popup("Успешно!");
                    Settings();
                    repaint();
                } else {
                    for (int i = 1; i < 5; i++) {
                        if (pair.getName().equals("r" + i)) {
                            pair.setEnabled(false);
                            setValue(name + "_answer", pair.getName());
                            for (JButton par : btnList) {
                                if (!par.getName().equals(pair.getName())) {
                                    par.setEnabled(true);
                                }
                            }
                        }
                    }
                }
            });
        }

        addPanel("res/setup.png");
    }
}
