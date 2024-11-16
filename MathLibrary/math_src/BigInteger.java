package math.computation.numbers;

import java.util.*;

public class BigInteger{

    ArrayList<Integer> Value = new ArrayList<>();

    //Constructor 1.
    public BigInteger(String IntString){

        for (char c:IntString.toCharArray()){
            Value.add(c-48);
        }

    }

    //Constructor 2.  Create byte array of length equal to size.
    //The entries will all be zero.
    public BigInteger(int size){

        for (int i=0;i<size;i++){
            Value.add(0);
        }

    }

    //Multiply by one-digit number.
    private static BigInteger singleDigitMult(BigInteger a, int d){

        ArrayList<Integer> result = new ArrayList<>();
        int carry = 0;
        int curr;
        for(int i=a.Value.size()-1;i>=0;i--){
            curr = a.Value.get(i) * d +carry;
            if (curr < 10){
                result.add(curr);
                carry = 0;
            }
            else{
                result.add(curr%10);
                carry = (curr - curr%10)/10;
            }
        }
        if (carry > 0){
            result.add(carry);
        }

        //Reverse the digits of the result.
        int n = result.size();
        BigInteger finalresult = new BigInteger(n);
        for (int j=0;j<n;j++){
            finalresult.Value.set(j, result.get(n-j-1));
        }

        return finalresult;

    }

    //Returns a multiplied by 10^i.
    private static BigInteger powerOfTen(BigInteger a, int i){
        
        if (i==0){
            return a;
        }
        BigInteger result = new BigInteger(a.Value.size()+i);
        for (int j=0;j<a.Value.size();j++){
            result.Value.set(j, a.Value.get(j));
        }

        return result;

    }

    //Add a to b.
    public static BigInteger Add(BigInteger a, BigInteger b){

        ArrayList<Integer> result = new ArrayList<>();
        int carry = 0;
        int curr;
        //Pad with zeros to make both same length.
        while (a.Value.size() < b.Value.size()){
            a.Value.add(0, 0);
        }
        while (b.Value.size() < a.Value.size()){
            b.Value.add(0, 0);
        }
        int n = b.Value.size();
        for(int i=n-1;i>=0;i--){
            curr = a.Value.get(i) + b.Value.get(i) + carry;
            if (curr < 10){
                result.add(curr);
                carry = 0;
            }
            else{
                result.add(curr%10);
                carry = (curr - curr%10)/10;
            }
            
        }
        if (carry > 0){
            result.add(carry);
        }

        //Reverse the digits of the result.
        n = result.size();
        BigInteger finalresult = new BigInteger(n);
        for (int j=0;j<n;j++){
            finalresult.Value.set(j, result.get(n-j-1));
        }

        return finalresult;

    }

    public static BigInteger Add(String a, String b){
        BigInteger p = new BigInteger(a);
        BigInteger q = new BigInteger(b);
        return Add(p, q);
    }


    public static BigInteger Multiply(BigInteger a, BigInteger b){

        BigInteger result = new BigInteger(1);
        int steps = b.Value.size();
        int j = 0;
        for (int i=steps-1;i>=0;i--){
            result = Add(result, powerOfTen(singleDigitMult(a, b.Value.get(i)), j++));
        }

        return result;
    }

    public static BigInteger Multiply(String a, String b){
        BigInteger p = new BigInteger(a);
        BigInteger q = new BigInteger(b);
        return Multiply(p, q);
    }

    @Override
    public String toString(){

        StringBuilder astr = new StringBuilder();
        for (int n:this.Value){
            astr.append(String.valueOf(n).charAt(0));
        }

        return astr.toString();

    }

}