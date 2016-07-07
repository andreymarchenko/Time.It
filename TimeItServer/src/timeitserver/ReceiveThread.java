/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeitserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author Andrey
 */
public class ReceiveThread extends Thread {

    private static Logger log = Logger.getLogger(ReceiveThread.class.getName());
    Socket socket = null;
    InputStream serverInputStream = null;
    String login = null;
    String password = null;
    String adviceCategory = null;
    ResultSet checkedlogin = null;
    ResultSet checkedpair = null;
    boolean isClientDisconnect = false;
    boolean isAuthorized = false;

    public ReceiveThread(Socket s) {
        socket = s;

        if (socket != null) {
            try {
                serverInputStream = socket.getInputStream();
            } catch (IOException ex) {
                Logger.getLogger(ReceiveThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void sendReply(String reply) {
        if (socket != null) {
            try {
                OutputStream serverOutputStream = socket.getOutputStream();
                DataOutputStream dataServerOutputStream = new DataOutputStream(serverOutputStream);
                dataServerOutputStream.writeUTF(reply);
            } catch (IOException ex) {
                Logger.getLogger(ReceiveThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void register(String _login, String _password) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReceiveThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:BASE.db");
            log.info("Database is opened successfully");
        } catch (SQLException ex) {
            Logger.getLogger(ReceiveThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        PreparedStatement checkuser;
        String str = "";

        try {
            checkuser = connection.prepareStatement("SELECT login FROM CLIENTS WHERE login = ?; ");
            checkuser.setString(1, _login);
            checkedlogin = checkuser.executeQuery();

            while (checkedlogin.next()) {
                str = checkedlogin.getString(1);
                break;
            }

            checkuser.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReceiveThread.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (!str.equalsIgnoreCase(_login)) { // Если пользователя нет в БД
            PreparedStatement adduser;
            try {
                adduser = connection.prepareStatement("INSERT INTO CLIENTS (login, password)"
                        + " VALUES (?, ?); ");
                adduser.setString(1, _login);
                adduser.setString(2, _password);
                adduser.executeUpdate();
                adduser.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ReceiveThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            String reply = "RecvThread:" + _login + " has been registrated succefully";
            sendReply("RO");
            log.info(reply);
        } else {
            String reply = "RecvThread:" + _login + " has not been registrated";
            sendReply("RE"); // Пользователь с таким именем существует
            log.info(reply);
        }
    }

    public void authorize(String _login, String _password) {
        if (!isAuthorized) {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ReceiveThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            Connection connection = null;
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:BASE.db");
                log.info("Database is opened successfully");
            } catch (SQLException ex) {
                Logger.getLogger(ReceiveThread.class.getName()).log(Level.SEVERE, null, ex);
            }

            PreparedStatement checkpair;
            String str1 = "";
            String str2 = "";

            try {
                checkpair = connection.prepareStatement("SELECT login,password FROM CLIENTS WHERE login = ?; ");
                checkpair.setString(1, _login);
                checkedpair = checkpair.executeQuery();

                while (checkedpair.next()) {
                    str1 = checkedpair.getString(1);
                    str2 = checkedpair.getString(2);
                    break;
                }

                checkpair.close();
            } catch (SQLException ex) {
                Logger.getLogger(ReceiveThread.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (str1.equalsIgnoreCase(_login) && str2.equalsIgnoreCase(_password)) {

                login = _login;
                password = _password;
                isAuthorized = true;
                sendReply("AO");
                String reply = "ReceiveThread:" + login + " is authorized";
                log.info(reply);

            } else {
                String reply = "ReceiveThread:" + "Authorization failed for " + login;
                log.info(reply);
                sendReply("AN");
                isClientDisconnect = true;
            }
        } else {
            sendReply("AN");
        }
    }

    public void getAdvice(String _category) {
        if (_category.equalsIgnoreCase("health")) {

        }
        if (_category.equalsIgnoreCase("sport")) {

        }
        if (_category.equalsIgnoreCase("work")) {

        }
        if (_category.equalsIgnoreCase("rest")) {

        }
        if (_category.equalsIgnoreCase("nutrition")) {

        }
    }

    public void getStatistic() {
    }

    public void disconnect() {
        if (isAuthorized) {
            isAuthorized = false;
            try {
                isClientDisconnect = true;
                sendReply("DO");
                socket.close();
            } catch (IOException ex) {
                sendReply("DN");
                Logger.getLogger(ReceiveThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            sendReply("DN");
        }
    }

    public void wrongCommand() {
        sendReply("WC");
    }

    public void manageServer() {
        DataInputStream serverDataInputStream = new DataInputStream(serverInputStream);
        String login;
        String password;
        String clientsCommand;
        String category;

        try {
            clientsCommand = serverDataInputStream.readUTF();
            if (clientsCommand.equalsIgnoreCase("R")) {
                login = serverDataInputStream.readUTF();
                password = serverDataInputStream.readUTF();
                register(login, password);
            } else if (clientsCommand.equalsIgnoreCase("A")) {
                login = serverDataInputStream.readUTF();
                password = serverDataInputStream.readUTF();
                authorize(login, password);
            } else if (clientsCommand.equalsIgnoreCase("D")) {
                disconnect();
            } else if (clientsCommand.equalsIgnoreCase("GA")) {
                category = serverDataInputStream.readUTF();
                getAdvice(category);
            } else if (clientsCommand.equalsIgnoreCase("GS")) {
                getStatistic();
            } else {
                wrongCommand();
            }
        } catch (IOException ex) {
            Logger.getLogger(ReceiveThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (true) {
            manageServer();
            if (isClientDisconnect) {
                break;
            }
        }
    }
}
