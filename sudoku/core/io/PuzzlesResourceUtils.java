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
package sudoku.core.io;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.io.IOUtils;

import sudoku.core.Square;

/**
 * @author Dennis Cosgrove (http://www.cse.wustl.edu/~cosgroved/)
 */
public class PuzzlesResourceUtils {
	public static Map<Square, Optional<Integer>> parseGivens(String givens) {
		Map<Square, Optional<Integer>> map = new EnumMap<>(Square.class);

		int row = 0;
		int column = 0;
		for (char c : givens.toCharArray()) {
			Optional<Integer> value;
			if (c == '.') {
				value = Optional.empty();
			} else {
				value = Optional.of(c - '0');
			}
			Square square = Square.valueOf(row, column);
			map.put(square, value);
			column++;
			if (column == 9) {
				row++;
				column = 0;
			}
		}
		return map;
	}

	public static List<String> readGivens(PuzzlesResource puzzlesResource) throws IOException {
		List<String> lines = IOUtils.readLines(puzzlesResource.getAsStream(), StandardCharsets.UTF_8);
		if (lines.get(0).length() == 81) {
			return lines;
		} else {
			List<String> result = new LinkedList<>();
			Iterator<String> iterator = lines.iterator();
			while (iterator.hasNext()) {
				StringBuilder sb = new StringBuilder();
				for (int row = 0; row < 9; row++) {
					assert iterator.hasNext();
					String line = iterator.next();
					sb.append(line.replaceAll("0", "."));
				}
				result.add(sb.toString());
				if (iterator.hasNext()) {
					String line = iterator.next();
					assert Objects.equals("========", line);
				}
			}
			return result;
		}
	}

	private static List<String> filterEasy(List<Integer> filterIndices) throws IOException {
//		List<String> hardestGivensList = PuzzlesResourceUtils.readGivens(PuzzlesResource.HARDEST);
//		results.add(new Object[] { hardestGivensList.get(4) });
		List<String> easyGivensList = readGivens(PuzzlesResource.EASY50);
		return IntStream.range(0, easyGivensList.size()).filter(i -> filterIndices.contains(i))
				.mapToObj(easyGivensList::get).collect(Collectors.toList());
	}

	public static List<String> filterSimpleGivens() throws IOException {
		return filterEasy(Arrays.asList(0, 4, 7, 11, 15, 16, 18, 19, 33, 35, 37, 39));
	}

	public static List<String> filterUnitAssignmentGivens() throws IOException {
		return filterEasy(Arrays.asList(0, 1, 2, 3, 4, 7, 8, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 25,
				26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 43, 44, 45));
	}

	public static List<String> filterNakedTwinsGivens() throws IOException {
		return filterEasy(Arrays.asList(9, 24, 42, 46));
	}

	public static List<String> filterUnitAssignmentAndNakedTwinsGivens() throws IOException {
		return filterEasy(Arrays.asList(0, 1, 2, 3, 4, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23,
				24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 42, 43, 44, 45, 46));
	}
}
