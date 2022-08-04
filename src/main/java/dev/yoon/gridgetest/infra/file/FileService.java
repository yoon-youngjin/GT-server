package dev.yoon.gridgetest.infra.file;

import dev.yoon.gridgetest.global.error.exception.BusinessException;
import dev.yoon.gridgetest.global.error.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FileService {

    public Page<String> getLog(Pageable pageable) {

        File[] fileList = new File("./log/user").listFiles();
        List<String> contents = new ArrayList<>();
        int totalSize = 0;

        for (File file : fileList) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String temp;
                while ((temp = reader.readLine()) != null) {
                    totalSize++;
                    contents.add(temp.split(" - ")[1].trim());
                }
            } catch (FileNotFoundException e) {
                throw new BusinessException(ErrorCode.AUTH_CODE_NOT_FOUND);

            } catch (IOException e) {
                throw new BusinessException(ErrorCode.AUTH_CODE_NOT_FOUND);
            }
        }


        List<String> results = contents.stream()
                .skip(pageable.getPageNumber() * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());

        return new PageImpl<>(results, pageable, totalSize);
    }
}
