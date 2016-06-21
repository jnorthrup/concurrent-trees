package com.googlecode.concurrenttrees.radix.node.util;

public class Operation {
	public String value;
	public boolean performingNode;
	public boolean isInsertion;
	
	public Operation(String value, boolean performingNode, boolean isInsertion){
		this.value = value;
		this.performingNode = performingNode;
		this.isInsertion = isInsertion;
	}
}
