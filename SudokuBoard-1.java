//Robert
//Sudoku board setup

//This program creates a method that will read a file that is 9x9 and create a 2D array of 
//a sudoku board from it. And a toString method to make the board look cleaner.
import java.io.*;
import java.util.*;

class SudokuBoard{

    private char board[][];
    
    
    //pre: file is valid
    //post: creates a board
    SudokuBoard(String name) {
      try {
        board=new char[9][9];
        
        File myObj=new File(name);
        Scanner sc=new Scanner(myObj);
        int k=0;
        while(sc.hasNextLine()){
            String data=sc.nextLine();
            for(int i=0;i<data.length();i++){
                if(data.charAt(i)=='.'){
                    board[k][i]= '*';

                } else {
                  board[k][i] = data.charAt(i);
                }

        
            }
         k++;
        }

      } catch (FileNotFoundException e) {
         System.out.println(e);
      }
      
    }
    
    //pre: none
    //post: returns the board and makes the board look prettier
    public String toString(){
    
        String result="[";

        for(int i=0;i<9;i++){
         result+="[";
            for(int j=0;j<9;j++){
                result+="'"+board[i][j]+"'";
                if(j != 8){
                  result+=",";
                }
            }
            result += "]";
            if(i != 8){
               result += ","+'\n';
            }
        }
        result += "]";
        return result;
    }
    //pre: none
    //post: solves the sudoku board using recursion and backtracking
    public boolean solve(){
        if(!isValid()){
           return false;
        }
        
        if(isSolved()){
           return true;
        }
    
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if(board[i][j] == '*'){
                    for(char num = '1' ; num <= '9'; num++){
                        //using a new isValid that takes in parameters to check if I were
                        //to put in the number, if it would be valid on that spot
                        if(isValidNumber(i, j, num)){
                            board[i][j] = num;
                            
                            if(solve()){
                                return true;
                            }
                            else{
                                board[i][j] = '*';
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
    
    //pre: none
    //post: return true if number is valid in the row col and box, false otherwise
    public boolean isValidNumber(int row, int col, int num){
    
        int rowIndex = row - row%3;
        int colIndex = col - col%3;
        
        //This is for row and the col
        for(int i = 0; i < board.length; i++){
            if(board[row][i] == num ||
               board[i][col] == num) {
                return false;
            }
        }
        
        //This is for each sub-grid
        for(int i = rowIndex; i < rowIndex+3; i++){
            for(int j = colIndex; j < colIndex+3;j++){
                if(board[i][j] == num){
                    return false;
                }
            }
        }
        return true;
    }
    //Pre: none
    //Post: returns true if board is valid, false otherwise.
    public boolean isValid() {
    
        HashSet<String> set = new HashSet<String>();
        
        HashSet<Character> validChar = new HashSet<Character>();
        
        validChar.add('1');
        validChar.add('2');
        validChar.add('3');
        validChar.add('4');
        validChar.add('5');
        validChar.add('6');
        validChar.add('7');
        validChar.add('8');
        validChar.add('9');
        validChar.add('*');
        
        for(int i = 0; i < 9; i++){
        
            for(int j = 0; j < 9; j++){
            
                 if(!validChar.contains(board[i][j])){
                 
                   return false;
                 }
                
                 if(board[i][j] != '*') {
                  
                      int val = board[i][j];
                       
                      if(!set.add("row"+i+"value"+val)||
                         !set.add("col"+j+"value"+val)||
                         !set.add("row-box"+i/3+"col-box"+j/3+"value"+val)){
                         
                          return false;
                       
                      }
                 
                 } 
                                
             }
        }
        
        return true;
    }
    //pre: none
    //post: returns true if board is solved and valid, false otherwise.
    public boolean isSolved() {
      
      HashMap<Character,Integer> map = new HashMap<Character,Integer>();
      
      for(int i = 0; i < 9; i ++) {
      
         for(int j = 0; j < 9; j++) {
         
            if(board[i][j] != '*') {
         
               if(map.containsKey(board[i][j])) {
               
                  int counter = map.get(board[i][j]);
                  
                  map.put(board[i][j], counter+1);
                  
               } else {
                  map.put(board[i][j], 1);
               }
               
             } else {
                  return false;
             }
            
         }
      }
      
      for(char i = '1'; i <= '9'; i++) {
         
         int frequency = map.get(i);
         
         if (frequency != 9) {
            return false;
         }
      }
      
      if(isValid()) {
      
         return true;
      }
       
      
    
       return false;
   }

    
    
}
/* Output on SudokuSolverEngine

  ----jGRASP exec: java -ea SudokuSolverEngine
 Initial board
 [['*','3','4','6','7','8','9','1','2'],
 ['*','7','2','1','9','5','3','4','8'],
 ['1','9','8','3','4','2','5','6','7'],
 ['*','*','9','*','6','1','4','2','3'],
 ['*','2','6','8','5','3','7','9','1'],
 ['*','1','3','9','2','4','*','5','6'],
 ['*','6','1','5','3','7','2','8','4'],
 ['*','8','*','4','1','9','6','3','5'],
 ['3','4','5','*','8','6','1','7','9']]
 
 Solving board...SOLVED in 0.000 seconds.
 
 [['5','3','4','6','7','8','9','1','2'],
 ['6','7','2','1','9','5','3','4','8'],
 ['1','9','8','3','4','2','5','6','7'],
 ['8','5','9','7','6','1','4','2','3'],
 ['4','2','6','8','5','3','7','9','1'],
 ['7','1','3','9','2','4','8','5','6'],
 ['9','6','1','5','3','7','2','8','4'],
 ['2','8','7','4','1','9','6','3','5'],
 ['3','4','5','2','8','6','1','7','9']]
 
  ----jGRASP: operation complete.
 		*/

    