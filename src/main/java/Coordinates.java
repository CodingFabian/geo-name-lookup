public class Coordinates {
  public final double latitude;
  public final double longitude;

  public Coordinates(String latitude, String longitude) {
    this(Double.valueOf(latitude).doubleValue(), Double.valueOf(longitude).doubleValue());
  }

  public Coordinates(double latitude, double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }
}
