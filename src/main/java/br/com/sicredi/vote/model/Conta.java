package br.com.sicredi.vote.model;

import br.com.sicredi.vote.dto.TipoConta;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "conta")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Conta {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "nome_correntista")
    private String nomeCorrentista;

    @Column(name = "sobrenome_correntista")
    private String sobrenomeCorrentista;

    @NonNull
    @Column(name = "numero_agencia")
    private Long numAgencia;

    @NonNull
    @Column(name = "numero_conta")
    private Long numConta;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_conta")
    private TipoConta tipoConta;

    @OneToMany(mappedBy = "conta")
    private List<ChavePix> pixList;

}