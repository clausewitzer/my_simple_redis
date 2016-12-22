package com.bear;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bear on 16-12-21.
 */
public class Test {

    public static void main(String[] args) {
        System.out.println("-->");


//        String s = System.getProperty("line.separator");
        String s = "\r\n";
        byte[] c = s.getBytes();
        for(byte cc : c){
            System.out.println("0x0"+Integer.toHexString(cc));
        }

        byte _a = c[1];
        byte _aa = "\r".getBytes()[0];

        System.out.println(_a==_aa);


        List list = new ArrayList<>(3);
        System.out.println(list.size());

//        byte a =  ()
    }
}
