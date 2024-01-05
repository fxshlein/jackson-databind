package com.fasterxml.jackson.databind.struct;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.*;

/**
 * Tests to verify [databind#1467].
 */
public class UnwrappedWithCreatorTest extends BaseMapTest
{
    static class ExplicitWithoutName {
        private final String _unrelated;
        private final Inner _inner;

        @JsonCreator
        public ExplicitWithoutName(@JsonProperty("unrelated") String unrelated, @JsonUnwrapped Inner inner) {
            _unrelated = unrelated;
            _inner = inner;
        }

        public String getUnrelated() {
            return _unrelated;
        }

        @JsonUnwrapped
        public Inner getInner() {
            return _inner;
        }
    }

    static class ExplicitWithName {
        private final String _unrelated;
        private final Inner _inner;

        @JsonCreator
        public ExplicitWithName(@JsonProperty("unrelated") String unrelated, @JsonProperty("inner") @JsonUnwrapped Inner inner) {
            _unrelated = unrelated;
            _inner = inner;
        }

        public String getUnrelated() {
            return _unrelated;
        }

        public Inner getInner() {
            return _inner;
        }
    }

    static class ImplicitWithName {
        private final String _unrelated;
        private final Inner _inner;

        public ImplicitWithName(@JsonProperty("unrelated") String unrelated, @JsonProperty("inner") @JsonUnwrapped Inner inner) {
            _unrelated = unrelated;
            _inner = inner;
        }

        public String getUnrelated() {
            return _unrelated;
        }

        public Inner getInner() {
            return _inner;
        }
    }

    static class Inner {
        private final String _property1;
        private final String _property2;

        public Inner(@JsonProperty("property1") String property1, @JsonProperty("property2") String property2) {
            _property1 = property1;
            _property2 = property2;
        }

        public String getProperty1() {
            return _property1;
        }

        public String getProperty2() {
            return _property2;
        }
    }

    /*
    /**********************************************************
    /* Tests, deserialization
    /**********************************************************
     */

    private final ObjectMapper MAPPER = newJsonMapper();
	
    public void testUnwrappedWithJsonCreatorWithExplicitWithoutName() throws Exception
    {
        String json = "{\"unrelated\": \"unrelatedValue\", \"property1\": \"value1\", \"property2\": \"value2\"}";
        ExplicitWithoutName outer = MAPPER.readValue(json, ExplicitWithoutName.class);

        assertEquals("unrelatedValue", outer.getUnrelated());
        assertEquals("value1", outer.getInner().getProperty1());
        assertEquals("value2", outer.getInner().getProperty2());
    }

    public void testUnwrappedWithJsonCreatorExplicitWithName() throws Exception
    {
        String json = "{\"unrelated\": \"unrelatedValue\", \"property1\": \"value1\", \"property2\": \"value2\"}";
        ExplicitWithName outer = MAPPER.readValue(json, ExplicitWithName.class);

        assertEquals("unrelatedValue", outer.getUnrelated());
        assertEquals("value1", outer.getInner().getProperty1());
        assertEquals("value2", outer.getInner().getProperty2());
    }

    public void testUnwrappedWithJsonCreatorImplicitWithName() throws Exception
    {
        String json = "{\"unrelated\": \"unrelatedValue\", \"property1\": \"value1\", \"property2\": \"value2\"}";
        ImplicitWithName outer = MAPPER.readValue(json, ImplicitWithName.class);

        assertEquals("unrelatedValue", outer.getUnrelated());
        assertEquals("value1", outer.getInner().getProperty1());
        assertEquals("value2", outer.getInner().getProperty2());
    }
}
