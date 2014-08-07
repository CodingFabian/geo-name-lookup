import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class LoadData {

  public static void main(String[] args) throws Exception {
    MongoClient mongo = new MongoClient(MongoConfig.MONGO_HOST);
    DB db = mongo.getDB(MongoConfig.DB_NAME);
    DBCollection collection = db.getCollection(MongoConfig.COLLECTION_NAME);
    collection.drop();

    // dump from http://download.geonames.org/export/dump/
    insertIntoCollection(collection, "/Users/fabian/Downloads/cities1000.txt");

    long count = collection.count();
    System.out.println("inserted " + count + " places");
  }

  private static void insertIntoCollection(DBCollection collection, String filename) throws IOException {
    try (Stream<String> allLines = Files.lines(Paths.get(filename))) {
      allLines.parallel().forEach(s -> insertInto(collection, s));
    }
    collection.createIndex(new BasicDBObject(MongoConfig.LOC, "2dsphere"));
  }

  private static void insertInto(DBCollection collection, String s) {
    // see http://download.geonames.org/export/dump/readme.txt
    String[] split = s.split("\t");

    String type = split[6];

    // only import places
    // http://www.geonames.org/export/codes.html
    if (!"P".equals(type)) {
      return;
    }

    // String pop = split[14];
    // look up only relevant places with more than 10000 people
    // avoid parsing it by checking for length of the population string
    // if (pop.length() < 5) {
    // return;
    // }
    String name = split[1];
    String lat = split[4];
    String lon = split[5];

    // see http://docs.mongodb.org/manual/tutorial/build-a-2dsphere-index/
    BasicDBObjectBuilder builder = BasicDBObjectBuilder.start();
    builder.add(MongoConfig.NAME, name);
    builder.push(MongoConfig.LOC).add(MongoConfig.TYPE_KEY, MongoConfig.TYPE_POINT);
    // mongodb might be the only one on the planet which uses longitude, latitude order
    builder.add(MongoConfig.COORDINATES_KEY, new double[] { Double.valueOf(lon).doubleValue(),
        Double.valueOf(lat).doubleValue() });

    collection.insert(builder.get());
  }
}
