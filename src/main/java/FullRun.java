import java.io.FileInputStream;

public class FullRun {

  public static void main(String[] args) throws Exception {
    FileInputStream stream;
    Coordinates coords;
    String nameOfLocation;

    stream = new FileInputStream("/Users/fabian/Downloads/locate-pizza.jpg");
    coords = ExtractGeo.getLocation(stream);
    nameOfLocation = LookupGeo.getNameOfLocation(coords);
    System.out.println("Pizza at: " + nameOfLocation);

    stream = new FileInputStream("/Users/fabian/Downloads/locate-mongo.jpg");
    coords = ExtractGeo.getLocation(stream);
    nameOfLocation = LookupGeo.getNameOfLocation(coords);
    System.out.println("MongoDB at:" + nameOfLocation);
  }

}
