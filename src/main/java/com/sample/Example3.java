
package com.sample;

public class Example3 {

    public boolean hasPathSum(TreeNode root, int targetSum) {

        if (root.val == targetSum){
            if (root.left == null && root.right == null){
                return true;
            }
        }else if (root.val > targetSum){
            return false;
        }

        boolean isSum = hasPathSum(root.left, targetSum, root.val);
        if (isSum){
            return isSum;
        }

        isSum = hasPathSum(root.right, targetSum, root.val);
        if (isSum){
            return isSum;
        };
        return false;
    }

    public boolean hasPathSum(TreeNode root, int targetSum, int currentSum) {

        if (root == null){
            return false;
        }
        if (currentSum+root.val == targetSum){
            if (root.left == null && root.right == null){
                return true;
            }
        }else if (currentSum+root.val > targetSum){
            return false;
        }

        boolean isSum = hasPathSum(root.left, targetSum, currentSum+root.val);
        if (isSum){
            return isSum;
        }
        isSum = hasPathSum(root.right, targetSum, currentSum+root.val);
        if (isSum){
            return isSum;
        }
        return false;
    }

    public static void main(String[] args){

        Example3 example3 = new Example3();
        TreeNode root = new TreeNode();
        root.val = 1;

        root.left = new TreeNode();
        root.left.val = 2;

        root.right = new TreeNode();
        root.right.val = 3;

        root.left.left = new TreeNode();
        root.left.left.val = 4;

        System.out.println(example3.hasPathSum(root, 4));
        System.out.println(example3.hasPathSum(root, 5));
        System.out.println(example3.hasPathSum(root, 6));
        System.out.println(example3.hasPathSum(root, 7));
        System.out.println(example3.hasPathSum(root, 8));
        System.out.println(example3.hasPathSum(root, 9));

    }
}
