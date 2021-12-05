package com.example.eg09batch.common.validation;

import org.apache.commons.lang3.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.function.Function;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = Parseable.ParseableValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface Parseable {

    ParseableType value();

    String message() default "{string.parseable}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class ParseableValidator implements ConstraintValidator<Parseable, String> {

        private Parseable annotation;

        @Override
        public void initialize(Parseable constraintAnnotation) {
            this.annotation = constraintAnnotation;
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {

            if (StringUtils.isBlank(value)) {
                return true;
            }

            ParseableType type = annotation.value();

            switch (type) {
                case TO_INT:
                    return catcher(value, Integer::parseInt);
                case TO_DOUBLE:
                    return catcher(value, Double::parseDouble);
                case TO_LONG:
                    return catcher(value, Long::parseLong);
                case TO_SHORT:
                    return catcher(value, Short::parseShort);
                case TO_FLOAT:
                    return catcher(value, Float::parseFloat);
            }

            return false;
        }

        private <T> boolean catcher(String value, Function<String, T> function) {
            try {
                function.apply(value);
                return true;
            } catch (Exception e) {
                return false;
            }
        }

    }


}