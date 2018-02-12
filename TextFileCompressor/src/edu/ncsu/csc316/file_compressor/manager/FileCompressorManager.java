/**
 * 
 */
package edu.ncsu.csc316.file_compressor.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
//import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Scanner;

import edu.ncsu.csc316.file_compressor.exception.InvalidInputFileTypeException;
import edu.ncsu.csc316.file_compressor.util.LinkedList;

/**
 * The File Compressor Manager class, takes in a given file and using move to front
 * heuristic algorithm, the given file is either compressed or decompressed.
 * @author Pratik Khanal
 */
public class FileCompressorManager {

	private LinkedList list = new LinkedList();
	

	/**
	* The processFile method processes the file with the given name to
	* determine whether to compress or decompress the file.
	* If the file type is invalid or the file cannot be found, an exception
	* is thrown.
	* @param fileName the name of the file to process
	* @return "DECOMPRESS" if the file was decompressed,
	* or "COMPRESS" if the file was compressed
	* @throws FileNotFoundException if the file cannot be found
	* @throws InvalidInputFileTypeException if the file is not a valid .txt file
	*/
	public String processFile(String fileName)
	throws FileNotFoundException, InvalidInputFileTypeException {
	
		if (fileName == null || fileName.equals(" "))
			throw new FileNotFoundException();
		if (!fileName.endsWith(".txt"))
			throw new InvalidInputFileTypeException();
		
		if(!fileName.contains("-compressed")){
			// passes the fileName and gets the name of the file.
			File out = new File("output/compressed/" + outputFileName(fileName) + "-compressed.txt");
			compress(fileName, out);
			return "COMPRESS";
		} else {
			File out = new File( "output/decompressed/" + outputFileName(fileName) + ".txt");
			decompress(fileName, out);
			return "DECOMPRESS";
		}
	}
	
