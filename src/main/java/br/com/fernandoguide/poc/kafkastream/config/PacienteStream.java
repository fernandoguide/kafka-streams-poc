package br.com.fernandoguide.poc.kafkastream.config;

import br.com.fernandoguide.poc.kafkastream.event.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private PacienteRepository repository;


    @Bean
    KStream<String, Paciente> pacienteKStream(StreamsBuilder streamsBuilder) {

        return streamsBuilder.stream(
                topic, Consumed.with(Serdes.String(), new PacienteDTOSerde()))
                .map(this::checkId)
                .map(this::convertDtoToPaciente)
//                .filter(this::filterIdNotNull)
                .peek((k,v) -> log.info("Consumindo tópico: {}, {}", k, v))
                .peek(this::salvarNoBanco)
                .map(this::convertPacienteToDTO)
                .map(this::convertDtoToPaciente);

    }

    private void salvarNoBanco(String s , Paciente p) {
        log.info("Salvando no banco de dados o  obj {}", p);
        repository.save(p);

    }
    private KeyValue<String, Paciente> convertDtoToPaciente(String chave, PacienteDTO pacienteDTO) {
        Paciente paciente= Paciente.convert(pacienteDTO);
        log.info("convertDtoToPaciente   {}", paciente);
        return new KeyValue<>(chave, paciente);
    }
    private KeyValue<String, PacienteDTO> convertPacienteToDTO(String chave, Paciente paciente) {
        PacienteDTO pacienteDTO = PacienteDTO.convert(paciente);
        log.info("convertPacienteToDTO   {}", pacienteDTO);
        return new KeyValue<>(chave, pacienteDTO);
    }

    private Boolean filterIdNotNull(String key, Paciente value) {
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
