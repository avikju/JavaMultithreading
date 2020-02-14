package com.avik.thread.locks;

@FunctionalInterface
interface Task {
  Runnable createTask(Account from, Account to, float amount);
}

public class AcountTransfer {

  public static void main(String[] args) {
    TransactionService transactionService = new TransactionService();
    Task task =
        (from, to, amount) -> {
          return () -> {
            sleepMe();
            try {
              transactionService.transfer(from, to, amount);
            } catch (InsufficientAmountException | InterruptedException | TimeoutException e) {
              Thread t = Thread.currentThread();
              t.getUncaughtExceptionHandler().uncaughtException(t, e);
            }
            System.out.println(Thread.currentThread().getName() + " says :: Transfer successful");
          };
        };
    Account from = new Account("Shamik Mitra", 20000, 1234);
    Account to = new Account("Samir Mitra", 10000, 12345);
    for (int i = 0; i < 4; i++) {
      new Thread(task.createTask(from, to, 200f)).start();
      new Thread(task.createTask(to, from, 100f)).start();
    }
  }

  private static void sleepMe() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
