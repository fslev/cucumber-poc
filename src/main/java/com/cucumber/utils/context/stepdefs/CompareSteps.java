package com.cucumber.utils.context.stepdefs;

import com.cucumber.utils.context.utils.Cucumbers;
import com.cucumber.utils.context.utils.ScenarioUtils;
import com.google.inject.Inject;
import io.cucumber.guice.ScenarioScoped;
import io.cucumber.java.en.Then;

import java.util.List;
import java.util.Map;

@ScenarioScoped
public class CompareSteps {

    @Inject
    private Cucumbers cucumbers;
    @Inject
    private ScenarioUtils logger;

    @Then("COMPARE {} with \"{}\"")
    public void compareWithString(Object expected, Object actual) {
        logger.log("    Compare:\n{}\n    Against:\n{}", expected, actual);
        cucumbers.compare(expected, actual);
    }

    @Then("Negative COMPARE {} with \"{}\"")
    public void compareNegativeWithString(Object expected, Object actual) {
        logger.log("    Negative Compare:\n{}\n    Against:\n{}", expected, actual);
        try {
            cucumbers.compare(expected, actual);
            throw new AssertionError("Compared objects match");
        } catch (AssertionError e) {
            logger.log("Assertion Error caught. Negative compare passes {}", e.getMessage());
        }
    }

    @Then("COMPARE {} with content from path \"{}\"")
    public void compareWithContentFromFilepath(Object expected, String filePath) {
        String actual = cucumbers.read(filePath);
        compareWithString(expected, actual);
    }

    @Then("COMPARE {} with")
    public void compareWithDocString(Object expected, StringBuilder actual) {
        logger.log("Compare {} against {}", expected, actual.toString());
        compareWithString(expected, actual.toString());
    }

    @Then("Negative COMPARE {} with")
    public void compareNegativeWithDocString(Object expected, StringBuilder actual) {
        compareNegativeWithString(expected, actual.toString());
    }

    @Then("COMPARE {} with table")
    public void compareWithDataTable(Object expected, List<Map<String, String>> actual) {
        logger.log("    Compare:\n{}\n    Against:\n{}", expected, actual);
        cucumbers.compare(expected, actual);
    }
}