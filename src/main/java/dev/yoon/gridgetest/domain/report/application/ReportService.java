package dev.yoon.gridgetest.domain.report.application;

import dev.yoon.gridgetest.domain.answer.application.AnswerService;
import dev.yoon.gridgetest.domain.answer.domain.Answer;
import dev.yoon.gridgetest.domain.answer.dto.ReportAnswerReq;
import dev.yoon.gridgetest.domain.board.application.BoardService;
import dev.yoon.gridgetest.domain.board.domain.Board;
import dev.yoon.gridgetest.domain.board.dto.ReportBoardReq;
import dev.yoon.gridgetest.domain.report.entity.Report;
import dev.yoon.gridgetest.domain.report.exception.CantReportMySelfException;
import dev.yoon.gridgetest.domain.report.model.ServiceType;
import dev.yoon.gridgetest.domain.report.repository.ReportRepository;
import dev.yoon.gridgetest.domain.user.application.UserService;
import dev.yoon.gridgetest.domain.user.domain.User;
import dev.yoon.gridgetest.global.error.exception.EntityNotFoundException;
import dev.yoon.gridgetest.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

    private final UserService userService;
    private final BoardService boardService;
    private final AnswerService answerService;
    private final ReportRepository reportRepository;

    public Page<Report> getAllReports(Pageable pageable) {
        return reportRepository.findAll(pageable);
    }

    public void deleteReport(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.REPORT_NOT_FOUND));
        reportRepository.delete(report);

    }

    @Transactional
    public void reportBoard(ReportBoardReq request, String phone) {

        User user = userService.getUserByPhoneNumber(phone);
        Board board = boardService.getBoardById(request.getBoardId());

        if (user == board.getUser()) {
            throw new CantReportMySelfException(ErrorCode.CANT_REPORT_MYSELF);
        }


        Report report = Report.createReport(ServiceType.BOARD, user, board.getUser(), board.getId(), request.getReason());
        reportRepository.save(report);

        log.info("[피드 신고]/" + user.getNickname().getValue() + "/" + LocalDateTime.now());
    }

    @Transactional
    public void reportAnswer(ReportAnswerReq request, String phone) {

        User user = userService.getUserByPhoneNumber(phone);
        Answer answer = answerService.getAnswerById(request.getAnswerId());

        if (user == answer.getUser()) {
            throw new CantReportMySelfException(ErrorCode.CANT_REPORT_MYSELF);
        }

        Report report = Report.createReport(ServiceType.BOARD, user, answer.getUser(), answer.getId(), request.getReason());
        reportRepository.save(report);

        log.info("[댓글 신고]/" + user.getNickname().getValue() + "/" + LocalDateTime.now());
    }


}
