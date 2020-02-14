package com.avik.thread.concurrent.collections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;

public class Test {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter wr = new PrintWriter(System.out);
    long n = Long.parseLong(br.readLine().trim());

    long out_ = FindBigNum(n);
    wr.println(out_);
    wr.close();
    br.close();
  }

  static long FindBigNum(long n) {
    StringBuilder sb = new StringBuilder("");
    for (int i = 1; i <= n; i++) {
      sb.append(Integer.toBinaryString(i));
    }
    BigInteger bigInt = new BigInteger(sb.toString(), 2);
    BigInteger bigIntMod = BigInteger.valueOf((long) (Math.pow(10, 9) + 7));
    return bigInt.mod(bigIntMod).longValue();
  }
}
