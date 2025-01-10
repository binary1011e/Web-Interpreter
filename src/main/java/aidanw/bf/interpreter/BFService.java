package aidanw.bf.interpreter;

import loxinterpreter.lox.Lox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import BrainF.BF;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Service
public class BFService {

    public static void interpretCode(Code code) {
        if (code.getInputCode() != null) {
            if ("Lox".equals(code.getLanguage())) {
                Lox lox = new Lox();
                code.setOutputCode(lox.run(code.getInputCode()));
            } else if ("BF".equals(code.getLanguage())) {
                BF bf = new BF(code.getInputCode(), code.getInput());
                code.setOutputCode(bf.run());
            } else {
                code.setOutputCode(makeApiCall(code));
            }

        }
    }

    static private String makeApiCall(Code code) {
        final String uri = "https://api.codex.jaagrav.in";

        RestTemplate restTemplate = new RestTemplate();

        String reqBody = "code=" + code.getInputCode() +
                "&input=" + code.getInput() + "&language=" + code.getLanguage();
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType("application", "x-www-form-urlencoded", StandardCharsets.UTF_8);
        headers.setContentType(mediaType);
        HttpEntity<String> entity = new HttpEntity<>(reqBody, headers);
        // convert your result into json
        String result = restTemplate.postForObject(uri, entity, String.class);
        JSONObject jsonResponse;
        try {
            jsonResponse = new JSONObject(result);
            return jsonResponse.getString("output") + "\n" + jsonResponse.getString("error");
        } catch (JSONException e) {
            e.printStackTrace();
            return e.toString();
        }
    }
}
