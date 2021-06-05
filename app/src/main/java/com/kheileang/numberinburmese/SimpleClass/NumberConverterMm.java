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
            " တစ်ဆယ့်တစ်",
            " တစ်ဆယ့်နှစ်",
            " တစ်ဆယ့်သုံး",
            " တစ်ဆယ့်လေး",
            " တစ်ဆယ့်ငါး",
            " တစ်ဆယ့်ခြောက်",
            " တစ်ဆယ့်ခုနှစ်",
            " တစ်ဆယ့်ရှစ်",
            " တစ်ဆယ့်ကိုး"
    };
    public static final String[] tens = {
            "",
            "",
            " နှစ်ဆယ်",
            " သုံးဆယ်",
            " လေးဆယ်",
            " ငါးဆယ်",
            " ခြောက်ဆယ်",
            " ခုနှစ်ဆယ်",
            " ရှစ်ဆယ်",
            " ကိုးဆယ်"
    };
    public static final String[] hundreds = {
            "",
            "ထောင်",
            "သောင်း",
            "သိန်း",
            "ရာ",
            "ထောင်",
            "သောင်း",
            "သိန်း",
            "သန်း",
            "ကုဋေ"
    };

    public static String convertToWord(long number) {
        String num = "";
        int index = 0;
        int n;
        int digits = 0;
        long origin = number;
        boolean firstIteration = true;
        do {
            if(firstIteration){
                digits = 1000;
            }else if (index == 1){
                digits = 10;
            }else if (index == 3){
                digits = 100;
            }else if (index == 4){
                digits = 10;
            }else if (index == 5){
                digits = 10;
            }
            n = (int) (number % digits);
            if (n != 0){
                String s = convertThreeOrLessThanThreeDigitNum(n);
                num = s + hundreds[index] + num;
            }
            index++;
            number = number/digits;
            firstIteration = false;
        } while (number > 0);
        num = Long.toString(origin).length() > 7 ? "သိန်း"+num: num;
        return num + "ကျပ်တိတိ";
    }

    public static String convertThreeOrLessThanThreeDigitNum(long number) {
        String word = "";
        int num = (int) (number % 100);
        // Logic to take word from appropriate array
        if(num < 10){
            word = word + units[num];
        }
        else if(num < 20){
            word = word + doubles[num%10];
        }else{
            word = tens[num/10] + units[num%10];
        }
        word = (number/100 > 0)? units[(int) (number/100)] + "ရာ " + word : word;
        return word;
    }
}
