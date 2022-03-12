package br.com.sicredi.vote.dto;

import br.com.sicredi.vote.dto.enums.TipoChave;
import br.com.sicredi.vote.dto.enums.TipoConta;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.Calendar;
import java.util.UUID;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PixResponseRequestDTO {

    @JsonProperty(required = true)
    private UUID id;

    @JsonProperty(required = true)
    private TipoChave tipoChave;

    @JsonProperty(required = true)
    private String valChave;

    @JsonProperty(required = true)
    private TipoConta tipoConta;

    @JsonProperty(required = true)
    private Long numAgencia;

    @JsonProperty(required = true)
    private Long numConta;

    @JsonProperty(required = true)
    private String nomeCorrentista;

    @JsonProperty
    private String sobrenomeCorrentista;

    @JsonProperty
    private Calendar dataHoraCadastro;

    @JsonProperty
    private Calendar dataHoraInativacao;

}
