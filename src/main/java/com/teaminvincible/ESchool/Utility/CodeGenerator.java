package com.teaminvincible.ESchool.Utility;

public final class CodeGenerator {

    public static String generateCourseJoinCode(){
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

        StringBuilder stringBuilder = new StringBuilder("");


        for (int i = 0; i < 10; i++) {
            int pos = (int) (AlphaNumericString.length()*Math.random());
            stringBuilder.append(AlphaNumericString.charAt(pos));
        }

        return stringBuilder.toString();
    }

}
