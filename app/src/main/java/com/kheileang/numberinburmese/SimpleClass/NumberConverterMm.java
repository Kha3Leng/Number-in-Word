package com.kheileang.numberinburmese.SimpleClass;

public class NumberConverterMm {
    public static final String[] units = {
            "",
            " တစ်",
            " နှစ်",
            " သုံး",
            " လေး",
            " ငါး",
            " ခြောက်",
            " ခုနှစ်",
            " ရှစ်",
            " ကိုး"
    };
    public static final String[] doubles = {
            " တစ်ဆယ်",
            " တစ်ဆယ့်နှစ်",
            " တစ်ဆယ့်သုံး",
            " thirteen",
            " fourteen",
            " fifteen",
            " sixteen",
            " seventeen",
            " eighteen",
            " nineteen"
    };
    public static final String[] tens = {
            "",
            "",
            " twenty",
            " thirty",
            " forty",
            " fifty",
            " sixty",
            " seventy",
            " eighty",
            " ninety"
    };
    public static final String[] hundreds = {
            "",
            " thousand",
            " million",
            " billion"
    };

    public static String convertToWord(long number) {
        String word = "";
        int index = 0;
        do {
            // take 3 digits at a time
            long num = number % 1000;
            if (num != 0){
                String str = convertThreeOrLessThanThreeDigitNum(num);
                word = str + hundreds[index] + word;
            }
            index++;
            // move left by 3 digits
            number = number/1000;
        } while (number > 0);
        return word;
    }

    public static String convertThreeOrLessThanThreeDigitNum(long number) {
        String word = "";
        long num = number % 100;
        // Logic to take word from appropriate array
        if(num < 10){
            word = word + units[(int) num];
        }
        else if(num < 20){
            word = word + doubles[(int) (num%10)] + " and";
        }else{
            word = tens[(int) (num/10)] + units[(int) (num%10)];
        }
        word = (number/100 > 0)? units[(int) (number/100)] + " hundred" + word : word;
        return word;
    }
}
