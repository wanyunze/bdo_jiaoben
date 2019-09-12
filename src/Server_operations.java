import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Yunze on 2019/9/10.
 */
public class Server_operations {
    public void send_all (String msg, Socket leader_socket, Socket inheritor_socket, Socket peasant_socket) throws IOException {
        DataOutputStream leader_out = new DataOutputStream(leader_socket.getOutputStream());
        DataOutputStream inheritor_out = new DataOutputStream(inheritor_socket.getOutputStream());
        DataOutputStream peasant_out = new DataOutputStream(peasant_socket.getOutputStream());


        leader_out.writeUTF(msg);
        System.out.println("server: sending leader message: " + msg);
        inheritor_out.writeUTF(msg);
        System.out.println("server: sending inheritor message: " + msg);
        peasant_out.writeUTF(msg);
        System.out.println("server: sending peasant message: " + msg);
    }

    public void receive_all (Socket leader_socket, Socket inheritor_socket, Socket peasant_socket) throws IOException {
        DataInputStream leader_in = new DataInputStream(leader_socket.getInputStream());
        DataInputStream inheritor_in = new DataInputStream(inheritor_socket.getInputStream());
        DataInputStream peasant_in = new DataInputStream(peasant_socket.getInputStream());

        String leader_in_str = leader_in.readUTF();
        System.out.println("server: received leader message: " + leader_in_str);
        String inheritor_in_str = inheritor_in.readUTF();
        System.out.println("server: received inheritor message: " + inheritor_in_str);
        String peasant_in_str = peasant_in.readUTF();
        System.out.println("server: received peasant message: " + peasant_in_str);
    }

    public void scrollCall(Socket leader_socket, Socket inheritor_socket, Socket peasant_socket) throws IOException {
        String outMsg = server_outMsg.scroll_msg;
        send_all(outMsg,leader_socket,inheritor_socket,peasant_socket);
    }

    public void stage_1(Socket leader_socket, Socket inheritor_socket, Socket peasant_socket) throws IOException {
        receive_all(leader_socket,inheritor_socket,peasant_socket);
        String outMsg = server_outMsg.stage_1_msg;
        send_all(outMsg,leader_socket,inheritor_socket,peasant_socket);
    }


    public void stage_2(Socket leader_socket, Socket inheritor_socket, Socket peasant_socket) throws IOException {
        receive_all(leader_socket,inheritor_socket,peasant_socket);
        String outMsg = server_outMsg.stage_2_msg;
        send_all(outMsg,leader_socket,inheritor_socket,peasant_socket);
    }

    public void stage_3(Socket leader_socket, Socket inheritor_socket, Socket peasant_socket) throws IOException {
        receive_all(leader_socket,inheritor_socket,peasant_socket);
        String outMsg = server_outMsg.stage_3_msg;
        send_all(outMsg,leader_socket,inheritor_socket,peasant_socket);
    }

    public void reward_collect(Socket leader_socket, Socket inheritor_socket, Socket peasant_socket) throws IOException {
        receive_all(leader_socket,inheritor_socket,peasant_socket);
    }

    public void passing_leader_complete(Socket leader_socket) throws IOException {
        String outMsg = server_outMsg.pass_on_leader_msg;
        DataOutputStream leader_out = new DataOutputStream(leader_socket.getOutputStream());
        leader_out.writeUTF(outMsg);
        System.out.println("server: sending leader message: " + outMsg);

        DataInputStream leader_in = new DataInputStream(leader_socket.getInputStream());
        String leader_in_str = leader_in.readUTF();
        System.out.println("server: received leader message: " + leader_in_str);

    }


    public boolean get_change_leader_signal(Socket leader_socket) throws IOException {
        boolean leader_signal;
        DataInputStream leader_in = new DataInputStream(leader_socket.getInputStream());
        System.out.println("server: received leader message: " + leader_in);
        int leader_in_str = Integer.parseInt(leader_in.readUTF());
        leader_signal = leader_in_str != 0;
        return leader_signal;
    }

    public boolean end_signal(Socket leader_socket) throws IOException {
        boolean leader_signal;
        DataInputStream leader_in = new DataInputStream(leader_socket.getInputStream());
        System.out.println("server: received leader message: " + leader_in);
        int leader_in_str = Integer.parseInt(leader_in.readUTF());
        leader_signal = leader_in_str != 0;
        return leader_signal;
    }
}
