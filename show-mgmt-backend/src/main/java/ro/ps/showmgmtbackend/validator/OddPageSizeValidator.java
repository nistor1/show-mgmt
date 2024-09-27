package ro.ps.showmgmtbackend.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OddPageSizeValidator implements ConstraintValidator<OddPageSize, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value % 2 == 1;
    }
}
