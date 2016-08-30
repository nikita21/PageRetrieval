# PageRetrieval

Search for the keywords in the webpages/files, and output the relevant pages based on the search query.
Calculating the priority of pages on the basis of position of the keyword in a file and the number of times that keyword exists in a file. 
This is a MapReduce Job which keeps track of the position as well the frequency of keyword in a file.

Compile : Mapper.java -> KeywordIndexing.java -> Search.java

Run Search.java
