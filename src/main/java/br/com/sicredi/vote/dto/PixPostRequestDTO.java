package br.com.sicredi.vote.dto;

import br.com.sicredi.vote.dto.enums.TipoChave;
import br.com.sicredi.vote.dto.enums.TipoConta;
import br.com.sicredi.vote.dto.enums.TipoPessoaConta;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PixPostRequestDTO {

    @NonNull
    @JsonProperty(required = true)
    private TipoChave tipoChave;

    @NonNull
    @JsonProperty(required = true)
    private String valChave;

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

    @JsonProperty
    private TipoPessoaConta tipoPessoaConta;

}
