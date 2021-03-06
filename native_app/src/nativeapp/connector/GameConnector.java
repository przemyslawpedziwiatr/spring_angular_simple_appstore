package nativeapp.connector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.ws.rs.client.Client;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 * Created by panpr on 09.12.2017.
 */
public class GameConnector {
  
  private JSONParser parser;
  
  public GameConnector() {
    parser = new JSONParser();
  }
  
  public JSONArray games() throws ParseException {
    Client client = ClientBuilder.newClient();
    WebTarget resource = client.target("http://localhost:8080/games");
    try {
      String response = resource.request(MediaType.APPLICATION_JSON).get(String.class);
      
      return (JSONArray) parser.parse(response);
    } catch (Exception e) {
      System.out.println(e);
      return (JSONArray) parser.parse("[]");
    }
  }
  
  public JSONObject gameByHash(String hash) throws ParseException {
    Client client = ClientBuilder.newClient();
    WebTarget resource = client.target("http://localhost:8080/game/" + hash);
    try {
      String response = resource.request(MediaType.APPLICATION_JSON).get(String.class);
      return (JSONObject) parser.parse(response);
    } catch (Exception e) {
      System.out.println(e);
      return (JSONObject) parser.parse("{}");
    }
  }
  
  public JSONObject getGameUpdateByHash(String defaultHash) throws ParseException {
    Client client = ClientBuilder.newClient();
    WebTarget resource = client.target("http://localhost:8080/game/" + defaultHash);
    
    try {
      String response = resource.request(MediaType.APPLICATION_JSON).get(String.class);
      return (JSONObject) parser.parse(response);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    } finally {
      return (JSONObject) parser.parse("{}");
    }
  }
}
