import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by zhaoshiqiang on 2016/10/17.
 */
public class test {

    public static void main(String[] args) throws Exception{
        //从客户端看，看不到这个序列化代理的任何信息，但是原来的对象确实被替换了
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(new People("li", "si", 22));

        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
        Object o =ois.readObject();
        System.out.println((People)o);
        //如果SerializationProxy1设计成public，下面就不会报错
//        People.SerializationProxy1 p =new People.SerializationProxy1(new People("li","si",22));
    }
}
