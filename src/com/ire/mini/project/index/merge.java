package com.ire.mini.project.index;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;


public class merge
{
	public int no_of_files;
	public HashMap <Integer,String> new_container_hash;
	public HashMap <Integer,String> existing_container_hash;
	
	public BufferedWriter index_write;
	
	public ArrayList<BufferedReader> buffer_reader_array = new ArrayList<BufferedReader>();
	
	public TreeMap <String, HashMap<Integer, String> > word_tree = new TreeMap <String, HashMap<Integer, String>>();
	
	public void start_merge() throws IOException
	{
		index_write = new BufferedWriter(new FileWriter(new File(Driver.out_file).getAbsoluteFile()));
		//index_write = new BufferedWriter(new FileWriter(new File("index.txt").getAbsoluteFile()));
		intialize_merge();
		create_final_merge();
	}	

	public void add_word_to_tree(int i ,String word , HashMap <Integer,String> container)
	{
		if(word != null)
		{
			if(!word_tree.containsKey(word.substring(0,word.indexOf(":"))))   // if word not already in the tree then create new array list and then add it to the tree.
			{
				HashMap<Integer,String> new_container = new HashMap<Integer,String>();
				new_container.put(i,word.substring(word.indexOf(":")+1,word.length()));
				word_tree.put(word.substring(0,word.indexOf(":")),new_container);
			}
			else  // if word already there in the tree then modify its entry in the tree with the new page counts corresponding the sub index file.
			{
				container = word_tree.get(word.substring(0,word.indexOf(":")));	
				container.put(i,word.substring(word.indexOf(":")+1,word.length()));
				word_tree.put(word.substring(0,word.indexOf(":")), container);
			}
		}
	}
	
	public void intialize_merge() throws IOException
	{
		File file_list[]=new File("./sub_indexes").listFiles();
		no_of_files = file_list.length;

		for(int i=0 ; i < no_of_files ; i++)
			buffer_reader_array.add(new BufferedReader(new FileReader(file_list[i])));
	
		for(int i=0 ; i < buffer_reader_array.size() ; i++)
			add_word_to_tree(i,buffer_reader_array.get(i).readLine(),new_container_hash);
	}
	
	public void create_final_merge() throws IOException
	{
		String word;
		Set<Integer> int_set;
		Entry <String, HashMap <Integer, String>> key;

		while(word_tree != null)
		{
			StringBuilder write_file = new StringBuilder();
			key = word_tree.pollFirstEntry();
			
			if(key==(null))
				break;
			
			write_file.append(key.getKey()+"#");
			new_container_hash = key.getValue();
		
			int_set = new_container_hash.keySet();
		
			int count = 0;
		
			for(int i:int_set)
			{		
				word=new_container_hash.get(i);
				
				if(count == 0)
				{
					write_file.append(word);
					count = 1;
				}
				else
					write_file.append(":"+word);
				
				add_word_to_tree(i, buffer_reader_array.get(i).readLine(),existing_container_hash);
			}
			
			index_write.write(write_file+"\n");
		}
		index_write.close();
		word_tree.clear();
		buffer_reader_array.clear();
		new_container_hash.clear();
	}
	
	/*public static void main(String arr[]) throws IOException
	{
		System.out.println("hello");
		merge mrg=new merge();
		mrg.start_merge();
	}*/
}