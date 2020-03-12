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
package matrixmultiply.challenge;

import static edu.wustl.cse231s.v5.V5.async;
import static edu.wustl.cse231s.v5.V5.finish;

import java.util.concurrent.ExecutionException;
import java.util.function.IntPredicate;

import edu.wustl.cse231s.NotYetImplementedException;
import matrixmultiply.core.MatrixMultiplier;
import matrixmultiply.core.MatrixUtils;
import matrixmultiply.core.OffsetSubMatrix;

/**
 * @author Zihao Miao
 * @author Dennis Cosgrove (http://www.cse.wustl.edu/~cosgroved/)
 */
public class ParallelDivideAndConquerMatrixMultiplier implements MatrixMultiplier {
	private final IntPredicate isParallelPredicate;

	public ParallelDivideAndConquerMatrixMultiplier(IntPredicate isParallelPredicate) {
		this.isParallelPredicate = isParallelPredicate;
	}

	@Override
	public double[][] multiply(double[][] a, double[][] b) throws InterruptedException, ExecutionException {
		double[][] result = MatrixUtils.createMultiplyResultBufferInitializedToZeros(a, b);
		parallelKernel(new OffsetSubMatrix(result), new OffsetSubMatrix(a), new OffsetSubMatrix(b),
				isParallelPredicate);
		return result;
	}

	private static void parallelKernel(OffsetSubMatrix result, OffsetSubMatrix a, OffsetSubMatrix b,
			IntPredicate isParallelPredicate) throws InterruptedException, ExecutionException {
		if (isParallelPredicate.test(result.getSize())) {
			OffsetSubMatrix result11 = result.newSub11();
			OffsetSubMatrix result12 = result.newSub12();
			OffsetSubMatrix result21 = result.newSub21();
			OffsetSubMatrix result22 = result.newSub22();

			OffsetSubMatrix a11 = a.newSub11();
			OffsetSubMatrix a12 = a.newSub12();
			OffsetSubMatrix a21 = a.newSub21();
			OffsetSubMatrix a22 = a.newSub22();

			OffsetSubMatrix b11 = b.newSub11();
			OffsetSubMatrix b12 = b.newSub12();
			OffsetSubMatrix b21 = b.newSub21();
			OffsetSubMatrix b22 = b.newSub22();

			throw new NotYetImplementedException();
		} else {
			SequentialDivideAndConquerMatrixMultiplier.sequentialKernel(result, a, b);
		}
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
