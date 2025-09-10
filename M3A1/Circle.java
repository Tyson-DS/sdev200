public class Circle extends GeometricObject implements Comparable<Circle>{
    private double radius;
    public Circle(){

    }

    public Circle(double radius){
        this.radius = radius;
    }
    
    public void setRadius(double radius){
        this.radius = radius;
    }

    public double getRadius(){
        return radius;
    }

    public double getArea(){
        return Math.PI * radius * radius;
    }

    
    public double getPerimeter(){
        return 2*Math.PI*radius;
    }

    

    public double getDiameter(){
        return 2 * radius;
    }

    @Override
    public boolean equals(Object e){
        if (e instanceof Circle ){
            Circle other = (Circle) e;
            return this.radius == other.radius;
        }
        return false; 
    }
    
    @Override
    public int compareTo(Circle circle){
        if (this.radius < circle.radius ) return -1;
        else if (this.radius > circle.radius) return 1;
        else return 0;
    }

    public void printCircle(){
        System.out.println("The cicle is created and the radius is " + radius);
    }
    
    
}
