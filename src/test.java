import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by zhaoshiqiang on 2016/10/17.
 */
public class test {

    public static void main(String[] args) throws Exception{
        //�ӿͻ��˿���������������л�������κ���Ϣ������ԭ���Ķ���ȷʵ���滻��
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(new People("li", "si", 22));

        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
        Object o =ois.readObject();
        System.out.println((People)o);
        //���SerializationProxy1��Ƴ�public������Ͳ��ᱨ��
//        People.SerializationProxy1 p =new People.SerializationProxy1(new People("li","si",22));
    }
}
