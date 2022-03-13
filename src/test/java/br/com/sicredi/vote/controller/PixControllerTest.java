package br.com.sicredi.vote.controller;


import br.com.sicredi.vote.dto.PixGetResponseRequestDTO;
import br.com.sicredi.vote.dto.PixPostRequestDTO;
import br.com.sicredi.vote.dto.PixPutRequestDTO;
import br.com.sicredi.vote.dto.PixPutResponseDTO;
import br.com.sicredi.vote.dto.enums.TipoChave;
import br.com.sicredi.vote.dto.enums.TipoConta;
import br.com.sicredi.vote.exception.PixException;
import br.com.sicredi.vote.service.interfaces.ChavePixService;
import br.com.sicredi.vote.service.interfaces.ContaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PixControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChavePixService chavePixService;
    @MockBean
    private ContaService contaService;

    @Test
    public void consultaPorIdTest() throws Exception {
        when(chavePixService.consultaPorId(any(UUID.class))).thenReturn(new PixGetResponseRequestDTO());
        this.mockMvc.perform(get("/pix/3430b233-adaf-4d87-9e12-bdf51f605f3b"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void consultaPorIdErrorTest() throws Exception {
        when(chavePixService.consultaPorId(any(UUID.class))).thenThrow(PixException.class);
        this.mockMvc.perform(get("/pix/3430b233-adaf-4d87-9e12-bdf51f605f3b"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deletePorIdTest() throws Exception {
        when(chavePixService.inativaChavePix(any(UUID.class))).thenReturn(new PixGetResponseRequestDTO());
        this.mockMvc.perform(delete("/pix/3430b233-adaf-4d87-9e12-bdf51f605f3b"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void addPixTest() throws Exception {
        when(chavePixService.addPix(any(PixPostRequestDTO.class))).thenReturn(UUID.randomUUID());
        this.mockMvc.perform(post("/pix")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pixPostRequestDTOMock()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void addPixTestError() throws Exception {
        when(chavePixService.addPix(any(PixPostRequestDTO.class))).thenThrow(PixException.class);
        this.mockMvc.perform(post("/pix")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pixPostRequestDTOMockError()))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void updatePixTest() throws Exception {
        when(contaService.atualizaConta(any(PixPutRequestDTO.class))).thenReturn(new PixPutResponseDTO());
        this.mockMvc.perform(put("/pix")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pixPutRequestDTOMock()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void consultaPorFiltroErrorTest() throws Exception {
        when(chavePixService.consulta(any(PixGetResponseRequestDTO.class))).thenReturn(new ArrayList<PixGetResponseRequestDTO>());
        this.mockMvc.perform(get("/pix")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pixGetResponseRequestDTOMock()))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void consultaPorFiltroTest() throws Exception {
        List<PixGetResponseRequestDTO> list = new ArrayList<>();
        list.add(pixGetResponseRequestDTOObjectMock());
        when(chavePixService.consulta(any(PixGetResponseRequestDTO.class))).thenReturn(list);
        this.mockMvc.perform(get("/pix")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pixGetResponseRequestDTOMock()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private PixGetResponseRequestDTO pixGetResponseRequestDTOObjectMock(){
        return PixGetResponseRequestDTO.builder()
                .tipoChave(TipoChave.CNPJ)
                .valChave("82836894000119")
                .tipoConta(TipoConta.CORRENTE)
                .numAgencia(1234L)
                .numConta(12345678L)
                .nomeCorrentista("Iuri")
                .sobrenomeCorrentista("Mardegan").build();
    }

    private String pixGetResponseRequestDTOMock(){
        return "{  " +
                "    \"numAgencia\": 1234,  " +
                "    \"numConta\": 12345678,  " +
                "    \"tipoChave\": \"CPF\"  " +
                "}";
    }

    private String pixPutRequestDTOMock(){
        return "{  " +
                "    \"id\":\"685961ae-f3a0-48b4-87d1-7be5e646b3d7\",  " +
                "    \"tipoConta\":\"CORRENTE\",  " +
                "    \"numAgencia\": 1234,  " +
                "    \"numConta\": 123456,  " +
                "    \"nomeCorrentista\":\"Iuri 2\",  " +
                "    \"sobrenomeCorrentista\":\"Mardegan\"  " +
                "}";
    }

    private String pixPostRequestDTOMockError(){
        return "{ " +
                "    \"tipoChave\":\"\", " +
                "    \"valChave\":\"\", " +
                "    \"tipoConta\":\"\", " +
                "    \"numAgencia\": 1234, " +
                "    \"numConta\": 12345678, " +
                "    \"nomeCorrentista\":\"\", " +
                "    \"sobrenomeCorrentista\":\"\" " +
                "}";
    }

    private String pixPostRequestDTOMock(){
        return "{ " +
                "    \"tipoChave\":\"CNPJ\", " +
                "    \"valChave\":\"82836894000119\", " +
                "    \"tipoConta\":\"CORRENTE\", " +
                "    \"numAgencia\": 1234, " +
                "    \"numConta\": 12345678, " +
                "    \"nomeCorrentista\":\"Iuri\", " +
                "    \"sobrenomeCorrentista\":\"Mardegan\" " +
                "}";
    }
}