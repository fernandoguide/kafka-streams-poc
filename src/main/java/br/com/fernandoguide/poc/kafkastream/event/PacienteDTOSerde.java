package br.com.fernandoguide.poc.kafkastream.event;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class PacienteDTOSerde implements Serde<PacienteDTO> {

    private final JsonSerializer<PacienteDTO> serializer;
    private final JsonDeserializer<PacienteDTO> deserializer;

    public PacienteDTOSerde() {
        this.serializer = new JsonSerializer<>(PacienteDTO.class);
        this.deserializer = new JsonDeserializer<>(PacienteDTO.class);
    }

    @Override
    public Serializer<PacienteDTO> serializer() {
        return serializer;
    }

    @Override
    public Deserializer<PacienteDTO> deserializer() {
        return deserializer;
    }
}
