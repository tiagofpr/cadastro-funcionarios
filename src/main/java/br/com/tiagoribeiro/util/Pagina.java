package br.com.tiagoribeiro.util;

import java.util.List;
import java.util.Map;

public class Pagina<T> {

    private final List<T> itens;
    private final int paginaAtual;
    private final int tamanho;
    private final long totalItens;

    public Pagina(List<T> itens, int paginaAtual, int tamanho, long totalItens){
        this.itens = itens;
        this.paginaAtual = paginaAtual;
        this.tamanho = tamanho;
        this.totalItens =  totalItens;
    }

    public List<T> getItens() {
        return itens;
    }

    public int getPaginaAtual() {
        return paginaAtual;
    }

    public int getTamanho() {
        return tamanho;
    }

    public long getTotalItens() {
        return totalItens;
    }

    public int getTotalPaginas(){
        return (int) Math.ceil((double) totalItens / tamanho);
    }

    public boolean isTemAnterior(){
        return paginaAtual > 1;
    }
    
    public boolean isTemProxima(){
        return paginaAtual < getTotalPaginas();
    }
}
