package com.stra.starplusuniform;

import androidx.appcompat.app.AppCompatActivity;

import com.eway.payment.sdk.presentation.RapidAPI;

import java.lang.reflect.Array;

public class EwayIntActivity{

    static String a [] ={"1","2","3","4","5","6","7","8","9","10"};

    public static void main(String[] args) {
        String []b=a.toString().split(",");
        System.out.println("VALUESSPLIT "+b);

    }

}