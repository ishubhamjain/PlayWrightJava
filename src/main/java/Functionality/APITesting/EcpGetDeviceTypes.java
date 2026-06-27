package Functionality.APITesting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.microsoft.playwright.*;

import java.util.HashMap;
import java.util.Map;

public class EcpGetDeviceTypes {
    public static void main(String[] args) {

        String token = "eyJ4NXQjUzI1NiI6Inh3NnI5QWlnS0hYMDBIblBFeUZ6VGtYNDVva2ZmZUpkZDAxMm5NZmZZajgiLCJ4NXQiOiJMV1lsbnczaEhqYnBOd281MXBWVGwtUGUwUEkiLCJraWQiOiJTSUdOSU5HX0tFWSIsImFsZyI6IlJTMjU2In0.eyJjbGllbnRfb2NpZCI6Im9jaWQxLmRvbWFpbmFwcC5vYzEucGh4LmFtYWFhYWFhYXF0cDViYWE0Y2Q2MzVhZWhncWM1YWFnZnI2Y3V2aTRrdmxteXdrc2J1d2Jvbnc1YnFzcSIsInVzZXJfdHoiOiJBbWVyaWNhL0NoaWNhZ28iLCJzdWIiOiJpb3QtY2xvdWRvcHNfd3dfZ3JwQG9yYWNsZS5jb20iLCJ1c2VyX2xvY2FsZSI6ImVuIiwic2lkbGUiOjQ4MCwidXNlci50ZW5hbnQubmFtZSI6ImlkY3MtNTljZWMzOWY3NzI4NGJkNTgyNTczOTMzYWZlOTI5ODEiLCJpc3MiOiJodHRwczovL2lkZW50aXR5Lm9yYWNsZWNsb3VkLmNvbS8iLCJkb21haW5faG9tZSI6InVzLXBob2VuaXgtMSIsImNhX29jaWQiOiJvY2lkMS50ZW5hbmN5Lm9jMS4uYWFhYWFhYWE3azJydWdsd3JoNHJ0a2Vxc3BreHNua3I2ejZyd3N3NjQ1bDc0MmJ1cmR4Z3pkbHBvZmVxIiwidXNlcl90ZW5hbnRuYW1lIjoiaWRjcy01OWNlYzM5Zjc3Mjg0YmQ1ODI1NzM5MzNhZmU5Mjk4MSIsImNsaWVudF9pZCI6IklvVEFwcHNUZXN0X2RwMmlhZHFhZHQwM19BUFBJRCIsImRvbWFpbl9pZCI6Im9jaWQxLmRvbWFpbi5vYzEuLmFhYWFhYWFhNjRwbXB0dGU1ZjU1Y3NmMnV6N2p0dTN4andheXI2Y2N1YjNtbWNzcXIyemVtYXM3cWt0cSIsInN1Yl90eXBlIjoidXNlciIsInNjb3BlIjoidXJuOm9wYzpyZXNvdXJjZTpjb25zdW1lcjpzYWFzOmlvdGFwcHM6OmFsbCIsInVzZXJfb2NpZCI6Im9jaWQxLnVzZXIub2MxLi5hYWFhYWFhYW8ydmdhejdsaG14aHZtZmR1dGNkZ3E1cHBwbjRycW56bGhmNmlsMmk1bXJoNXBkMm9zZGEiLCJjbGllbnRfdGVuYW50bmFtZSI6ImlkY3MtNTljZWMzOWY3NzI4NGJkNTgyNTczOTMzYWZlOTI5ODEiLCJyZWdpb25fbmFtZSI6InVzLXBob2VuaXgtaWRjcy0zIiwidXNlcl9sYW5nIjoiZW4iLCJleHAiOjE3NzMzMjE4MzIsImlhdCI6MTc3MzMxODIzMiwiY2xpZW50X2d1aWQiOiIwNzZjYTVkNTIzN2M0OWVmYTljZjdiODk1MWFjZmY5OCIsImNsaWVudF9uYW1lIjoiSW9UQXBwc1Rlc3RfZHAyaWFkcWFkdDAzIiwidGVuYW50IjoiaWRjcy01OWNlYzM5Zjc3Mjg0YmQ1ODI1NzM5MzNhZmU5Mjk4MSIsImp0aSI6IjkwZDE4OTY3NDM3ODRlYmVhNTc4YTc0ZjQ0MTY2OTJkIiwiZ3RwIjoicm8iLCJ1c2VyX2Rpc3BsYXluYW1lIjoieHl6MSBxYXRlc3QiLCJvcGMiOnRydWUsInN1Yl9tYXBwaW5nYXR0ciI6InVzZXJOYW1lIiwicHJpbVRlbmFudCI6ZmFsc2UsInRva190eXBlIjoiQVQiLCJjYV9ndWlkIjoiY2FjY3QtYjgxYzhmMDA2NDcxNDFjMTg3ZDVlNmRkYjNkNWQxYTgiLCJhdWQiOiJodHRwczovL2RwMmlhZHFhZHQwMy10ZXN0LmludGVybmFsLmlvdC5vY3Mub3JhY2xlY2xvdWQuY29tIiwiY2FfbmFtZSI6ImlvdGRldiIsInN0dSI6IklPVEludGVsbGlnZW50QXBwIiwidXNlcl9pZCI6IjJiZTA5OTk5ZGFkMDQyYmZhYjNhZThlZTgzZDlmYjdkIiwiZG9tYWluIjoiZWNwZGV2IiwidGVuYW50X2lzcyI6Imh0dHBzOi8vaWRjcy01OWNlYzM5Zjc3Mjg0YmQ1ODI1NzM5MzNhZmU5Mjk4MS5pZGVudGl0eS5vcmFjbGVjbG91ZC5jb206NDQzIiwicmVzb3VyY2VfYXBwX2lkIjoiMDc2Y2E1ZDUyMzdjNDllZmE5Y2Y3Yjg5NTFhY2ZmOTgifQ.BZIWv-kl8NgbVwPjDDO1nYRv3MpmMk1F0lWs6gO-vkyQHYS6Si9XZY7zmFjTOh1Ud5JvbmcL6ZKSCv32VMoFN9vzB6Z4e47PwGGXsneVWY_1bZSkJ_KM3W19BrRcYSzYxsqmEx0AafHCdmekc4yn5k7VqgGXBlU4jfDSp8MGbqyWsTfVVlyMQj88OJTmVDR_7Q0C0z97GteUL-ftEyj2qcEPGf9Qi7-4YAQUjkRJ_NUVaDpjuuMfhTWdZ3e13NM5T4S-WOmoGv_cLKxfbwah3p44bzxMPV0HtvxTk3SltEcnXCXcGN7nHMLR0Qg4Am6IexUMHNmFZvbpA04zNrBE8Q";
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);

