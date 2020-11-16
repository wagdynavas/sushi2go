package com.wagdynavas.sushi2go.util;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringUtil {

    public static void main (String[] args) {
           // smaller(new int[] {5, 4 , 3 ,2, 1} );
            nextBiggerNumber(1234);
    }

    public String cleanString(String s) {
        String result = "";
        final Character hash = '#';
        for (int i = 0;  i < s.length() - 1; i++) {
            Character c = s.charAt(i);
            int resultLength = result.length();
            if (c == hash) {
                if (resultLength == 1) {
                    result = "";
                    continue;
                }
                if ( resultLength > 1) {
                    result  = result.substring(0, result.length() - 1);
                }
            } else {
                result = result + c.toString();
            }
        }

        return result;
    }

    public static int[] smaller(int[] unsorted) {
        int arrayLength = unsorted.length;
        int[] result = new int[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            for (int j = i + 1; j < arrayLength; j++) {
                if (unsorted[i] > unsorted[j] ) result[i]++;
            }

        }

        return result;
    }

    public static long nextBiggerNumber(long n) {
        String number = String.valueOf(n);
        String[] units = number.split("");
        String stringResult = "";
        int pivotIndex = -1;
        long result;


        for (int i = units.length - 1; i > 0; i--) {
            int unit = Integer.valueOf(units[i]);
            int nextUnit = Integer.valueOf(units[i - 1]);

            if (unit > nextUnit) {
                pivotIndex = i - 1;
                break;
            }
        }
        if (pivotIndex == -1) return pivotIndex;
        String rightNumbers = number.substring(pivotIndex + 1);
        String pivot = number.substring(pivotIndex, pivotIndex + 1);
        String leftNumbers = number.substring(0,  pivotIndex);
        if (pivotIndex == 0)  leftNumbers = "";

        int pivotSub = -1;
        int pivotSutIndex = -1;

        for (int i = 0; i < rightNumbers.length(); i++) {
            int pv = Character.getNumericValue(rightNumbers.charAt(i));
            if (pv > Integer.valueOf(pivot)) {
                if (pivotSub > -1 || pv > pivotSub)
                pivotSutIndex = i;
                pivotSub = pv;
            }
        }

        String rightNumber = rightNumbers.substring(0, pivotSutIndex) + pivot + rightNumbers.substring(pivotSutIndex + 1);

        //stringResult = Arrays.asList(units).stream().collect(Collectors.joining());
        //result = Long.valueOf(stringResult);
        stringResult = leftNumbers + pivotSub + Arrays.asList(rightNumber.split("")).stream().sorted().collect(Collectors.joining());
        result = Long.valueOf(stringResult);
        if (result == n) result = -1;
        return result;
    }
}
