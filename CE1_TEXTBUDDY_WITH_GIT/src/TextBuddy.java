/* Done By: Aaron Koh Group C05 (CS2103T)
 * Name of Program: TextBuddy
 * This program works with file manipulation
 * User inputs a file name. Program will check if file exists.
 * If file exists, we will not create a new file
 * If file does not exists, we will create the file with the provided name
 * This program allows us to perform add/delete/display/clear functions
 * Internally, all operations are performed on a array list data structure. 
 * Upon user's input of exit, contents of data structure will be written to a 
 * temporary file, which will then replace the original file. 

*/
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
	
	private static final int INDEX_ZERO = 0;
	private static final int INPUT_CONTENTS=1;
	
	private static final String MESSAGE_WELCOME_GREETING = "Welcome to TextBuddy. %1$s is ready for use";
	
	//Main function direct input reading/ choice execution to respective methods
	public static void main(String[ ] args) throws Exception{
	
		list = new ArrayList<String>();
		String fileReady = makeFile(args[INDEX_ZERO]);
		showToUser(fileReady);
		printCommand();
		loadToList();
		
		while(sc.hasNext()){
			String splitter[] = readInput();
			executeChoice(splitter,args[INDEX_ZERO]);
		}
		sc.close();
	}
	
	//This method accepts the input name for the file, and checks if the file exists. 
	//If it does,we simply work on that file, if it doesn't, we will create this file
	public static String makeFile(String inputName) throws Exception {
		newFile = new File(inputName);
		if(newFile.exists()){
		return String.format(MESSAGE_WELCOME_GREETING, inputName);
		}
		
		else{
			newFile.createNewFile();
			return String.format(MESSAGE_WELCOME_GREETING, inputName);
			
		}
	
	}
	
	//This method is called to load all contents of the file into an array list 
	//data structure. This is to cater for the case in which file already exists.
	public static void loadToList() throws Exception{
	
		Scanner read = new Scanner(newFile);
		
		while(read.hasNextLine()){
		
			String line = read.nextLine();
			list.add(line);
			
		}
		read.close();	
	}
	
	//This function is called to accept user's command. Here, we separate the "command" 
	//from the details
	public static String[] readInput(){
		
		String line = "";
		String input = sc.nextLine();
		String[] splitter = input.split(" ");
		splitter[INDEX_ZERO] = splitter[INDEX_ZERO].toLowerCase();
		
		if(splitter[0].equals("add")|| splitter[INDEX_ZERO].equals("delete")){
		
			for(int i=1; i<splitter.length;i++){
				line+=splitter[i]+" ";
			}
			splitter[INPUT_CONTENTS] = line;
		}
		return splitter;
	}

	//This method is provided with user's command, as well as details of the command. 
	//It functions as a router,routing the execution to the correct command method
	public static void executeChoice(String[] input, String inputName) throws Exception{
		
		switch(input[INDEX_ZERO].toLowerCase()){
			case "add": 
				addCall(input[INPUT_CONTENTS].trim(),inputName);
				printCommand();
				break;
				
			case "delete":
				deleteCall(input[INPUT_CONTENTS],inputName);
				printCommand();
				break;
				
			case "display":
				displayCall(inputName);
				printCommand();
				break;
			case "clear": 
				clearCall(inputName);
				printCommand();
				break;
				
			case "exit": saveToFile(); System.exit(0);
		}
	}
	
	
	//This function is called if user's command is to add. It appends user's input
	//to the array list
	public static void addCall(String input, String inputName) throws Exception{
		
		list.add(input);
		saveToFile();
		System.out.println("added to "+inputName+ ": \""+input+"\"");
	}
	
	
	//This function is called if user's command is to delete. It determines the line 
	//to be deleted,and deletes that from the array list
	public static void deleteCall(String input, String inputName) throws Exception{
		int serial =0;
		serial = Integer.valueOf(input.trim());
		String toClear = list.remove(serial-1).trim();
		saveToFile();
		System.out.println("deleted from "+inputName+" \""+toClear+"\"");
	}
	
	//This function is called if user's command is to display. It displays all contents
	//of the array list
	public static void displayCall(String inputName){
		if(list.size()==0){
			System.out.println(inputName+" is empty");
		}
		else{
			for(int i=0;i<list.size();i++){
				System.out.println((i+1)+". "+list.get(i));
			}
		}
	}
	
	//This function is called if user's command is to clear. It removes all contents 
	//from the array list 
	public static void clearCall(String inputName) throws Exception{
		if(list.size()!=0){
			list.clear();
			saveToFile();
		}
		
		fileW = new FileWriter(newFile);
		fileW.close();
		System.out.println("all content deleted from "+inputName);
	}
	
	//This function is called by the delete method. It does 3 mains tasks, it creates a
	//temporary file, copy all of the array list contents to this temporary file, then
	//replace the temporary file with the original
	public static void saveToFile() throws Exception{
		createTempFile();
		copyToTempFile();
		replaceFile();
	}
	
	//This function is called to create a temporary file
	public static void createTempFile(){
		temp = new File("temp.txt");
	}
	
	//This function is called to copy contents of array list into the temporary file
	public static void copyToTempFile() throws Exception{
		Scanner read = new Scanner(newFile);
		fileW = new FileWriter(temp);
		buffW = new BufferedWriter(fileW);
		
		for(int i=0;i<list.size();i++){
			buffW.write(list.get(i));
			buffW.newLine();buffW.flush();
		}
		read.close();
		buffW.close();
		fileW.close();
		
	}
	
	//This function is called to replace the temporary file with the original file
	public static void replaceFile(){
		if(newFile.delete()){
			temp.renameTo(newFile);
		}
	}
	
	//This function prints the word "command: "
	public static void printCommand(){
		System.out.print("command: ");
	}
	
	public static void showToUser(String text){
		System.out.println(text);
	}
	
}
