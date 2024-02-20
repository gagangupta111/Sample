
package com.sample;

class Node {
    private Node leftChild, rightChild;

    public Node(Node leftChild, Node rightChild) {
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public Node getLeftChild() {
        return this.leftChild;
    }

    public Node getRightChild() {
        return this.rightChild;
    }

    public boolean isHeightBalanced(Node node){

        int left = node.leftChild == null ? 0 : height(node.leftChild);
        int right = node.rightChild == null ? 0 : height(node.rightChild);
        int diff = 0;

        if (left > right){
            diff = left-right;
        }else {
            diff = right-left;
        }

        return diff <= 1;

    }

    public int height() {
        return height(this)-1;
    }

    public int height(Node root) {

        int left = 0;
        int right = 0;

        if (root.leftChild != null){
            left = height(root.leftChild);
        }

        if (root.rightChild != null){
            right = height(root.rightChild);
        }

        return left > right ? left+1 : right+1;

    }

    public static void main(String[] args) {
        Node leaf1 = new Node(null, null);
        Node leaf2 = new Node(null, null);
        Node node = new Node(leaf1, null);
        Node root = new Node(node, leaf2);
        Node rootMore = new Node(root, null);

        System.out.println((rootMore.isHeightBalanced(root)));
    }
}
