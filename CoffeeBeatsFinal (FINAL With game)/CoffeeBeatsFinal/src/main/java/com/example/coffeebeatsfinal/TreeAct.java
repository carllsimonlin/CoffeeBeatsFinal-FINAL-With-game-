package com.example.coffeebeatsfinal;

import java.util.LinkedList;

public class TreeAct {

    TreeNode root;

//constructor
    public TreeAct() {
        root = null;
    }
//checks if empty
    boolean isEmpty() {return root == null;}

    // insert
    void insert (Song d) {root = insertRec(root, d);}
    TreeNode insertRec (TreeNode root, Song d) {

        if (root == null) {
            root = new TreeNode(d);
            return root;}


        //else traverse down the list
        else if (isAlphabeticallySmaller(d.Nameout(),  root.data.Nameout())) {
            root.left = insertRec(root.left, d);
        } else if (isAlphabeticallySmaller(root.data.Nameout(), d.Nameout())) {
            root.right = insertRec(root.right, d);
        } else if (d.Nameout().equals(root.data.Nameout())){
            root.left = insertRec(root.left, d);
        }


        return root;
    }


    public TreeNode Lchild(TreeNode root){
        if (root.left != null){
            return root.left;
        }
        else return null;
    }
    public TreeNode Rchild(TreeNode root){
        if (root.right != null){
            return root.right;
        }
        else return null;
    }

    //search
    public Song search(String d){
        System.out.println(root.data.Nameout());
        TreeNode a = searchRec(root, d);
        return a.data;}


    TreeNode searchRec(TreeNode root, String d){
        if (root == null) {
            System.out.println("Tree is empty or data not found");
            return root;}

        if (isAlphabeticallySmaller(d,  root.data.Nameout())) {
            root = searchRec(root.left, d);
        } else if (isAlphabeticallySmaller(root.data.Nameout(), d)) {
            root = searchRec(root.right, d);
        }
        // If the data is the same as the node,
        else if (d.equalsIgnoreCase(root.data.Nameout())) {
            return root;
        } else System.out.println("Data not found");

        return root;

    }




    static boolean isAlphabeticallySmaller(String str1, String str2)
    {
        str1 = str1.toUpperCase();
        str2 = str2.toUpperCase();
        if (str1.compareTo(str2) < 0) {
            return true;
        }
        return false;
    }
}