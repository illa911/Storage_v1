package server;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;


public class Commands {
    private String filePath = "src\\file\\"; // Для проверки можно указать свой путь к файлам, так же если меняется путь то надо поменять в ClientHandler
    private String dounloadPath = "C:\\Users\\Downloads\\"; //Путь куда загружается файл

    public void loadingFile(String nameFile) throws IOException {
        RandomAccessFile aFile = new RandomAccessFile(nameFile, "rw");
        FileChannel inChannel = aFile.getChannel();
        ByteBuffer buf = ByteBuffer.allocate(48);
        int bytesRead = inChannel.read(buf);
        while (bytesRead != -1) {
            buf.flip();
            while(buf.hasRemaining()){
                System.out.print((char) buf.get());
            }
            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        aFile.close();

        //Пока недоделано
        }

    public void downloadingFile(String nameFile) {
        Path sourcePath = Paths.get(filePath + nameFile);
        Path destinationPath = Paths.get(dounloadPath + nameFile);

        try {
            Files.copy(sourcePath, destinationPath);
        } catch (FileAlreadyExistsException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void deleteFile (String nameFile) throws IOException {


        Path path = Paths.get(filePath+nameFile);

        try {
            Files.delete(path);
        } catch (IOException e) {
            // Не удалось удалить файл
            e.printStackTrace();
        }
        System.out.println("File delete: "+nameFile);
    }

    public void renameFile(String nameFile, String renameFile) {
        System.out.println(nameFile+" "+renameFile); // для тестирования
        Path folder = Paths.get(filePath);
        File file = folder.resolve(filePath+nameFile).toFile();
        File newFile = folder.resolve(filePath+renameFile).toFile();
        if(file.renameTo(newFile)){
            System.out.println("Файл переименован успешно");;
        }else{
            System.out.println("Файл не был переименован");
        }

    }
    public void walkFile(String nameFile) {

        Path rootPath = Paths.get(filePath);
        String fileToFind = File.separator + nameFile;

        try {
            Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    String fileString = file.toAbsolutePath().toString();
                    //System.out.println("pathString = " + fileString);

                    if(fileString.endsWith(fileToFind)){
                        System.out.println("Файл найден: " + file.toAbsolutePath());
                        return FileVisitResult.TERMINATE;
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch(IOException e){
            e.printStackTrace();
        }

    }

}
