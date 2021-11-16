package ir.ac.iust;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class client {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the sentence for encryption:");
        String str = scanner.nextLine();


        try {
            Socket socket = new Socket("localhost", 1999);
            System.out.println("\n\n****************encryption****************\n");
            System.out.println("Connected to the encryption server(port:1999)");
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            int row = str.length()/2;
            if(str.length()%2 != 0)
                row++;

            outputStream.writeInt(row);
            outputStream.flush();

            int index = 0;
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < 2; j++)
                {
                    if(index == str.length()) {
                        outputStream.writeDouble(0x00);
                        outputStream.flush();
                    }
                    else {
                        outputStream.writeDouble((double) str.charAt(index));
                        outputStream.flush();
                    }
                    index++;
                }
            }
            System.out.println("data has been send to encryption Server...");

            System.out.println("encrypted matrix is : ");
            double encMtrx[][] = new double[row][2];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < 2; j++)
                {
                    encMtrx[i][j] = inputStream.readDouble();
                    System.out.print("\t"+ encMtrx[i][j] + "\t");
                }
                System.out.println();
            }


            //////////////////Decryption
            System.out.println("\n\n****************decryption****************\n");
            Socket socket2 = new Socket("localhost", 1378);
            System.out.println("Connected to the Decryption server(port:1378)");
            ObjectOutputStream outputStream2 = new ObjectOutputStream(socket2.getOutputStream());
            ObjectInputStream inputStream2 = new ObjectInputStream(socket2.getInputStream());

            outputStream2.writeInt(row);
            outputStream2.flush();

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < 2; j++) {
                    outputStream2.writeDouble(encMtrx[i][j]);
                    outputStream2.flush();
                }
            }
            System.out.println(" encrypted data has been send...");

            System.out.println("Waiting for the decrypted data ...");
            String response = (String) inputStream2.readObject();
            System.out.println("decrypted data is : "+response);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
