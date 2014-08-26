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

}
