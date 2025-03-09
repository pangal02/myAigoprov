package gr.myaigoprov.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import gr.myaigoprov.model.Animal;
import gr.myaigoprov.model.DeletedAnimal;
import gr.myaigoprov.model.Farmer;
import gr.myaigoprov.model.Gender;
import gr.myaigoprov.model.Goat;
import gr.myaigoprov.model.Milk;
import gr.myaigoprov.model.Sheep;

import android.content.ContentValues;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {


    // Όνομα της βάσης δεδομένων
    private static final String DATABASE_NAME = "users.db";
    // Έκδοση της βάσης δεδομένων
    private static final int DATABASE_VERSION = 14;

    // Πίνακας για τον αγρότη
    public static final String TABLE_FARMER = "Farmers";

    // Πίνακας για τα Goats
    public static final String TABLE_GOATS = "Goats";
    // Πίνακας για τα Sheeps
    public static final String TABLE_SHEEPS = "Sheeps";

    // Στήλες κοινές για Goats και Sheeps
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TAG_NUMBER = "tag_number";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_BIRTHDATE = "birthdate";
    public static final String COLUMN_YOUNG_MOM = "young_mom";

    //Πίνακας για τα ζωα που εχουν φυγει
    public static final String TABLE_REMOVED = "Removed";
    public static final String COLUMN_ANIMAL_TYPE = "animal_type";
    public static final String COLUMN_EXIT_TYPE = "exit_type";

    // Πίνακας Κατσικίσιου Γάλακτος
    public static final String TABLE_GOAT_MILK = "Goat_Milk";

    // Πίνακας Πρόβιου Γάλακτος
    public static final String TABLE_SHEEP_MILK = "Sheep_Milk";

    // Στήλες κοινές για Γάλα
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_COUNT_ANIMALS_MILK = "count_animals";





    // Σχέδιο δημιουργίας πίνακα για τα Goats
    private static final String CREATE_TABLE_GOATS = "CREATE TABLE " + TABLE_GOATS + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TAG_NUMBER + " INTEGER NOT NULL, "
            + COLUMN_GENDER + " VARCHAR NOT NULL, "
            + COLUMN_BIRTHDATE + " VARCHAR NOT NULL, "
            + COLUMN_YOUNG_MOM + " INTEGER);";

    // Σχέδιο δημιουργίας πίνακα για τα Sheeps
    private static final String CREATE_TABLE_SHEEPS = "CREATE TABLE " + TABLE_SHEEPS + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TAG_NUMBER + " INTEGER NOT NULL, "
            + COLUMN_GENDER + " VARCHAR NOT NULL, "
            + COLUMN_BIRTHDATE + " VARCHAR NOT NULL, "
            + COLUMN_YOUNG_MOM + " INTEGER);";

    // Σχέδιο δημιουργίας πίνακα για τα ζώα που εχουν φυγει
    private static final String CREATE_TABLE_REMOVED = "CREATE TABLE " + TABLE_REMOVED + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_ANIMAL_TYPE + " VARCHAR NOT NULL, "
            + COLUMN_TAG_NUMBER + " INTEGER NOT NULL, "
            + COLUMN_GENDER + " VARCHAR NOT NULL, "
            + COLUMN_BIRTHDATE + " VARCHAR NOT NULL, "
            + COLUMN_YOUNG_MOM + " INTEGER, "
            + COLUMN_EXIT_TYPE + " VARCHAR);";

    // Σχέδιο δημιουργίας πίνακα για τo κατσικίσιο γάλα
    private static final String CREATE_TABLE_GOAT_MILK = "CREATE TABLE " + TABLE_GOAT_MILK + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_DATE + " VARCHAR NOT NULL, "
            + COLUMN_QUANTITY + " REAL NOT NULL, "
            + COLUMN_COUNT_ANIMALS_MILK + " INTEGER NOT NULL);";

    // Σχέδιο δημιουργίας πίνακα για τo πρόβειο γάλα
    private static final String CREATE_TABLE_SHEEP_MILK = "CREATE TABLE " + TABLE_SHEEP_MILK + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_DATE + " VARCHAR NOT NULL, "
            + COLUMN_QUANTITY + " REAL NOT NULL, "
            + COLUMN_COUNT_ANIMALS_MILK + " INTEGER NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_GOATS);
        db.execSQL(CREATE_TABLE_SHEEPS);
        db.execSQL(CREATE_TABLE_REMOVED);
        db.execSQL(CREATE_TABLE_GOAT_MILK);
        db.execSQL(CREATE_TABLE_SHEEP_MILK);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Αν η βάση δεδομένων αναβαθμιστεί, θα διαγράψουμε τους υπάρχοντες πίνακες και θα τους δημιουργήσουμε ξανά
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GOATS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHEEPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMOVED);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GOAT_MILK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHEEP_MILK);
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

    public long removeAnimal(Animal animal) {
        if (animal instanceof Goat) {
            Goat goat = (Goat) animal;
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, goat.getId());
            values.put(COLUMN_TAG_NUMBER, goat.getTagNumber());
//            values.put(COLUMN_GENDER, goat.getGender());
            values.put(COLUMN_BIRTHDATE, goat.getBirthdate());

            return db.insert(TABLE_REMOVED, null, values);
        }

        /*
        " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_ANIMAL_TYPE + " VARCHAR NOT NULL, "
            + COLUMN_TAG_NUMBER + " INTEGER NOT NULL, "
            + COLUMN_GENDER + " VARCHAR NOT NULL, "
            + COLUMN_BIRTHDATE + " VARCHAR NOT NULL, "
            + COLUMN_YOUNG_MOM + " INTEGER, "
            + COLUMN_EXIT_TYPE + " VARCHAR);";
         */
        return -1;
    }

    public long addGoatMilk(Milk milk, int count){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUANTITY, milk.getQuantity());
        values.put(COLUMN_DATE, milk.getDate());
        values.put(COLUMN_COUNT_ANIMALS_MILK, count);
        return db.insert(TABLE_GOAT_MILK, null, values);
    }

    public long addSheepMilk(Milk milk, int count){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUANTITY, milk.getQuantity());
        values.put(COLUMN_DATE, milk.getDate());
        values.put(COLUMN_COUNT_ANIMALS_MILK, count);
        return db.insert(TABLE_SHEEP_MILK, null, values);
    }

    public boolean deleteDatabase(Context context){
        return context.deleteDatabase(DATABASE_NAME);
    }
    public Cursor getAllGoats() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_GOATS, null);
    }

    public int getNumberOfGoats(){
        Cursor cursor = getAllGoats();
        int count = 0;
        if(cursor != null) {
            while (cursor.moveToNext()) {
                count++;
            }
            cursor.close();
        }
        return count;
    }

    public Cursor getAllSheeps() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_SHEEPS, null);
    }

    public Cursor getBornGoats(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_GOATS + " WHERE " + COLUMN_TAG_NUMBER + " = -1", null);
    }

    public Cursor getBornSheeps(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_SHEEPS + " WHERE " + COLUMN_TAG_NUMBER + " = -1", null);
    }


    public int getCountBornGoats(){
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  getBornGoats();

        if(cursor != null){
            while (cursor.moveToNext()){
                count++;
            }
            cursor.close();
        }

        db.close();
        return count;
    }

    public int getCountBornSheeps(){
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  getBornSheeps();

        if(cursor != null){
            while (cursor.moveToNext()){
                count++;
            }
            cursor.close();
        }
        db.close();
        return count;
    }



    public Cursor getGoatMilkOrderedByLatest() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_GOAT_MILK + " ORDER BY " + COLUMN_DATE + " DESC", null);
    }

    public Cursor getSheepMilkOrderedByLatest() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_SHEEP_MILK + " ORDER BY " + COLUMN_ID + " DESC", null);
    }


    public long setAsRemoved(DeletedAnimal animal, String exitType){
        long result = -2; // Return id of the row, if return -1 animal has not inserted

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, animal.getAnimal().getId());
            values.put(COLUMN_TAG_NUMBER, animal.getAnimal().getTagNumber());
            values.put(COLUMN_ANIMAL_TYPE, animal.getAnimalType());
            if(animal.getAnimal().getGender() == Gender.MALE){
                values.put(COLUMN_GENDER, "ΤΡΑΓΟΣ");
            }
            else if(animal.getAnimal().getGender() == Gender.FEMALE){
                values.put(COLUMN_GENDER, "ΓΙΔΑ");
            }
            else if(animal.getAnimal().getGender() == Gender.OTHER){
                values.put(COLUMN_GENDER, "ΑΛΛΟ");
            }
            values.put(COLUMN_BIRTHDATE, animal.getAnimal().getBirthdate());
            if(animal.getAnimal().isYoungMom()){
                values.put(COLUMN_YOUNG_MOM, 1);
            }
            else{
                values.put(COLUMN_YOUNG_MOM, 0);
            }
            values.put(COLUMN_EXIT_TYPE, exitType);
            result =  db.insert(TABLE_REMOVED, null, values);

        return result;
    }


    public int deleteAnimal(Animal animal){
        // Επιστρέφει ο πλήθος των γραμμών που διαγράφηκαν από τον πίνακα.
        int rowsDeleted = -1;
        SQLiteDatabase db = this.getReadableDatabase();
        if(animal instanceof Goat){
            Goat goat = (Goat) animal;
            int id = goat.getId();
            String deleteQuery = "DELETE FROM " + TABLE_GOATS + " WHERE " + COLUMN_ID + " = " + id;
            rowsDeleted = db.delete(TABLE_GOATS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        }
        else if(animal instanceof Sheep){
            Sheep sheep = (Sheep) animal;
            int id = sheep.getId();
            String deleteQuery = "DELETE FROM " + TABLE_SHEEPS + " WHERE " + COLUMN_ID + " = " + id;
            rowsDeleted = db.delete(TABLE_SHEEPS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        }
        return rowsDeleted;
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
