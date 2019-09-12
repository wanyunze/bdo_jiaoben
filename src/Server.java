// Java server program for ANU's comp3310 sockets Lab 
// Peter Strazdins, RSCS ANU, 03/18

import java.io.*;
import java.net.*;

public class Server {
    static int port = 3310;
    public static void main (String[] args) throws IOException {
	if (args.length >= 1)
	    port = Integer.parseInt(args[0]);
        ServerSocket sSock = new ServerSocket(port);
        System.out.println("server: created socket with port number " +
			   sSock.getLocalPort()); 

        Socket sock = sSock.accept();
        System.out.println("server: received connection from "   +
                           sock.getRemoteSocketAddress() + ", " +
                           "now on port " + sock.getLocalPort());

        Socket sock2 = sSock.accept();
        System.out.println("server: received connection from "   +
                sock2.getRemoteSocketAddress() + ", " +
                "now on port " + sock2.getLocalPort());

        DataInputStream in = new DataInputStream(sock.getInputStream());
        String buf = in.readUTF();
        System.out.println("server: received message: " + buf);


        DataInputStream in2 = new DataInputStream(sock2.getInputStream());
        String buf2 = in2.readUTF();
        System.out.println("server: received message: " + buf2);

        buf = buf + " and hello to you client";
        DataOutputStream out = new DataOutputStream(sock.getOutputStream());
        out.writeUTF(buf);
	System.out.println("server: sent message " + buf);

        buf2 = buf2 + " and hello to you client";
        DataOutputStream out2 = new DataOutputStream(sock2.getOutputStream());
        out2.writeUTF(buf2);
        System.out.println("server: sent message " + buf2);

        sock.close();
        sock2.close();
        sSock.close();
	System.out.println("server: closed sockets and terminating");
    }
}
