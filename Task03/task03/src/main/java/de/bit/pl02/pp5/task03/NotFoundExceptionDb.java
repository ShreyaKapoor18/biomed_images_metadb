package de.bit.pl02.pp5.task03;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * Returns error 'Not Found' Exception with the message: "DatabaseId not found"
 * when the API user uses a DatabaseId that is not in the allowedDBs.txt file. 
 * @author Shreya Kapoor
 * @author Sophia Krix
 * @author Gemma van der Voort 
 *
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="DatabaseId not found")
public class NotFoundExceptionDb extends Exception {
	/**
	 * Returns error 'Not Found' Exception with the message: "DatabaseId not found"
	 * when the API user uses a DatabaseId that is not in the allowedDBs.txt file.
	 *
	 * @param msg message to be displayed to the user.
	 */
    public NotFoundExceptionDb(String msg) {
        super(msg);
    }
}