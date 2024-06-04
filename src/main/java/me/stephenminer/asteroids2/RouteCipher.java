package me.stephenminer.asteroids2;

public class RouteCipher {
    /** A two-dimensional array of single-character strings, instantiated in the constructor */
    private String[][] letterBlock;
    /** The number of rows of letterBlock, set by the constructor */
    private int numRows;
    /** The number of columns of letterBlock, set by the constructor */
    private int numCols;

    public RouteCipher(int numRows, int numCols){
        this.numRows = numRows;
        this.numCols = numCols;
        letterBlock = new String[numRows][numCols];
    }
    /** Places a string into letterBlock in row-major order.
     * @param str the string to be processed
     * Postcondition:
     * if str.length() < numRows * numCols, "A" is placed in each unfilled cell
     * if str.length() > numRows * numCols, trailing characters are ignored
     */
    private void fillBlock(String str)
    {
        /* to be implemented in part (a) */
        int index = 0;
        for (int i = 0; i < letterBlock.length; i++){
            String[] entry = letterBlock[i];
            for (int a = 0; a < entry.length; a++){
                if (index < str.length()){
                    entry[a] = str.substring(index,index+1);
                }else entry[a] = "A";
                index++;
            }
        }
    }
    /** Extracts encrypted string from letterBlock in column-major order.
     * Precondition: letterBlock has been filled
     * @return the encrypted string from letterBlock
     */
    // a,a,a,a,a
    // a,a,a,a,a
    // a,a,a,a,a
    private String encryptBlock()
    { /* implementation not shown */
        StringBuilder builder = new StringBuilder();
        int index = 0;
        for (int i = 0; i < numCols; i++){
            for (int a = 0; a < numRows; a++){
                builder.append(letterBlock[a][i]);
            }

        }
        return builder.toString();
    }
    /** Encrypts a message.
     * @param message the string to be encrypted
     * @return the encrypted message;
     * if message is the empty string, returns the empty string
     */
    public String encryptMessage(String message)
    {
        int sLength = numCols*numRows;
        StringBuilder out= new StringBuilder();
        int index = 0;
        while (index +  sLength< message.length()){
            fillBlock(message.substring(index, index+6));
            out.append(encryptBlock());
            index+=sLength;
        }
        if (index < message.length()) {
            fillBlock(message.substring(index));
            out.append(encryptBlock());
        }

        //fillBlock(message);
        return out.toString();//encryptBlock();
        /* to be implemented in part (b) */
    }
// There may be instance variables, constructors, and methods that are not shown.

}
