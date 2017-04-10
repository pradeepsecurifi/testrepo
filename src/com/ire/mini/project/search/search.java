package com.ire.mini.project.search;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.*;
import java.util.Map.Entry;

class query_string
{
  public String tag_name;
  public String keyword;
  public String positions;
  public int document_frequency = 0;
  public HashMap<String, Double> weight = new HashMap<String, Double>();
};

public class search
{
	public static query_string query_term[];// = new query_string[10];
	public static int no_of_terms_in_query = 0;
	public static boolean is_tag = true;
	public static boolean is_keyword=false;
	public static int result_count;
	public static TreeMap<String,Long> secondary_index_words = new TreeMap<String, Long>();
	public static TreeMap<Long,Long> secondary_title_words = new TreeMap<Long, Long>();
	public static TreeMap<Long,Long> primary_title_words = new TreeMap<Long, Long>();
	public static ArrayList<Long> addresses  =  new ArrayList<Long>();
	public static String line = "";
	public static int index_entries = 0;
	public static long primary_index_size = 0;
	public static FileReader rstream;
	public static BufferedReader in;
	public static HashMap<String,String> stopwords = new HashMap<String,String>();
	public static HashMap<String,Double> i_result = new HashMap<String,Double>();
	public static int number_of_required_results = 10;
	
	public static void main(String args[]) throws  IOException
	{
		try
		{
			BufferedReader secondary_index_reader = new BufferedReader(new FileReader("secondary_index.txt"));
			
			while((line = secondary_index_reader.readLine())!= null)
			{
				String index_entry[] = line.split("#");
				secondary_index_words.put(index_entry[0],Long.parseLong(index_entry[1]));
			}
			secondary_index_reader.close();
			index_entries = secondary_index_words.size();
		}
		catch(Exception e)
		{
			System.out.println("Error here :"+e.getMessage()+e.getCause());
		}

		try
		{
			rstream = new FileReader("stopwords.txt");
			in = new BufferedReader(rstream);
			while(true)
			{
				String word = in.readLine();
				if(word == null)
					break;
				stopwords.put(word,null);
			}
			in.close();
		}
		catch(Exception e)
		{
			System.out.println("Stopwords file Error : "+ e.getMessage());
		}
		
		try
		{
			BufferedReader secondary_title_reader = new BufferedReader(new FileReader("title_secondary.txt"));
			
			while((line = secondary_title_reader.readLine())!= null)
			{
				String index_entry[] = line.split(":");
				secondary_title_words.put(Long.parseLong(index_entry[0]),Long.parseLong(index_entry[1]));
			}
			
			secondary_title_reader.close();
		}
		catch(Exception e)
		{
			System.out.println("Title error : " +  e.getMessage());
		}
		
		try
		{
		    BufferedReader query_read = new BufferedReader(new InputStreamReader(System.in));
		    
		    System.out.print("\n\t\tMini - Wiki Search Engine\n");
		    while(true)
		    {
			    System.out.print("\n######### | ######## | ########\n\nEnter Query : ");
			    String query = query_read.readLine();
			    if(query.equals("exit")) break;
			    Date d1 = new Date();
			    no_of_terms_in_query = 0;
			    parse_query(query);
			    
			    Stemmer stem = new Stemmer();
			    
			    for(int i = 0 ; i < no_of_terms_in_query ; i++)
			    {
			    	if(query_term[i].keyword.length()> 2 && !stopwords.containsKey(query_term[i].keyword))
					{
				    	String stemmed_keyword = query_term[i].keyword.toLowerCase();
				    	stem.add(stemmed_keyword.toCharArray(),stemmed_keyword.length());
						stem.stem();
						stemmed_keyword = stem.toString();
						
				    	perform_search(i,query_term[i].tag_name,stemmed_keyword);
					}
			    	else
			    	{
			    		query_term[i].keyword = "";
			    	}
			    }
		    	//Date d2 = new Date();
			  //  System.out.println("\n=============\nSearch Time Taken : "+(d2.getTime()-d1.getTime()));			
			    rank_result_documents();
			    intersect();
			    Date d3 = new Date();
			    System.out.println("=============\nTime Taken : "+(d3.getTime()-d1.getTime()+" ms"));
		    }
		}
		catch(IOException e)
		{
			System.out.println("Input Stream Error : "+ e.getMessage());
		}
	}

