package nativeapp.data;

import nativeapp.game.Game;
import nativeapp.utilities.B64Converter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.nio.file.*;
import java.util.Comparator;
import java.util.function.Predicate;

/**
 * Created by panpr on 03.12.2017.
 */
public final class GamesDataController {
    private static String homeFolder = System.getProperty("user.home");
    private static String gameFolder = "/games";
    private static String folderName = "labGame";
    private static String fileName = "appData.json";

    public static boolean addGame(Game game) {
        createGameFolder(game);
        appGameDataToFolder(game);
        addGameToApplicationData(game);
        game.setLocal(true);
        return true;
    }

    public static JSONArray localGames() {
        return appData();
    }

    public static boolean createGameFolder(Game game) {
        new File(homeFolder, folderName + gameFolder + "/"
                + game.title.replace(" ", "")
                + "_"
                + game.version).mkdir();
        return true;
    }

    public static boolean appGameDataToFolder(Game game){
        try {
            ByteArrayInputStream fileStream = B64Converter.convertFile(
                    game.file, 23);

            OutputStream out = new FileOutputStream(
                    homeFolder+ "/" + folderName + gameFolder + "/"
                        + game.title.replace(" ","")
                        + "_" + game.version + "/game.html");

            byte[] buf = new byte[1024];
            int len;
            while((len = fileStream.read(buf)) >0 ){
                out.write(buf, 0, len);
            }
            fileStream.close();
            out.close();

            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public static boolean addGameToApplicationData(Game game) {
        try {
            JSONArray appData = (JSONArray) appData();

            JSONObject gameData = new JSONObject();
            gameData.put("title",game.title);
            gameData.put("download_dir_name",
                    game.title.replace(" ","") + "_" + game.version);
            gameData.put("isDownloaded", true);
            gameData.put("version", game.version);
            gameData.put("description", game.description);
            gameData.put("icon",game.icon);
            gameData.put("screenshot",game.screenshot);
            gameData.put("default_hash", game.default_hash);
            gameData.put("current_version_hash", game.current_version_hash);
            appData.add(gameData);

            saveAppDataFromJSON(appData);

        } catch (Exception e ) {
            System.out.println(e);
        }
        return false;
    }

    public static boolean removeGame(Game game) {
        removeGameFolder(game);
        removeGameFromApplicationData(game);
        game.setLocal(false);
        return true;
    }

    private static void removeGameFolder(Game game) {
        try {
            Path gamePath = FileSystems.getDefault().getPath(
                    homeFolder +  "/" + folderName + "/" +
                        gameFolder + "/" +game.getLocalFolder());
            Files.walk(gamePath, FileVisitOption.FOLLOW_LINKS)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .peek(System.out::println)
                .forEach(File::delete);
        } catch(Exception e) {
            System.out.println("Could not delete file!" + e.getLocalizedMessage());
        }
    }

    private static boolean removeGameFromApplicationData(Game game) {
        try {
            JSONArray appData = (JSONArray) appData();

            int index = 0;
            for (Object gameData : appData) {
                JSONObject localGame = (JSONObject) gameData;

                if(localGame.get("default_hash").toString().equalsIgnoreCase(
                        game.getDefault_hash())) {
                    appData.remove(index);
                    break;
                }
                index += 1;
            }

            saveAppDataFromJSON(appData);

        } catch (Exception e ) {
            System.out.println(e);
        }
        return false;
    }

    public static boolean updateGame() {
        return true;
    }

    public static boolean createGamesFolderIfNotAvailable() {
        if(!new File(homeFolder +  "/" + folderName + gameFolder).exists()) {
            return new File(homeFolder, folderName + gameFolder).mkdir();
        }
        return false;
    }

    public static boolean isGameAvailableOffline(String gameId) {
        // return isGameDataInFolder && isGameDataInAppData;
        return true;
    }

    public GamesDataController() throws Exception{
        boolean isFolderAvailable = createFolderIfNotExists();
        if(isFolderAvailable) {
            createDataFileIfNotAvailable();
            createGamesFolderIfNotAvailable();
        }
    }

    private static boolean saveAppDataFromJSON(JSONArray appDataJson) throws Exception{
        File dataFile = new File(homeFolder+"/"+folderName, fileName);
        BufferedWriter out = new BufferedWriter(new FileWriter(dataFile));

        try {
            out.write(appDataJson.toJSONString());
            return true;
        }
        finally {
            out.close();
            return false;
        }
    }
    private static JSONArray appData() {
        try {
            byte[] encoded = Files.readAllBytes(
                    Paths.get(homeFolder +  "/" + folderName + "/" + fileName));

            String data = new String(encoded, "UTF-8");

            JSONParser parser = new JSONParser();
            return (JSONArray) parser.parse(data);
        }catch (Exception e) {

        }
        return null;
    }

    private static boolean createFolderIfNotExists() {
        if(!new File(homeFolder +  "/" + folderName).exists()){
            return new File(homeFolder, folderName).mkdir();
        } else {
            return true;
        }
    }

    private static boolean createDataFileIfNotAvailable() throws Exception{
        File dataFile = new File(homeFolder+"/"+folderName, fileName);

        if(!dataFile.exists()){
            return createEmptyJsonFileInFile(dataFile);
        }
        return false;
    }

    private static boolean createEmptyJsonFileInFile(File file) throws Exception{
        BufferedWriter out = new BufferedWriter(new FileWriter(file));

        try {
            out.write("[]");
            return true;
        }
        finally {
            out.close();
            return false;
        }
    }

    public static String getHomeFolder() {
        return homeFolder;
    }

    public static void setHomeFolder(String homeFolder) {
        GamesDataController.homeFolder = homeFolder;
    }

    public static String getGameFolder() {
        return gameFolder;
    }

    public static void setGameFolder(String gameFolder) {
        GamesDataController.gameFolder = gameFolder;
    }

    public static String getFolderName() {
        return folderName;
    }

    public static void setFolderName(String folderName) {
        GamesDataController.folderName = folderName;
    }
}
