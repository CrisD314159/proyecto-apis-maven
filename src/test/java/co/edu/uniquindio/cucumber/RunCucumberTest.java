package co.edu.uniquindio.cucumber;

import org.junit.platform.suite.api.*;

import static io.cucumber.core.options.Constants.PLUGIN_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features") // Carpeta base de los .feature
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "co.edu.uniquindio.cucumber.steps")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber-reports/cucumber.html, json:target/cucumber-reports/cucumber.json")
public class RunCucumberTest {
}