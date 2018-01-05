package com.orienit.kalyan.hadoop.xml;

import java.io.IOException;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

public class JsonMapper extends Mapper<LongWritable, MapWritable, DBOutputWritable, NullWritable> {

	String dt,ip,country,status;
	@Override
	protected void map(LongWritable key, MapWritable value, Context context)
			throws IOException, InterruptedException {
	
		for (java.util.Map.Entry<Writable, Writable> entry : value.entrySet()) {
			if(entry.getKey().toString().equals("dt")){
				dt=entry.getValue().toString();
			}else if(entry.getKey().toString().equals("ip")){
				ip=entry.getValue().toString();
			}else if(entry.getKey().toString().equals("country")){
				country=entry.getValue().toString();
			}else if(entry.getKey().toString().equals("status")){
				status=entry.getValue().toString();
			}
		}
		if(status.equals("SUCCESS")){
		context.write(new DBOutputWritable(dt, ip, country, status),NullWritable.get());
		}
		
	}
}
