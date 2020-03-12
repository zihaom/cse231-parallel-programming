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
package filter.studio;

import java.util.LinkedList;
import java.util.List;

import edu.wustl.cse231s.NotYetImplementedException;

/**
 * @author Zihao Miao
 * @author Dennis Cosgrove (http://www.cse.wustl.edu/~cosgroved/)
 */
public class FilterApps {
	private static char[] LOWER_CASE_VOWELS = "aeiou".toCharArray();

	/**
	 * Creates and returns a list whose contents are the filtered result of the
	 * specified input words which contain each of the vowels 'a', 'e', 'i', 'o',
	 * and 'u'. The order in the input words must be preserved in the result.
	 * 
	 * For example, if the input is ["parallel", "equation", "concurrent",
	 * "tenacious"] then the returned list should be ["equation", "tenacious"].
	 * 
	 * @param words
	 *            the specified list of words
	 * @return the filtered list of specified words which contain each of the vowels
	 */
	public static List<String> filterWordsWhichContainAllVowels(List<String> words) {
		List<String> ret = new LinkedList<String>();
		for (String w : words) {
			int count = 0;
			for (char c : LOWER_CASE_VOWELS) {
				if (w.indexOf(c)!=-1) {
					count +=1;
				}
			}
			if (count == 5) {
				ret.add(w);
			}
		}
		
		return ret;
	}

	/**
	 * Creates and returns a list whose contents are the filtered result of the
	 * specified input integers which are even. The order in the input integers must
	 * be preserved in the result.
	 * 
	 * For example, if the input is [12, 71, 100, 231, 404] then the returned list
	 * should be [12, 100, 404].
	 * 
	 * @param xs
	 *            the specified list of integers
	 * @return the filtered list of specified integers which are even
	 */
	public static List<Integer> filterEvens(List<Integer> xs) {
		List<Integer> ret = new LinkedList<Integer>();
		for (int i : xs) {
			if (i % 2 == 0) {
				ret.add(i);
			}
		}
		return ret;
	}
}
