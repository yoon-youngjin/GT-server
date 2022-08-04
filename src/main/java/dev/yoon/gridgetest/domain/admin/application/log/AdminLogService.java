package dev.yoon.gridgetest.domain.admin.application.log;

import dev.yoon.gridgetest.domain.admin.dto.log.GetUserLogRes;
import dev.yoon.gridgetest.infra.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminLogService {

    private final FileService fileService;

    public Page<GetUserLogRes> getUserLog(Pageable pageable) {

        Page<String> log = fileService.getLog(pageable);
        List<GetUserLogRes> collect = log.stream().map(GetUserLogRes::from).collect(Collectors.toList());

        return new PageImpl<>(collect, pageable, log.getTotalElements());

    }
}
