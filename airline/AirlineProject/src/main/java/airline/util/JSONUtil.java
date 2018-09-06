package airline.util;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.json.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtil {
	
	public static JSONObject requestToJSON(HttpServletRequest req) throws IOException{
		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = req.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);
		} catch (Exception e) {
			/* report an error */ }

		try {
			JSONObject jsonObject = new JSONObject(jb.toString());
			return jsonObject;
		}catch (JSONException e) {
			// crash and burn
			throw new IOException("Error parsing JSON request string");
		}
	}
	
	public static JSONObject requestToJSONArray(HttpServletRequest req) throws IOException{
		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = req.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);
		} catch (Exception e) {
			/* report an error */ }

		try {
			JSONObject jsonObject = HTTP.toJSONObject(jb.toString());
			return jsonObject;
		}catch (JSONException e) {
			// crash and burn
			throw new IOException("Error parsing JSON request string");
		}
	}

}
