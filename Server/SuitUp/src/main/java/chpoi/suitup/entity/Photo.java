package chpoi.suitup.entity;

import net.sf.json.JSONObject;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Photo Class
 * 
 * @author Dong Zhiyuan
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Photo {

	private String _id;
	private Integer size;
	private String file;
	
	public Photo() {}

	/**
     * This method changes JSONObject to Photo
     * 
     * @param json the JSONObject to change
     * @return the expected photo
     */
	public static Photo JSONToPhoto(JSONObject json){
		Photo photo = null;
        try {
			ObjectMapper objectMapper = new ObjectMapper();
			photo = objectMapper.readValue(json.toString(), Photo.class);
		} catch (Exception e) {	}
		return photo;
	}
	
	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	
}
