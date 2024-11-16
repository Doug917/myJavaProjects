import math.computation.algebra.*;
import math.computation.geometry.*;
import java.util.*;
class PolyTest{


    public static void main(String[] args){

        //Do some polynomial operations.
        Polynomial p1 = new Polynomial(1.0,2.0);
        Polynomial p2 = new Polynomial(0.0,1.0);

        Polynomial p = Polynomial.Multiply(p1, p2);

        p1.Display();
        p2.Display();
        p.Display();

        p1.addTerm(2.0,5);
        p1.Add(p2);

        p1.Display();

        p1.Derivative();

        p1.Display();

        Polynomial c1 = new Polynomial(-1.0);
        Polynomial c2 = new Polynomial(0.0,3.0,0.0,-8.0);
        Polynomial c3 = new Polynomial(0.0,-5.0,7.0,0.0,-11.0);
        Polynomial c4 = new Polynomial(0.0,0.0,8.0,0.0,0.0,1.0);
        Polynomial c5 = new Polynomial(0.0,0.0,0.0,-3.0,0.0,-4.0);

        //Sort a few polynomials.
        Polynomial P[] = new Polynomial[5];
        P[0] = c1;
        P[1] = c2;
        P[2] = c3;
        P[3] = c4;
        P[4] = c5;

        Arrays.sort(P);

        for (Polynomial q:P){
            q.Display();
        }

        //Make a few shapes.
        Polygon2D g1 = new Polygon2D(new double[][] {{0.0,0.0},{1.0,0.0},{1.0,1.0},{0.0,1.0}});
        System.out.print("Num vertices:");
        System.out.println(g1.numVertices);
        System.out.print("Perimeter:");
        System.out.println(g1.Perimeter());
        System.out.print("Area:");
        System.out.println(g1.Area());


        
    }
}