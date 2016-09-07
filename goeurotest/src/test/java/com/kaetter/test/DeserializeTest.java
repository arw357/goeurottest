package com.kaetter.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.kaetter.goeurotest.EntryPoint;
import com.kaetter.goeurotest.GetResponse;

public class DeserializeTest {
	private String goodString;
	private String badString;
	private static final String GOOD_URL = "http://api.goeuro.com/api/v2/position/suggest/en/";
	private static final String BAD_URL = "http://ai.goeuro.com/api/v2/position/suggest/en/";

	@BeforeTest(description = "read test")

	void initTestString() throws FileNotFoundException, IOException {

		goodString = getStringFromFile("goodString.txt");
		badString = getStringFromFile("badString.txt");

	}

	@Test(expectedExceptions = { Exception.class })
	void testBadURL() throws Exception {

		new GetResponse(BAD_URL).callWebService(EntryPoint.getCityFromArray(new String[] { "A" }));

	}

	@Test(expectedExceptions = { Exception.class })
	void testBadResponse() throws Exception {

		new GetResponse(BAD_URL).deserializeResponse(badString);

	}

	private String getStringFromFile(String fileName) throws IOException, FileNotFoundException {

		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());

		StringBuffer buffer = new StringBuffer();
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

			String s;
			while ((s = reader.readLine()) != null) {
				buffer.append(s);
			}
			reader.close();

		}

		return buffer.toString();

	}

}
