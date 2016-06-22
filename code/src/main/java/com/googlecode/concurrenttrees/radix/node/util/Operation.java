package com.googlecode.concurrenttrees.radix.node.util;



public class Operation {
	public Classification kind;
	public Object value;
	public CharSequence key;
	public boolean performingNode;
	public boolean isInsertion;
	
	public Operation(Classification classification, CharSequence key, Object value, boolean performingNode, boolean isInsertion){
		this.kind = classification;
		this.key = key;
		this.value = value;
		this.performingNode = performingNode;
		this.isInsertion = isInsertion;
	}
}
