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
package sudoku.lab;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.SortedSet;

import edu.wustl.cse231s.NotYetImplementedException;
import javax.annotation.concurrent.Immutable;
import sudoku.core.ConstraintPropagator;
import sudoku.core.ImmutableSudokuPuzzle;
import sudoku.core.Square;

/**
 * Your implementation of an immutable sudoku puzzle. As noted in the
 * {@code ImmutableSudokuPuzzle} documentation, the board in this class cannot
 * change after construction, but it can create new immutable boards with an
 * additional value.
 * 
 * @author Zihao Miao
 * @author Dennis Cosgrove (http://www.cse.wustl.edu/~cosgroved/)
 */
@Immutable
public final class DefaultImmutableSudokuPuzzle implements ImmutableSudokuPuzzle {
	private final ConstraintPropagator constraintPropagator;
	private final Map<Square, SortedSet<Integer>> candidateSets;

	/**
	 * Constructs a new puzzle from the provided givens (and any propagated
	 * constraints).
	 * 
	 * @param constraintPropagator the constraint propagator to apply as values are
	 *                             eliminated
	 * @param givens               the provided starting point for the puzzle
	 */
	public DefaultImmutableSudokuPuzzle(ConstraintPropagator constraintPropagator, String givens) {
		this.constraintPropagator = constraintPropagator;
		this.candidateSets = this.constraintPropagator.createCandidateSetsFromGivens(givens);
		}

	/**
	 * Constructs a new puzzle from the provided other puzzle one with a new square
	 * filled in. This puzzle will have values copied over from the given one, but
	 * an additional value will be assigned for the given square (and any propagated
	 * constraints).
	 * 
	 * @param other  the puzzle to copy values over from
	 * @param square the square (unfilled in other) to fill
	 * @param value  the value to put in square
	 */
	private DefaultImmutableSudokuPuzzle(DefaultImmutableSudokuPuzzle other, Square square, int value) {
		this.constraintPropagator = other.getConstraintPropagator();
		this.candidateSets = this.constraintPropagator.createNextCandidateSets(other.candidateSets, square, value);
	}


	public ConstraintPropagator getConstraintPropagator() {
		return constraintPropagator;
	}

	@Override
	public ImmutableSudokuPuzzle createNext(Square square, int value) {
		return new DefaultImmutableSudokuPuzzle(this, square, value);
	}

	/**
	 * @param square the location on this puzzle to examine
	 * @return the value of the single remaining option for a given square,
	 *         otherwise returns 0
	 */
	@Override
	public Optional<Integer> getValue(Square square) {
		if (this.getCandidates(square).size() == 1) {
			return Optional.of(this.getCandidates(square).first());
		}
		return Optional.empty();
	}

	/**
	 * @param square the location in the puzzle to examine
	 * @return the set of all options at that location
	 */
	@Override
	public SortedSet<Integer> getCandidates(Square square) {
		return this.candidateSets.get(square);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 9; column++) {
				Square square = Square.valueOf(row, column);
				SortedSet<Integer> optionSet = this.candidateSets.get(square);
				int size = optionSet.size();
				if (size == 1) {
					sb.append(optionSet.iterator().next());
				} else if (size == 0) {
					sb.append('X');
				} else {
					sb.append('.');
				}
			}
		}
		return sb.toString();
	}
}
