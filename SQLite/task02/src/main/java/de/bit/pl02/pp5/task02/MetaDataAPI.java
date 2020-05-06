package de.bit.pl02.pp5.task02;

/**
 * MetaDataAPI receives metadata from the SQLite database upon querying by the
 * getForAPI method in the database class.
 * 
 * @author Shreya Kapoor
 * @author Sophia Krix
 * @author Gemma van der Voort
 * 
 * 
 */
public class MetaDataAPI {
	// attributes with metadata information
	private int id;
	private String author;
	private String title;
	private String link;

	/**
	 * Class constructor MetaDataAPI receives metadata from the SQLite database upon
	 * querying by the getForAPI method in the database class.
	 * 
	 * @param id     the identifier id
	 * @param author the AUTHOR value
	 * @param title  the TITLE value
	 * @param link   the LINK value
	 */
	public MetaDataAPI(int id, String author, String title, String link) {// , String link, String fileName) {
		this.id = id;
		this.author = author;
		this.title = title;
		this.link = link;
	}

	/**
	 * Getter method for the id.
	 * 
	 * @return the identifier
	 */
	public int getId() {
		return id;
	}

	/**
	 * Setter method for the id.
	 * 
	 * @param id the identifier value
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Getter method for the author.
	 * 
	 * @return the author value
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Setter method for the author.
	 * 
	 * @param author the author value
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * Getter method for the title.
	 * 
	 * @return the title value
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter method for the title.
	 * 
	 * @param title the title value
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter method for the link.
	 * 
	 * @return the link value
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Setter method for the link
	 * 
	 * @param link the link value
	 */
	public void setLink(String link) {
		this.link = link;
	}
}
