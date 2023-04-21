import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Panya Bhinder
 * @version 1.0
 * @userid pbhinder3
 * @GTID 903690652
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class Sorting {

    /**
     * Implement selection sort.
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Cannot pass in null array or comparator.");
        }
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (comparator.compare(arr[j], arr[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            T temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
        
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Cannot pass in null array or comparator.");
        }
        boolean swaps = true;
        int start = 0;
        int end = arr.length - 1; 
        int swapped;
        while (swaps) {
            swaps = false;
            swapped = start;
            for (int i = start; i < end; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapped = i;
                    swaps = true;
                }
            }
            end = swapped;
            if (swaps) {
                swaps = false;
                swapped = end;
                for (int i = end; i > start; i--) {
                    if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                        T temp = arr[i];
                        arr[i] = arr[i - 1];
                        arr[i - 1] = temp;
                        swaps = true;
                        swapped = i;
                    }
                }
                start = swapped;
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Cannot pass in null array or comparator.");
        } else if (arr.length <= 1) {
            return;
        } else {
            int mid = arr.length / 2;
            T[] left = (T[]) new Object[mid];
            for (int i = 0; i < mid; i++) {
                left[i] = arr[i];
            }
            T[] right = (T[]) new Object[arr.length - mid];
            for (int i = mid; i < arr.length; i++) {
                right[i - mid] = arr[i];
            }
            mergeSort(left, comparator);
            mergeSort(right, comparator);

            int k = 0;
            int j = 0;
            while (k < left.length && j < right.length) {
                if (comparator.compare(left[k], right[j]) <= 0) {
                    arr[k + j] = left[k];
                    k++;
                } else {
                    arr[k + j] = right[j];
                    j++;
                }
            }

            while (k < left.length) {
                arr[k + j] = left[k];
                k++;
            }
            while (j < right.length) {
                arr[k + j] = right[j];
                j++;
            }
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("Cannot pass in null array or comparator.");
        }
        int start = 0;
        int end = arr.length;
        quickHelper(arr, comparator, rand, start, end);
    }

    /**
     * Recursive quick sort helper method.
     * @param <T> data type to sort.
     * @param arr array that should be sorted after helper method is called.
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     * @param start int that represents the starting index
     * @param end int that represents the ending index
     */
    private static <T> void quickHelper(T[] arr, Comparator<T> comparator,
                                        Random rand, int start, int end) {
        if (end - start <= 1) {
            return;
        } else {
            int pivot = rand.nextInt(end - start) + start;
            T pivotVal = arr[pivot];
            arr[pivot] = arr[start];
            arr[start] = pivotVal;
                                    
            int i = start + 1;
            int j = end - 1;
            while (i <= j) {
                while (i <= j && comparator.compare(arr[i], pivotVal) <= 0) {
                    i++;
                }
                while (i <= j && comparator.compare(arr[j], pivotVal) >= 0) {
                    j--;
                }
                if (i <= j) {
                    T temp = arr[i]; 
                    arr[i] = arr[j];
                    arr[j] = temp;
                    i++;
                    j--;
                }          
            }
            T temp = arr[start];
            arr[start] = arr[j];
            arr[j] = temp;
            quickHelper(arr, comparator, rand, start, j);
            quickHelper(arr, comparator, rand, j + 1, end); 
        }
                                            
        
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Cannot pass in null array.");
        }
        if (arr.length == 0) {
            return;
        }
        LinkedList<Integer>[] buckets = new LinkedList[19];
        for (int i = 0; i < 19; i++) {
            buckets[i] = new LinkedList<Integer>();
        }
        int max = 0;
        if (arr[0] > 0) {
            max = arr[0];
        } else {
            max = arr[0] - 1;
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > max || arr[i] - 1 > max) {
                max = arr[i];
            }
        }
        
        int maxdigits = 0;
        while (max != 0) {
            maxdigits++;
            max = max / 10;
        }
        int exponent = 1;
        for (int j = 0; j < maxdigits; j++) {
            for (int i = 0; i < arr.length; i++) {
                int digit = (arr[i] / exponent) % 10;
                buckets[digit + 9].add(arr[i]);
            }
            exponent *= 10;
            int index = 0;
            for (LinkedList<Integer> bucket : buckets) {
                while (!bucket.isEmpty()) {
                    arr[index++] = bucket.remove();
                }
            }
        }
    
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list in sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot pass in null array or comparator.");
        }
        PriorityQueue<Integer> queue = new PriorityQueue<Integer>(data);
        int[] sorted = new int[data.size()];
        for (int i = 0; i < data.size(); i++) {
            sorted[i] = queue.remove();
        }
        return sorted;
    }
}
