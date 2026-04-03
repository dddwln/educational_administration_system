package base;

public class DemoArray {
    public static void main(String[] args) {
        // 声明数组的方式：
        int[] array = new int[10];
        int[] array2 = { 1, 2, 3, 4, 5 };
        // 获取数组的长度
        int length = array.length;
        /*
          数组的遍历：
           for循环
           foreach循环
         */
        for(int x : array2) {
            System.out.println(x);

        }
    }
}
