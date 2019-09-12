import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Yunze on 2019/9/10.
 */
public class Central_Server {
    static int port = 3310;
    static String Li_Luan_IP = "58.84.162.78";
    static String Van_IP = "58.166.208.228";

    static Socket Li_socket;
    static Socket Luan_socket;
    static Socket Van_socket;

    static Socket Leader_socket;
    static Socket Inheritor_socket;
    static Socket Peasant_socket;

    static boolean end_signal = false;
    static boolean change_leader_signal = true;

    public static void main(String[] args) throws IOException {
        Server_operations so = new Server_operations();
        port = Integer.parseInt(args[0]);
        ServerSocket sSock = new ServerSocket(port);

        // set Server IP
        server_outMsg.server_IP = sSock.getInetAddress().getHostAddress();

        System.out.println("server: created socket with port number " +
                sSock.getLocalPort());

        // recognize socket
        ArrayList<Socket> sList = new ArrayList<>();
        sList.add(sSock.accept());
        sList.add(sSock.accept());
        sList.add(sSock.accept());
        int connection_count = 0;

        for (Socket s : sList){
            if ((s.getInetAddress().getHostAddress()).equals(Li_Luan_IP)){
                if (connection_count == 0) {
                    Li_socket = s;
                    System.out.println("li or luan connected");
                    connection_count += 1;
                }else{
                    Luan_socket = s;
                    System.out.println("li or luan connected");
                }
            } else{
                Van_socket = s;
                System.out.println("Van connected");
            }


        }

        while(!end_signal) {
             // get each role
            if(change_leader_signal) {
                DataInputStream Li_in = new DataInputStream(Li_socket.getInputStream());
                String Li_role = Li_in.readUTF();
                System.out.println("server: received Li role: " + Li_role);
                DataInputStream Luan_in = new DataInputStream(Luan_socket.getInputStream());
                String Luan_role = Luan_in.readUTF();
                System.out.println("server: received Luan role: " + Luan_role);
                DataInputStream Van_in = new DataInputStream(Van_socket.getInputStream());
                String Van_role = Van_in.readUTF();
                System.out.println("server: received Van role: " + Van_role);

                HashMap<Socket, String> roles = new HashMap<Socket, String>();
                roles.put(Li_socket, Li_role);
                roles.put(Luan_socket, Luan_role);
                roles.put(Van_socket, Van_role);

                for (HashMap.Entry<Socket, String> role : roles.entrySet()) {
                    if ((role.getValue()).equals("Leader")) {
                        Leader_socket = role.getKey();
                    } else if ((role.getValue()).equals("Inheritor")) {
                        Inheritor_socket = role.getKey();
                    } else {
                        Peasant_socket = role.getKey();
                    }
                }
            }
            //reset
            change_leader_signal = false;

            // get scroll_message from leader, send start message to all;
            DataInputStream in = new DataInputStream(Leader_socket.getInputStream());
            String buf = in.readUTF();
            System.out.println("server: received leader message: " + buf);
            so.scrollCall(Leader_socket, Inheritor_socket, Peasant_socket);
            // get finish stage 1 message from all, send start stage 2 message to all
            so.stage_1(Leader_socket, Inheritor_socket, Peasant_socket);
            // get finish stage 2 message from all, send start collect reward message to all
            so.stage_2(Leader_socket, Inheritor_socket, Peasant_socket);
            // get collect reward message from all
            so.reward_collect(Leader_socket, Inheritor_socket, Peasant_socket);
            // get change leader signal from leader
            change_leader_signal = so.get_change_leader_signal(Leader_socket);
            // send start "pass on leader" to leader, get finish passing leader from ex-leader
            if(change_leader_signal) {
                so.passing_leader_complete(Leader_socket);
            }
            // receive end signal from leader/ex-leader
            end_signal = so.end_signal(Leader_socket);
        }


    }
}