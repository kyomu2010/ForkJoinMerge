package com.msmart.forkjoinmerge;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

import com.msmart.forkjoinmerge.parallel.ParallelMergeSortTask;
import com.msmart.forkjoinmerge.sequential.SequentialMergeSort;

public class App {

	public static void main(String[] args) {

		int[] nums = initializeNums();

		SequentialMergeSort sequentialMergeSort = new SequentialMergeSort();

		long start = System.currentTimeMillis();
		sequentialMergeSort.mergeSort(nums);
		System.out.println("Sequential merge sort algorithm: " + (System.currentTimeMillis() - start) + "ms");

		System.out.println();

		// initialize thread pool to match number of available processors
		ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
		ParallelMergeSortTask parallelMergeSortTask = new ParallelMergeSortTask(nums);

		start = System.currentTimeMillis();
		pool.invoke(parallelMergeSortTask);
		System.out.println("Parallel merge sort algorithm: " + (System.currentTimeMillis() - start) + "ms");

	}

	public static int[] initializeNums() {

		Random random = new Random();

		//int[] nums = new int[10000];
		int[] nums = new int[10000000];

		for (int i = 0; i < nums.length; i++) {
			nums[i] = random.nextInt(1000);
		}

		return nums;
	}

}
