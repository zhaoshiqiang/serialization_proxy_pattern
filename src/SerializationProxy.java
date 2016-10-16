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
     * 这个是序列化提供的一个特别的钩子——序列化类中一个私有的被实例化的方法readResolve（）；
     * 对于一个正在被反序列化的对象，如果它的类定义了一个readResolve方法，并且具备了正确的声明，
     * 那么在反序列化之后，新建对象上的readResolve()将被调用，并用这个方法返回的对象替换掉新建的对象
     * （其实这个方法被广泛用在单例模式中，通过它返回唯一的对象，防止通过序列化创建过多的对象）
     */
    /**
     * 该方法起到的作用：
     i. 调用该方法之前会先调用readObject反序列化得到对象；
     ii. 接着，如果该方法存在则会自动调用该方法；
     iii. 在该方法中可以正常通过this访问到刚才反序列化得到的对象的内容；
     iv. 然后可以根据这些内容进行一定处理返回一个对象；
     vi. 该对象将作为ObjectInputStream的readObject的返回值（即该对象将作为对象输入流的最终输入），从而可以彻底替换对象
     */
    private Object readResolve(){
        System.out.println("readResolve required!");
        return new People("zhang","san",11);
    }
    private static final long serialVersionUID = 22222222222222222L;
}
