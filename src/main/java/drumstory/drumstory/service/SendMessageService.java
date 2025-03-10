package drumstory.drumstory.service;


import drumstory.drumstory.domain.Member;
import drumstory.drumstory.domain.TimeTable;
import drumstory.drumstory.repository.MemberInterface;
import drumstory.drumstory.repository.ReservationInterface;
import drumstory.drumstory.repository.TimeTableInterface;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Balance;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import net.nurigo.sdk.message.service.DefaultMessageService;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class SendMessageService {
    private final MemberInterface memberInterface;
    private final DefaultMessageService messageService;
    private final TimeTableInterface timeTableInterface;
    private final ReservationInterface reservationInterface;

    @Autowired
    public SendMessageService(MemberInterface memberInterface, TimeTableInterface timeTableInterface, ReservationInterface reservationInterface) {
        this.memberInterface = memberInterface;
        this.timeTableInterface = timeTableInterface;
        this.reservationInterface = reservationInterface;
        this.messageService = NurigoApp.INSTANCE.initialize("NCSUXF2U6OZRK7KG",
                "NAT8BU5MW5HZWEFTBNDG0HUTQQBZZQOK", "https://api.coolsms.co.kr");
    }

    @Scheduled(cron = "0 25,55 * * * *")
    public void checkAndSendMessages(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime targetTime = now.minusMinutes(25);
        LocalDateTime nextTime = now.plusMinutes(5);

        String targetHourMinute = targetTime.format(DateTimeFormatter.ofPattern("HH:mm"));  //-25분 시간
        String nextHourMinute = nextTime.format(DateTimeFormatter.ofPattern("HH:mm"));    //+5분 시간

        TimeTable targetTimeTable = timeTableInterface.findByTime(targetHourMinute);
        TimeTable nextTimeTable = timeTableInterface.findByTime(nextHourMinute);

        //-25분 된 시간에 예약된 사람 찾기
        List<Member> memberList= reservationInterface.findMembersByTime(targetTimeTable);
        for (Member member: memberList){
            //이 멤버가 다음 시간 예약도 되어있는지 확인
            if (!reservationInterface.checkNextReservation(nextTimeTable, member)){
                sendOne(member.getPhoneNumber(),member.getName());
                System.out.println("문자 전송 완료");
            }
        }

    }


    public void sendOne(String toPhoneNum, String name) {
        Message message = new Message();
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
        String adminPhoneNum = memberInterface.findAdmin().getPhoneNumber();
        message.setFrom(adminPhoneNum);
        message.setTo(toPhoneNum);
        message.setText("[드럼스토리]"+ name+"님 이용 종료 5분 전입니다.");

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        System.out.println(response);

    }

    public Balance getBalance(){
        Balance balance = this.messageService.getBalance();
        System.out.println(balance);
        return balance;
    }




}
