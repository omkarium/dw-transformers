# DW Transformers
A simple spring-boot REST API which helps in transforming data of one mimetype to another. The app is a wrapper around DWL (Dataweave files) and it uses the MuleSoft DW runtime inside for Dataweave parsing.

## Features
- Expose a REST API to help with transformations
- Can do the following conversions currently
  - JSON to RAML
  - CSV to JSON Array
  - JSON Array to CSV
  - JSON Object to XML
  - XML to JSON

## How to use?
* Clone this repository
* Change your directory into the repository using `cd transformers`
- Run `mvn spring-boot:run` if you have maven already installed on your machine
- Run `curl http://localhost:8080/api/welcome` to see the welcome page which has endpoints available for use

In case you do not have Maven on your machine, try running the Jar file located in the repo jar-file folder directly as `java -jar transformers.jar`

## When should you use this?
If you are someone who understands MuleSoft and the power of dataweave, you might have wondered if its possible to use it standalone without deploying a Mule Application. It turns out Dataweave is already opensourced and there are CLI tools which can do those powerful tranformations.

Look at https://github.com/mulesoft-labs/data-weave-cli. It is licensed under BSD-3-Clause

This means as long as you have some app which acts as a wrapper to the DW runtime, you can do whatever a Native Mule app can do, minus the Connectors, Cloudhub, Enterprise support etc. For simple transformations, this can be a good choice. So, develop your app in Java, use the DW runtime for data transformations and do your DB operations using java or even better use RUST https://www.rust-lang.org/
