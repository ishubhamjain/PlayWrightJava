package Functionality.APITesting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;

import com.microsoft.playwright.options.RequestOptions;
import java.util.HashMap;
import java.util.Map;

public class PostRequest {
    public static void main(String[] args) {
        Playwright playwright = Playwright.create();
        // Prepare headers
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        APIRequestContext request = playwright.request().newContext(
                new APIRequest.NewContextOptions().setExtraHTTPHeaders(headers));

        // Prepare form data as a url-encoded body
        // Prepare form-encoded payload manually
        String postData =
                "grant_type=password" +
                "&client_id=IoTAppsTest_dp2iadqadt03_APPID" +
                "&client_secret=idcscs-26799968-34d1-49e2-b38d-2e7b1c9e26f5" +
                "&username=iot-cloudops_ww_grp@oracle.com" +
                "&password=Welcome1234%23" +
                "&scope=https://dp2iadqadt03-test.internal.iot.ocs.oraclecloud.comurn:opc:resource:consumer:saas:iotapps::all";

        // Send the post request
        // Use RequestOptions to set POST data
        APIResponse response = request.post(
                "https://idcs-59cec39f77284bd582573933afe92981.identity.oraclecloud.com/oauth2/v1/token",
                RequestOptions.create().setData(postData)
        );
        String body = "";
        // Print status and body
        System.out.println("Status: " + response.status());
        try {
            body = response.text();
           // System.out.println("Response Body:\n" + body);
        } catch (Exception e) {
            System.out.println("Failed to read response body: " + e.getMessage());
        }

        JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
        JsonElement accessToken = jsonObject.get("access_token");
        System.out.println("Access token: " + accessToken.getAsString());

        /// get devices - types

        Map<String, String> headersDeviceType = new HashMap<>();
        headersDeviceType.put("Authorization", "Bearer " + accessToken.getAsString());
        APIRequestContext requestDeviceType = playwright.request().newContext(
                new APIRequest.NewContextOptions().setExtraHTTPHeaders(headersDeviceType));
        APIResponse responseDeviceType = requestDeviceType.get("https://dp2iadqadt03-test.internal.iot.ocs.oraclecloud.com/ecp/dm/clientapi/v1/device-types/details");

        System.out.println("Status Code = " + responseDeviceType.status());
        String responseBody = responseDeviceType.text();
        System.out.println("Raw Response:");
        System.out.println(responseBody);
        request.dispose();
        requestDeviceType.dispose();
        playwright.close();
    }
}
