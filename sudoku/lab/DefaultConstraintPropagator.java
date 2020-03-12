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

package sudoku.lab;

import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;

import candidate.core.CandidateSet;
import edu.wustl.cse231s.NotYetImplementedException;
import sudoku.core.ConstraintPropagator;
import sudoku.core.Square;
import sudoku.core.SudokuUtils;
import sudoku.core.io.PuzzlesResourceUtils;

/**
 * @author Zihao Miao
 * @author Dennis Cosgrove (http://www.cse.wustl.edu/~cosgroved/)
 */
public class DefaultConstraintPropagator implements ConstraintPropagator {
	@Override
	public Map<Square, SortedSet<Integer>> createCandidateSetsFromGivens(String givens) {
		Map<Square, SortedSet<Integer>> Canididatesets = new EnumMap<>(Square.class);
		Map<Square, Optional<Integer>> mapGiven = PuzzlesResourceUtils.parseGivens(givens);

		for (Square s : Square.values()) {
			Canididatesets.put(s, CandidateSet.createAllCandidates());
		}
		for (Square s : Square.values()) {
			if (mapGiven.get(s).isPresent()&&(!mapGiven.get(s).equals(0))) {
				assign(Canididatesets, s,mapGiven.get(s).get());
			}
		}
		
		return Canididatesets;
	}

	@Override
	public Map<Square, SortedSet<Integer>> createNextCandidateSets(Map<Square, SortedSet<Integer>> otherCandidateSets,
			Square square, int value) {
		Map<Square, SortedSet<Integer>> Canididatesets = SudokuUtils.deepCopyOf(otherCandidateSets);
		assign(Canididatesets, square, value);
		return Canididatesets;
	}

	/**
	 * @throws IllegalArgumentException if the given value is not an option for the
	 *                                  given square
	 */
	private void assign(Map<Square, SortedSet<Integer>> resultOptionSets, Square square, int value) {
//		if (!CandidateSet.copyOf(resultOptionSets.get(square)).contains(value)) {
		if (!resultOptionSets.get(square).contains(value)) {
			throw new IllegalArgumentException();
		}
		resultOptionSets.put(square, CandidateSet.createSingleCandidate(value));

		eliminate(resultOptionSets, square, value);	
	}

	private void eliminate(Map<Square, SortedSet<Integer>> resultOptionSets, Square square, int value) {
		for (Square s : square.getPeers()) {

			SortedSet<Integer> Canididates = resultOptionSets.get(s);
			if (Canididates.contains(value)) {
				if (Canididates.size() == 0) {
					return;
				}
				else if (Canididates.size() == 1) {
					Canididates.remove(value);
					return;
				}
				else if (Canididates.size() == 2){
					Canididates.remove(value);
					assign(resultOptionSets, s, Canididates.first());
				}
				else {
					Canididates.remove(value);
				}
			}
		}
	}
}
