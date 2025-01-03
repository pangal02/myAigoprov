package gr.myaigoprov.model;

public class Calendar {
    int day;
    int month;
    int year;

    public Calendar(String date){
        /*  dd/mm/yyyy   */
            String[] fields = date.split("/");
            int d = Integer.parseInt(fields[0].trim());
            int m = Integer.parseInt(fields[1].trim());
            int y = Integer.parseInt(fields[2].trim());

            if(y > 0){
                this.year = y;
            }
            else{
                throw new IllegalArgumentException("ΔΕΝ ΜΠΟΡΕΙΤΕ ΝΑ ΒΑΛΕΤΕ ΑΡΝΗΤΙΚΗ ΧΡΟΝΟΛΟΓΙΑ.");
            }
            if( (m==1) || (m==3) || (m==5) || (m==7) || (m==8) || (m==10) || (m==12) ){
                month = m;
                if( (d>=1) && (d<=31)){
                    day = d;
                }
                else{
                    throw new IllegalArgumentException("ΔΕΝ ΜΠΟΡΕΙ Ο " + month + "ος ΜΗΝΑΣ ΝΑ ΕΧΕΙ ΛΙΓΟΤΕΡΕΣ ΑΠΟ 1 'Η ΠΑΝΩ ΑΠΟ 31 ΗΜΕΡΕΣ.");
                }
            } else if( (m==4) || (m==6) || (m==9) || (m==11) ){
                month = m;
                if( (d>=1) && (d<=30)){
                    day = d;
                }
                else{
                    throw new IllegalArgumentException("ΔΕΝ ΜΠΟΡΕΙ Ο " + month + "ος ΜΗΝΑΣ ΝΑ ΕΧΕΙ ΛΙΓΟΤΕΡΕΣ ΑΠΟ 1 'Η ΠΑΝΩ ΑΠΟ 30 ΗΜΕΡΕΣ.");
                }
            }
            else if(m == 2){
                if(year%2 == 0){
                    month = m;
                    if( (d>=1) && (d<=29)){
                        day = d;
                    }
                    else{
                        throw new IllegalArgumentException("ΔΕΝ ΜΠΟΡΕΙ Ο " + month + "ος ΜΗΝΑΣ ΝΑ ΕΧΕΙ ΛΙΓΟΤΕΡΕΣ ΑΠΟ 1 'Η ΠΑΝΩ ΑΠΟ 29 ΗΜΕΡΕΣ.");
                    }
                }
                else{
                    if( (d>=1) && (d<=29)){
                        day = d;
                    }
                    else{
                        throw new IllegalArgumentException("ΔΕΝ ΜΠΟΡΕΙ Ο " + month + "ος ΜΗΝΑΣ ΝΑ ΕΧΕΙ ΛΙΓΟΤΕΡΕΣ ΑΠΟ 1 'Η ΠΑΝΩ ΑΠΟ 28 ΗΜΕΡΕΣ.");
                    }
                }
            }
    }

    public void setDay(String sday){
        int d = Integer.parseInt(sday.trim());
//        if((d < 1) || (d > 31))
    }

    @Override
    public String toString(){
        String date = String.format("%d/%d/%d", day, month, year);
        return date;
    }
}
