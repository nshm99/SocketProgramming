package ir.ac.iust;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    ClientHandler(Socket socket, ObjectInputStream inputStream, ObjectOutputStream outputStream) {
        this.socket = socket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    public void run() {
        try {
            double[][] pKey = {{1,2},{-1,2}};
            int row = inputStream.readInt();

            for (int i = 0; i < row; i++) {
                double s1=0,s2=0;
                for (int j = 0; j < 2; j++) {
                    double m1,m2;
                    double input = inputStream.readDouble();
                    m1 = input * pKey[j][0];
                    m2 = input * pKey[j][1];
                    s1+=m1;
                    s2+=m2;
                }
                outputStream.writeDouble(s1);
                outputStream.flush();

                outputStream.writeDouble(s2);
                outputStream.flush();

            }
            System.out.println("The encrypted matrix has been sent to client");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
