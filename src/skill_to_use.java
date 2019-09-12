import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * Created by Yunze on 2019/9/7.
 */
public class skill_to_use {
    Robot robot;

    // SHIFT + Q
    public void skill1() throws AWTException {
        robot = new Robot();
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(KeyEvent.VK_Q);
        robot.keyRelease(KeyEvent.VK_SHIFT);
        robot.keyRelease(KeyEvent.VK_Q);
    }
    // LMB + RMB
    public void skill2() throws AWTException, InterruptedException {
        robot = new Robot();
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
        // runs back
        robot.keyPress(KeyEvent.VK_S);
        //2800
        Thread.sleep(1000);
        robot.keyRelease(KeyEvent.VK_S);

    }
    // Unbridled Wrath - quick slot 5
    public void skill3() throws InterruptedException, AWTException {
        robot = new Robot();
        robot.keyPress(KeyEvent.VK_5);
        robot.keyRelease(KeyEvent.VK_5);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_C);
        robot.keyRelease(KeyEvent.VK_C);


    }
    // SHIFT+RMB
    public void skill4() throws InterruptedException, AWTException {
        robot = new Robot();
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        Thread.sleep(2000);
        robot.keyRelease(KeyEvent.VK_SHIFT);
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
    }
    // FFF
    public void skill5() throws InterruptedException, AWTException {
        robot = new Robot();
        robot.keyPress(KeyEvent.VK_F);
        Thread.sleep(2000);
        robot.keyRelease(KeyEvent.VK_F);
    }
    // RMB
    public void skill6() throws InterruptedException, AWTException {
        robot = new Robot();
        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        Thread.sleep(2000);
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
    }
}
