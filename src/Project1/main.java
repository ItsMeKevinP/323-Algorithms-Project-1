package Project1;

import java.util.Random;
import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		//20777 lines in this one
		
				int count = 0;
				
				Scanner scan = new Scanner(System.in);
				System.out.println("Input a value for n");//request how many values to insert
				int n = scan.nextInt();
				//INSTANTIATE OUR DATA STRUCTURES
		        Map<Integer, String>map = new Map<>(); 
		        
		        AvlTree<Integer> tree = new AvlTree<>();
		        
		        BST<Integer, String> bst = null;;
		        
		        
		        Random rand = new Random();
		  
		        int random = rand.nextInt(n)+1; //choose number from 1 to 20777
		        int insertRandom = 0;
		        System.out.println("random int for choosing which line to choose for search: " + random);
		        //skip first line
		        
		        int SearchKey = 0;
		        for(int i=0; i< n ;i++) {
		        	insertRandom = rand.nextInt(999999)+1;// 1 to 1 million
		        	
		        	if(random == count) {
		        		SearchKey = insertRandom;
		        	}
		        		
		        	map.add(insertRandom, ""+Integer.hashCode(insertRandom));//INSERT INTO HASHMAP
		        	tree.insert(insertRandom);// insert into AVL TREE
		        	if(count ==0)
		        		bst = new BST<Integer, String>(insertRandom,""+Integer.hashCode(insertRandom));
		        	else bst.insert(insertRandom,""+Integer.hashCode(insertRandom));
		        	//INSERT INTO BINARY SEARCH TREE
		        	count++;
		        	//System.out.println(line.substring(0, line.indexOf(',')));
		        	//System.out.println(line.substring(line.indexOf(',')+1));
		        	
		        }
		        	
		        	
		 System.out.println("Starting Hash Table Search of: " + SearchKey);
		 long start = System.nanoTime();
		 //System.out.println(map.remove(SearchKey));
		 System.out.println("comparisons: "+map.get(SearchKey));
		 System.out.println("runtime nano: " + (System.nanoTime() - start));
		 System.out.println("------------------------------------------------------------------------\n\n");
		 
		 System.out.println("Starting AVL Tree Search of: " + SearchKey);
		 start = System.nanoTime();
		 System.out.println("comparisons: "+tree.contains(SearchKey));
		 System.out.println("runtime nano: " + (System.nanoTime() - start) );
		 System.out.println("------------------------------------------------------------------------\n\n");
		 
		 System.out.println("Starting BST Search of: " + SearchKey);
		 start = System.nanoTime();
		 //System.out.println(bst);
		 System.out.print("comparisons: ");
		 bst.find(SearchKey,0);
		 System.out.println("runtime nano: " + (System.nanoTime() - start));
		 System.out.println("------------------------------------------------------------------------\n\n");


	}

}
