package cn.imzfz.wordbook;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import java.io.InputStream;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zfz on 2017/10/13.
 */

public class Translate implements Runnable{
    private static String phonetic = "";
    private static String appKey ="3800798914813276";
    private static String query = "";
    private static String salt = String.valueOf(System.currentTimeMillis());
    /*private static String from = "EN";
    private static String to = "zh-CHS";*/
    private static String from = "auto";
    private static String to = "auto";
    private static String result = "";

    public Translate(String query){
        Translate.query = query;
    }

    @Override
    public void run() {
        String sign = md5(appKey + query + salt + "JpTknj63iWTxqh3llw7Yu1L7mor0cXlW");
        Map<String, String> params = new HashMap<>();
        query = encode(query);
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);
        params.put("sign", sign);
        params.put("salt", salt);
        params.put("appKey", appKey);
        getRes(getUrlWithQueryString("http://openapi.youdao.com/api", params));
    }

    /**
     * 获取翻译结果
     */
    public static void getRes(String t){
        JSONObject mainObject = null;
        JSONObject basic = null;
        JSONArray trans = null;

        try {
            URL u = new URL(t);
            InputStream inn = u.openStream();
            ByteArrayOutputStream outt = new ByteArrayOutputStream();

            byte buf[] = new byte[2048];
            int read = 0;
            while ((read = inn.read(buf)) > 0) {
                outt.write(buf, 0, read);
            }
            byte b[] = outt.toByteArray();
            String res = new String(b, "utf-8");
            System.out.println(res);
            mainObject = new JSONObject(res);
            if(mainObject.has("basic")) {
                basic = mainObject.getJSONObject("basic");
            }
            else {
                trans = mainObject.getJSONArray("translation");
                result = format(trans);
                return;
            }
            phonetic = basic.getString("phonetic");
            trans = basic.getJSONArray("explains");
            result = format(trans);
            Log.v("result", format(trans) + "");
            Log.v("phonetic", phonetic + "");

            inn.close();
            outt.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    public static String getRes(){
        return result;
    }

    /**
     * 生成32位MD5摘要
     * @param string
     * @return
     */
    public static String md5(String string) {
        if(string == null){
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};

        try{
            byte[] btInput = string.getBytes("utf-8");
            /** 获得MD5摘要算法的 MessageDigest 对象 */
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            /** 使用指定的字节更新摘要 */
            mdInst.update(btInput);
            /** 获得密文 */
            byte[] md = mdInst.digest();
            /** 把密文转换成十六进制的字符串形式 */
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        }catch(NoSuchAlgorithmException | UnsupportedEncodingException e){
            return null;
        }
    }

    /**
     * 根据api地址和参数生成请求URL
     * @param url
     * @param params
     * @return
     */
    public static String getUrlWithQueryString(String url, Map<String, String> params) {
        if (params == null) {
            return url;
        }

        StringBuilder builder = new StringBuilder(url);
        if (url.contains("?")) {
            builder.append("&");
        } else {
            builder.append("?");
        }

        int i = 0;
        for (String key : params.keySet()) {
            String value = params.get(key);
            if (value == null) { // 过滤空的key
                continue;
            }

            if (i != 0) {
                builder.append('&');
            }

            builder.append(key);
            builder.append('=');
            builder.append(encode(value));

            i++;
        }

        return builder.toString();
    }
    /**
     * 进行URL编码
     * @param input
     * @return
     */
    public static String encode(String input) {
        if (input == null) {
            return "";
        }

        try {
            return URLEncoder.encode(input, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return input;
    }

    public static String format(JSONArray array){
        StringBuilder t = new StringBuilder();
        try {
            for (int i = 0; i < array.length(); i++) {
                t.append(array.get(i).toString() + "\n");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return t.toString();
    }

    public static String getPhonetic() {
        return phonetic;
    }

    public static void setPhonetic(String phonetic) {
        Translate.phonetic = phonetic;
    }

    public static String getQuery() {
        return query;
    }

    public static void setQuery(String query) {
        Translate.query = query;
    }
}
