
public class Main {

    static void makeItSpeak(Animal animal) {
        animal.speak();
    }

    public static void main(String[] args) {
        Cat milo = new Cat("Milo", 2);
        Cat luna = new Cat("Luna", 5);
        Dog rex = new Dog("Rex");

        milo.haveBirthday();
        milo.speak();
        luna.speak();

        makeItSpeak(milo);
        makeItSpeak(rex);
    }
}

interface Animal {

    void speak();
}

class Cat implements Animal {

    private String name;
    private int age;

    public Cat(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void speak() {
        System.out.println(name + " is " + age + " years old and says meow!");
    }

    public void haveBirthday() {
        age = age + 1;

        System.out.println(name + " is now " + age + " years old!");
    }

}

class Dog implements Animal {

    private String name;

    public Dog(String name) {
        this.name = name;
    }

    public void speak() {
        System.out.println(name + " says woof!");
    }
}
