package com.avik.thread.locks;

import java.util.concurrent.locks.ReentrantLock;

class Account {

  private String name;
  private float balance;
  private int accountNumber;
  private ReentrantLock lock = new ReentrantLock();

  public Account(String name, int balance, int accountNumber) {
    this.name = name;
    this.balance = balance;
    this.accountNumber = accountNumber;
  }

  public String getName() {
    return name;
  }

  public float getBalance() {
    return balance;
  }

  public void debit(float amount) {
    balance = balance - amount;
  }

  public void credit(float amount) {
    balance = balance + amount;
  }

  @Override
  public String toString() {
    return "Account [name="
        + name
        + ", balance="
        + balance
        + ", accountNumber="
        + accountNumber
        + "]";
  }

  public ReentrantLock getLock() {
    return lock;
  }

  public void setLock(ReentrantLock lock) {
    this.lock = lock;
  }
}
