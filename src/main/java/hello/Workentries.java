package hello;

import com.google.gson.JsonObject;

public class Workentries {

    String project = "";
    String issueKey = "";
    String issueSummary = "";
    String user = "";
    String startTimeOfWorklog = "";
    String TimeSpent = "";
    String dateAndTimeOfEdit = "";

    Workentries(){
        this.project = "";
        this.issueKey = "";
        this.issueSummary = "";
        this.user = "";
        this.startTimeOfWorklog = "";
        this.TimeSpent = "";
        this.dateAndTimeOfEdit = "";
    }

    Workentries(String iproject, String iissueKey, String iissueSummary, String iuser, String istartTimeOfWorklog, String itimeSpent, String idateAndTimeOfEdit){
        this.project = iproject;
        this.issueKey = iissueKey;
        this.issueSummary = iissueSummary;
        this.user = iuser;
        this.startTimeOfWorklog = istartTimeOfWorklog;
        this.TimeSpent = itimeSpent;
        this.dateAndTimeOfEdit = idateAndTimeOfEdit;
    }

    public JsonObject getJSONWorklog(){

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("project",this.project);
        jsonObject.addProperty("issueKey",this.issueKey);
        jsonObject.addProperty("issueSummary",this.issueSummary);
        jsonObject.addProperty("user",this.user);
        jsonObject.addProperty("startTimeOfWorklog",this.startTimeOfWorklog);
        jsonObject.addProperty("TimeSpent",this.TimeSpent);
        jsonObject.addProperty("dateAndTimeOfEdit",this.dateAndTimeOfEdit);


        return jsonObject;
    }
}
