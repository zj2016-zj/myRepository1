import java.lang.reflect.Field;
import java.util.ArrayList;

public class TestDemo {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        ArrayList arrayList = new ArrayList();

        Field elementData = ArrayList.class.getDeclaredField("elementData");


        elementData.setAccessible(true);
        Object[] o1=(Object[])elementData.get(arrayList);
        System.out.println("默认"+o1.length);
        for (int i = 0; i < 100; i++) {
            arrayList.add(i);
            Object[] o=(Object[])elementData.get(arrayList);
            System.out.println(o.length);
        }
    }
}
