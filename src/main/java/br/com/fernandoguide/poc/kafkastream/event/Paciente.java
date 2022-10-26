package br.com.fernandoguide.poc.kafkastream.event;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Builder
@Jacksonized
@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private  String nome;
    private String documento;
    private LocalDate dataNascimento;


    public  static Paciente convert(PacienteDTO p) {
        return Paciente.builder()
                .id(p.getId())
                .nome(p.getNome())
                .documento(p.getCpf())
                .dataNascimento(p.getDataNascimento())
                .build();
    }

}
