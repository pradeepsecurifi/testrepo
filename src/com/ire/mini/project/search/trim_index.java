package com.ire.mini.project.search;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Comparator;
import java.util.TreeMap;

class sort_comparator implements Comparator<Double>
{
    public int compare(Double val1,Double val2)
    {
    	if(val1 < val2)
            return 1;
    	
        else if (val1 > val2)
            return -1;
        
        else
        	return 0;
    }
}
class trim_index
{
	public static TreeMap<String, String> postings_map = new TreeMap<String,String>();
	public static TreeMap<Double, String> values_map = new TreeMap<Double,String>(new sort_comparator());
	public static int posting_count = 0;
	public static int posting_limit = 50000;
	
	public static void main(String[] args)
	{
		System.out.println("Stop Running this again and again..!!");
		System.exit(1);
		System.out.println("Croping started...");
		try
		{
			BufferedReader main_index_reader = new BufferedReader(new FileReader("index.txt"));
			BufferedWriter trim_index_writer = new BufferedWriter(new FileWriter("crop_index.txt"));
			String word_posting;
			double term_frequency = 0;
			
			while((word_posting = main_index_reader.readLine())!=null )
			{
				 String array [] = word_posting.split("#");
				 System.out.println(array[0]);
 				 String posting[]=array[1].split(":");
				
				if(posting.length>posting_limit)
				{
					StringBuilder final_post  = new StringBuilder();
					final_post.append(array[0]+"#");
					
					for(int z=0;z<posting.length;z++)
					{
						String parts[] = posting[z].split(",");
						postings_map.put(parts[0], posting[z].substring(posting[z].indexOf(",")+1));
						term_frequency=0;
						
						for(int i = 1 ; i < parts.length;i++)
						{
							String tag = parts[i].substring(0,1);
							int counter = Integer.parseInt(parts[i].substring(1));
							
							if(tag.equals("T"))			term_frequency+=counter*0.45;
							if(tag.equals("I"))			term_frequency+=counter*0.25;
							if(tag.equals("B"))			term_frequency+=counter*0.15;
							if(tag.equals("C"))			term_frequency+=counter*0.1;
							if(tag.equals("O"))			term_frequency+=counter*0.05;
						}
							
						term_frequency *= Math.log(10000000/posting.length);
						values_map.put(term_frequency,posting[z].substring(0,posting[z].indexOf(",")));
					}
					
					for(Double i : values_map.keySet())
				    {
						if(posting_count > posting_limit)
						{
							posting_count = 0;
				    		break;
						}
						String Value = values_map.get(i);
						final_post.append(Value +","+postings_map.get(Value)+":");
						posting_count++;
			        }
					
					trim_index_writer.write(final_post.toString()+"\n");
				}
				
				else
					trim_index_writer.write(word_posting+"\n");	
				
				values_map.clear();
				postings_map.clear();
			}
			
			main_index_reader.close();
			trim_index_writer.close();
		}
		catch(Exception e)
		{
			System.out.println("Exception : "+e.getMessage());
			System.out.println("Cause : "+e.getCause());
			System.out.println("Class : "+e.getClass());
			System.out.println("Trace : "+e.getStackTrace());
		}
		
		System.out.println("Croping finished...");
	}
}
