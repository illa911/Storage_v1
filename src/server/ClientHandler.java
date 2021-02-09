package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Stream;

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
                     * "-auth user 1"
                     */
                    if (credentials.startsWith("-auth")) {
                        /**
                         * After splitting sample
                         * array of ["-auth", "user", "1"]
                         */
                            String authValues = "-auth user 1";
                            if(credentials.equals(authValues)){
                                name = "User";
                                System.out.println(name +" auth ok");
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
            sendMessage(" 1 - Отправка файла\n 2 - Скачивание файла\n 3 - Удаление файла\n 4 - Переименование файла");
            try {
                Stream<Path> stream;
                stream = Files.walk(Path.of("C:\\Storage_v1\\src\\file\\"));
                sendMessage("Содержимое вашего хранилища:");
                stream.forEach(x -> sendMessage(String.valueOf(x)));
                String message = in.readUTF();
                String close = "close";
                int number = Integer.parseInt(message);
                Commands com = new Commands();
                    if (number == 1) {
                        System.out.println("1");
                    } else if (number == 2) {
                        System.out.println("2");
                    } else if (number == 3) {
                        sendMessage("Введите название файла:");
                        String nameFile = in.readUTF();
                        com.deleteFile(nameFile);
                    } else if (number == 4) {
                        sendMessage("Введите название файла и на что хотите его переименовать через пробел:");
                        String nameAndRenameFile = in.readUTF();
                        String[] mas = nameAndRenameFile.split("\\s");
                        String nameFile = mas[0];
                        String renameFile = mas[1];
                        com.renameFile(nameFile, renameFile);
                    }

            } catch (IOException e) {
                throw new RuntimeException("SWW", e);
            }

        }

        public void sendMessage(String message) {
            try {
                out.writeUTF(message);
            } catch (IOException e) {
                throw new RuntimeException("SWW", e);
            }
        }



}
