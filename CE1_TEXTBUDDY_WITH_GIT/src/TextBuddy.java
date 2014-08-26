import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class TextBuddy {
	
	private static ArrayList<String> list;
	private static File newFile,temp;
	private static BufferedWriter buffW;
	private static FileWriter fileW;
	private static Scanner sc = new Scanner(System.in);
	
	public static void main(String[ ] args){
	
		list = new ArrayList<String>();
		makeFile(args[0]);
		loadToList();
		
		while(sc.hasNext()){
			String splitter[] = readInput();
			executeChoice(splitter,args[0]);
		}
		sc.close();
	}
	
	public static void makeFile(String inputName) throws Exception {
		newFile = new File(inputName+"txt");
		if(newFile.exists()){
			System.out.println("Welcome to TextBuddy,"+inputName+".txt is ready for use");
		}
		else{
			newFile.createNewFile();
			System.out.println("Welcome to TextBuddy,"+inputName+".txt is ready for use");
			
		}
		printCommand();
	}
	
	public static void loadToList() throws Exception{
	
		Scanner read = new Scanner(newFile);
		
		while(read.hasNextLine()){
		
			String line = read.nextLine();
			list.add(line);
			
		}
		read.close();	
	}
	
	public static String[] readInput(){
		
		String line = "";
		String input = sc.nextLine();
		String[] splitter = input.split(" ");
		splitter[0] = splitter[0].toLowerCase();
		
		if(splitter[0].equals("add")|| splitter[0].equals("delete")){
		
			for(int i=1; i<splitter.length;i++){
				line+=splitter[i]+" ";
			}
			splitter[1] = line;
		}
		return splitter;
	}

	public static void executeChoice(String[] input, String inputName) throws Exception{
		
		switch(input[0].toLowerCase()){
			case "add": addCall(input[1],inputName); break;
			case "delete": deleteCall(input[1],inputName); break;
			case "display": displayCall(inputName); break;
			case "clear": clearCall(inputName); break;
			case "exit": saveToFile(); System.exit(0);
		}
	}
	
	public static void addCall(String input, String inputName){
		
		list.add(input);
		System.out.println("added to "+inputName+".txt\""+input+"\"");
	}
	
	
	
	
}
