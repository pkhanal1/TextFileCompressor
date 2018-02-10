/**
 * 
 */
package edu.ncsu.csc316.file_compressor.exception;

/**
 * A custom exception class which creates an exception message for Invalid input file type.
 * @author Pratik Khanal
 */
public class InvalidInputFileTypeException extends Exception {

	/**
	 * Exception message for invalid input file type.
	 */
	public InvalidInputFileTypeException() {
		super("Invalid Input File Type");
	}
}
