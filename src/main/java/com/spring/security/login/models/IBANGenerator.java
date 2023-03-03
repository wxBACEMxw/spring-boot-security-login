package com.spring.security.login.models;
import org.apache.commons.validator.routines.IBANValidator;
import org.apache.commons.validator.routines.checkdigit.CheckDigitException;
import org.apache.commons.validator.routines.checkdigit.IBANCheckDigit;

public class IBANGenerator {

   // public static void main(String[] args) throws CheckDigitException {
       // String countryCode = "NL"; e
       // String bankCode = "INGB";
       // String accountNumber = "123456789";

        //String iban = generateIban(countryCode, bankCode, accountNumber);
       // System.out.println("IBAN: " + iban);
    //}

    public  String generateIban(String countryCode, String bankCode, String accountNumber) throws CheckDigitException {
        String iban = countryCode + "00" + bankCode + accountNumber; // concatenate country code, "00", bank code, and account number
        IBANCheckDigit ibanCheckDigit = new IBANCheckDigit(); // create an instance of IBANCheckDigit
        int remainder = Integer.parseInt(ibanCheckDigit.calculate(iban)); // calculate the checksum
        iban = countryCode + String.format("%02d", 98 - remainder) + bankCode + accountNumber; // replace the "00" with the checksum

        return iban;
    }
}
