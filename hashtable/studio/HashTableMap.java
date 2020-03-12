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
package hashtable.studio;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import javax.annotation.concurrent.NotThreadSafe;

import org.apache.commons.collections4.iterators.LazyIteratorChain;

import edu.wustl.cse231s.NotYetImplementedException;
import edu.wustl.cse231s.util.KeyMutableValuePair;
import hash.studio.HashUtils;

/**
 * @author Dennis Cosgrove (http://www.cse.wustl.edu/~cosgroved/)
 */
@NotThreadSafe
public class HashTableMap<K, V> extends AbstractMap<K, V> {
	private final Collection<Entry<K, V>>[] buckets;

	@SuppressWarnings("unchecked")
	public HashTableMap(int capacity, Supplier<Collection<Entry<K, V>>> bucketSupplier) {
		buckets = new Collection[capacity];
		for(int i = 0; i<capacity;i++) {
			buckets[i]=bucketSupplier.get();
		}
	}

	/**
	 * @param key
	 * @return index of the bucket for the specified key
	 */
	private int getBucketIndex(Object key) {
		return HashUtils.toIndex(key,this.buckets.length);
	}

	/**
	 * @param key
	 * @return the bucket for the specified key
	 */
	private Collection<Entry<K, V>> getBucketFor(Object key) {
		return this.buckets[getBucketIndex(key)];
	}

	@Override
	public int size() {
		int size=0;
		for(Collection<Entry<K, V>> c : this.buckets) {
			size+= c.size();
		}
		return size;
	}

	@Override
	public V put(K key, V value) {
		for(Entry<K, V> e : this.buckets[getBucketIndex(key)]) {
			if(Objects.equals(e.getKey(), key)){
				V ret = e.getValue();
				e.setValue(value);
				return ret;
			}
		}
		this.buckets[getBucketIndex(key)].add(new KeyMutableValuePair<K,V>(key, value));
		return null;
	}

	@Override
	public V remove(Object key) {
		Iterator<Entry<K, V>>i =this.buckets[getBucketIndex(key)].iterator();
		while (i.hasNext()) {
			Entry<K, V> ret =(Entry<K, V>)i.next();
			if(Objects.equals(ret.getKey(), key)){
				i.remove();
				return ret.getValue();
			}
		}
		return null;
	
	}

	@Override
	public V get(Object key) {
		for(Entry<K, V> e : this.buckets[getBucketIndex(key)]) {
			if(Objects.equals(e.getKey(), key)){
				return e.getValue();
			}
		}
		return null;
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return new AbstractSet<Entry<K, V>>() {
			@Override
			public Iterator<Entry<K, V>> iterator() {
				return new LazyIteratorChain<Map.Entry<K, V>>() {
					@Override
					protected Iterator<? extends java.util.Map.Entry<K, V>> nextIterator(int count) {
						int index = count - 1;
						return index < buckets.length ? buckets[index].iterator() : null;
					}
				};
			}

			@Override
			public int size() {
				return HashTableMap.this.size();
			}
		};
	}

}
