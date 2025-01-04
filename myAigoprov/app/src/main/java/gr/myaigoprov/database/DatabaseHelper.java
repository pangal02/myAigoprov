package gr.myaigoprov.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import gr.myaigoprov.model.Animal;
import gr.myaigoprov.model.Gender;
import gr.myaigoprov.model.Goat;
import gr.myaigoprov.model.Sheep;

import android.content.ContentValues;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Όνομα της βάσης δεδομένων
    private static final String DATABASE_NAME = "animals.db";
    // Έκδοση της βάσης δεδομένων
    private static final int DATABASE_VERSION = 5;

    // Πίνακας για τα Goats
    public static final String TABLE_GOATS = "goats";
    // Πίνακας για τα Sheeps
    public static final String TABLE_SHEEPS = "sheeps";

    // Στήλες κοινές για Goats και Sheeps
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TAG_NUMBER = "tag_number";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_BIRTHDATE = "birthdate";
    public static final String COLUMN_YOUNG_MOM = "young_mom";

    // Σχέδιο δημιουργίας πίνακα για τα Goats
    private static final String CREATE_TABLE_GOATS = "CREATE TABLE " + TABLE_GOATS + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TAG_NUMBER + " INTEGER NOT NULL, "
            + COLUMN_GENDER + " VARCHAR, "
            + COLUMN_BIRTHDATE + " VARCHAR, "
            + COLUMN_YOUNG_MOM + " INTEGER);";

    // Σχέδιο δημιουργίας πίνακα για τα Sheeps
    private static final String CREATE_TABLE_SHEEPS = "CREATE TABLE " + TABLE_SHEEPS + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TAG_NUMBER + " INTEGER, "
            + COLUMN_GENDER + " VARCHAR, "
            + COLUMN_BIRTHDATE + " VARCHAR, "
            + COLUMN_YOUNG_MOM + " INTEGER);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Δημιουργία πινάκων
        db.execSQL(CREATE_TABLE_GOATS);
        db.execSQL(CREATE_TABLE_SHEEPS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Αν η βάση δεδομένων αναβαθμιστεί, θα διαγράψουμε τους υπάρχοντες πίνακες και θα τους δημιουργήσουμε ξανά
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GOATS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHEEPS);
        onCreate(db);
    }

    public long addAnimal(Animal animal){
        if(animal instanceof Goat){
            Goat goat = (Goat) animal;
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_TAG_NUMBER, goat.getTagNumber());
            if(goat.getGender() == Gender.MALE){
                values.put(COLUMN_GENDER, "ΤΡΑΓΟΣ");
            }
            else if(goat.getGender() == Gender.FEMALE){
                values.put(COLUMN_GENDER, "ΓΙΔΑ");
            }
            else if(goat.getGender() == Gender.OTHER){
                values.put(COLUMN_GENDER, "ΑΛΛΟ");
            }
            values.put(COLUMN_BIRTHDATE, goat.getBirthdate());
            if(animal.isYoungMom()){
                values.put(COLUMN_YOUNG_MOM, 1);
            }
            else{
                values.put(COLUMN_YOUNG_MOM, 0);
            }
            return db.insert(TABLE_GOATS, null, values);
        }
        else {
            Sheep sheep = (Sheep) animal;
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_TAG_NUMBER, sheep.getTagNumber());
            if(sheep.getGender() == Gender.MALE){
                values.put(COLUMN_GENDER, "ΚΡΙΑΡΙ");
            }
            else if(sheep.getGender() == Gender.FEMALE){
                values.put(COLUMN_GENDER, "ΠΡΟΒΑΤΙΝΑ");
            }
            else if(sheep.getGender() == Gender.OTHER){
                values.put(COLUMN_GENDER, "ΑΛΛΟ");
            }
            values.put(COLUMN_BIRTHDATE, sheep.getBirthdate());
            if(animal.isYoungMom()){
                values.put(COLUMN_YOUNG_MOM, 1);
            }
            else{
                values.put(COLUMN_YOUNG_MOM, 0);
            }
            return db.insert(TABLE_SHEEPS, null, values);

        }
    }

    public Cursor getAllGoats() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_GOATS, null);
    }

    public Cursor getAllSheeps() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_SHEEPS, null);
    }

    public void printDatabase(SQLiteDatabase db) {
        // Εκτύπωση δεδομένων από τον πίνακα Goats
        Cursor cursorGoats = db.rawQuery("SELECT * FROM goats", null);
        Log.d("DATABASE", "Printing Goats Table:");
        while (cursorGoats.moveToNext()) {
            int id = cursorGoats.getInt(cursorGoats.getColumnIndexOrThrow(COLUMN_ID));
            int tagNumber = cursorGoats.getInt(cursorGoats.getColumnIndexOrThrow(COLUMN_TAG_NUMBER));
            String gender = cursorGoats.getString(cursorGoats.getColumnIndexOrThrow(COLUMN_GENDER));
            String birthdate = cursorGoats.getString(cursorGoats.getColumnIndexOrThrow(COLUMN_BIRTHDATE));
            int youngMomNum = cursorGoats.getInt(cursorGoats.getColumnIndexOrThrow(COLUMN_YOUNG_MOM));
            Boolean youngMom = false;
            if(youngMomNum == 1){
                youngMom = true;
            }
            Log.d("DATABASE", "Goat - Id: " + id + ", Tag: " + tagNumber + ", Gender: " + gender + ", Birth Year: " + birthdate);
        }
        cursorGoats.close();

        // Εκτύπωση δεδομένων από τον πίνακα Sheeps
        Cursor cursorSheeps = db.rawQuery("SELECT * FROM sheeps", null);
        Log.d("DATABASE", "Printing Sheeps Table:");
        while (cursorSheeps.moveToNext()) {
            int id = cursorSheeps.getInt(cursorSheeps.getColumnIndexOrThrow(COLUMN_ID));
            int tagNumber = cursorSheeps.getInt(cursorSheeps.getColumnIndexOrThrow(COLUMN_TAG_NUMBER));
            String gender = cursorSheeps.getString(cursorSheeps.getColumnIndexOrThrow(COLUMN_GENDER));
            String birthdate = cursorSheeps.getString(cursorSheeps.getColumnIndexOrThrow(COLUMN_BIRTHDATE));
            int youngMomNum = cursorSheeps.getInt(cursorSheeps.getColumnIndexOrThrow(COLUMN_YOUNG_MOM));
            Boolean youngMom = false;
            if(youngMomNum == 1){
                youngMom = true;
            }
            Log.d("DATABASE", "Goat - Id: " + id +  ", Tag: " + tagNumber + ", Gender: " + gender + ", Birth Year: " + birthdate);
        }
        cursorSheeps.close();
    }

}
