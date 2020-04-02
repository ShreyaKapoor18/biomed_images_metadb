package de.bit.pl02.pp5.task02;

import de.bit.pl02.pp5.task02.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Image is the class of images used to read in .jpg, .jpeg or .png files and to
 * store these in a byte array in a database. These byte arrays can be converted
 * back again into .jpg files and stored locally.
 * 
 * @author Shreya Kapoor
 * @author Sophia Krix
 * @author Gemma van der Voort
 * 
 * 
 */
public class Image {

	/** the name of the image **/
	private String name;
	/** the path of the image in the machine **/
	private String path;

	/**
	 * Class constructor
	 * 
	 * @param path The location of the image in the file system
	 * @param name The name of the image file.
	 */
	Image(String path, String name) {

		this.name = name;
		this.path = path;
	}

	/**
	 * Reads in an image file and converts it to a byte array so that it can be
	 * entered into the database. Returns byte array of input image if not null.
	 * 
	 * @param file the file path of the .jpg or .png file
	 * @return bos the byte array of the input image
	 */
	public byte[] readFile(String file) {
		ByteArrayOutputStream bos = null;
		try {
			// load image file given by the user
			File f = new File(file);
			FileInputStream fis = new FileInputStream(f);
			byte[] buffer = new byte[1024];
			bos = new ByteArrayOutputStream();
			// write file to ByteArrayOutputStream
			for (int len; (len = fis.read(buffer)) != -1;) {
				bos.write(buffer, 0, len);
			}
			fis.close();
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (IOException e2) {
			System.err.println(e2.getMessage());
		}
		// if successfull then return the byte array else return null
		return bos != null ? bos.toByteArray() : null;
	}

	/**
	 * Reads in all .meta files in a folder with specified path and returns the meta
	 * information found about author and title together with the constructed id in
	 * a String array.
	 * 
	 * 
	 * @param path the file path of the metadata
	 * @return meta a String array with the metadata about Id, title and author
	 */
	ArrayList<String> find_metadata(String path) {
		ArrayList<String> meta = new ArrayList<String>();
		// We consider that we follow the same path for metadata files and the actual
		// image files
		// so we can just strip off the . in the filename and then add .meta in front of
		// it in order to get the
		// related to a particular image.
		String metapath = this.path.split("\\.(?=[^\\\\.]+$)")[0] + ".meta";
		try {
			FileReader filer = new FileReader(metapath);
			BufferedReader buffr = new BufferedReader(filer);
			boolean eof = false;
			// default values if not present
			String author = "xx";
			String title = "yyzz";
			String link = "no link";
			while ((!eof)) {
				// read from the metadata file
				String s = buffr.readLine();
				if (s == null) {
					eof = true;
				} else {
					// get values of title, author and link
					if (s.contains("Title:")) {
						title = s.split(":")[1];
					} else if (s.contains("Author:")) {
						author = s.split(":")[1];
					} else if (s.contains("http")) {
						link = s;
					}
				}
			}
			buffr.close();
			// add found values to the ArrayList
			meta.add(title);
			meta.add(author);
			meta.add(link);

		} catch (Exception e) {
			/*
			 * if there existed none of such files then probably some default values shall
			 * be set and passed on in the given fields.
			 */
			meta.add("xx");
			meta.add("yyzz");
			meta.add("https");
		}
		return meta;
	}

}
