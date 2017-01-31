package com.oci.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oci.domain.Participant;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Ishtiaq on 1/31/2017.
 */
@Service
public class SecureFormDisplatService {

    private ObjectMapper jacksonMapper;

    @Autowired
    public void setJacksonMapper(ObjectMapper jacksonMapper) {
        this.jacksonMapper = jacksonMapper;
    }

    /**
     * Show user entered info on next page to participant
     */
    public boolean showInfoToParticipant(Participant participant) {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            String endpoint = "/show";
            HttpPost postRequest = new HttpPost("http://localhost:7003/participant/" + endpoint);
            postRequest.addHeader("accept", "application/json");
            postRequest.addHeader("content-type", "application/json");
            StringEntity input = new StringEntity(jacksonMapper.writeValueAsString(participant), "UTF8");
            input.setContentType("application/json");
            postRequest.setEntity(input);
            CloseableHttpResponse response = httpClient.execute(postRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
