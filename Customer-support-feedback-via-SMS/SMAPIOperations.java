
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class SMAPIOperations
{

	String apikey;

	public SMAPIOperatios(String apikey)
	{
		this.apikey = apikey;
	}

	public String generateSignedLink(int surveyId, String destination) throws JSONException, UnirestException
	{

		String url = "http://api.gupshup.io/sm/api/smartmsg/msg/"+ surveyId +"/signedlink";
		HttpResponse<String> data = Unirest.post(url).header("apikey", this.apikey).header("Content-Type", "application/x-www-form-urlencoded").field("destination", destination).asString();
		JSONArray arr = new JSONArray(data.getBody().toString());
		return arr.getJSONObject(0).getString("embedlink").toString();

	}

	public void sendSMS(String mobile, String message, String link) throws UnsupportedEncodingException, UnirestException, JSONException
	{
		String messageBody = message+"\n"
				+link;
		String url = "http://api.gupshup.io/sm/api/sms/msg";
		HttpResponse<String> data = Unirest.put(url).header("apikey", this.apikey).header("Content-Type", "application/x-www-form-urlencoded").field("destination", mobile).field("text",messageBody).asString();
	}

}
