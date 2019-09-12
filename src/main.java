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
        int your_round_number = 1;  // TODO: change to your role accordingly
        int port = 3310;
        String server_IP_address = server_outMsg.server_IP;
        // var
        int round = 1;
        int current_scroll_No = 0;
        boolean leader;
        boolean inheritor;
        boolean peasant;
        // import functions
        steps step = new steps();
        skill_to_use skills = new skill_to_use();
        Client_operations co;

        // *************************************** start of application ****************************************

        Socket sock = new Socket(server_IP_address, port);

        // TODO: initialise connection message
        co = new Client_operations();
        co.connect_start_message(sock);

        //  3 rounds in total
        while(round <= 3) {
            // check your role each round
            if (round == your_round_number) {
                leader = true;
                inheritor = false;
                peasant = false;
                // TODO: send role signal
                co.send_role("Leader",sock);
            }else if (your_round_number == (round + 1)){
                leader = false;
                inheritor = true;
                peasant = false;
                co.send_role("Inheritor",sock);
            }else{
                leader = false;
                inheritor = false;
                peasant = true;
                co.send_role("Peasant",sock);
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
                // TODO: send complete stage 1 message
                co.complete_stage_1(sock);
                // TODO: receive start stage 2 message
                co.start_stage_2(sock);
                step.stage_2_skill();
                // TODO: send complete stage 2 message
                co.complete_stage_2(sock);
                // TODO: receive start stage 3 message
                co.start_stage_3(sock);
                step.stage_3_skill();
                // TODO: send complete stage 3 message
                co.complete_stage_3(sock);
                // TODO: receive start collect reward message
                co.start_collect_reward(sock);

                // collect reward
                step.collect_reward();
                // TODO: send complete collect reward message
                co.complete_collect_reward(sock);

                if(current_scroll_No != total_scrolls) {

                    // TODO: send not change leader signal
                    if(leader) {
                        co.not_change_leader(sock);

                        // TODO: send continue signal
                        co.continue_signal(sock);
                    }
                }

                current_scroll_No++;
            }
            // ************** END steps for each round **************

            if (leader){
                co.change_leader(sock);
                steps.pass_on_leader();
                // TODO: send continue signal
                if(round != 3) {
                    co.continue_signal(sock);
                }
            }

            // increment round
            round+=1;
        }
        co.end_signal(sock);
    }
}
