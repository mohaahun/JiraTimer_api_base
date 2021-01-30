package hello;

import org.json.JSONObject;

public class Workentries {

    String project = "";
    String issueKey = "";
    String issueSummary = "";
    String user = "";
    String startTimeOfWorklog = "";
    int TimeSpent = 0;
    String dateAndTimeOfEdit = "";

    Workentries() {
        this.project = "";
        this.issueKey = "";
        this.issueSummary = "";
        this.user = "";
        this.startTimeOfWorklog = "";
        this.TimeSpent = 0;
        this.dateAndTimeOfEdit = "";
    }

    Workentries(String iproject, String iissueKey, String iissueSummary, String iuser, String istartTimeOfWorklog, int itimeSpent, String idateAndTimeOfEdit) {
        this.project = iproject;
        this.issueKey = iissueKey;
        this.issueSummary = iissueSummary;
        this.user = iuser;
        this.startTimeOfWorklog = istartTimeOfWorklog;
        this.TimeSpent = itimeSpent;
        this.dateAndTimeOfEdit = idateAndTimeOfEdit;
    }

    public JSONObject getJSONWorklog() {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("project", this.project);
        jsonObject.put("issueKey", this.issueKey);
        jsonObject.put("issueSummary", this.issueSummary);
        jsonObject.put("user", this.user);
        jsonObject.put("startTimeOfWorklog", this.startTimeOfWorklog);
        jsonObject.put("TimeSpent", this.TimeSpent);
        jsonObject.put("dateAndTimeOfEdit", this.dateAndTimeOfEdit);


        return jsonObject;
    }
}
