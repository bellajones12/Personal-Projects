package org.example;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateDeserialiser extends JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String dateStr = p.getText();
        try {
            // Assuming the date format in the JSON is yyyy-MM-dd HH:mm:ss
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            Date date = formatter.parse(dateStr);

            // Convert it to the desired format (yy-MM-dd)
            SimpleDateFormat newFormatter = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDateStr = newFormatter.format(date);

            // Parse the new formatted date string into a Date object
            return newFormatter.parse(formattedDateStr);
        } catch (Exception e) {
            throw new IOException("Unable to parse date", e);
        }
    }
}
