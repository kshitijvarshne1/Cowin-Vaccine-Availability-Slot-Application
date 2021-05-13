/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 07-May-21
 *   Time: 6:15 PM
 *   File: WebController.java
 */

package com.example.cowinvaccineavailablityslotapplication;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Controller
public class WebController {

    @Autowired
    PinCodeAndLinkService pinCodeAndLinkService;

    @RequestMapping("/")
    public String working(Model attribute) {
        attribute.addAttribute("show", "Wait for a time , link not generated yet</");
        attribute.addAttribute("link", "/");
        return "Web";
    }

   @Scheduled(cron = "*/10 * * * * *")
    public String writeSession() throws InterruptedException {
        while (true) {
            System.out.println("Hello dude writeSession called");
            List<PincodeAndLink> list = pinCodeAndLinkService.getAll();
            for (int j = 0; j < list.size(); j++) {
                String pin = list.get(j).getPincode();
                String link = list.get(j).getLink();
                System.out.println(pin + " pin and link " + link);
                try {
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    String date = now.format(formatter);
                    System.out.println("Current DateTime after Formatting:: " + date );
                    URL uRL = new URL("https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByPin?pincode=" + pin + "&date=" + date);

                    System.out.println("URL :-"+uRL);
                    String string3 = "";
                    HttpURLConnection httpURLConnection = (HttpURLConnection) uRL.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setRequestProperty("Contents-Type", "application/json");
                    httpURLConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
                    int n = httpURLConnection.getResponseCode();
                    System.out.println("n - >   " + n);
                    System.out.println(httpURLConnection.getResponseMessage());
                    if (n == 200 || n==403) {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                        StringBuffer stringBuffer = new StringBuffer();
                        while ((string3 = bufferedReader.readLine()) != null) {
                            stringBuffer.append(string3);
                        }
                        if(n==403){
                            System.out.println(stringBuffer.toString());
                            return "";
                        }
                        JSONObject jsonObject = new JSONObject(stringBuffer.toString());
                        JSONArray array = jsonObject.getJSONArray("sessions");
                        if (array != null) {
                            String[] state_name = new String[array.length()];
                            String[] state_address = new String[array.length()];
                            long[] availableCapacity = new long[array.length()];
                            long[] pincode = new long[array.length()];
                            long[] min_age_limit = new long[array.length()];

                            String result = "";
                            for (int i = 0; i < array.length(); i++) {
                                state_name[i] = array.getJSONObject(i).getString("name");
                                state_address[i] = array.getJSONObject(i).getString("address");
                                availableCapacity[i] = array.getJSONObject(i).getLong("available_capacity");
                                pincode[i] = array.getJSONObject(i).getLong("pincode");
                                min_age_limit[i] = array.getJSONObject(i).getLong("min_age_limit");
                                result += i + 1 + "+:-+" + "Vaccination+centres+for+minimum+age+limit+" + min_age_limit[i] + "%0A" + state_name[i].replace(" ", "+") + "+%0A"
                                        + state_address[i].replace(" ", "+") + "+%0A" +
                                        "pincode+ " + pincode[i] + "%0A" + availableCapacity[i] + "+slots+are+available+on+" + date + "%0A" + "%0A";
                            }
                            System.out.println(result);
                            String apiURL = "https://api.telegram.org/bot1604017651:AAEWlp-OllFlAF4clHk4WPnUBVmtf0SR1b8/sendMessage?chat_id=" + link + "&text=";
                            apiURL += result;
                            System.out.println(apiURL);
                            URL uRL2 = new URL(apiURL);
                            HttpURLConnection httpURLConnection2 = (HttpURLConnection) uRL2.openConnection();
                            httpURLConnection2.setRequestMethod("GET");
                            int n2 = httpURLConnection2.getResponseCode();
                        }
                        bufferedReader.close();
                    }
                } catch (Exception exception) {
                    System.out.println(exception);
                }

            }
            Thread.sleep(1200);
            return "Web";
        }
    }

    @RequestMapping(value = "/getLink")
    public String getLink(@RequestParam("pincode") String pincode, Model attribute) {
        if (pinCodeAndLinkService.findLink(pincode).equals("Wait for a time , link not generated yet")) {
            attribute.addAttribute("show", "Wait for a time , link not generated yet");
            attribute.addAttribute("link", "/");
            pinCodeAndLinkService.save(pincode);
        } else {
            String res = pinCodeAndLinkService.findLink(pincode).replace("@", "https://t.me/");
            attribute.addAttribute("show", res);
            attribute.addAttribute("link", res);
        }
        System.out.println(pincode);
        return "Web";
    }
}