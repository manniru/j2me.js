package gfx;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

public class DrawStringBaselineAnchorTest extends MIDlet {
    private Display display;

    class TestCanvas extends Canvas {
        protected void paint(Graphics g) {
            g.setColor(0x00FFFFFF);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(0);
            g.setStrokeStyle(Graphics.SOLID);
            g.drawString("Top left", 50, 50, Graphics.BASELINE | Graphics.LEFT);
            System.out.println("PAINTED");
        }
    }

    public DrawStringBaselineAnchorTest() {
        display = Display.getDisplay(this);
    }

    public void startApp() {
        TestCanvas test = new TestCanvas();
        test.setFullScreenMode(true);
        display.setCurrent(test);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }
}

