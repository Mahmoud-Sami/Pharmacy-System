package pharmacy.ExtraClasses;
import java.time.LocalDate;
import java.time.LocalTime;
public class DateTime {
    //--------(Attributes)----------//
    private int seconds, minutes, hours;
    private int day, month, year;
    //---------(Methods)-------------//
    private String format(int value) {
        if (value < 10 && value > 0)
            return "0" + value;
        else
            return Integer.toString(value);
    }
    //Constructor Methods
    public DateTime() {
        this.setTime(
            LocalTime.now().getSecond(), LocalTime.now().getMinute(), LocalTime.now().getHour()
        );
        this.setDate(
            LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear()
        );
    }
    public DateTime(int s, int m, int h, int D, int M, int Y) {
        this.setTime(s, m, h);
        this.setDate(D, M, Y);
    }
    //Mutator Methods
    public void setTime(int s, int m, int h) {
        if (s > 59 || s < 0) throw new ArithmeticException("Invalid Second input");
        if (m > 59 || m < 0) throw new ArithmeticException("Invalid Minute input");
        if (h > 23 || h < 0) throw new ArithmeticException("Invalid Hours input");
        this.seconds = s;
        this.minutes = m;
        this.hours = h;
    }
    public void setDate(int D, int M, int Y) {
        if (D > 31 || D < 1) throw new ArithmeticException("Invalid Day input");
        if (M > 12 || M < 1) throw new ArithmeticException("Invalid Month input");
        if (Y < 0) throw new ArithmeticException("Invalid Year input");
        this.day = D;
        this.month = M;
        this.year = Y;
    }
    //Accessor Methods
    public int getSecond() {
        return this.seconds;
    }
    public int getMinute() {
        return this.minutes;
    }
    public int getHour() {
        return this.hours;
    }
    public int getDay() {
        return this.day;
    }
    public int getMonth() {
        return this.month;
    }
    public int getYear() {
        return this.year;
    }
    public String getDate() {
        return format(getDay()) + "/" + format(getMonth()) + "/" + getYear();
    }
    public String getTime() {
        return format(getSecond()) + ":" + format(getMinute()) + ":" + format(getHour());
    }
}