package com.orienit.hbase.mr;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Mutation;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.HFileOutputFormat;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Mapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.orienit.hbase.mr.ImportJsonFromFile.ImportMapper;

public class ImportXmlFromFIle {
	private static final Log LOG = LogFactory.getLog(ImportXmlFromFIle.class);

	public static final String NAME = "ImportXmlFromFIle";

	static class ImportMapper extends
			Mapper<LongWritable, Text, ImmutableBytesWritable, Mutation> {

		@Override
		public void map(LongWritable key, Text value, Context context)
				throws IOException {
			try {
				System.out.println("==========================");

				InputStream is = new ByteArrayInputStream(value.toString()
						.getBytes());
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(is);

				doc.getDocumentElement().normalize();

				NodeList nList = doc.getElementsByTagName("employee");

				for (int temp = 0; temp < nList.getLength(); temp++) {

					Node nNode = nList.item(temp);

					if (nNode.getNodeType() == Node.ELEMENT_NODE) {

						Element eElement = (Element) nNode;
						String empid = eElement.getElementsByTagName("empid")
								.item(0).getTextContent();
						String name = eElement.getElementsByTagName("name")
								.item(0).getTextContent();
						String salary = eElement.getElementsByTagName("salary")
								.item(0).getTextContent();
						String dept = eElement.getElementsByTagName("dept")
								.item(0).getTextContent();
						
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
					}
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public static void main(String[] args) throws Exception {
		String table = "employee1_xml";
		String columnFamily = "cf";
		Configuration conf = HBaseConfiguration.create();
		Job job = Job.getInstance(conf, "Import from file " + args[0]
				+ " into table " + table);
		// setting the job name
		job.setJobName("XML to HBASE");
		job.setJarByClass(ImportJsonFromFile.class);
		job.setMapperClass(ImportMapper.class);
		job.setNumReduceTasks(0);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(LongWritable.class);
		//job.setInputFormatClass(XmlInputFormat.class);
		job.setOutputFormatClass(TableOutputFormat.class);
		job.getConfiguration().set(TableOutputFormat.OUTPUT_TABLE, table);
		job.setOutputKeyClass(ImmutableBytesWritable.class);
		job.setOutputValueClass(Writable.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		// run the job
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
