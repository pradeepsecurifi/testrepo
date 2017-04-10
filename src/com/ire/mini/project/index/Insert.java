package com.ire.mini.project.index;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;


public class Insert
{
	int file_no = 0;
	HashMap<String, Frequency> word_freq;
	
	TreeMap<String, HashMap<String, Frequency>> word_tree = new TreeMap<String,HashMap<String,Frequency>>();
	
	
	public void write_to_index_file()
	{
		StringBuffer write_file = new StringBuffer();
		Frequency Freq;
		File file;
		FileWriter sub_index_write;
		BufferedWriter sub_index_buffer_write;
		
		try
		{
			file = new File("./sub_indexes"+"/"+"index"+ file_no++ +".txt");
			
			if (!file.exists()) 
				file.createNewFile();
			
			sub_index_write = new FileWriter(file.getAbsoluteFile());
			sub_index_buffer_write = new BufferedWriter(sub_index_write);
		
			Set<String> word_set = word_tree.keySet();
			
			for(String word:word_set)
			{
				if(word != "")
				{
					write_file.append(word);
					
					word_freq=word_tree.get(word);
					
					Set<String> hash_word_set = word_freq.keySet();
				
					for(String hash_word:hash_word_set)
					{
						write_file.append(":"+hash_word);
						
						Freq = word_freq.get(hash_word);
						
						if(Freq.title != 0) 	write_file.append(",T"+Freq.title);
						if(Freq.infobox != 0) 	write_file.append(",I"+Freq.infobox);
						if(Freq.content != 0)	write_file.append(",B"+Freq.content);
						if(Freq.category != 0)	write_file.append(",C"+Freq.category);
						if(Freq.outlink != 0)	write_file.append(",O"+Freq.outlink);
					}
	
					sub_index_buffer_write.write(write_file+"\n");
					write_file.setLength(0);
				}
			}
			sub_index_buffer_write.close();
		}
		catch (Exception e)
		{
			System.out.println("Exception "+e);
		}
		word_freq.clear();
		word_tree.clear();
	}
	
	public void add_to_tree_map(String word,int cate,String id)
	{
		if(!word_tree.containsKey(word))
		{
			word_freq = new HashMap<String,Frequency>();
			Frequency Freq =new Frequency();
			if(cate==1) Freq.infobox++;
			if(cate==3) Freq.outlink++;
			if(cate==2) Freq.content++;
			if(cate==4) Freq.category++;
			if(cate==0) Freq.title++;
			word_freq.put(id, Freq);
			word_tree.put(word, word_freq);
		}
		else
		{
			word_freq=word_tree.get(word);
			if(word_freq.containsKey(id))
			{
				Frequency f=new Frequency();
				f=word_freq.get(id);
				if(cate==1) f.infobox++;
				if(cate==3) f.outlink++;
				if(cate==2) f.content++;
				if(cate==4) f.category++;
				if(cate==0) f.title++;
				word_freq.put(id, f);
				word_tree.put(word, word_freq);
			}
			else
			{
				Frequency f=new Frequency();
				if(cate==1) f.infobox++;
				if(cate==3) f.outlink++;
				if(cate==2) f.content++;
				if(cate==4) f.category++;
				if(cate==0) f.title++;
				word_freq.put(id, f);
				word_tree.put(word, word_freq);
			}
		}
	}
}