package br.com.sicredi.vote.model;

import br.com.sicredi.vote.dto.TipoConta;
import br.com.sicredi.vote.dto.TipoPessoaConta;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "conta")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Conta  implements Persistable<UUID> {

    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "nome_correntista")
    private String nomeCorrentista;

    @Column(name = "sobrenome_correntista")
    private String sobrenomeCorrentista;

    @NonNull
    @Column(name = "numero_agencia", columnDefinition = "DECIMAL(4,0)")
    private Long numAgencia;

    @NonNull
    @Column(name = "numero_conta", columnDefinition = "DECIMAL(8,0)")
    private Long numConta;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_conta")
    private TipoConta tipoConta;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pessoa_conta")
    private TipoPessoaConta tipoPessoaConta;

    @OneToMany(mappedBy = "conta")
    private List<ChavePix> pixList;

    @Override
    public boolean isNew() {
        return id == null;
    }
}