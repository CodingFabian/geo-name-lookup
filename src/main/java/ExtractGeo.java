import java.io.FileInputStream;
import java.io.IOException;

import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;

public class ExtractGeo {

  public static void main(String[] args) throws Exception {
    FileInputStream stream = new FileInputStream("/Users/fabian/Downloads/locate-pizza.jpg");

    Coordinates coords = getLocation(stream);

    System.out.println("Pizza at: " + coords.latitude + ";" + coords.longitude);
  }

  public static Coordinates getLocation(FileInputStream stream) throws IOException {
    Tika tika = new Tika();
    Metadata metadata = new Metadata();
    tika.parse(stream, metadata);

    String lat = metadata.get(TikaCoreProperties.LATITUDE);
    String lon = metadata.get(TikaCoreProperties.LONGITUDE);

    return new Coordinates(lat, lon);
  }
}
