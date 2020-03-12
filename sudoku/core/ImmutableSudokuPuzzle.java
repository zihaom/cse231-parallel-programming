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
package sudoku.core;

import java.util.Optional;
import java.util.SortedSet;

/**
 * A sudoku board. This board encapsulates the state of the puzzle, and has
 * methods to get information about the board. Because the values in the board
 * cannot change, the only way to modify an <code>ImmutableSudokuPuzzle</code>
 * is to create a new one using the {@link #createNext(Square, int)} method.
 * 
 * @author Dennis Cosgrove (http://www.cse.wustl.edu/~cosgroved/)
 */
public interface ImmutableSudokuPuzzle {
	/**
	 * Creates a new puzzle from the provided other puzzle. The new
	 * {@code ImmutableSudokuPuzzle} will have values identical to this one, except
	 * that the given square will be filled in with the given value (and any
	 * propagated constraints).
	 * 
	 * @param square the (currently unfilled) square to fill.
	 * @param value  the value to put in square
	 * @return a new puzzle with the modified square
	 */
	ImmutableSudokuPuzzle createNext(Square square, int value);

	/**
	 * Gets the number written at a given square. If the square is filled, this
	 * method will return a number between 1 and 9 (inclusive), otherwise it will
	 * return empty.
	 * 
	 * @param square the location on this board to examine
	 * @return the number written at that location, or 0 if no number is written
	 *         there
	 */
	Optional<Integer> getValue(Square square);

	/**
	 * Gets the values possible at the given square, given that all other board
	 * locations are correct. In other words, this method will return the set of all
	 * values not written in any of this square's peers. If this square already has
	 * a value (the same value returned by {@link #getValue(Square)}), this method
	 * will return a set containing only that value.
	 * 
	 * @param square the location in the puzzle to examine
	 * @return the set of all options at that location
	 */
	SortedSet<Integer> getCandidates(Square square);
}
