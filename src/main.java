import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Yunze on 2019/9/7.
 */
public class main {
    public static void main(String[] args) throws AWTException, InterruptedException, IOException {
        // user input
        int total_scrolls = 71; //  IMPORTANT: 0 indexed
        int your_round_number = 1;
        int port = 3310;
        String IP_address = "localhost/127.0.0.1";
        // var
        int round = 1;
        int current_scroll_No = 0;
        boolean leader;
        boolean inheritor;
        boolean peasant;
        // import functions
        steps step = new steps();
        skill_to_use skills = new skill_to_use();

        // *************************************** start of application ****************************************

        Socket sock = new Socket(IP_address, port);

        //  3 rounds in total
        while(round <= 3) {

            // check your role each round
            if (round == your_round_number) {
                leader = true;
                inheritor = false;
                peasant = false;
            }else if (your_round_number == (round + 1)){
                leader = false;
                inheritor = true;
                peasant = false;
            }else{
                leader = false;
                inheritor = false;
                peasant = true;
            }

            // ************** START steps for each round **************
            while (current_scroll_No <= total_scrolls) {
                if (leader) {
                    step.click_scroll(current_scroll_No, sock);
                }

                step.confirm();

                //wait for summon, 13 seconds
                Thread.sleep(13000);

                // stage 1-3
                step.stage_1_skill();
                step.stage_2_skill();
                step.stage_3_skill();

                // collect reward
                step.collect_reward();

                current_scroll_No++;
            }
            // ************** END steps for each round **************

            if (leader){
                steps.pass_on_leader();
            }

            // increment round
            round++;
        }
    }
}
