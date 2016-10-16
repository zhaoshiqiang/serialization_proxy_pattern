import java.io.Serializable;

/**
 * Created by zhaoshiqiang on 2016/10/16.
 */
public class SerializationProxy implements Serializable {
    private final String firstname;
    private final String lastname;
    private int age;

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
    private static final long serialVersionUID = 22222222222222222L;
}
