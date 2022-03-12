package br.com.sicredi.vote.controller;


import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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