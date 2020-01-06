package com.tianshuo.beta.sso.client.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * @author tianshuo
 */
public class HttpUtil {
    private String charsetName = "UTF-8";
    private String request = "";
    private String response = "";
    private String sendUrl;
    private int readTimeout;
    private int connectionTimeout;
    private String requestType = "POST";
    private String contentType = "application/x-www-form-urlencoded;charset=UTF-8";

    public HttpUtil() {
        this.readTimeout = 10000;
        this.connectionTimeout = 10000;
    }

    public HttpUtil(String sendUrl) {
        this.sendUrl = sendUrl;
        this.readTimeout = 10000;
        this.connectionTimeout = 10000;
    }

    public HttpUtil(String sendUrl, int connectionTimeout, int readTimeout) {
        this.sendUrl = sendUrl;
        this.readTimeout = readTimeout;
        this.connectionTimeout = connectionTimeout;
    }

    public String getCharsetName() {
        return this.charsetName;
    }

    public void setCharsetName(String charsetName) {
        this.charsetName = charsetName;
    }

    public String getRequest() {
        return this.request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return this.response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getSendUrl() {
        return this.sendUrl;
    }

    public void setSendUrl(String sendUrl) {
        this.sendUrl = sendUrl;
    }

    public int getReadTimeout() {
        return this.readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getConnectionTimeout() {
        return this.connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public String getRequestType() {
        return this.requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public boolean call() {
        HttpURLConnection connection = null;
        BufferedReader bufferReader = null;

        boolean var7;
        try {
            URL logoutUrl = new URL(this.sendUrl);
            String output = this.request;
            connection = (HttpURLConnection) logoutUrl.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod(this.requestType);
            connection.setReadTimeout(this.readTimeout);
            connection.setConnectTimeout(this.connectionTimeout);
            connection.setRequestProperty("Content-Length", Integer.toString(output.getBytes().length));
            connection.setRequestProperty("Content-Type", this.contentType);
            connection.setRequestProperty("Accept", "*/*");
            OutputStream outStrm = connection.getOutputStream();
            outStrm.write(output.getBytes(this.charsetName));
            outStrm.flush();
            outStrm.close();
            bufferReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), this.charsetName));

            for (String line = null; (line = bufferReader.readLine()) != null; this.response = this.response + line) {
            }

            if (null == this.response || "".equals(this.response)) {
                throw new RuntimeException("内部异常");
            }

            var7 = true;
        } catch (SocketTimeoutException var17) {
            throw new RuntimeException("Socket Timeout Detected while attempting to send message to [" + this.sendUrl + "]");
        } catch (Exception var18) {
            throw new RuntimeException("Error Sending message to url endpoint [" + this.sendUrl + "].  Error is [" + var18.getMessage() + "]");
        } finally {
            if (bufferReader != null) {
                try {
                    bufferReader.close();
                } catch (IOException var16) {
                    var16.printStackTrace();
                    throw new RuntimeException("内部异常");
                }
            }

            if (connection != null) {
                connection.disconnect();
            }

        }

        return var7;
    }

    public String invokeRequest(String reqUrl, String params, String encode, String headFormate) {
        HttpURLConnection connection = null;
        BufferedReader bufferReader = null;

        try {
            URL logoutUrl = new URL(reqUrl);
            connection = (HttpURLConnection) logoutUrl.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod(this.requestType);
            connection.setReadTimeout(this.readTimeout);
            connection.setConnectTimeout(this.connectionTimeout);
            connection.setRequestProperty("Content-Length", Integer.toString(params.getBytes().length));
            connection.setRequestProperty("Content-Type", headFormate);
            OutputStream outStrm = connection.getOutputStream();
            outStrm.write(params.getBytes(encode));
            outStrm.flush();
            outStrm.close();
            bufferReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), encode));
            String line = null;
            StringBuilder response = new StringBuilder();

            while ((line = bufferReader.readLine()) != null) {
                response.append(line);
            }

            String var12 = response.toString();
            return var12;
        } catch (Exception var21) {
            throw new RuntimeException("内部异常");
        } finally {
            if (bufferReader != null) {
                try {
                    bufferReader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            if (connection != null) {
                connection.disconnect();
            }

        }
    }

    public static void main(String[] args) {
        String url = "http://101.201.106.254/smsJson.aspx";
        String param = "action=send&userid=&account=szychy&password=58A8EE89486F5219551FC03B1367D2EB&mobile=18565746607,18588457848&content=您的验证码为：8888 【云材互联】";
        HttpUtil client = new HttpUtil(url);
        client.setRequest(param);
        client.call();
        System.out.println("11111===================>" + client.getResponse());
    }
}
