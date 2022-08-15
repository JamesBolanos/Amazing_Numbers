package numbers;

import java.util.Objects;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Main {
    public static void main(String[] args) {

        welcomeBanner();

        while (true) {

            UserInput ui = new UserInput();

            // Validating first input
            if (ui.isWrongNumber() || (!ui.isNatural() && ui.getNumber() != 0)) {
                System.out.println("The first parameter should be a natural number or zero.");
                continue;
            }

            //Exit if the user requested with number ZERO
            if (ui.getNumber() == 0) {
                System.out.println("Goodbye!");
                return;
            }

            //Validating second input ...
            if (ui.isWrongIterations()) {
                System.out.println("The second parameter should be a natural number.");
                continue;
            }

            //Validating properties

            StringBuilder wrongProps = new StringBuilder();
            int countWrongProps = 0;

            wrongProps.append("[");

            //check for wrong properties

            if (!Objects.isNull(ui.getWrongProperty())) {

                for (int i = 0; i <= ui.getWrongProperty().size() -1; i++) {
                    if (ui.getWrongProperty().get(i)) {

                        wrongProps.append(ui.getProperty().get(i));
                        wrongProps.append(", ");
                        countWrongProps++;

                    }

                }

                //remove the trailing comma and add the last bracket ]

                wrongProps = new StringBuilder(wrongProps.toString().replaceAll(", $", "]"));

            }

            if (countWrongProps > 0) {

                if (countWrongProps == 1) {
                    System.out.println("The property " + wrongProps.toString() + " is wrong.");
                    System.out.println("Available properties : " + getAllEnumProperties());
                    continue;
                } else {

                    System.out.println("The properties " + wrongProps.toString() + " are wrong.");
                    System.out.println("Available properties : " + getAllEnumProperties());
                    continue;
                }


            }

            if (!Objects.isNull(ui.getProperty())) {

                if (((ui.getProperty().contains("even"))  && (ui.getProperty().contains("odd")))   ||
                        ((ui.getProperty().contains("duck"))  && (ui.getProperty().contains("spy")))   ||
                        ((ui.getProperty().contains("sunny")) && (ui.getProperty().contains("square"))) ||
                        ((ui.getProperty().contains("-odd")) && (ui.getProperty().contains("-even")) ||
                        ((ui.getProperty().contains("-odd")) && (ui.getProperty().contains("odd"))) ||
                        ((ui.getProperty().contains("-even")) && (ui.getProperty().contains("even"))) ||
                        ((ui.getProperty().contains("-duck")) && (ui.getProperty().contains("duck"))) ||
                        ((ui.getProperty().contains("-buzz")) && (ui.getProperty().contains("buzz"))) ||
                        ((ui.getProperty().contains("-palindromic")) && (ui.getProperty().contains("palindromic"))) ||
                        ((ui.getProperty().contains("-gapful")) && (ui.getProperty().contains("gapful"))) ||
                        ((ui.getProperty().contains("-spy")) && (ui.getProperty().contains("spy"))) ||
                        ((ui.getProperty().contains("-sunny")) && (ui.getProperty().contains("sunny"))) ||
                        ((ui.getProperty().contains("-square")) && (ui.getProperty().contains("square"))) ||
                        ((ui.getProperty().contains("-jumping")) && (ui.getProperty().contains("jumping"))) ||
                        ((ui.getProperty().contains("-happy")) && (ui.getProperty().contains("happy"))) ||
                        ((ui.getProperty().contains("-sad")) && (ui.getProperty().contains("sad")))
                        )) {

                    System.out.println("The request contains mutually exclusive properties:");
                    System.out.println("There are no numbers with these properties");
                    continue;
                }
            }


            /*
              After all validations are made, we need to select one of four scenarios:
              1. only one parameter was inputted: requires all properties of one specific number
              2. two parameters were inputted: requires the properties of n numbers
              3. three parameters were inputted: requires the properties of n numbers having p Property true
              4. four params were inputted: requires the properties of n numbers having p and q properties true
            */


            //Only one parameter inputted
            if (ui.getIterations() == 0) {

                displayNumberProperties(new Number(ui.getNumber()), true);

            }

            //Two numeric parameters inputted (number and iterations) and no properties inputted
            if ((ui.getIterations() > 0) && (Objects.isNull(ui.getProperty()))) {

                ArrayList<Number> numbers = new ArrayList<>();

                long number = ui.getNumber();

                for (int i = 0; i <= ui.getIterations() - 1; i++) {
                    numbers.add(new Number(number));
                    number++;
                }

                for (Number n : numbers) {
                    displayNumberProperties(n, false);
                }

            }

            //Three parameters or more inputted: number, iterations and one or more properties...

            if ((ui.getIterations() > 0) && (!Objects.isNull(ui.getProperty()))) {

                //Create a new arraylist and include ONLY the numbers that have the requested properties
                //This loop is just to verify if the number contains the REQUESTED properties
                //Other method will display ALL the possible properties the number has.

                ArrayList<Number> numbersWithPropertiesRequested = new ArrayList<>();


                long requiredNumbers = ui.getIterations();
                long startingNumber = ui.getNumber();

                while (requiredNumbers > 0) {


                    boolean allPropsAreOk = true;

                    for (int i = 0; i <= ui.getProperty().size() -1; i++) {

                        NumberProperty p;
                        boolean negatedProperty = ui.getProperty().get(i).startsWith("-");

                        // Convert the negated property to a proper ENUM by removing the - at the beginning
                        if (negatedProperty) {

                            p = NumberProperty.valueOf(ui.getProperty().get(i).toString().substring(1).toUpperCase() );

                        } else {

                            p = NumberProperty.valueOf(ui.getProperty().get(i).toUpperCase());

                        }

                        // handle normal and negated properties

                        if ( (!negatedProperty && !p.is(startingNumber)) || (negatedProperty && p.is(startingNumber))) {

                            allPropsAreOk = false;

                        }


                    }

                    if (allPropsAreOk) {
                        //A number was found, and it has all the properties requested
                        numbersWithPropertiesRequested.add(new Number(startingNumber));
                        requiredNumbers--;
                    }

                    startingNumber++;

                }

                for (Number n : numbersWithPropertiesRequested) {
                    displayNumberProperties(n, false);
                }
            }
        }
    }

    private static void welcomeBanner() {
        System.out.println("Welcome to Amazing Numbers!");
        System.out.println("");
        System.out.println("Supported requests:");
        System.out.println("- enter a natural number to know its properties;");
        System.out.println("- enter two natural numbers to obtain the properties of the list:");
        System.out.println("  * the first parameter represents a starting number;");
        System.out.println("  * the second parameter shows how many consecutive numbers are to be printed;");
        System.out.println("- two natural numbers and a property to search for;");
        System.out.println("- two natural numbers and two properties to search for;");
        System.out.println("- a property preceded by minus must not be present in numbers;");
        System.out.println("- separate the parameters with one space;");
        System.out.println("- enter 0 to exit.");
    }


    //Style true: multi line display, false: one-line display
    public static void displayNumberProperties(Number number, boolean style) {

        if (style) {

            System.out.println("Properties of " + number.getValue());

            for (NumberProperty p : NumberProperty.values()) {

                System.out.println(" ".repeat(20 - p.getValue().length())+ p.getValue() + ": " + p.is(number.getValue()));

            }

        } else {
            //all properties in one-line

            //create a string with all the properties...
            StringBuilder  strOut =  new StringBuilder();

            strOut.append(" ".repeat(20 - Long.toString(number.getValue()).length() ));
            strOut.append(number.getValue());
            strOut.append(" is ");


            for (NumberProperty p : NumberProperty.values()) {

                strOut.append(p.is(number.getValue()) ? p.getValue() + ", ": "");

            }

            //remove the trailing comma and blank space and output the string

            System.out.println(strOut.toString().replaceAll(", $", ""));

        }

    }

    public static void replaceAll(StringBuilder sb, Pattern pattern, String replacement) {
        Matcher m = pattern.matcher(sb);
        int start = 0;
        while (m.find(start)) {
            sb.replace(m.start(), m.end(), replacement);
            start = m.start() + replacement.length();
        }
    }

    public static String getAllEnumProperties() {

        StringBuilder allEnumProperties = new StringBuilder();

        allEnumProperties.append("[");

        for (NumberProperty np : NumberProperty.values()) {

            allEnumProperties.append(np.getValue());
            allEnumProperties.append(", ");

        }

        //remove the trailing comma and add the last bracket
        allEnumProperties = new StringBuilder(allEnumProperties.toString().replaceAll(", $", "]"));

        return allEnumProperties.toString();

    }








}