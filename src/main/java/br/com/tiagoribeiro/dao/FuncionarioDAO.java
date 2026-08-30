package br.com.tiagoribeiro.dao;

import br.com.tiagoribeiro.model.Funcionario;

import java.util.List;

public interface FuncionarioDAO {

    void salvar(Funcionario funcionario);

    Funcionario buscarPorId(Long id);

    List<Funcionario> listarTodos();

    void atualizar(Funcionario funcionario);

    void deletar(Long id);


}
