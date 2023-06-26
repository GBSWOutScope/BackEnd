package com.example.outscope.service.impl;

import com.example.outscope.entity.GoOut;
import com.example.outscope.entity.User;
import com.example.outscope.entity.dto.GoOutRequestDto;
import com.example.outscope.entity.dto.GoOutResponseDto;
import com.example.outscope.entity.dto.SignResponse;
import com.example.outscope.entity.dto.StateDto;
import com.example.outscope.repository.GoOutRepository;
import com.example.outscope.repository.UserRepository;
import com.example.outscope.service.GoOutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoOutServiceImpl implements GoOutService {

    private final UserRepository userRepository;

    private final GoOutRepository goOutRepository;

    @Override
    public GoOut SaveGoOut(GoOutRequestDto goOutRequestDto, SignResponse account) {
        GoOut go = GoOut.builder()
                .name(account.getUsername())
                .classNumber(account.getClassNumber())
                .departure(goOutRequestDto.getDeparture())
                .arrival(goOutRequestDto.getArrival())
                .time(goOutRequestDto.getTime())
                .state(goOutRequestDto.getState())
                .reason(goOutRequestDto.getReason())
                .build();

        List<User> users = new ArrayList<>();

        for (Long memberId : goOutRequestDto.getMember()) {
            Optional<User> userOpt = userRepository.findById(memberId);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                log.info("### 동행자 {}를 찾았습니다.", user.getUsername());
                users.add(user);
            } else {
                log.info("### User not found in the database");
            }
        }
        go.setMember(users);
        goOutRepository.save(go);

        return go;
    }

    @Override
    public ResponseEntity<List<GoOutResponseDto>> ListCheckGoOut() {
        List<GoOut> go = goOutRepository.findAll();
        List<GoOutResponseDto> goOutResponseDtoList = new ArrayList<>();

        for (GoOut write : go) {
            if(!GoOutResponseDto.entityToDto(write).getState().equals("승인")) {
                goOutResponseDtoList.add(GoOutResponseDto.entityToDto(write));
            }
        }

        if (!go.isEmpty()) {
            return new ResponseEntity<>(goOutResponseDtoList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public ResponseEntity<List<GoOutResponseDto>> ListGoOut(String time, String period) {
        List<GoOut> go = goOutRepository.findAllByTime(time, period);
        List<GoOutResponseDto> goOutResponseDtoList = new ArrayList<>();

        for (GoOut write : go) {
            if(GoOutResponseDto.entityToDto(write).getState().equals("승인")) {
                goOutResponseDtoList.add(GoOutResponseDto.entityToDto(write));
            }
        }

        if (!go.isEmpty()) {
            return new ResponseEntity<>(goOutResponseDtoList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public ResponseEntity<HttpStatus> delGoOut(String time, String period) {
        goOutRepository.deleteByTime(time);
        goOutRepository.deleteByTime(period);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GoOut> UpdateBook(StateDto stateDto) {
        Optional<GoOut> optGo= goOutRepository.findById(stateDto.getId());
        if (optGo.isPresent()) {

            GoOut goOut = optGo.get();
            goOut.setState(stateDto.getState());


            goOutRepository.save(goOut);

            return new ResponseEntity<>(goOut, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<GoOutResponseDto> goOutInfo(Long id) {
        Optional<GoOut> go = goOutRepository.findById(id);
        if(go.isPresent()) {
            GoOutResponseDto goOutResponseDtoList = GoOutResponseDto.entityToDto(go.get());
            return new ResponseEntity<>(goOutResponseDtoList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}
