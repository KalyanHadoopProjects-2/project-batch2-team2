package com.orienit.kalyan;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class MyReducer extends Reducer<Text, Text, Text, NullWritable> {
	@Override
	protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		
		for (Text value : values) {
			System.out.println("Reduce values......."+value);
			System.out.println("Reduce Key......."+key);
		}

		// Assign sum to the corresponding word
		context.write(key,NullWritable.get());
	}
}

