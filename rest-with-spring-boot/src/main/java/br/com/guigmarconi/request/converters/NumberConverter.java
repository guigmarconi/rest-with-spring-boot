package br.com.guigmarconi.request.converters;

import br.com.guigmarconi.exception.UnsupportedMathOperationException;
import org.springframework.stereotype.Component;

@Component
public class NumberConverter {

    public static Double convertToDouble(String strNumber) {

        if(strNumber == null || strNumber.isEmpty()) throw new UnsupportedMathOperationException("Please set a numeric value.");

        String number = strNumber.replace(",", ".");

        return Double.parseDouble(strNumber);
    }

    public static boolean isNumeric(String strNumber) {

        if(strNumber == null || strNumber.isEmpty()){
            return false;
        }

        String number = strNumber.replace(",", ".");

        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }
}
