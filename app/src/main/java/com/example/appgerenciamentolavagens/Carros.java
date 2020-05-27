package com.example.appgerenciamentolavagens;

import androidx.annotation.NonNull;

public class Carros {
    public String id,placa,modelo,ano,portas;

    @NonNull
    @Override
    public String toString() {
        return "modelo: "+modelo;
    }
}
