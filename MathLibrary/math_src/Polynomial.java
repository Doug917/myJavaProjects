package math.computation.algebra;

import java.util.*;

/** Create and operate on polynomials with double type coefficients; based on ArrayList.
 * @author Doug B
 * @version (1.0, 11-12-2024)
 */
public class Polynomial implements Comparable<Polynomial>{

    ArrayList<Double> coefficients = new ArrayList<Double>();
    int degree = 0;

    //Constructor.
    /**
     * 
     * @param c variable argument representing polynomial coefficients from lowest to highest degree.
     */
    public Polynomial(double ...c){

        for (double x:c){
            coefficients.add(x);
        }

        this.degree = coefficients.size()-1;
        this.Reduce();

    }

    /** Eliminate the last term if coefficient is equal to zero; purpose is to conserve space for objects.
     * 
     */
    public void Reduce(){

        //Allow for a zero polynomial by checking for size.
        if (coefficients.size() == 1){
            return;
        }

        if (coefficients.get(coefficients.size()-1) == 0){ //if highest-order tem is zero, take it out.
            coefficients.remove(coefficients.size()-1);
        }

        degree = coefficients.size()-1;

    }

    //Print polynomial to console.
    /**
     * Print the polynomial to the console.
     */
    public void Display(){

        StringBuilder s = new StringBuilder();
        String sp;
        int i = 0;
        for (double a:coefficients){
            s.append(a+"x^"+i+" ");
            i+=1;
        }
        sp = s.toString();
        System.out.println(sp.substring(0,sp.length()-1));
    }

    //Create additional term.
    /** Add a term to polynomial; cancellation will occur if term is negative of one already in instance.
     * 
     * @param coeff     the coefficient of added monomial
     * @param exponent  the exponent of added monomial
     */
    public void addTerm(double coeff, int exponent){

        while (exponent > degree){
            //add element to end of current coefficients list.
            coefficients.add(0.0);
            degree = coefficients.size()-1;
        }
        
        coefficients.set(exponent, coefficients.get(exponent)+coeff);
        //Because of how polynomials are added and multiplied,
        //we don't want to reduce after adding a term.

    }

    //Add p to this polynomial.
    /** Add polynomial p to this polynomial instance.
     * 
     * @param p     polynomial instance to be added.
     */
    public void Add(Polynomial p){

        while (this.degree < p.degree){
            this.addTerm(0.0, this.degree+1);
        }

        for(int i=0;i<=p.degree;i++){
            this.addTerm(p.coefficients.get(i), i);
        }

        //Reduce is only performed after addition of terms is done.
        this.Reduce();

    }

    //Differentiate this polynomial.
    /** Differentiate polynomial instance.
     * 
     */
    public void Derivative(){

        // update coefficients.
        coefficients.set(0, 0.0);
        for (int i=1;i<coefficients.size();i++){
            coefficients.set(i-1, coefficients.get(i) * (i));
        }

        coefficients.remove(coefficients.size()-1);
        this.Reduce();

    }

    //Return the product of polynomials p and q.
    /** Create new polynomial instance equal to the product of polynomial parameters.
     * 
     * @param p     First factor for product
     * @param q     Second factor product
     * @return      new polynomial equal to a * b
     */
    public static Polynomial Multiply(Polynomial p, Polynomial q){

        int pDegree = p.degree;
        int qDegree = q.degree;
        int totalDegree = pDegree + qDegree;

        Polynomial prod = new Polynomial(0.0);
        while (prod.degree < totalDegree){
            prod.addTerm(0.0, prod.degree+1);
        }

        while (p.degree < totalDegree){
            p.addTerm(0.0, p.degree+1);
        }

        while (q.degree < totalDegree){
            q.addTerm(0.0, q.degree+1);
        }

        for (int i=0;i<=pDegree;i++){
            for (int j=0;j<=qDegree;j++){
                prod.coefficients.set(i+j, prod.coefficients.get(i+j) + (p.coefficients.get(i) * q.coefficients.get(j)));
            }
        }

        p.Reduce();
        q.Reduce();
        prod.Reduce();

        return prod;
    }

    /** Comparison of this with input p using P_low convention.
     * @param p     input polynomial for comparison
     */
    @Override public int compareTo(Polynomial p){
        //Uses the P_low comparison.
        //p1 > p2 if the coefficient of the lowest degree of p1 - p2
        //is positive.
        Polynomial diff = new Polynomial(0.0);

        //Make diff equal to this-p.
        diff.Add(p);
        for (int i=0;i<=diff.degree;i++){
            diff.coefficients.set(i, -1.0 * diff.coefficients.get(i));
        }
        diff.Add(this);

        System.out.println("c1-c2");
        diff.Display();

        //Find first nonzero coefficient of the difference.
        int i = 0;
        while (i <= diff.degree && diff.coefficients.get(i)==0.0){
            i++;
        }
        if (i == diff.degree){
            return 0;
        }
        else if (diff.coefficients.get(i) < 0){
            return -1;
        }
        else {
            return 1;
        }

        

    }

    
}