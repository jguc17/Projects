import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


public class Numbers {
    public static void main(String[] args) {
        // run solutions to Numbers section of Mega Project List
        Numbers test = new Numbers();   // need to call non-static methods from an instance of the object
        int success1 = test.findPiNthDigit(100);
        int success2 = test.findFibNumberIterative(20);
        int success3 = test.findFibNumberRecursive(20, 1, 0 );
        int[] success4 = test.findPrimeFactors(101);
        test.findNextPrimeNumber();
    }


    public int findPiNthDigit(int N) {
        BigDecimal pi = new BigDecimal(Math.PI);
        System.out.println(pi);
        BigDecimal scaled = null;

        // check for precision limit
        if(N < 32627) {
            int currentScale = pi.scale();
            System.out.println("scale is "+currentScale);

            if (currentScale <= N) {
                scaled = pi.setScale(N,BigDecimal.ROUND_DOWN);
            }
            else {
                scaled = pi.setScale(N,BigDecimal.ROUND_UP);
            }
            System.out.println(scaled);
        } else {
            System.out.println("You exceeded the precision limit\n");
            return -1;
        }
        return 0;
    }

    public int findFibNumberIterative(int N) {
        int lead = 1;
        int lag = 1;
        int temp;

        // check for erroneous input:
        if (N <= 0) return -1;

        for(int i = 1; i <= N; ++i) {
            if (i == 1 || i == 2) {
                // do nothing
            }
            else {
                temp = lead;
                lead += lag;
                lag = temp;
            }
            System.out.println("Fib "+i+": "+lead);
        }
        return 0;
    }

    public int findFibNumberRecursive(int N, int lead, int lag) {
        int temp;

        // check for erroneous input:
        if (N < 0) return -1;
        if (N == 0) return 0;

        System.out.println(lead);

        // prepare for next recursion
        temp = lead;
        lead += lag;
        lag = temp;
        N--;

        int recurse = findFibNumberRecursive(N, lead, lag);

        return 0;
    }

    public int[] findPrimeFactors(int N) {
        // iteratively divide by lower primes until unfactorable
        // prime: divisible only by 1 and itself

        // iterate up until N/2 (because only prime number bigger than that is potentially N)
        // two steps:
            // (1) figure out next prime <= N/2
            // (2) see if N is divisible by prime; if yes

        // input check
        if (N < 1) {
            System.out.println("findPrimeFactors wrong input");
            int[] errorArray = {-1};
            return errorArray;
        }

        ArrayList<Integer> primeList = new ArrayList<Integer>();
        primeList.add(1);

        int maxIter = N/2;
        int reducedN = N;
        int i = 2;

        int primeCounter = 0;
        int j;

        while(i <= maxIter) {
            //check if prime is divisble
            if(reducedN % i == 0) {
                // if divisible, add prime # to list, and factorize N
                primeList.add(i);
                reducedN /= i;

                // if N is still divisible by prime #, continue doing so
                while(reducedN % i == 0) {
                    reducedN /= i;
                }
            }

            // find next prime #
            int nextNum = i;

            //primeCounter must = 2 for each time the number is divisible by numbers >= 1 and <= the number
            while(primeCounter!=2) {
                primeCounter = 0;
                ++nextNum;

                // if next num is greater than N/2, break
                if(nextNum > maxIter) break;

                j = nextNum;
                while(j >= 1) {
                    if (nextNum % j == 0) {
                        ++primeCounter;
                    }
                    --j;
                }
            }
            i = nextNum;    // found next prime num for next iteration
            primeCounter = 0;   // reset primeCounter
        }

        // last check: if N itself is prime
        if(primeList.size() == 1) {
            primeList.add(N);
        }

        System.out.println(primeList.toString());

        // convert output to array
        Iterator<Integer> iter = primeList.iterator();
        int size = primeList.size();
        System.out.println("size of prime list is " + size);
        int[] primeOutput = new int[size];

        for (int k = 0; k< size; ++k) {
            primeOutput[k] = iter.next().intValue();
        }
        return primeOutput;
    }

    public void findNextPrimeNumber() {
        // take user input
        int counter = 1;    // for base case and text display
        int finder = 1;     // for storing next prime number
        int primeCounter = 0;   // for prime number validation

        Scanner scan = new Scanner(System.in);  // take in user input from cmd line
        while(true) {
            System.out.println("Would you like prime number " + counter + "? (Y/N)");
            String input = scan.nextLine();
            switch(input) {
                case "Y":
                    // base case check
                    if (counter == 1) {
                        System.out.println(finder);
                    } else {
                        // if next number is divisible only by itself and 1, then it is prime
                        while (primeCounter != 2) {
                            ++finder;   // check next number
                            primeCounter = 0;
                            int j = finder;
                            while (j >= 1) {
                                if (finder % j == 0) {
                                    ++primeCounter;
                                }
                                --j;
                            }
                        }
                        // after next prime has been found, reset counter
                        primeCounter = 0;
                        System.out.println(finder);
                    }
                    ++counter;
                    break;
                case "N":
                    System.out.println("bye!");
                    break;
                default:
                    System.out.println("couldn't understand the input!");
                    break;
            }
            //need another break stmt to exit outer while loop
            if(input.equals("N")) break;
        }
    }

}