	private static void parse_query(String q) 
	{
		query_term = new query_string[10];
		
		for(int i = 0 ; i < 10 ; i++)
			query_term[i] = new query_string();
		
		if(q.indexOf(":") < 0) // raw query. no need to parse. just tokenize with space.
		{
			String[] query_array = q.split("[ ]+");
			for(int i=0;i<query_array.length;i++)
			{
				query_term[no_of_terms_in_query].tag_name = "#";
				query_term[no_of_terms_in_query++].keyword = query_array[i];
			}
		}
		else
		{
			String[] query_array = q.split("[ :]+");
			StringBuilder normalized_query = new StringBuilder();
			String tag="#";
			int count = 0;
			
			for(int i =0; i < query_array.length ;i++ )
			{			
				if(query_array[i].compareTo("T")==0 ||query_array[i].compareTo("B")==0 ||query_array[i].compareTo("C")==0 ||query_array[i].compareTo("I")==0 ||query_array[i].compareTo("O")==0 )
				{
					tag = query_array[i];
					count=0;
				}
				else
				{
					if(count == 0)
					{
						normalized_query.append(tag+" : "+query_array[i]+" ");
						count=1;
					}
					else
						normalized_query.append(tag+" : "+query_array[i]+" ");
				}
			}
			query_array = normalized_query.toString().split("[ :]+");
			System.out.println("Interpreted Query : "+normalized_query.toString());
			for(int i=0;i<query_array.length;i++)
			{
				if(is_tag)
				{
					query_term[no_of_terms_in_query].tag_name = query_array[i];
					is_tag = false;
					is_keyword = true;
					continue;
				}
				
				if(is_keyword)
				{
					query_term[no_of_terms_in_query++].keyword = query_array[i];
					is_tag = true;
					is_keyword = false;
				}
			}
		}
	}

