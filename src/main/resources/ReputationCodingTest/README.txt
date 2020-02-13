## Reputation Take Home Coding Test (Process and Transform PII)
This is the Process and Transform PII Test

#Take Home Coding Test (Process and Transform PII) 
Process PII (Personally Identifiable Information) and transform the data into JSON documents. 
We are looking for:
* A well thought out design
* Clean code 
* Attention to quality
* Clear execution instructions 
* A successful master test input file execution

Input to your program 
A file with multiple lines of text. Each line contains “PII” information, which consists of a first name, last name, phone number, zip code, and favorite color.
Sample Formats:
* LastName, FirstName, Phone, Zip Code, Color
* LastName, FirstName, Zip Code, Phone, Color
* LastName, FirstName, Color, Phone, Zip Code
* FullName(FirstName LastName), Phone, Zip Code, Color
* FullName(FirstName LastName), Zip Code, Color, Phone
First attribute is Name followed by phone number, zip code, and color in any order.
If there are 5 attributes then the first attribute is last name, second attribute is first name, followed by phone number, zip code, and color in any order
If there are 4 attributes then the first attribute is full name(first name last name), followed by phone number, zip code, and color in any order
Valid Zip Code consists of 5 digits only
Valid Phone Number is a US phone number
Make meaningful assumptions to develop a good solution
Input file may contain invalid lines and should not interfere with the processing of subsequent valid lines. 

Output from your program 
The program outputs a valid formatted JSON object. 
The JSON representation should be indented with two spaces and the keys should be sorted in ascending alphabetical order by (last name, first name) 
Successfully processed lines should result in normalized addition to the list associated with the “entries” key. 
For lines that were unable to be processed, a line number i (where 0 ≤ i < n) for each faulty line should be appended to the list associated with the “errors” key. 

Your program must execute the following way: 
Solution <inputfile.txt> outputfile.txt 

Attached are the sample input and output files. 

Please submit a zip file (example Eclipse Project Archive) with your solution. 

This is the time to show case your experience and all the best!





