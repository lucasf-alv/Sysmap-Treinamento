package com.example.demo.model;

import com.example.demo.exception.BusinessException;
import com.example.demo.repository.DonoRepository;
import com.example.demo.service.DonoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DonoTest {
    @Mock
    private DonoRepository donoRepository;
    @InjectMocks
    private DonoService donoService;
    // Arrange : Prepare os dados dos testes e configure os Mocks
    // Act : Execute o método a ser testado
    // Assert: valide os resultados esperados
    @Test
    void createDeveRemoverEspacoseSalvarDono(){
        // Arrange
        Dono input = new Dono();
        input.setId(null);
        input.setNome("  Joao da Silva "  );
        input.setCpf("   030344039  ");

        Dono response = new Dono();
        response.setId(1L);
        response.setNome("Joao da Silva");
        response.setCpf("030344039");

        when(donoRepository.existsByCpf("030344039")).thenReturn(false);
        when(donoRepository.save(input)).thenReturn(response);


        // Act
        Dono result = donoService.create(input);

        // Assert
        assertNotNull(result);
        assertEquals("030344039",result.getCpf());
        assertEquals("Joao da Silva", input.getNome());




    }
    @Test
    void createDeveRetornarErroQuandoCPFjaexistir(){
        Dono input = new Dono();
        input.setId(null);
        input.setNome("Maria");
        input.setCpf("0345959430");
        when(donoRepository.existsByCpf("0345959430")).thenReturn(true);


        BusinessException exception = assertThrows(BusinessException.class, () ->{
            donoService.create(input);
        });

        assertEquals("Já existe um dono como esse CPF", exception.getMessage());

    }

}