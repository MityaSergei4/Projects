package app;

import javax.swing.*;

class Popup extends JFrame{
    Popup(String msg) {
        super();
        final JOptionPane pane = new JOptionPane(msg, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
        JDialog dialog = pane.createDialog(null, "Уведомление");
        dialog.setModal(false);
        dialog.setVisible(true);
        dialog.setContentPane(pane);
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.pack();
        new Timer(1500, e1 -> dialog.dispose()).start();
    }
}
