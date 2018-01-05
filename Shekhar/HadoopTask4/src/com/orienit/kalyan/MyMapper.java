package com.orienit.kalyan;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MyMapper extends
		Mapper<LongWritable, DBInputWritable, Text, Text> {
	@Override
	protected void map(LongWritable key, DBInputWritable value, Context context)
			throws IOException, InterruptedException {
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		try {
			System.out.println("dt ----------->"+value.getDt());
			System.out.println("ip ----------->"+value.getIp());
			System.out.println("country ----------->"+value.getCountry());
			System.out.println("status ----------->"+value.getStatus());
			
			if(value.getStatus().equals("SUCCESS")){
				context.write(new Text(value.getCountry()), new Text());
			}
			
			
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
 
    }
}











