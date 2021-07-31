package algonquin.cst2335.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "OCTranspo.db";
    private static final int DATABASE_VERSION = 21;

    private static final String TABLE_NAME2 = "specificBusInfo";

    private static final String TABLE_NAME = "mybusinfo";
    private static final String COLUMN_DESTINATION = "destination";
    private static final String COLUMN_TRIP_DESTINATION = "tripdestination";
    private static final String UNIQUE_NUM = "id";

    private static final String COLUMN_LONGITUDE = "longitude";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_GPS_SPEED = "gpsspeed";
    private static final String COLUMN_START_TIME = "starttime";
    private static final String COLUMN_ADJUSTED_SCHEDULE = "adjustedschedule";
    private static final String COLUMN_ROUTE = "routeNo";
    private static final String COLUMN_DIRECTIONID = "directionId";






    public MyDatabaseHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query2 =
                "CREATE TABLE " + TABLE_NAME2 +
                        " (" + UNIQUE_NUM + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TRIP_DESTINATION + " TEXT, " +
                        COLUMN_LATITUDE + " TEXT, " +
                        COLUMN_LONGITUDE + " TEXT," +
                        COLUMN_GPS_SPEED + " TEXT, " +
                        COLUMN_START_TIME + " TEXT, " +
                        COLUMN_ADJUSTED_SCHEDULE + " TEXT);";



        String query =
                "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_DIRECTIONID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_ROUTE + " TEXT, " +
//                        COLUMN_LATITUDE + " TEXT, " +
//                        COLUMN_LONGITUDE + " TEXT," +
//                        COLUMN_GPS_SPEED + " TEXT, " +
//                        COLUMN_START_TIME + " TEXT, " +
//                        COLUMN_ADJUSTED_SCHEDULE + " TEXT, " +
                        COLUMN_DESTINATION + " TEXT);";
        db.execSQL(query);
//        db.execSQL(query2);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

//
//    void addBusRouteNo(String routeNo){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        cv.put(COLUMN_ROUTE, routeNo);
//        long result = db.insert(TABLE_NAME, null, cv);
//        if(result == -1 ){
////            Toast.makeText(context,"FAILURE DATABASE",Toast.LENGTH_LONG).show();
//        }else{
////            Toast.makeText(context,"Added to database successfully",Toast.LENGTH_LONG).show();
//        }
//
//    }
//
//    void addBusDestination(String destination){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        cv.put(COLUMN_DESTINATION, destination);
//        long result = db.insert(TABLE_NAME, null, cv);
//        if(result == -1 ){
////            Toast.makeText(context,"FAILURE DATABASE",Toast.LENGTH_LONG).show();
//        }else{
////            Toast.makeText(context,"Added to database successfully",Toast.LENGTH_LONG).show();
//        }
//
//    }
//    void addBusLatitude(String Latitude){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        cv.put(COLUMN_LATITUDE, Latitude);
//        long result = db.insert(TABLE_NAME, null, cv);
//        if(result == -1 ){
////            Toast.makeText(context,"FAILURE DATABASE",Toast.LENGTH_LONG).show();
//        }else{
////            Toast.makeText(context,"Added to database successfully",Toast.LENGTH_LONG).show();
//        }
//
//    }
//    void addBusLongitude(String longitude){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        cv.put(COLUMN_LONGITUDE, longitude);
//        long result = db.insert(TABLE_NAME, null, cv);
//        if(result == -1 ){
////            Toast.makeText(context,"FAILURE DATABASE",Toast.LENGTH_LONG).show();
//        }else{
////            Toast.makeText(context,"Added to database successfully",Toast.LENGTH_LONG).show();
//        }
//
//    }
//    void addBusGpsSpeed(String gpsSpeed){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        cv.put(COLUMN_GPS_SPEED, gpsSpeed);
//        long result = db.insert(TABLE_NAME, null, cv);
//        if(result == -1 ){
////            Toast.makeText(context,"FAILURE DATABASE",Toast.LENGTH_LONG).show();
//        }else{
////            Toast.makeText(context,"Added to database successfully",Toast.LENGTH_LONG).show();
//        }
//
//    }
//    void addBusStartTime(String startTime){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        cv.put(COLUMN_START_TIME, startTime);
//        long result = db.insert(TABLE_NAME, null, cv);
//        if(result == -1 ){
////            Toast.makeText(context,"FAILURE DATABASE",Toast.LENGTH_LONG).show();
//        }else{
////            Toast.makeText(context,"Added to database successfully",Toast.LENGTH_LONG).show();
//        }
//
//    }
//    void addBusadjusted(String adjustedTime){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        cv.put(COLUMN_ADJUSTED_SCHEDULE, adjustedTime);
//        long result = db.insert(TABLE_NAME, null, cv);
//        if(result == -1 ){
////            Toast.makeText(context,"FAILURE DATABASE",Toast.LENGTH_LONG).show();
//        }else{
////            Toast.makeText(context,"Added to database successfully",Toast.LENGTH_LONG).show();
//        }
//
//    }
//
//

//String latitude, String longitude, String gpsSpeed, String startTime, String adjustedSchedule
    void addBusInfo(int directionID, String routeNo, String destination){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_DIRECTIONID, directionID);
        cv.put(COLUMN_ROUTE, routeNo);
        cv.put(COLUMN_DESTINATION, destination);
//        cv.put(COLUMN_LATITUDE, latitude);
//        cv.put(COLUMN_LONGITUDE, longitude);
//        cv.put(COLUMN_GPS_SPEED, gpsSpeed);
//        cv.put(COLUMN_START_TIME, startTime);
//        cv.put(COLUMN_ADJUSTED_SCHEDULE, adjustedSchedule);

        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1 ){
//            Toast.makeText(context,"FAILURE DATABASE",Toast.LENGTH_LONG).show();
        }else{
//            Toast.makeText(context,"Added to database successfully",Toast.LENGTH_LONG).show();
        }




    }


    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }
}
