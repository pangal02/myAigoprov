package gr.myaigoprov.model;

import androidx.room.TypeConverter;

public class GenderConverter {
    @TypeConverter
    public static String fromGender(Gender gender) {
        return gender.name();
    }

    @TypeConverter
    public static Gender toGender(String gender) {
        return Gender.valueOf(gender);
    }
}
