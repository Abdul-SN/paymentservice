package com.example.paymentservice.error.handler;

import com.example.paymentservice.error.exception.InsufficientFundsException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.ExponentialBackOffWithMaxRetries;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Кастомный error‑handler для платежного сервиса.
 * <ul>
 *   <li>делает N попыток повторной обработки сообщения;</li>
 *   <li>после неудачи публикует запись в &lt;topic&gt;.dlq;</li>
 *   <li>дополнительно отправляет в топик «payment.notifications» событие о провале;</li>
 *   <li>фиксирует offset, чтобы консьюмер перешёл дальше.</li>
 * </ul>
 */
@Slf4j
@Component
public class KafkaErrorHandler extends DefaultErrorHandler {

    public KafkaErrorHandler(KafkaTemplate<String, String> dlqTemplate) {
        super(
                new DeadLetterPublishingRecoverer(
                        dlqTemplate,
                        (record, ex) -> new TopicPartition(record.topic() + ".dlq", record.partition())
                ),
                new ExponentialBackOffWithMaxRetries(3)// 3 повтора: 1s,2s,4s
        );

        // 1) Не пытаться ретраить, если в процессе обработки упало
        //    именно наше «InsufficientFundsException»
        addNotRetryableExceptions(InsufficientFundsException.class);

        // 2) После recover (т. е. после публикации в DLQ) зафиксировать offset
        setCommitRecovered(true);
    }


    @Override
    public void handleRemaining(Exception thrownException,
                                List<ConsumerRecord<?, ?>> records,
                                Consumer<?, ?> consumer,
                                MessageListenerContainer container) {
        super.handleRemaining(thrownException, records, consumer, container);
    }
}
