package br.com.sicredi.vote.dto;

import br.com.sicredi.vote.dto.enums.TipoConta;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PixPutRequestDTO {

    @NonNull
    @JsonProperty(required = true)
    private UUID id;

    @NonNull
    @JsonProperty(required = true)
    private TipoConta tipoConta;

    @NonNull
    @JsonProperty(required = true)
    private Long numAgencia;

    @NonNull
    @JsonProperty(required = true)
    private Long numConta;

    @NonNull
    @JsonProperty(required = true)
    private String nomeCorrentista;

    @JsonProperty
    private String sobrenomeCorrentista;

}
