package com.pradyumna.assignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pradyumna.assignment.model.Position;
import com.pradyumna.assignment.model.Transaction;

@SpringBootApplication
public class PositionCalculatorApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(PositionCalculatorApplication.class, args);
		

		
		List<Transaction> transactions = extractTransactionFromTransactionFile();

		List<Position> positions = extractStartOfDayPositionFromFile();

		Map<String, List<Position>> map = positions.stream()
													.collect(Collectors.groupingBy(Position::getInstrument));

		System.out.println(map);

		map=mapQuantityAndDeltaToInstrument(transactions, map);

		writeToEndOfDayPositionFile(map);

	}

	/** This method will write the position into end of day file 'Expected_EndOfDay_Position.txt' present in src/main/resources
	 * @param map
	 * @throws URISyntaxException
	 */
	private static void writeToEndOfDayPositionFile(Map<String, List<Position>> map) throws URISyntaxException {
		List<Position> positionsPo = map.entrySet().stream()
				.map(set -> set.getValue()).flatMap(value -> value.stream())
				.collect(Collectors.toList());

		System.out.println("positionsPo--------------" + positionsPo);

		List<String> str = positionsPo.stream().map(position -> {
					return position.getInstrument() + "," + position.getAccount() + "," + position.getAccountType() + ","
					+ position.getQuantity() + "," + position.getDelta();
				}).peek(System.out::println)
				.collect(Collectors.toList());
		
		Path path = Paths.get(ClassLoader.getSystemResource("Expected_EndOfDay_Positions.txt").toURI());
		Iterable<String> iterable = str;
		Charset charset = Charset.forName("UTF-8");

		try {
			Files.write(path, iterable, charset, StandardOpenOption.APPEND);
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	/**
	 * For each transaction in Transaction file,

		Apply TransactionQuantity into matching instrument records in the position file

			If Transaction Type =B ,

                                For AccountType=E, Quantity=Quantity + TransactionQuantity

                                For AccountType=I, Quantity=Quantity - TransactionQuantity

			If Transaction Type =S ,

                                For AccountType=E, Quantity=Quantity - TransactionQuantity

                                For AccountType=I, Quantity=Quantity + TransactionQuantity
	 * 
	 * After calculating it will map each position list with instrument
	 * 
	 * @param transactions
	 * @param map 
	 * 
	 * 
	 */
	private static Map<String, List<Position>> mapQuantityAndDeltaToInstrument(List<Transaction> transactions,
			Map<String, List<Position>> map) {
		for (Transaction transaction : transactions) {

			List<Position> positionList = map.get(transaction.getInstrument());

			if ("B".equalsIgnoreCase(transaction.getTransactionType())) {
				for (Position position : positionList) {
					if ("E".equalsIgnoreCase(position.getAccountType())) {
						position.setDelta(
								position.getQuantity() + transaction.getTransactionQuantity() - position.getQuantity());
						position.setQuantity(position.getQuantity() + transaction.getTransactionQuantity());
					} else if ("I".equalsIgnoreCase(position.getAccountType())) {
						position.setDelta(
								position.getQuantity() - transaction.getTransactionQuantity() - position.getQuantity());

						position.setQuantity(position.getQuantity() - transaction.getTransactionQuantity());
					}
				}
			}
			if ("S".equalsIgnoreCase(transaction.getTransactionType())) {
				for (Position position : positionList) {
					if ("E".equalsIgnoreCase(position.getAccountType())) {
						position.setDelta(
								position.getQuantity() - transaction.getTransactionQuantity() - position.getQuantity());

						position.setQuantity(position.getQuantity() - transaction.getTransactionQuantity());
					} else if ("I".equalsIgnoreCase(position.getAccountType())) {
						position.setDelta(
								position.getQuantity() + transaction.getTransactionQuantity() - position.getQuantity());

						position.setQuantity(position.getQuantity() + transaction.getTransactionQuantity());
					}
				}
			}

			map.put(transaction.getInstrument(), positionList);
		}
		return map;
	}

	/** 
	 * This method will read the file Input_StartOfDay_Positions.txt present in src/main/resources and
	 * will return List of Position objects.
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	private static List<Position> extractStartOfDayPositionFromFile() throws IOException, URISyntaxException {
		List<String> lines = Files
				.readAllLines(Paths.get(ClassLoader.getSystemResource("Input_StartOfDay_Positions.txt").toURI()));

		Function<String, Position> getPositionObject = string -> {
			List<String> values = Arrays.asList(string.split(","));
			return (Position) new Position(values.get(0), Integer.parseInt(values.get(1)), values.get(2),
					Integer.parseInt(values.get(3)));

		};
		
		List<Position> positions = lines.stream()
										.skip(1)
										.map(getPositionObject)
										.collect(Collectors.toList());
		return positions;
	}

	/**
	 * This method will read the file Input_Transactions.json present in src/main/resources and
	 * will return List of Transaction objects.
	 * 
	 * @return 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 */
	private static List<Transaction> extractTransactionFromTransactionFile()
			throws FileNotFoundException, IOException, JsonParseException, JsonMappingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.AUTO_CLOSE_SOURCE, true);
		File file = ResourceUtils.getFile("classpath:Input_Transactions.json");
		List<Transaction> transactions = mapper.readValue(file, new TypeReference<List<Transaction>>() {});
		
		return transactions;
	}

}
