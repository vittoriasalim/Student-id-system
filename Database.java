/* PROVIDED DATABASE CLASS
 * Version     : 1.3
 * Revised Date: March 09, 2020 */
import java.util.Scanner;
import java.io.File;            
import java.io.IOException;     

public class Database
{
   // Global variables to be used throughout the program
   public final static int DEFAULT_NUM_ACCOUNT       = 100;
   
   public final static int ARRAY_ID_POS              =   0;
   public final static int ARRAY_NAME_POS            =   1;
   public final static int ARRAY_PASS_POS            =   2;
   public final static int ARRAY_START_OF_COURSE_POS =   3;
   
   String[] allAcct;   // The array that stores all the student�s information
   int numAcct;        // Keeps track of how many accounts are currently in the database
   
   //=================== CONSTRUCTOR ===================//
   /* The default constructor. Initializes the database. */
   public Database() {
      allAcct = new String[DEFAULT_NUM_ACCOUNT];
      numAcct = 0;
   }  
   
   /* The constructor that allows the caller to 
    * specify the maximum number of accounts it 
    * can store
    * @param maxNumAcct - The maximum number of accounts the
    *                     database can store */
   public Database( int maxNumAcct ) {
      allAcct = new String[ maxNumAcct ];
      numAcct = 0;
   }
   
   //=================== PUBLIC METHODS ===================//
   /* Used for testing purposes: Import account information from 
    * the textfile 
    * @param filename  - The file that contains the data. Use "StuSysAcctInfo.txt"
    * @param currNewId - The account ID # that the imported accounts will 
    *                    start with
    * @return          - The number of accounts that has been added to the
    *                    database */
   public int ImportAcct( String filename, int currNewId ) {
      numAcct = 0;
      
      try
      {
         // pass the path to the file as a parameter 
         File file = new File(filename); 
         Scanner sc = new Scanner(file); 
         String infoStr;
         
         while (sc.hasNextLine()) {
            infoStr = sc.nextLine();
            if( !infoStr.equals("") ) {
               allAcct[numAcct] = currNewId + ":" + infoStr;
               currNewId++;
               numAcct++;
            }
         }
         
         sc.close();
      }
      catch (IOException ex)
      {
         System.out.println("Import Error: Unable to complete the task");
      }  
      
      return numAcct;      
   }
   
   /* Create a new account 
    * @param acctInfo - The String that contains all the info for the account
    *                   to be created
    * @return         - The account creation status. 
    *                   True if the account is created successful
    *                   False otherwise */
   public boolean AddAcct(String acctInfo) {
      if( numAcct < allAcct.length ) {
         allAcct[numAcct] = acctInfo;
         numAcct++;
         return true;
      }
      else {
         return false;
      }
   }
   
   /* Checks if the database has the account id
    * @param id - The account ID
    * @return   - The status of the search. 
    *             Returns TRUE, if it is found
    *             Returns FALSE, otherwise */
   public boolean IsAcctExist(String id) {
      boolean result = false;
      for( int i = 0; i < numAcct; i++ ) {
         String acctInfoStr = allAcct[i];
         String[] acctInfoArr = acctInfoStr.split(":");
         
         if( acctInfoArr[Database.ARRAY_ID_POS].equals(id) ) {
            /* When the ID is found in the system */
            result = true;
            break;
         }
      }
      return result;
   }   

   /* Search for the account with the ID, and get the account name 
    * @param id - The account ID
    * @return   - The String that contains the account name */   
   public String GetAcctName( String id ) {
      String name = null;
      for( int i = 0; i < numAcct; i++ ) {
         String acctInfoStr = allAcct[i];
         String[] acctInfoArr = acctInfoStr.split(":");
         
         if( acctInfoArr[Database.ARRAY_ID_POS].equals(id) ) {
            /* When the ID is found in the system */
            name = acctInfoArr[ARRAY_NAME_POS];
            break;
         }
      }
      return name;      
   }   
   
   /* Search for the account with the ID, and get the account password 
    * @param id - The account ID
    * @return   - The String that contains the account password */   
   public String GetAcctPass( String id ) {
      String pass = null;
      for( int i = 0 ; i < numAcct; i++ ) {
         String acctInfoStr = allAcct[i];
         String[] acctInfoArr = acctInfoStr.split(":");
         
         if( acctInfoArr[Database.ARRAY_ID_POS].equals(id) ) {
            /* When the ID is found in the system */
            pass = acctInfoArr[ARRAY_PASS_POS];
            break;
         }
      }
      return pass;      
   }

