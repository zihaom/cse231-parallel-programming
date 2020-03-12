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
package mapreduce.framework.lab.bottlenecked;

import static edu.wustl.cse231s.v5.V5.forall;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collector;

import javax.annotation.concurrent.Immutable;

import edu.wustl.cse231s.NotYetImplementedException;
import edu.wustl.cse231s.util.KeyValuePair;
import mapreduce.framework.core.MapReduceFramework;
import mapreduce.framework.core.Mapper;

/**
 * A MapReduce framework that separates mapping, accumulating, and finishing
 * into three separate stages. The {@link #mapAll(Object[])} method calls the
 * {@code Mapper} on everything in the given array in parallel. Then, the
 * {@link #accumulateAll(List[])} method sequentially groups the output of the
 * {@code mapAll} method. Finally, the {@link #finishAll(Map)} method finishes
 * off the reduction, going from a map with values of type A to a map with
 * values of type R.
 * 
 * @param <E> the type of element that is originally being input
 * @param <K> the key in the key-value pairs written by the mapper
 * @param <V> the value in the key-value pairs written by the mapper
 * @param <A> the mutable result container of the collector
 * @param <R> the final result of the collector
 * 
 * @author Zihao Miao
 * @author Finn Voichick
 * @author Dennis Cosgrove (http://www.cse.wustl.edu/~cosgroved/)
 */
@Immutable
public final class BottleneckedMapReduceFramework<E, K, V, A, R> implements MapReduceFramework<E, K, V, A, R> {
	/** Mapper used to transform input data to key-value pairs */
	private final Mapper<E, K, V> mapper;
	/**
	 * Collector used to create mutable containers, accumulate values into those
	 * containers, then finish containers to reduced values.
	 */
	private final Collector<V, A, R> collector;

	/**
	 * Constructs a BottleneckedMapReduceFramework using the given mapper and
	 * collector.
	 * 
	 * @param mapper    Mapper used to transform input data to key-value pairs
	 * @param collector Collector used to create mutable containers, accumulate
	 *                  values into those containers, then finish containers to
	 *                  reduced values.
	 */
	public BottleneckedMapReduceFramework(Mapper<E, K, V> mapper, Collector<V, A, R> collector) {
		this.mapper = mapper;
		this.collector = collector;
	}

	@Override
	public Mapper<E, K, V> getMapper() {
		return this.mapper;
	}

	@Override
	public Collector<V, A, R> getCollector() {
		return this.collector;
	}

	/**
	 * Should call the {@link Mapper#map(Object, java.util.function.BiConsumer)}
	 * method on everything in the input. The input array contains a items of type
	 * E. In the end, this method should return an array of lists. Each of these
	 * lists contains key-value pairs that are the result of the mapper's map
	 * method. Each item can be mapped in parallel.
	 * 
	 * @param input the original input array
	 * @return array of lists corresponding to the mapped output of the input array
	 * @throws InterruptedException if the computation was cancelled
	 * @throws ExecutionException   if the computation threw an exception
	 */
	private List<KeyValuePair<K, V>>[] mapAll(E[] input) throws InterruptedException, ExecutionException {
		throw new NotYetImplementedException();
	}

	/**
	 * Sequentially accumulate specified mapAllResults. Note: these are the results
	 * of the {@link #mapAll(Object[])} method. This method should build up a result
	 * Map<K,A> by sequentially go through the array of lists, looking at each
	 * key-value pair. For each key-value pair, if there is nothing in the result
	 * map associated with a key, a mutable container should be created using the
	 * {@link Collector#supplier()}. Whether the mutable container was associated
	 * before, or just now created, it should accumulate the value with the other
	 * values currently associated with the key in the Map, using the
	 * {@link Collector#accumulator()}.
	 * 
	 * @param mapAllResults the results of the mapAll method invocation
	 * @return a Map<K,A> which contains all of the keys in the key-value pairs in
	 *         mapAllResults, and each associated value is the accumulation of all
	 *         of the values paired that key in mapAllResults.
	 */
	private Map<K, A> accumulateAll(List<KeyValuePair<K, V>>[] mapAllResults) {
		throw new NotYetImplementedException();
	}

	/**
	 * Should reduce the mutable containers to their finished values using the
	 * {@link Collector#finisher()}. After a long sequential strand to convert the
	 * accumulateAllResult to an Entry<K,A> array, the reductions can all be
	 * performed in parallel. Once you have the finishedValues, you should build the
	 * result Map<K,R> with another long sequential strand.
	 * 
	 * @param accumulateAllResult the results from the accumulateAll method
	 * @return a Map<K,R> which contains all of the keys from accumulateAllResult
	 *         each associated with the finished reduction.
	 * @throws InterruptedException if the computation was cancelled
	 * @throws ExecutionException   if the computation threw an exception
	 */
	private Map<K, R> finishAll(Map<K, A> accumulateAllResult) throws InterruptedException, ExecutionException {
		Entry<K, A>[] entries = toEntriesArray(accumulateAllResult);
		R[] finishedValues = createFinishedValuesArray(entries.length);

		throw new NotYetImplementedException();
	}

	@SuppressWarnings("unchecked")
	private static <K, A> Entry<K, A>[] toEntriesArray(Map<K, A> accumulateAllResult) {
		return accumulateAllResult.entrySet().toArray(new Entry[accumulateAllResult.size()]);
	}

	@SuppressWarnings("unchecked")
	private static <R> R[] createFinishedValuesArray(int length) {
		// NOTE: ideally, this class would be created with R's class so we could use it
		// here. As it turns out, creating an Object[] should be fine for our purposes.
		Class<?> componentType = Object.class;
		return (R[]) Array.newInstance(componentType, length);
	}

	@Override
	public Map<K, R> mapReduceAll(E[] input) throws InterruptedException, ExecutionException {
		List<KeyValuePair<K, V>>[] mapAllResult = this.mapAll(input);
		Map<K, A> accumulateAllResult = this.accumulateAll(mapAllResult);
		return this.finishAll(accumulateAllResult);
	}
}
