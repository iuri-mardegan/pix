package br.com.sicredi.vote.controller;


import br.com.sicredi.vote.dto.PixRequestDTO;
import br.com.sicredi.vote.model.Conta;
import br.com.sicredi.vote.model.ChavePix;
import br.com.sicredi.vote.service.ChavePixService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class VoteControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ChavePixService votoService;
//    @MockBean
//    private ContaService pautaService;
//
//    @Test
//    public void gerarPautaTest() throws Exception {
//        when(pautaService.novaPauta()).thenReturn(new Conta());
//        this.mockMvc.perform(get("/pauta"))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void votosPautaTest() throws Exception {
//        when(votoService.addVoto(anyString(), any(Integer.class), anyString())).thenReturn(new ChavePix());
//        this.mockMvc.perform(get("/voto/12345678911/123/sim"))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void resultadoPautaTest() throws Exception {
//        when(votoService.getPauta(any(Integer.class)))
//                .thenReturn(PixRequestDTO.builder().build());
//        this.mockMvc.perform(get("/resultado-pauta/123"))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testPerformace() throws Exception {
//        when(pautaService.novaPauta()).thenReturn(new Conta());
//        for (int i = 1; i <= 10000; i++) {
//            this.mockMvc.perform(get("/pauta"))
//                    .andDo(print())
//                    .andExpect(status().isOk());
//        }
//    }
}