package com.kaetter.goeurotest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.kaetter.model.City;

/**
 * Main class - from here it all starts
 * 
 * @author user
 *
 */
public class EntryPoint {
	private static final String YYYY_MM_DD_H_HMMSS_SSS = "yyyy_MM_ddHHmmssSSS";
	private static final String TXT_EXTENSION = ".txt";
	private static final Logger LOG = Logger.getLogger(EntryPoint.class.getSimpleName());
	private static final String URL = "http://api.goeuro.com/api/v2/position/suggest/en/";
	private static String cityName = null;

	public static void main(String[] args) throws Exception {

		try {
			validate(args);

			GetResponse getCities = new GetResponse(URL);

			List<City> cityList = getCities.callWebService(getCityFromArray(args));

			String csv = new CSVCreator().getCSV(cityList);

			String filename = writeStringToFile(csv);
			
			// maybe log should be externalized ? 
			LOG.info("Output succesfull. File is " + filename);

		} catch (Exception e) {

			LOG.error("Error occurred - exiting! ");
			System.exit(2);

		}
	}

	public static String getCityFromArray(String[] args) {

		cityName = Arrays.stream(args).map(s -> s.trim()).collect(Collectors.joining(" "));

		return cityName;

	}

	/**
	 * Simple validation
	 * 
	 * @param args
	 * @throws Exception
	 */
	private static void validate(String[] args) throws Exception {
		LOG.debug("Validating...");
		if (args == null || args.length == 0) {
			LOG.error("We need a parameter that is the name of a City!");
			throw new Exception("No parameter! ");
		}

	}

	private static String writeStringToFile(String string) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_H_HMMSS_SSS);

		String fileName = cityName.replaceAll(" ", "") + sdf.format(new Date()) + TXT_EXTENSION;

		LOG.debug("Writing to ..." + fileName);

		try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));
				BufferedReader br = new BufferedReader(new StringReader(string))) {

			String line;
			while ((line = br.readLine()) != null) {
				out.write(line);
				out.newLine();
			}

		} catch (IOException e) {
			LOG.error("Error writing file:", e);
			throw new Exception();
		}

		return fileName;

	}
}
