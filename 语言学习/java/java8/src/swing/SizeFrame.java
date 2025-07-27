package swing;

import javax.swing.*;
import java.awt.*;

public class SizeFrame extends JFrame {


    public SizeFrame() throws HeadlessException {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize =  toolkit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        setSize(screenWidth/2,screenHeight/2);
        setLocationByPlatform(true);

        Image img = new ImageIcon("resources/xmut.jpg").getImage();
        setIconImage(img);
//        repaint();

    }
}
