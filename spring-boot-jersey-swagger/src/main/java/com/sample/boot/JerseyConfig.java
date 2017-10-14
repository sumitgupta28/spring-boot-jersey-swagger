package com.sample.boot;

import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;

@Component
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		registerJackson();
		packages("com.sample.boot");
	}

	private void registerJackson() {
		ObjectMapper mapper = new ObjectMapper();

		// customize ObjectMapper
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);

		// create JsonProvider to provide custom ObjectMapper
		JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
		provider.setMapper(mapper);

		register(provider);
		register(RequestContextFilter.class);
		property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
		configureSwagger();
	}

	private void configureSwagger() {
		register(ApiListingResource.class);
		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setVersion("1.0.0");
		beanConfig.setSchemes(new String[] { "http" });
		beanConfig.setHost("localhost:8080");
		beanConfig.setBasePath("/app");
		beanConfig.setTitle("spring-boot-jersey-swagger");
		beanConfig.setDescription("spring-boot-jersey-swagger");
		beanConfig.getSwagger().addConsumes(MediaType.APPLICATION_JSON);
		beanConfig.getSwagger().addProduces(MediaType.APPLICATION_JSON);
		beanConfig.setContact("Sumit");
		beanConfig.setResourcePackage("com.sample.boot");
		beanConfig.setPrettyPrint(false);
		beanConfig.setScan(true);
	}

}
