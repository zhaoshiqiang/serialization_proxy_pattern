import java.io.*;

public class People implements Serializable {

    private final String firstname;
    private final String lastname;
    private int age;

    public People(String firstname, String lastname, int age) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString(){
        return firstname + "-" + lastname + " is " + age;
    }

    /**
     *��������������������л�ʱ�滻����
     * Serializable����������ǽӿڷ�������ʵ�����л�������滻����writeReplace��readResolve��
     * writeReplace��ԭ�ͣ���������޶��� Object writeReplace() throws ObjectStreamException;
     * ���ʵ����writeReplace��������ô�����л�ʱ���ȵ���writeReplace���������ø÷������صĶ����滻����ǰ���󣨸÷����᷵���滻��Ķ��󣩲�����д������
     */
    private Object writeReplace(){
        return new SerializationProxy(this);
    }

    /**
     * readObject�����������л�����������÷�����ObjectInputStream�������������������Ĭ�ϵ����л����̡�
     * ���Ȳ�������java.lang.Object��Ҳû����Serializable����������ôObjectInputStream���ʹ�����ǵ��أ�
     * ����ObjectOutputStreamʹ���˷�����Ѱ���Ƿ�������������������
     * ��ΪObjectOutputStreamͨ��getPrivateMethod����ȡ�÷�����
     * ������Щ�������ò�������Ϊpriate�����ڹ�ObjectOutputStream��ʹ�á�
     * ��writeObject����ͬ��
     */
    /**
     * �����������Ŀ�ʼ����ͨ�������defaultWriteObject()��defaultReadObject()��
     * ����������Ĭ�ϵ����л����̣�����д/�����е�non-transient�� non-static�ֶ�(�����ǲ���ȥ��serialVersionUID�ļ��)��
     * ͨ��˵��������������Ҫ�Լ�������ֶζ�Ӧ������Ϊtransient�������Ļ���defaultWriteObject/defaultReadObject�����רע�������ֶΣ�
     * ���������Ϊ��Щ�ض����ֶ�(transient)�������л���ʹ��������Ĭ�ϵķ���������ǿ�Ƶģ����Ǹ����˴�����Ӧ��ʱ���������ԡ�
     */
    /**
     * ������������������Ϊ�˷�ֹ������α�죬��ͼΥ�������Լ��������
     */
    private void readObject(ObjectInputStream stream)throws InvalidObjectException{
        throw new InvalidObjectException("Proxy required");
    }

    public static void main(String[] args) throws Exception{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(new People("li","si",22));

        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
        Object o =ois.readObject();
        System.out.println((People)o);
    }
}
