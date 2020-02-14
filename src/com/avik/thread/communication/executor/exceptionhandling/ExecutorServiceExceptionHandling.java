package com.avik.thread.communication.executor.exceptionhandling;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * In this example we are using ExecutorService.execute which does not swallow the exception, so we
 * get the exception
 *
 * @author Avik
 */
public class ExecutorServiceExceptionHandling {

  public static void main(String[] args) {

    // this will throw exception as it is not swallowed by execute method
    new ExecuteDemo().runTasksByExecute();
    // this will NOT throw exception as it is swallowed by submit method
    // the exception is thrown only when we call future.get
    new SubmitDemo().runTasksBySubmit();
    // so, the solution is to inherit ThreadPoolExecutor and override it's afterExecute method in
    // which we call future.get
    // now this will throw exception instead of swallowing it
    new SubmitDemoWithException().runTasksBySubmit();
  }
}

class ExecuteDemo {
  ExecutorService service =
      Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

  public void runTasksByExecute() {
    for (int i = 0; i < 2; i++) {
      service.execute(
          () -> {
            int a = 4, b = 0;
            System.out.println("a and b=" + a + ":" + b);
            System.out.println("a/b:" + (a / b));
            System.out.println(
                "Thread Name in Runnable after divide by zero:" + Thread.currentThread().getName());
          });
    }

    service.shutdown();
  }
}

class SubmitDemo {
  ExecutorService service =
      Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

  public void runTasksBySubmit() {
    for (int i = 0; i < 2; i++) {
      service.submit(
          () -> {
            int a = 4, b = 0;
            System.out.println("a and b=" + a + ":" + b);
            System.out.println("a/b:" + (a / b));
            System.out.println(
                "Thread Name in Runnable after divide by zero:" + Thread.currentThread().getName());
          });
    }

    service.shutdown();
  }
}

class SubmitDemoWithException {
  ExecutorService service = new ExtendedExecutor(Runtime.getRuntime().availableProcessors());

  public void runTasksBySubmit() {
    for (int i = 0; i < 2; i++) {
      service.submit(
          () -> {
            int a = 4, b = 0;
            System.out.println("a and b=" + a + ":" + b);
            System.out.println("a/b:" + (a / b));
            System.out.println(
                "Thread Name in Runnable after divide by zero:" + Thread.currentThread().getName());
          });
    }

    service.shutdown();
  }
}

class ExtendedExecutor extends ThreadPoolExecutor {

  public ExtendedExecutor(int corePoolSize) {
    super(corePoolSize, corePoolSize, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100));
  }

  @Override
  protected void afterExecute(Runnable r, Throwable t) {
    super.afterExecute(r, t);
    if (t == null && r instanceof Future<?>) {
      try {
        ((Future<?>) r).get();
      } catch (CancellationException ce) {
        t = ce;
      } catch (ExecutionException ee) {
        t = ee.getCause();
      } catch (InterruptedException ie) {
        Thread.currentThread().interrupt(); // ignore/reset
      }
    }
    if (t != null) System.out.println(t);
  }
}
