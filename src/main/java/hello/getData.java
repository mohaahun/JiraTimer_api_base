package hello;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class getData {
    private static DateTimeFormatter jiraDateformat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    public JSONArray accessAPI(String loginUserName, String sdate, String edate, String qtext) {

        //get formatted times
        ZonedDateTime d1time = ZonedDateTime.parse(sdate + "T00:00:00+02:00");
        ZonedDateTime d2time = ZonedDateTime.parse(edate + "T23:59:59+02:00");

        //search issues that have appropriate worklog entrie(s)
        String searchPart = "?jql=worklogDate>=" + sdate + "&worklogDate<=" + edate + "&" + qtext;
        searchPart.replaceAll("=", "%3D");
        //get all issues between the dates with authorization
        urlConnector urlConnect = new urlConnector();

        //get issue adresses
        try {
            URL url = new URL("https://adamlaszlomohacsi.atlassian.net//rest/api/3/search" + searchPart);

            JSONObject mainSearch = urlConnect.getJsonFromUrl(url, loginUserName);
            ArrayList<URL> issuelinks = new ArrayList<>();
            for (int i = 0; i < mainSearch.getJSONArray("issues").length(); i++) {
                issuelinks.add(new URL(mainSearch.getJSONArray("issues").getJSONObject(i).get("self").toString()));
            }

            //check and collect worklogs between date constraints
            ArrayList<Workentries> worklogs = new ArrayList<>();
            for (URL a : issuelinks) {

                JSONObject temp = urlConnect.getJsonFromUrl(a, loginUserName);
                JSONArray logs = temp.getJSONObject("fields").getJSONObject("worklog").getJSONArray("worklogs");

                for (int i = 0; i < temp.getJSONObject("fields").getJSONObject("worklog").getJSONArray("worklogs").length(); i++) {
                    //get time and check if it's in specified timeframe
                    String startTimeOfWorklog = ((JSONObject) logs.get(i)).get("started").toString();
                    ZonedDateTime starttime = ZonedDateTime.parse(startTimeOfWorklog, jiraDateformat);

                    //if worklog is between time constraints collect data and add to array
                    if (starttime.isAfter(d1time) & starttime.isBefore(d2time)) {
                        String project = temp.getJSONObject("fields").getJSONObject("project").get("key").toString();
                        String issueKey = temp.get("key").toString();
                        String issueSummary = temp.getJSONObject("fields").get("summary").toString();
                        String user = ((JSONObject) logs.get(i)).getJSONObject("updateAuthor").get("displayName").toString();
                        int timeSpent = (int) ((JSONObject) logs.get(i)).get("timeSpentSeconds");
                        String dateAndTimeOfEdit = ((JSONObject) logs.get(i)).get("updated").toString();
                        worklogs.add(new Workentries(project, issueKey, issueSummary, user, startTimeOfWorklog, timeSpent, dateAndTimeOfEdit));
                    }
                }
            }
            JSONArray workarray = new JSONArray();
            for (int i = 0; i < worklogs.size(); i++) {

                JSONObject tempjson = worklogs.get(i).getJSONWorklog();
                workarray.put(tempjson);
            }
            return workarray;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
