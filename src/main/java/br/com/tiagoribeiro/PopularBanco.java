package br.com.tiagoribeiro;

import br.com.tiagoribeiro.model.Funcionario;
import br.com.tiagoribeiro.service.FuncionarioService;

import java.math.BigDecimal;

public class PopularBanco {
    public static void main(String[] args) {
        FuncionarioService service = new FuncionarioService();
        for (int i = 1; i <= 12; i++) {
            Funcionario f = new Funcionario();
            f.setNome("Funcionario " + i);
            f.setCargo("Cargo " + i);
            f.setSalario(new BigDecimal(1000 + i));
            service.cadastrar(f);
        }
    }
}
