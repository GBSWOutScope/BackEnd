package com.example.outscope.controller;


import com.example.outscope.entity.GoOut;
import com.example.outscope.entity.dto.GoOutRequestDto;
import com.example.outscope.entity.dto.GoOutResponseDto;
import com.example.outscope.entity.dto.SignResponse;
import com.example.outscope.entity.dto.StateDto;
import com.example.outscope.mail.MailService;
import com.example.outscope.service.GoOutService;
import com.example.outscope.service.SignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@EnableScheduling
@RestController
@RequiredArgsConstructor
@Slf4j
public class GoOutController {

    private final SignService signService;

    private final GoOutService goOutService;

    private final MailService mailService;

    @PostMapping("/api/user/write")
    public ResponseEntity<GoOut> writeGoOut(
            @RequestBody GoOutRequestDto goOutRequestDto) throws Exception {
        SignResponse user = signService.getUser(goOutRequestDto.getAccount());
        mailService.sendApplication(goOutRequestDto.getAccount(), goOutRequestDto.getState());
        return new ResponseEntity<>(goOutService.SaveGoOut(goOutRequestDto, user), HttpStatus.OK);
    }

    @GetMapping("/api/admin/list")
    public ResponseEntity<List<GoOutResponseDto>> listAccess() throws Exception {
        return goOutService.ListCheckGoOut();
    }

    @GetMapping("/api/all/listLunch")
    public ResponseEntity<List<GoOutResponseDto>> listLunch() throws Exception {
        return goOutService.ListGoOut("점심", "오전");
    }

    @GetMapping("/api/all/listDinner")
    public ResponseEntity<List<GoOutResponseDto>> listDinner() throws Exception {
        return goOutService.ListGoOut("저녁", "오후");
    }

    @GetMapping("/api/admin/info")
    public ResponseEntity<GoOutResponseDto> GoOutInfo(@RequestParam Long id) throws Exception {
        return goOutService.goOutInfo(id);
    }

    @Scheduled(cron = "0 0 14 * * ?")
    @DeleteMapping("/api/del/lunch")
    public ResponseEntity<HttpStatus> delLunch() throws Exception {
        log.info("### 데이터가 삭제 되었습니다.");
        goOutService.delGoOut("점심", "오전");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Scheduled(cron = "0 30 19 * * ?")
    @DeleteMapping("/api/del/dinner")
    public ResponseEntity<HttpStatus> delDinner() throws Exception {
        log.info("### 데이터가 삭제 되었습니다.");
        goOutService.delGoOut("저녁", "오후");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/api/all/update")
    public ResponseEntity<GoOut> updateState(@RequestBody StateDto stateDto) throws Exception {
        if(stateDto.getState().equals("복귀")) {
            mailService.sendApplication(stateDto.getAccount(), stateDto.getState());
        } else {
            mailService.sendState(stateDto);
        }
        return goOutService.UpdateBook(stateDto);
    }

}
