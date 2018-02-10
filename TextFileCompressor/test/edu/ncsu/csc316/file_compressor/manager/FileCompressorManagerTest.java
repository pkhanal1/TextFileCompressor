/**
 * 
 */
package edu.ncsu.csc316.file_compressor.manager;



import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

import edu.ncsu.csc316.file_compressor.exception.InvalidInputFileTypeException;

/**
 * This class tests the file compressor manager class.
 * @author Pratik Khanal
 */
public class FileCompressorManagerTest {

	private String input1 = "test-files/input1";
	private String input2 = "test-files/input2";
	private String input3 = "test-files/input3";
	private String input4 = "test-files/input4";
	private String input5 = "test-files/input5";
	private String input6 = "test-files/input6";
	private String input7 = "test-files/input7";
	private String input8 = "test-files/input8";
	private String input9 = "test-files/input9";
	private String input10 = "test-files/input10";
	private String output = "output/compressed/input1-compressed.txt";
	/**
	 * Test method for {@link edu.ncsu.csc316.file_compressor.manager.FileCompressorManager#FileCompressorManager()}.
	 */
	@Test
	public void testFileCompressorManager() {
		FileCompressorManager f = new FileCompressorManager();
		try {
			f.processFile(input1);
			f.processFile(input2);
			f.processFile(input3);
			f.processFile(input4);
			f.processFile(input5);
			f.processFile(input6);
			f.processFile(input7);
			f.processFile(input8);
			f.processFile(input9);
			f.processFile(input10);
			assertTrue(f.outputFileName(input1).equals("input1"));
			assertFalse(f.outputFileName(input2).equals("input1"));
			assertTrue(f.outputFileName(input3).equals("input3"));
			assertTrue(f.outputFileName(input4).equals("input4"));
			assertTrue(f.outputFileName(input5).equals("input5"));
			assertTrue(f.outputFileName(input6).equals("input6"));
			assertTrue(f.outputFileName(input7).equals("input7"));
			assertTrue(f.outputFileName(input8).equals("input8"));
			assertTrue(f.outputFileName(input9).equals("input9"));
			assertTrue(f.outputFileName(input10).equals("input10"));
			f.processFile(output);
			assertFalse(f.checkSpecialC('!'));
			f.processFile("output.html");
			f.processFile(" ");
		} catch (FileNotFoundException | InvalidInputFileTypeException e) {
			e.printStackTrace();
		}
		
		try {
			assertTrue(f.processFile("test-files/input2.txt").equals("COMPRESS"));
			assertTrue(f.processFile("output/compressed/input1-compressed.txt").equals("DECOMPRESS"));
		} catch (FileNotFoundException | InvalidInputFileTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
