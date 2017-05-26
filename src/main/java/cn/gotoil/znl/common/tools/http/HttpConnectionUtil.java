package cn.gotoil.znl.common.tools.http;

        import cn.gotoil.znl.common.union.SSLUtil;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

public class HttpConnectionUtil {
    private HttpURLConnection conn;
    private String connectUrl;

    public HttpConnectionUtil(String connectUrl){
        this.connectUrl = connectUrl;
    }

    public void init() throws Exception{
        URL url = new URL(connectUrl);
        System.setProperty("java.protocol.handler.pkgs", "javax.net.ssl");
        HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String urlHostName, SSLSession session) {
                return urlHostName.equals(session.getPeerHost());
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
        URLConnection conn = url.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setReadTimeout(60000);
        conn.setConnectTimeout(30000);
        if (conn instanceof HttpsURLConnection){
            HttpsURLConnection httpsConn = (HttpsURLConnection)conn;
            httpsConn.setSSLSocketFactory(SSLUtil.getInstance().getSSLSocketFactory());
        } else if (conn instanceof HttpURLConnection){
            HttpURLConnection httpConn = (HttpURLConnection)conn;
        } else {
            throw new Exception("不是http/https协议的url");
        }
        this.conn = (HttpURLConnection)conn;
        initDefaultPost();
    }

    public void destory(){
        try{
            if(this.conn!=null){
                this.conn.disconnect();
            }
        }catch(Exception e){

        }
    }

    private void initDefaultPost() throws Exception{
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("POST");
        conn.setUseCaches(false);
        conn.setInstanceFollowRedirects(true);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    }

    public byte[] postParams(Map<String, String> params,boolean readreturn) throws IOException {
        StringBuilder outBuf = new StringBuilder();
        boolean isNotFirst = false;
        for (Map.Entry<String, String> entry: params.entrySet()){
            if (isNotFirst)
                outBuf.append('&');
            isNotFirst = true;
            outBuf
                    .append(entry.getKey())
                    .append('=')
                    .append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        System.out.println("参数:"+outBuf.toString());
        return postParams(outBuf.toString(),readreturn);
    }

    public byte[] postParams(String message,boolean readreturn) throws IOException {
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        out.write(message.getBytes("UTF-8"));
        out.close();
        if(readreturn){
            return readBytesFromStream(conn.getInputStream());
        }else{
            return null;
        }
    }

    public byte[] postParams(byte[] message,boolean readreturn) throws IOException {
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        out.write(message);
        out.close();
        if(readreturn){
            return readBytesFromStream(conn.getInputStream());
        }else{
            return null;
        }
    }

    private byte[] readBytesFromStream(InputStream is) throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int readLen;
        byte[] tmpBuf = new byte[4096];
        while ((readLen = is.read(tmpBuf)) > 0)
            baos.write(tmpBuf, 0, readLen);
        is.close();
        return baos.toByteArray();
    }

    public HttpURLConnection getConn() {
        return conn;
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            System.out.println("请求："+urlNameString);
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();

            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }

            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static String postMessage(String notifyUrl, String notifyContent) {
        //todo
        return notifyContent;
    }
}
