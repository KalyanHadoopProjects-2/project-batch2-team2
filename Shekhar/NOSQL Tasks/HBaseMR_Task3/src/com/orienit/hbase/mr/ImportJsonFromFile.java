package com.orienit.hbase.mr;

import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Mutation;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ImportJsonFromFile {
	private static final Log LOG = LogFactory.getLog(ImportJsonFromFile.class);

	public static final String NAME = "ImportJsonFromFile";

	static class ImportMapper extends
			Mapper<LongWritable, Text, ImmutableBytesWritable, Mutation> {

		private JSONParser parser = new JSONParser();

		@Override
		public void map(LongWritable offset, Text line, Context context)
				throws IOException {
			try {
				JSONObject json = (JSONObject) parser.parse(line.toString());
				
				String empid = (String)json.get("empid").toString();
				String name = (String)json.get("name").toString();
				String salary = (String)json.get("salary").toString();
				String dept = (String)json.get("dept").toString();
				System.out.println("empid "+ empid);
				System.out.println("name "+ name);
				System.out.println("salary "+ salary);
				System.out.println("dept "+ dept);
				
				ImmutableBytesWritable HKey = new ImmutableBytesWritable(
						Bytes.toBytes(empid));
				Put HPut = new Put(Bytes.toBytes(empid));
				
				HPut.add(Bytes.toBytes("cf"), Bytes.toBytes("name"),
						Bytes.toBytes(name));
				HPut.add(Bytes.toBytes("cf"), Bytes.toBytes("salary"),
						Bytes.toBytes(salary));
				HPut.add(Bytes.toBytes("cf"), Bytes.toBytes("dept"),
						Bytes.toBytes(dept));
				
				context.write(HKey, HPut);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		String table = "employee1_json";
		String columnFamily = "cf";
		Configuration conf = HBaseConfiguration.create();
		Job job = Job.getInstance(conf, "Import from file " + args[0]
				+ " into table " + table);
		job.setJarByClass(ImportJsonFromFile.class);
		job.setMapperClass(ImportMapper.class);
		job.setOutputFormatClass(TableOutputFormat.class);
		job.getConfiguration().set(TableOutputFormat.OUTPUT_TABLE, table);
		job.setOutputKeyClass(ImmutableBytesWritable.class);
		job.setOutputValueClass(Writable.class);
		job.setNumReduceTasks(0);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		// run the job
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}

