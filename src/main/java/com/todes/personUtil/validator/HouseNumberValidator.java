package com.todes.personUtil.validator;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@FacesValidator("com.todes.personUtil.validator.HouseNumberValidator")
public class HouseNumberValidator implements Validator {


    private static final String NAME_PATTERN = "^[0-9]*$";


    private Pattern pattern;
    private Matcher matcher;

    public HouseNumberValidator() {
        pattern = Pattern.compile(NAME_PATTERN);
    }


    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        if (value == null || value.toString().length() == 0) {
            generateValidatorException("Please, enter HouseNumber");
        }
        matcher = pattern.matcher(value.toString());
        if (!matcher.matches()) {
            generateValidatorException("Invalid house number. input positive integer value");
        }

        int inputValue = 0;
        try {
            inputValue = (Integer) value;
        } catch (Exception e) {
            generateValidatorException("Invalid HouseNumber. input positive integer value");
        }

        if (inputValue <= 0) {
            generateValidatorException("Please, enter valid HouseNumber. input positive integer value");
        }

        if (inputValue > 10000) {
            generateValidatorException("maximum number of houses = 10000");
        }
    }

    private void generateValidatorException(String summary) {
        FacesMessage msg = new FacesMessage(summary);
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ValidatorException(msg);
    }
}
