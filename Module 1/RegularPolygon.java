public class RegularPolygon {
    private int n = 3;
    private double side = 1.0;
    private double x = 0.0;
    private double y = 0.0;
    
    public RegularPolygon(int n, double side, double x, double y)
    {
        this.n = n;
        this.side = side;
        this.x = x;
        this.y = y;
        
    }
 
    public double getPerimeter()
    {
        return side * n;
    }

    public int getN() {
        return n;
    }

    public double getSide() {
        return side;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setN(int n) {
        this.n = n;
    }

    public void setSide(double side) {
        this.side = side;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
    public double getArea()

    {
        double Area = (n * (side*side))/(4*Math.tan(Math.PI/n));
        return Area;
    }

    public static void main(String[] args)
    {
        RegularPolygon NPolygon = new RegularPolygon(10,4, 5.6, 7.8);
        System.out.println("perimeter: "+ NPolygon.getPerimeter());
        System.out.println("area: "+ NPolygon.getArea());
    }
}