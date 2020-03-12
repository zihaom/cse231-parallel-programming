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
package mapreduce.apps.friends.core;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/**
 * @author Dennis Cosgrove (http://www.cse.wustl.edu/~cosgroved/)
 */
public final class MutualFriendIds implements Iterable<AccountId> {
	public static MutualFriendIds createInitializedToUniverse(Collection<AccountId> universe) {
		return new MutualFriendIds(universe);
	}
	
	private MutualFriendIds(Collection<AccountId> universe) {
		this.universe = universe;
		this.intersection = null;
	}
	
	public void intersectWith(Set<AccountId> friendIds) {
		if( this.intersection != null ) {
			this.intersection.retainAll(friendIds);
		} else {
			this.intersection = new HashSet<>();
			this.intersection.addAll(friendIds);
		}
	}

	@Override
	public Iterator<AccountId> iterator() {
		if( this.intersection != null ) {
			return this.intersection.iterator();
		} else {
			return this.universe.iterator();
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof MutualFriendIds) {
			MutualFriendIds other = (MutualFriendIds) obj;
			if( Objects.equals(this.intersection, other.intersection) ) {
				return true;
			} else {
				//TODO: check intersection==null, universe combinations
				return false;
			}
		} else {
			return false;
		}
	}

	private final Collection<AccountId> universe;
	private Set<AccountId> intersection;
}
