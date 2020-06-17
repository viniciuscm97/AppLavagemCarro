package com.example.appgerenciamentolavagens;

import androidx.annotation.NonNull;

public class Lavagens {

    public String id, data_entrega,data_cadastro ;
    public Funcionarios funcionario;
    public Carros carro;

    public double valor;

    @NonNull
    @Override
    public String toString() {
        return "Carro: "+carro.placa+" Funcionário: "+funcionario.nome+"\nValor: "+valor+" Entrega: " +data_entrega;
    }
}
