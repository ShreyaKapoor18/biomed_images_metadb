package de.bit.pl02.pp5.task02;

import java.io.FileFilter;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import java.io.IOException;

/**
 * The class Database can be used to create database objects and helps the user
 * to interact with them. The user can give a directory in the
 * {@link #read_director(String)} method from which a table will be created with
 * the images and the corresponding metadata. It is possible to print the values
 * of the created table with the method {@link #see_table()}. If the user only
 * wants the metadata of a specific sample, then the metadata can be retrieved
 * by specifying either the author or the title with the
 * {@link #get_meta(String, String, String)} method and be saved as a .txt file.
 * The user can add images with the {@link #updatePicture(Image, int, String)}
 * method to the table.
 * 
 * Columns: 
 * AUTHOR the name of the author 
 * TITLE the name of the title 
 * LINK the URL related to the image 
 * PICUTRE the image as a byte array
 * 
 * @author Shreya Kapoor
 * @author Sophia Krix
 * @author Gemma van der Voort
 * 
 */

public class Database {

	/** the name of the database */
	private String path;
	private String name;
	// private int error_code; // giving an error when something goes wrong with the
	// database.
	private Connection con;
	private int id = 0; // start the id with 1 for each database

	/**
	 * Constructor method, makes a new database if it didn't exist, otherwise
	 * connects to the existing database.
	 * 
	 * @param path the path of the database
	 * @param name the name of the database
	 */
	public Database(String path, String name) {

		this.path = path;
		this.name = name;
		try {
			this.Connect_db();

		} catch (SQLException e) {

			System.out.println(StringUtils.repeat("=", 20) + " ERROR " + StringUtils.repeat("=", 20));
			System.out.println(" Could not connect to the database using predefined method");
		}
	}

	/**
	 * Establishes a connection to the SQLite database
	 *
	 * @throws SQLException if SQL command can not be executed
	 */
	public void Connect_db() throws SQLException {
		// connecting to the database whether existing or not existing!
		try {
			Class.forName("org.sqlite.JDBC");
			System.out.println("jdbc:sqlite:" + this.path + "/" + this.name + ".db");
			this.con = DriverManager.getConnection("jdbc:sqlite:" + this.path + "/" + this.name + ".db");
			// System.out.println("jdbc:sqlite:" + this.path + "/"+ this.name + ".db");
			try {
				// Check if TABLE Images is already present
				// create id by counting the instances that are already in the database
				Statement smt = this.con.createStatement();
				String count_query = "SELECT COUNT(*) from 'IMAGES'";
				ResultSet r1 = smt.executeQuery(count_query);
				int count;
				//count = r1.getInt("COUNT(*)");
				count = r1.getInt(1);
				r1.close();
				smt.close();
				System.out.println(" On first connection, the database contains " + count + " elements");
				this.id = count;
			} catch (Exception e) {
				// create table IMAGES with columns ID, TITLE, AUTHOR, LINK
				Statement smt = this.con.createStatement();
				String sql = "CREATE TABLE IMAGES " + "(ID INTEGER PRIMARY KEY NOT NULL," + "TITLE TEXT NOT NULL, "
						+ "AUTHOR TEXT NOT NULL, " + "LINK TEXT NOT NULL);";
				smt.execute(sql);
				// add column PICTURE
				String sql1 = "ALTER TABLE IMAGES ADD COLUMN PICTURE blob";
				smt.execute(sql1);
				smt.close();
			}

			// con = DriverManager.getConnection("jdbc:sqlite:" + this.name + ".db");
		} catch (ClassNotFoundException e) {
			System.out.println(StringUtils.repeat("=", 20) + " ERROR " + StringUtils.repeat("=", 20));
			System.out.println(" Could not find the class");
		}
	}