   /* Replace the original password in an account with a new password 
    * @param id   - The account ID
    * @param pass - The new password to be used for the account
    * @return     - Returns TRUE, if successfully replaced the password 
    *               Returns FALSE, otherwise. */
   public boolean ReplaceAcctPass( String id, String pass ) {
      boolean result = false;
      String newAcctInfo = "";
      for( int i = 0; i < numAcct; i++ ) {
         String acctInfoStr = allAcct[i];
         String[] acctInfoArr = acctInfoStr.split(":");
         
         if( acctInfoArr[Database.ARRAY_ID_POS].equals(id) ) {
            /* When the ID is found in the system */
            for( int j = 0; j < acctInfoArr.length; j++ ) {
               if( j == 0 ) {
                  newAcctInfo += acctInfoArr[j];
               }
               else if( j == Database.ARRAY_PASS_POS ) {
                  newAcctInfo += ":" + pass;
               }
               else {
                  newAcctInfo += ":" + acctInfoArr[j];
               }
            }
            allAcct[i] = newAcctInfo;
            result = true;
            break;
         }
      }
      return result;       
   }
   
   /* Get all the course information of an account represented by a String 
    * @param id - The account ID
    * @return   - Returns the String that contains all the account's course information
    *             or else Return null if there's no courses. */
   public String GetAllCourseInfo( String id ) {
      String courseFullInfo = null;
      
      for( int i = 0; i < numAcct; i++ ) {
         String acctInfoStr = allAcct[i];
         String[] acctInfoArr = acctInfoStr.split(":", 4); 
         if( acctInfoArr.length != 3 && acctInfoArr[Database.ARRAY_ID_POS].equals(id) ) {
            courseFullInfo = acctInfoArr[3];
            break;
         }
      }
      return courseFullInfo;
   }

   /* Get the course information at a specified position in 
    * the database. 
    * @param id  - The account ID
    * @param pos - The position in the storage that stores the course 
    *              Eg. The first course enrolled will be stored at pos 0,
    *                  the second course will be stored at pos 1 and etc. 
    * @return    - The string that contains the course name and its grade, 
    *              in the format: [courseCode]-[courseGrade].
    *              Eg. ICS4U-95 */
   public String GetCourseAt( String id, int pos) {
      String courseInfo = null;
      for( int i = 0; i < numAcct; i++ ) {
         String acctInfoStr = allAcct[i];
         String[] acctInfoArr = acctInfoStr.split(":");
         
         if( acctInfoArr[Database.ARRAY_ID_POS].equals(id) ) {
            /* When the ID is found in the system */
            courseInfo = acctInfoArr[ARRAY_START_OF_COURSE_POS + pos];
            break;
         }
      }
      return courseInfo;        
   }   
   
   /* Add a course to the account with the given ID 
    * @param id         - The account ID
    * @param courseInfo - The String that contains the course.
    *                     It has the format [courseName]-tbd 
    * @return           - Returns TRUE if successful added the course 
    *                     Returns FALSE otherwise. */
   public boolean AddCourse( String id, String courseInfo ) {
      boolean result = false;
      for( int i = 0; i < numAcct; i++ ) {
         String acctInfoStr = allAcct[i];
         String[] acctInfoArr = acctInfoStr.split(":");
         if( acctInfoArr[Database.ARRAY_ID_POS].equals(id) ) {  
            allAcct[i] += courseInfo;
            result = true;
            break;
         }
      }
      return result;
   }    
   
   /* Replace all the course information of an account represented by a String 
    * @param id            - The account ID
    * @param newCourseInfo - The course info that will replace the existing info 
    *                        in the database. It has the format: 
    *                        ":[courseCode1]-[courseGrade1]:[courseCode2]-[courseGrade2]: ... :[courseCodeN]-[courseGradeN]"
    * @return              - Returns TRUE, if successfully replaced course info
    *                        Returns FALSE, otherwise */  
   public boolean ReplaceCourseInfo( String id, String newCourseInfo ) {
      boolean result = false;
      for( int i = 0; i < numAcct; i++ ) {
         String acctInfoStr = allAcct[i];
         String[] acctInfoArr = acctInfoStr.split(":", 4);         
         if( acctInfoArr[Database.ARRAY_ID_POS].equals(id) ) {
            allAcct[i] = acctInfoArr[0] + ":" + 
                         acctInfoArr[1] + ":" + 
                         acctInfoArr[2] + newCourseInfo;
            result = true;
            break;
         }
      }
      return result;
   } 
   
   /* For testing purposes */
   public void DisplayDatabase() {
      for( int i = 0; i < allAcct.length; i++ ) {
         if( allAcct[i] != null ) {
            System.out.println( allAcct[i] );
         }
         else {
            break;
         }
      }
   }
}