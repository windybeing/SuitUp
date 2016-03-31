package hk.suitup.Service;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sony on 2015/7/30.
 */
public class GetImageService {

    private List<String> temp;
    public GetImageService(List<String> id,String type) {

        Map map = new HashMap();
        map.put("function", "Get");

        Gson gson = new Gson();
        String func_name=gson.toJson(map);

        ImageInfo imageInfo = new ImageInfo(id,type);
        String para = gson.toJson(imageInfo);

        temp=new ArrayList<String>();
        temp.add(func_name);
        temp.add(para);

    }

    public String formLength(){
        int length=2*(temp.size()-1)+2+temp.get(0).length()+temp.get(1).length();
        Map map = new HashMap();

        Gson gson = new Gson();
        map.put("length", length);
        String length_of_message = gson.toJson(map)+"<EOF>";

        return length_of_message;
    }



    public List<String> getTemp() {
        return temp;
    }

    public void setTemp(List<String> temp) {
        this.temp = temp;
    }

    private class ImageInfo{
        private List<String> photos_id;
        private String type;

        public ImageInfo(List<String> photos_id, String type) {
            this.photos_id = photos_id;
            this.type = type;
        }

        public List<String> getPhoto_id() {
            return photos_id;
        }

        public void setPhoto_id(List<String> photo_id) {
            this.photos_id = photo_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
