package br.com.fernandoguide.poc.kafkastream.event;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Value
@Builder
@Jacksonized
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Paciente {

    String id;
    String nome;
    String documento;
    String dataNascimento;

    public  static Paciente convert(PacienteDTO p) {
        return Paciente.builder()
                .id(p.getId())
                .nome(p.getNome())
                .documento(p.getCpf())
                .dataNascimento(p.getDataNascimento().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .build();
    }

}
