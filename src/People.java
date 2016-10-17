import java.io.*;

public class People implements Serializable {
    private static final long serialVersionUID = 111111111111111111L;
    private final String firstname;
    private final String lastname;
    private int age;

    public People(String firstname, String lastname, int age) {

        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
    }
    //Ϊ�����л��������һ��˽�еľ�̬Ƕ���࣬�����������д��һ������Ҳ�ǿ��Եģ�
    //�����������Ϳ��Ա�����������ˣ���Ϊ�������ֻ�����People����࣬������Ƴ�˽�о�̬Ƕ����ȽϺ�
    private static class SerializationProxy implements Serializable {
        private static final long serialVersionUID = 22222222222222222L;
        //������Ҫ����Χ��һ�£��������Ծ�ȷ�ı�ʶ��Χ��ʵ�����߼�״̬
        private final String firstname;
        private final String lastname;
        private int age;
        //����������Ĳ������;�����Χ�࣬���������ֻ����������и�������
        //����ô��ǲ���Ҫ����һ���Լ����߱����Կ���
        public SerializationProxy(People people) {
            this.firstname = people.getFirstname();
            this.lastname = people.getLastname();
            this.age = people.getAge();
        }

        /**
         * ��������л��ṩ��һ���ر�Ĺ��ӡ������л�����һ��˽�еı�ʵ�����ķ���readResolve������
         * ����һ�����ڱ������л��Ķ�����������ඨ����һ��readResolve���������Ҿ߱�����ȷ��������
         * ��ô�ڷ����л�֮���½������ϵ�readResolve()�������ã���������������صĶ����滻���½��Ķ���
         * ����ʵ����������㷺���ڵ���ģʽ�У�ͨ��������Ψһ�Ķ��󣬷�ֹͨ�����л���������Ķ���
         */
        /**
         * �÷����𵽵����ã�
         i. ���ø÷���֮ǰ���ȵ���readObject�����л��õ�����
         ii. ���ţ�����÷�����������Զ����ø÷�����
         iii. �ڸ÷����п�������ͨ��this���ʵ��ղŷ����л��õ��Ķ�������ݣ�
         iv. Ȼ����Ը�����Щ���ݽ���һ��������һ������
         vi. �ö�����ΪObjectInputStream��readObject�ķ���ֵ�����ö�����Ϊ�������������������룩���Ӷ����Գ����滻����
         */
        private Object readResolve(){
            System.out.println("readResolve required!");
            return new People("zhang","san",11);
        }

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


}
