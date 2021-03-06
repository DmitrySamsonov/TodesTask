package com.todes.personUtil.validator;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@FacesValidator("com.todes.personUtil.validator.PatronymicValidator")
public class PatronymicValidator implements Validator {

    private static final String NAME_PATTERN = "^[A-Za-z]*$";

    private Pattern pattern;
    private Matcher matcher;

    public PatronymicValidator() {
        pattern = Pattern.compile(NAME_PATTERN);
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String inputText = value.toString();

        if (inputText.length() == 0) {
            generateValidatorException("Please, enter Patronymic.");
        }

        if (!Character.isUpperCase(inputText.charAt(0))) {
            generateValidatorException("Patronymic must be start with Upper case.");
        }

        if (inputText.length() > 15) {
            generateValidatorException("max Patronymic length 15 symbols.");
        }

        matcher = pattern.matcher(inputText);
        if (!matcher.matches()) {
            generateValidatorException("Patronymic must be consist of letters.");
        }
    }

    private void generateValidatorException(String summary) {
        FacesMessage msg = new FacesMessage(summary);
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ValidatorException(msg);
    }


}
