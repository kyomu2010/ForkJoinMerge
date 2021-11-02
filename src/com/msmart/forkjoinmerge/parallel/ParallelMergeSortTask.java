package com.msmart.forkjoinmerge.parallel;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class ParallelMergeSortTask extends RecursiveAction {

	private static final long serialVersionUID = 1L;

	private int[] nums;

	public ParallelMergeSortTask(int[] nums) {
		this.nums = nums;
	}

	@Override
	protected void compute() {
		
		// if length of array is 10 or less use sequential merge sort algorithm
		if (nums.length <= 10) {
			mergeSort(nums);
			return;
		}

		int middleIndex = nums.length / 2;
		int[] leftSubarray = Arrays.copyOfRange(nums, 0, middleIndex);
		int[] rightSubarray = Arrays.copyOfRange(nums, middleIndex + 1, nums.length);
		
		// create left and right task threads
		ParallelMergeSortTask leftTask = new ParallelMergeSortTask(leftSubarray);
		ParallelMergeSortTask rightTask = new ParallelMergeSortTask(rightSubarray);
		
		// execute tasks in parallel
		invokeAll(leftTask, rightTask);

		merge(leftSubarray, rightSubarray, nums);
	}

	private void mergeSort(int[] nums) {

		// define base case for recursion
		if (nums.length <= 1)
			return;

		int middleIndex = nums.length / 2;

		int[] left = Arrays.copyOfRange(nums, 0, middleIndex);
		int[] right = Arrays.copyOfRange(nums, middleIndex, nums.length);

		mergeSort(left);
		mergeSort(right);

		merge(left, right, nums);
	}

	private void merge(int[] leftSubarray, int[] rightSubarray, int[] originalArray) {

		int i = 0;
		int j = 0;
		int k = 0;

		while (i < leftSubarray.length && j < rightSubarray.length) {
			if (leftSubarray[i] < rightSubarray[j]) {
				originalArray[k++] = leftSubarray[i++];
			} else {
				originalArray[k++] = rightSubarray[j++];
			}
		}

		while (i < leftSubarray.length) {
			originalArray[k++] = leftSubarray[i++];
		}

		while (j < rightSubarray.length) {
			originalArray[k++] = rightSubarray[j++];
		}
	}
}
