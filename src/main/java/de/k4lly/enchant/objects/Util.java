package main.java.de.k4lly.enchant.objects;

public class Util {
    public static String getRomanNumber(int level) {
        if (level < 1 || level > 3999)
            return "Invalid Roman Number Value";
        String s = "";
        while (level >= 1000) {
            s += "M";
            level -= 1000;        }
        while (level >= 900) {
            s += "CM";
            level -= 900;
        }
        while (level >= 500) {
            s += "D";
            level -= 500;
        }
        while (level >= 400) {
            s += "CD";
            level -= 400;
        }
        while (level >= 100) {
            s += "C";
            level -= 100;
        }
        while (level >= 90) {
            s += "XC";
            level -= 90;
        }
        while (level >= 50) {
            s += "L";
            level -= 50;
        }
        while (level >= 40) {
            s += "XL";
            level -= 40;
        }
        while (level >= 10) {
            s += "X";
            level -= 10;
        }
        while (level >= 9) {
            s += "IX";
            level -= 9;
        }
        while (level >= 5) {
            s += "V";
            level -= 5;
        }
        while (level >= 4) {
            s += "IV";
            level -= 4;
        }
        while (level >= 1) {
            s += "I";
            level -= 1;
        }
        return s;
    }

    public static int parseRomanNumber(String str) throws Exception {
        String[] letras = {"M","CM","D","CD","C","XC","L","XL","X", "IX","V","IV","I"};
        int[] valores = {1000,900,500,400,100,90,50,40,10,9,5,4,1};

        // here we can do even more business validations like avoiding sequences like XXXXM
        if (str==null || str.isEmpty()) {
            return 0;
        }
        for(int i=0; i<letras.length; i++) {
            if (str.startsWith(letras[i])) {
                return valores[i] + parseRomanNumber(str.substring(letras[i].length()));
            }
        }
        throw new Exception("Something bad happened.");
    }
}