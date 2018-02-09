package com.spinsci.cassandra;

import java.io.FileWriter;
import java.io.IOException;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class CreateCSVFile {
	static Cluster cluster;
	static Session session;
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	private static final String FILE_HEADER="empid,name,salary,dept";
	public static void main(String[] args) throws IOException {
		FileWriter fileWriter = null;
		String fileName = "employee1_csv_op.csv";
		try {
			fileWriter = new FileWriter(fileName);
			fileWriter.append(FILE_HEADER.toString());
			fileWriter.append(NEW_LINE_SEPARATOR);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		session = cluster.connect("test");	
		ResultSet results = session.execute("SELECT * FROM employee1_csv where empid>2 and dept='dev'");
		
		for (Row row : results) {
			fileWriter.append(String.valueOf(row.getInt("empid")));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(row.getString("name"));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(row.getInt("salary")));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(row.getString("dept"));
			fileWriter.append(NEW_LINE_SEPARATOR);
		}
		fileWriter.flush();
		fileWriter.close();
		cluster.close();
	}

}
