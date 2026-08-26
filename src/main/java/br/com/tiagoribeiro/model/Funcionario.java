package br.com.tiagoribeiro.model;

import java.math.BigDecimal;

public class Funcionario {

    private Long id;
    private String nome;
    private String cargo;
    private BigDecimal salario;

    public Funcionario(){

    }
    public Funcionario(Long id, String nome, String cargo, BigDecimal salario){
        this.id = id;
        this.nome = nome;
        this.cargo = cargo;
        this.salario = salario;
    }

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public String getCargo(){
        return cargo;
    }
    public void setCargo(String cargo){
        this.cargo = cargo;
    }
    public BigDecimal getSalario(){
        return salario;
    }
    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }
}
