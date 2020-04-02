package de.bit.pl02.pp5.task03;

/** StoreImageResponse returns the API user a message whether or not the file upload
 * has succeeded. 
 * 
 * @author Shreya Kapoor
 * @author Sophia Krix
 * @author Gemma van der Voort 
 * 
 */
public class StoreImageResponse {

	    private String fileName;
	    
	    /**Constructor returns the API user a message whether or not the file upload
	     * has succeeded. See store method in FileController.
	     * 
	     * @param fileName name of the file when uploaded by the API user
	     */
		public StoreImageResponse(String fileName) {
	        this.fileName = fileName;
	    }
	    public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

}

