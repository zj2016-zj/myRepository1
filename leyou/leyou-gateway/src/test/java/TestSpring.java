import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Array;
import java.util.Scanner;

public class TestSpring {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] nums = new int[10];
        for (int i = 0; i <10; i++) {
            int num = scanner.nextInt();
            nums[i]=num;
        }
        double average = getAverage(nums);
        System.out.println("正数的平均数为"+average);
    }
    public static double getAverage(int[] nums){
        double sum=0;
        int j=0;
        int length = nums.length;
        for (int i = 0; i < length; i++) {
          if (nums[i]>0){
              j++;
              sum=sum+nums[i];
          }
        }
        return sum/j;
    }
}
//1、输入两个整数，求最大值；

class TestDemn3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int max = getMax(a, b);
        System.out.println("最大值为" + max);
    }

    public static int getMax(int a, int b) {
        if (a > b) {
            return a;
        } else {
            return b;
        }
    }
}
//2、请输入一个整形的年份，然后判断是否是闰年。如果输入的是闰年则在屏幕上输出YES，否则输出NO。
class TestDemn4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int year = scanner.nextInt();
        judgeYear(year);
    }
    public static void judgeYear(int year){
        if (year%400==0||(year%4==0&&year%100==0)){
            System.out.println("YES");
        }else{
            System.out.println("NO");
        }
    }
}
//输入某分数，判断是否及格

class TestDemn5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int score = scanner.nextInt();
        judgeScore(score);
    }
    public static void judgeScore(int score){
        if (score<60){
            System.out.println("成绩不合格");
        }
    }
}
//③体重低于标准体重5kg，体形过瘦。

class TestDemn6 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double weight = scanner.nextDouble();
        double height = scanner.nextDouble();
        judgeFigure(weight,height);
    }

    public static void judgeFigure(double weight,double height) {
        if (weight<=(height-110+5)&&weight>=(height-110-5)) {
            System.out.println("体型正常");
        }else if (weight>(height-110+5)){
            System.out.println("体型过胖");
        }else if (weight<(height-110-5)){
            System.out.println("体型过轻");
        }
    }
}
class TestSpring1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        printClass(i);
    }
    public static void printClass(int age){
        switch (age){
            case 2:
            case 3:
                System.out.println("年龄为"+age+"岁；Enter Lower class");break;
            case 4:
                System.out.println("年龄为"+age+"岁；Enter Middle class");break;
            case 5:
            case 6:
                System.out.println("年龄为"+age+"岁；Enter Higher class");break;
        }
    }
}
//2、 输入10个整数，求正数的平均值

class TestSpring3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] nums = new int[10];
        for (int i = 0; i <10; i++) {
            int num = scanner.nextInt();
            nums[i]=num;
        }
        double average = getAverage(nums);
        System.out.println("正数的平均数为"+average);
    }
    public static double getAverage(int[] nums){
        double sum=0;
        int j=0;
        int length = nums.length;
        for (int i = 0; i < length; i++) {
            if (nums[i]>0){
                j++;
                sum=sum+nums[i];
            }
        }
        return sum/j;
    }
}
//1、 零件的问题，一堆零件100个到200个之间，分4个一组余2个，分7个一组余3个，分9个一组余5个，问共有多少零件
class TestSpring4 {
    public static void main(String[] args) {
        for (int i = 100; i <200 ; i++) {
            if (((i%4-2)==0)&&((i%7-3)==0)&&((i%9-5)==0)){
                System.out.println("共有零件"+i+"个");
            }
        }
    }
}
//求10个人的最高分，最低分，平均分，最高与最低之差
class TestSpring5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double[] doubles = new double[10];
        for (int i = 0; i < 10; i++) {
            double v = scanner.nextDouble();
            doubles[i]=v;
        }
        double max = getMax(doubles);
        double min = getMin(doubles);
        System.out.println("最高分为"+max);
        System.out.println("最低分为"+min);
        System.out.println("平均分为"+getAverage(doubles));
        System.out.println("最高与最低之差为"+(max-min));

    }
    public static double getMax(double score[]){
        double max=score[0];
        for (int i = 1; i <score.length ; i++) {
            if (max<score[i]){
                max=score[i];
            }
        }
        return max;
    }
    public static double getMin(double score[]){
        double min=score[0];
        for (int i = 1; i <score.length ; i++) {
            if (min>score[i]){
                min=score[i];
            }
        }
        return min;
    }
    public static double getAverage(double score[]){
        double sum=score[0];
        int length = score.length;
        for (int i = 1; i <length ; i++) {
            sum=sum+score[i];
        }
        return sum/length;
    }
}
