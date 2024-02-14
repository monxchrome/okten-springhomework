package owu.springhomework.cron;

import com.google.common.collect.Iterators;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import owu.springhomework.repository.WebSocketSessionRepository;

import java.util.Iterator;

@Component
@RequiredArgsConstructor
@Slf4j
public class SendToActiveUsers {

    private final WebSocketSessionRepository webSocketSessionRepository;
    private final Iterator<String> promos = Iterators.cycle(
            "Buy premium for 50% discount",
            "Buy verification button",
            "-20% if you pay from Monobank card"
    );

    @Scheduled(cron = "0 0 * * * *")
    public void sendPromos() {
        log.info("Sending...");
        webSocketSessionRepository.sendToAll(promos.next());
    }

    // delay = 1 hour
    // 1 job - 13:00 - 13:05
    // 2 job - 14:05 - 14:10
    // 3 job - 15:10 - 15:15

    // rate = 1 hour
    // 1 job - 13:00 - 13:05
    // 2 job - 14:00 - 14:05
    // 3 job - 15:00 - 15:05
}
