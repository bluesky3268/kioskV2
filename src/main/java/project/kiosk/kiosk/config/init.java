package project.kiosk.kiosk.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import project.kiosk.kiosk.dto.ItemAddDTO;
import project.kiosk.kiosk.dto.MemberJoinDTO;
import project.kiosk.kiosk.entity.Member;
import project.kiosk.kiosk.entity.Role;
import project.kiosk.kiosk.service.ItemService;
import project.kiosk.kiosk.service.MemberService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class init {

    private final MemberService memberService;
    private final ItemService itemService;

    // 서버 실행 시 supervisor가 하나도 없으면 root 생성
    @PostConstruct
    public void init(){
        log.info("PostConstructor init() ");
        List<Member> supervisorList = memberService.findMemberByRole(Role.SUPERVISOR);

        if (supervisorList.isEmpty()) {
            MemberJoinDTO member = new MemberJoinDTO("ROOT", "1234", "1234", "supervisor");
            MemberJoinDTO member2 = new MemberJoinDTO("SAMPLE", "1234", "1234", "manager");
            log.info("joinInit()호출");
            Long memberNo = memberService.joinInit(member);
            Long member2No = memberService.joinInit(member2);


            Member sample = memberService.findMemberById("SAMPLE");

            ItemAddDTO itemAddDTO = new ItemAddDTO("item", 10000, "false", sample.getNo());
            itemService.addItemInit(itemAddDTO, member2No);

        }else{
            return;
        }
    }
}
