package com.ire.mini.project.index;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Calendar;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class Driver
{
	public static String out_file; 
	
	public static void main(String args[]) throws SAXException, IOException
	{
		XMLReader p = XMLReaderFactory.createXMLReader();
		p.setContentHandler(new XMLContentHandler());
		Calendar now = Calendar.getInstance();
		System.out.println("Indexing Begin: "+now.get(Calendar.HOUR)+":"+now.get(Calendar.MINUTE)+ ":"+ now.get(Calendar.SECOND));
		out_file = "index.txt";
		p.parse(args[0]);
		p.parse("sample.xml");
		
		now = Calendar.getInstance();
	
		System.out.println("Indexing End: "+now.get(Calendar.HOUR)+":"+now.get(Calendar.MINUTE)+ ":"+ now.get(Calendar.SECOND));
		System.out.println("Creating secondary index...");
		create_secondary_index();
		System.out.println("Finished secondary index...");
	}

	private static void create_secondary_index()  throws  IOException
	{
		try
		{
			RandomAccessFile primary_index_file = new RandomAccessFile(out_file, "r");
			FileWriter secondary_index_file = new FileWriter("secondary_index.txt");
			
			String line = primary_index_file.readLine();
			
			do
			{
				secondary_index_file.write(line.split("#")[0]+"#"+(primary_index_file.getFilePointer()-line.length()-1)+""+"\n");
				primary_index_file.seek(primary_index_file.getFilePointer()+1000);
				primary_index_file.readLine();
				line = primary_index_file.readLine();
			}
			while(line != null);
			
			primary_index_file.close();
			secondary_index_file.close();
		}
		
		catch(Exception e)
		{
			System.out.println("My Error  : " + e.getMessage());
		}
	}
}
