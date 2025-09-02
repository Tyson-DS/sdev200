import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MyDate{
    private int year;
    private int month;
    private int day;

    public MyDate(){
        LocalDate currentDate = LocalDate.now();
        this.year = currentDate.getYear();
        this.month = currentDate.getMonthValue() -1;
        this.day = currentDate.getDayOfMonth();

    }

    public MyDate(long elapsedTime){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(elapsedTime);
        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH);
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        

    }
    
    public MyDate(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;

    }

    public int getYear() {return year; }
    public int getMonth() {return month; }
    public int getDay() {return day; }


    public void setDate(long elapsedTime) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(elapsedTime);
        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH);
        this.day = calendar.get(Calendar.DAY_OF_MONTH);

    }
}
