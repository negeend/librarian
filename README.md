# librarian
A library operating system called Librarian in the Java programming language. 

Librarian stores collections of Member accounts and Book objects. 
Member accounts are identified by their member number, and store their name, the books they are currently renting, and their rental history. 
Member accounts start at 100000 and increment up (such that the second Member account will have member number 100001).

A Book is identified by its serial number, and store the title, author, genre, member currently, and rental history. 
Members are able to rent and return books, with only one member able to rent a book at any time. 
Books are considered copies of each other if they have the same title and author (note that copies of a book all have different serial numbers).

Book collections can be read from and archived in a CSV (comma-separate values) file, stored in order of serial number in the following form:
<serial number>,<title>,<author>,<genre>

rchived books can be added to Librarian, either individually or as a whole collection. 
If any serial numbers are already present in the system, those books are not added.
