package com.googlecode.concurrenttrees.radix.node.util;

import com.googlecode.concurrenttrees.radix.ConcurrentRadixTree.SearchResult;
import com.googlecode.concurrenttrees.radix.node.Node;



public class Operation {
	public SearchResult searchResult;
	public Object value;
	public CharSequence key;
	public boolean performingNode;
	public boolean isInsertion;
	public boolean overwrite;
	
	public Operation(SearchResult searchResult, CharSequence key, Object value, boolean performingNode, boolean isInsertion, boolean overwrite){
		this.searchResult = searchResult;
		this.key = key;
		this.value = value;
		this.performingNode = performingNode;
		this.isInsertion = isInsertion;
		this.overwrite = overwrite;
	}
}
