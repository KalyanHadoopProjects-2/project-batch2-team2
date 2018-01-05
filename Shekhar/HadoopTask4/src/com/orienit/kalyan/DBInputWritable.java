package com.orienit.kalyan;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

public class DBInputWritable implements Writable, DBWritable{
	
	public static String[] fields = { "dt", "ip", "country", "status" };
	
	private String dt;
	private String ip;
	private String country;
	private String status;
	
	
	public String getDt() {
		return dt;
	}

	public String getIp() {
		return ip;
	}

	public String getCountry() {
		return country;
	}

	public String getStatus() {
		return status;
	}

	@Override
	public void readFields(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		dt=rs.getString(1);
		ip=rs.getString(2);
		country=rs.getString(3);
		status=rs.getString(4);
	}

	@Override
	public void write(PreparedStatement ps) throws SQLException {
		// TODO Auto-generated method stub
		ps.setString(1, dt);
		ps.setString(2, ip);
		ps.setString(3, country);
		ps.setString(4, status);
		
	}

	@Override
	public void readFields(DataInput arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write(DataOutput arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
