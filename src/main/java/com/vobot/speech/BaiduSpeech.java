package com.vobot.speech;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.vobot.dto.Response;
import com.vobot.util.PropertiesReader;

public class BaiduSpeech {
	private static final Logger log = Logger.getLogger(BaiduSpeech.class);
	
	private static final String serverURL = "http://vop.baidu.com/server_api";
	private static final String TOKEN_URL = "https://openapi.baidu.com/oauth/2.0/token?grant_type=client_credentials"
			+ "&client_id=%s&client_secret=%s";

	private static String token = "";

	// server configurations
	private static final String apiKey = PropertiesReader.getInstnace().getApiKey();
	private static final String secretKey = PropertiesReader.getInstnace().getSecretKey();
	private static final String cuid = PropertiesReader.getInstnace().getCuid();

	private static BaiduSpeech INSTANCE = new BaiduSpeech();

	private BaiduSpeech() {

	}

	public static BaiduSpeech getInstance() {
		synchronized (BaiduSpeech.class) {
			if (StringUtils.isBlank(token)) {
				getToken();
			}
			return INSTANCE;
		}
	}

	private static void getToken() {
		String url = String.format(TOKEN_URL, apiKey, secretKey);

		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(url)
					.openConnection();
			token = getToken(conn);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String speech(File file) {
			if (!file.getName().endsWith("amr")) {
				return "";
			}

			try {
				HttpURLConnection conn = (HttpURLConnection) new URL(serverURL)
						.openConnection();

				// construct params
				JSONObject params = new JSONObject();
				params.put("format", "amr");
				params.put("rate", 8000);
				params.put("channel", "1");
				params.put("token", token);
				params.put("cuid", cuid);
				params.put("len", file.length());
				params.put("speech",
						DatatypeConverter.printBase64Binary(loadFile(file)));

				// add request header
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type",
						"application/json; charset=utf-8");

				conn.setDoInput(true);
				conn.setDoOutput(true);

				// send request
				DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
				wr.writeBytes(params.toString());
				wr.flush();
				wr.close();

				return getVoiceText(conn);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (ProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return "";
	}

	private static String getToken(HttpURLConnection conn) throws Exception {
		String res = getResponse(conn);
		JSONObject tokenJO = new JSONObject(res);

		return tokenJO.getString("access_token");
	}

	private String getVoiceText(HttpURLConnection conn) throws Exception {
		String res = getResponse(conn);

		Gson gson = new Gson();
		Response resObj = gson.fromJson(res, Response.class);

		if (resObj.getResult() != null && resObj.getResult().size() > 0) {
			return resObj.getResult().get(0);
		}

		return "";
	}

	private static String getResponse(HttpURLConnection conn) throws Exception {
		if (conn.getResponseCode() != 200) {
			return "";
		}
		InputStream is = conn.getInputStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		String line;
		StringBuffer response = new StringBuffer();
		while ((line = rd.readLine()) != null) {
			response.append(line);
			response.append('\r');
		}
		rd.close();

		log.info(response.toString());
		return response.toString();
	}

	private byte[] loadFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);

		long length = file.length();
		byte[] bytes = new byte[(int) length];

		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		if (offset < bytes.length) {
			is.close();
			throw new IOException("Could not completely read file "
					+ file.getName());
		}

		is.close();
		return bytes;
	}
}
