package br.com.fernandoguide.poc.kafkastream.config;

import br.com.fernandoguide.poc.kafkastream.event.Paciente;
import br.com.fernandoguide.poc.kafkastream.event.PacienteDTO;
import br.com.fernandoguide.poc.kafkastream.event.PacienteDTOSerde;
import br.com.fernandoguide.poc.kafkastream.event.PacienteSerde;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
@Slf4j
public class PacienteStream {

    @Value("${spring.kafka.topic}")
    private  String topic;
    @Value("${spring.kafka.othertopic}")
    private  String otherTopic;



    @Bean
    KStream<String, Paciente> flightKStream(StreamsBuilder streamsBuilder) {

        KStream<String, Paciente> outputStream = streamsBuilder.stream(
                topic,
                Consumed.with(Serdes.String(), new PacienteDTOSerde())
        ).map(
                this::checkId
        ).filter(
                this::filterIdNotNull
        ).peek(
                (k,v) -> log.info("Consumindo tópico: {}, {}", k, v)
        ).peek(
                this::salvarNoBanco
        ).map(
                this::converterPaciente
        ).peek(
                (k,v) -> log.info("publicando em outro tópico: {}, {}", k, v)
        );

        outputStream.to(otherTopic, Produced.with(Serdes.String(), new PacienteSerde()));

        return outputStream;

    }

    private void salvarNoBanco(String s , PacienteDTO p) {
        log.info("Salvando no banco de dados o  obj {}", p);

    }
    private KeyValue<String, Paciente> converterPaciente(String chave, PacienteDTO pacienteDTO) {
        Paciente paciente= Paciente.convert(pacienteDTO);
        log.info("converterPaciente   {}", paciente);
        return new KeyValue<>(chave, paciente);
    }

    private Boolean filterIdNotNull(String key, PacienteDTO value) {
        if (key == null) {
            log.info("id null: {}", value.getNome());
        }
        log.info("filterIdNotNull {}", value);
        return key != null;
    }

    private KeyValue<String, PacienteDTO> checkId(String key,
                                               PacienteDTO value) {
        return new KeyValue<>(
                Optional.ofNullable(value.getId())
                        .map(String::valueOf)
                        .orElse(key),
                value
        );
    }


}
