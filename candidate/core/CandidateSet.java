/*******************************************************************************
 * Copyright (C) 2016-2020 Dennis Cosgrove
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
package candidate.core;

import java.util.AbstractSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.SortedSet;

/**
 * @author Dennis Cosgrove (http://www.cse.wustl.edu/~cosgroved/)
 */
public class CandidateSet extends AbstractSet<Integer> implements SortedSet<Integer> {
	private int bits;

	private CandidateSet(int bits) {
		this.bits = bits;
	}

	@Override
	public int size() {
		return Integer.bitCount(bits);
	}

	@Override
	public boolean remove(Object o) {
		Objects.requireNonNull(o);
		if (o instanceof Integer) {
			int candidate = (Integer) o;
			if (candidate <= 0 || candidate > 9) {
				throw new IllegalArgumentException(Integer.toString(candidate));
			}
			int bitsToShift = (candidate - 1);
			int bit = 0x1 << bitsToShift;
			if ((this.bits & bit) != 0) {
				int complement = ~bit;
				this.bits &= complement;
				return true;
			} else {
				return false;
			}
		} else {
			throw new IllegalArgumentException(Objects.toString(o));
		}
	}

	@Override
	public Iterator<Integer> iterator() {
		return new Iterator<Integer>() {
			private int mask = ALL_CANDIDATES_BITS;

			@Override
			public boolean hasNext() {
				return (bits & mask) != 0;
			}

			@Override
			public Integer next() {
				if (hasNext()) {
					int bitsToShift = 9 - Integer.bitCount(mask);
					while (bitsToShift < 9) {
						int optionBits = 0x1 << bitsToShift;
						int complement = ~optionBits;
						mask &= complement;
						if ((bits & optionBits) != 0) {
							return bitsToShift + 1;
						}
						bitsToShift++;
					}
					throw new Error();
				} else {
					throw new NoSuchElementException();
				}
			}

		};
	}

	@Override
	public Comparator<? super Integer> comparator() {
		return Comparator.naturalOrder();
	}

	@Override
	public SortedSet<Integer> subSet(Integer fromElement, Integer toElement) {
		int mask = 0;
		for (int option = fromElement; option < toElement; option++) {
			int bitsToShift = option - 1;
			mask |= (0x1) << bitsToShift;
		}
		return new CandidateSet(this.bits & mask);
	}

	@Override
	public SortedSet<Integer> headSet(Integer toElement) {
		return subSet(1, toElement);
	}

	@Override
	public SortedSet<Integer> tailSet(Integer fromElement) {
		return subSet(fromElement, 10);
	}

	@Override
	public Integer first() {
		if (this.bits == 0) {
			throw new NoSuchElementException();
		} else {
			return Integer.numberOfTrailingZeros(this.bits) + 1;
		}
	}

	@Override
	public Integer last() {
		if (this.bits == 0) {
			throw new NoSuchElementException();
		} else {
			int v = Integer.highestOneBit(this.bits);
			return Integer.numberOfTrailingZeros(v) + 1;
		}
	}

//	public CandidateSet copyAllExcept(int candidateToOmit) {
//		int bitsToShift = candidateToOmit - 1;
//		int singleBit = 0x1 << bitsToShift;
//		int complement = ~singleBit;
//		return new CandidateSet(this.bits & complement);
//	}

	private static final int ALL_CANDIDATES_BITS = 0x01FF;

	public static CandidateSet createAllCandidates() {
		return new CandidateSet(ALL_CANDIDATES_BITS);
	}

	public static CandidateSet createSingleCandidate(int candidate) {
		int bitsToShift = candidate - 1;
		return new CandidateSet(0x1 << bitsToShift);
	}

	public static CandidateSet copyOf(SortedSet<Integer> other) {
		if (other instanceof CandidateSet) {
			CandidateSet otherOptionSet = (CandidateSet) other;
			return new CandidateSet(otherOptionSet.bits);
		} else {
			int otherBits = pack(other);
			return new CandidateSet(otherBits);
		}
	}

	public static CandidateSet createCandidatesFromBits(int bits) {
		return new CandidateSet(bits);
	}

	public static int pack(Iterable<Integer> iterable) {
		int otherBits = 0;
		for (int value : iterable) {
			int bitsToShift = (value - 1);
			otherBits |= (0x1 << bitsToShift);
		}
		return otherBits;
	}

	// TODO: support ranges other than [1..9]
//	public static int pack(int count, IntPredicate predicate) {
//		int otherBits = 0;
//		for (int i = 0; i < count; ++i) {
//			if (predicate.test(i+1)) {
//				otherBits |= (0x1 << i);
//			}
//		}
//		return otherBits;
//	}
}
