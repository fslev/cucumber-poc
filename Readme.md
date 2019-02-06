# Cucumber Core

## Summary
Cucumber-core is a Java library meant to help you write organized and clean **Cucumber** tests.  
It takes over the usual features that need to be present inside a test framework, such as:  
* the _expected-actual_ comparing mechanism
* read from from file functionality
* customized up-to-date clients to access various resources i.e databases, HTTP services, etc  
* a light state-sharing mechanism between Cucumber scenario steps       

Most of these features can be imported directly in your test project and are also available as Cucumber step definitions.  
  

## Features
#### _Expected-Actual_ compare mechanism
The following types of objects are supported for comparison:
* JSONs (String, JsonNode)  
  * dependency: [**json-compare**](https://github.com/fslev/json-compare)
    ```css
    String expected = "{\"!b\":val1\",\"a\":\"val2\"}";
    String actual = "{\"a\":\"val2\",\"c\":\"val1\"}";
    Cucumbers.compare(expected,actual);
    ```

* XMLs  
  * dependency: [**xml-unit**](https://github.com/xmlunit/xmlunit)
    ```css
    String expected = "<struct><int a=1>some .* text</int><boolean>false</boolean></struct>";
    String actual = "<struct><int a=1>some dummy text</int><boolean>false</boolean></struct>";
    Cucumbers.compare(expected,actual);
    ```      

* JSON convertible objects  
     
    ```css
    List<Map<String, Object>> expected = new ArrayList<>();
    List<Map<String, Object>> actual = new ArrayList<>();
    //To Do: Fill expected
    //To Do: Fill actual
    Cucumbers.compare(expected,actual);
    ```     