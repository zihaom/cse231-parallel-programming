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
package count.output;

import static edu.wustl.cse231s.v5.V5.launchApp;

import java.util.concurrent.ExecutionException;

import count.lab.NucleobaseCounting;
import edu.wustl.cse231s.bioinformatics.Nucleobase;
import edu.wustl.cse231s.bioinformatics.io.resource.ChromosomeResource;

/**
 * @author Dennis Cosgrove (http://www.cse.wustl.edu/~cosgroved/)
 */
public class OutputNucleobaseCounting {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ChromosomeResource chromosomeResource = ChromosomeResource.MITOCHONDRION;
		byte[] chromosome = chromosomeResource.getData();
		Nucleobase targetNucleobase = Nucleobase.ADENINE;

		System.out.println(chromosomeResource);
		System.out.println();

		int countSeq = NucleobaseCounting.countSequential(chromosome, targetNucleobase);
		System.out.println("=== countSequential =================");
		System.out.println(targetNucleobase + ": " + countSeq);
		System.out.println();

		launchApp(() -> {
			int count2 = NucleobaseCounting.countParallelLowerUpperSplit(chromosome, targetNucleobase);
			System.out.println("=== countParallelLowerUpperSplit ====");
			System.out.println(targetNucleobase + ": " + count2);
			System.out.println();

			int count10 = NucleobaseCounting.countParallelNWaySplit(chromosome, targetNucleobase, 10);
			System.out.println("=== countParallelNWaySplit ==========");
			System.out.println(targetNucleobase + ": " + count10);
			System.out.println();

			int threshold = chromosome.length / 5;
			int countDnC = NucleobaseCounting.countParallelDivideAndConquer(chromosome, targetNucleobase,
					length -> length > threshold);
			System.out.println("=== countParallelDivideAndConquer ===");
			System.out.println(targetNucleobase + ": " + countDnC);
			System.out.println();
		});
	}
}
