/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeitserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrey
 */
public class ServerThread extends Thread {

    private static Logger log = Logger.getLogger(ServerThread.class.getName());
    ServerSocket serverSocket;
    Socket socket;
    int port = 4445;
    InetAddress ip = null;
    boolean isStopped = true;
    InputStream serverInputStream;
    OutputStream serverOutputStream;

    public ServerThread() {
        try {
            ip = InetAddress.getLocalHost();
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, "ServerThread: Error in getLocalHost() function", ex);
        }
        try {
            serverSocket = new ServerSocket(port, 0, ip);
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, "ServerThread: Error in create server_socket", ex);
        }
    }

    @Override
    public void run() {
        while (!isStopped) {
            try {
                socket = serverSocket.accept();
                log.info("Connection with client is established");
            } catch (IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, "ServerThread: Can't accept", ex);
            }
        }
        ReceiveThread receiveThread = new ReceiveThread(socket);
            receiveThread.start();        
    }

    synchronized void startServer() {
        isStopped = false;
        log.info("Server is successfully started");
        start();
    }

    synchronized void stopServer() {
        isStopped = true;
        log.info("Server is successfully stopped");
        stop();

        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
