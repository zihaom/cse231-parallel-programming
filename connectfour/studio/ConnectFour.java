/*******************************************************************************
 * Copyright (C) 2016-2019 Dennis Cosgrove
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
package connectfour.studio;

import static edu.wustl.cse231s.v5.V5.forall;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.function.IntPredicate;
import java.util.function.ToDoubleFunction;

import connectfour.core.Board;
import edu.wustl.cse231s.NotYetImplementedException;

/**
 * @author Zihao Miao
 * @author Finn Voichick
 * @author Dennis Cosgrove (http://www.cse.wustl.edu/~cosgroved/)
 */
public class ConnectFour {
	

	/**
	 * @param board         the board state
	 * @param heuristic     the heuristic function to evaluate a board state
	 * @param searchAtDepth predicate to test whether to continue searching (true
	 *                      case) or to evaluate (false case)
	 * @param currentDepth  current depth of this search which is used to test the
	 *                      predicates.
	 * @return
	 * @throws InterruptedException if the computation was cancelled
	 * @throws ExecutionException   if the computation threw an exception
	 */
	private static double negamaxKernel(Board board, ToDoubleFunction<Board> heuristic, IntPredicate searchAtDepth,
			int currentDepth) throws InterruptedException, ExecutionException {
		throw new NotYetImplementedException();
	}

	/**
	 * Given a specified board state, heuristic function, and searchAtDepth
	 * predicate select which column is best for the next move.
	 * 
	 * @param board         the board state
	 * @param heuristic     the heuristic function to evaluate
	 * @param searchAtDepth predicate to test whether to continue searching (true
	 *                      case) or to evaluate (false case)
	 * @return the chosen best column index for the next move
	 * @throws InterruptedException if the computation was cancelled
	 * @throws ExecutionException   if the computation threw an exception
	 */
	public static Optional<Integer> selectNextColumn(Board board, ToDoubleFunction<Board> heuristic,
			IntPredicate searchAtDepth) throws InterruptedException, ExecutionException {
		throw new NotYetImplementedException();
	}
}
