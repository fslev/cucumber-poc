package com.cucumber.utils.context;

import io.cucumber.guice.ScenarioScoped;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.message.ParameterizedMessage;

@ScenarioScoped
public class ScenarioUtils {
    private Scenario scenario;

    @Before(order = Integer.MIN_VALUE)
    public void init(Scenario scenario) {
        this.scenario = scenario;
    }

    public void log(String msg, Object... args) {
        scenario.log(ParameterizedMessage.format(msg, args));
    }

    public void log(Object o) {
        scenario.log(ParameterizedMessage.deepToString(o));
    }

    public Scenario getScenario() {
        return scenario;
    }
}
