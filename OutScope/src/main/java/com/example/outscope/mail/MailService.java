package com.example.outscope.mail;

import com.example.outscope.entity.GoOut;
import com.example.outscope.entity.User;
import com.example.outscope.entity.dto.StateDto;
import com.example.outscope.repository.GoOutRepository;
import com.example.outscope.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    private final UserRepository userRepository;

    private final GoOutRepository goOutRepository;

    public void sendApplication(String account, String state) {

        String title = "";
        String content = "";

        ArrayList<String> toUserList = new ArrayList<>();

        Optional<User> user = userRepository.findByAccount(account);
        String teacherNum = user.get().getClassNumber().substring(0, 2) + "00";
        Optional<User> teacher = userRepository.findByClassNumber(teacherNum);

        if(state.equals("대기")) {
            title = user.get().getUsername()+" 학생이 외출증을 신청했습니다.";
            content = "이름 : " + user.get().getUsername()
                    + "\n학번 : " + user.get().getClassNumber()
                    +"\n자세한 정보는 아래의 주소를 참고해 주세요. \n https://www.youtube.com";
        } else {
            title = user.get().getUsername()+" 학생이 복귀했습니다.";
            content = user.get().getUsername()+" 학생이 복귀했습니다.";
        }

        toUserList.add(teacher.get().getEmail());

        int toUserSize = toUserList.size();

        SimpleMailMessage simpleMessage = new SimpleMailMessage();

        simpleMessage.setTo((String[]) toUserList.toArray(new String[toUserSize]));

        simpleMessage.setSubject(title);

        simpleMessage.setText(content);

        javaMailSender.send(simpleMessage);
    }

    public void sendState(StateDto stateDto) {

        ArrayList<String> toUserList = new ArrayList<>();

        Optional<GoOut> go = goOutRepository.findById(stateDto.getId());
        Optional<User> user = userRepository.findByClassNumber(go.get().getClassNumber());

        toUserList.add(user.get().getEmail());

        int toUserSize = toUserList.size();

        SimpleMailMessage simpleMessage = new SimpleMailMessage();

        simpleMessage.setTo((String[]) toUserList.toArray(new String[toUserSize]));

        simpleMessage.setSubject(user.get().getUsername() +"님, 외출증이 " + stateDto.getState() + "되었습니다.");

        simpleMessage.setText(user.get().getUsername() +"님, 외출증이 " + stateDto.getState() + "되었습니다.");

        javaMailSender.send(simpleMessage);
    }

}
