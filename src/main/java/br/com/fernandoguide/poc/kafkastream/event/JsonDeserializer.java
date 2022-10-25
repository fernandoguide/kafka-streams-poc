package br.com.fernandoguide.poc.kafkastream.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

public class JsonDeserializer<T> implements Deserializer<T> {

    private static final Charset CHARSET = StandardCharsets.UTF_8;
    private final ObjectMapper objectMapper;
    private final Class<T> mainObject;

    public JsonDeserializer(Class<T> mainClass) {
        this.objectMapper = new ObjectMapper().configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.objectMapper.findAndRegisterModules();
        this.mainObject = mainClass;
    }

    @Override
    public T deserialize(String s, byte[] bytes) {
        try {
            return objectMapper.readValue(bytes, mainObject);
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao deserializar json", e);
        }
    }

}

