import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");


        GameProgress gameProgress1 = new GameProgress(20, 50, 2, 105);
        GameProgress gameProgress2 = new GameProgress(50, 70, 10, 1105);
        GameProgress gameProgress3 = new GameProgress(80, 40, 7, 505);

        newDi();
        saveGame("savegames/game1.dat", gameProgress1);
        saveGame("savegames/game2.dat", gameProgress2);
        saveGame("savegames/game3.dat", gameProgress3);

        zipFiles("savegames/zip1.zip", "savegames/game1.dat");
        zipFiles("savegames/zip2.zip", "savegames/game2.dat");
        zipFiles("savegames/zip3.zip", "savegames/game3.dat");

        openZip("savegames/zip1.zip", "savegames");
        openZip("savegames/zip2.zip", "savegames");
        openZip("savegames/zip3.zip", "savegames");

//        GameProgress gameProgress11 = openProgress("D:/Games/savegames/packed.txt");
//        GameProgress gameProgress22 = openProgress("D:/Games/savegames/packed.txt");
//        GameProgress gameProgress33 = openProgress("D:/Games/savegames/packed.txt");
//        System.out.println(gameProgress11.toString());

    }

    public static void saveGame(String path, GameProgress gameProgress) {
        try(FileOutputStream fileOutputStream = new FileOutputStream(path);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(gameProgress);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static GameProgress openProgress(String path){
        GameProgress gameProgress = null;
        try (FileInputStream fileInputStream = new FileInputStream(path);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)){
            gameProgress = (GameProgress) objectInputStream.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  gameProgress;
    }

    public static void zipFiles(String pathArchive, String pathFile) {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(pathArchive))) {
            FileInputStream fileInputStream = new FileInputStream(pathFile);
            File file = new File(pathFile);
            ZipEntry zipEntry = new ZipEntry("text.txt");
            zipOutputStream.putNextEntry(zipEntry);
            byte[] buffer = new byte[fileInputStream.available()];
            fileInputStream.read(buffer);
            zipOutputStream.write(buffer);
            zipOutputStream.closeEntry();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void openZip(String pathArchive, String pathFile){
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(pathArchive))){
            ZipEntry entry;
            String name;
            while ((entry = zipInputStream.getNextEntry()) != null){
                name = entry.getName();
                FileOutputStream fout = new FileOutputStream(pathFile + "/" + name );
                for (int i = zipInputStream.read(); i != -1; i = zipInputStream.read()) {
                    fout.write(i);
                }
                fout.flush();
                zipInputStream.closeEntry();
                fout.close();
            }
        }catch (Exception e) {
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