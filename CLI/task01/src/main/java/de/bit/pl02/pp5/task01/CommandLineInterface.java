package de.bit.pl02.pp5.task01;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;

/**
 * Main Class to interact with the user and receive input through command line
 * options.
 * 
 * 
 * @author Shreya Kapoor
 * @author Sophia Krix
 * @author Gemma van der Voort
 */
public class CommandLineInterface {

	/**
	 * The command line to receive arguments from the user, static method because at
	 * once only one command line will run from this class.
	 */
	static CommandLine cmd;
	/**
	 * To keep a track of certain errors throughout the program 1. Means that the
	 * metafile didn't exist and the program made it.
	 **/
	public static int error_code;

	/**
	 * Checks if a metadata file exists already, otherwise creates a new metadata
	 * file with the same name as the image file and stores it with an extension
	 * .meta
	 * 
	 * 
	 * @param filename the name of the input file
	 * @return file if the oldfile exists return it else return newfile
	 **/
	public static File checkmetafile(String filename) {
		// Assuming that the existing metadata file is in the same directory as the
		// image
		// and differs only in the extension .meta
		String directory = cmd.getOptionValue("d");
		// String absolutePath = new File(directory).getAbsolutePath();
		String[] tmp = filename.split("\\.(?=[^\\.]+$)"); // split through the last dot
		// System.out.println(tmp);
		String path = directory + "/" + tmp[0] + ".meta";
		System.out.println("Reading file in directory, path is: " + path);
		File file = new File(path);
		if (file.exists()) {
			System.out.println(StringUtils.repeat("-", 20) + " MESSAGE " + StringUtils.repeat("-", 20));
			System.out.println("Metafile exists, will deal with existing metafile");
			System.out.println(StringUtils.repeat("-", 50));
		} else {
			error_code = 1; // this means that the file didnt exist before and was made by the program itself.
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println(StringUtils.repeat("-", 20) + " MESSAGE " + StringUtils.repeat("-", 20));
				System.out.println("The file did not exist before, program has created a new one");
				System.out.println(StringUtils.repeat("-", 50));
			}
		}
		return file;
	}

	/**
	 * Get input from the user about author, title and infographic as a tuple,
	 * separated by a comma. Saves a .txt file of the metadata. If a file does not
	 * already exist, create a new file if a file exists add contents to that one
	 * 
	 * 
	 * @param file      the metadata returned file
	 * @param array     contains the attributes which are a present in the metadata
	 *                  file already
	 * @param overwrite if the user wants to overwrite the contents of the file or
	 *                  not
	 */
	public static void getMetaUser(File file, List<String> array, Boolean overwrite) {
		// improvements write in the form of a class in the file.

		MetaData metadata = new MetaData();
		String directory = cmd.getOptionValue("d");

		String[] metavalues = cmd.getOptionValues("im");
		// System.out.println("Print values: "+ metavalues[0] + metavalues[1] +
		// metavalues[2] + metavalues[3] );

		String author = metavalues[0];
		String title = metavalues[1];
		String db = metavalues[2];
		int infographic = Integer.parseInt(metavalues[3]);
		// Set input by user as attributes of metadata instance
		metadata.setAuthor(author);
		metadata.setTitle(title);
		metadata.setInfographic(infographic);
		metadata.setDatabase(db);
		// System.out.println("metad" + metadata);
		// Write metadata to file, split from the last line to get the meta path
		FileWriter os;
		// only write to the file if the author tile etc. have not been entered or if
		// overwrite option entered
		if (array.isEmpty() || overwrite) {
			try {
				os = new FileWriter(file, true);
				os.write(metadata.to_String());
				os.close();
			} catch (IOException e) {
				System.out.println(StringUtils.repeat("=", 20) + "ERROR" + StringUtils.repeat("=", 20));
				System.out.println("Could not write to the path related to the file");
			}
		}

	}

	/**
	 * Creates command line options to make a database, store images in it and
	 * retrieve image and metadata information
	 * 
	 * @return options the command line options
	 */
	public static Options make_options() {
		Options options = new Options();
		Option directory = new Option("d", "directory", true, "Enter the path of the directory of the metadata");
		Option importfile = new Option("ip", "inputfile", true, "Enter the name of the input file");
		Option print = new Option("p", "print", false, "If you want to print the entire information");
		Option meta = new Option("m", "meta", false, "If you want to add meta information");
		Option inputmeta = new Option("im", "inputmeta", false,
				"Enter the value of author, title and infographic separated by a comma");
		Option overwrite = new Option("o", "overwrite", false, "If you want to overwrite the original contents");

		directory.setType(String.class); // the datatype of the directoryname must be string
		importfile.setType(String.class); // the datatype of the inputfile must be string
		// overwrite.setType(Boolean.class); // overwrite option has to be a true/false
		// statement!
		inputmeta.setType(String.class);
		inputmeta.setArgs(4);
		inputmeta.setValueSeparator(',');

		options.addOption(directory);
		options.addOption(importfile);
		options.addOption(meta);
		options.addOption(print);
		options.addOption(inputmeta);
		options.addOption(overwrite);
		directory.setRequired(true); // It is mandatory to specify the directory, terminate otherwise
		importfile.setRequired(true); // Mandatory to specify the input file, terminate otherwise

		return options;
	}

	/**
	 * Parses command line options for arguments and returns the corresponding
	 * options and values. Exits the application if the input file is not found.
	 * 
	 * @param options the command line options
	 * @param args    the values of the command line options
	 * @return cmd list of atomic option and value tokens
	 */
	public static CommandLine parse_commandline(Options options, String[] args) {

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			System.out.println(StringUtils.repeat("=", 20) + "FATAL ERROR" + StringUtils.repeat("=", 20));
			System.out.println("There is an issue with parsing the options, ending the application " + e.getMessage());
			formatter.printHelp("parsingtest", options);
			System.exit(1); // exit the application if the input file is not found!
		}
		return cmd;
	}

	/**
	 * Reads an input file and prints the content if option "p" is specified
	 * 
	 * 
	 * @param file the file of the input picture
	 * @throws FileNotFoundException the metadata file didnt exist before
	 * @return array the list of what metadata content is already present and might
	 *         be overwritten
	 */
	public static List<String> readfile(File file) throws FileNotFoundException {

		FileReader filer;
		filer = new FileReader(file);
		BufferedReader buffr = new BufferedReader(filer);
		boolean eof = false;
		List<String> array = new ArrayList<String>();
		List<String> checklist = Arrays.asList("Title", "Author", "Database", "Infographic");
		if (error_code != 1) {
			System.out.println("\nContents of the current file\n" + StringUtils.repeat("=", 50));
			try {
				String s;
				while ((!eof) && cmd.hasOption("p")) {
					s = buffr.readLine();
					if (s == null) {
						eof = true;
					} else {
						System.out.println(s);
						for (String a : checklist) {
							if (s.contains(a)) {
								String tmp = s.split(":")[0];
								array.add(tmp);
							}
						}
					}
				}
				System.out.println(StringUtils.repeat("=", 50) + "\n");
				buffr.close();
			} catch (IOException e) {
				System.out.println(StringUtils.repeat("=", 20) + "ERROR" + StringUtils.repeat("=", 20));
				System.out.println("The metadata file cannot be read!");
				// System.exit(0);
			}
		} else {
			System.out.println(StringUtils.repeat("-", 20) + " MESSAGE " + StringUtils.repeat("-", 20));
			System.out.println("The metafile didn't exist before so nothing to print");
			System.out.println(StringUtils.repeat("-", 40));
			error_code = 0; // will reuse this code when we want to print the file with the new metadata!
		}

		return array;
	}

	public static void main(String[] args) {
		/** create command line options */
		Options options = CommandLineInterface.make_options(); // for static method of the class
		/** parse command line for options */
		cmd = CommandLineInterface.parse_commandline(options, args); // Parse the input given by the user

		/*
		 * Print the metadata file content if metadata file exists if it doesn't exist
		 * then a new file will be created
		 */
		if (cmd.hasOption("p")) {
			String filename = cmd.getOptionValue("ip");
			System.out.println(StringUtils.repeat("*", 60));
			System.out.println("File name: " + filename);
			String directory = cmd.getOptionValue("d"); // user needs to enter absolute path
			File metafile = CommandLineInterface.checkmetafile(filename);
			try {
				List<String> containers = readfile(metafile);
				// Save the input of the user as a .txt file if metadata file does not yet exist
				if (cmd.hasOption("im") && cmd.hasOption("m")) {
					String imagename = cmd.getOptionValue("ip");
					Boolean overwrite = Boolean.parseBoolean(cmd.getOptionValue("o"));
					if (overwrite) {
						System.out.println(StringUtils.repeat("-", 20) + " MESSAGE " + StringUtils.repeat("-", 20));
						System.out.println("Overwriting the file as selected");
						System.out.println(StringUtils.repeat("-", 40));
					}
					CommandLineInterface.getMetaUser(metafile, containers, overwrite);
					System.out.println("After dealing with your meta options the file will be displayed!");
					List<String> containers2 = readfile(metafile);
				}
			} catch (FileNotFoundException e) {
				System.out.println(StringUtils.repeat("=", 20) + "FATAL ERROR" + StringUtils.repeat("=", 20));
				System.out.println("Could not connect to the metadata file!");
			}

		}

	}
}
