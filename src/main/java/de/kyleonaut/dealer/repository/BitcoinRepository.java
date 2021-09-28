package de.kyleonaut.dealer.repository;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author kyleonaut
 * @version 1.0.0
 * created at 26.09.2021
 */
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class BitcoinRepository {

    public Double getPrice() throws IOException, ParseException {
        final URL url = new URL("https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=USD");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        final int status = connection.getResponseCode();
        if (status == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String res;
            StringBuilder builder = new StringBuilder();
            while ((res = reader.readLine()) != null) {
                builder.append(res);
            }
            reader.close();
            connection.disconnect();
            JSONParser jsonParser = new JSONParser();
            JSONObject object = (JSONObject) jsonParser.parse(builder.toString());
            return (Double) object.get("USD");
        }
        return 0d;
    }
}
