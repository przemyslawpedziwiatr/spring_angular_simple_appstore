package nativeapp.files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

/**
 * Created by panpr on 03.12.2017.
 */
public class FileController {
    private String homeFolder = System.getProperty("user.home");
    private String folderName = "labGame";
    private String fileName = "appData.json";

    public boolean addGame() {
        // createGameFolder()
        // addGameDataToFolder();
        // addGameToApplicationData();
        return true;
    }

    public boolean removeGame() {
        // removeGameFolder()
        // removeGameFromApplicationData()
        return true;
    }

    public boolean isGameAvailableOffline(String gameId) {
        // return isGameDataInFolder && isGameDataInAppData;
        return true;
    }

    public FileController() throws Exception{
        boolean isFolderAvailable = createFolderIfNotExists();
        if(isFolderAvailable) {
            createDataFileIfNotAvailable();
//            createDataFolderIfNotAvailable();
        }
    }

    private boolean createFolderIfNotExists() {
        if(!new File(homeFolder +  "/" + folderName).exists()){
            return new File(homeFolder, folderName).mkdir();
        } else {
            return true;
        }
    }

    private boolean createDataFileIfNotAvailable() throws Exception{
        File dataFile = new File(homeFolder+"/"+folderName, fileName);

        if(!dataFile.exists()){
            return createEmptyJsonFileInFile(dataFile);
        }
        return false;
    }

    private boolean createEmptyJsonFileInFile(File file) throws Exception{
        BufferedWriter out = new BufferedWriter(new FileWriter(file));

        try {
            out.write("{}");
            return true;
        }
        finally {
            out.close();
            return false;
        }
    }

}
