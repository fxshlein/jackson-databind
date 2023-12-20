package com.fasterxml.jackson.databind.records;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.struct.TestUnwrappedWithJsonCreator;

public class RecordWithJsonUnwrappedTest extends BaseMapTest {
    record RecordWithJsonUnwrapped(String unrelated, @JsonUnwrapped Inner inner) {
    }

    record Inner(String property1, String property2) {
    }

    private final ObjectMapper MAPPER = newJsonMapper();

    /*
    /**********************************************************************
    /* Tests, JsonUnwrapped deserialization
    /**********************************************************************
     */

    public void testUnwrappedWithRecord() throws Exception
    {
        String json = "{\"unrelated\": \"unrelatedValue\", \"property1\": \"value1\", \"property2\": \"value2\"}";
        RecordWithJsonUnwrapped outer = MAPPER.readValue(json, RecordWithJsonUnwrapped.class);

        assertEquals("unrelatedValue", outer.unrelated());
        assertEquals("value1", outer.inner().property1());
        assertEquals("value2", outer.inner().property2());
    }
}
