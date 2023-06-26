package com.example.outscope.service;

import com.example.outscope.entity.GoOut;
import com.example.outscope.entity.dto.GoOutRequestDto;
import com.example.outscope.entity.dto.GoOutResponseDto;
import com.example.outscope.entity.dto.SignResponse;
import com.example.outscope.entity.dto.StateDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GoOutService {

    GoOut SaveGoOut(GoOutRequestDto goOutRequestDto, SignResponse account);

    public ResponseEntity<List<GoOutResponseDto>> ListCheckGoOut();

    ResponseEntity<List<GoOutResponseDto>> ListGoOut(String time, String period);

    ResponseEntity<HttpStatus> delGoOut(String time, String period);

    ResponseEntity<GoOut> UpdateBook(StateDto stateDto);

    ResponseEntity<GoOutResponseDto> goOutInfo(Long id);

}
