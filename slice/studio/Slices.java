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
package slice.studio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.wustl.cse231s.NotYetImplementedException;
import slice.core.IndexedRange;

/**
 * @author Zihao Miao
 * @author Finn Voichick
 * @author Dennis Cosgrove (http://www.cse.wustl.edu/~cosgroved/)
 */
public class Slices {
	/**
	 * Should create a list of {@link IndexedRange} objects of length numSlices.
	 * Each slice in the returned result should be allocated a balanced amount of
	 * the range. Any remaining data should be distributed one each to the front
	 * slices.
	 * 
	 * @param minInclusive minimum (inclusive) of the range to slice
	 * @param maxExclusive maximum (exclusive) of the range to slice
	 * @param numSlices    the number of slices to divide [minInclusive,
	 *                     maxExclusive) into
	 * @return the created list of slices
	 */
	public static List<IndexedRange> createNSlices(int minInclusive, int maxExclusive, int numSlices) {
		ArrayList<IndexedRange> ret = new ArrayList<IndexedRange>();
		int n = maxExclusive-minInclusive;
		int base = n/numSlices;
		int remainder = n%numSlices;
		for (int i = 0;i<remainder;i++) {
			ret.add(new IndexedRange(i,minInclusive+ i*(base+1), minInclusive+(i+1)*(base+1)));
		}
		int last=remainder*(1+base)+minInclusive;
		for (int i = 0;i<numSlices-remainder;i++) {
			ret.add(new IndexedRange(i+remainder, i*base+last, (i+1)*base+last));	
		}
		return ret;
	}

	public static <C> List<IndexedRange> createNSlices(C[] data, int numSlices) {
		return createNSlices(0, data.length, numSlices);
	}

	public static List<IndexedRange> createNSlices(byte[] data, int numSlices) {
		return createNSlices(0, data.length, numSlices);
	}

	public static List<IndexedRange> createNSlices(char[] data, int numSlices) {
		return createNSlices(0, data.length, numSlices);
	}

	public static List<IndexedRange> createNSlices(short[] data, int numSlices) {
		return createNSlices(0, data.length, numSlices);
	}

	public static List<IndexedRange> createNSlices(int[] data, int numSlices) {
		return createNSlices(0, data.length, numSlices);
	}

	public static List<IndexedRange> createNSlices(long[] data, int numSlices) {
		return createNSlices(0, data.length, numSlices);
	}

	public static List<IndexedRange> createNSlices(float[] data, int numSlices) {
		return createNSlices(0, data.length, numSlices);
	}

	public static List<IndexedRange> createNSlices(double[] data, int numSlices) {
		return createNSlices(0, data.length, numSlices);
	}
}
