package gr.myaigoprov.model;

import androidx.room.TypeConverter;


public class FarmerConverter {
    @TypeConverter
    public static String fromFarmer(Farmer farmer) {
        return new String(farmer.getFarmerCode());
    }

}
