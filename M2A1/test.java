
public class test {    
    
    public static void main(String[] args) {
        String[] monthdate ={"January", "February", "March", "April", "May", "June", "July", "August", 
    "September", "October", "November", "December"}; 
        MyDate d1 = new MyDate();
        MyDate d2 = new MyDate(34355555133101L);

        System.out.println(monthdate[d1.getMonth()] + " " + d1.getDay() + ", " + d1.getYear());
         System.out.println(monthdate[d2.getMonth()] + " " + d2.getDay() + ", " + d2.getYear());
        }
    }
