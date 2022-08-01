package dev.yoon.gridgetest.domain.board.dto;

import dev.yoon.gridgetest.domain.board.domain.Board;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CreateBoardReq {

    @Size(min = 1, max = 1000, message = "10자 이상 500자 이하로 작성해주세요.")
    @NotBlank(message = "글 내용은 필수값 입니다.")
    private String content;

    @Size(max = 10, message = "최대 10개까지 사진을 추가하실 수 있습니다.")
    private List<MultipartFile> boardImages = new ArrayList<>();

    public Board toEntity() {

        return Board.builder()
                .content(content)
                .build();
    }

}