	/**
	 * This method compresses the given file and writes the compressed file in a new 
	 * file in output folder. Throws file not found exception if the file does not exist.
	 * @param filename the name of file to be compressed.
	 * @param output the name of output file that the compressed is written into.
	 * @throws FileNotFoundException thrown when there is no file.
	 */
	public void compress(String filename, File output) throws FileNotFoundException{
		
		Scanner scan = new Scanner(new FileInputStream(filename), "UTF8");
		int uSize = 0;
		int cSize = 0;
		String s = "";
		StringBuilder word = new StringBuilder();
		
		try {
			FileOutputStream fos = new FileOutputStream(output);
			Writer w = new OutputStreamWriter(fos, "UTF8");
			
			w.write("0 ");
			while (scan.hasNextLine()){
				s = scan.nextLine();
				uSize += s.length();
				if(s.equals("")){
					w.write(Character.toString('\n'));
					continue;
				}
				char[] line = s.toCharArray();
				
				for (int i = 0; i < line.length; i++){
					
					if (checkSpecialC(line[i])) { // returns true if the character is a letter.
						word = word.append(Character.toString(line[i]));
						if(i == line.length - 1){
							
							int index = list.removeAndGetIdx(word.toString());
							if(index == -1){
								list.add(word.toString());
								w.write(word.toString());
								cSize += word.length();
								word.setLength(0);
							} else {
								list.add(word.toString());
								w.write(Integer.toString(index + 1));
								cSize += String.valueOf(index + 1).length();
								word.setLength(0);
							}
							break;
						}
						
					} else if((!checkSpecialC(line[i])) && (word.length() > 0) && (word != null)){
						int index = list.removeAndGetIdx(word.toString());
						if(index == -1){
							list.add(word.toString());
							w.write(word.toString());
							cSize += word.length();
							word.setLength(0);
						} else {
							list.add(word.toString());
							w.write(Integer.toString(index + 1));
							cSize += toString().valueOf(index + 1).length();
							word.setLength(0);
						}
					}
					
					if (!checkSpecialC(line[i])){
						w.write(Character.toString(line[i]));
						cSize++;
					}
				}
				word.setLength(0);
				w.write(Character.toString('\n'));
			}
			
			w.write(Character.toString('\n'));
			w.write("0 Uncompressed: " + uSize + " bytes;  " + "Compressed: " + cSize + " bytes" );
			
			w.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		scan.close();
	}
	
	/**
	 * This method decompress the given file and writes the output in a new file. Throws 
	 * File not found exception if the file to be decompressed does not exist. 
	 * @param filename the name of file to decompress.
	 * @param output the name of file to write decompressed file.
	 * @throws FileNotFoundException thrown when the file does not exist.
	 */
	public void decompress(String filename, File output) throws FileNotFoundException{
		Scanner scan = new Scanner(new FileInputStream(filename), "UTF8");
		StringBuilder word = new StringBuilder();
		StringBuilder number = new StringBuilder();
		try {
			FileOutputStream fos = new FileOutputStream(output);
			Writer w = new OutputStreamWriter(fos, "UTF8");
			int end = 0;
			while (scan.hasNextLine()){
				String s = scan.nextLine();
				if(s.equals("")){
					w.write(Character.toString('\n'));
					continue;
				}
				char[] line = s.toCharArray();
				String first2 = "";
				if(line.length > 1){
					first2 += line[0];
					first2 += line[1];
				}
				int start = 0;
				if(first2.equals("0 ")){ // first line
					start = 2;
					end++;
					if( end == 2){
						break;
					}
				}

				for(int i = start; i < line.length; i++){
					
					if(checkSpecialC(line[i])){
						word.append(line[i]);
						if(i == line.length - 1){
							list.add(word.toString());
							w.write(word.toString());
							word.setLength(0);
							w.write(Character.toString('\n'));
						}
					} else {
						
						if(Character.isDigit(line[i])){
							number.append(line[i]);
							if(i == line.length - 1){
								String x = "";
								int index = Integer.parseInt(number.toString());
								if (index > 0){
									x = list.getRemoveString(index - 1);
									list.add(x);
									number.setLength(0);
									w.write(x);
								}
							} 
						} 
						else {
							if(number.length() > 0){
								String x = "";
								int index = Integer.parseInt(number.toString());
								if (index > 0){
									x = list.getRemoveString(index - 1);
									list.add(x);
									number.setLength(0);
									w.write(x);
								}
							}
							
							if(word.length() > 0){
								list.add(word.toString());
								w.write(word.toString());
								word.setLength(0);
							}
							w.write(Character.toString(line[i]));
						}
						if(i == line.length - 1){
							w.write(Character.toString('\n'));
						}
					}
				}
			}
			//}
		w.close();	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		scan.close();
	}
	
	/**
	 * This method takes in a file path name and strips the path name to just file name. 
	 * @param filename the path name of file.
	 * @return the name of file;
	 */
	public String outputFileName(String filename){
		String output = "";
		char [] name = filename.toCharArray();
		
		int i = 0;
		// loops until it finds the period before .txt
		while(name[i] != '.'){
			i++;
		}
		int j = i ;
		i = j;
		// form the period position loops backward until if finds first / character.
		while(name[i] != '/'){
			i--;
			if ( i == 0){
				break;
			}
		}
		int k = i + 1;
		
		// if filename have -compressed on it then this loop will only send the string name of 
		// file between / character and - character.
		if(filename.contains("-compressed")){
			i = i + 1;
			while(name[i] != '-'){
				output += name[i];
				i++;
			}
		}
		// if filename does not have -compressed than it 
		if(!filename.contains("-compressed")){
			for (int l = k; l < j; l++){
				output += name[l];
			}
		}
		return output.toString();
		
		
	}
	
	/**
	 * Checks the passed character is a letter or not. Return true if character is letter,
	 * and return false if it is not a character. 
	 * @param c character to check if it is a letter.
	 * @return true for letter and false for other.
	 */
	public boolean checkSpecialC(char c){
		if(Character.isLetter(c)){
			return true;
		}
		return false;
	}
	

	
	public static void main(String[] args){
		System.out.println("Please Enter a File Name.");
		Scanner fileNameScan = new Scanner(System.in);
		String fileName = fileNameScan.next();
		try {
			System.out.println(new FileCompressorManager().processFile(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidInputFileTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
