package hello;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Map;

public class Maptest implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {


        System.out.println(input.getBody());
        //gson.toJson(input.getBody());
        //JsonObject jsonObject = gson.toJson(input.getBody());
        //System.out.println(gson);

        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject request = (JSONObject) jsonParser.parse(input.getBody().replace("}}","}"));
            System.out.println(request);

            String mail = request.get("mail").toString();
            String sdate = request.get("startdate").toString();
            String edate = request.get("enddate").toString();
            String qtext = request.get("querrytext").toString();

            getData getData = new getData();
           // ArrayList<Workentries> workentries = getData.accessAPI(mail,sdate,edate,qtext);
            //JSONArray workentriesJSON = getData.accessAPI(mail,sdate,edate,qtext);
            JsonArray workentriesJSON = getData.accessAPI(mail,sdate,edate,qtext);


            APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
            Map<String, String> headers = new HashMap<String, String>();
            headers.put("Content-Type", "application/json");
            headers.put("Access-Control-Allow-Origin","*");
            response.withStatusCode(213).withHeaders(headers).withBody(String.valueOf(workentriesJSON));

            return response;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        // System.out.println(jsonObject);

        APIGatewayProxyResponseEvent badresponse = new APIGatewayProxyResponseEvent();

        return badresponse.withBody("Something is wrong!");
    }
}
