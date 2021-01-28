package com.rakuten.robiniocliservice.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONException;
import org.json.JSONObject;
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

	@PostMapping(value = "/executebash", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String getExecutorResp(@RequestHeader HttpHeaders headers, @RequestBody String requestStr) {
		try {
			JSONObject jsonObject = new JSONObject(requestStr);
			System.out.println("jsonObject:::" + jsonObject);
			String commandName = jsonObject.getString("commandname");
			String bashName = jsonObject.getString("bashname");
			String bashParams = jsonObject.getString("bashparams");
			String command = commandName + " " + bashName + " " + bashParams;
			Process p = null;

			p = Runtime.getRuntime().exec(command.trim());

			BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
			StringBuilder line = null;
			while (true) {
				line = line.append(r.readLine());
				if (line == null) {
					break;
				}
				
			}
			System.out.println(line);
			return line.toString();
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{\"status\":\"error\"}";
		}
	}
}
