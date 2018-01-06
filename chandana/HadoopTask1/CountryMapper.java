package com.orienit;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class CountryMapper extends Mapper <LongWritable, Text, Text, IntWritable>{
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		// Read the line
		String line = value.toString();

		// Split the line in to words
		String[] words = line.split("|");
		
		Text country = new Text(words[2]);
		if(words[3].equalsIgnoreCase("SUCCESS"))
		{
			context.write(country, new IntWritable(1));
		}

	}
}