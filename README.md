# Company Reception
Java GUI that is connected to a Database. Implements a company reception with basic commands

### The program has the following private attributes:
1. Visitor’s first name
2. Visitor’s last name
3. Company from
4. Visitor’s ID
5. Photo
6. Staff visiting name
7. Office no.
8. Date time in (timestamp)
9. Date time out (timestamp)

### The program has the following menu and menu items:
1. File
   * Add Visitor
   * Modify Visitor
   * Display Visitor History
   * Today Report
   * Exit 
2. Help
   * Help Contents

### Items menu description:
* Add visitor – adds a new visitor. Should include the visitor’s photo.
* Modify visitor – search is made first by visitor’s id, then if found will display modify frame. Should include the visitor’s photo.
* Display visitor history – search is made first by visitor’s id, then if found will display visitor history (using a JTable).
* Today Report – displays all visits of that date (in a JTable).
* Help contents – displays the help of program.

All the records or data is saved into a database and the photo image into a default directory.

Before opening the Modify and Display visitor history frame you should open a dialog window search frame asking the visitor’s id. Once you enter the value then you will open the Modify or Display visitor history frames.

For the Display visitor history and Today Report frames a JTable component is used.

In Add, Modify and Display visitor history frame there is the visitor’s photo. The program uses the class JFileChooser in order to get the visitor’s photo name.