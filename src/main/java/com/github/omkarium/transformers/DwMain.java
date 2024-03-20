//  FileCopyrightText: 2023 Venkatesh Omkaram
//  License-Identifier: MIT

package com.github.omkarium.transformers;

import lombok.extern.log4j.Log4j2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log4j2
public class DwMain {
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_RED = "\u001B[31m";
	public static void main(String[] args) {
		SpringApplication.run(DwMain.class, args);
		log.info("Welcome to Transform Tools\n");
		System.out.println(ANSI_GREEN + "DW-Transformers is issue under the licenses of MIT and APACHE 2.0. Read the license from the source repository before use.\n" + ANSI_GREEN);
		System.out.println("\n### Developed by: Venkatesh Omkaram (@github/omkarium) ###" + "\n\n" + ANSI_RED +
		"# The app is exposed a REST API. You can reach the endpoint at http://localhost:8080/api/welcome\n" +
		"# The welcome page gives you the list of endpoint resource paths available for use" + ANSI_RED);
	}
}
