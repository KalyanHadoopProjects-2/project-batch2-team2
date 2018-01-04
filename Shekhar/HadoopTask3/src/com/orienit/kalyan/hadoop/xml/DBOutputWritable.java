package com.orienit.kalyan.hadoop.xml;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

public class DBOutputWritable implements Writable, DBWritable {
	public static String[] fields = { "dt", "ip", "country", "status" };
	
	private String dt;
	private String ip;
	private String country;
	private String status;
	public DBOutputWritable(String dt,String ip,String country,String status) {
		this.dt=dt;
		this.ip=ip;
		this.country=country;
		this.status=status;
	}

	@Override
	public void readFields(ResultSet rs) throws SQLException {
		dt=rs.getString(1);
		ip=rs.getString(2);
		country=rs.getString(3);
		status=rs.getString(4);		
	}

	@Override
	public void write(PreparedStatement ps) throws SQLException {
		
		ps.setString(1, dt);
		ps.setString(2, ip);
		ps.setString(3, country);
		ps.setString(4, status);		
		
	}

	@Override
	public void readFields(DataInput dip) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write(DataOutput dop) throws IOException {
		// TODO Auto-generated method stub
		
	}


}
