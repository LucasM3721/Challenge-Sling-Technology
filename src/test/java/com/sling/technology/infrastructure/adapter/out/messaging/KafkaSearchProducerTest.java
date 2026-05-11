package com.sling.technology.infrastructure.adapter.out.messaging;

import com.sling.technology.domain.model.HotelSearch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class KafkaSearchProducerTest {

    @Mock
    private KafkaTemplate<String, HotelSearch> kafkaTemplate;

    private static final String TEST_TOPIC = "test-topic";

    @InjectMocks
    private KafkaSearchProducer kafkaSearchProducer;

    @Captor
    private ArgumentCaptor<String> topicCaptor;

    @Captor
    private ArgumentCaptor<String> keyCaptor;

    @Captor
    private ArgumentCaptor<Object> valueCaptor;

    @BeforeEach
    void setUp() {
        kafkaSearchProducer = new KafkaSearchProducer(kafkaTemplate, TEST_TOPIC);
    }

    @Test
    void shouldPublishMessageToCorrectTopic() {
        HotelSearch search = createValidHotelSearch("search-123");

        kafkaSearchProducer.publish(search);

        verify(kafkaTemplate).send(topicCaptor.capture(), keyCaptor.capture(), (HotelSearch) valueCaptor.capture());
        assertEquals(TEST_TOPIC, topicCaptor.getValue());
    }

    @Test
    void shouldUseSearchIdAsMessageKey() {
        String expectedSearchId = "unique-search-id-456";
        HotelSearch search = createValidHotelSearch(expectedSearchId);

        kafkaSearchProducer.publish(search);

        verify(kafkaTemplate).send(topicCaptor.capture(), keyCaptor.capture(), (HotelSearch) valueCaptor.capture());
        assertEquals(expectedSearchId, keyCaptor.getValue());
    }

    @Test
    void shouldSendHotelSearchAsMessageValue() {
        HotelSearch search = createValidHotelSearch("search-789");

        kafkaSearchProducer.publish(search);

        verify(kafkaTemplate).send(topicCaptor.capture(), keyCaptor.capture(), (HotelSearch) valueCaptor.capture());
        assertSame(search, valueCaptor.getValue());
    }

    private HotelSearch createValidHotelSearch(String searchId) {
        return new HotelSearch(
                "hotel123",
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                List.of(24, 30, 7),
                searchId
        );
    }
}
