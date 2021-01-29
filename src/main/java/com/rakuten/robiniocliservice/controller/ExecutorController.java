package com.rakuten.robiniocliservice.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin("*")
@RestController
public class ExecutorController {
	
	private static Logger LOG = LoggerFactory.getLogger(ExecutorController.class);

	@PostMapping(value = "/executebash", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String getExecutorResp(@RequestHeader HttpHeaders headers, @RequestBody String requestStr) {
		try {
			JSONObject jsonObject = new JSONObject(requestStr);
			LOG.info("jsonObject:::" + jsonObject);
			String commandName = jsonObject.getString("commandname");
			String bashName = jsonObject.getString("bashname");
			String bashParams = jsonObject.getString("bashparams");
			String command = commandName + " " + bashName + " " + bashParams;
			Process p = null;

			p = Runtime.getRuntime().exec(command.trim());

			BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
			StringBuilder lineBuilder = new StringBuilder();
			String line = "";
			while (true) {
				line = r.readLine();
				if (line == null) {
					break;
				}
				lineBuilder.append(line);
			}
			if(!checkIsNullOrEmpty(lineBuilder.toString())) {
			LOG.info("response from "+lineBuilder.toString());
			}else {
				LOG.info("response from getExecutorResp is null");
			}
			return lineBuilder.toString();
		} catch (IOException | JSONException e) {
			LOG.error("Exception occured in getExecutorResp",e.getMessage());
			return "{\"status\":\"error\"}";
		}
	}
	
	public static boolean checkIsNullOrEmpty(String value) {
		return (value == null || value.equals("") || value.equalsIgnoreCase("null"));
	}
}
