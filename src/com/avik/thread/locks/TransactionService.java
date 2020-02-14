package com.avik.thread.locks;

public class TransactionService {

  public boolean transfer(Account fromAccount, Account toAccount, float amount)
      throws InsufficientAmountException, InterruptedException, TimeoutException {

    // we are defining a stopTime
    long stopTime = System.nanoTime() + 5000;
    while (true) {
      if (fromAccount.getLock().tryLock()) {
        try {
          if (toAccount.getLock().tryLock()) {
            try {
              if (amount > fromAccount.getBalance()) {
                throw new InsufficientAmountException("Insufficient Balance");
              } else {
                fromAccount.debit(amount);
                toAccount.credit(amount);
              }
            } finally {
              toAccount.getLock().unlock();
            }
          }

        } finally {
          fromAccount.getLock().unlock();
        }
      }
      if (System.nanoTime() < stopTime) {
        throw new TimeoutException("Transaction timeout");
      }
      Thread.sleep(100);
    } // while
  }
}
