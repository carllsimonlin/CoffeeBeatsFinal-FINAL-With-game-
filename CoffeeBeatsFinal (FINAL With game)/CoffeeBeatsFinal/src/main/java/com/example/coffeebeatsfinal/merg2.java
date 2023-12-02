package com.example.coffeebeatsfinal;

import java.util.LinkedList;

public class merg2 {
    public static void mergeSort(LinkedList<Song> array){
        int n = array.size();
        if (n <= 1) return;

        LinkedList<Song> left = new LinkedList();
        LinkedList<Song> right = new LinkedList();


        for(int i = 0; i < n; i++){
            if (i<  n/2) {left.add((Song) array.get(i));}
            else {
                right.add((Song) array.get(i)) ;}

        }
        mergeSort(left);
        mergeSort(right);
        merge(left, right, array);
    }

    private static void merge(LinkedList<Song> leftarr, LinkedList<Song> rightarr, LinkedList<Song> array){
        int sizeL= array.size()/2;
        int sizeR = array.size() - sizeL;

        int i = 0, l = 0, r = 0;

        while(l < sizeL && r< sizeR){
            if (isAlphabeticallySmaller(leftarr.get(l).Nameout(), rightarr.get(r).Nameout())) {
                array.set(i,leftarr.get(l) );
                i++;
                l++;
            }
            else {
                array.set(i,rightarr.get(r) );
                i++;
                r++;
            }
        }
        while(l< sizeL){
            array.set(i,leftarr.get(l) );
            i++;
            l++;
        }
        while(r< sizeR){
            array.set(i,rightarr.get(r) );
            i++;
            r++;
        }
    }

    static boolean isAlphabeticallySmaller(String str1, String str2) {
        str1 = str1.toUpperCase();
        str2 = str2.toUpperCase();
        if (str1.compareTo(str2) < 0) {
            return true;
        }
        return false;}


    public static void mergeSortAlbum(LinkedList<Song> array){
        int n = array.size();
        if (n <= 1) return;

        LinkedList<Song> left = new LinkedList();
        LinkedList<Song> right = new LinkedList();


        for(int i = 0; i < n; i++){
            if (i<  n/2) {left.add((Song) array.get(i));}
            else {
                right.add((Song) array.get(i)) ;}

        }
        mergeSortAlbum(left);
        mergeSortAlbum(right);
        mergeAlbum(left, right, array);
    }

    private static void mergeAlbum(LinkedList<Song> leftarr, LinkedList<Song> rightarr, LinkedList<Song> array){
        int sizeL= array.size()/2;
        int sizeR = array.size() - sizeL;

        int i = 0, l = 0, r = 0;

        while(l < sizeL && r< sizeR){
            if (isAlphabeticallySmaller(leftarr.get(l).Albout(), rightarr.get(r).Albout())) {
                array.set(i,leftarr.get(l) );
                i++;
                l++;
            }
            else {
                array.set(i,rightarr.get(r) );
                i++;
                r++;
            }
        }

        while(l< sizeL){
            array.set(i,leftarr.get(l) );
            i++;
            l++;
        }

        while(r< sizeR){
            array.set(i,rightarr.get(r) );
            i++;
            r++;
        }
    }
}



