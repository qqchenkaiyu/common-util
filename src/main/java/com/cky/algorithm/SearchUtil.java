package com.cky.algorithm;

/**
 * Created by Administrator on 2019/3/27.
 */
public class SearchUtil {
    public static<T extends Comparable<T>> int binarySearch(T[] x, T key) {
        int low=0;
        int high=x.length-1;
     return   binarySearch(x,low,high,key);
    }
    private static<T extends Comparable<T>> int binarySearch(T[] x, int low, int high, T key) {
        if(low <= high) {
            int mid = low + ((high -low) >> 1);
            if(key.compareTo(x[mid])== 0) {
                return mid;
            }
            else if(key.compareTo(x[mid])< 0) {
                return binarySearch(x,low, mid - 1, key);
            }
            else {
                return binarySearch(x,mid + 1, high, key);
            }
        }
        return -1;
    }
}
