package com.ire.mini.project.index;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import java.io.*;
import java.util.HashMap;


public class XMLContentHandler extends DefaultHandler 
{
	public boolean isPAGEset = false;
	public boolean isTITLEset = false;
	public boolean isIDset = false;
	public boolean isREVISIONset = false;
	public boolean isTEXTset = false;
	public boolean isUSERNAMEset = false;
	public boolean isCONTRIBUTORset = false;
	public boolean isMINORset = false;
	public boolean isCOMMENTset = false;
	public boolean isTIMESTAMPset = false;
    public int INFOBOX = 0;
    public boolean INFO = false;
    public StringBuilder contentdata;
    public StringBuilder infodata;
    public StringBuilder outlink;
    public StringBuilder externellink;
    public StringBuilder category;
    public StringBuilder title;
    public boolean OUTLINKFOUND = false;
    public boolean CATEGORY = false;
    public boolean EXLINKS = false;
    public Insert ins = new Insert();
    public int count = 0;
	public HashMap<String,String> stopwords = new HashMap<String,String>();
	public FileReader rstream;
	public FileWriter wstream;
	public BufferedReader in;
	public BufferedWriter out;
	String Title;
	public String doc_id = "";
	public String[] title_string;
	
	public void startDocument()
	{
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
			
			//wstream = new FileWriter("title-index.txt");
			//out = new BufferedWriter(wstream);
		}
		catch(Exception e)
		{
		}
	}
   
	public void endDocument()
	{
		try
		{
			//out.close();
			merge data = new merge();
			data.start_merge();
		}
		catch (Exception e)
		{
			System.err.println("Error: " + e.getMessage());
		}
	}
    
	public void startElement( String namespaceURI,String localName, String tagName,Attributes atts)
	{
		if(tagName.equals("title"))
			isTITLEset = true;
		if(tagName.equals("id"))
			isIDset = true;
		if(tagName.equals("revision"))
			isREVISIONset = true;
		if(tagName.equals("timestamp"))
			isTIMESTAMPset = true;	
		if(tagName.equals("contributor"))
			isCONTRIBUTORset = true;			
		if(tagName.equals("minor"))
			isMINORset = true;	
		if(tagName.equals("comment"))
			isCOMMENTset = true;	
		if(tagName.equals("text"))
			isTEXTset = true;	
		if(tagName.equals("page"))
		{
			contentdata = new StringBuilder();
			infodata = new StringBuilder();
			outlink= new StringBuilder();
			category= new StringBuilder();
			title = new StringBuilder();
			INFOBOX = 0;
			isPAGEset = true;
		}
	}
	
	public void endElement(String namespaceURI,String localName, String tagName)
	{
		if(tagName.equals("title"))
			isTITLEset = false;
		if(tagName.equals("id"))
			isIDset = false;
		if(tagName.equals("revision"))
			isREVISIONset = false;
		if(tagName.equals("timestamp"))
			isTIMESTAMPset = false;	
		if(tagName.equals("contributor"))
			isCONTRIBUTORset = false;			
		if(tagName.equals("minor"))
			isMINORset = false;	
		if(tagName.equals("comment"))
			isCOMMENTset = false;	
		if(tagName.equals("text"))
			isTEXTset = false;	
		if(tagName.equals("page"))
		{
				OUTLINKFOUND = false;
				INFOBOX = 0;
				INFO = false;
				CATEGORY = false;
				try 
				{
					Stemmer stem = new Stemmer();
					
					//out.write(doc_id+"#"+Title+"\n");
					
					for(int i = 0 ; i < title_string.length ; i++)
					{
						String title = title_string[i].trim().replaceAll("\\W","").toLowerCase();
								
						if(title.length() > 2 && !stopwords.containsKey(title))
						{
							ins.add_to_tree_map(title, 0, doc_id.toString());
							count++;
						}
					}
				
					String[] info = infodata.toString().replaceAll("#|\\n|\\W|_|\\.|[0-9]+"," ").split(" ");
					
					for(int i = 0 ; i < info.length ; i++)
					{
						String data = info[i].toLowerCase();
						
						if(data.length() > 2 && !stopwords.containsKey(data))
						{
							stem.add(data.toCharArray(),data.length());
							stem.stem();
							String s = stem.toString();
							if(s.length() > 2)
							{
								ins.add_to_tree_map(s, 1, doc_id.toString());
								count++;
							}
						}
					}
				
					String[] content = contentdata.toString().replaceAll("#|\\W|_|\\.|[0-9]+"," ").split(" ");
					
					for(int i = 0 ; i < content.length ; i++)
					{
						String data = content[i].toLowerCase();

						if(data.length() > 2 && !stopwords.containsKey(data))
						{
							stem.add(data.toCharArray(),data.length());
							stem.stem();
							String s = stem.toString();
							if(s.length() > 2)
							{
								ins.add_to_tree_map(s, 2, doc_id.toString());
								count++;
							}
						}
					}
				
					String[] link = outlink.toString().replaceAll("\\W|\\.|_|[0-9]+"," ").split(" ");
				
					for(int i = 0 ; i < link.length ; i++)
					{
						String data = link[i].toLowerCase();
				
						if(data.length() > 2 && !stopwords.containsKey(link[i].toLowerCase()))
						{
							stem.add(data.toCharArray(),data.length());
							stem.stem();
							String s = stem.toString();
							if(s.length() > 2)
							{
								ins.add_to_tree_map(s, 3, doc_id.toString());
								count++;
							}
						}
					}
					
					String[] cat = category.toString().replaceAll("\\)|\\(|\\[|\\W|\\.|_|[0-9]+"," ").split(" ");
					
					for(int i = 0 ; i < cat.length ; i++)
					{
						String data = cat[i].toLowerCase();
						if(data.length() > 2 && !stopwords.containsKey(data))
						{
							stem.add(data.toCharArray(),data.length());
							stem.stem();
							String s = stem.toString();
							if(s.length() > 2)
							{
								ins.add_to_tree_map(s, 4, doc_id.toString());
								count++;
							}
						}
					}
					
					if(count > 5000000)
					{
						ins.write_to_index_file();
						count = 0;
					}
				}	
				catch (Exception e) 
				{
				}
		}
	}
   
	public void characters(char[] ch, int start, int length)
	{
		if(isCOMMENTset || isCONTRIBUTORset || isMINORset || isTIMESTAMPset || isUSERNAMEset)
		{}
		else
		{
			String innerText = new String(ch,start,length);
			
			if(isTITLEset)
			{
				title_string = innerText.trim().replaceAll("[\\(\\)0-9\\n ]"," ").split("[ ,]+");
				Title = innerText;
			}
			if(!isREVISIONset && isIDset)
				doc_id = innerText.trim();
			
			else
			{
				if(isTEXTset)
				{
					innerText = innerText.trim();
					
					if(innerText.indexOf("{{Infobox") != -1)
						INFOBOX++;
					
					if(innerText.indexOf("|footnotes") != -1 || innerText.startsWith("}}"))
						INFOBOX--;
					
					if(INFOBOX>=1 )
					{
						String[] result = innerText.trim().split("\\|");
						for(int i = 0 ; i < result.length;i++)
						{
							String token = result[i].trim();
							
							if(!token.equals(""))
							{
								String[] exp = token.split("=");
								if(exp.length == 2 )
								{
									String rhs = exp[1].trim();
									
									if( !rhs.equals("") && rhs.indexOf("[[") > -1)
									{
										OUTLINKFOUND = true;
										if(rhs.startsWith("[["))
										{
											rhs = rhs.substring(2,rhs.length());
											
											if(rhs.endsWith("]]"))
											{
												String var = rhs.substring(0,rhs.length()-2);
													outlink.append(" " + var );
												OUTLINKFOUND = false;
											}
											else if(rhs.contains("]]"))
											{
												String[] d = rhs.split("\\]\\]");
												String var = d[0];
												outlink.append(" " + var );
												OUTLINKFOUND = false;
												String p = d[1];
												
												if(p.toString().endsWith(".") && !p.toString().equals(""))
													infodata.append(" " + p);
											}
											else
												outlink.append(" " + rhs.trim());
										}
										else
										{
											int index = 0;
											int index_end = 0;
											while(true)
											{
												//System.out.println(index);
												int index_start = rhs.indexOf("[[",index);
												
												if(index_start > -1)
												{
													//System.out.println(rhs.substring(index,index_start));
													infodata.append(" "+rhs.substring(index,index_start));
												    index_end = rhs.indexOf("]]",index_start);
													OUTLINKFOUND = true;
												}
												else
													index_end = -1;
												//int index_end = rhs.indexOf("]]",index_start);
												
												if(index_end > -1)
												{
													//System.out.println(index_start);
													//System.out.println(rhs.substring(index_start+2,index_end));
													//System.exit(1);
													outlink.append(" "+rhs.substring(index_start+2,index_end));
													index = index_end + 2;
													
													OUTLINKFOUND = false;
												}
												
												else if((index_start + 2  <= rhs.length()) && (index_start > -1) )
												{
													//System.exit(1);
													//if((index_start + 2  <= rhs.length()) && (index_start > -1) )
													//{
														outlink.append(" "+rhs.substring(index_start+2,rhs.length()));
														break;
													//}
												}
												else if(index_start < 0 && index_end < 0 && index < rhs.length())
												{
													infodata.append(" "+rhs.substring(index,rhs.length()));
													break;
												}
													
												
												//index = index_end + 2;
												//System.out.println(index);
												//System.exit(1);
												if(index >= rhs.length())
													break;
												
											}
											/*String[] d = exp[1].split("\\[\\[");
											infodata.append(" "+d[0]);
											
											if(d.length > 1)
											{
												if(d[1].indexOf("]]") > -1)
													OUTLINKFOUND = false;
												String var = d[1].replaceAll("[\\]]","").trim();
												outlink.append(" " + var);
											}*/
										}
									}
									else
									{
										if( !rhs.equals(""))
										{
											if(!rhs.toString().equals(".") && !rhs.toString().equals(""))
												infodata.append(" " + rhs);
											
											OUTLINKFOUND = false;
										}
									}
								}
								else
								{
									if(OUTLINKFOUND)
									{
										if(token.indexOf("]]") > -1)
										{
											if(token.indexOf("[[") >-1)
											{
												if(token.endsWith("]]"))
												{
													String word  = token.substring(token.indexOf("[")+2,token.length()-2);
													outlink.append(" "+word.trim());
													OUTLINKFOUND = false;
												}
												else
												{
													try
													{
														String word;
														int x= token.indexOf("]")+2;
														int y=token.indexOf("[");
														
														if(x>y)
															word = token.substring(y,x);
														else
															word = token.substring(x,y);
														
														infodata.append(" "+word.trim());
													}
													catch(Exception e){
														System.out.println("-->"+token);
												}
											}
										}
										else
											OUTLINKFOUND = false;
									}
									else
										outlink.append(" "+token.trim());
								}
								else
								{
									
									if(!token.endsWith("="))
									{
										if(!token.toString().equals("") )
											infodata.append(" " + token);
									}
								}
							}
						}
					}
				}
				else
				{
				    String[] result = innerText.split(" ");
				    
					for(int i = 0 ; i < result.length;i++)
						{
						    String token = result[i].trim();
						    
						    if(CATEGORY || token.startsWith("[[Category"))
						    {
						    	if(token.startsWith("[[Category"))	
						    	{
						    		CATEGORY = true;
						    		String var = token.substring(10,token.length());
						    		category.append(" "+var);
						    	}
						    	else if(token.endsWith("]]"))
						    	{
						    		String var = token.substring(0,token.length()-2);
					    			category.append(" "+var);
						    		CATEGORY = false;
						    	}
						    	else
					    			category.append(" "+token);
						    }
					
						    if(!CATEGORY)
						    {
								if(token.length() > 2 )
								{
									if(token.startsWith("[["))
									{
										token = token.substring(2, token.length());
										OUTLINKFOUND = true;
									}
									
									if(token.endsWith("]]"))
									{
										String var = token.substring(0, token.length() - 2);
										outlink.append(" "+ var);
										OUTLINKFOUND = false;
									}
									
									if(OUTLINKFOUND )
									{
										if(token.indexOf("]]") == -1)
											outlink.append(" "+token);
										else
										{
											String[] d = token.split("\\]\\]");
											outlink.append(" "+d[0]);
											contentdata.append(" "+d[1]);
											OUTLINKFOUND = false;
										}
									}
									else if (!token.endsWith("]]"))
									{
										OUTLINKFOUND = false;
										contentdata.append(" " +token);
									}
								}
							}	
						}
					}
				}
			}	
		}
	}	
}
