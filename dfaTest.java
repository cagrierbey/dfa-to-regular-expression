import java.io.*;
import java.util.*;
	
public class dfaTest {
	
	public static void main (String [] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter a any character to start: ");
		String start = sc.nextLine();
		System.out.println("Enter 1 to display the DFA and read strings on the DFA\nEnter 2 to create and display a GNFA from the DFA\nEnter 3 to create a regular Expression from the GNFA\nEnter 0 to exit");
		DFA dfa = createDFAFromFile(start);
		
		while (true) {
			String input = sc.nextLine();
			if (input.equals("1")) {
				System.out.println(dfa.toString()+"\n");
				while (true) {
					System.out.print("Enter a string or -1 to exit: ");
					String inputString = sc.nextLine();
					if (inputString.equals("-1")) break;
					try {
						System.out.println(dfa.runDFA(inputString));
					}
					catch (ArrayIndexOutOfBoundsException e) {
						System.out.println("Your string had a symbol not in the alphabet. Please enter a new string.");
					}
					System.out.println();
				}
			}
			else if (input.equals("2")) {
				GNFA gnfa = new GNFA(dfa);
				System.out.println(gnfa.toString()+"\n");
			}
			else if (input.equals("3")) {
				GNFA gnfa = new GNFA(dfa);
				System.out.println(gnfa.createRegex());
				System.out.println();
			}
			else if (input.equals("0")) {
				break;
			}

			System.out.println("Enter 1 to display the DFA and read strings on the DFA\nEnter 2 to create and display a GNFA from the DFA\nEnter 3 to create a regular Expression from the GNFA\nEnter 0 to exit");
		}		
	}

	public static DFA createDFAFromFile(String start) {
		try {
			start = "C:\\Users\\Cagri ERBEY\\Desktop\\text.txt";
			Scanner sc = new Scanner(new File(start));			
			
			String[] startS = sc.nextLine().split("=");
			String startState = startS[1];			
			String[] acceptStates = sc.nextLine().substring(2).split(",");			
			String[] alphabet = sc.nextLine().substring(2).split(",");			
			String[] states = sc.nextLine().substring(2).split(",");			
			String[][] transitionFunction = new String[states.length][alphabet.length];
			String currentState;
			String symbol;
			String transitionState;
						
			int counter = 0;
			int numTransitions = alphabet.length*states.length;

			while (sc.hasNextLine() && counter < numTransitions) {
				String[] line = sc.nextLine().split("=");
								
				currentState = line[0].substring(0,2);
				symbol = line[0].substring(3, 4);
				transitionState = line[1];
				
				int posCurrentState = Arrays.asList(states).indexOf(currentState);
				int posSymbol = Arrays.asList(alphabet).indexOf(symbol);
				transitionFunction[posCurrentState][posSymbol] = transitionState;
			   				
				counter++;
			}

			
			

			DFA dfa = new DFA(startState, acceptStates , alphabet , states , transitionFunction );

			return dfa;
							
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found");			
			return null;
		}
		
	}

}
