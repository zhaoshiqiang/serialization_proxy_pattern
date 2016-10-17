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
    //为可序列化的类设计一个私有的静态嵌套类，把这个类重新写在一个类中也是可以的，
    //那样这个代理就可以被其他类访问了，因为这个代理只是针对People这个类，所以设计成私有静态嵌套类比较好
    private static class SerializationProxy implements Serializable {
        private static final long serialVersionUID = 22222222222222222L;
        //其属性要和外围类一致，这样可以精确的标识外围类实例的逻辑状态
        private final String firstname;
        private final String lastname;
        private int age;
        //这个构造器的参数类型就是外围类，这个构造器只从这个参数中复制数据
        //这个好处是不需要进行一致性检查或者保护性拷贝
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
     *这个方法的作用是在序列化时替换对象
     * Serializable还有两个标记接口方法可以实现序列化对象的替换，即writeReplace和readResolve；
     * writeReplace的原型：任意访问限定符 Object writeReplace() throws ObjectStreamException;
     * 如果实现了writeReplace方法后，那么在序列化时会先调用writeReplace方法，并用该方法返回的对象替换掉当前对象（该方法会返回替换后的对象）并将其写入流中
     */
    private Object writeReplace(){
        return new SerializationProxy(this);
    }

    /**
     * readObject处理对象的序列化。如果声明该方法，ObjectInputStream将调用这个方法而不是默认的序列化进程。
     * 它既不存在于java.lang.Object，也没有在Serializable中声明。那么ObjectInputStream如何使用它们的呢？
     * 这个嘛，ObjectOutputStream使用了反射来寻找是否声明了这两个方法。
     * 因为ObjectOutputStream通过getPrivateMethod来获取该方法，
     * 所以这些方法不得不被声明为priate以至于供ObjectOutputStream来使用。
     * （writeObject方法同理）
     */
    /**
     * 在两个方法的开始处，通常会调用defaultWriteObject()和defaultReadObject()。
     * 它们做的是默认的序列化进程，就像写/读所有的non-transient和 non-static字段(但他们不会去做serialVersionUID的检查)。
     * 通常说来，所有我们想要自己处理的字段都应该声明为transient。这样的话，defaultWriteObject/defaultReadObject便可以专注于其余字段，
     * 而我们则可为这些特定的字段(transient)定制序列化。使用那两个默认的方法并不是强制的，而是给予了处理复杂应用时更多的灵活性。
     */
    /**
     * 这里添加这个方法，是为了防止攻击者伪造，企图违反该类的约束条件。
     */
    private void readObject(ObjectInputStream stream)throws InvalidObjectException{
        throw new InvalidObjectException("Proxy required");
    }


}
