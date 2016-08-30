import java.util.*;
import java.io.*;

public class Mapper{
	
	protected static TreeMap<String, TreeMap<String, Integer>> keywordPosition = 
															new TreeMap<String, TreeMap<String, Integer>>(String.CASE_INSENSITIVE_ORDER);

	/* map each word in a file to <filename, position of word> 
		if same word appears in another page, it will append with the previous value and returns a list of 
		all filenames and the position at which the word appears.
		This will create an inverted index map.*/
	public static TreeMap<String, TreeMap<String, Integer>> map(File file) throws IOException
	{
		BufferedReader br = null;
		try 
		{
			br = new BufferedReader(new FileReader(file));
    		String line = br.readLine();
    		int countPages = 0;
    		while (null != line)
    		{
        		String[] keys = line.split("\\s+");
        		if(keys[0].equals("P"))
        		{
        			countPages++;
        			for(int i = 1 ; i < keys.length; i++)
		    		{
						TreeMap<String, Integer> mapKeys = new TreeMap<String, Integer>();
		    			if(!keywordPosition.containsKey(keys[i]))
		    			{
		    				mapKeys.put(keys[0]+countPages, (i-1));
		    				keywordPosition.put(keys[i], mapKeys);
		    			}
		    			else
		    			{
		    				mapKeys = keywordPosition.get(keys[i]);
		    				mapKeys.put(keys[0]+countPages, (i-1));
		    				keywordPosition.put(keys[i], mapKeys);
		    			}
		    		}
        		}
        		else
        		{
        			break;
        		}
        		line = br.readLine();
   			}
    		
		}
		catch(FileNotFoundException e)
		{
			System.err.println("file not found: " + e.getMessage());
		}
		finally
		{
			if(null != br)
				br.close();
		}
		return keywordPosition;
	}
}