package br.com.sicredi.vote.service;

import br.com.sicredi.vote.model.Conta;
import br.com.sicredi.vote.repository.ContaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class PautaServiceTest {

//    @Autowired
//    private ContaService pautaService;
//
//    @MockBean
//    private ContaRepository pautaRepository;
//
//    @Test
//    public void teste(){
//        when(pautaRepository.save(any(Conta.class))).thenReturn(new Conta());
//        pautaService.novaPauta();
//    }
}