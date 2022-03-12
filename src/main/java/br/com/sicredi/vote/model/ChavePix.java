package br.com.sicredi.vote.model;

import br.com.sicredi.vote.dto.StatusChave;
import br.com.sicredi.vote.dto.TipoChave;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.Calendar;
import java.util.UUID;

@Entity
@Table(name = "chave_pix")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChavePix  implements Persistable<UUID> {

    @Id
    @GenericGenerator(name = "UUIDGenerator2", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator2")
    @Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
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
    @Enumerated(EnumType.STRING)
    @Column(name = "status_chave")
    private StatusChave statusChave;

    @NonNull
    @Column(name = "valor_chave", updatable = false, unique = true)
    private String valorChave;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_cadastro", updatable = false)
    private Calendar dataCadastro;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_inativacao")
    private Calendar dataInativacao;

    @Override
    public boolean isNew() {
        return id == null;
    }
}