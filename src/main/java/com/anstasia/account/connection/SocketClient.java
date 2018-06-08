package com.anstasia.account.connection;

import com.anstasia.account.controller.Controller;
import com.anstasia.account.model.Account;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class SocketClient implements Serializable {

    private ObjectOutputStream out;
    private ObjectInputStream in;

    public SocketClient() throws IOException {

        int serverPort = 6666; // здесь обязательно нужно указать порт к которому привязывается сервер.
        String address = "127.0.0.1"; // это IP-адрес компьютера, где исполняется наша серверная программа.// Здесь указан адрес того самого компьютера где будет исполняться и клиент.
        InetAddress ipAddress = InetAddress.getByName(address); // создаем объект который отображает вышеописанный IP-адрес.

        Socket socket = new Socket(ipAddress, serverPort);
        System.out.println("Yes! I just got hold of the program.");

        //   Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиентом.
        InputStream sin = socket.getInputStream();
        OutputStream sout = socket.getOutputStream();

        //  Koнвертируем потоки в другой тип, чтоб легче обрабатывать  данные.
        out = new ObjectOutputStream(sout);
        in = new ObjectInputStream(sin);
    }

    public void generateDataObjectA() {
        try {
            out.writeObject("generateDataObjectA"); // отсылаем введенную строку текста серверу.
            System.out.println("отослали строку");
            out.flush(); // заставляем поток закончить передачу данных.
            //line = in.readUTF(); // ждем пока сервер отошлет строку текста.
            Object obj = in.readObject();

            System.out.println(obj);
            System.out.println(obj.getClass());
            //Generic<Account> gAc = new Generic<Account>(obj);
            Controller.getInstance().setObjectA((ArrayList<Account>)obj);
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }
    }

//    class Generic<T>{// указали что класс будет параметризированный любой ссылочный тип
//        T ob;// Объект типа буква Т потому что это параметр типа(неформальное солашение)
//        Generic (T ob){
//            this.ob=ob;
//        }
//        T getOb(){return ob;}
//        void showType(){
//            System.out.println("Типом Т является  " + ob.getClass().getName() );
//        }

    public void addNewAccount(String startBalance, String name){
        try {
            out.writeObject("addNewAccount");
            Account account = new Account(startBalance,name);
            out.writeObject(account); // отсылаем введенную строку текста серверу.
            out.flush();
            System.out.println(account);
            generateDataObjectA();
        }catch (IOException e){}
    }
}

//        out.writeObject("a"); // отсылаем введенную строку текста серверу.
//        System.out.println("отослали строку");
//        out.flush(); // заставляем поток закончить передачу данных.
//        //line = in.readUTF(); // ждем пока сервер отошлет строку текста.
//        line = in.readObject();
//        Controller.getInstance().setObjectA((ArrayList<Account>) in.readObject());
