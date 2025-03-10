package drumstory.drumstory.controller;

import drumstory.drumstory.service.SendMessageService;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.model.Balance;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AdminMessageController {
    private final SendMessageService sendMessageService;

//    @PostMapping("admin/sendMessage")
//    public SingleMessageSentResponse sendMessage() {
//        return sendMessageService.sendOne();
//    }

    @GetMapping("admin/get-balance")
    public Balance getBalance() {
        return sendMessageService.getBalance();

    }
}
