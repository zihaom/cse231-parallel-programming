/*******************************************************************************
 * Copyright (C) 2016-2018 Dennis Cosgrove
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
package count.lab;

import static edu.wustl.cse231s.v5.V5.async;
import static edu.wustl.cse231s.v5.V5.finish;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.IntPredicate;

import edu.wustl.cse231s.NotYetImplementedException;
import edu.wustl.cse231s.bioinformatics.Nucleobase;
import edu.wustl.cse231s.util.IntegerRange;
import midpoint.lab.MidpointUtils;
import slice.core.IndexedRange;
import slice.studio.Slices;

/**
 * @author Zihao Miao
 * @author Dennis Cosgrove (http://www.cse.wustl.edu/~cosgroved/)
 */
public class NucleobaseCounting {
	/**
	 * Should sequentially count all of the instances of a specific nucleobase that
	 * are within the given range. This method should involve no parallelism. The
	 * range of numbers to search includes all indices in the array that are equal
	 * to or greater than the min index and less than the max index.
	 * 
	 * @param chromosome
	 *            The chromosome to examine, represented as an array of bytes. Each
	 *            byte in the sequence represents one nucleobase--that is, cytosine
	 *            (C), guanine (G), adenine (A), thymine (T), or unknown (N). A byte
	 *            is used (rather than char, int, String, or enum, for example)
	 *            because a byte is a primitive data type that takes up very little
	 *            memory.
	 * @param targetNucleobase
	 *            The nucleobase to look for in the chromosome. The byte value
	 *            representing this nucleobase occurs some number of times in the
	 *            chromosome. In other words, if you call nucleobase.toByte(), it
	 *            will be equal to of the bytes in the chromosome array.
	 * @param min
	 *            the lowest array index in the range to search, inclusive
	 * @param maxExclusive
	 *            the highest array index in the range to search, exclusive
	 * @return the total number of times that the given nucleobase occurs in the
	 *         given chromosome
	 */
	public static int countRangeSequential(byte[] chromosome, Nucleobase targetNucleobase, int min, int maxExclusive) {
		int count =0;
		for(int i :new IntegerRange(min, maxExclusive)) {
			if (chromosome[i]==targetNucleobase.toByte()) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Sequentially counts all of the instances of a specific nucleobase. This
	 * method is very simple in that all it does is call
	 * {@link #countRangeSequential(byte[], Nucleobase, int, int)}. You do not need
	 * to change this method at all, but you will need to implement the
	 * {@code countRangeSequential} method.
	 * 
	 * @param chromosome
	 *            the chromosome to examine, as an array of bytes
	 * @param targetNucleobase
	 *            the nucleobase to look for in the chromosome
	 * @return The total number of times that the given nucleobase occurs in the
	 *         given chromosome.
	 */
	public static int countSequential(byte[] chromosome, Nucleobase targetNucleobase) {
		return countRangeSequential(chromosome,targetNucleobase,0,chromosome.length);
	}

	

	/**
	 * This method should asynchronously count all of the instances of a specific
	 * nucleobase, creating two tasks. The chromosome should be split into two
	 * halves, and the "upper" half should be counted at the same time
	 * (asynchronously) as the "lower" half. You will need async and finish for this
	 * method.
	 * 
	 * @param chromosome
	 *            The chromosome to examine, represented as an array of bytes.
	 * @param targetNucleobase
	 *            The nucleobase to look for in the chromosome.
	 * @return The total number of times that the given nucleobase occurs in the
	 *         given chromosome.
	 * @throws InterruptedException
	 *             if the computation was cancelled
	 * @throws ExecutionException
	 *             if the computation threw an exception
	 */
	public static int countParallelLowerUpperSplit(byte[] chromosome, Nucleobase targetNucleobase) 			
	throws InterruptedException, ExecutionException {
		int m = MidpointUtils.calculateMidpoint(0, chromosome.length);
		int[] LowerUpper=new int[2] ;
		finish(() -> {
			async(() -> {
				LowerUpper[0] = countRangeSequential(chromosome,targetNucleobase,0,m);
    		});
			LowerUpper[1] = countRangeSequential(chromosome,targetNucleobase,m,chromosome.length);
		});
		return LowerUpper[0]+LowerUpper[1];
	}

	/**
	 * This method should asynchronously count all of the instances of a specific
	 * nucleobase, creating the given number of tasks. In other words, you should
	 * spawn n tasks, each of which counts for 1/n of the chromosome. For example,
	 * if numTasks is 8, the chromosome should be divided into 8 pieces, and each of
	 * these pieces should be counted in a separate asynchronous task. You should
	 * enclose each of these tasks in an async block, so that each task can run in
	 * parallel. Note: if numTasks is 2, the behavior of this method will be the
	 * same as countParallelLowerUpperSplit.
	 * 
	 * @param chromosome
	 *            The chromosome to examine, represented as an array of bytes.
	 * @param targetNucleobase
	 *            The nucleobase to look for in the chromosome.
	 * @param numTasks
	 *            The number of tasks to create.
	 * @return The total number of times that the given nucleobase occurs in the
	 *         given chromosome.
	 * @throws InterruptedException
	 *             if the computation was cancelled
	 * @throws ExecutionException
	 *             if the computation threw an exception
	 */
	public static int countParallelNWaySplit(byte[] chromosome, Nucleobase targetNucleobase, int numTasks)
			throws InterruptedException, ExecutionException {
		List<IndexedRange> slices = Slices.createNSlices(chromosome, numTasks);
		int[] counts=new int[slices.size()] ;
        Iterator itr = slices.iterator(); 
		finish(() -> {
			int i=0;
			while (itr.hasNext()) {
				final int _i=i;
				IndexedRange slice = (IndexedRange)itr.next();
				async(() -> {
					counts[_i] = countRangeSequential(chromosome,targetNucleobase,slice.getMinInclusive(),slice.getMaxExclusive());
				});
				i++;
			}
		});
		int count =0;
		for (int j : counts) {
			count+= j;
		}
		return count;
	}

	/**
	 * This method should count all of the instances of a target nucleobase in a
	 * chromosome.
	 * 
	 * @see #countParallelDivideAndConquerKernel(byte[], Nucleobase, IntPredicate,
	 *      int, int)
	 * 
	 * @param chromosome
	 *            The chromosome to examine, represented as an array of bytes.
	 * @param targetNucleobase
	 *            The nucleobase to look for in the chromosome.
	 * @param isParallelRangeLengthPredicate
	 *            Predicate whose test method will return whether or not the current
	 *            range length is large enough to justify running in parallel.
	 * @return The total number of times that the given nucleobase occurs in the
	 *         given chromosome.
	 * @throws InterruptedException
	 *             if the computation was cancelled
	 * @throws ExecutionException
	 *             if the computation threw an exception
	 */
	public static int countParallelDivideAndConquer(byte[] chromosome, Nucleobase targetNucleobase,
			IntPredicate isParallelRangeLengthPredicate) throws InterruptedException, ExecutionException {
		return countParallelDivideAndConquerKernel(chromosome, targetNucleobase, isParallelRangeLengthPredicate, 0,
				chromosome.length);
	}

	/**
	 * This method should count all of the instances of a target nucleobase in a
	 * chromosome between [min,maxExclusive).
	 * 
	 * This method should perform each lower and upper half in parallel, creating
	 * tasks until the length between min and maxExclusive falls below threshold. At
	 * that point it should invoke countRangeSequential to do the rest of the work.
	 * 
	 * @see #countRangeSequential(byte[],Nucleobase,int,int)
	 * 
	 * @param chromosome
	 *            The chromosome to examine, represented as an array of bytes.
	 * @param targetNucleobase
	 *            The nucleobase to look for in the chromosome.
	 * @param isParallelRangeLengthPredicate
	 *            Predicate whose test method will return whether or not the current
	 *            range length is large enough to justify running in parallel.
	 * @param min
	 *            the lowest array index in the range to search, inclusive
	 * @param maxExclusive
	 *            the highest array index in the range to search, exclusive
	 * 
	 * @return The total number of times that the given nucleobase occurs in the
	 *         given chromosome.
	 * @throws InterruptedException
	 *             if the computation was cancelled
	 * @throws ExecutionException
	 *             if the computation threw an exception
	 */
	static int countParallelDivideAndConquerKernel(byte[] chromosome, Nucleobase targetNucleobase,
			IntPredicate isParallelRangeLengthPredicate, int min, int maxExclusive)
			throws InterruptedException, ExecutionException {
		if (isParallelRangeLengthPredicate.test(maxExclusive - min)) {
			int m = MidpointUtils.calculateMidpoint(min, maxExclusive);
			int[] LowerUpper= {0,0};
			finish(() -> {
				async(() -> {
					LowerUpper[0] = countParallelDivideAndConquerKernel(chromosome,targetNucleobase,isParallelRangeLengthPredicate,min,m);
	    		});
				LowerUpper[1] = countParallelDivideAndConquerKernel(chromosome,targetNucleobase,isParallelRangeLengthPredicate,m,maxExclusive);
			});
			return LowerUpper[0]+LowerUpper[1];
		}else {
			return countRangeSequential(chromosome,targetNucleobase,min,maxExclusive);
		}
		
	}
}
