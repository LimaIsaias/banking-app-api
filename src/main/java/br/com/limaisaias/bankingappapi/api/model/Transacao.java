package br.com.limaisaias.bankingappapi.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Transacao {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "conta_rem_id")
    private Conta contaRemetente;

    @ManyToOne
    @JoinColumn(name = "conta_dest_id")
    private Conta contaDestinatario;

    @Column
    private Double valor;

    @Column(name = "data_transacao")
    private LocalDate dataTransacao;

}
