//  FileCopyrightText: 2023 Venkatesh Omkaram
//  License-Identifier: MIT

package com.github.omkarium.transformers;

import lombok.extern.log4j.Log4j2;

import org.mule.weave.v2.runtime.*;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.HashMap;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequestMapping("/api")
public class DataWeaveController {


    @GetMapping(value = "/welcome")
    public ResponseEntity<String> welcome() throws FileNotFoundException {
        log.info("You tried the welcome page");
        InputStreamReader Hello = new InputStreamReader(DataWeaveController.class.getResourceAsStream("/examples/welcome.json"));
        String result = new BufferedReader(Hello).lines().collect(Collectors.joining("\n"));

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(httpHeaders -> httpHeaders.add("Content-Type", "application/json"))
                .body(result);
    }

    @PostMapping(path= "/json-to-raml", consumes = "application/json", produces = "application/yaml")
    public ResponseEntity<String> runFromFile(@RequestBody String inputPayload) throws IOException {
        log.info("Transforming JSON to RAML");

        DataWeaveScriptingEngine scriptingEngine = new DataWeaveScriptingEngine();
        InputStreamReader Hello = new InputStreamReader(DataWeaveController.class.getResourceAsStream("/dw_scripts/json-to-raml.dwl"));
        String result1 = new BufferedReader(Hello).lines().collect(Collectors.joining("\n"));
        DataWeaveScript compile =  scriptingEngine.compile(result1, new String[]{"payload","vars"});
        DataWeaveResult result = compile.write(new ScriptingBindings()
                .addBinding("payload", inputPayload, "application/json", new HashMap<>())
                .addBinding("vars", "my_variable", "application/java", new HashMap<>()));
        log.info(result);

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(httpHeaders -> httpHeaders.add("Content-Type", result.getMimeType()))
                .body(result.getContentAsString());
        
    }

    @PostMapping(path= "/csv-to-jsonarray", consumes = "text/plain", produces = "application/json")
    public ResponseEntity<String> runFromFile2(@RequestBody String inputPayload) throws IOException {
        log.info("Transforming CSV to JSON");

        DataWeaveScriptingEngine scriptingEngine = new DataWeaveScriptingEngine();
        InputStreamReader Hello = new InputStreamReader(DataWeaveController.class.getResourceAsStream("/dw_scripts/csv-to-jsonarray.dwl"));
        String result1 = new BufferedReader(Hello).lines().collect(Collectors.joining("\n"));       
        DataWeaveScript compile =  scriptingEngine.compile(result1, new String[]{"payload","vars"});
        DataWeaveResult result = compile.write(new ScriptingBindings()
                .addBinding("payload", inputPayload, "application/csv", new HashMap<>())
                .addBinding("vars", "my_variable", "application/java", new HashMap<>()));
        log.info(result);
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(httpHeaders -> httpHeaders.add("Content-Type", result.getMimeType()))
                .body(result.getContentAsString());
        
    }

    @PostMapping(path= "/jsonarray-to-csv", consumes = "application/json", produces = "application/csv")
    public ResponseEntity<String> runFromFile3(@RequestBody String inputPayload) throws IOException {
        log.info("Transforming JSON Array to CSV");

        DataWeaveScriptingEngine scriptingEngine = new DataWeaveScriptingEngine();
        InputStreamReader Hello = new InputStreamReader(DataWeaveController.class.getResourceAsStream("/dw_scripts/jsonarray-to-csv.dwl"));
        String result1 = new BufferedReader(Hello).lines().collect(Collectors.joining("\n"));       
        DataWeaveScript compile =  scriptingEngine.compile(result1, new String[]{"payload","vars"});
        DataWeaveResult result = compile.write(new ScriptingBindings()
                .addBinding("payload", inputPayload, "application/json", new HashMap<>()));
        log.info(result);
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(httpHeaders -> httpHeaders.add("Content-Type", result.getMimeType()))
                .body(result.getContentAsString());
        
    }

    @PostMapping(path= "/jsonobject-to-xml", consumes = "application/json", produces = "application/xml")
    public ResponseEntity<String> runFromFile4(@RequestBody String inputPayload) throws IOException {
        log.info("Transforming JSON Object to XML");

        DataWeaveScriptingEngine scriptingEngine = new DataWeaveScriptingEngine();
        InputStreamReader Hello = new InputStreamReader(DataWeaveController.class.getResourceAsStream("/dw_scripts/jsonobject-to-xml.dwl"));
        String result1 = new BufferedReader(Hello).lines().collect(Collectors.joining("\n"));       
        DataWeaveScript compile =  scriptingEngine.compile(result1, new String[]{"payload","vars"});
        DataWeaveResult result = compile.write(new ScriptingBindings()
                .addBinding("payload", inputPayload, "application/json", new HashMap<>()));
        log.info(result);
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(httpHeaders -> httpHeaders.add("Content-Type", result.getMimeType()))
                .body(result.getContentAsString());
        
    }

    @PostMapping(path= "/xml-to-json", consumes = "application/xml", produces = "application/json")
    public ResponseEntity<String> runFromFile5(@RequestBody String inputPayload) throws IOException {
        log.info("Transforming XML to JSON");

        DataWeaveScriptingEngine scriptingEngine = new DataWeaveScriptingEngine();
        InputStreamReader Hello = new InputStreamReader(DataWeaveController.class.getResourceAsStream("/dw_scripts/xml-to-json.dwl"));
        String result1 = new BufferedReader(Hello).lines().collect(Collectors.joining("\n"));       
        DataWeaveScript compile =  scriptingEngine.compile(result1, new String[]{"payload","vars"});
        DataWeaveResult result = compile.write(new ScriptingBindings()
                .addBinding("payload", inputPayload, "application/xml", new HashMap<>()));
        log.info(result);
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(httpHeaders -> httpHeaders.add("Content-Type", result.getMimeType()))
                .body(result.getContentAsString());
    }

}
