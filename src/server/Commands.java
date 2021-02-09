package server;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;


public class Commands {
    private String filePath = "C:\\Storage_v1\\src\\file\\"; // Для проверки можно указать свой путь к файлам, так же если меняется путь то надо поменять в ClientHandler

    public  void loadingFile(int command){
        //Пока недоделано
        }

    public static void downloadingFile(int command){
        // Пока недоделано
    }

    public  void deleteFile (String nameFile) throws IOException {
        Files.delete(Path.of(filePath+nameFile));
        System.out.println("File delete: "+nameFile);

        // Еще добавлю исключения
    }

    public void renameFile(String nameFile, String renameFile){
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

}
