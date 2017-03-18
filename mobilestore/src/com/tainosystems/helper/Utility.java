package com.tainosystems.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;

import org.jdom2.Element;

public class Utility {

	private static String directory = "E://";
	private static String logFile = "E://log.txt";
	private static boolean isLogOn = true;

	// private static String directory = "/home/signatures//";
	
	/**
	 * 
	 * @param code_magasin
	 * @param code_client
	 * @param no_livraison_client
	 * @param d
	 * @return FileName
	 * @throws ParseException
	 * 
	 * This function is used to create the file name with
	 * the help of code_magasin,code_client,no_livraison and date
	 * 
	 */
	
	public static String createFileName(String code_magasin,
			String code_client, String no_livraison_client, String d)
			throws ParseException {
		d = d.trim();
		String y = d.substring(0, 4);
		String m = d.substring(5, 7);
		String dt = d.substring(8, 10);
		String h = d.substring(d.indexOf(' ') + 1, d.indexOf(':'));
		String mint = d.substring(d.indexOf(':') + 1, d.indexOf(':') + 3);
		d = y + m + dt + h + mint;
		String fileName = "";
		fileName = code_magasin + code_client + no_livraison_client + d
				+ ".jpg";
		return directory + fileName;
	}


	/**
	 * 
	 * @param uploadedInputStream
	 * 			An object of UploadedInputStream containing data to be write on the file
	 * @param uploadedFileLocation
	 * 			String parameter having location of the file to save
	 * @return boolean is file has been saved or not
	 * 
	 * This funtion is used to save the file in the given location
	 */
	
	
	public static boolean writeToFile(InputStream uploadedInputStream,
			String uploadedFileLocation) {
		try {
			OutputStream out = new FileOutputStream(new File(
					uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];
			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}


	/**
	 * 
	 * @param data
	 * 		String value that is to be logged
	 * 
	 * This function is used to log the data in the predefined text file
	 * 
	 */
	public static void logData(String data) {
		FileWriter fw;
		try {
			if (isLogOn) {
				fw = new FileWriter(new File(logFile), true);
				fw.write(data + "\n");
				fw.flush();
				fw.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * 
	 * This function is used to off the logging either
	 * 
	 */
	public static void offLog() {
		isLogOn = false;
	}
	
	/**
	 * 
	 * This function is used to on the logging either
	 * 
	 */
	public static void onLog() {
		isLogOn = true;
	}
}