	/**
	 * Creates a table IMAGES
	 */
	public void make_table() {
		try {
			Statement stmt = this.con.createStatement();
			// SQL commands to create the table IMAGES with columns ID, TITLE, AUTHOR, LINK
			ArrayList<String> sqls = this.insert_fields();
			for (String sql : sqls) {
				try {
					// See if these are getting added or not.
					// System.out.println("create tables!");
					stmt.execute(sql);
				} catch (SQLException e) {
					System.out.println(sql + " got error with the query");
					e.printStackTrace();
					continue; // if there is an error means it does contain the columns etc.
				}
				stmt.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Creates SQL commands to create a table with columns ID, AUTHOR, TITLE,
	 * PICTURE blob
	 * 
	 * @return commands the SQL commands
	 */
	public ArrayList<String> insert_fields() {
		// arraylist of SQL commands which can be given to the program so that the
		// execution gets up and running.
		ArrayList<String> commands = new ArrayList<String>();
		commands.add("CREATE TABLE IF NOT EXISTS IMAGES " + "(ID INTEGER PRIMARY KEY NOT NULL,"
				+ "TITLE TEXT NOT NULL, " + "AUTHOR TEXT NOT NULL, " + "LINK TEXT NOT NULL, PICTURE blob );");
		// add a column so that pictures can be stored there.
		return commands;
	}

	/**
	 * Prints the values of the table IMAGES of column ID, TITLE and AUTHOR.
	 * 
	 * @throws SQLException if the SQL command can not be executed
	 */
	public void see_table() throws SQLException {
		Statement smt = this.con.createStatement();
		// select all content from table IMAGES
		ResultSet rs = smt.executeQuery("SELECT * from IMAGES");
		while (rs.next()) { // split the ResultSet into according values of ID, TITLE, AUTHOR, LINK
			String x = rs.getString("ID");
			String s = rs.getString("TITLE");
			String a = rs.getString("AUTHOR");
			String l = rs.getString("LINK");
			System.out.println("Printing table:");
			System.out.println("ID: " + x + "\nAUTHOR: " + a + "\nTITLE: " + s + "\nLINK: " + l);
			System.out.println("ID: " + x + "\nAUTHOR: " + a + "\nTITLE: "+ s + "\nLINK: " + l);
		}
		smt.close();

	}

	/**
	 * Reads the files of a directory, finds the images and its corresponding
	 * metadata. Inserts the metadata of ID, TITLE and AUTHOR into the database and
	 * inserts the image into the PICTURE blob column via the
	 * {@link #updatePicture(Image, int, String)} method.
	 * 
	 * @param dir the path of the directory
	 * @return arr the SQL commands
	 * @throws SQLException if image could not be inserted into database
	 */
	public ArrayList<String> read_director(String dir) throws SQLException {
		ArrayList<String> arr = new ArrayList<String>();
		Statement smt = this.con.createStatement();
		// create list of files of the directory given by the user
		File dir1 = new File(dir);
		System.out.println("Directory: " + dir);
		//System.out.println("before");
		File[] filesindir = dir1.listFiles();
		//System.out.println("after");
		if (filesindir==null){
		System.out.println("files in dir null");}
		if (filesindir!= null) {
			for (File f : filesindir) {
				String imgname = f.getName();
				if (imgname.contains(".png") || imgname.contains(".jpg") || imgname.contains(".jpeg")) {
					// create instance of Image with the name of the file given by the user
					// directory
					Image img = new Image(f.getAbsolutePath(), imgname);
					// increase ID with every new image instance
					id += 1;
					System.out.println(id + " " + imgname);
					// meta information as String array of file
					ArrayList<String> meta = img.find_metadata(f.getAbsolutePath());
					// insert the metadata into the table IMAGES
					if (meta != null) {
						arr.add("INSERT INTO IMAGES (ID,TITLE, AUTHOR, LINK)" + "VALUES ('" + id + "','" + meta.get(0)
								+ "'," + meta.get(1) + "','" + meta.get(2) + "')");
						try {
							String author = "'" + meta.get(1) + "'";
							String title = "'" + meta.get(0) + "'";
							String link = "'" + meta.get(2) + "'";
							String sql = "INSERT INTO IMAGES (ID,TITLE, AUTHOR, LINK) VALUES (" + id + "," + title + ","
									+ author + "," + link + ")";
							smt.execute(sql);
							// add image as byte array into table IMAGES
							this.updatePicture(img, id, f.getAbsolutePath());
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
					}
				}
			}
		}
		// System.out.println("inserting images");
		return arr;
	}

	/**
	 * Updates the database with the new image by using the method
	 * {@link Image#readFile(String)} to read in an image file and store it as a
	 * byte array.
	 *
	 * @param img  the image to be stored
	 * @param Id   the value of the id column in the database
	 * @param path the path of the image to be stored
	 */
	public void updatePicture(Image img, int Id, String path) {
		// update sql and add image byte array into table
		String updateSQL = "UPDATE IMAGES " + "SET PICTURE =?" + "WHERE id=?";
		try {

			PreparedStatement pstmt = this.con.prepareStatement(updateSQL);
			// set parameters
			pstmt.setBytes(1, img.readFile(path));
			pstmt.setInt(2, Id);
			pstmt.executeUpdate();
			// System.out.println("Stored the file in the BLOB column.");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			// only this particular image could not be inserted into the database.
		}
	}

	/**
	 * MODIFIED FOR API Updates the database with the new image by using the method
	 * {@link Image#readFile(String)} to read in an image file and store it as a
	 * byte array. 
	 * 
	 * @param bytes  the byte array of the image
	 * @param author the value of column AUTHOR
	 * @param title  the value of column TITLE
	 * @param link   the value of column LINK
	 */
	public void storePictureForAPI(byte[] bytes, String author, String title, String link) {
		// update sql
		id++;
		String updateSQL = "UPDATE IMAGES SET PICTURE =? WHERE id=?";
		try {
			Statement smt = this.con.createStatement();
			String sql = "INSERT INTO IMAGES (ID,TITLE, AUTHOR, LINK) VALUES (" + id + ",'" + title + "','" + author
					+ "','" + link + "')";
			smt.execute(sql);
			smt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		try {
			PreparedStatement pstmt = this.con.prepareStatement(updateSQL);
			// set parameters
			pstmt.setBytes(1, bytes);
			pstmt.setInt(2, id);
			pstmt.executeUpdate();
			pstmt.close();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * FOR API USE function returns a list of id's and metadata that matches the get
	 * command author and/or title. Cannot both be null!
	 * 
	 * @param author the value of column AUTHOR to be searched
	 * @param title  the value of column TITLE to be search
	 * @return MetaDataList the list of id and metadata
     * command author and/or title. CANNOT BOTH BE NULL!
	 */
	public List<MetaDataAPI> getForAPI(String author, String title) {
		if (author == null && title == null) {
			return new ArrayList<MetaDataAPI>();
		}
		PreparedStatement stmt = null;
		// ArrayList<Integer> idList = new ArrayList<Integer>();
		List<MetaDataAPI> MetaDataList = new ArrayList<MetaDataAPI>();
		try {
			if (title == null) {
				stmt = this.con.prepareStatement("SELECT * FROM IMAGES WHERE AUTHOR=?");
				stmt.setString(1, author);
			} else if (author == null) {
				stmt = this.con.prepareStatement("SELECT * FROM IMAGES WHERE TITLE=?");
				stmt.setString(1, title);
			} else {
				stmt = this.con.prepareStatement("SELECT * FROM IMAGES WHERE AUTHOR=? AND TITLE=?");
				stmt.setString(1, author);
				stmt.setString(2, title);
			}
			System.out.println("executed:" + stmt.toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				System.out.println("executed: get binary stream ");
				int id = rs.getInt("ID");
				String author2 = rs.getString("AUTHOR");
				String title2 = rs.getString("TITLE");
				String link = rs.getString("LINK");
				// idList.add(id);
				MetaDataAPI MetaDataList2 = new MetaDataAPI(id, author2, title2, link);
				MetaDataList.add(MetaDataList2);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
			;
		}
		// convert array list into an array
		return MetaDataList;
		// ArrayUtils.toPrimitive((Integer[]) idList.toArray(new
		// Integer[idList.size()]));
	}

	/**
	 * FOR API USE returns byte[] of picture with identifier id. Since id is unique,
	 * this function returns only 1 byte[]. overloaded
	 * 
	 * @param id		the identifier id
	 * @return image	the byte array of the image
	 */
	public byte[] getForAPI(int id) {
		PreparedStatement stmt = null;
		byte[] image = null;
		System.out.println("id: " + id);
		try {
			stmt = this.con.prepareStatement("SELECT * FROM IMAGES WHERE ID=?");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			//image = IOUtils.toByteArray(rs.getBinaryStream("PICTURE"));
			java.io.InputStream is = rs.getBinaryStream("PICTURE");
			BufferedImage img = ImageIO.read(is); 
			ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
			ImageIO.write(img, "PNG", baos); 
			image = baos.toByteArray();
			rs.close();
		
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
			;
		}

		return image;
	}

	/**
	 * Takes a String with value of column AUTHOR or TITLE, retrieves image in
	 * column PICTURE blob and stores the image in specified outputpath.
	 * 
	 * @param column_name the String of the column name
	 * @param value       the String of the value of column AUTHOR or TITLE in the
	 *                    database
	 * @param outputpath  the output path of the retrieval
	 */
	public void get_byteImage(String column_name, String value, String outputpath) {
		Statement stmt;
		try {
			stmt = this.con.createStatement();
			try {
				// select corresponding metadata and byte array to the value of column AUTHOR or
				// TITLE
				// which was given by the user
				String query = "SELECT * FROM IMAGES WHERE " + column_name + "='" + value + "';";
				ResultSet rs = stmt.executeQuery(query);
				System.out.println("executed the query:" + query);
				while (rs.next()) {
					// System.out.println("executed: get binary stream ");
					String id = rs.getString("ID");
					String author = rs.getString("AUTHOR");
					// System.out.println("Putting in path: ");
					// create file at specified outputpath with the name of AUTHOR and ID from the
					// table

					System.out.println("saved image at: " + outputpath + "/" + author + id + ".png");
					// write the byte array to the file

					// byte[] buffer = new byte[1];
					java.io.InputStream is = rs.getBinaryStream("PICTURE");
					BufferedImage img = ImageIO.read(is);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ImageIO.write(img, "PNG", baos);
					byte[] array = baos.toByteArray();
					File image = new File(outputpath + "/" + author + id + ".png");
					FileOutputStream fos = new FileOutputStream(image);
					fos.write(array);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Takes value of AUTHOR or TITLE column and saves the corresponding metadata as
	 * a .txt file
	 * 
	 * @param value       the value of the specified column
	 * @param column_name either AUTHOR or TITLE
	 * @param outputpath  the output path for the retrieval
	 */
	public void get_meta(String column_name, String value, String outputpath) {
		Statement stmt = null;
		try {
			stmt = this.con.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// select all meta information of the corresponding AUTHOR or TITLE value
		// which was given by the user
		String query = "SELECT * FROM IMAGES WHERE " + column_name + "='" + value + "';";
		// System.out.println(query);
		ResultSet rs;
		try {
			rs = stmt.executeQuery(query);
			System.out.println("Executed query!");
			try {
				while (rs.next()) {
					// retrieve value of column AUTHOR, TITLE, LINK from the ResultSet
					//System.out.println("inside loop");
					String author = rs.getString("AUTHOR");
					String title = rs.getString("TITLE");
					String link = rs.getString("LINK");
					String id = rs.getString("ID");
					String metadata = "Author: " + author + "\nTitle: " + title + "\nLink: " + link;
					System.out.println("Author: " + author + "\nTitle: " + title + "\nLink: " + link);
					// File dir = new File("txtresults");
					// dir.mkdir();
					// create file with name of AUTHOR and ID from the table in the
					// directory given by the user
					FileWriter writer = new FileWriter(outputpath + "/" + author + id + ".txt");
					try {
						// write metadata to file
						BufferedWriter buff = new BufferedWriter(writer);
						try {
							buff.append(metadata);
						} finally {
							buff.flush();
							buff.close();
						}
					} finally {
						writer.close();
					}
				}

			} catch (IOException e) {
				System.out.println(e.getMessage());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

	}

	/**
	 * method to close the connection at the end of API usage, to prevent your
	 * database locking.
	 */
	public void close() {
		try {
			con.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}
