package com.rakuten.robiniocliservice.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class ExecutorConbtroller {

	@GetMapping(value = "/executebash")
	public String getExecutorResp(@RequestHeader HttpHeaders headers,@RequestParam("bashname") String bashName,@RequestParam("bashparams") String bashParameters) {
		bashName = "scripts\\"+bashName;
		String command = "sh "+bashName+" "+bashParameters;
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
