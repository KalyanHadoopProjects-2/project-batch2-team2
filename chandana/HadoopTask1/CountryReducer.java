package com.orienit;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class CountryReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
	public void reduce(Text word, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
		// Sum the list of values
		int sum = 0;
		   for(IntWritable value : values)
		   {
			   sum += value.get();
		   }
		   context.write(word, new IntWritable(sum));
	}
}

