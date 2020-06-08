package Student_Information;
import javax.swing.*;
import java.awt.*;

public class MainFunction
{
        public static void main(String args[])
        {
                EventQueue.invokeLater(()->
                {
                        var Window=new MainFrame();
                        Window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        Window.setVisible(true);
                });
        }
}
