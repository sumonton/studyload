package swing;

import javax.swing.*;
import java.awt.*;

public class SimpleFrameTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
//            SimpleFrame simpleFrame = new SimpleFrame();
//            simpleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            simpleFrame.setVisible(true);
            JFrame sizeFrame = new SizeFrame();

            sizeFrame.setTitle("SizeFrame");
            sizeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            sizeFrame.add(new NotHel1oWorldComponent());
            sizeFrame.pack();
            sizeFrame.setVisible(true);
            
        });
    }
}
