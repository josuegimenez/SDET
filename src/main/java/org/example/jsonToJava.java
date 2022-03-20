package org.example;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class jsonToJava {
    public static void main(String[] args) throws SQLException, IOException {
        Connection con = null;
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Business","root","Whisbi2022");
        Statement st = con.createStatement();
        ResultSet result = st.executeQuery("select * from CustomerInfo where purchasedDate=CURDATE() and Location ='Asia';");

        customerDetails cd = new customerDetails();
        ArrayList<customerDetails> customerDetailsArray = new ArrayList<customerDetails>();
        ObjectMapper om = new ObjectMapper();

        while(result.next()) {

            cd.setCourseName(result.getString(1));
            cd.setPurchasedDate(result.getString(2));
            cd.setAmount(result.getInt(3));
            cd.setLocation(result.getString(4));

            customerDetailsArray.add(cd);
        }

        for (int i=0; i<customerDetailsArray.size(); i++)
        om.writeValue(new File("/Users/jgimenez/Josu/SDET project/src/main/java/org/example/customerInfo"+i+".json"), customerDetailsArray.get(i));
        om.writeValue(new File("/Users/jgimenez/Josu/SDET project/src/main/java/org/example/customerInfoCompletJSON.json"), customerDetailsArray);

        con.close();

    }
}
