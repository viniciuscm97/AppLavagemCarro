package com.example.appgerenciamentolavagens;

import androidx.annotation.NonNull;

public class Lavagens {

    public String id,data_recebido, data_entrega,data_cadastro ;
    public Funcionarios funcionario;
    public Carros carro;

    public double valor;

    @NonNull
    @Override
    public String toString() {
        return "valor: "+valor;
    }
}
