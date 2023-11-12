//package com.example.myapplication.DB;
//
//import static android.content.ContentValues.TAG;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.util.Log;
//
//import com.example.myapplication.MainActivity;
//import com.example.myapplication.leavedata;
//import java.sql.Array;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//
//public class DatabaseHelper extends SQLiteOpenHelper {
//    Connection conn;
//private static final String dbname="EmployeeData8.db";
//
//    public DatabaseHelper(Context context)
//    {
//        super(context, dbname, null, 1);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db)
//    {
//        String qry="create table tbl_employee (id integer primary key, name text, salary text, fa text, otb text, age text, tr text, epfRate text, password text,position text,manager text)";
//        db.execSQL(qry);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
//    {
//        db.execSQL("DROP TABLE IF EXISTS tbl_employee");
//        onCreate(db);
//    }
//
//    public  String addRecord(String p1, String p2, String p3,String p4,String p5,String p6,String p7,String p8,String p9,String p10,String p11)
//    {
//        SQLiteDatabase db=this.getWritableDatabase();
//
//        ContentValues cv=new ContentValues();
//        cv.put("id",p1);
//        cv.put("name",p2);
//        cv.put("salary",p3);
//        cv.put("fa",p4);
//        cv.put("otb",p5);
//        cv.put("age",p6);
//        cv.put("tr",p7);
//        cv.put("epfRate",p8);
//        cv.put("password",p9);
//        cv.put("position",p10);
//        cv.put("manager",p11);
//
//        long res=db.insert("tbl_employee",null,cv);
//
//        if(res==-1)
//            return "Failed";
//        else
//            return "Successfully inserted";
//    }
//    public int deleteData(){
//        SQLiteDatabase db=this.getWritableDatabase();
//        return db.delete("tbl_employee",null,null);
//    }
//    public ArrayList<leavedata> viewall() {
//        ArrayList<leavedata> leavedata2 = new ArrayList<>();
////        SQLiteDatabase db=this.getReadableDatabase();
////        Cursor cur= db.rawQuery("SELECT * FROM tbl_employee" ,null);
////        if (cur.moveToFirst()) {
////
////                leavedata2.add(leavedata(cur.getString(1),cur.getString(2),cur.getString(3));
////
////        }
//        MainActivity connsql = new MainActivity();
//        conn = connsql.connClass();
//        if (conn != null) {
//            String sQuery = "Select * from tblEmLeave where emID='1' and emStatus='Pending Request'";
//            Statement st = null;
//            try {
//                st = conn.createStatement();
//                ResultSet rs = st.executeQuery(sQuery);
//                while (rs.next()) {
//                    leavedata2.add(new leavedata(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(10),rs.getString(11),rs.getString(12),rs.getString(13),rs.getString(14)));
//                }
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return leavedata2;
//    }
//
//    public ArrayList<employeedata> viewallPCB(int id) {
//        ArrayList<employeedata> employeedata2 = new ArrayList<>();
////        SQLiteDatabase db=this.getReadableDatabase();
////        Cursor cur= db.rawQuery("SELECT * FROM tbl_employee" ,null);
////        if (cur.moveToFirst()) {
////
////                leavedata2.add(leavedata(cur.getString(1),cur.getString(2),cur.getString(3));
////
////        }
//        MainActivity connsql = new MainActivity();
//        conn = connsql.connClass();
//        if (conn != null) {
//            String sQuery = "Select * from tblEmployeeRecord2 where emID='" + id + "'";
//            Statement st = null;
//            try {
//                st = conn.createStatement();
//                ResultSet rs = st.executeQuery(sQuery);
//                while (rs.next()) {
//                    employeedata2.add(new employeedata(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12)));
//                Log.d(TAG,"dbname=" + rs.getString(2));
//                }
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return employeedata2;
//    }
//public ArrayList<String> emInfo(){
//    ArrayList<String>emInfo2=new ArrayList<>();
//    MainActivity connsql = new MainActivity();
//    conn = connsql.connClass();
//    if (conn != null) {
//        String sQuery = "Select * from tblEmLeave";
//        Statement st = null;
//        try {
//            st = conn.createStatement();
//            ResultSet rs = st.executeQuery(sQuery);
//            while (rs.next()) {
//                emInfo2.add(rs.getString(1)+","+rs.getString(2));
//            }
//Log.d(TAG,"emInfo=" + emInfo2);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    return emInfo2;
//}
//    public Cursor viewall2(){
//        SQLiteDatabase db=this.getReadableDatabase();
//        Cursor cur= db.rawQuery("SELECT * FROM tbl_employee" ,null);
//        return cur;
//    }
//    public Cursor viewid(String id){
//        SQLiteDatabase db=this.getReadableDatabase();
//        Cursor cur= db.rawQuery("SELECT * FROM tblemployee where id=" + id ,null);
//        return cur;
//    }
//    public boolean updateRecord(int name, String leavestate, String leavestateLatest) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
////        values.put("leavestate", leavestate);
//        values.put("leavestatus", leavestateLatest);
//
//        int rowsAffected = db.update("tbl_employee", values, "id=?", new String[]{String.valueOf(name)});
//
//        db.close();
//
//        return (rowsAffected > 0);
//    }
//}
