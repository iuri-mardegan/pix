package br.com.sicredi.vote.model;

import br.com.sicredi.vote.dto.TipoChave;
import lombok.*;

import javax.persistence.*;
import java.util.Calendar;
import java.util.UUID;

@Entity
@Table(name = "chave_pix")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChavePix {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "conta")
    private Conta conta;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_chave")
    private TipoChave tipoChave;

    @NonNull
    @Column(name = "valor_chave", updatable = false, unique = true)
    private String valorChave;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_cadastro", updatable = false)
    private Calendar dataCadastro;
}