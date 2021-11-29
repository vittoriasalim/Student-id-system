import java.util.Scanner; 
public class StuSysMain
{
  //main display
  private void MainMenu()
  {
    System.out.println("=====================================================================================");
    System.out.println("|                         WELCOME TO CIMP STUDENT SYSTEM                            |");
    System.out.println("=====================================================================================");
    System.out.println("|     1. NEW STUDENT     |       2. RETURNING STUDENT         |     3. EXIT SYSTEM  |");
    System.out.println("=====================================================================================");
    System.out.print("Pick an option:");
  }
  //edit course display
  private void EditCourse()
  {
    System.out.println("----------------------------------------");
    System.out.println("|           EDIT COURSE GRADE          |");
    System.out.println("----------------------------------------");
  }
  //add course display
  private void AddCourse()
  {
    System.out.println("----------------------------------------");
    System.out.println("|              ADD COURSES             |");
    System.out.println("----------------------------------------");
  }
  //student detail display
  private void StudentDetail()
  {
    System.out.println("----------------------------------------");
    System.out.println("|            STUDENT DETAIL            |");
    System.out.println("----------------------------------------");
  }
  
  public static void main(String[] args)
  {
    
    Scanner input = new Scanner(System.in);
    StuSys stuSys = new StuSys();              // DO NOT DELETE: The StuSys object that manages the system. 
    stuSys.GenAcct( "StuSysAcctInfo.txt" ); // The method that import the dummy data for testing. May erase after finish
    StuSysMain s = new StuSysMain();
    //indicating each variable to its datatype
    int option ;
    
    String name;
    int id,drop;
    String pass,retypePass,oldPass,newPass,retypeChangePass,loginPass,loginId;
    
    
    //do while loop is used to loop the main menu .it will execute the code first before checking 
    //the boolean expression 
    //means whenever it is 1 or 2 it will keep looping the main menu
    //.however when it is 3 it will exit the program
    
    do
    {
      //display of the main menu or option area where prompts get to choose their action
      
      s.MainMenu ();
      option = input.nextInt();
      
      
      //if statement is used to display the option 1
      if (option == 1 )
      {
        System.out.println("-------------------------");
        System.out.println("|   CREATE AN ACOOUNT   |");
        System.out.println("-------------------------");
        System.out.print("Student ID        : ");
        id = stuSys.currNewId; 
        System.out.print(id);
        //to move the cursor to the next line because the input before is an integer .
        input.nextLine();
        
        
        System.out.print("\nFull Name         : ");
        name = input.nextLine();
        
        
        System.out.print("Password          : ");
        pass = input.nextLine();
        System.out.print("Retype password   : ");
        retypePass=input.nextLine();
        //to recall the method from stuSys.java
        int createAcct = stuSys.CreateNewAcct(name,pass,retypePass);
        
        //if statement is used to state each function .and to give a message whether 
        //the account has successfully been created or not
        
        //if it recalls 2 .it will diplay a message it needs to be 6 digits
        if (createAcct == 2)
        {
          
          System.out.println("Error : Unable to create account - passwords needs to be 6 digits");
        }
        
        //if it recalls 1 acct has succesfully been created
        else if (createAcct == 1)
        {
          System.out.println("Succesfully created account \'"+id+"\'");
        }
        
        //if it recalls -1 will display a message the number of acct has reached the limit
        else if(createAcct == -1 )
        {
          System.out.println("Number of accounts has reached the limit ");
        }
        //it is 0 account cant be created because it doesnt match the password
        else if(createAcct == 0)
        {
          System.out.println("Error : Unable to create account - password does not match ");
        }
        
        
        
        
      }
      //second option is used to display the layout of login screen
      else if (option == 2)
      {
        System.out.println("-------------------------");
        System.out.println("|      LOGIN SCREEN     |");
        System.out.println("-------------------------");
        System.out.print("Student ID : ");
        input.nextLine();
        loginId = input.nextLine();
        
        System.out.print("Password   : ");
        loginPass = input.nextLine();
        
        
        
        
        //if statement indicating each function whether
        //the account has successfully logged in or not
        
        //if it is -1 account id cant be found
        if (stuSys.Login(loginId,loginPass) == -1 )
        {
          System.out.println("Account ID not found in the system.Try again");
        }
        //if it is 0 ,password is incorrect and it will ask user  try to log in again
        else if (stuSys.Login(loginId,loginPass)== 0)
        {
          System.out.println("Password incorrect.Try again.");
        }
        //if it is 1 login is success and it will display the selection area
        //where student could choose to perform
        else if(stuSys.Login(loginId,loginPass) ==1)
        {
          loginId=stuSys.loginUserId;
          System.out.println("Login Success");
          
          //while loop is used to showcase some fuction for the logged in prompts
          //it will keep looping/executing the code unless if it is 6 it will exit the loop
          while (true )
          {
            //the display or the layout for the logged in pro3mpts
            System.out.println("============================================================");
            System.out.println("|                    CIMP STUDENT SYSTEM                   |");
            System.out.println("|                       OPTION SCREEN                      |");
            System.out.println("============================================================");
            System.out.println("|   1. STUDENT DETAIL        |    4. EDIT COURSE GRADE     |");
            System.out.println("------------------------------------------------------------");
            System.out.println("|   2. ADD COURSE            |    5. CHANGE PASSWORD       |");
            System.out.println("------------------------------------------------------------");
            System.out.println("|   3. DROP COURSE           |    6. LOG OUT               |");
            System.out.println("------------------------------------------------------------");
            System.out.println("Pick an option :");
            int select = input.nextInt();
            //if "select" equals to 1 ,it will display the first option of the the log in menu
            //student detail
            if ( select ==1)
            {
              //recall the method in the stuSys.java program 
              //regarding the student name
              String studentName = stuSys.GetStudentName(loginId);
              
              
              //the display layout for the student detail
              s.StudentDetail();
              
              
              System.out.printf("|Student ID   :              %10s",loginId);
              System.out.println("|");
              System.out.printf("|Student name :    %20s",studentName);
              System.out.println("|");
              //it recalls and display the gpa of the students from the method in the 
              //stuSys.java
              System.out.printf("|GPA          : %22.2f%%",stuSys.GetGPA(""+loginId));
              System.out.println("|");
              
              System.out.println("----------------------------------------");
              //the display of the course info in the student detail
              System.out.println("----------------------------------------");
              System.out.println("|     COURSES      |       GRADES      |");
              System.out.println("----------------------------------------");
              int numCourse = stuSys.NumCourse(loginId);
              
              
              
              //if statement is used to check whether there is any course inside
              //if no course availlable it will display nothing
              if(numCourse ==0 )
              {
                System.out.print(" ");
              }
              else
              {
                //for loop is used to display each courses and its grades to 
                //to the student detail based on the number of courses the prompts has
                for (int i = 0;i < numCourse ; i++)
                {
                  
                  String courseName =stuSys.GetCourseNameAt(loginId,i);
                  String courseGrade=stuSys.GetCourseGradeAt(loginId,i);
                  System.out.printf("| %-17s|",courseName);
                  System.out.printf("%19s|\n",courseGrade);  
                }
              }
              
              
              System.out.println("----------------------------------------");
              
              System.out.println("Press enter to continue");
              //it will continue when prompt "enter" 
              try{System.in.read();}
              catch(Exception e){}
              
            }
            //if select equals to 2 ,it will display the second option of the add course display
            else if ( select ==2)
            {
              //display for add course header
              s.AddCourse();
              System.out.println("|YOUR CURRENT COURSE:                  |");
              input.nextLine();
              
              int numCourse1 = stuSys.NumCourse(""+loginId);
              //if statement is used to check whether there is any course store inside 
              //if no,it will display nothing
              if(numCourse1==0)
              {
                System.out.print(" ");
              }
              else
              {
                //for loop is used to display each grades and courses from each position 
                //based of the number of courses the prompts has 
                for (int i = 0 ;i < numCourse1 ; i++)
                {
                  String courseName =stuSys.GetCourseNameAt(loginId,i);
                  String courseGrade=stuSys.GetCourseGradeAt(loginId,i);
                  System.out.printf("|%-20s",courseName);
                  System.out.printf("%-18s|\n",courseGrade);
                  
                }
              }
              
              System.out.println("----------------------------------------");
              
              System.out.print("Course to add:");
              String course = input.nextLine();
              //to recall the method from stSys.java to the main to be displayed
              boolean addCourse = stuSys.AddCourse(""+loginId,course);
              
              
              //if addCourse method recalls true ,course has successfully been added
              if(addCourse==true)
              {
                
                System.out.println("Successfully added"+"\'"+course+"\'");
                
              }
              //if addCourse method recalls false ,course has not successfully been added
              else if (addCourse==false)
              {
                System.out.println("Error : can't add "+"\'"+course+"\'");
              }
              
              
            }
            //if select equivalent to 3 it will display drop courses layout
            else if (select == 3)
            {
              System.out.println("----------------------------------------");
              System.out.println("|              DROP COURSES            |");
              System.out.println("----------------------------------------");
              System.out.println("|YOUR CURRENT COURSE:                  |");
              //it will recalls the NumCourse from the method stuSys.java
              int numCourse2 = stuSys.NumCourse(""+loginId);
              //if numCoruse equivalent to 0 it will display nothing
              if(numCourse2==0)
              {
                System.out.print(" ");
              }
              else
              {
                //for loop is used to display each position of the course name and course grade
                for (int i = 0 ;i < numCourse2 ; i++)
                {
                  
                  String courseName =stuSys.GetCourseNameAt(loginId,i);
                  String courseGrade=stuSys.GetCourseGradeAt(loginId,i);
                  System.out.printf("|"+(i+1));
                  System.out.printf(" %-18s",courseName);
                  System.out.printf("%-18s|\n",courseGrade);
                }
                
              }
              System.out.println("----------------------------------------");
              input.nextLine();
              System.out.println("Select the number beside courses to drop: ");
              drop=input.nextInt();
              
              //to recall the method from the delete course stuSys.java
              int dropCourse = stuSys.DeleteCourse(""+loginId,drop-1);
              //if it recalls "1" course has successfully been dropped
              if(dropCourse==1)
              {
                
                System.out.println("Successfully delete"+"\'"+drop+"\'");
                
              }
              //if it recalls "0" course cant be deleted
              else if (dropCourse==0)
              {
                System.out.println("Error : can't deleted "+"\'"+drop+"\'");
              }
              //if it recalls "-1" course has exceed the limit
              else if(dropCourse ==-1)
              {
                System.out.println("Error.Invalid course option");
              }
              
              System.out.println("----------------------------------------");
              
            }
            //select 4 will display edit course display
            else if (select == 4)
            {
              s.EditCourse();
              
              //recalls numCourse method from the stuSys.java
              int numCourse3 = stuSys.NumCourse(loginId);
              for (int i = 0 ;i < numCourse3; i++)
              {
                //for loop is used to display each position of the course name and grade
                String courseName =stuSys.GetCourseNameAt(loginId,i);
                String courseGrade=stuSys.GetCourseGradeAt(loginId,i);        
                System.out.printf("|"+(i+1));
                System.out.printf(" %-18s",courseName);
                System.out.printf("%-18s|\n",courseGrade);
                
              }
              System.out.println("----------------------------------------");
              input.nextLine();
              System.out.print("Select the number beside courses to edit: ");
              int posGrade=input.nextInt();
              input.nextLine();
              System.out.print("Enter the new Grade");
              int newGrade=input.nextInt();
              
              
              //to recall the function of EditCourse method in the stuSys.java 
              int edit = stuSys.EditCourse(loginId,posGrade-1,newGrade);
              //if edit equivalent to -1 the position of the course is not in the data
              if(edit ==-1)
              {
                System.out.println("Error: Invalid course option");
              }
              //if it recalls -2 the new grade enter does not match with mathematical percentage
              else if (edit==-2)
              {
                System.out.println("Error: Invalid new grade "+"\'"+newGrade+"\'");
              }
              //if it recalls one course has successfully been edited
              else if(edit==1)
              {
                
                System.out.println("Successfully edited the course");
                
              }
              //if it recalls 0 course can't be edited
              else if (edit==0)
              {
                System.out.println("Error : can't edited "+"\'"+newGrade+"\'");
              }
              
            }
            else if (select == 5)
            {
              //display of change password
              System.out.println("----------------------------------------");
              System.out.println("|           CHANGE PASSOWORD           |");
              System.out.println("----------------------------------------");
              
              input.nextLine();
              System.out.print("Enter old password : ");
              oldPass = input.nextLine();
              System.out.print("Enter new password : ");
              newPass = input.nextLine();
              System.out.print("Retype password    : ");
              retypeChangePass = input.nextLine();
              //it is used to recall the funciton of ChangePassword method in the stuSys.java
              int replacePass = stuSys.ChangePassword(""+loginId,oldPass,newPass,retypeChangePass);
              //if the method return 1 course has successfully been replaced
              if(replacePass ==1)
              {
                System.out.println("Successfully changed the account's password ");
              }
              //if method return 0 
              //it gives a message acct id cant be found
              else if (replacePass == 0)
              {
                System.out.println("Error : Account ID not found in the system");
              }
              //if it returns -1
              //it gives a message old password is incorrect
              else if (replacePass == -1)
              {
                System.out.println("Error : Old password incorrect");
              }
              // if it returns -1 
              //it gives a message new password doesnot match retypr password 
              else if (replacePass == -2)
              {
                System.out.println("Error : New password does not match retype password");
              }
              
              
            }
            //to break the loop or exit the main meu when the prompts enters 6
            else if (select == 6)
            {
              break;
            }
            //To give a warning incase if the selection is not in the login menu 
            else if (select != 1||select != 2||select != 3||select != 4||select != 5||select!=6)
            {
              System.out.println("SELECTION IS NOT IN THE OPTIONS ");
            }
            
          }
          
        } 
      }
      
      
    }
    
    
    //to break the the loop or to exit the main menu when the prompts enter 3
    while (option != 3);
    System.out.println("Thank You for using.");
    
  }
}



