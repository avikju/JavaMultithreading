package com.avik.thread.communication.test;

// Thread printing 1 continuously
class Print1Thread implements Runnable {

  // we will have lock on this object, this object actually prints int
  Printer printer;

  Print1Thread(Printer printer) {
    this.printer = printer;
  }

  @Override
  public void run() {
    // infinite loop
    while (true) {
      // in order to print thread should have lock on printer object
      // to enter the synchronized block
      synchronized (printer) {
        // thread checks if the last printed value is 1 or 2,
        // if so then it cannot print 1 again so it has to wait
        while (printer.lastPrintedValue == 2 || printer.lastPrintedValue == 1) {
          try {
            // thread releases the lock and goes to waiting state, so other thread gets the lock
            printer.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        // thread prints 1
        printer.print(1);
        // thread changes the lastprinted value
        printer.lastPrintedValue = 1;
        // thread notifies all other thread who were waiting on the same printer object
        printer.notifyAll();
      }
    }
  }
}

class Print2Thread implements Runnable {

  Printer printer;

  Print2Thread(Printer printer) {
    this.printer = printer;
  }

  @Override
  public void run() {
    while (true) {
      synchronized (printer) {
        while (printer.lastPrintedValue == 2
            || printer.lastPrintedValue == 3
            || printer.lastPrintedValue == 0) {
          try {
            printer.wait();
          } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
        printer.print(2);
        printer.lastPrintedValue = 2;
        printer.notifyAll();
      }
    }
  }
}

class Print3Thread implements Runnable {

  Printer printer;

  Print3Thread(Printer printer) {
    this.printer = printer;
  }

  @Override
  public void run() {
    while (true) {
      synchronized (printer) {
        while (printer.lastPrintedValue == 1
            || printer.lastPrintedValue == 3
            || printer.lastPrintedValue == 0) {
          try {
            printer.wait();
          } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
        printer.print(3);
        printer.lastPrintedValue = 3;
        printer.notifyAll();
      }
    }
  }
}

class Printer {
  int lastPrintedValue;

  public void print(int i) {
    System.out.println(i);
  }
}

public class ContinousThreading {

  public static void main(String[] args) {

    Printer printer = new Printer();
    Thread t1 = new Thread(new Print1Thread(printer));
    Thread t2 = new Thread(new Print2Thread(printer));
    Thread t3 = new Thread(new Print3Thread(printer));
    t1.start();
    t2.start();
    t3.start();
  }
}
