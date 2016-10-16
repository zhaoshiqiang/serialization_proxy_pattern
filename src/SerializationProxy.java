import java.io.Serializable;

/**
 * Created by zhaoshiqiang on 2016/10/16.
 */
public class SerializationProxy implements Serializable{
    private final String firstname;
    private final String lastname;
    private int age;

    public SerializationProxy(People people) {
        this.firstname = people.getFirstname();
        this.lastname = people.getLastname();
        this.age = people.getAge();
    }

    private Object readResolve(){
        System.out.println("readResolve required!");
        return new People("zhang","san",11);
    }
    private static final long serialVersionUID = 22222222222222222L;
}
