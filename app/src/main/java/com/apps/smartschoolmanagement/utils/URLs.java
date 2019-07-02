package com.apps.smartschoolmanagement.utils;

public class URLs {


    // new url start
    public  static  String getStd = "http://quickedu.co.in/finstdBySchoolId?school="; //1
    public static String getDiv = "http://quickedu.co.in/findByStd?std="; //1&school=1
    public static String getStudentByStdAndDiv ="http://quickedu.co.in/findByStdAndDivAndSchool?std="; //1&div=1&School=1
    public static String addAttendce ="http://quickedu.co.in/addAttendance";
    public static String getStudent = "http://quickedu.co.in/findByStudentid?id="; //1
    public static String getSubject = "http://quickedu.co.in/getSubjectApi?stdid="; //1
    public static String getPrentByStudent = "http://quickedu.co.in/findByStudents?id=";


    // end




    public static String acceptleave = "http://findlogics.in/school-master/index.php?/api/leaveRequest/acceptLeave";
    public static String album_create = "http://findlogics.in/school-master/index.php?/api/gallery/create";
    public static String all_student_codes = "http://findlogics.in/school-master/index.php?/api/selectStudent";
    public static String all_teacher_codes = "http://findlogics.in/school-master/index.php?/api/selectTeacher";
    public static String allleaves = "http://findlogics.in/school-master/index.php?/api/leaveRequest/AllLeaves";
    public static String allstudentslist = "http://findlogics.in/school-master/index.php?/api/selectStudentFullData";
    public static String allteacherslist = "http://findlogics.in/school-master/index.php?/api/selectTeacherFullData";
    public static String applyleave = "http://findlogics.in/school-master/index.php?/api/leaveRequest/create";
    public static String appointment = "http://findlogics.in/school-master/index.php?/api/appointment";
    public static String appointmentsList = "http://findlogics.in/school-master/index.php?/api/appointmentListForAdmin";
    public static String assignment_post = "http://findlogics.in/school-master/index.php?api/Assignment/create";
    public static String assignmentsListStudent = "http://findlogics.in/school-master/index.php?/api/Assignment/assignmentListForStudent";
    public static String assignmentsListTeacher = "http://findlogics.in/school-master/index.php?/api/Assignment/assignmentTeacherList";
    public static String assignmentsUpdate = "http://findlogics.in/school-master/index.php?/api/Assignment/do_update";
    public static String assignments_List_bydate = "http://findlogics.in/school-master/index.php?/api/Assignment/assignmentListByDate";
    public static String assignments_status_completed = "http://findlogics.in/school-master/index.php?/api/Assignment/compleated";
    public static String assignments_status_completedList = "http://findlogics.in/school-master/index.php?/api/Assignment/compleatedList";
    public static String assignments_status_pending = "http://findlogics.in/school-master/index.php?/api/Assignment/pending";
    public static String assignments_status_pendingList = "http://findlogics.in/school-master/index.php?/api/Assignment/pendingList";
    public static String assignments_status_update = "http://findlogics.in/school-master/index.php?/api/Assignment/status";
    public static String attendance = "http://findlogics.in/school-master/index.php?/api/attendance";
    public static String books = "http://findlogics.in/school-master/index.php?api/mybooks";
    public static String busdetails = "http://findlogics.in/school-master/index.php?api/studentBusDetails";
    public static String buses = "http://findlogics.in/school-master/index.php?api/transport_buses";
    public static String class_codes = "http://findlogics.in/school-master/index.php?/api/selectClass";
    public static String editprofile = "http://findlogics.in/school-master/index.php?/api/manage_profile/update_profile_info";
    public static String examSchedule = "http://findlogics.in/school-master/index.php?/api/examschedule";
    public static String exam_codes = "http://findlogics.in/school-master/index.php?/api/selectExam";
    public static String feedetails = "http://findlogics.in/school-master/index.php?api/invoice";
    public static String gallery = "http://findlogics.in/school-master/index.php?/api/gallery/galleryList";
    public static String healthStatus = "http://findlogics.in/school-master/index.php?/api/health/";
    public static String login = "http://findlogics.in/school-master/index.php?/api/loginMe";
    public static String marks = "http://findlogics.in/school-master/index.php?/api/marksforstudent";
    public static String notifications = "http://findlogics.in/school-master/index.php?/api/noticeboard";
    public static String online_material = "http://findlogics.in/school-master/index.php?/api/onlinematerials";
    public static String pendingleaves_student = "http://findlogics.in/school-master/index.php?/api/leaveRequest/studentPendingLeaves";
    public static String pendingleaves_teacher = "http://findlogics.in/school-master/index.php?/api/leaveRequest/AllTeachersPendingLeaves";
    public static String post_online_material = "http://findlogics.in/school-master/index.php?/api/postonlinematerial";
    public static String principalstatement_post = "http://findlogics.in/school-master/index.php?/api/principlestatement/create";
    public static String principalstatement_view = "http://findlogics.in/school-master/index.php?/api/principlestatement/List";
    public static String rejectleave = "http://findlogics.in/school-master/index.php?/api/leaveRequest/rejectLeave";
    public static String remarks = "http://findlogics.in/school-master/index.php?/api/remarks";
    public static String remarks_post = "http://findlogics.in/school-master/index.php?api/addremarks/create";
    public static String resetpassword = "http://findlogics.in/school-master/index.php?/api/forgotPwd";
    public static String sendotp = "http://findlogics.in/school-master/index.php?/api/sendotp";
    public static String staffpayrolls = "http://findlogics.in/school-master/index.php?/api/staffPayrole";
    public static String stdentleaves = "http://findlogics.in/school-master/index.php?/api/leaveRequest/StudentLeaves";
    public static String student_codes = "http://findlogics.in/school-master/index.php?/api/selectStudentByClass";
    public static String subject_codes = "http://findlogics.in/school-master/index.php?/api/selectSubject";
    public static String teacher_codes = "http://findlogics.in/school-master/index.php?/api/selectTeacherByClass";
    public static String teacherleaves = "http://findlogics.in/school-master/index.php?/api/leaveRequest/TeachersLeaves";
    public static String userPicUpdate = "http://findlogics.in/school-master/index.php?/api/gallery/profileimgupdate";
    public static String userprofile = "http://findlogics.in/school-master/index.php?/api/userprofile";
}
