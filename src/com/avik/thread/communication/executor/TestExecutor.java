package com.avik.thread.communication.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * This class shows how to execute multiple tasks in parallel and collect their result Here I am
 * using invokeAll instead of submit for tasks execution because I need to wait for the completion
 * of the tasks. We use submit when we just submit the tasks and we don't want to wait for the
 * completion of the tasks
 *
 * @author Avik
 */
public class TestExecutor {

  public static void main(String... a) {
    ExecutorService executorService =
        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    DataLoader dataLoader = new DataLoader();

    // ids for which data to be fetched
    List<Integer> ids = new ArrayList<>();
    for (int i = 0; i < 50; i++) {
      ids.add(i);
    }

    try {
      getPersons(executorService.invokeAll(createTasks(ids, dataLoader)))
          .forEach(System.out::println);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    executorService.shutdownNow();
  }

  private static List<Callable<Person>> createTasks(List<Integer> ids, DataLoader dataLoader) {

    return ids.stream()
        .map(
            id ->
                new Callable<Person>() {
                  @Override
                  public Person call() throws Exception {
                    return dataLoader.getPersonById(id);
                  }
                })
        .collect(Collectors.toList());
  }

  private static List<Person> getPersons(List<Future<Person>> futures) {

    return futures
        .stream()
        .map(
            future -> {
              try {
                return future.get();
              } catch (InterruptedException e) {
                e.printStackTrace();
              } catch (ExecutionException e) {
                e.printStackTrace();
              }
              return null;
            })
        .collect(Collectors.toList());
  }
}

class DataLoader {

  public Person getPersonById(int id) {
    System.out.println("Going to fetch data for:" + id);
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) { // TODO Auto-generated catch block
      e.printStackTrace();
    }
    System.out.println("Fetching data for:" + id);
    return new Person(id, "name_" + id, id * 20);
  }
}

class Person {

  private int id;
  private String name;
  private int age;

  public Person(int id, String name, int age) {
    this.id = id;
    this.name = name;
    this.age = age;
  }

  @Override
  public String toString() {
    return "Person [id=" + id + ", name=" + name + ", age=" + age + "]";
  }
}
