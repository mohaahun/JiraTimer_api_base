package hello;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.*;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;

public class Caller implements RequestStreamHandler {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        LambdaLogger logger = context.getLogger();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8"))); //Charset.forName("US-ASCII")
        PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream, Charset.forName("UTF-8"))));
        try
        {
            HashMap event = gson.fromJson(reader, HashMap.class);
            logger.log("STREAM TYPE: " + inputStream.getClass().toString());
            logger.log("EVENT TYPE: " + event.getClass().toString());
           /* JsonElement mailaddr = new JsonParser().parse(reader);
            mailaddr.getAsJsonObject().get("mail");
            writer.write(mailaddr.getAsString());*/
           // writer.write(gson.toJson(event.get("mail")));
            writer.write(gson.toJson(event.get("body-json")));
           // writer.write(gson.toJson(event.get("startdate")));
           // writer.write(gson.toJson(event.get("enddate")));
           // writer.write(gson.toJson(event.get("querrytext")));
            if (writer.checkError())
            {
                logger.log("WARNING: Writer encountered an error.");
            }


        }
        catch (IllegalStateException | JsonSyntaxException exception)
        {
            logger.log(exception.toString());
        }
        finally
        {
            reader.close();
            writer.close();
        }
    }
}

