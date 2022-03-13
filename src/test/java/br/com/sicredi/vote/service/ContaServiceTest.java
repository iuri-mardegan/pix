
package br.com.sicredi.vote.service;

import br.com.sicredi.vote.dto.PixPutRequestDTO;
import br.com.sicredi.vote.dto.enums.StatusChave;
import br.com.sicredi.vote.dto.enums.TipoChave;
import br.com.sicredi.vote.dto.enums.TipoConta;
import br.com.sicredi.vote.exception.PixException;
import br.com.sicredi.vote.model.ChavePix;
import br.com.sicredi.vote.model.Conta;
import br.com.sicredi.vote.repository.ChavePixRepository;
import br.com.sicredi.vote.repository.ContaRepository;
import br.com.sicredi.vote.service.interfaces.ContaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class ContaServiceTest {

    @Autowired
    private ContaService contaService;

    @MockBean
    private ContaRepository contaRepository;
    @MockBean
    private ChavePixRepository chavePixRepository;

    @Test
    public void atualizaContaTeste() throws PixException {
        when(chavePixRepository.findById(any(UUID.class))).thenReturn(Optional.of(chavePixMock()));
        when(contaRepository.save(any(Conta.class))).thenReturn(contaMock());
        contaService.atualizaConta(pixPutRequestDTOMock());
    }

    @Test
    public void atualizaContaErrorTeste() throws PixException {
        when(chavePixRepository.findById(any(UUID.class))).thenReturn(Optional.of(chavePixMock()));
        when(contaRepository.save(any(Conta.class))).thenThrow(DataIntegrityViolationException.class);
        assertThrows(PixException.class, () -> {
            contaService.atualizaConta(pixPutRequestDTOMock());
        });
    }


    private PixPutRequestDTO pixPutRequestDTOMock(){
        return PixPutRequestDTO.builder()
                .id(UUID.randomUUID())
                .tipoConta(TipoConta.CORRENTE)
                .numAgencia(1234L)
                .numConta(12345678L)
                .nomeCorrentista("Iuri")
                .sobrenomeCorrentista("Mardegan").build();
    }

    private ChavePix chavePixMock(){
        return ChavePix.builder()
                .id(UUID.randomUUID())
                .tipoChave(TipoChave.CNPJ)
                .valorChave("82836894000119")
                .statusChave(StatusChave.ATIVO)
                .dataCadastro(Calendar.getInstance())
                .conta(contaMock())
                .build();
    }

    private Conta contaMock(){
        return Conta.builder()
                .tipoConta(TipoConta.CORRENTE)
                .numAgencia(1234L)
                .numConta(12345678L)
                .nomeCorrentista("Iuri")
                .sobrenomeCorrentista("Mardegan")
                .build();
    }
}