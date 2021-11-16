package ir.ac.iust;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler2 extends Thread {
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    ClientHandler2(Socket socket, ObjectInputStream inputStream, ObjectOutputStream outputStream) {
        this.socket = socket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    public void run() {
        try {
            double[][] pKey = {{0.5,-0.5},{0.25,0.25}};
            double row = inputStream.readInt();
            String output = "";
            for (int i = 0; i < (int) row; i++) {
                double s1=0,s2=0;
                for (int j = 0; j < 2; j++)
                {
                    double m1,m2;
                    double input = inputStream.readDouble();
                    m1 = input * pKey[j][0];
                    m2 = input * pKey[j][1];

                    s1+=m1;
                    s2+=m2;
                }
                if(s1!= 0)
                    output+=(char)s1;
                if(s2!= 0)
                    output+=(char)s2;
            }
            outputStream.writeObject(output);
            outputStream.flush();
            System.out.println("The decrypted data has been sent to client");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
