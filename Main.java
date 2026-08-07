
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

        milo.sleep();
        rex.sleep();
    }
}

interface Animal {

    void speak();
}

class Cat extends Pet implements Animal {

    private int age;

    public Cat(String name, int age) {
        super(name);
        this.age = age;
    }

    public void speak() {
        System.out.println(getName() + " is " + age + " years old and says meow!");
    }

    public void haveBirthday() {
        age = age + 1;

        System.out.println(getName() + " is now " + age + " years old!");
    }

}

class Dog extends Pet implements Animal {

    public Dog(String name) {
        super(name);
    }

    public void speak() {
        System.out.println(getName() + " says woof!");
    }
}

class Pet {

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
}
