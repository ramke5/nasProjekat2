package ba.nalaz.util;



import java.util.Random;

public class PasswordGenerator {	

    private Random r = new Random();

    /*
     *  An Array of Strings that contain the appropriate characters based on the type of password to be generated.
     */
    String[] passwordValuesComplex = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
            "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "!", "@", "£", "$", "%", "^", "&", "*", "~", "`", "<", ">", "/", "?", "|", "[", "]", "+", "=", "-", "_"};

    String[] passwordValuesComplexNoSymbols = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
            "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9"};

    String[] passwordValuesLowerCaseNumbers = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
            "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9"};

    public PasswordGenerator() {
    	// default generator
    }
    public String generatePassword() {
        return generatePassword("ComplexNoSymbols", 8);
    }
    private String generatePassword(String passwordType, int length) {
    	StringBuilder temp = new StringBuilder();

        if ("Complex".equalsIgnoreCase(passwordType)) {
            for (int i = 0; i < length + 1; i++) {
                int random = r.nextInt(passwordValuesComplex.length);
                temp.append(passwordValuesComplex[random]);
            }
        }
        if ("ComplexNoSymbols".equalsIgnoreCase(passwordType)) {
            for (int i = 0; i < length + 1; i++) {
                int random = r.nextInt(passwordValuesComplexNoSymbols.length);
                temp.append(passwordValuesComplexNoSymbols[random]);
            }
        }
        if ("LowerCaseAndNumbers".equalsIgnoreCase(passwordType)) {
            for (int i = 0; i < length + 1; i++) {
                int random = r.nextInt(passwordValuesLowerCaseNumbers.length);
                temp.append(passwordValuesLowerCaseNumbers[random]);
            }
        }
        return temp.toString();
    }
}