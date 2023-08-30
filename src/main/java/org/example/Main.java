package org.example;

import static org.example.Utils.*;

public class Main {
    public static void main(String[] args) {
        String uri = "https://api.nasa.gov/planetary/apod?api_key=gRBZnjjepuCF5Cmodh18FDOuV657rYAOgRlV5KWA";
        String pictureUri = getPictureUri(uri);
        if (pictureUri == null) {
            System.out.println("No data.");
        } else {
            getPicture(pictureUri);
        }
        String pictureHDUri = getPictureHDUri(uri);
        if (pictureHDUri == null) {
            System.out.println("No data.");
        } else {
            getPicture(pictureHDUri);
        }
    }
}