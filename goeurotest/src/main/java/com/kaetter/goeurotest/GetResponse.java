package com.kaetter.goeurotest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaetter.model.City;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * 
 * Deals with the response from the WebService
 * 
 * @author user
 *
 */
public class GetResponse {

	private static final Logger LOG = Logger.getLogger(GetResponse.class.getSimpleName());
	private String URL;

	/**
	 * We need the URL to call
	 * 
	 * @param url
	 */
	public GetResponse(String url) {
		this.URL = url;
	}

	/**
	 * deserializing the response to a List of {@link City}
	 * 
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public List<City> deserializeResponse(String response) throws Exception {
		LOG.debug("Deserializing...");
		ObjectMapper mapper = new ObjectMapper();

		List<City> l = null;

		try {

			l = mapper.readValue(response, new TypeReference<ArrayList<City>>() {
			});

		} catch (IOException e1) {

			LOG.error("Received error while mapping response: ", e1.getCause());

			throw new Exception();
		}

		return l;
	}

	/**
	 * Calls the WebService to get the response. It needs the city name.
	 * 
	 * @param cityName
	 * @return List<{@link City}>
	 * @throws Exception
	 */
	public List<City> callWebService(String cityName) throws Exception {

		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		// client.addFilter(new LoggingFilter(System.out));
		WebResource service = client.resource(UriBuilder.fromUri(URL).build());

		String response = null;
		try {
			LOG.debug("Calling WS...");
			response = service.path(cityName).accept(MediaType.APPLICATION_JSON_TYPE).get(String.class);

		} catch (ClientHandlerException e) {

			LOG.error("Failed to get response from " + URL + "because: " + e.getCause());

			throw new Exception();
		}

		return deserializeResponse(response);

	}
}
