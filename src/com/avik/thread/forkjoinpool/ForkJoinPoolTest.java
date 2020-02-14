package com.avik.thread.forkjoinpool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.RecursiveTask;
/*
class SearchFolder extends RecursiveTask<V> {

  public List<File> search(String path) {

    File file = new File(path);
    List<File> allFiles = new ArrayList<File>();
    File files[] = file.listFiles();
    if (files != null) {
      for (File nestedFile : files) {
        if (nestedFile != null) {
          if (nestedFile.isDirectory()) {
            allFiles.addAll(search(nestedFile.getAbsolutePath()));
          } else {
            allFiles.add(nestedFile);
          }
        }
      }
    }
    allFiles.add(file);
    return allFiles;
  }
}

public class ForkJoinPoolTest {

  public static void main(String a[]) {
    try (Scanner sc = new Scanner(System.in)) {
      String path = sc.nextLine();
      System.out.println("Path:" + path);
      List<File> allFiles = new SearchFolder().search(path);
      System.out.println("Folder:" + allFiles.stream().filter(file -> file.isDirectory()).count());
      System.out.println("Files:" + allFiles.stream().filter(file -> !file.isDirectory()).count());
    }
  }
}
*/
