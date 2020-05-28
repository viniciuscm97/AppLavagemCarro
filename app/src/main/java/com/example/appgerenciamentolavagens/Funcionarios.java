package com.example.appgerenciamentolavagens;

import androidx.annotation.NonNull;

public class Funcionarios {
    public String id,nome, dta_cadastro;

    @NonNull
    @Override
    public String toString() {
        return "nome: "+nome;
    }
}
