package com.cucumber.utils.engineering.utils;

import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.junit.Assert.*;

public class XmlUtilsTest {

    private Function<String, List<String>> extractSpecialRegexCharsFct = s -> {
        List<String> regexChars = RegexUtils.getRegexCharsFromString(s);
        return regexChars.isEmpty() ? null : regexChars;
    };

    @Test
    public void testXmlIsNotValid() {
        String xml = "<a>test";
        assertFalse(XmlUtils.isValid(xml));
    }

    @Test
    public void testXmlsAreValid() {
        assertTrue(XmlUtils.isValid("<a>test<b>test</b></a>"));
        assertTrue(XmlUtils.isValid("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><a>test<b>test</b></a>"));
        assertTrue(XmlUtils.isValid("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<bookingResponse>\n" +
                "  <bookingId>dlc:~[var1]</bookingId>\n" +
                "</bookingResponse>"));
    }

    @Test
    public void testXmlSpecialRegexChars() throws IOException, SAXException, ParserConfigurationException {
        Map<String, List<String>> result = XmlUtils.walkXmlAndProcessNodes(ResourceUtils.read("xml/regex_chars/test1.xml"),
                extractSpecialRegexCharsFct);
        assertEquals(Arrays.asList("."), result.get("/cata.log"));
        assertTrue(Arrays.asList(".", "?").containsAll(result.get("/cata.log/product{product_image}")));
        assertEquals(Arrays.asList("."), result.get("/cata.log/product/catalog_item[2]/size[2]/color_swatch[2]{image}"));
        assertTrue(Arrays.asList("+", "[", "*").containsAll(result.get("/cata.log/product/catalog_item[2]/size[2]/color_swatch[4]{image}")));
        assertEquals(Arrays.asList("("), result.get("/cata.log/product/catalog_item[2]/size[2]/color_swatch[4]"));
        assertEquals(Arrays.asList("$"), result.get("/cata.log/product/catalog_item[2]/size[4]{description}"));
    }

    @Test
    public void testXmlSpecialRegexChars_fromXmlWithNone() throws IOException, SAXException, ParserConfigurationException {
        Map<String, List<String>> result = XmlUtils.walkXmlAndProcessNodes("<a><b>lorem ips'um</b></a>", extractSpecialRegexCharsFct);
        assertTrue(result.isEmpty());
    }
}