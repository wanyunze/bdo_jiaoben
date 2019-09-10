/**
 * Created by Yunze on 2019/9/9.
 */
public class tets {
    public static void main(String[] args) {
        screen_size screen = new screen_size();

        int current_scroll_No = 4;
        int row = (int)Math.ceil((double)current_scroll_No/9);
        double res = (3.8-((double)row*(0.37)));
        int y = (int)(screen.height/3.43);
        System.out.println("y: "+y);
        System.out.println(res);
        System.out.println(row);
    }
}
