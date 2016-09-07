package com.kaetter.goeurotest;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.kaetter.model.City;
import com.kaetter.model.CityCSVBean;

/**
 * Used to get the CSV from the response
 * 
 * @author user
 *
 */
public class CSVCreator {
	private static final Logger LOG = Logger.getLogger(CSVCreator.class.getSimpleName());
	CsvMapper csvMapper = new CsvMapper();
	CsvSchema schema = null;

	public CSVCreator() {

		schema = csvMapper.schemaFor(new TypeReference<CityCSVBean>() {
		}).withQuoteChar('"');

	}

	public String createCSV(CityCSVBean t) {

		try {

			return csvMapper.writer(schema).writeValueAsString(t);

		} catch (JsonProcessingException e) {
			// eating up the exception - can't have throwers in lambda.
			LOG.error("Parsing did not work for " + t.toString(), e);

		}

		return null;

	}

	/**
	 * Takes care of the parsing for a list of {@link City}
	 * 
	 * @param cityList
	 * @return
	 */
	public String getCSV(List<City> cityList) {

		LOG.debug("Mapping to CSV ...");

		return cityList.stream().map(c -> createCSV(new CityCSVBean(c))).collect(Collectors.joining(""));

	}
}
