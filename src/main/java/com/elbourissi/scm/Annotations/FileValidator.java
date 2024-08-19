package com.elbourissi.scm.Annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {

    private static final long MAX_FILE_SIZE = 1024 * 1024 * 2; // 2MB

    /**
     * @param file
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
        if (file == null || file.isEmpty()) {
            //constraintValidatorContext.disableDefaultConstraintViolation();
            //constraintValidatorContext.buildConstraintViolationWithTemplate("File cannot be empty")
              //      .addConstraintViolation();
            return false;

        }
        System.out.println("file size: " + file.getSize());

        if (file.getSize() > MAX_FILE_SIZE) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("File size should be less than 2MB")
                    .addConstraintViolation();
            return false;
        }
        // resolution

        // try {
        // BufferedImage bufferedImage = ImageIO.read(file.getInputStream());

        // if(bufferedImage.getHe)

        // } catch (IOException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        return true;
    }
}
