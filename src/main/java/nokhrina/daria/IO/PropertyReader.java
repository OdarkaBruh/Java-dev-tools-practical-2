package nokhrina.daria.IO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class PropertyReader {
    private String path = "";

    /**
     * Sets the default path for the property reader.
     * @param path new default path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * //     * Reads properties from the file which is located INSIDE the jar
     * //     *
     * //     * @param fileName the file to read
     * //     * @return received properties
     * //
     */
    public Properties getFromFile(String fileName) throws IOException {
        String fullName = path + fileName + ".properties";
        Properties props = new Properties();
        try (InputStreamReader inputStream = new InputStreamReader(new FileInputStream(fullName), StandardCharsets.UTF_8)) {
            props.load(inputStream);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File with name \""+ fileName + "\" does not exist.");
        }
        return props;
    }

    public String getFromFile(String filename, String propertyName) throws IOException {
        return getFromFile(filename).getProperty(propertyName);
    }

    public String getSystem(String propertyName) {
        return System.getProperty(propertyName);
    }

}
