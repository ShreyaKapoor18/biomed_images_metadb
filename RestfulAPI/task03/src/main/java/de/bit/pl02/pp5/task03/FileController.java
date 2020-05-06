package de.bit.pl02.pp5.task03;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import de.bit.pl02.pp5.task02.Database;
import de.bit.pl02.pp5.task02.MetaDataAPI;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
/**
 * RESTful API Controller class that implements controlled access to a database created
 * by the user via the url: http://localhost:8080/{databaseId}/store or http://localhost:8080/{databaseId}/get
 * 
 * @author Shreya Kapoor
 * @author Sophia Krix
 * @author Gemma van der Voort 
 *
 */
@RestController
public class FileController {
	
	public Map<String, String> allowedDB;
	
	/**
	 * Constructor opens file 'config.json', which contains all databases 
	 * that the API user is allowed to access, and stores it in the Hashmap allowedDB.
	 */
	public FileController() { 
		JSONParser parser = new JSONParser();
		allowedDB = new HashMap<String, String>();
        JSONObject jsonObject=null;
		try {
			//path is hardcoded to json object in task03
			jsonObject = (JSONObject) parser.parse(new FileReader("config.json"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
        JSONArray databases = (JSONArray) jsonObject.get("allowedDatabasesConfiguration");
        for (Object i:databases) {
        	JSONObject database=(JSONObject) i;
        	String id = database.getAsString("databaseId");
        	String path = database.getAsString("path");
        	allowedDB.put(id, path);

    	}
	}

	/**
	 * Updates the database under databaseId with a new image file and metadata.
	 * File and databaseId are required parameters, the metadata are not.
	 * 
	 * @param databaseId for valid databaseId, see allowedDBs.txt. Required.
	 * @param file new image file with .png, .jpg or .jpeg extension. Required.
	 * @param author author of the image. Optional
	 * @param title title of the image. Optional
	 * @param link  link of the image. Optional
	 * @return filename if successful.
	 * @throws NotFoundExceptionDb if input database path or id are not valid according to allowedDBs.txt.
	 */
	@PostMapping("/{databaseId}/store")
	public StoreImageResponse store(@PathVariable String databaseId, @RequestParam("file") MultipartFile file, @RequestParam(value= "author", required = false) String author,
			@RequestParam(value = "title", required = false) String title, @RequestParam(value = "link", required = false) String link) throws NotFoundExceptionDb {
		// if-else statement checks if the user has queried a valid databaseId
		if (allowedDB.containsKey(databaseId)) {
			//opens the database
			Database db = new Database(allowedDB.get(databaseId), databaseId);
			try {
				//adds image and metadata to the database
				db.storePictureForAPI(file.getBytes(), author, title, link);
			} catch (IOException e) {
				e.printStackTrace();
			}
			db.close();
			// success statement returned to the user
			return new StoreImageResponse("You have succesfully uploaded file: "+ file.getOriginalFilename());
		} else {
			//Exception if wrong databaseId is submitted.
			throw new NotFoundExceptionDb("Error: database name not allowed.");
		}
	}
	/**
	 * Search the database with databaseId by author and/or title. Returns matches with
	 * id and metadata in a list. The id can be used to look at the actual image using 
	 * the other get method (see below). The metadata can be used to distinguish the 
	 * results to find the wanted image id when multiple results are returned.
	 * Overloaded. Author and title cannot both be null.
	 * If failure due to no result, returns empty ArrayList.
	 * 
	 * @param databaseId for valid databaseId, see allowedDBs.txt. Required.
	 * @param author optional query, case sensitive.
	 * @param title optional query, case sensitive.
	 * @return matches to query with id and metadata information in a list.
	 * @throws NotFoundExceptionDb if input database path or id are not valid according to allowedDBs.txt.
	 */
	@GetMapping(value= "/{databaseId}/get")
	@ResponseBody
	public List<MetaDataAPI> get(@PathVariable String databaseId, @RequestParam(value= "author", required = false) String author, @RequestParam(value = "title", required = false) String title) throws NotFoundExceptionDb {
		// if-else statement checks if the user has queried a valid databaseId
		if (allowedDB.containsKey(databaseId)) {
			//opens the database
			Database db = new Database(allowedDB.get(databaseId), databaseId);
			List<MetaDataAPI> ids = db.getForAPI(author, title);
			return ids;
		} else {
			//Exception if wrong databaseId is submitted.
			throw new NotFoundExceptionDb("Error: database name not allowed.");
			 
		        
		}

	}
	/**
	 * gives the id and gets the .png image in return, regardless of upload format.
	 * Search the database with databaseId by id. Returns unique match as a picture.
	 * The id can be found using the other get method (see above), which searches by
	 * author and/or title.
	 * Overloaded.
	 * If failure due to no result, returns empty byte[].
	 * 
	 * @param databaseId for valid databaseId, see allowedDBs.txt. Required.
	 * @param id identification of database entry, to use as query. Required.
	 * @return image in .png format.
	 * @throws NotFoundExceptionDb if input database path or id are not valid according to allowedDBs.txt.
	 */
	@GetMapping(value = "/{databaseId}/get", params = {"id"}, produces = MediaType.IMAGE_PNG_VALUE)
	public @ResponseBody byte[] get(@PathVariable String databaseId,  @RequestParam("id") int id) throws NotFoundExceptionDb {
		// if-else statement checks if the user has queried a valid databaseId		
		if (allowedDB.containsKey(databaseId)) {
			//opens the database
			Database db = new Database(allowedDB.get(databaseId), databaseId);
			byte[] imageArray = db.getForAPI(id);
			db.close();
			return imageArray;
		} else {
			//Exception if wrong databaseId is submitted.
			System.out.println("allowedDB error");
			throw new NotFoundExceptionDb("Error: databasename not allowed.");
		}
	}

	
}