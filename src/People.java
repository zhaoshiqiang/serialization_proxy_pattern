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

    public static void main(String[] args) throws Exception{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(new People("li","si",22));

        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
        Object o =ois.readObject();
        System.out.println((People)o);
    }
}
