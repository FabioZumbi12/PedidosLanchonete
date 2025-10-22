package com.github.fabiomagalhaes.pedidoslanchonete.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class Helper {
    public static double dollarRate = 1.0;
    public enum FoodType {
        LANCHE,
        PORCAO,
        BEBIDA
    }

    public static String formatPrice(double price) {
        return "R$ " + String.format("%.2f", price);
    }

    public static String getDollarPrice(double price){
        return "USD " + String.format("%.2f", price * dollarRate);
    }

    public static String getImageType(FoodType type) {
        return switch (type) {
            case FoodType.PORCAO -> "/images/portion.png";
            case FoodType.LANCHE -> "/images/sandwich.png";
            default -> "/images/refri.png";
        };
    }

    public static void setupDollarRate() {
        double rate = 0;
        try {
            URL url = new URL("https://api.frankfurter.app/latest?from=BRL&to=USD");
            JSONObject json = getJsonObject(url);
            rate = json.getJSONObject("rates").getDouble("USD");

        } catch (Exception e) {
            e.printStackTrace();
        }
        dollarRate = rate;
    }

    private static JSONObject getJsonObject(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            content.append(line);
        }
        in.close();
        conn.disconnect();

        return new JSONObject(content.toString());
    }
}
