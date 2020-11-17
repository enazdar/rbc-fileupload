# rbc-fileupload

Assumptions/ Requirements:
In order to succesfully run the project
Java 8 or higher
since no extrenal tools or application is being used, no DBs are integrated
in order to search the file or update it, one must upload the file everytime the server is restarted

Things to improve:
1. integrate a DB or a systems to keep the data securly
2. integrate user access (login, read write)
3. Integrate error and exception handling
4. Integrate more unit testcase

What I really wanted to add was:
a.. the objectvalues so that the data that is being uploaded be parsed
and stored into this object values (to resolve the integrity of data and improve error handling )
b.. Updating the file is now have no way of stopping the user from adding invalid data.
c.. Search is only happening in the stock id, which again is not being processed.
d.. I did not write a parser to parse the data file and read into the data object values
