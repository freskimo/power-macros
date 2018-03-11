package burp;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.List;

public class ExtractReplaceScript extends ExtractReplaceMethod {
    protected String scriptPath;

    public ExtractReplaceScript(Extraction extraction, String scriptPath){
        super(extraction);
        this.scriptPath = scriptPath;
    }

    @Override

    //Script takes the request/response string as input to its function
    //Script can dynamically decide what the extraction regex should be
    //    based on the request/response details.
    public String getReplacedExtraction(String requestResponse)  {
        ScriptEngineManager factory = new ScriptEngineManager();
        // escape all the single quotes or do other required modifications to the body

        ScriptEngine engine = factory.getEngineByName("JavaScript");
        // set the RequestBody for the javascript functionality
        engine.put("requestBody",requestResponse);
        // execute the js
        try{ //TODO
            engine.eval(new java.io.FileReader(this.scriptPath));
        }catch(Exception e){
            BurpExtender.getInstance().stdout.println("getReplacedExtraction SCRIPT FAILURE: " + e.getMessage());
        }

        // read the result variable from the js
        Object res = engine.get("result");
        // and return it
        return res.toString();
    }

    public ExtractReplaceScript(Replace replace, String scriptPath){
        super(replace);
        this.scriptPath = scriptPath;
    }

    public void PostProcessAction(IHttpRequestResponse currentRequest, IHttpRequestResponse[] macroItems) {
        // get the HTTP service for the request
        IHttpService httpService = currentRequest.getHttpService();

        // get the URL of the request
        URL url= BurpExtender.getInstance().helpers.analyzeRequest(currentRequest).getUrl();

        // if the target host is the right one and the url is not e.g login
        if (BurpExtender.HOST_FROM.equalsIgnoreCase(httpService.getHost()) && !url.getPath().equalsIgnoreCase("/login")){ //TODO: remove login?
            // get the request info
            IRequestInfo rqInfo = BurpExtender.getInstance().helpers.analyzeRequest(currentRequest);
            // retrieve all headers
            List<String> headers = rqInfo.getHeaders();
            // get the request
            String request = new String(currentRequest.getRequest());
            // get the request body
            String messageBody = request.substring(rqInfo.getBodyOffset());
            String signature = null;
            try {
                signature = PostProcessSignBody(messageBody);
            } catch (Exception e) { BurpExtender.getInstance().stdout.println(e.toString()); }

            // go through the header and look for the one that we want to replace
            for (int i = 0; i < headers.size();i++){
                if(headers.get(i).startsWith("Signature-Header:"))
                    headers.set(i, "Signature-Header: " + signature);
            }

            // create the new http message with the modified header
            byte[] message = BurpExtender.getInstance().helpers.buildHttpMessage(headers, messageBody.getBytes());
            // print out the debug message if applicable (will be shown in the ui of Burp)
            BurpExtender.getInstance().stdout.println(BurpExtender.getInstance().helpers.bytesToString(message));
            BurpExtender.getInstance().stdout.println("---------------");
            // replace the current request and forward it
            currentRequest.setRequest(message);
        }
    }
    public String PostProcessSignBody(String requestbody) throws GeneralSecurityException, FileNotFoundException, IOException, ScriptException {
        ScriptEngineManager factory = new ScriptEngineManager();
        // escape all the single quotes or do other required modifications to the body
        String bodyRequest = requestbody.replace("'","\'");

        ScriptEngine engine = factory.getEngineByName("JavaScript");
        // set the RequestBody for the javascript functionality
        engine.put("requestBody",bodyRequest);
        // execute the js
        engine.eval(new java.io.FileReader("/path/to/sign.js"));
        // read the result variable from the js
        Object res = engine.get("result");
        // and return it
        return res.toString();
    }

    @Override
    public String getExtractionArgument() {
        return scriptPath;
    }
}
