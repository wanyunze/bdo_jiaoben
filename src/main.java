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
        int total_scrolls = 71; //  TODO: IMPORTANT: 0 indexed
        int your_round_number = 1;  // TODO: change to your role accordingly
        int port = 3310;
        String server_IP_address = server_outMsg.server_IP; // TODO: change to host IP address
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

        // initialise connection message
        co = new Client_operations();
        co.connect_start_message(sock);

        //  3 rounds in total
        while(round <= 3) {
            // check your role each round
            if (round == your_round_number) {
                leader = true;
                inheritor = false;
                peasant = false;
                // send role signal
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
                co.complete_stage_1(sock);
                co.start_stage_2(sock);
                step.stage_2_skill();
                co.complete_stage_2(sock);

//                co.start_stage_3(sock);
//                step.stage_3_skill();
//                co.complete_stage_3(sock);

                // collect reward
                co.start_collect_reward(sock);
                step.collect_reward();
                co.complete_collect_reward(sock);

                if(current_scroll_No != total_scrolls) {
                    // send not change leader signal
                    if(leader) {
                        co.not_change_leader(sock);
                        // send continue signal
                        co.continue_signal(sock);
                    }
                }

                current_scroll_No++;
            }
            // ************** END steps for each round **************
            current_scroll_No = 0;

            if (round == 3){
                co.not_change_leader(sock);
                co.end_signal(sock);
            }else{
                if (leader){
                    co.change_leader(sock);
                    if(round == 1) {
                        // van to 486
                        steps.pass_on_leader_1();
                    }else{
                        // 486 to luan
                        steps.pass_on_leader_2();
                    }
                    co.continue_signal(sock);
                }
            }
            // increment round
            round+=1;
        }
    }
}