	private static void perform_search(int term_index, String tag, String keyword) 
	{
		try
		{
			RandomAccessFile primary_index_reader = new RandomAccessFile("crop_index.txt","r");
			
			long match = 0;
			long upper_bound = 0;
			long lower_bound = 0;
			try
			{
				lower_bound = secondary_index_words.floorEntry(keyword).getValue();				
			}
			catch(Exception e)
			{
				lower_bound = 0;
			}
			
			try
			{
				upper_bound = secondary_index_words.ceilingEntry(keyword).getValue();
			}
			catch(Exception e)
			{
				upper_bound = primary_index_reader.length();
			}
		
			if(upper_bound == lower_bound)
				match=upper_bound;
				
			String position = "";

			if(match != 0)
			{
				primary_index_reader.seek(match);
				position = primary_index_reader.readLine().split("#")[1];
				query_term[term_index].keyword = keyword;
				query_term[term_index].positions = position;
				primary_index_reader.close();
			}
			else
			{
				primary_index_reader.seek(lower_bound);
				
				while(true)
				{
					String line[] = primary_index_reader.readLine().split("#");
					if(line[0].compareTo(keyword) > 0)
					{
						System.out.println("Message : keyword \""+keyword+"\" not found.");
						query_term[term_index].keyword = "";
						break;
					}
					if(line[0].equals(keyword))
					{
						query_term[term_index].keyword = keyword;
						query_term[term_index].positions = line[1];
						break;
					}
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception : "+e.getMessage());
			System.out.println("Cause : "+e.getCause());
			System.out.println("Class : "+e.getClass());
			System.out.println("Trace : "+e.getStackTrace());
		}
	}

	private static void rank_result_documents() 
	{
		double t_weight = 45;
		double i_weight = 30;
		double b_weight = 20;
		double c_weight = 2;
		double o_weight = 3;
		
		for(int i = 0 ; i< no_of_terms_in_query ; i++)
		{
			if(!query_term[i].keyword.equals(""))
			{
			    int start = 0;
			    int end = 0;
				
			    String positions[]=query_term[i].positions.split(":");
			    double idf = Math.log(10000000/positions.length); 				// here posting length will be document frequency.
			    
				if(query_term[i].tag_name.equals("#"))
				{
					for(int j = 0 ; j < positions.length ;j++)
					{
						double tf=0;
						
						String doc_id = positions[j].split("[^0-9]")[0];
						String listings = positions[j].substring(doc_id.length(),positions[j].length());
						String tag[] = listings.split(",");
						
						for(int k = 1 ; k < tag.length ; k++)
						{
							if(tag[k].startsWith("T"))
							{
								tf+= t_weight * Integer.parseInt(tag[k].substring(1,tag[k].length()));
							}
							else if(tag[k].startsWith("B"))
							{
								tf+= b_weight * Integer.parseInt(tag[k].substring(1,tag[k].length()));
							}
							else if(tag[k].startsWith("I"))
							{
								tf+= i_weight * Integer.parseInt(tag[k].substring(1,tag[k].length()));
							}
							else if(tag[k].startsWith("C"))
							{
								tf+= c_weight * Integer.parseInt(tag[k].substring(1,tag[k].length()));
							}
							else// if(tag[k].startsWith("O"))
							{
								tf+= o_weight * Integer.parseInt(tag[k].substring(1,tag[k].length()));
							}
						}
						
						query_term[i].weight.put(doc_id, tf*idf);
					}
				}
				
				else if(query_term[i].tag_name.equals("T"))
				{
					for(int j = 0 ; j < positions.length ;j++)
					{
						String doc_id = positions[j].split("[^0-9]")[0];
						
						String listings = positions[j].substring(doc_id.length(), positions[j].length());
					    
						if(listings.contains("T"))
						{
							start = listings.indexOf("T");
							end = listings.indexOf(",",start); 
							
							if(end == -1)
								end = listings.length();
							
							query_term[i].weight.put(doc_id,t_weight * Integer.parseInt(listings.substring(start+1,end)));
						}
					}
				}
				
				else if(query_term[i].tag_name.equals("B"))
				{
					for(int j = 0 ; j < positions.length ;j++)
					{
						String doc_id = positions[j].split("[^0-9]")[0];
						
						String listings = positions[j].substring(doc_id.length(), positions[j].length());
					    
						if(listings.contains("B"))
						{
							start = listings.indexOf("B");
							end = listings.indexOf(",",start); 
							
							if(end == -1)
								end = listings.length();
							
							query_term[i].weight.put(doc_id,b_weight * Integer.parseInt(listings.substring(start+1,end)));
						}
					}
				}
					
				else if(query_term[i].tag_name.equals("C"))
				{
					for(int j = 0 ; j < positions.length ;j++)
					{
						String doc_id = positions[j].split("[^0-9]")[0];
						
						String listings = positions[j].substring(doc_id.length(), positions[j].length());
					    
						if(listings.contains("C"))
						{
							start = listings.indexOf("C");
							end = listings.indexOf(",",start); 
							
							if(end == -1)
								end = listings.length();
							
							query_term[i].weight.put(doc_id,c_weight * Integer.parseInt(listings.substring(start+1,end)));
						}
					}
				}
					
				else if(query_term[i].tag_name.equals("I"))
				{
					for(int j = 0 ; j < positions.length ;j++)
					{
						String doc_id = positions[j].split("[^0-9]")[0];
						
						String listings = positions[j].substring(doc_id.length(), positions[j].length());
					    
						if(listings.contains("I"))
						{
							start = listings.indexOf("I");
							end = listings.indexOf(",",start); 
							
							if(end == -1)
								end = listings.length();
							
							query_term[i].weight.put(doc_id,i_weight * Integer.parseInt(listings.substring(start+1,end)));
						}
					}
				}
				
				else if(query_term[i].tag_name.equals("O"))
				{
					for(int j = 0 ; j < positions.length ;j++)
					{
						String doc_id = positions[j].split("[^0-9]")[0];
						
						String listings = positions[j].substring(doc_id.length(), positions[j].length());
					    
						if(listings.contains("O"))
						{
							start = listings.indexOf("O");
							end = listings.indexOf(",",start); 
							
							if(end == -1)
								end = listings.length();
							
							query_term[i].weight.put(doc_id,o_weight * Integer.parseInt(listings.substring(start+1,end)));
						}
					}
				}
			}
		}
	}

	private static void intersect()
	{
		int result_required = number_of_required_results;
		
		for(int i = 0 ; i < no_of_terms_in_query ; i++)
		{
			if(!query_term[i].keyword.equals(""))
			{
				Set<String> doc_set;
				doc_set = query_term[i].weight.keySet();

				for(String doc : doc_set)
				{
					if(i_result.containsKey(doc))
					{
						Double value = i_result.get(doc) * query_term[i].weight.get(doc);
						i_result.put(doc,value);
					}
					else
					{
						i_result.put(doc, query_term[i].weight.get(doc));
					}
				}
			}
		}
		
		i_result = sortByComparator(i_result, false);
		
		
		Set<String> doc_set;
		doc_set = i_result.keySet();
		
		System.out.println("\nMatch found in " + doc_set.size() + " Document(s)");
		
		if(doc_set.size() > result_required)
			System.out.println("=============\nShowing top  " + result_required  + " results..\n=============");
		else
			System.out.println("=============");
		
		int count=1;
		
		for(String doc : doc_set)
		{
			if(count > result_required)
			{
				break;
			}
			Long start = secondary_title_words.floorEntry(Long.parseLong(doc)).getValue();
			Long end;
			try
			{
				RandomAccessFile primary_title_reader = new RandomAccessFile("title_index.txt","r");
				try
				{
					end = secondary_title_words.ceilingEntry(Long.parseLong(doc)).getValue();	
				}
				catch(Exception e)
				{
					end = primary_title_reader.length();
				}
				primary_title_reader.seek(start);
				
				if(start == end)
				{
					System.out.println(doc +": "+ primary_title_reader.readLine().split("#")[1] );//+ " ["+i_result.get(doc)+"]");
					primary_title_reader.close();
				}
				
				else
				{
					while(true)
					{
						String line[] = primary_title_reader.readLine().split("#");
						
						if(line[0].equals(doc))
						{
							System.out.println(doc +": "+ line[1] + " ["+i_result.get(doc)+"]");
							break;
						}
					}
				}
				count++;
			}
			catch(Exception e)
			{
				
			}
		}
		i_result.clear();
		//System.out.println("=============");
	}
	
	private static HashMap<String, Double> sortByComparator(Map<String, Double> unsortMap, final boolean order)
    {
        List<Entry<String, Double>> list = new LinkedList<Entry<String, Double>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<String, Double>>()
        {
            public int compare(Entry<String, Double> o1, Entry<String, Double> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());
                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        HashMap<String, Double> sortedMap = new LinkedHashMap<String, Double>();
        for (Entry<String, Double> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
}
