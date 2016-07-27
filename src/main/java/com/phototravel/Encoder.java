package com.phototravel;

/**
 * Created by Olga_Govor on 7/27/2016.
 */
public class Encoder {

    public String encode (String input){

        if (input.indexOf("&oacute;")>-1)
            input = input.substring(0,input.indexOf("&oacute;"))+"o"+input.substring(input.indexOf("&oacute;")+8);
        if (input.indexOf("ł")>-1)
            input = input.substring(0,input.indexOf("ł"))+"l"+input.substring(input.indexOf("ł")+1);
        if (input.indexOf("&uuml;")>-1)
            input = input.substring(0,input.indexOf("&uuml;"))+"u"+input.substring(input.indexOf("&uuml;")+6);

        if (input.indexOf("ń")>-1)
            input = input.substring(0,input.indexOf("ń"))+"n"+input.substring(input.indexOf("ń")+1);

        if (input.indexOf("ę")>-1)
            input = input.substring(0,input.indexOf("ę"))+"e"+input.substring(input.indexOf("ę")+1);

        if (input.indexOf("ż")>-1)
            input = input.substring(0,input.indexOf("ż"))+"z"+input.substring(input.indexOf("ż")+1);

        if (input.indexOf("ś")>-1)
            input = input.substring(0,input.indexOf("ś"))+"s"+input.substring(input.indexOf("ś")+1);

        if (input.indexOf("ą")>-1)
            input = input.substring(0,input.indexOf("ą"))+"a"+input.substring(input.indexOf("ą")+1);

        if (input.indexOf("ś")>-1)
            input = input.substring(0,input.indexOf("ś"))+"s"+input.substring(input.indexOf("ś")+1);

        if (input.indexOf("ź")>-1)
            input = input.substring(0,input.indexOf("ź"))+"z"+input.substring(input.indexOf("ź")+1);

        return input;
    }
}
