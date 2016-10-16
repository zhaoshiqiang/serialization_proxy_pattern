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


    private Object writeReplace(){
        return new SerializationProxy(this);
    }

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
