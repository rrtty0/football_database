package BaseClasses;


import javax.swing.*;
import java.awt.*;

public class MyMessages {

    public static void showErrorMessage(Component parent, String title, String message){
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void showGoodMessage(Component parent, String title, String message){
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static int showQuestionMessage(Component parent, String title, String message){
        UIManager.put("OptionPane.yesButtonText"   , "Да"    );
        UIManager.put("OptionPane.noButtonText"    , "Нет"   );
        UIManager.put("OptionPane.cancelButtonText", "Отмена");
        return JOptionPane.showConfirmDialog(parent, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    }
}
