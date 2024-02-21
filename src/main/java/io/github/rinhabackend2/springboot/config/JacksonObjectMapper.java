package io.github.rinhabackend2.springboot.config;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class JacksonObjectMapper {
	private static final DateTimeFormatter PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXXX")
			.withZone(ZoneOffset.UTC);

	private static class OffsetDateTimeSerializer extends JsonSerializer<OffsetDateTime> {
		@Override
		public void serialize(OffsetDateTime offsetDateTime, JsonGenerator jsonGenerator,
				SerializerProvider serializerProvider) throws IOException {
			jsonGenerator.writeString(PATTERN.format(offsetDateTime));
		}
	}

	@Primary
	@Bean
	ObjectMapper objectMapper() {
		var objectMapper = Jackson2ObjectMapperBuilder.json().featuresToDisable(DeserializationFeature.ACCEPT_FLOAT_AS_INT).build();
		objectMapper.registerModule(new JavaTimeModule());

		var simpleModule = new SimpleModule();
		simpleModule.addSerializer(OffsetDateTime.class, new OffsetDateTimeSerializer());
		objectMapper.registerModule(simpleModule);

		return objectMapper;
	}
}
