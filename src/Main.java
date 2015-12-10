import java.util.*;

/*
 * CSCI3501 Lab #13
 * 
 * Dynamic programming or something.
 * 
 */

/*
 * Finding Sub-Problems and finding overlaps
 * "thisIsVeryGoodString"
 *  01234567890123456789
 * Break indexes: 2, 8, 10
 * 
 * "th" "isIsVeryGoodString"
 * "thisIsVe" "ryGoodString"
 * "thisIsVery" "GoodString"
 * 
 * so the result of applying all of these breaks is:
 * 
 * "th" "isIsVe" "ry" "GoodString"
 * 
 * 
 * thisIsVeryGoodString 2 8 10
 * 
 * 
 * We can create all possible sequences of breaks, and then evaluate
 * them all for cost, keeping track of the best one thus far.
 */

public class Main {

	static String mainString;
	static int bestCost = Integer.MAX_VALUE;
	
	public static void main(String[] args) throws InvalidBreakIndexException{
		Scanner scan = new Scanner(System.in); 
		
		String input = null;
		String[] stringArr = null;
		Integer[] breaks;
		ArrayList<Integer[]> perms = new ArrayList<Integer[]>();
		Integer[] bestPath = null;
		

			
		while(scan.hasNextLine()){
			
			perms = new ArrayList<Integer[]>();
			bestPath = null;
			bestCost = Integer.MAX_VALUE; 
			
			// Gather input and prepare it for use.
			input = scan.nextLine(); 
			System.out.println("Raw Input: " + input);
			stringArr = input.split(" ");
			breaks = new Integer[stringArr.length - 1]; 
			System.out.println("String to break: " + stringArr[0] + " (" + stringArr[0].length() + ")");
			mainString = stringArr[0];
			System.out.print("Break Indexes: " );
			for(int i = 1; i < stringArr.length; i++){
				if(Integer.parseInt(stringArr[i]) > (stringArr[0].length() - 1)){
					throw new InvalidBreakIndexException("Inlvid Break Index: " + stringArr[i] + " for string of length: " + stringArr[0].length());
				}
				breaks[i-1] = Integer.parseInt(stringArr[i]);
				if(i < stringArr.length - 1){
					System.out.print(stringArr[i] + ", ");
				} else {
					System.out.println(stringArr[stringArr.length - 1]);
				}
			}
			
			
			// -----------------------------------
			
			// Call recursion to generate permutations
			recurse(Arrays.asList(breaks), 0, perms);
			System.out.println("Done with permutations...");
			
			// Print permutations
			//System.out.println("Permutations of breaks:");
			for(Integer[] arr : perms){
				//System.out.print("[");
				for(int i=0; i < arr.length; i++){
					if(i < arr.length - 1){
					//	System.out.print(arr[i] + ", ");
					} else {
					//	System.out.print(arr[i]);
					}
				}
				//System.out.print("]");
				
				int cost = evaluateCost(arr);
				
				if(cost < bestCost){
					bestPath = arr;
					bestCost = cost;
				}
				
				//System.out.println(" Cost: " + cost);
			}
			
			System.out.println("--------------------");
			System.out.print("Best Break Sequence: [");
			for(int i = 0; i < bestPath.length; i++){
				if(i < bestPath.length -1){
					System.out.print(bestPath[i] + ", ");
				} else {
					System.out.print(bestPath[i]);
				}
			}
			System.out.println("]");
			
			System.out.println("Lowest Possible Steps: " + bestCost); 
			
		}
	}
	
	public static int evaluateCost(Integer[] arr){
		int k = 0; 
		
		for(int i=0; i<arr.length; i++){
			int m=0;
			int n=mainString.length();
			
			for(int j=0; j<i; j++){
				if(arr[j] < arr[i] && arr[j] > m){
					m=arr[j];
				}
				if(arr[j]>arr[i] && arr[j]<n){
					n=arr[j];
				}
			}
		    if(k > bestCost){
				break;
			} else {
				k=k+n-m;
			}
		}
		
		
		return k;
	}
	
	public static void recurse(List<Integer> combo, int k, ArrayList<Integer[]> glob){
		for(int i=k; i<combo.size(); i++){
			Collections.swap(combo, i, k);
			recurse(combo, k+1, glob);
			Collections.swap(combo, k, i);
		}
		if(k == combo.size() - 1){
			glob.add((Integer[]) combo.toArray());
		}
	}
	
	private static class InvalidBreakIndexException extends Exception {
		public InvalidBreakIndexException(String message){
			System.err.println(message);
		}
	}
	
}
