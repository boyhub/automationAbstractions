package uk.co.compendiumdev.examples.domain.todos;

import com.github.javafaker.Faker;

public class RandomTodoGenerator {

    Faker faker;

    public RandomTodoGenerator(){

        faker = new Faker();
        // TODO: EXERCISE: seed the faker with a seeded Random and log the seed value
        //                 new Random(System.currentTimeMillis());

        // TODO: EXERCISE: create a constructor which allows seeding the RandomTodoGenerator
        //                 with a long during construction and use to create
        //                 a random but repeatable test data generation

    }

    public String getRandomTodoName() {
        String todo = faker.shakespeare().hamletQuote();
        if(todo.length()>50)
            todo = todo.substring(1, 50);

        return todo.trim();
    }

    /*
     TODO:
        EXERCISE:
            return a random TodoItem object
            also randomising the 'completed' state
            implement a createTodoItem method which adds the to do and sets the
            completed state correctly.
            Initially make this local to the @Test,
            and then refactor it so that it belongs to an object
      */
}
