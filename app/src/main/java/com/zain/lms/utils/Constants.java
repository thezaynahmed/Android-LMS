package com.zain.lms.utils;

import java.util.regex.Pattern;

public class Constants {
    public static final String ASSIGNMENTS_DATA_URL = "https://zainahmedsproject.tk/assignments_data.php";
    public static final String REGISTER_USER = "https://zainahmedsproject.tk/Register.php";
    public static final String SIGN_IN = "https://zainahmedsproject.tk/getdata2.php";
    public static final String GET_APPROVAL = "https://zainahmedsproject.tk/getApproval.php";
    public static final String SET_APPROVAL = "https://zainahmedsproject.tk/setApproval.php";
    public static final String STUDENT_ATTENDANCE = "https://zainahmedsproject.tk/attendance.php";
    public static final String STUDENT_ASSIGNMENTS = "https://zainahmedsproject.tk/getAllAssignments.php";
    public static final String BATCHES = "https://zainahmedsproject.tk/batches.php";
    public static final String GET_ALL_TEACHERS = "https://zainahmedsproject.tk/getAllTeachers.php";
    public static final String GET_ALL_COURSES = "https://zainahmedsproject.tk/getCourse.php";
    public static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no whitespace allowed in the entire string
                    ".{8,}" +               //at least 8 characters
                    "$");
}
