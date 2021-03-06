package br.com.sicredi.vote.dto;

import br.com.sicredi.vote.dto.enums.TipoChave;
import br.com.sicredi.vote.dto.enums.TipoConta;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Calendar;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class PixPutResponseDTO {

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

    @JsonProperty
    private TipoChave tipoChave;

    @JsonProperty
    private String valorChave;

    @JsonProperty
    private Calendar dataHoraCadastro;
}
