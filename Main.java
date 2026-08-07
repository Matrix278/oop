public class Main {
    public static void main(String[] args) {
        Cat milo = new Cat("Milo", 2);
        Cat luna = new Cat("Luna", 5);

        milo.meow();
        luna.meow();
    }
}


class Cat {
    String name;
    int age;

    Cat(String name, int age) {
        this.name = name;
        this.age = age;
    }

    void meow() {
        System.out.println(name + " is "+ age +" years old and says meow!");
    }
}
