import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class HashTest {
    public int probingType;
    public int tableSize, load;
    private static double loadSize;
    
    public static void main(String[] args) {
    	try {
    		loadSize = Double.parseDouble(args[1]);
    		if (loadSize > 1 || loadSize < 0) {
    			usage();
    		}
    		if (args.length == 2 || Integer.parseInt(args[2]) == 0) {
    			if(Integer.parseInt(args[0]) == 1) {
    				randomDebug0();
    				System.exit(1);
    			} else if(Integer.parseInt(args[0]) == 2) {
    				currentDebug0();
    				System.exit(1);
    			}else if(Integer.parseInt(args[0]) == 3) {
    				fileTest0();
    				System.exit(1);
    			}
    		}
    		if (args.length == 3) {
    			int debug = Integer.parseInt(args[2]);
    			if(Integer.parseInt(args[0]) == 1) {
    				if(debug == 1) {
    					randomDebug1();
    					System.exit(1);
    				}
    			}
    			else if(Integer.parseInt(args[0]) == 2) {
    				if (debug == 1) {
    					currentDebug1();
        				System.exit(1);
    				}
    			}
    			else if(Integer.parseInt(args[0]) == 3) {
    				if (debug == 1) {
    					fileTest1();
        				System.exit(1);
    				}
    			}
    		}
    		if (Integer.parseInt(args[0]) > 0 || Integer.parseInt(args[0]) < 1) {
    			usage();
    		}else {
    			usage();
    		}
    	}catch(Exception e) {
    		usage();
    	}
    	
    }
    
    public static void randomDebug0(){
		HashTable<Integer> linear = new HashTable<Integer>(loadSize, 0);
		DoubleHash<Integer> trial = new DoubleHash<Integer>(loadSize,0);
		System.out.println("A good table size is: " + linear.getSize());
		System.out.println("Data Source Type: Random Number Generator\n");
		System.out.println("Using Linear Hashing...");
		while(linear.getLoad() < loadSize){
			Random rand = new Random();
			HashObject<Integer> hash1 = new HashObject<Integer>(Math.abs(rand.nextInt()));
			linear.linearSearch(hash1);
			trial.DoubleSearch(hash1);
		}
		System.out.println("Inserted: " + (linear.getNumItemsLinear() + linear.getDup1()) + " elements, of which " + linear.getDup1() + " were duplicates");
		System.out.println("Load Factor = "+loadSize +" Avg. Probes: " + linear.avgProbelinear()+ "\n");
		System.out.println("Using Double Hashing...");
		System.out.println("Inserted: " + (trial.getItems() + linear.getDup1()) + " elements, of which " + linear.getDup1() + " were duplicates");
		System.out.println("Load Factor = "+ loadSize +" Avg. Probes: " + trial.getAverage()+ "\n");
	}
    
    public static void randomDebug1() throws IOException{
		HashTable<Integer> linear = new HashTable<Integer>(loadSize, 0);
		DoubleHash<Integer> trial = new DoubleHash<Integer>(loadSize,0);
		while(linear.getLoad() < loadSize){
			Random rand = new Random();
			HashObject<Integer> hash = new HashObject<Integer>(Math.abs(rand.nextInt()));
			linear.linearSearch(hash);
			trial.DoubleSearch(hash);
		}
	
		System.out.println("Linear Dump Created");
		linear.viewTable();
		System.out.println("Double Dump Created");
		trial.viewTable();
    }
    
    public static void currentDebug0(){
		HashTable<Long> linear = new HashTable<Long>(loadSize, 0);
		DoubleHash<Long> trial = new DoubleHash<Long>(loadSize,0);
		System.out.println("A good table size is: " + linear.getSize());
		System.out.println("Data Source Type: System Time\n");
		System.out.println("Using Linear Hashing...");
		while(linear.getLoad() < loadSize){
		HashObject<Long> hash = new HashObject<Long>(System.currentTimeMillis());
		linear.linearSearch(hash);
		trial.DoubleSearch(hash);
		}
		System.out.println("Inserted: " + (linear.getNumItemsLinear() + linear.getDup1()) + " elements, of which " + linear.getDup1() + " were duplicates");
		System.out.println("Load Factor = "+loadSize +" Avg. Probes: " + linear.avgProbelinear()+ "\n");
		System.out.println("Using Double Hashing...");
		System.out.println("Inserted: " + (trial.getItems() + linear.getDup1()) + " elements, of which " + linear.getDup1() + " were duplicates");
		System.out.println("Load Factor = "+ loadSize +" Avg. Probes: " + trial.getAverage()+ "\n");
	}
    
    public static void currentDebug1() throws IOException{
		HashTable<Long> linear = new HashTable<Long>(loadSize, 0);
		DoubleHash<Long> trial = new DoubleHash<Long>(loadSize,0);
		while(linear.getLoad() < loadSize){
		HashObject<Long> hash = new HashObject<Long>(System.currentTimeMillis());
		linear.linearSearch(hash);
		trial.DoubleSearch(hash);
		}
		
		System.out.println("Linear Dump Created");
		linear.viewTable();
		System.out.println("Double Dump Created");
		trial.viewTable();
	}
    
    public static void fileTest0() throws FileNotFoundException{
		HashTable<String> linear = new HashTable<String>(loadSize, 0);
		DoubleHash<String> trial = new DoubleHash<String>(loadSize,0);
		System.out.println("A good table size is: " + linear.getSize());
		System.out.println("Data Source Type: word-list\n");
		System.out.println("Using Linear Hashing...");
		File file = new File("word-list");
		Scanner scan = new Scanner(file);
		while(linear.getLoad() < loadSize  && scan.hasNextLine()){
		HashObject<String> hash = new HashObject<String>(scan.nextLine());
		linear.linearSearch(hash);
		trial.DoubleSearch(hash);
		}
//		System.out.println("Inserted: " + (linear.getNumItemsLinear() + linear.getDup1()) + " elements, of which " + linear.getDup1() + " were duplicates");
		System.out.println("Inserted: " + linear.getNumItemsLinear() + " elements, of which " + linear.getDup1() + " were duplicates");
//		System.out.println("Load Factor = "+ loadSize +" Avg. Probes: " + linear.avgProbelinear()+ "\n");
		System.out.println("Load Factor = " + loadSize + " Avg. Probes: " + (double)linear.numProbe1()/linear.getHashRequest()+ "\n");
		System.out.println("Using Double Hashing...");
		System.out.println("Inserted: " + (trial.getItems() + linear.getDup1()) + " elements, of which " + linear.getDup1() + " were duplicates");
		System.out.println("Load Factor = "+ loadSize +" Avg. Probes: " + trial.getAverage()+ "\n");

        scan.close();
    }
    
    public static void fileTest1() throws IOException{
		HashTable<String> linear = new HashTable<String>(loadSize, 0);
		DoubleHash<String> trial = new DoubleHash<String>(loadSize,0);
		File file = new File("word-list");
		Scanner scan = new Scanner(file);
		while(linear.getLoad() < loadSize  && scan.hasNextLine()){
		HashObject<String> hash = new HashObject<String>(scan.nextLine());
		linear.linearSearch(hash);
		trial.DoubleSearch(hash);
		}

		System.out.println("Linear Dump Created");
		linear.viewTable();
		System.out.println("Double Dump Created");
		trial.viewTable();
        scan.close();
    }
    
    public static void usage(){
		System.err.println("Java HashTest <input type> <load factor> [<debug level>]");
	}
     
    
	public boolean testPrime(int testIt){
		Random rand = new Random();
		int number = 0;
		int base = Math.abs(rand.nextInt(500));
		String binary = Integer.toBinaryString(testIt-1);
		while(base < testIt){
			number = base;
			for(int i = 1; i < binary.length(); i++){
				if (binary.charAt(i) == '0'){
					number = (int) ((Math.pow(number, 2))% testIt);
				}else if (binary.charAt(i) == '1'){
					number = (int) ((((Math.pow(number,2))% testIt) * base) % testIt);
				} 
			}
			if (number != 1){
				return false;
			}
			base ++;
		}
		return true;
	}
	public int getPrimes(int min, int max){
		int prime2 = 0;
		while(prime2 == 0){
			for (int i = min; i <= max; i++){
				if (testPrime(i) == true){
					if(testPrime(i+2) == true){
						prime2 = i + 2;
						return prime2;
					}
				}
			}
		}
		return 0;
	}

}


