/*******************************************************************************
 * Copyright (C) 2016-2017 Dennis Cosgrove, Finn Voichick
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
package slice.core;

import java.util.concurrent.ExecutionException;

import edu.wustl.cse231s.v5.api.CheckedIntConsumer;

/**
 * @author Finn Voichick
 * @author Dennis Cosgrove (http://www.cse.wustl.edu/~cosgroved/)
 */
public final class IndexedRange {
	private final int sliceIndexId;
	private final int minInclusive;
	private final int maxExclusive;

	/**
	 * Creates an instance useful for slicing up work, composed of an index id, an
	 * inclusive lower bound, and an exclusive upper bound.
	 * 
	 * @param sliceIndexId the id/index of the slice, negative values indicate
	 *                     invalidity
	 * @param minInclusive the inclusive lower bound
	 * @param maxExclusive the exclusive upper bound
	 */
	public IndexedRange(int sliceIndexId, int minInclusive, int maxExclusive) {
		this.sliceIndexId = sliceIndexId;
		this.minInclusive = minInclusive;
		this.maxExclusive = maxExclusive;
	}

	/**
	 * Gets the value of the slice index/ID.
	 * 
	 * @return the slice index
	 */
	public int getSliceIndexId() {
		return this.sliceIndexId;
	}

	/**
	 * Gets the inclusive lower bound of the slice.
	 * 
	 * @return inclusive lower bound of the slice
	 */
	public int getMinInclusive() {
		return this.minInclusive;
	}

	/**
	 * Gets the exclusive upper bound of the slice.
	 * 
	 * @return exclusive upper bound of the slice
	 */
	public int getMaxExclusive() {
		return this.maxExclusive;
	}

	/**
	 * Performs the given consumer for each index [min, maxExclusive).
	 * 
	 * @param body the computation to be performed for each index
	 * @throws InterruptedException if the computation was cancelled
	 * @throws ExecutionException   if the computation threw an exception
	 */
	public void forEachIndex(CheckedIntConsumer body) throws InterruptedException, ExecutionException {
		for (int i = minInclusive; i < maxExclusive; i++) {
			body.accept(i);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 17;
		result = prime * result + sliceIndexId;
		result = prime * result + maxExclusive;
		result = prime * result + minInclusive;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj instanceof IndexedRange) {
			IndexedRange other = (IndexedRange) obj;
			if (sliceIndexId == other.sliceIndexId) {
				if (maxExclusive == other.maxExclusive) {
					if (minInclusive == other.minInclusive) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[sliceIndexId=" + sliceIndexId + ", minInclusive=" + minInclusive
				+ ", maxExclusive=" + maxExclusive + "]";
	}

}
