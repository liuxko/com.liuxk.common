package com.liuxk.common.file;

import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;

public class HttpFileDownload {
    /**
     * 根据http地址下载到制定位置并返回文件名
     * @param downAddress http://xxx.com/xxx/xxx.file
     * @param localAddress /xxx/xxx.file
     */
    public int  downFileByHttp(String downAddress, String localAddress){
        CloseableHttpClient client = HttpClients.createDefault();

        HttpGet get = new HttpGet(downAddress);
        try {
            CloseableHttpResponse response = null;

            String proxyHost = System.getProperty("http.ProxyHost");
            String proxyPort = System.getProperty("http.ProxyPort");
            if (proxyHost != null || proxyPort!=null) {
                HttpHost host =new HttpHost(proxyHost,Integer.parseInt(proxyPort));
                response = client.execute(host,get);
            }else {
                response = client.execute(get);
            }

            InputStream stream = response.getEntity().getContent();
            File file =new File(localAddress);
            if (!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            if (!file.exists()){
                file.createNewFile();
            }
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(localAddress));
            byte[] buffer =new byte[1];

            while(stream.read(buffer) != -1) {
                out.write(buffer);
            }
            out.close();
            stream.close();
            client.close();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 2;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 1;
        }
        return 0;
    }
}
