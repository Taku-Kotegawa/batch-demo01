package com.example.eg09batch.common.validation;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Documented
@Constraint(validatedBy = IsDate.IsDateValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface IsDate {
    String value();

    String message() default "{date.isdate}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public class IsDateValidator implements ConstraintValidator<IsDate, String> {

        private IsDate annotation;

        @Override
        public void initialize(IsDate constraintAnnotation) {
            this.annotation = constraintAnnotation;
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {

            if (value == null) {
                return true;
            }

            if (isEmpty(value)) {
                return false;
            }

            SimpleDateFormat sdf = new SimpleDateFormat(annotation.value());
            try {
                sdf.parse(value);
                return true;
            } catch (ParseException e) {
                return false;
            }
        }

    }
}

