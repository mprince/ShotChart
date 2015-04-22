import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author marcelprince
 */
public class CvsToJson {

    public String x;
    public String y;
    public String result;
    
    //Delimiter used in CSV file
    private static final String COMMA_DELIMITER = ",";
	
    //Shot attributes index
    private static final int TEAM = 12;
    private static final int ETYPE = 13;
    private static final int PLAYER = 23;
    private static final int RESULT = 27;
    private static final int X = 30;
    private static final int Y = 31;
    
    public CvsToJson(String x, String y, String result) {
        this.x = x;
        this.y = y;
        this.result = result;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String dir = System.getProperty("user.dir") + "/";
        String filePath = dir + args[0];
        // Get Shots
        BufferedReader fileReader = null;
        BufferedWriter fileWriter = null;
        try {
            String line;
     
            //Create the file reader
            fileReader = new BufferedReader(new FileReader(filePath));
            //Create the file writer
            fileWriter = new BufferedWriter(new FileWriter(args[1]));
            String home = args[0].substring(9,12);
            String away = args[0].substring(12,15);
            
            //Read the CSV file header to skip it
            fileReader.readLine();
            //Write Json File
            fileWriter.write("{\n\"shots\": [\n");
            //Read the file line by line starting from the second line
            while ((line = fileReader.readLine()) != null) {
                String[] data = line.split(COMMA_DELIMITER);
                if (data[ETYPE].equals("shot")) {
                    fileWriter.write("{\n\"x\": ");fileWriter.write(data[X]);fileWriter.write(",\n");
                    fileWriter.write("\"y\": ");fileWriter.write(data[Y]);fileWriter.write(",\n");
                    fileWriter.write("\"made\": ");fileWriter.write('"' + data[RESULT] + '"');fileWriter.write("\n},\n");
                }
            }
            fileWriter.write("]\n}");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (fileReader != null)
                try {
                    fileReader.close();
                    fileWriter.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
