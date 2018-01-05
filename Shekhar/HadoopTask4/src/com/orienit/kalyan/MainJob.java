package com.orienit.kalyan;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class MainJob implements Tool {
	// Declare Configuration Object
	private Configuration conf;

	@Override
	public Configuration getConf() {
		return conf; // Getting the configuration
	}

	@Override
	public void setConf(Configuration conf) {
		this.conf = conf; // Setting the configuration
	}

	@Override
	public int run(String[] args) throws Exception {
		String url = "jdbc:mysql://localhost:3306/kalyan?user=root&password=hadoop";
		String driver = "com.mysql.jdbc.Driver";
		
		DBConfiguration.configureDB(getConf(), driver, url);
		// initializing the job object with configuration
		Job job = new Job(getConf());

		// setting the job name
		job.setJobName("DBInputFormat");

		// Set the Jar by finding where a given class came from
		job.setJarByClass(this.getClass());

		// setting custom mapper class
		job.setMapperClass(MyMapper.class);
		
		// setting custom reducer class
		job.setReducerClass(MyReducer.class);

		// setting mapper output key class: K2
		job.setMapOutputKeyClass(Text.class);

		// setting mapper output value class: V2
		job.setMapOutputValueClass(Text.class);

		// setting final output key class: K3
		job.setOutputKeyClass(Text.class);

		// setting final output value class: V3
		job.setOutputValueClass(Text.class);

		// setting the input format class ,i.e for K1, V1
		job.setInputFormatClass(DBInputFormat.class);

		// setting the output format class
		job.setOutputFormatClass(TextOutputFormat.class);

		// define the input & output paths
		Path input = new Path(args[0]);
		Path output = new Path(args[1]);

		// setting the input path
		//FileInputFormat.addInputPath(job, input);
		
		DBInputFormat.setInput(job, DBInputWritable.class, "eventlog",null,null, DBInputWritable.fields);
		
		// setting the output path
		FileOutputFormat.setOutputPath(job, output);

		// delete the output path if exists
		output.getFileSystem(conf).delete(output, true);

		// to execute the job and return the status
		return job.waitForCompletion(true) ? 0 : -1;

	}

	public static void main(String[] args) throws Exception {
		// if `status == 0` then `Job Success`
		// if `status == -1` then `Job Failure`
		Configuration conf = new Configuration();
		
		int status = ToolRunner.run(conf, new MainJob(), args);

		System.out.println("Job Status: " + status);
	}

}
