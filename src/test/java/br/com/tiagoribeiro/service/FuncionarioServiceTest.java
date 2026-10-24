package br.com.tiagoribeiro.service;

import br.com.tiagoribeiro.dao.FuncionarioDAO;
import br.com.tiagoribeiro.model.Funcionario;
import br.com.tiagoribeiro.util.Pagina;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FuncionarioServiceTest {

    @Mock
    private FuncionarioDAO dao;

    @InjectMocks
    private FuncionarioService service;

    private Funcionario funcionarioValido;

    @BeforeEach
    void setUp(){
        funcionarioValido = new Funcionario();
        funcionarioValido.setNome("Ana Costa");
        funcionarioValido.setCargo("Analista");
        funcionarioValido.setSalario(new BigDecimal("4000.00"));
    }

    @Test
    void deveCadastrarFuncionarioValido(){
        //Arranje: já feiro no srtUp()

        //Act
        service.cadastrar(funcionarioValido);

        //Assert:
        verify(dao, times(1)).salvar(funcionarioValido);
    }

    @Test
    void deveRejeitarFuncionarioComNomeVazio(){
        funcionarioValido.setNome("");

        IllegalArgumentException exception =  assertThrows(
                IllegalArgumentException.class,
                () -> service.cadastrar(funcionarioValido)
        );

        assertEquals("Nome do Funcionario é obrigatório.", exception.getMessage());
        verify(dao, never()).salvar(any()); //para garantir que nada foi salvo
    }

    @Test
    void deveRejeitarSalarioNegativoOuZero(){
        funcionarioValido.setSalario(BigDecimal.ZERO);

        assertThrows(IllegalArgumentException.class,
                () -> service.cadastrar(funcionarioValido));
        verify(dao, never()).salvar(any());
    }

    @Test
    void deveLancarExcecaAoBuscarFuncionarioInexistente(){
        when(dao.buscarPorId(99L)).thenReturn(null);//para o mock responder isso

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.buscarPorId(99L)
                );

        assertTrue(exception.getMessage().contains("99"));
    }

    @Test
    void deveCalcularOffsetCorretoParaSegundaPagina(){
        when(dao.contar()).thenReturn(12L);
        when(dao.listarPaginado(5,5)).thenReturn(Collections.emptyList());

        Pagina<Funcionario> resultado = service.listarPaginado(2,5);

        assertEquals(2,resultado.getPaginaAtual());
        assertEquals(3, resultado.getTotalPaginas());
        verify(dao).listarPaginado(5,5);
    }

    @Test
    void deveAjustarPaginaAcimaDoLimiteParaUltimaPagina(){
        when(dao.contar()).thenReturn(12L);
        when(dao.listarPaginado(10,5)).thenReturn(Collections.emptyList());

        Pagina<Funcionario> resultado = service.listarPaginado(999,5);

        assertEquals(3,resultado.getTotalPaginas());
        assertFalse(resultado.isTemProxima());

    }

    @Test
    void deveAjustarPaginaZeroOuNegativaParaPrimeira(){
        when(dao.contar()).thenReturn(12L);
        when(dao.listarPaginado(0,5)).thenReturn(Collections.emptyList());

        Pagina<Funcionario> resultado = service.listarPaginado(-3,5);

        assertEquals(1, resultado.getPaginaAtual());
        assertFalse(resultado.isTemAnterior());
    }

}
