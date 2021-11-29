public class StuSys
{
  // Global variables from the StuSys class. 
  public final static int LOGIN_SUCCESS                =  1;
  public final static int LOGIN_PASSWORD_INCORRECT     =  0;
  public final static int LOGIN_ID_NOT_FOUND           = -1;
  
  //indicate the constant value for create acct function
  public final static int CREATE_ACCT_SUCCESS          =  1;
  public final static int RETYPE_PASSWORD_NOT_MATCH    =  0;
  public final static int MAX_NUM_ACCT_REACHED         = -1;
  public final static int PASSWORD_6_DIGIT             =  2;
  //indicate the constant value for change pass function
  public final static int CHANGE_PASS_SUCCESS          =  1;
  public final static int ACCT_NOT_IN_DATABASE         =  0;
  public final static int OLD_PASS_INCORRECT           = -1;
  public final static int NEW_PASS_NOT_MATCH           = -2;
  //indicate the constant value for replace course function
  public final static int REPLACE_COURSE_SUCCESS       = 1;
  public final static int REPLACE_COURSE_ID            = 0;
  public final static int REPLACE_COURSE_POS             =-1;
  //indicate the constant value for edit course function
  public final static int EDIT_COURSE_SUCCESS          = 1;
  public final static int EDIT_COURSE_UNSUCCESS        = 0;
  public final static int EDIT_INVALID_POS             =-1;
  public final static int EDIT_INVALID_GRADE           =-2;
  
  
  
  Database db;            // The Database object
  String loginUserId ;     // Stores the currently logged User ID
  int currNewId;          // The next new user's ID. 
  
  
  //========== CONSTRUCTOR ==========// 
  public StuSys() {
    db = new Database();    // Creating a Database object
    currNewId = 1000000;
    
    
  }
  
  //========= PUBLIC METHOD =========//
  /* PROVIDED METHOD: Used to import dummy data into the database.
   *                  Could be used whenever you need to test your 
   *                  program 
   * @param filename - The filename of the textfile that contains all 
   *                   dummy data. */
  public void GenAcct( String filename ) {
    currNewId += db.ImportAcct( filename, currNewId );
  }
  
  /* Create a new account with given info. It will also check if 
   * password and retype password matches
   * @param name       - The name for the account 
   * @param pass       - The password for the account
   * @param retypePass - The retype password to be used to check with the password
   * @return           - The status of the account creation process 
   *                     Returns 1, if account is created successfully 
   *                     Returns 0, if password and retype password don't match 
   *                     Returns -1, if number of accounts have reached its limit */
  public int CreateNewAcct(String name, String pass, String retypePass ) 
  {
    //indicating the datatype to the variable to be returned
    int result = 0;
    
    //to check if the password is 6 digits or not
    if (retypePass.length() != 6)
    {
      result = PASSWORD_6_DIGIT;
    }
    //if pass word not math retype password
    else if ( !(pass.equals(retypePass)))
    {
      result = RETYPE_PASSWORD_NOT_MATCH;
    }
    //if acct has successfully been created
    else if (db.AddAcct(currNewId+":"+name+":"+pass)==true && pass.equals(retypePass))
    {
      result = CREATE_ACCT_SUCCESS;  
      currNewId++;
    }
    //num of acct has exceed the limit
    else 
    {
      result =MAX_NUM_ACCT_REACHED;
    }
    return result;
    
  }
  
  /* Login a user with the given student ID and password
   * @param id   - The account ID
   * @param pass - The password for the account
   * @return     - Returns 1, if the ID and password both matches the info in the database 
   *               Returns 0, if the ID matches, but not the password 
   *               Returns -1, if both ID and password don't match */
  
  public int Login(String id, String pass) 
  {
    //indicatin datatype to the variable
    int resultLogin  =0;
    String status = db.GetAcctPass(id);
    boolean acct = db.IsAcctExist(id);
    
    //if statement is used to check whether the the password and id matches the saved acc
    //also to check the requirements to login
    if (acct == false)
    {
      resultLogin = LOGIN_ID_NOT_FOUND;
    }
    
    else if(acct== false && !(pass.equals(status)))
    {
      resultLogin = LOGIN_PASSWORD_INCORRECT;
    }
    else if(acct== true && pass.equals(status)) 
    {
      loginUserId = id;
      resultLogin = LOGIN_SUCCESS;
    }
    return resultLogin;  // Return value needs to be changed
    
  }
  
  /* Change the account's password
   * @param id   - The account ID
   * @param pass - The new password for the account
   * @return     - Returns 1, if successfully changes the password
   *               Returns 0, if account not found in the database
   *               Returns -1, if old password is incorrect
   *               Returns -2, if new password doesn't match retype password */   
  public int ChangePassword( String id, String oldPass, String newPass, String retypePass ) 
  {
    //if statement is used to check if the password is successfully changes or not 
    //also to check if the old password is incorrect or doesnt match with new pass
    int resultPass=0;
    if(!(oldPass.equals(db.GetAcctPass(id))))
    {
      resultPass=OLD_PASS_INCORRECT;
    }
    else if (db.ReplaceAcctPass(id,newPass)== false )
    {
      resultPass=ACCT_NOT_IN_DATABASE;
    }
    
    else if (!(newPass.equals(retypePass)))
    {
      resultPass=NEW_PASS_NOT_MATCH;
    }
    else if (db.ReplaceAcctPass(id,newPass)==true)
    {
      resultPass=CHANGE_PASS_SUCCESS;
    }
    
    
    
    
    
    return resultPass; // Return value needs to be changed
  }
  
  /* Get the student's name with the given user id from 
   * the database. 
   * @id     - The user id of the account
   * @return - The student name of the account */
  public String GetStudentName(String id) 
  {
    //getAcctName method is taken from the database method to display the name of the student
    //base of her student id
    
    return db.GetAcctName (id) ; // Return value needs to be changed
  }
  
  /* Get the course's name stored at the specified position in the database
   * @param id  - The account ID
   * @param pos - The pos where the course is stored in. 
   *              First course is at pos 0, second is at pos 1, etc.
   * @return    - The name of the course at pos */
  public String GetCourseNameAt(String id, int pos)
  {
    String result = " ";
    String course = db.GetAllCourseInfo(id);
    
    //if statement is used to check if there is any information stores inside 
    //if no it will return nothing
    if(course==null)
    {
      result =" ";
    }
    //if yes it get the course name
    else 
    {
      //split the array to get each course Name in the array 
      String [] courseName=db.GetCourseAt(id,pos).split("-");
      //0 indicates the position of the course (coursename - coursegrade)
      result = courseName[0];
    }
    
    
    return result;
    
    
    
    // Return value needs to be changed
  }
  
  
  
  /* Get the course's grade stored at the specified position in the database
   * @param id  - The account ID
   * @param pos - The pos where the course is stored in. 
   *              First course is at pos 0, second is at pos 1, etc.
   * @return    - The grade of the course at pos */
  public String GetCourseGradeAt(String id, int pos) {
    
    String result =" ";
    String course = db.GetAllCourseInfo(id);
    //to check id there is any course inside if no it will return nothing
    if(course==null)
    {
      result =" "; 
    }
    //if yes it will get the course grade
    else 
    {
      //split the course name and course grade from each position
      String [] courseGrade = db.GetCourseAt(id,pos).split("-");
      //1 indicates the position of the course grade (coursename - coursegrade)
      result = courseGrade[1];
    }
    return result; 
  }
  
  /* Get the number of courses the account has 
   * @param id - The account ID
   * @return   - The number of courses the account has */     
  public int NumCourse(String id) {
    String course = db.GetAllCourseInfo (id);
    int result =0;
    //check if there is a course inside the array or student id 
    if(course!= null)
    {
      //if yes it will split each courses and count the length of the course 
      String[] allCourse = course.split(":");
      int numCourse = allCourse.length ;
      result =numCourse;
    }
    //if no numcourse will be 0
    else 
    {
      //id no it will return 0 ,indicates no course number were stores inside
      result =0;
    }
    
    return result ;  // Return value needs to be changed
    
  }
  
  /* Get the student's GPA (overall average)
   * @param id - The account ID
   * @return   - The GPA of the student. 
   *             If the student doesn't have any course, it will return 0. */
  public double GetGPA(String id) {
    double sum = 0;
    int courseCount=0;
    double result =0;
    
    //if statement to check if there is a course inside
    if(db.GetAllCourseInfo(id)==null)
    {
      //if no it will return 0 ,means no course being stored
      result =0;
      
    }
    else
    {
      //if yes it will check the course of the grade by for loop
      //to get each position of the grade
      for(int i =0;i<NumCourse(id);i++)
      {
        //if it doesnt has any "tbd" value it will convert the grades into integer value and sum each grade
        if(!(GetCourseGradeAt(id,i).equals("tbd")))
        {
          int courseGrade =Integer.parseInt(GetCourseGradeAt(id,i));
          sum+=courseGrade;
          //courseCount is used to count the number of courses, the course grade has
          courseCount++;
        }
        
      }
      //then it will be divided ,to get the gpa of the student
      result=sum/courseCount;
    }
    
    return result;    // Return value needs to be changed
  }
  
  
  
  
  /* Add a course to the account
   * @param id         - The account that the course will be added to
   * @param courseInfo - The String that contains the course name.
   * @return           - Returns TRUE if successful added the course 
   *                     Returns FALSE otherwise. */
  public boolean AddCourse( String id, String courseInfo ) {
    boolean result = false ;
    //indicates the format of the courseinfo ":"+course info "-"+"tbd"
    boolean status = db.AddCourse(id,":"+courseInfo+"-"+"tbd");
    //if true it will successfully add new course into the student account
    if(status == true )
    {
      result =true;
    }
    //if false it wont add any new course into the student account
    else if(status == false)
    {
      result = false;
    }
    
    
    
    return result;  // Return value needs to be changed
  }      
  
  /* Delete a course in an account 
   * @param id  - The account ID
   * @param pos - The pos where the course is stored in. 
   *              First course is at pos 0, second is at pos 1, etc. 
   * @return    - Returns 1, if successful deleted the course 
   *              Returns 0, if ID is not in the system
   *              Returns -1, if pos specified is beyond the number of courses
   *                          in the course.  */
  public int DeleteCourse( String id, int pos ) 
  {
    int result = 0;
    boolean status = false;
    String courseInfo = "";
    //to detemine whether the position is within the limit or not
    if(pos >NumCourse(id))
    {
      result =-1;
    }
    else
    {
      //it will print the course and grade to one position behind the course to be deleted
      for (int i = 0;i<pos;i++)
      {
        courseInfo += ":" + GetCourseNameAt(id, i) + "-" + GetCourseGradeAt(id, i);
      }
      
      //it will skip the position or the course which will be deleted and print the next courses
      for (int i = (pos + 1); i < NumCourse(id); i++)
      {
        courseInfo += ":" + GetCourseNameAt(id, i) + "-" + GetCourseGradeAt(id, i);
      }
      
      status = db.ReplaceCourseInfo(id, courseInfo);
      //to determine if course has successfully been replaced
      if (status == true)
      {
        result = 1;
      }
      //id is not in the system
      else if (status == false)
      {
        result = 0;
      }
    }
    
    
    
    
    return result ;  // Return value needs to be changed
  }   
  
  /* Edit a course's grade in an account 
   * @param id       - The account ID
   * @param pos      - The pos where the course is stored in. 
   *                   First course is at pos 0, second is at pos 1, etc. 
   * @param newGrade - The new grade for the course
   * @return         - Returns 1, if successful deleted the course 
   *                   Returns 0, if ID is not in the system
   *                   Returns -1, if pos specified is beyond the number of courses
   *                            in the course.
   *                   Returns -2, is newGrade is not valid */   
  
  public int EditCourse( String id, int pos, int newGrade ) {
    int result = 0;
    boolean status = false;
    String courseInfo = "";
    //to check the position of the grades to be replaced is not exceed the limit of the number of the courses
    if(pos >NumCourse(id))
    {
      result =EDIT_INVALID_POS ;
    }
    //to check the grade is not exceed the range of the mathematical percentage standard 
    else if(newGrade>100)
    {
      result = EDIT_INVALID_GRADE ;
    }
    //if it has an eligible position and grade ,it will start to check whether the grade is replacable or not
    else
    {
      
      //1st for loop will print out to the position indicates to the position of the grade to be replaced
      for (int i = 0;i<pos;i++)
      {
        courseInfo += ":" + GetCourseNameAt(id, i) + "-" + GetCourseGradeAt(id,i);
      }
      //2nd for loop will replace the grade to where the position has been indicates
      for (int i = pos ;i <pos+1;i++)
      {
        courseInfo +=":"+GetCourseNameAt(id, i) + "-" + newGrade;
      }
      //3rd for loop will print out the rest of the courses and grades
      for(int i = pos+1;i<NumCourse(id);i++)
      {
        
        courseInfo +=":"+GetCourseNameAt(id, i) + "-" + GetCourseGradeAt(id,i);
      }
      status =db.ReplaceCourseInfo(id,courseInfo);
      
      //to determine if grade has beeen successfully been replaced
      if (status == true)
      {
        result = EDIT_COURSE_SUCCESS;
      }
      //to determine if grade has not successfully been replaced
      else if (status == false)
      {
        result = EDIT_COURSE_UNSUCCESS ;
      }
    }
    
    return result ;   // Return value needs to be changed
  }
}