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
package lambda.asyncfinish.demo;

import static edu.wustl.cse231s.sleep.SleepUtils.sleepRandom;
import static edu.wustl.cse231s.v5.V5.async;
import static edu.wustl.cse231s.v5.V5.finish;
import static edu.wustl.cse231s.v5.V5.launchApp;

import java.util.concurrent.ExecutionException;

import edu.wustl.cse231s.v5.api.CheckedRunnable;

class ApplesPrinter implements CheckedRunnable {
	@Override
	public void run() throws InterruptedException, ExecutionException {
		sleepRandom(1_000);
		System.out.println("-apples");
	}
}

class OrangesPrinter implements CheckedRunnable {
	@Override
	public void run() throws InterruptedException, ExecutionException {
		sleepRandom(1_000);
		System.out.println("--oranges");
	}
}

class BananasPrinter implements CheckedRunnable {
	@Override
	public void run() throws InterruptedException, ExecutionException {
		sleepRandom(1_000);
		System.out.println("---bananas");
	}
}

class MangoesPrinter implements CheckedRunnable {
	@Override
	public void run() throws InterruptedException, ExecutionException {
		sleepRandom(1_000);
		System.out.println("----mangoes");
	}
}

class FruitInAnyOrderPrinter implements CheckedRunnable {
	@Override
	public void run() throws InterruptedException, ExecutionException {
		async(new ApplesPrinter());
		async(new OrangesPrinter());
		async(new BananasPrinter());
		async(new MangoesPrinter());
	}
}

/**
 * @author Dennis Cosgrove (http://www.cse.wustl.edu/~cosgroved/)
 */
public class NamedClassesAsyncAndFinishExample {
	public static void main(final String[] args) throws InterruptedException, ExecutionException {
		launchApp(() -> {
			System.out.println("start");

			finish(new FruitInAnyOrderPrinter());

			System.out.println("stop");
		});
	}
}
