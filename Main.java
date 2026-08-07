
public class Main {

    static void makeItSpeak(Animal animal) {
        animal.speak();
    }

    public static void main(String[] args) {
        Toy ball = new Toy("a ball");
        Toy mouse = new Toy("a toy mouse");

        Cat milo = new Cat("Milo", 2, ball);
        Cat luna = new Cat("Luna", 5, mouse);
        Dog rex = new Dog("Rex");

        milo.haveBirthday();
        milo.speak();
        luna.speak();

        makeItSpeak(milo);
        makeItSpeak(rex);

        milo.sleep();
        rex.sleep();

        milo.play();
        luna.play();
    }
}

interface Animal {

    void speak();
}

class Cat extends Pet {

    private int age;
    private Toy toy;

    public Cat(String name, int age, Toy toy) {
        super(name);
        this.age = age;
        this.toy = toy;
    }

    @Override
    public void speak() {
        System.out.println(getName() + " is " + age + " years old and says meow!");
    }

    public void haveBirthday() {
        age = age + 1;

        System.out.println(getName() + " is now " + age + " years old!");
    }

    public void play() {
        toy.useBy(getName());
    }

}

class Dog extends Pet {

    public Dog(String name) {
        super(name);
    }

    @Override
    public void speak() {
        System.out.println(getName() + " says woof!");
    }
}

abstract class Pet implements Animal {

    private String name;

    public Pet(String name) {
        this.name = name;
    }

    protected String getName() {
        return name;
    }

    public void sleep() {
        System.out.println(name + " is sleeping.");
    }

    public abstract void speak();
}

class Toy {

    private String name;

    public Toy(String name) {
        this.name = name;
    }

    public void useBy(String petName) {
        System.out.println(petName + " plays with " + name + "!");
    }
}
