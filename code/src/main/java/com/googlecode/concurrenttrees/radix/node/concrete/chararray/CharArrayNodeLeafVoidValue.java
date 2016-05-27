/**
 * Copyright 2012-2013 Niall Gallagher
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.concurrenttrees.radix.node.concrete.chararray;

import com.googlecode.concurrenttrees.common.CharSequences;
import com.googlecode.concurrenttrees.radix.node.Node;
import com.googlecode.concurrenttrees.radix.node.concrete.voidvalue.VoidValue;
import com.googlecode.concurrenttrees.radix.node.util.Pair;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * Stores only incoming edge as a {@code char[]}.
 * Returns {@link VoidValue} for the value. Does <b>not</b> store any outgoing edges.
 *
 * @author Niall Gallagher
 */
public class CharArrayNodeLeafVoidValue implements Node {

    // Characters in the edge arriving at this node from a parent node.
    // Once assigned, we never modify this...
    private final char[] incomingEdgeCharArray;
    
    // this represents the partial work and a flag that indicates if the partial work is doing by its father (false) or 
    // by the node itself (true)
    // restriction: when modifying (insertion or deletion) old node and its father must have this partial work
    private AtomicMarkableReference<CharSequence> toDo;
    
    // flag to indicate if partial work corresponds to insertion or deletion
    private AtomicBoolean isToDoInsertion;

    public CharArrayNodeLeafVoidValue(CharSequence edgeCharSequence) {
        this.incomingEdgeCharArray = CharSequences.toCharArray(edgeCharSequence);
        this.toDo = new AtomicMarkableReference<CharSequence>(null, false);
        this.isToDoInsertion = new AtomicBoolean();
    }

    @Override
    public CharSequence getIncomingEdge() {
        return CharSequences.fromCharArray(incomingEdgeCharArray);
    }

    @Override
    public Character getIncomingEdgeFirstCharacter() {
        return incomingEdgeCharArray[0];
    }

    @Override
    public Object getValue() {
        return VoidValue.SINGLETON;
    }

    @Override
    public Node getOutgoingEdge(Character edgeFirstCharacter) {
        return null;
    }

    @Override
    public void updateOutgoingEdge(Node childNode) {
        throw new IllegalStateException("Cannot update the reference to the following child node for the edge starting with '" + childNode.getIncomingEdgeFirstCharacter() +"', no such edge already exists: " + childNode);
    }
    
    @Override
    public boolean updateOutgoingEdge(Node expectedNode, Node childNode) {
    	 throw new IllegalStateException("Cannot update the reference to the following child node for the edge starting with '" + childNode.getIncomingEdgeFirstCharacter() +"', no such edge already exists: " + childNode);
    }
    
    @Override
    public boolean updateOutgoingEdgeSentinel(Node expectedNode, Node childNode) {
    	 throw new IllegalStateException("Cannot update the reference to the following child node for the edge starting with '" + childNode.getIncomingEdgeFirstCharacter() +"', no such edge already exists: " + childNode);
    }

    @Override
    public List<Node> getOutgoingEdges() {
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Node{");
        sb.append("edge=").append(incomingEdgeCharArray);
        sb.append(", value=").append(VoidValue.SINGLETON);
        sb.append(", edges=[]");
        sb.append("}");
        return sb.toString();
    }
    
    @Override
    public void isToDoInsertion(){
    	this.isToDoInsertion.get();
    }
    
    @Override
    public void setToDoInsertion(boolean insert){
    	this.isToDoInsertion.set(insert);
    }
    
    @Override
	public void setWorkToDo(CharSequence newString, boolean newFlag){
    	this.toDo.set(newString, newFlag);
    }
    
   
    @Override
    public CharSequence getWorkToDo(boolean [] markHolder){
    	return this.toDo.get(markHolder);
    }
    
    @Override
    public boolean compareAndSetWorkToDo(CharSequence expectedWork, CharSequence newWork, boolean expectedMark, boolean newMark){
    	return this.toDo.compareAndSet(expectedWork, newWork, expectedMark, newMark);

    }
}
