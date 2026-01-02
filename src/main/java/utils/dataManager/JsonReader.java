package utils.dataManager;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jayway.jsonpath.JsonPath;
import org.json.simple.parser.JSONParser;
import utils.logs.LogsManager;
import org.json.simple.JSONObject;

import java.io.FileReader;

public class JsonReader {
    private final String TEST_DATA_PATH="src/test/resources/testData";
    String jsonReader;
    String jsonFileName;

    public JsonReader(String jsonFileName){
        this.jsonFileName=jsonFileName;
        try{
            JSONObject data=(JSONObject) new JSONParser().parse(new FileReader(TEST_DATA_PATH+jsonFileName));
            jsonReader=data.toJSONString();
        }
        catch (Exception e){
            LogsManager.error("Failed to read JSON file: ",jsonFileName," Error: ",e.getMessage());
            jsonReader="{}"; // Initialize with empty JSON object to avoid null pointer exceptions
        }
    }

    public String getJsonData(String jsonPath){
        try{
            return JsonPath.read(jsonReader,jsonPath);
        } catch (Exception e){
            LogsManager.error("Failed to get JSON data for path: ",jsonPath," Error: ",e.getMessage());
            return null;
        }
    }

}
