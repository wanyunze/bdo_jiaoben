import java.awt.*;

/**
 * Created by Yunze on 2019/9/10.
 */
public class quest_coordinate_helper {
    public static void main(String[] args) throws InterruptedException {
        while(true) {
            Point p = MouseInfo.getPointerInfo().getLocation();
            int x = p.x;
            int y = p.y;
            System.out.println("x coordinate: " + x + "\nycoordinate: " + y);
            Thread.sleep(1000);
        }
    }
}
