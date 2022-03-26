package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class extractJson {
    public static void main(String[] args) throws IOException {

        ObjectMapper om = new ObjectMapper();
        customerDetailsAppium cdA=om.readValue(new FileReader("/Users/jgimenez/Josu/SDET project/src/main/java/org/example/customerInfo0.json"),customerDetailsAppium.class);
        System.out.println(cdA.getCourseName());

    }
}
