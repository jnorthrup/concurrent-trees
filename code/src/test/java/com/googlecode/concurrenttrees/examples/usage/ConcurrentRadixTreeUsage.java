package com.googlecode.concurrenttrees.examples.usage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.googlecode.concurrenttrees.common.PrettyPrinter;
import com.googlecode.concurrenttrees.radix.ConcurrentRadixTree;

import com.googlecode.concurrenttrees.radix.node.concrete.DefaultCharArrayNodeFactory;
import com.googlecode.concurrenttrees.radix.node.util.PrettyPrintable;

public class ConcurrentRadixTreeUsage {
	public static final String FILENAME="datasets/wordsEN.txt";
	public static final int NUM_THREADS=2;
	public static final int NUM_WORDS=4;
	
	public static String words[];
	
	public static ConcurrentRadixTree<Integer> tree;
	public static void readFile(){
        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =  new FileReader(FILENAME);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int i=0;
            while((line = bufferedReader.readLine()) != null && i<NUM_WORDS) {
                words[i++]=line;
                
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                FILENAME + "'");  
            System.exit(1);
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + FILENAME + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
            System.exit(1);
        }
	}
	
	
	public static void main(String[] args) {
		words= new String[NUM_WORDS];
        tree = new ConcurrentRadixTree<Integer>(new DefaultCharArrayNodeFactory());
        
        ThreadRadix [] threads = new ThreadRadix[NUM_THREADS];
        
        readFile();
        int scope= NUM_WORDS/NUM_THREADS;
        int start=0, end=0;
        for (int i = 0; i < NUM_THREADS; i++) {
        	start=end;
        	end+=scope;
			threads[i]=new ThreadRadix(tree, start, end);
			threads[i].start();
		}
        
        for (int i = 0; i < NUM_THREADS; i++) {
        	try {
				threads[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
        
        PrettyPrinter.prettyPrint((PrettyPrintable) tree, System.out);
        
        System.out.println("Radix tree size: "+tree.size());

       
    }
	
	

}
