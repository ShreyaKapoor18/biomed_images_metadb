package de.bit.pl02.pp5.task01;
//import java.io.Serializable;

/**
 * This class is used so that an object can be written and extracted from text
 * files. This makes the retrieval of the metadata standardised.
 * 
 * @author Shreya Kapoor
 * @author Sophia Krix
 * @author Gemma van der Voort
 *
 */

public class MetaData {
	// private static final long serialVersionUID =1L;
	/** Title of the image/article related to it **/
	private String title;
	/** Name of the author related to the image or article **/
	private String author;
	/** The database from where the image came **/
	private String database;
	/** What is the information about **/
	private int infographic;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public int getInfographic() {
		return infographic;
	}

	public void setInfographic(int infographic) {
		/**
		 * We accept infographic values only of the range 1 to 4. Values 1. Implies
		 * image of a cell/tissue 2. Implies image of a biological cartoon 3. Implies
		 * that the image is a graph 4. Implies the type of the image doesn't fit into
		 * the above classification
		 **/
		if (infographic >= 1 && infographic <= 4) {
			this.infographic = infographic;
		}
	}

	public String to_String() {
		return "Author:" + author + "\nTitle:" + title + "\nDatabase: " + database + "\nInfographic:" + infographic;
	}

}
