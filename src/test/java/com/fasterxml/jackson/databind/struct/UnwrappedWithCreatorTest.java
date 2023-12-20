package com.fasterxml.jackson.databind.struct;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.*;

/**
 * Tests to verify [databind#1467].
 */
public class UnwrappedWithCreatorTest extends BaseMapTest
{

	static class ExplicitWithoutName {
		private final String unrelated;
		private final Inner inner;

		@JsonCreator
		public ExplicitWithoutName(@JsonProperty("unrelated") String unrelated, @JsonUnwrapped Inner inner) {
			this.unrelated = unrelated;
			this.inner = inner;
		}

		public String getUnrelated() {
			return unrelated;
		}

		@JsonUnwrapped
		public Inner getInner() {
			return inner;
		}
	}

	static class ExplicitWithName {
		private final String unrelated;
		private final Inner inner;

		@JsonCreator
		public ExplicitWithName(@JsonProperty("unrelated") String unrelated, @JsonProperty("inner") @JsonUnwrapped Inner inner) {
			this.unrelated = unrelated;
			this.inner = inner;
		}

		public String getUnrelated() {
			return unrelated;
		}

		public Inner getInner() {
			return inner;
		}
	}

	static class ImplicitWithName {
		private final String unrelated;
		private final Inner inner;

		public ImplicitWithName(@JsonProperty("unrelated") String unrelated, @JsonProperty("inner") @JsonUnwrapped Inner inner) {
			this.unrelated = unrelated;
			this.inner = inner;
		}

		public String getUnrelated() {
			return unrelated;
		}

		public Inner getInner() {
			return inner;
		}
	}

	static class Inner {
		private final String property1;
		private final String property2;

		public Inner(@JsonProperty("property1") String property1, @JsonProperty("property2") String property2) {
			this.property1 = property1;
			this.property2 = property2;
		}

		public String getProperty1() {
			return property1;
		}

		public String getProperty2() {
			return property2;
		}
	}

    /*
    /**********************************************************
    /* Tests, deserialization
    /**********************************************************
     */

	public void testUnwrappedWithJsonCreatorWithExplicitWithoutName() throws Exception
	{
		ObjectMapper mapper = new ObjectMapper();

		String json = "{\"unrelated\": \"unrelatedValue\", \"property1\": \"value1\", \"property2\": \"value2\"}";
		ExplicitWithoutName outer = mapper.readValue(json, ExplicitWithoutName.class);

		assertEquals("unrelatedValue", outer.getUnrelated());
		assertEquals("value1", outer.getInner().getProperty1());
		assertEquals("value2", outer.getInner().getProperty2());
	}

	public void testUnwrappedWithJsonCreatorExplicitWithName() throws Exception
	{
		ObjectMapper mapper = new ObjectMapper();

		String json = "{\"unrelated\": \"unrelatedValue\", \"property1\": \"value1\", \"property2\": \"value2\"}";
		ExplicitWithName outer = mapper.readValue(json, ExplicitWithName.class);

		assertEquals("unrelatedValue", outer.getUnrelated());
		assertEquals("value1", outer.getInner().getProperty1());
		assertEquals("value2", outer.getInner().getProperty2());
	}

	public void testUnwrappedWithJsonCreatorImplicitWithName() throws Exception
	{
		ObjectMapper mapper = new ObjectMapper();

		String json = "{\"unrelated\": \"unrelatedValue\", \"property1\": \"value1\", \"property2\": \"value2\"}";
		ImplicitWithName outer = mapper.readValue(json, ImplicitWithName.class);

		assertEquals("unrelatedValue", outer.getUnrelated());
		assertEquals("value1", outer.getInner().getProperty1());
		assertEquals("value2", outer.getInner().getProperty2());
	}
}
