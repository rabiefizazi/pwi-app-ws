package com.elrancho.pwi.shared;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.elrancho.pwi.security.SecurityConstants;
import com.elrancho.pwi.ui.model.request.ItemDetailRequestModel;
import com.elrancho.pwi.ui.model.response.InventoryCountRest;
import com.elrancho.pwi.ui.model.response.InventoryCountSummaryRest;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.fasterxml.jackson.core.JsonGenerator;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class Utils {

	public static final Random RANDOM = new SecureRandom();

	public static final String ALPHANUMERIC = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	public String generateItemId(int length) {

		return generateRandomString(length);
	}

	public String generateInventoryCountId(int length) {

		return generateRandomString(length);
	}

	public String generateUserId(int length) {
		return generateRandomString(length);

	}

	public String generateRandomString(int length) {

		StringBuilder randomString = new StringBuilder();

		for (int i = 0; i < length; i++)
			randomString.append(ALPHANUMERIC.charAt(RANDOM.nextInt(ALPHANUMERIC.length())));

		return new String(randomString);
	}

	public LocalDateTime getTodaysDate() {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String s = LocalDateTime.now().format(formatter);

		return LocalDateTime.parse(s, formatter);

	}

	public static LocalDate getWeekEndDate() {

//		Added this condition to check if it is Sunday, so to allow the managers to still do inventory on that day as well.
		if (LocalDate.now().getDayOfWeek().toString().equals("SUNDAY"))
			return LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.SATURDAY));
		else
			return LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));

	}

	public static boolean hasTokenExpired(String token) {
		// TODO Auto-generated method stub

		Claims claims = Jwts.parser().setSigningKey(SecurityConstants.getTokenSecret()).parseClaimsJws(token).getBody();

		Date tokenExpirationDate = claims.getExpiration();
		Date todaysDate = new Date();

		return tokenExpirationDate.before(todaysDate);
	}

	public String generateEmailConfirmationToken(String publicUserId) {

		String token = Jwts.builder().setSubject(publicUserId)
				.setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret()).compact();

		return token;
	}

	public String generatePasswordResetToken(String userIdString) {

		String token = Jwts.builder().setSubject(userIdString)
				.setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret()).compact();
		return token;
	}

	public static List<ItemDetailRequestModel> uploadItemListCsv(Class<ItemDetailRequestModel> pojo, InputStream stream)
			throws IOException {

		// read the csv file and skip malformatted rows
		CSVReader csvReader = null;

		List<ItemDetailRequestModel> returnValue = new ArrayList<>();
		// save into
		final String FIELS_DIRECTORY = "C:\\HQ-ItemMaster-Upload\\log.txt";
		final String PROCESSED_ITEMS_FILE = "C:\\HQ-ItemMaster-Upload\\processed-items.csv";
		final String FAILED_ITEMS_FILE = "C:\\HQ-ItemMaster-Upload\\failed-items.csv";

		File file = new File(FIELS_DIRECTORY);
		file.getParentFile().mkdir();
		file.createNewFile();
//		} else {
//			throw new IOException("Failed to create directory " + file.getParent());
//		}

		try {
			csvReader = new CSVReader(new InputStreamReader(stream), ',','\f');

			Writer writerProcessedItems = Files.newBufferedWriter(Paths.get(PROCESSED_ITEMS_FILE));
			CSVWriter csvWriterProcessedItems = new CSVWriter(writerProcessedItems, CSVWriter.DEFAULT_SEPARATOR,
					CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.RFC4180_LINE_END);

			Writer writerFailedItems = Files.newBufferedWriter(Paths.get(FAILED_ITEMS_FILE));
			CSVWriter csvWriterFailedItems = new CSVWriter(writerFailedItems, CSVWriter.DEFAULT_SEPARATOR,
					CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.RFC4180_LINE_END);

			String[] line;

			// skip the first row
			line = csvReader.readNext();

			// skip the header row
			line = csvReader.readNext();
			csvWriterProcessedItems.writeNext(line);
			csvWriterFailedItems.writeNext(line);

			while ((line = csvReader.readNext()) != null) {

				@SuppressWarnings("unused")
				long itemUpc, vensdorItem, storeId;
				double itemCost;

				try {
					itemUpc = Long.parseLong(line[0]);
				} catch (NumberFormatException e) {
					ArrayList<String> list = new ArrayList<>(Arrays.asList(line));
					list.add("Item UPC is not in the right data format");
					String[] stringArray = list.toArray(new String[0]);
					csvWriterFailedItems.writeNext(stringArray);
					continue;
				}
				try {
					vensdorItem = Long.parseLong(line[2]);
				} catch (NumberFormatException e) {
					ArrayList<String> list = new ArrayList<>(Arrays.asList(line));
					list.add("Vendor Item is not in the right data format");
					String[] stringArray = list.toArray(new String[0]);
					csvWriterFailedItems.writeNext(stringArray);
					continue;
				}
				try {
					storeId = Long.parseLong(line[6]);
					storeId = storeId + 1000;
					line[6]=Long.toString(storeId);
				} catch (NumberFormatException e) {
					ArrayList<String> list = new ArrayList<>(Arrays.asList(line));
					list.add("Store Id is not in the right data format");
					String[] stringArray = list.toArray(new String[0]);
					csvWriterFailedItems.writeNext(stringArray);
					continue;
				}
				try {
					itemCost = Double.parseDouble(line[7]);
				} catch (NumberFormatException e) {
					ArrayList<String> list = new ArrayList<>(Arrays.asList(line));
					list.add("Cost is not in the right data format");
					String[] stringArray = list.toArray(new String[0]);
					csvWriterFailedItems.writeNext(stringArray);
					continue;
				}

				csvWriterProcessedItems.writeNext(line);

			}

			csvWriterProcessedItems.close();
			csvWriterFailedItems.close();

			Reader reader = Files.newBufferedReader(Paths.get(PROCESSED_ITEMS_FILE));

			CsvToBean<ItemDetailRequestModel> csvToPojo = new CsvToBeanBuilder(reader)
					.withType(ItemDetailRequestModel.class).withIgnoreLeadingWhiteSpace(true).build();

			Iterator<ItemDetailRequestModel> iterator = csvToPojo.iterator();

			while (iterator.hasNext())
				returnValue.add(iterator.next());

		} catch (IOException e) {
			e.printStackTrace();
		}

		return returnValue;
	}
	
	public static void saveInventoryCountToCsv(PrintWriter writer, List<InventoryCountSummaryRest> inventoryCounts) {
		String[] CSV_HEADER = { "storeId", "departmentId", "weekEndDate", "totalInventory" };
		StatefulBeanToCsv<InventoryCountSummaryRest> beanToCsv = null;
		try (
				CSVWriter csvWriter = new CSVWriter(writer,
			                CSVWriter.DEFAULT_SEPARATOR,
			                CSVWriter.NO_QUOTE_CHARACTER,
			                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
			                CSVWriter.DEFAULT_LINE_END);
			){
			csvWriter.writeNext(CSV_HEADER);
			
			// write List of Objects
			ColumnPositionMappingStrategy<InventoryCountSummaryRest> mappingStrategy = new ColumnPositionMappingStrategy<InventoryCountSummaryRest>();
			mappingStrategy.setType(InventoryCountSummaryRest.class);
			mappingStrategy.setColumnMapping(CSV_HEADER);
			beanToCsv = new StatefulBeanToCsvBuilder<InventoryCountSummaryRest>(writer)
					.withMappingStrategy(mappingStrategy)
	                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
	                .build();
			beanToCsv.write(inventoryCounts);
			System.out.println("Write CSV using BeanToCsv successfully!");	
			
			}catch (Exception e) {
				System.out.println("Writing CSV error!");
				e.printStackTrace();
			}
	}
	//Save inventory by store by department by week to csv file
	public static void saveInventoryCountToCsvByStoreByDepartmentByWeek(PrintWriter writer, List<InventoryCountRest> inventoryCounts) {
        	
		String[] CSV_HEADER = { "storeId", "departmentId","userId",
				"vendorItem","itemDescription","cost","quantity","totalAmount", "weekEndDate", "dateUpdated", "itemMaster","unitOfMeasure"};
		StatefulBeanToCsv<InventoryCountRest> beanToCsv = null;
		try (
				CSVWriter csvWriter = new CSVWriter(writer,
			                CSVWriter.DEFAULT_SEPARATOR,
			                CSVWriter.NO_QUOTE_CHARACTER,
			                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
			                CSVWriter.DEFAULT_LINE_END);
			){
			csvWriter.writeNext(CSV_HEADER);
			
			// write List of Objects
			ColumnPositionMappingStrategy<InventoryCountRest> mappingStrategy = new ColumnPositionMappingStrategy<InventoryCountRest>();
			mappingStrategy.setType(InventoryCountRest.class);
			mappingStrategy.setColumnMapping(CSV_HEADER);
			beanToCsv = new StatefulBeanToCsvBuilder<InventoryCountRest>(writer)
					.withMappingStrategy(mappingStrategy)
	                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
	                .build();
			beanToCsv.write(inventoryCounts);
			System.out.println("Write CSV using BeanToCsv successfully!");	
			
			}catch (Exception e) {
				System.out.println("Writing CSV error!");
				e.printStackTrace();
			}
	}
}
