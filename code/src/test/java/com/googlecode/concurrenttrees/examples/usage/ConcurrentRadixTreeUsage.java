package com.googlecode.concurrenttrees.examples.usage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.googlecode.concurrenttrees.common.PrettyPrinter;
import com.googlecode.concurrenttrees.radix.ConcurrentRadixTree;
import com.googlecode.concurrenttrees.radix.node.concrete.DefaultCharArrayNodeFactory;
import com.googlecode.concurrenttrees.radix.node.util.PrettyPrintable;

public class ConcurrentRadixTreeUsage {
	public static String FILENAME="";
	public static int NUM_THREADS;
	public static int NUM_WORDS;
	
	public static ArrayList<String> words;
	
	public static ArrayList<String> auxWords;
	
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
            	//StringTokenizer stk = new StringTokenizer(line, " ");
                //while(stk.hasMoreTokens())
                 // auxWords.add(stk.nextToken().trim());
            	if(line.length()!=0){
            		words.add(line.trim());
            		i++;
            	}
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
		if(args.length<3){
			System.out.println("The arguments do not match. Use $.program NUM_THREADS NUM_KEYS filename");
			System.exit(1);
		}
		NUM_THREADS=Integer.parseInt(args[0]);
		NUM_WORDS=Integer.parseInt(args[1]);
		FILENAME = args[2];
		
		
		//auxWords= new ArrayList<String>();
		words= new ArrayList<String>(NUM_WORDS);
		readFile();
		
		/*
		int index, index1, index2, index3, index4, index5, index6, index7;
		for (int i = 0; i < auxWords.size() && i<NUM_WORDS; i++) {
			index = (int) (Math.random()*NUM_WORDS-1);
			index1 = (int) (Math.random()*NUM_WORDS-1);
			index2 = (int) (Math.random()*NUM_WORDS-1);
			index3 = (int) (Math.random()*NUM_WORDS-1);
			//index4 = (int) (Math.random()*NUM_WORDS-1);
			//index5 = (int) (Math.random()*NUM_WORDS-1);
			//index6 = (int) (Math.random()*NUM_WORDS-1);
			//index7 = (int) (Math.random()*NUM_WORDS-1);
			words.add(auxWords.get(index)+auxWords.get(index1)+auxWords.get(index2)+auxWords.get(index3));
		}
		
		toFile();
		
		System.exit(0);
		*/
		
        tree = new ConcurrentRadixTree<Integer>(new DefaultCharArrayNodeFactory());
        
        ThreadRadix [] threads = new ThreadRadix[NUM_THREADS];
        
        int scope= words.size()/NUM_THREADS;
        int start=0, end=0;
        for (int i = 0; i < NUM_THREADS; i++) {
        	start=end;
        	end+=scope;
			threads[i]=new ThreadRadix(tree, start, end);

		}
        
        long time0=System.currentTimeMillis();
        
        for (int i = 0; i < NUM_THREADS; i++) {
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
        
        long time1=System.currentTimeMillis();
        
        //PrettyPrinter.prettyPrint((PrettyPrintable) tree, System.out);
        
        System.out.println("Radix tree size: "+tree.size());
        System.out.println("Final time: "+(time1-time0));

       
    }


	private static void toFile() {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("datasets/randomWordsENx4.txt", "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < words.size(); i++) {
			writer.println(words.get(i));
		}

		writer.close();
		
	}
	
	

}
