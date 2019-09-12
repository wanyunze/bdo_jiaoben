import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Yunze on 2019/9/7.
 */

public class steps {
    Robot robot;
    screen_size screen = new screen_size();
    //1.25，1.22，1.19，1.16，1.13
    // 0.051
    //1.62,1.569,1.518,1.467,1.416,1.365,
    //3.8, 3.45,3.1,2.75,2.4,2.05,1.7
    // 0.37
    int item_x;
    int item_y;
    int row_gap = (int)(screen.height*0.04);
    int column_gap = (int)(screen.width*0.023);
    final int x_confirm = (int)(screen.width/1.9);
    final int y_confirm = (int)(screen.height/2.25);
    int x_reward = (int)(screen.width/1.044);
    int y_reward = (int)(screen.height/1.94);
    final int x_reward_confirm = (int)(screen.width/1.7);
    final int y_reward_confirm = (int)(screen.height/1.18);
    Client_operations co;

    public void click_scroll(int current_scroll_No, Socket socket) throws AWTException, InterruptedException,IOException {
        robot = new Robot();
        co = new Client_operations();

        // open item
        robot.keyPress(KeyEvent.VK_I);
        robot.keyRelease(KeyEvent.VK_I);
        Thread.sleep(1000);
        
        // move mouse
        int column = current_scroll_No % 8;
        item_x = (int)(screen.width/(1.62)+column*column_gap);

        if((current_scroll_No/8) < 1){
             item_y = (int)(screen.height/3.8);
        }else{
            int row = (int)((double)current_scroll_No/8);
            item_y = (int)((screen.height/3.8)+(row*row_gap));
        }
        Thread.sleep(500);
        robot.mouseMove(0,0);
        Thread.sleep(500);
        robot.mouseMove(item_x,item_y);

        // start: scroll synchronization
        co.signal_scroll(socket);
        co.wait_scroll_signal(socket);
        // end: scroll synchronization

        // click
        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        Thread.sleep(500);
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);

    }

    // TODO: reset to (0,0) and test coordinates again
    public void collect_reward() throws AWTException, InterruptedException {
        robot = new Robot();
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        Thread.sleep(1000);
        robot.mouseMove(x_reward, y_reward);
        Thread.sleep(2000);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(500);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(2000);
        robot.mouseMove(x_reward_confirm, y_reward_confirm);
        Thread.sleep(500);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(500);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.keyPress(KeyEvent.VK_ESCAPE);
        robot.keyRelease(KeyEvent.VK_ESCAPE);

    }
    // TODO: reset to (0,0) and test coordinates again
    public void confirm() throws AWTException, InterruptedException {
        robot = new Robot();
        robot.mouseMove(x_confirm, y_confirm);
        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
        Thread.sleep(500);
        //close item
        robot.keyPress(KeyEvent.VK_ESCAPE);
        robot.keyRelease(KeyEvent.VK_ESCAPE);
    }

    // TODO：Set your skill order
    public void stage_1_skill() throws AWTException, InterruptedException {
        skill_to_use skills = new skill_to_use();
    }

    public void stage_2_skill() {
        skill_to_use skills = new skill_to_use();
    }

    public void stage_3_skill() {
        skill_to_use skills = new skill_to_use();
    }

    // TODO: Implement function and test coordinates
    public static void pass_on_leader() {
    }
}
