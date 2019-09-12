import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Yunze on 2019/9/10.
 */
public class Client_operations {

    public void send(String msg, Socket sock) throws IOException{
        System.out.println("Client: sending "+msg+" to server");
        DataOutputStream out = new DataOutputStream(sock.getOutputStream());
        out.writeUTF(msg);
    }

    public void receive(Socket sock) throws IOException{
        System.out.println("Client: receiving server message");
        DataInputStream in = new DataInputStream(sock.getInputStream());
        System.out.println("Client received "+ in.readUTF() + " from server");
    }

    public void connect_start_message(Socket sock) throws IOException {
        send("connect",sock);
    }

    public void send_role(String role, Socket sock) throws IOException {
        send(role,sock);
    }

    public void signal_scroll(Socket socket) throws IOException {
        send(server_outMsg.scroll_msg,socket);
    }

    public void wait_scroll_signal(Socket socket) throws IOException {
        receive(socket);
    }

    public void complete_stage_1(Socket sock) throws IOException {
        send("stage 1 complete",sock);
    }

    public void start_stage_2(Socket sock) throws IOException {
        receive(sock);
    }

    public void complete_stage_2(Socket sock) throws IOException {
        send("stage 2 complete",sock);
    }

    public  void start_collect_reward(Socket sock) throws IOException {
        receive(sock);
    }

    public void complete_collect_reward(Socket sock) throws IOException {
        send("collecting reward complete",sock);
    }

    public void continue_signal(Socket sock) throws IOException {
        send("0",sock);
    }

    public void change_leader(Socket sock) {
    }

    public void not_change_leader(Socket sock) {
    }

    public void end_signal(Socket sock) throws IOException {
        send("1",sock);
    }

}
