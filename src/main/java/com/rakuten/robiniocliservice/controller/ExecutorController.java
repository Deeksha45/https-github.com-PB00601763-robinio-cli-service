package com.rakuten.robiniocliservice.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONObject;

@CrossOrigin("*")
@RestController
public class ExecutorController {

	@PostMapping(value = "/executebash")
	public String getExecutorResp(@RequestHeader HttpHeaders headers,@RequestBody JSONObject jsonObject) {
		
		String bashName = "scripts/"+jsonObject.getAsString("bashname");
		String command = "sh "+bashName+" "+jsonObject.getAsString("bashparams")==null?"":jsonObject.getAsString("bashparams");
		ProcessBuilder builder = new ProcessBuilder(command.trim());
		builder.redirectErrorStream(true);
		Process p = null;
		try {
			p = builder.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line = null;
		while (true) {
			try {
				line = r.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (line == null) {
				break;
			}
			System.out.println(line);
		}

		return line;
	}
}
