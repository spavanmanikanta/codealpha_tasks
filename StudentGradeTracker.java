import java.util.ArrayList;
import java.util.Scanner;

class Student {

  String name;

  double marks;

  String grade;

  Student(String name, double marks) {

    this.name = name;

    updateMarks(marks);

  }

  void updateMarks(double marks) {

    this.marks = marks;

    if

    (marks >= 90)

      grade = "A+";

    else if (marks >= 80)

      grade = "A";

    else if

    (marks >= 70)

      grade = "B";

    else if

    (marks >= 60)

      grade = "C";

    else if

    (marks >= 50)

      grade = "D";

    else

      grade = "F";

  }

}

public class StudentGradeTracker {

  static Scanner sc = new Scanner(System.in);

  public static void main(String[] args) {

    ArrayList<Student> students = new ArrayList<>();

    int choice;

    do {

      System.out.println("===== Student Grade Tracker =====");

      // ============== Main Manu of Student Grade Tracker ============//

      System.out.println("1. Add Student");

      System.out.println("2. Search for Student");

      System.out.println("3. View Students");

      System.out.println("4. Update Student Marks");

      System.out.println("5. Delete Student");

      System.out.println("6. Show Highest Marks");

      System.out.println("7. Show Average Marks");

      System.out.println("8. Show Lowest Marks");

      System.out.println("9. Display Summary Report");

      System.out.println("10. Exit");

      choice = readMenuChoice();

      switch (choice) {

        // ================= Add Student =================

        case 1:

          System.out.print("Enter Student Name : ");

          String name = sc.nextLine().trim();

          if (name.isEmpty()) {

            System.out.println("Name shouldn't be empty.");

            break;

          }

          double marks = readMarks();

          if (marks == -1) {

            System.out.println("Invalid marks. Enter numbers between 0 and 100.");

            break;

          }

          students.add(new Student(name, marks));

          System.out.println("Student Added Successfully!");

          break;

        // ================= Search for Student =================

        case 2:

          System.out.print("Enter Student Name: ");

          String searchName = sc.nextLine().trim();

          boolean found = false;

          for (Student s : students) {

            if (s.name.equalsIgnoreCase(searchName)) {

              System.out.println("Search Student Found");

              System.out.println("--------------------------");

              System.out.println("Name  : " + s.name);

              System.out.println("Marks : " + s.marks);

              System.out.println("Grade : " + s.grade);

              System.out.println("--------------------------");
              found = true;

              break;

            }

          }

          if (!found) {

            System.out.println("Student Not Found.");

          }

          break;

        // ================= View Students =================

        case 3:

          if (students.isEmpty()) {

            System.out.println("Student Not Found.");

          } else {

            System.out.println("------ Student Records ------");

            for (Student s : students) {

              System.out.println("=============================");

              System.out.println("Name  : " + s.name);

              System.out.println("Marks : " + s.marks);

              System.out.println("Grade : " + s.grade);

              System.out.println("=============================");
            }

          }

          break;

        // ================= Update Student Marks =================

        case 4:

          System.out.println("=============================================");

          System.out.print("Enter Student Name: ");

          String updateName = sc.nextLine().trim();

          boolean updated = false;

          for (Student s : students) {

            if (s.name.equalsIgnoreCase(updateName)) {

              double newMarks = readMarks();

              if (newMarks == -1) {

                System.out.println("Invalid marks. Enter numbers between 0 and 100.");

                updated = true;

                break;

              }

              s.updateMarks(newMarks);

              System.out.println("Update Student Marks: Successfully completed!");

              System.out.println("=============================================");

              updated = true;

              break;

            }

          }

          if (!updated) {

            System.out.println("Student Not Found.");

          }

          break;

        // ================= Delete Student From Records =================

        case 5:

          System.out.print("Enter Student Name: ");

          String deleteName = sc.nextLine().trim();

          boolean deleted = false;

          for (int i = 0; i < students.size(); i++) {

            if (students.get(i).name.equalsIgnoreCase(deleteName)) {

              students.remove(i);

              System.out.println("---------------------------------------");

              System.out.println("Delete Student: Successfully completed!");

              System.out.println("---------------------------------------");

              deleted = true;

              break;

            }

          }

          if (!deleted) {

            System.out.println("----------------------------------");

            System.out.println("Delete Student: Student Not Found.");

            System.out.println("----------------------------------");
          }

          break;

        // ================= Highest Marks of Students =================

        case 6:

          if (students.isEmpty()) {

            System.out.println("Highest Marks: Student Record Not Found.");

          } else {

            Student highest = students.get(0);

            for (Student s : students) {

              if (s.marks > highest.marks) {

                highest = s;

              }

            }

            System.out.println("-------Highest Marks-------");

            System.out.println("===========================");

            System.out.println("Name  : " + highest.name);

            System.out.println("Marks : " + highest.marks);

            System.out.println("Grade : " + highest.grade);

            System.out.println("===========================");

          }

          break;

        // ================= Average Marks of Students =================

        case 7:

          if (students.isEmpty()) {

            System.out.println("Average Marks: No Student Records Found.");

          } else {

            double total = 0;

            for (Student s : students) {

              total += s.marks;

            }

            double average = total / students.size();

            System.out.println("---------------------------------");

            System.out.printf("Average Marks: %.2f%n", average);

            System.out.println("---------------------------------");
          }

          break;

        // ================= Lowest Marks of Students =================

        case 8:

          if (students.isEmpty()) {
            System.out.println("-----------------------------------------");

            System.out.println("Lowest Marks: No Student Records Found.");

            System.out.println("-----------------------------------------");

          } else {

            Student lowest = students.get(0);

            for (Student s : students) {

              if (s.marks < lowest.marks) {

                lowest = s;

              }

            }

            System.out.println("-------Lowest Marks------");

            System.out.println("--------------------------");

            System.out.println("Name  : " + lowest.name);

            System.out.println("Marks : " + lowest.marks);

            System.out.println("Grade : " + lowest.grade);

            System.out.println("--------------------------");

          }

          break;

        // ================= Final Report =================

        case 9:

          if (students.isEmpty()) {

            System.out.println("-------------------------");

            System.out.println("No Student Records Found.");

            System.out.println("-------------------------");

          } else {

            double total = 0;

            Student highest = students.get(0);

            Student lowest = students.get(0);

            System.out.println("========== SUMMARY REPORT of Students ==========");

            for (Student s : students) {

              System.out.println("--------------------------");

              System.out.println("Name  : " + s.name);

              System.out.println("Marks : " + s.marks);

              System.out.println("Grade : " + s.grade);

              System.out.println("--------------------------");

              total += s.marks;

              if (s.marks > highest.marks) {

                highest = s;

              }

              if (s.marks < lowest.marks) {

                lowest = s;

              }

            }

            double average = total / students.size();

            System.out.println(

                "\nTotal Students : " + students.size());

            System.out.printf(

                "Average Marks : %.2f%n",

                average);

            System.out.println("-------Highest Marks------");
            System.out.println("--------------------------");
            System.out.println("Name  : " + highest.name);
            System.out.println("Marks : " + highest.marks);
            System.out.println("Grade : " + highest.grade);
            System.out.println("--------------------------");
            System.out.println("-------Average Marks-------");
            System.out.println("--------------------------");
            System.out.printf("Average Marks : %.2f%n", average);
            System.out.println("--------------------------");
            System.out.println("-------Lowest Marks-------");
            System.out.println("--------------------------");
            System.out.println("Name  : " + lowest.name);
            System.out.println("Marks : " + lowest.marks);
            System.out.println("Grade : " + lowest.grade);
            System.out.println("--------------------------");

          }

          break;

        case 10:

          System.out.println("Thanks, for using ");

          break;

        // ================= Invalid Choice =================

        default:

          System.out.println("Invalid Choice!");

      }

    } while (choice != 10);

    sc.close();

  }

  // ================= Main Menu Choice Reader =================

  static int readMenuChoice() {

    System.out.print("Enter your choice: ");

    while (true) {

      String input = sc.nextLine().trim();

      try {

        return Integer.parseInt(input);

      } catch (NumberFormatException e) {

        System.out.print("Invalid input. Please enter a number between 1 and 10: ");

      }

    }

  }

  // ================= Marks Reader =================

  static double readMarks() {

    System.out.print("Enter Marks: ");

    while (true) {

      String input = sc.nextLine();

      try {

        double value = Double.parseDouble(input);

        if (value < 0 || value > 100) {

          return -1;

        }

        return value;

      } catch (NumberFormatException e) {

        System.out.print("Invalid input, please enter valid Input: ");

      }

    }

  }
}
