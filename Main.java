
public class Main {

    public static void main(String[] args) {
        Cat milo = new Cat("Milo", 2);
        Cat luna = new Cat("Luna", 5);

        milo.haveBirthday();
        milo.meow();
        luna.meow();
    }
}

class Cat {

    private String name;
    private int age;

    public Cat(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void meow() {
        System.out.println(name + " is " + age + " years old and says meow!");
    }

    public void haveBirthday() {
        age = age + 1;

        System.out.println(name + " is now " + age + " years old!");
    }
}
