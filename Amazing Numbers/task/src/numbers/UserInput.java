package numbers;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class UserInput {

    private long number;
    private boolean wrongNumber = false;
    private boolean isNatural;
    private long iterations;
    private boolean wrongIterations = false;

    private final ArrayList<String> property = new ArrayList<>();
    private final ArrayList<Boolean> wrongProperty = new ArrayList<>();

    private final ArrayList<Boolean>  negatedProperty = new ArrayList<>();


    public UserInput() {

        String[] strInput;

        System.out.println("");
        System.out.println("Enter a request:");
        Scanner scan = new Scanner(System.in);

        strInput = scan.nextLine().split(" ");

        //get the first input, it should be a number...

        try {

            setNumber(Long.parseLong(strInput[0]));

        } catch (NumberFormatException e) {

            //Something went wrong converting the string input into a long number
            setWrongNumber(true);

        }

        //Check if the number is a natural number

        setNatural( getNumber() > 0);


        //get the second input, it should be a number or an empty string

        if (strInput.length > 1) {

            try {

                setIterations(Long.parseLong(strInput[1]));

            } catch (NumberFormatException e) {

                //Something went wrong converting the string input into a long number

                setWrongIterations(true);

            }

        } else {

            // the user does not include a second parameter so the system assumes ZERO iterations...
            setIterations(0);
            setWrongIterations(false);
        }


        //also check if iterations parameter is negative...
        setWrongIterations(getIterations() < 0);


        // Obtain all possible properties inputs and add them into three arrays: name of the property "property"
        // and a boolean property "wrongProperty" indicating if the property is a valid enum or is not.
        // and a last one called "negatedProperty" which indicates if the property is requested eliminated from the number

        //Starting with the position 2 to the length of the array - 2 do the loop
        //Checking if there is at least a third parameter in the input array...

        if (strInput.length > 2) {

            for (int i = 2; i <= strInput.length -1; i++ ) {

                String requestedProperty = strInput[i];

                // Handle negated properties (properties with starts with "-")


                if (requestedProperty.startsWith("-")) {

                    this.property.add(requestedProperty);
                    this.negatedProperty.add(true);
                    this.wrongProperty.add(!isValidProperty(requestedProperty.substring(1)));

                } else {

                    this.property.add(requestedProperty);
                    this.negatedProperty.add(false);
                    this.wrongProperty.add(!isValidProperty(requestedProperty));
                }

            }

        }

    }

    public long getNumber() {
        return this.number;
    }

    private void setNumber(long number) {
        this.number = number;
    }

    public boolean isWrongNumber() {
        return this.wrongNumber;
    }

    private void setWrongNumber(boolean isWrongNumber) {
        this.wrongNumber = isWrongNumber;
    }

    public boolean isWrongIterations() {
        return this.wrongIterations;
    }
    private void setWrongIterations(boolean isWrongIterations) {
        this.wrongIterations = isWrongIterations;
    }

    public long getIterations() {
        return this.iterations;
    }

    private void setIterations(long iterations) {
        this.iterations = iterations;
    }

    private boolean isValidProperty(String prop) {

                for (NumberProperty p: NumberProperty.values()) {

                    if (Objects.equals(p.getValue(), prop.toUpperCase())) {

                        return true;
                    }

                }

                return false;

    }

    public boolean isNatural() {
        return isNatural;
    }

    public void setNatural(boolean natural) {
        isNatural = natural;
    }

    public ArrayList<Boolean> getWrongProperty() {
        return wrongProperty;
    }

    public ArrayList<Boolean> getNegatedProperty() {
        return negatedProperty;
    }


    public ArrayList<String> getProperty() {
        return property;
    }


}
