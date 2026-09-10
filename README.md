Student Grade Tracker

A lightweight, interactive console-based Java application designed to help educators and administrators manage student records, track academic performance, and generate summary reports.

🚀 Features

1. Add Students: Easily input student names and numerical marks (0-100).
2. View Records: Display a complete list of all students alongside their marks and automatically        
calculated letter grades.
3. Search & Update: Quickly locate specific students by name to view their details or update their marks.
4. Delete Records: Remove a student from the database when necessary.
5. Performance Analytics: Calculate the class average and instantly identify the highest and lowest-performing students.
6. Summary Report: Generate a comprehensive final report detailing all student records and class statistics in one view.
7. Input Validation: Includes built-in error handling to prevent application crashes from invalid data entries (e.g., entering text instead of numbers).

📊 Grading Scale

The application automatically assigns letter grades based on the inputted marks using the following scale:


Marks Range            Letter Grade
___________________________________
90 - 100.                 A
___________________________________
+80 - 89.                 A
___________________________________
70 - 79.                  B
___________________________________
60 - 69.                  C
___________________________________
50 - 59.                  D
___________________________________
0 - 49.                   F
___________________________________


🛠️ Prerequisites

To run this application, you will need:

•Java Development Kit (JDK): Version 8 or higher installed on your system.

•Terminal or Command Prompt: To compile and execute the Java file.

💻 How to Run

1. Download the Source Code: Save the provided Java code as StudentGradeTracker.java in your preferred directory.
2. Open Terminal/Command Prompt: Navigate to the folder where the file is saved.
3. Compile the Program: Run the following command to compile the StudentGradeTracker.java
4. Execute the Program: Once compiled successfully, start the application by running:java StudentGradeTracker

📂 Project Structure

•Student Class: A data model representing an individual student, containing properties for their name, marks, and grade, along with the logic to calculate the grade.

•StudentGradeTracker Class: The main execution class that houses the interactive menu loop, the ArrayList database, and all core operations (adding, updating, viewing, and deleting records).

Project Directory
 └── StudentGradeTracker.java
     │
     ├── class Student (Data Model)
     │    ├──  String name
     │    ├──  double marks
     │    ├──  String grade
     │    ├──  Student(String name, double marks)     // Constructor
     │    └──  void updateMarks(double marks)         // Grade calculation logic
     │
     └── public class StudentGradeTracker (Main Application)
          ├──  static Scanner sc                        // Global scanner for input
          ├──  public static void main(String[] args)    // Main execution & menu loop
          ├──  static int readMenuChoice()              // Input validation for menu
          └──  static double readMarks()                // Input validation for marks