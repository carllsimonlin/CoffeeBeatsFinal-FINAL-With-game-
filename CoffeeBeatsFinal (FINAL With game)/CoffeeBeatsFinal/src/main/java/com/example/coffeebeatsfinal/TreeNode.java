package com.example.coffeebeatsfinal;

public class TreeNode {

    TreeNode left;
    TreeNode right;
    Song data;

    TreeNode(){
        left = null;
        right = null;
    }

    TreeNode(Song data) {
        left = null;
        right = null;
        this.data = data;
    }
}
