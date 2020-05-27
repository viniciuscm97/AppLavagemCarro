package com.example.appgerenciamentolavagens;

import androidx.annotation.NonNull;

public class Lavagens {

    public String id,dataRecebido, carro, dataEntrega,funcionario;

    public double valor;

    @NonNull
    @Override
    public String toString() {
        return "valor: "+valor;
    }
}
