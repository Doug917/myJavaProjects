package math.computation.geometry;

import java.util.*;

/**create a shape defined by a list of vertices and perform geometric operations.
 * @author Doug B
 * @version 1.0
 */
public class Polygon2D{

    public int numVertices = 0;
    public ArrayList<double[]> Vertices = new ArrayList<>();

    /** One constructor using input of double array.
     * @param Vertices list of vertices; each entry is a list of length two with x and y coordinates [x,y].
     */
    public Polygon2D(double[][] Vertices){

        for (double[] v:Vertices){
            this.Vertices.add(v);
            numVertices++;
        }
    }

    /** Add a vertex at desired position.
     * 
     * @param Vertex    two-element double [x,y] representing added vertex.
     * @param Position  position in range 0 to Vertex.length - 1.
     */
    public void addVertex(double[] Vertex, int Position){
        
        this.Vertices.add(Position, Vertex);
        numVertices++;

    }

    /** Remove an existing vertex at position p in range 0 to vertex.length.
     * 
     * @param Position
     */
    public void removeVertex(int Position){
        
        this.Vertices.remove(Position);
        numVertices--;

    }

    /** Compute the perimeter of object using the distance formula.
     * 
     * @return  double value representing perimeter.
     */
    public double Perimeter(){

        double result = 0.0;
        for (int i=1;i<numVertices;i++){
            result += Math.sqrt(Math.pow(Vertices.get(i)[0]-Vertices.get(i-1)[0], 2) + Math.pow(Vertices.get(i)[1]-Vertices.get(i-1)[1], 2));
        }
        //Add the length between final vertex and first vertex.
        result += Math.sqrt(Math.pow(Vertices.get(0)[0]-Vertices.get(numVertices-1)[0], 2) + Math.pow(Vertices.get(0)[1]-Vertices.get(numVertices-1)[1], 2));

        return result;

    }

    /** Compute the perimeter of object using the shoelace formula.
     * 
     * @return double value repesenting shape area.
     */
    public double Area(){

        double result = 0;
        for (int i=1;i<numVertices;i++){
            result += (Vertices.get(i)[0] + Vertices.get(i-1)[0]) * (Vertices.get(i)[1] - Vertices.get(i-1)[1]);
        }
        //Account for term between last vertex and first vertex.
        result += (Vertices.get(0)[0] + Vertices.get(numVertices-1)[0]) * (Vertices.get(0)[1] - Vertices.get(numVertices-1)[1]);

        return 0.5 * Math.abs(result);

    }

}