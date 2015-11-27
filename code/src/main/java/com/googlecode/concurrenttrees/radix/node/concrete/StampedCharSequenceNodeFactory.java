package com.googlecode.concurrenttrees.radix.node.concrete;

import java.util.List;
import java.util.concurrent.atomic.AtomicStampedReference;

import com.googlecode.concurrenttrees.common.CharSequences;
import com.googlecode.concurrenttrees.radix.node.Node;
import com.googlecode.concurrenttrees.radix.node.StampedNodeFactory;
import com.googlecode.concurrenttrees.radix.node.concrete.charsequence.CharSequenceNodeDefault;
import com.googlecode.concurrenttrees.radix.node.concrete.charsequence.CharSequenceNodeLeafNullValue;
import com.googlecode.concurrenttrees.radix.node.concrete.charsequence.CharSequenceNodeLeafVoidValue;
import com.googlecode.concurrenttrees.radix.node.concrete.charsequence.CharSequenceNodeLeafWithValue;
import com.googlecode.concurrenttrees.radix.node.concrete.charsequence.CharSequenceNodeNonLeafNullValue;
import com.googlecode.concurrenttrees.radix.node.concrete.charsequence.CharSequenceNodeNonLeafVoidValue;
import com.googlecode.concurrenttrees.radix.node.concrete.voidvalue.VoidValue;
import com.googlecode.concurrenttrees.radix.node.util.NodeUtil;

/*
 * pending: create Stamped objects
 */

public class StampedCharSequenceNodeFactory implements StampedNodeFactory  {
	@Override
    public Node createNode(CharSequence edgeCharacters, Object value, List<Node> childNodes, boolean isRoot) {
		if (edgeCharacters == null) {
            throw new IllegalStateException("The edgeCharacters argument was null");
        }
        if (!isRoot && edgeCharacters.length() == 0) {
            throw new IllegalStateException("Invalid edge characters for non-root node: " + CharSequences.toString(edgeCharacters));
        }
        if (childNodes == null) {
            throw new IllegalStateException("The childNodes argument was null");
        }
        NodeUtil.ensureNoDuplicateEdges(childNodes);


        if (childNodes.isEmpty()) {
            // Leaf node...
            if (value instanceof VoidValue) {
                return new CharSequenceNodeLeafVoidValue(edgeCharacters);
            }
            else if (value != null) {
                return new CharSequenceNodeLeafWithValue(edgeCharacters, value);
            }
            else {
                return new CharSequenceNodeLeafNullValue(edgeCharacters);
            }
        }
        else {
            // Non-leaf node...
            if (value instanceof VoidValue) {
                return new CharSequenceNodeNonLeafVoidValue(edgeCharacters, childNodes);
            }
            else if (value == null) {
                return new CharSequenceNodeNonLeafNullValue(edgeCharacters, childNodes);
            }
            else {
                return new CharSequenceNodeDefault(edgeCharacters, value, childNodes);
            }
        }
    }

	@Override
	public AtomicStampedReference<Node> createStampedNode(CharSequence edgeCharacters, Object value, List<Node> childNodes,	boolean isRoot) {
		if (edgeCharacters == null) {
            throw new IllegalStateException("The edgeCharacters argument was null");
        }
        if (!isRoot && edgeCharacters.length() == 0) {
        	throw new IllegalStateException("Invalid edge characters for non-root node: " + CharSequences.toString(edgeCharacters));
        }
        if (childNodes == null) {
            throw new IllegalStateException("The childNodes argument was null");
        }
        NodeUtil.ensureNoDuplicateEdges(childNodes);


        if (childNodes.isEmpty()) {
            // Leaf node...
            if (value instanceof VoidValue) {
                return new AtomicStampedReference<Node>(new CharSequenceNodeLeafVoidValue(edgeCharacters), 0);
            }
            else if (value != null) {
            	return new AtomicStampedReference<Node>(new CharSequenceNodeLeafWithValue(edgeCharacters, value),0);
            }
            else {
            	return new AtomicStampedReference<Node>(new CharSequenceNodeLeafNullValue(edgeCharacters),0);
            }
        }
        else {
            // Non-leaf node...
            if (value instanceof VoidValue) {
            	return new AtomicStampedReference<Node>(new CharSequenceNodeNonLeafVoidValue(edgeCharacters, childNodes),0);
            }
            else if (value == null) {
            	return new AtomicStampedReference<Node>(new CharSequenceNodeNonLeafNullValue(edgeCharacters, childNodes),0);
            }
            else {
            	return new AtomicStampedReference<Node>(new CharSequenceNodeDefault(edgeCharacters, value, childNodes),0);
            }
        }
	}

	@Override
	public Node createNode(CharSequence incomingEdge, Object value,
			AtomicStampedReference<Node>[] finalEdges, boolean parent) {
		// TODO Auto-generated method stub
		return null;
		//pending
	}
}
