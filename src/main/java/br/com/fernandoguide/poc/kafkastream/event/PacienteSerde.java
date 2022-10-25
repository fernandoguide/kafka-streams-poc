package br.com.fernandoguide.poc.kafkastream.event;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class PacienteSerde implements Serde<Paciente> {

    private final JsonSerializer<Paciente> serializer;
    private final JsonDeserializer<Paciente> deserializer;

    public PacienteSerde() {
        this.serializer = new JsonSerializer<>(Paciente.class);
        this.deserializer = new JsonDeserializer<>(Paciente.class);
    }

    @Override
    public Serializer<Paciente> serializer() {
        return serializer;
    }

    @Override
    public Deserializer<Paciente> deserializer() {
        return deserializer;
    }
}
