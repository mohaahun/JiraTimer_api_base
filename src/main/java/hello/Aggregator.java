package hello;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class Aggregator {

    public JSONArray getAggregatedData(JSONArray inarray) {

        System.out.println(inarray);

        JSONArray aggregated = new JSONArray();
        aggregated.put(byIssue(inarray));
        aggregated.put(byProject(inarray));
        aggregated.put(byPerson(inarray));
        return aggregated;
    }

    private static JSONObject byProject(JSONArray in) {

        HashMap<String, Integer> projectAggr = new HashMap<>();

        for (int i = 0; i < in.length(); i++) {
            int temptime = 0;
            String tempproj = "";
            JSONObject tempobj = (JSONObject) in.get(i);

            temptime = (int) tempobj.get("TimeSpent");
            tempproj = String.valueOf(tempobj.get("project"));

            if (projectAggr.containsKey(tempproj)) {
                projectAggr.replace(tempproj, temptime + projectAggr.get(tempproj));
            } else {
                projectAggr.put(tempproj, temptime);
            }
        }

        JSONObject projectaggreg = new JSONObject(projectAggr);
        projectaggreg = new JSONObject("{project:" + projectaggreg.toString() + "}");

        return projectaggreg;

    }

    private static JSONObject byPerson(JSONArray in) {

        HashMap<String, Integer> personAggr = new HashMap<>();

        for (int i = 0; i < in.length(); i++) {
            int temptime = 0;
            String tempname = "";
            JSONObject tempobj = (JSONObject) in.get(i);

            temptime = (int) tempobj.get("TimeSpent");
            tempname = String.valueOf(tempobj.get("user"));

            if (personAggr.containsKey(tempname)) {
                personAggr.replace(tempname, temptime + personAggr.get(tempname));
            } else {
                personAggr.put(tempname, temptime);
            }
        }

        JSONObject personaggreg = new JSONObject(personAggr);
        personaggreg = new JSONObject("{person:" + personaggreg.toString() + "}");

        return personaggreg;

    }

    public static JSONObject byIssue(JSONArray in) {

        HashMap<String, Integer> issueAggr = new HashMap<>();

        for (int i = 0; i < in.length(); i++) {
            System.out.println("issue " + i + "= " + in.get(i));
            int temptime = 0;
            String tempID = "";
            if (in.get(i).toString().length() > 3) {
                JSONObject tempobj = (JSONObject) in.get(i);
                temptime = tempobj.getInt("TimeSpent") / 60;
                tempID = String.valueOf(tempobj.get("issueKey"));
            }
            if (issueAggr.containsKey(tempID)) {
                issueAggr.replace(tempID, temptime + issueAggr.get(tempID));
            } else {
                issueAggr.put(tempID, temptime);
            }

        }

        for (int i = 0; i < issueAggr.size(); i++) {
            System.out.println(issueAggr.entrySet().toArray()[i].toString());

        }


        JSONObject issueaggreg = new JSONObject(issueAggr);
        issueaggreg = new JSONObject("{issue:" + issueaggreg + "}");

        return issueaggreg;
    }
}
