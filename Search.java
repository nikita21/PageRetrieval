import java.io.*;
import java.util.*;

public class Search{

	private static final String FILES_TO_INDEX_DIRECTORY = "input.txt";
	
	public static void main(String[] args) throws IOException
	{
		BufferedReader br  = new BufferedReader(new InputStreamReader(System.in));
		
		File file = new File(FILES_TO_INDEX_DIRECTORY);
		
		if(file.exists())
		{
			TreeMap<String, TreeMap<String, Integer>> mappings = 
										new TreeMap<String, TreeMap<String, Integer>>(String.CASE_INSENSITIVE_ORDER);
			
			//create an inverted index map for each keyword that appers in the page
			mappings = Mapper.map(file);
			System.out.println("Enter Maximum Keywords Allowed for a web page and query: ");
			int maxKeywordsAllowed = Integer.parseInt(br.readLine());

			List<String> extractQueries = findQueries(file);
			int queryCount = 0;
			for(String s : extractQueries)
			{
				queryCount++;
				findPagesBasedOnQuery(s, "Q"+queryCount, maxKeywordsAllowed);
			}
		}

	}

	public static List<String> findQueries(File file) throws IOException
	{
		BufferedReader br = null;
		List<String> queries = new ArrayList<String>();
		try
		{
			br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			while(null != line)
			{
				String firstWord = line.substring(0, 1);
				if(firstWord.equals("Q"))
				{
					queries.add(line);
				}
				line = br.readLine();
			}
		}
		catch(FileNotFoundException e)
		{
			System.err.println("file not found : " + e.getMessage());
		}
		finally
		{
			try
			{
				if(null != br)
					br.close();
			}
			catch(IOException e)
			{
				System.err.println("Error while closing buffered stream : " + e.getMessage());
			}
		}
		return queries;
	}

	/* calculate strength of pages based on query and store it in hashmap
		Sort the hashmap based on the value. This will place the highest strength page at the top */
	public static void findPagesBasedOnQuery(String queryString, String queryName, int maxKeywordsAllowed)
	{
		TreeMap<String, Integer> strength = new TreeMap<String, Integer>();
		strength = KeyWordIndexing.reduce(queryString, maxKeywordsAllowed);

		//Sort the map based on values
		List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(strength.entrySet());
        Collections.sort( list, new Comparator<Map.Entry<String, Integer>>()
        {
            public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        } );

        /* check whether the query results are null or not */
        System.out.print(queryName + ":");
		if(strength.size() > 0)
		{
			int topSearchResults = 5;
			for(Map.Entry<String, Integer> entry : list)
			{
				if(topSearchResults > 0)
				{
					String fName = entry.getKey();
					int pos = fName.lastIndexOf(".");
					if(pos > 0)
						fName = fName.substring(0, pos);
					System.out.print(" " + fName);
					topSearchResults--;
				}
				else
					break;
			}
			System.out.println();
		}
	}
}