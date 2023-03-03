package com.spring.security.login.models;
import org.apache.commons.validator.routines.checkdigit.CheckDigitException;
import org.apache.commons.validator.routines.checkdigit.IBANCheckDigit;
import java.util.regex.*;


class IBANFormat {
    public static String getFormatForCountry(String countryCode) {
        switch (countryCode) {
            case "AD":
                return "^AD[0-9]{2}[0-9]{4}[0-9]{4}[A-Z0-9]{12}$";
            case "AE":
                return "^AE[0-9]{2}[0-9]{3}[0-9]{16}$";
            case "AL":
                return "^AL[0-9]{2}[0-9]{8}[A-Z0-9]{16}$";

            default:
                return null;
        }
    }
}
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


    public boolean isValidIBAN(String iban) {

        iban = iban.replaceAll("\\s", "");


        Pattern countryCodePattern = Pattern.compile("^[A-Z]{2}");
        Matcher countryCodeMatcher = countryCodePattern.matcher(iban);
        if (!countryCodeMatcher.find()) {
            return false;
        }


        String countryCode = countryCodeMatcher.group();
        String format = IBANFormat.getFormatForCountry(countryCode);
        if (format == null) {

            return false;
        }


        String ibanWithoutCountryCode = iban.substring(2);
        Pattern ibanPattern = Pattern.compile(format);
        Matcher ibanMatcher = ibanPattern.matcher(ibanWithoutCountryCode);
        return ibanMatcher.matches();
    }


}
