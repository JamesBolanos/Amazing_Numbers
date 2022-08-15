package numbers;

import java.util.HashSet;
import java.util.Set;

public enum NumberProperty {

    EVEN ("EVEN"){
        @Override
        public boolean is(long i) {
            return (i % 2) == 0;
        };
    },
    ODD ("ODD") {
        @Override
        public boolean is(long i) {
            return ! ((i % 2) == 0);
        }
    },
    BUZZ ("BUZZ") {
        @Override
        public boolean is(long i) {
            boolean divisible = false, endsWith7 = false;

            if ((i % 7) == 0) {
                divisible = true;
            }

            if ((i % 10) == 7) {
                endsWith7 = true;
            }

            return divisible || endsWith7;
        }
    },
    DUCK ("DUCK"){
        @Override
        public boolean is(long i) {
            return hasZero(i);
        }
    },
    PALINDROMIC ("PALINDROMIC"){
        @Override
        public boolean is(long i) {
            //Step one: convert the  number to a string...
            String str = Long.toString(i);

            //Step two: create the reverse of the string...
            int strLength = str.length();
            StringBuilder reverseStr = new StringBuilder();

            for (int j = (strLength -1); j >= 0; --j) {
                reverseStr.append(str.charAt(j));

            }

            return str.equals(reverseStr.toString());
        }
    },
    GAPFUL ("GAPFUL") {
        @Override
        public boolean is(long i) {
            if (i <= 99) {
                return false;
            }

            //convert long into string...
            String str = Long.toString(i);

            //create a new number concatenating the first and last digit.
            long concat = Long.parseLong(String.valueOf(str.charAt(0)) +
                    String.valueOf(str.charAt(str.length()-1)));

            return i % concat == 0;
        }
    },
    SPY ("SPY") {
        @Override
        public boolean is(long i) {
            //A number is SPY if the sum of all its digit is equal to the product of all digits. ie 123 1+2+3=6 1*2*3=6

            //convert long into string...
            String str = Long.toString(i);
            long sum = 0;
            long mul = 1;

            for (int j = 0; j <= str.length() -1; j++ ) {

                sum = sum + Character.getNumericValue(str.charAt(j));
                mul = mul * Character.getNumericValue(str.charAt(j));

            }

            return sum == mul;

        }
    },
    SUNNY ("SUNNY") {
        @Override
        public boolean is(long i) {
            // i is a sunny number if i + 1 is a perfect square number.

            return isSquare(i+1);

        }
    },
    SQUARE ("SQUARE") {
        @Override
        public boolean is(long i) {

            return isSquare(i);

        }
    },
    JUMPING ("JUMPING") {
        @Override
        public boolean is(long i) {
            // a number n is called a jumping number if all the adjacent digits in n
            // have an absolute difference of 1.
            // note that the difference between 9 and 0 is not considered as 1
            // Therefore all single-digit number is considered as a jumping number

            boolean isJumping = true;

            if (i <= 0) {
                // Any single number is a jumping number
                return true;

            } else {

                //verify jumping property

                long num = i;

                while (num != 0) {

                    //determine the last digit rom the given number
                    int digit1 = (int) (num % 10);

                    //removes the last digit...
                    num = num / 10;

                    //checks if the number is equal to 0 or not
                    if (num != 0) {
                        //if the above condition is correct
                        //determines the second last digit from the number
                        int digit2 = (int) (num % 10);

                        //subtract the digits and finds the absolute value
                        //after that checks if the difference of two adjacent digits is equal to 1 or not
                        if (Math.abs(digit1 - digit2) != 1) {

                            isJumping = false;
                            break;

                        }
                    }
                }
            }

            return isJumping;
        }
    },
    HAPPY ("HAPPY") {
        @Override
        public boolean is(long i) {

            return isHappy(i);

        }
    },
    SAD ("SAD") {
        @Override
        public boolean is(long i) {

            return !isHappy(i);

        }
    };


    private final String value;

    NumberProperty(String value) {

        this.value = value;

    }

    public abstract boolean is (long i);

    private static boolean hasZero (long number){

        while (number > 0) {
            if (number % 10 == 0)
                return true;
            number = number / 10;
        }
        return false;
    }

    private static boolean isSquare ( long number){
        double x = Math.sqrt(number);
        return x == (int) x;
    }

    private static boolean isHappy (long i) {

        Set<Integer> digits = new HashSet<Integer>();

        while (digits.add((int) i)) {
            int result = 0;
            while (i > 0) {
                result += Math.pow(i % 10,2);
                i = i / 10;
            }
            i = result;
        }
        return i == 1;

    }

    public String getValue () {
        return value;
    }
}


