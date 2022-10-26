package br.com.fernandoguide.poc.kafkastream.event;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Builder
@Jacksonized
@Getter
@ToString
public class PacienteDTO {

    private Long id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;

    public  static PacienteDTO convert(Paciente p) {
        return PacienteDTO.builder()
                .id(p.getId())
                .nome(p.getNome())
                .cpf(p.getDocumento())
                .dataNascimento(p.getDataNascimento())
                .build();
    }

}
