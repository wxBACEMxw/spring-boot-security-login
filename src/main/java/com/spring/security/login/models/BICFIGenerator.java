package com.spring.security.login.models;
import java.util.Random;

public class BICFIGenerator {

    public static String generateBICFI(String countryCode, String bankCode, String branchCode){
        Random random = new Random();
        StringBuilder bicfiBuilder = new StringBuilder();
        bicfiBuilder.append(countryCode);
        bicfiBuilder.append(bankCode);
        bicfiBuilder.append(branchCode);
        for(int i = 0; i<3; i++){
            bicfiBuilder.append(random.nextInt(10));
        }
        bicfiBuilder.append("XXX");
        return bicfiBuilder.toString();
    }
}
