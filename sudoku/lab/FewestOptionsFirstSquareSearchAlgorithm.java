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
package sudoku.lab;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.concurrent.Immutable;

import edu.wustl.cse231s.NotYetImplementedException;
import sudoku.core.ImmutableSudokuPuzzle;
import sudoku.core.Square;
import sudoku.core.SquareSearchAlgorithm;

/**
 * @author Zihao Miao
 * @author Dennis Cosgrove (http://www.cse.wustl.edu/~cosgroved/)
 */
@Immutable
public class FewestOptionsFirstSquareSearchAlgorithm implements SquareSearchAlgorithm {
	/**
	 * A square search algorithm that prefers squares with fewer options. In other
	 * words, this algorithm should always pick the empty square that is the most
	 * constrained by its peers. This algorithm should return a square with no
	 * options if it exists (meaning that the puzzle is impossible to solve), or it
	 * should return null, if the puzzle is already completely solved.
	 */
	@Override
	public Optional<Square> selectNextUnfilledSquare(ImmutableSudokuPuzzle puzzle) {
		Square smin = null;
		int count = 9;
		for (Square s : Square.values()) {
			if (puzzle.getCandidates(s).isEmpty()) {
				return Optional.of(s);
			}
			if (puzzle.getValue(s).isEmpty()) {
				int ncandidates = puzzle.getCandidates(s).size();
				if (ncandidates < count) {
					smin = s;
					count = ncandidates;
				}
			}
		}
		if (null == smin) {
			return Optional.empty();
		}
		return Optional.of(smin);	
	}
}
