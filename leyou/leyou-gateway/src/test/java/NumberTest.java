import java.util.Scanner;

public class NumberTest {
    public static void main(String[] args) {
        for (int i = 100; i <=200 ; i++) {
            if (((i%4-2)==0)&&((i%7-3)==0)&&((i%9-5)==0)){
                System.out.println("共有零件"+i+"个");
            }
        }

    }
}
