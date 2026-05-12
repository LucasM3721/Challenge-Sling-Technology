package com.sling.technology.infrastructure.adapter.out.messaging;

import com.sling.technology.domain.model.HotelSearch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KafkaSearchProducerTest {

    @Mock
    private KafkaTemplate<String, HotelSearch> kafkaTemplate;

    private static final String TEST_TOPIC = "test-topic";

    private KafkaSearchProducer kafkaSearchProducer;

    @Captor
    private ArgumentCaptor<String> topicCaptor;

    @Captor
    private ArgumentCaptor<String> keyCaptor;

    @Captor
    private ArgumentCaptor<HotelSearch> valueCaptor;

    @BeforeEach
    void setUp() {
        kafkaSearchProducer = new KafkaSearchProducer(kafkaTemplate, TEST_TOPIC);
    }

    @Test
    void shouldPublishMessageToCorrectTopic() {
        when(kafkaTemplate.send(anyString(), anyString(), any(HotelSearch.class)))
                .thenReturn(CompletableFuture.completedFuture(null));

        HotelSearch search = createValidHotelSearch("search-123");

        kafkaSearchProducer.publish(search);

        verify(kafkaTemplate).send(topicCaptor.capture(), keyCaptor.capture(), valueCaptor.capture());
        assertEquals(TEST_TOPIC, topicCaptor.getValue());
    }

    @Test
    void shouldUseSearchIdAsMessageKey() {
        when(kafkaTemplate.send(anyString(), anyString(), any(HotelSearch.class)))
                .thenReturn(CompletableFuture.completedFuture(null));

        String expectedSearchId = "unique-search-id-456";
        HotelSearch search = createValidHotelSearch(expectedSearchId);

        kafkaSearchProducer.publish(search);

        verify(kafkaTemplate).send(topicCaptor.capture(), keyCaptor.capture(), valueCaptor.capture());
        assertEquals(expectedSearchId, keyCaptor.getValue());
    }

    @Test
    void shouldSendHotelSearchAsMessageValue() {
        when(kafkaTemplate.send(anyString(), anyString(), any(HotelSearch.class)))
                .thenReturn(CompletableFuture.completedFuture(null));

        HotelSearch search = createValidHotelSearch("search-789");

        kafkaSearchProducer.publish(search);

        verify(kafkaTemplate).send(topicCaptor.capture(), keyCaptor.capture(), valueCaptor.capture());
        assertSame(search, valueCaptor.getValue());
    }

    @Test
    void shouldLogErrorWhenKafkaSendFails() {
        CompletableFuture<Object> failedFuture = new CompletableFuture<>();
        failedFuture.completeExceptionally(new RuntimeException("Kafka connection failed"));

        when(kafkaTemplate.send(anyString(), anyString(), any(HotelSearch.class)))
                .thenReturn((CompletableFuture) failedFuture);

        HotelSearch search = createValidHotelSearch("error-search-id");

        // Should not throw - error is handled in whenComplete callback
        assertDoesNotThrow(() -> kafkaSearchProducer.publish(search));

        verify(kafkaTemplate).send(TEST_TOPIC, "error-search-id", search);
    }

    private HotelSearch createValidHotelSearch(String searchId) {
        return new HotelSearch(
                "hotel123",
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(2),
                List.of(24, 30, 7),
                searchId
        );
    }
}
