package com.googlecode.concurrenttrees.examples.usage;

import com.googlecode.concurrenttrees.radix.ConcurrentRadixTree;

public class ThreadRadix extends Thread {
	private int start, end;
	ConcurrentRadixTree<Integer> tree;
	
	public ThreadRadix(ConcurrentRadixTree<Integer> tree,int start, int end){
		this.start=start;
		this.end=end;
		this.tree=tree;
	}

    public void run() {
        for (int i = start; i < end; i++) {
        	tree.put(ConcurrentRadixTreeUsage.words.get(i), i);
		}
    }
}