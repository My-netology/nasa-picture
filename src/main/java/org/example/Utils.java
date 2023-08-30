package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.FileOutputStream;
import java.io.IOException;

public class Utils {
    private static final CloseableHttpClient httpClient = HttpClientBuilder
            .create()
            .setDefaultRequestConfig(
                    RequestConfig
                            .custom()
                            .setConnectTimeout(5000)
                            .setSocketTimeout(30000)
                            .setRedirectsEnabled(false)
                            .build()
            )
            .build();
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String getPictureUri(String uri) {
        try (CloseableHttpResponse response = httpClient.execute(new HttpGet(uri))) {
            return mapper.readValue(response.getEntity().getContent(), NASAPictureData.class).getUrl();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static String getPictureHDUri(String uri) {
        try (CloseableHttpResponse response = httpClient.execute(new HttpGet(uri))) {
            return mapper.readValue(response.getEntity().getContent(), NASAPictureData.class).getHdUrl();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void getPicture(String uri) {
        try (CloseableHttpResponse response = httpClient.execute(new HttpGet(uri))) {
            HttpEntity entity = response.getEntity();
            String[] uriComponents = uri.split("/");
            savePicture(uriComponents[uriComponents.length - 1], entity);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void savePicture(String fileName, HttpEntity data) {
        try (FileOutputStream ioDevice = new FileOutputStream(fileName)) {
            data.writeTo(ioDevice);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
