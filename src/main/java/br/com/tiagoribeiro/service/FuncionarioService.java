package br.com.tiagoribeiro.service;

import br.com.tiagoribeiro.dao.FuncionarioDAO;
import br.com.tiagoribeiro.dao.FuncionarioDAOImpl;
import br.com.tiagoribeiro.model.Funcionario;

import java.math.BigDecimal;
import java.util.List;

public class FuncionarioService {

    private final FuncionarioDAO dao;

    public FuncionarioService(){
        this.dao = new FuncionarioDAOImpl();
    }

    public FuncionarioService(FuncionarioDAO dao){
        this.dao= dao;
    }

    public void cadastrar(Funcionario funcionario){
        validar(funcionario);
        dao.salvar(funcionario);
    }

    public Funcionario buscarPorId(Long id){
        Funcionario funcionario = dao.buscarPorId(id);
        if(funcionario == null){
            throw new IllegalArgumentException("Funcionario com id: " + id + "não encontrado");
        }
        return funcionario;
    }

    public List<Funcionario> listarTodos(){
        return dao.listarTodos();
    }

    public void atualizar(Long id, Funcionario dadosNovos){
        Funcionario existente = buscarPorId(id);

        existente.setNome(dadosNovos.getNome());
        existente.setCargo(dadosNovos.getCargo());
        existente.setSalario(dadosNovos.getSalario());

        validar(existente);
        dao.atualizar(existente);
    }

    public void deletar(Long id){
        buscarPorId(id); // para garantir que exista antes de remover
        dao.deletar(id);
    }

    //Metodos privado auxiliar
    private void validar(Funcionario funcionario){
        if(funcionario.getNome() == null || funcionario.getNome().trim().isEmpty()){
            throw new IllegalArgumentException("Nome do Funcionario é obrigatório.");
        }
        if(funcionario.getCargo() == null || funcionario.getCargo().trim().isEmpty()){
            throw new IllegalArgumentException("Cargo do Funcionario é obrigatório.");
        }
        if(funcionario.getSalario() == null || funcionario.getSalario().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Salario deve ser maior que zero.");
        }
    }




}
