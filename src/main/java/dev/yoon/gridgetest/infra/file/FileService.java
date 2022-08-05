package dev.yoon.gridgetest.infra.file;

import dev.yoon.gridgetest.domain.report.model.ServiceType;
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

    public Page<String> getLog(Pageable pageable, ServiceType serviceType) {

        File[] fileList = new File("./log/" + serviceType.getValue()).listFiles();
        List<String> contents = new ArrayList<>();
        int totalSize = 0;

        for (File file : fileList) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String temp;
                while ((temp = reader.readLine()) != null) {
                    totalSize++;
                    contents.add(temp.split(" - ")[1].trim());
                }
            } catch (IOException e) {
                throw new BusinessException(ErrorCode.FILE_IO);
            }
        }

        List<String> results = contents.stream()
                .skip(pageable.getPageNumber() * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());

        return new PageImpl<>(results, pageable, totalSize);
    }
}
