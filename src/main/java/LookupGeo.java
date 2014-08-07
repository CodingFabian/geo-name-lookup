import java.net.UnknownHostException;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.MongoClient;

public class LookupGeo {

  public static void main(String[] args) throws Exception {
    Coordinates coordinates = new Coordinates(11.555651, 48.131432);
    String location = getNameOfLocation(coordinates);
    System.out.println(location);
  }

  public static String getNameOfLocation(Coordinates coords) throws UnknownHostException {
    MongoClient mongo = new MongoClient(MongoConfig.MONGO_HOST);
    DB db = mongo.getDB(MongoConfig.DB_NAME);

    BasicDBObjectBuilder builder = BasicDBObjectBuilder.start();
    builder.add("geoNear", MongoConfig.COLLECTION_NAME).add("spherical", true);
    builder.push("near").add(MongoConfig.TYPE_KEY, MongoConfig.TYPE_POINT);
    // mongodb might be the only one on the planet which uses longitude, latitude order
    builder.add(MongoConfig.COORDINATES_KEY, new double[] { coords.longitude, coords.latitude });

    CommandResult command = db.command(builder.get());
    BasicDBList results = (BasicDBList) command.get("results");
    BasicDBObject firstResult = (BasicDBObject) results.get(0);
    BasicDBObject firstResultObject = (BasicDBObject) firstResult.get("obj");
    String location = firstResultObject.getString(MongoConfig.NAME);
    return location;
  }
}
