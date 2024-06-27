import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");
        newDi();

        GameProgress gameProgress1 = new GameProgress(20, 50, 2, 105);
        GameProgress gameProgress2 = new GameProgress(50, 70, 10, 1105);
        GameProgress gameProgress3 = new GameProgress(80, 40, 7, 505);

    }

    public static void saveGame(String path, GameProgress gameProgress) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(gameProgress);
            objectOutputStream.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String pathArchive, String pathFile) {

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(pathArchive))) {
            FileInputStream fileInputStream = new FileInputStream(pathFile);
            ZipEntry zipEntry = new ZipEntry("packed.txt");
            zipOutputStream.putNextEntry(zipEntry);
            byte[] buffer = new byte[fileInputStream.available()];
            fileInputStream.read(buffer);
            zipOutputStream.write(buffer);

//            int byteRead = fileInputStream.read(buffer);
//            while (byteRead != -1) {
//                System.out.println((char) byteRead);
//                byteRead = fileInputStream.read();
//            }
//            zipOutputStream.write(byteRead);

            zipOutputStream.closeEntry();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }

    public static void newDi() throws IOException {
        StringBuilder builder = new StringBuilder();

        String currentDir = System.getProperty("user.dir");
        System.out.println(currentDir);

        File dirUser = new File(currentDir);
        File dir1 = newDir(dirUser, "src", builder);
        File dir2 = newDir(dirUser, "res", builder);
        File dir3 = newDir(dirUser, "savegames", builder);
        File dir4 = newDir(dirUser, "temp", builder);

        File dir11 = newDir(dir1, "main", builder);
        File dir12 = newDir(dir1, "test", builder);
        File dir21 = newDir(dir2, "drawables", builder);
        File dir22 = newDir(dir2, "vectors", builder);
        File dir23 = newDir(dir2, "icons", builder);

        newFile(dir11, "Main.java", builder);
        newFile(dir11, "Utils.java", builder);
        newFile(dir4, "temp.txt", builder);

        String w = builder.toString();
        System.out.println(builder.toString());
        FileWriter fileWriter = new FileWriter("temp/temp.txt");
        fileWriter.write(w);
        fileWriter.close();
    }

    public static File newDir(File dir, String path, StringBuilder builder) {
        File newDir1 = new File(dir, path);
        if (newDir1.mkdir()) {
            builder.append("Дирректория " + path + " создана по пути - ").append(newDir1).append("\n");
        }
        return newDir1;
    }

    public static void newFile(File dir, String name, StringBuilder builder) {
        try {
            File file = new File(dir, name);
            if (file.createNewFile()) {
                builder.append("Файл " + name + " создан по пути - ").append(file).append("\n");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}