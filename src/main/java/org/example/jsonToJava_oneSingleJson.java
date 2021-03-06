package org.example;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.text.StringEscapeUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class jsonToJava_oneSingleJson {
    public static void main(String[] args) throws SQLException, IOException, ParseException {
        Connection con = null;
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Business","root","Whisbi2022");
        Statement st = con.createStatement();
        ResultSet result = st.executeQuery("select * from CustomerInfo where Location ='Asia';");

        ArrayList<customerDetails> customerDetailsArray = new ArrayList<customerDetails>();
        ObjectMapper om = new ObjectMapper();

        while(result.next()) {

            customerDetails cd = new customerDetails();
            cd.setCourseName(result.getString(1));
            cd.setPurchasedDate(result.getString(2));
            cd.setAmount(result.getInt(3));
            cd.setLocation(result.getString(4));

            customerDetailsArray.add(cd);
        }

        JSONArray jarray=new JSONArray();
//        jarray.add(jsonString);

        for (int i=0; i<customerDetailsArray.size(); i++) {
            //Se crea un JSON FILE (un json file por cada registro en la db)
            om.writeValue(new File("/Users/jgimenez/Josu/SDET project/src/main/java/org/example/customerInfo" + i + ".json"), customerDetailsArray.get(i));
            //create json string from java object
            Gson gson = new Gson();
            String jsonString = gson.toJson(customerDetailsArray.get(i));
            jarray.add(jsonString);
        }

        JSONObject jo= new JSONObject();
        jo.put("data",jarray); //We add first key to the JSON to be "data"

        String unescapeString = StringEscapeUtils.unescapeJava(jo.toJSONString());

        System.out.println(jo.toJSONString());
        System.out.println(unescapeString);
        String unescapeString2 = unescapeString.replace("\"{","{").replace("}\"","}");
        System.out.println(unescapeString2);
        JSONParser parser = new JSONParser();
        JSONObject unescapeString2json = (JSONObject) parser.parse(unescapeString2);

        om.writeValue(new File("/Users/jgimenez/Josu/SDET project/src/main/java/org/example/1JSON.json"), unescapeString2json);


        con.close();

    }
}
