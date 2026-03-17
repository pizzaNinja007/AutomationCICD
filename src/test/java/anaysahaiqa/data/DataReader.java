package anaysahaiqa.data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * DataReader
 * ----------
 * This utility class is responsible for reading test data
 * from external JSON files and converting it into Java objects.
 *
 * It supports data-driven testing by supplying structured
 * test data to TestNG test methods.
 */
public class DataReader {

    /**
     * Reads JSON test data from PurchaseOrder.json file
     * and converts it into a List of HashMaps.
     *
     * Each HashMap represents one test data set
     * with key-value pairs corresponding to JSON attributes.
     *
     * @return List of HashMap<String, String> containing test data
     * @throws IOException if file reading or parsing fails
     */
    public List<HashMap<String, String>> getJsonData() throws IOException {

        // Read JSON file content as String
        String jsonContent = FileUtils.readFileToString(
                new File(System.getProperty("user.dir")
                        + "\\src\\test\\java\\anaysahaiqa\\data\\PurchaseOrder.json"),
                StandardCharsets.UTF_8
        );

        // Create ObjectMapper instance for JSON parsing
        ObjectMapper mapper = new ObjectMapper();

        // Convert JSON String to List of HashMaps
        List<HashMap<String, String>> data =
                mapper.readValue(
                        jsonContent,
                        new TypeReference<List<HashMap<String, String>>>() {
                        }
                );

        return data;
    }
}
