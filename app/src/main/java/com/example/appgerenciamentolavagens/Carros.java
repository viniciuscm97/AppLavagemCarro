package com.example.appgerenciamentolavagens;

import androidx.annotation.NonNull;

public class Carros {
    public String id,placa,modelo,ano,portas,data_cadastro,marca;

    @NonNull
    @Override
    public String toString() {
        return "modelo: "+modelo;
    }
}
