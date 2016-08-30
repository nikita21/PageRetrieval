import java.util.*;
import java.io.*;

public class KeyWordIndexing{

	/* list contains all the files which contains the required keyword and their respective position 
		in the file. N is maximum allowed value. */ 
	public static TreeMap<String, Integer> reduce(String queryString, int N)
	{
		int dummy = N;
		String[] query = queryString.split("\\s+");
		TreeMap<String, TreeMap<String, Integer>> mappings = Mapper.keywordPosition;

		TreeMap<String, Integer> strengthOfWebpages = new TreeMap<String, Integer>();
		for(int k = 1; k < query.length; k++)
		{
			/*check whether the keyword is present in inverted index map or not.
			  If present, get the list associated with the keyword and pass it to reduce function. */
			if(mappings.containsKey(query[k]))
			{
				for(Map.Entry<String, Integer> entry : mappings.get(query[k]).entrySet())
				{
					int value = dummy * (N - entry.getValue());
					String filename = entry.getKey();
					if(strengthOfWebpages.containsKey(filename))
					{
						int sum = strengthOfWebpages.get(filename);
						sum += value;
						strengthOfWebpages.put(filename, sum);
					}
					else
					{
						strengthOfWebpages.put(filename, value);
					}
				}
			}
			dummy--;
		}
		
		return strengthOfWebpages;
	}
}