 

/* Nhi Tuong CSC 3410 MW
 * 
 * 1) To gain experience with stacks & convert equations to infix to postfix
 * 2) Program checks for errors in given equations and then rearrange the equation based on postfix algorithm/rules 
 * 	  calculate the postfix equations then printing out the final result.
 * 3) Stacks & char array
 * 4) User is required to input a valid infix equation and output would be a postfix equation with calculation.
 * 5) Only one class was used to execute all desire algorithm and print out results.
 */
package calculator;
import java.util.*;


public class calculator {
	
	//access scanner from all method
	public static Scanner sc = new Scanner(System.in);
	
    public static void main(String[] args) {
        
        Stack operators = new Stack();
        String postfix = new String();
        String infix = new String();
        int result;
        boolean test = false;
      
        
        System.out.print("Enter an infix expression: ");
        infix = sc.nextLine().replaceAll("\\s", "");
        char temp[] = infix.toCharArray();
         //Testing for an incorrect ending of the infix expression
           	if(infix.endsWith("+") || infix.endsWith("-") || infix.endsWith("/") ||infix.endsWith("*") ){
            System.out.println("Error in expression!! No operator between operands. Also last token must be an operand.");
            System.exit(1);
           	}
           	
        for(int g=0; g<temp.length; g++){
            if (temp[g] == '.'){ //Testing for decimals
                System.out.println("Error in expression!! Cannot accept floating point numbers.");
                System.exit(1);
            }
            if ( Character.toString(temp[g]).equals("(") && isOperator(Character.toString(temp[g+1])) ){ //Testing for incorrect (
                System.out.println("Error in expression!! No operator between operand and left parentheses.");
                System.exit(1);
            }
            if ( Character.toString(temp[g]).equals("(") ){ 
                test = true;
            }
            if ( Character.toString(temp[g]).equals(")") && !test ){ //Testing for incorrect )
                System.out.println("Error in expression!! No matching left parentheses for a right parentheses.");
                System.exit(1);
            }
            if ( isOperator(Character.toString(temp[g])) && isOperator(Character.toString(temp[g+1])) ){ //Testing for double operators
                System.out.println("Error in expression!! The operator cannot be preceded by another operator.");
                System.exit(1);
            }
           
        }
        //Making the postfix expression
        
        for (int i = 0; i<temp.length; i++){
            if(Character.isDigit(temp[i]) || Character.toString(temp[i]).equals("x")){ //Adding characters to the postfix exppression
                postfix += temp[i];
               
            if (i>1){
                if ((infix.charAt(i-1)==' ') && (Character.isDigit(postfix.charAt(postfix.length()-1)))){
                		System.out.println("Error in expression!!");
                		System.exit(0);
                	}
               }
            }
            
            if(Character.toString(temp[i]).equals("(")){ //Pushing the (
                operators.push(temp[i]);
            }
            
            if(Character.toString(temp[i]).equals(")")){ //Popping the stack till the rest of the expression in parenthesis is found and adding whats neccesary to the exp
                if(!operators.empty()){                    
                    
                    while(operators.peek() != "("){
                    postfix += operators.pop();
                    }           
                    operators.pop();                
                }
            }
            if( isOperator(Character.toString(temp[i])) ){ //Deciding where to put the operators
                try{
                while( !operators.empty() && !operators.peek().toString().equals("(") && precedence(Character.toString(temp[i])) <= precedence(operators.peek().toString()) ){
                postfix += operators.pop();
                }
              }
              catch(EmptyStackException e){
                  System.out.println("Error in Expression!!");
                  System.exit(0);
              }
                operators.push(temp[i]);              
            }
            
        }
        while(!operators.isEmpty()){ //Pushing the rest of the operators
            postfix += operators.pop();
        }
        
        
        System.out.println(postfix); //Printing the postfix
        
        while(true){
        result = calculate(postfix);  //Calculation
        System.out.println(result);
        }
    }

    /*
     * precondition: must be a string
     * postcondition: return an integer determining operator's precedence
     * 
     */
    public static int precedence(String c){
        if ("+".equals(c) || "-".equals(c) ) return 1;        
        else return 2;
    }
    
    //Testing to see if character is an operator
    /*
     * precondition: must be a string
     * postcondition: true or false if it equals operator strings
     */
    public static boolean isOperator(String z){
        if ("+".equals(z) || "-".equals(z) || "*".equals(z) || "/".equals(z))  return true;
        else return false;
    }
    //Method calculates postfix expression
    /*
     * precondition: must be a string
     * postcondition: return calculated int variable
     */
    public static int calculate(String s)  
   {  
     int n,r=0;  
     int tempNum = 0;
     String tempVal;
     n=s.length();  
     Stack a = new Stack();
     
     for(int i=0; i<n; i++) {  
       char ch=s.charAt(i);  
       if(ch>='0'&&ch<='9')  
         a.push((int)(ch-'0'));  
       else if(ch == 'x'){ //Gets the value for x
           System.out.print("Enter value of x: ");
           
           tempVal = sc.next();
           if(tempVal.equals("q")){ //Terminates program if insert q
               System.exit(1);
           }
           else tempNum = Integer.parseInt(tempVal);
               
           a.push(tempNum);
       }
       else  
       {  
         int x = Integer.parseInt(a.pop().toString());  
         int y = Integer.parseInt(a.pop().toString());  
         switch(ch)  
         {  
           case '+':r=x+y;  
              break;  
           case '-':r=y-x;  
              break;  
           case '*':r=x*y;  
              break;  
           case '/':r=y/x;  
              break;  
           default:r=0;  
         }  
         a.push(r);  
       }  
     }  
     r = Integer.parseInt(a.pop().toString());  
     return(r);  
   }  
  
}