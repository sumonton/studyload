package swing;

import javax.swing.*;
import java.awt.*;

public class NotHel1oWorldComponent extends JComponent {
    public static final int MESSACE_X = 75;
    public static final int MESSACE_Y = 100;
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;
    public void paintComponent(Graphics g) {
        g.drawString("Not a Hello World program", MESSACE_X, MESSACE_Y);
    }
    public Dimension getPreferredSize(){
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
}
