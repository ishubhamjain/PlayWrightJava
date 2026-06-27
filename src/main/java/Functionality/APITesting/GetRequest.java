package Functionality.APITesting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;

public class GetRequest {
    public static void main(String[] args) {
        Playwright playwright = Playwright.create();
        APIRequestContext request = playwright.request().newContext();
        APIResponse response =request.get("https://dogapi.dog/api/v2/breeds?page%5Bnumber%5D=2&page%5Bsize%5D=2");

        System.out.println("Status Code = " + response.status());
        String responseBody = response.text();
        System.out.println(responseBody);

        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        JsonElement jsonElement = jsonObject.get("links");
        System.out.println(jsonElement.getAsJsonObject());
        request.dispose();
        playwright.close();
    }
}
