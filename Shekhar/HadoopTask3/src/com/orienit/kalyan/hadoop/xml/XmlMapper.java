package com.orienit.kalyan.hadoop.xml;

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

public class XmlMapper extends
		Mapper<LongWritable, Text, DBOutputWritable, NullWritable> {
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		try {
			 
            InputStream is = new ByteArrayInputStream(value.toString().getBytes());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);
 
            doc.getDocumentElement().normalize();
 
            NodeList nList = doc.getElementsByTagName("record");
 
            for (int temp = 0; temp < nList.getLength(); temp++) {
 
                Node nNode = nList.item(temp);
 
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
 
                    Element eElement = (Element) nNode;
 
                    String dt = eElement.getElementsByTagName("dt").item(0).getTextContent();
                    String ip = eElement.getElementsByTagName("ip").item(0).getTextContent();
                    String country = eElement.getElementsByTagName("country").item(0).getTextContent();
                    String status = eElement.getElementsByTagName("status").item(0).getTextContent();
                    if(status.equalsIgnoreCase("SUCCESS")){
                    	context.write(new DBOutputWritable(dt, ip, country, status), NullWritable.get());	
                    } 
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
 
    }
}











