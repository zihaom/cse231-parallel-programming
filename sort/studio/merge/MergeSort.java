/*******************************************************************************
 * Copyright (C) 2016-2017 Dennis Cosgrove
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package sort.studio.merge;

import static edu.wustl.cse231s.v5.V5.async;
import static edu.wustl.cse231s.v5.V5.finish;

import java.util.concurrent.ExecutionException;
import java.util.function.IntPredicate;

import edu.wustl.cse231s.NotYetImplementedException;
import sort.core.merge.Combiner;

/**
 * @author Zihao Miao
 * @author Aaron Handleman
 * @author Dennis Cosgrove (http://www.cse.wustl.edu/~cosgroved/)
 */
public class MergeSort {
	/**
	 * @param data          the array to sort
	 * @param lowInclusive  the lower bound of the range to sort (inclusive)
	 * @param highExclusive the upper bound of the range to sort (exclusive)
	 * @param combiner      used to merge the two sub-problem solutions into one
	 * @throws InterruptedException if the computation was cancelled
	 * @throws ExecutionException   if the computation threw an exception
	 */
	private static void sequentialMergeSortKernel(int[] data, int lowInclusive, int highExclusive, Combiner combiner)
			throws InterruptedException, ExecutionException {
		int m = lowInclusive+(highExclusive-lowInclusive)/2;
		if (lowInclusive < highExclusive-1) {
			sequentialMergeSortKernel(data,lowInclusive,m,combiner);
			sequentialMergeSortKernel(data,m,highExclusive,combiner);
			combiner.combineRange(data, lowInclusive, m, highExclusive);
		}

		
	}

	/**
	 * @param data     the array to sort
	 * @param combiner used to merge the two sub-problem solutions into one
	 * @throws InterruptedException if the computation was cancelled
	 * @throws ExecutionException   if the computation threw an exception
	 */
	public static void sequentialMergeSort(int[] data, Combiner combiner)
			throws InterruptedException, ExecutionException {
		sequentialMergeSortKernel(data,0,data.length,combiner);
	}

	/**
	 * Recursively and concurrently sorts the given array by breaking it down into
	 * arrays of size one, comparing the values, and merging them by order
	 * 
	 * @param data                the array to sort
	 * @param lowInclusive        the lower bound of the range to sort (inclusive)
	 * @param highExclusive       the upper bound of the range to sort (exclusive)
	 * @param isParallelPredicate Predicate whose test method will return whether or
	 *                            not the current range length is large enough to
	 *                            justify running in parallel.
	 * @param combiner            used to merge the two sub-problem solutions into
	 *                            one
	 * @throws InterruptedException if the computation was cancelled
	 * @throws ExecutionException   if the computation threw an exception
	 */
	private static void parallelMergeSortKernel(int[] data, int lowInclusive, int highExclusive,
			IntPredicate isParallelPredicate, Combiner combiner) throws InterruptedException, ExecutionException {
		if (isParallelPredicate.test(highExclusive-lowInclusive)) {
			int m = (lowInclusive +highExclusive)/2;
			finish(() -> {
				async(() -> {
					parallelMergeSortKernel(data,lowInclusive,m,isParallelPredicate,combiner);
	    		});
				parallelMergeSortKernel(data,m,highExclusive,isParallelPredicate,combiner);
			});
			combiner.combineRange(data, lowInclusive, m, highExclusive);
		}
		else
			sequentialMergeSortKernel(data,lowInclusive,highExclusive,combiner);
	}

	/**
	 * @param data                the array to sort
	 * @param isParallelPredicate Predicate whose test method will return whether or
	 *                            not the current range length is large enough to
	 *                            justify running in parallel.
	 * @param combiner            used to merge the two sub-problem solutions into
	 *                            one
	 * @throws InterruptedException if the computation was cancelled
	 * @throws ExecutionException   if the computation threw an exception
	 */
	public static void parallelMergeSort(int[] data, IntPredicate isParallelPredicate, Combiner combiner)
			throws InterruptedException, ExecutionException {
		parallelMergeSortKernel(data,0,data.length,isParallelPredicate,combiner);
	}
}
