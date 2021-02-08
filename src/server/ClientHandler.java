package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class ClientHandler {

    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String name;

    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            doListen();
        } catch (IOException e) {
            throw new RuntimeException("SWW", e);
        }
    }

        public String getName() {
            return name;
        }

        private void doListen() {
            new Thread(() -> {
                try {
                    doAuth();
                    commands();
                } catch (Exception e) {
                    throw new RuntimeException("SWW", e);
                }
            }).start();
        }

        private void doAuth() {

            try {
                while (true) {
                    String credentials = in.readUTF();
                    /**
                     * Input credentials sample
                     * "-auth n1@mail.com 1"
                     */
                    if (credentials.startsWith("-auth")) {
                        /**
                         * After splitting sample
                         * array of ["-auth", "n1@mail.com", "1"]
                         */
                            String authValues = "-auth n1@mail.com 1";
                            if(credentials.equals(authValues)){
                                name = "n1";
                                System.out.println("Auth ok" + name);
                                sendMessage("Auth ok");
                                return;
                            }
                            else {
                                sendMessage("Not auth");
                            }
                    }
                }

            } catch (IOException e) {
                throw new RuntimeException("SWW", e);
            }

        }
        private void commands(){
            sendMessage("Введите номер команды или для просмотра команд введите 0");
            try {
                String message = in.readUTF();
                if(Integer.parseInt(message) ==0){
                    sendMessage(" 1 - Отправка файла\n 2 - Скачивание файла\n 3 - Удаление файла\n 4 - Переименование файла");
                }
            } catch (IOException e) {
                throw new RuntimeException("SWW", e);
            }

        }
//
//        private void receiveMessage() {
//            try {
//                while (true) {
//                    String message =name + ":" + in.readUTF();
//                    server.broadcastMessage(message);
//                }
//            }
//            catch (IOException e) {
//                throw new RuntimeException("SWW", e);
//            }
//        }


        public void sendMessage(String message) {
            try {
                out.writeUTF(message);
            } catch (IOException e) {
                throw new RuntimeException("SWW", e);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ClientHandler that = (ClientHandler) o;
            return Objects.equals(server, that.server) &&
                    Objects.equals(socket, that.socket) &&
                    Objects.equals(in, that.in) &&
                    Objects.equals(out, that.out) &&
                    Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(server, socket, in, out, name);
        }




}