        Playwright playwright = Playwright.create();
        // Create context, passing headers directly (using available overload)
        APIRequestContext request = playwright.request().newContext(
                new APIRequest.NewContextOptions().setExtraHTTPHeaders(headers));
        APIResponse response = request.get("https://dp2iadqadt03-test.internal.iot.ocs.oraclecloud.com/ecp/dm/clientapi/v1/device-types/details");

        System.out.println("Status Code = " + response.status());
        String responseBody = response.text();
        System.out.println("Raw Response:");
        System.out.println(responseBody);


//
//        // Parse device types
//        try {
//            JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
//
//            // Try to find device types array/object in the response
//            // The structure may vary; common property names: "items", "deviceTypes", etc.
//            if (jsonObject.has("items")) {
//                System.out.println("Device Types:");
//                for (JsonElement element : jsonObject.getAsJsonArray("items")) {
//                    System.out.println(element);
//                }
//            } else if (jsonObject.has("deviceTypes")) {
//                System.out.println("Device Types:");
//                for (JsonElement element : jsonObject.getAsJsonArray("deviceTypes")) {
//                    System.out.println(element);
//                }
//            } else {
//                System.out.println("No device types property found. Showing full response object:");
//                System.out.println(jsonObject);
//            }
//        } catch (Exception e) {
//            System.out.println("Error parsing response as device types: " + e.getMessage());
//        }

        request.dispose();
        playwright.close();
    }
}
