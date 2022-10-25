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
public class PacienteDTO {

    String id;
    String nome;
    String cpf;
    LocalDate dataNascimento;

    public  static PacienteDTO convert(Paciente p) {
        return PacienteDTO.builder()
                .id(p.getId())
                .nome(p.getNome())
                .cpf(p.getDocumento())
                .dataNascimento(LocalDate.parse(p.getDataNascimento(), DateTimeFormatter.ISO_LOCAL_DATE))
                .build();
    }

}
