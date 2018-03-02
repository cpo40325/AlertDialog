package com.example.administrator.alertdialog;

/**
 * Created by Administrator on 2018/3/2.
 */

public class tt {
    public static void main(String[] a){

        System.out.println(sum(5));


    }

    public static int sum(int n){

        if(n==1){
            return 1;
        }else {
            return n*sum(n-1);
        }



    }
}
