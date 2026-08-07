class Cat {
    String name;

    Cat(String name) {
        this.name = name;
    }

    void meow(){
        System.out.println(name + " says meow!");
    }
}

public class Main {
    public static void main(String[] args) {
        Cat cat = new Cat("Milo");

        cat.meow();
    }
}
