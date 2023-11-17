package com.votacao.utils;

import org.springframework.stereotype.Component;

@Component
public class UtilCpf {

    public static String removerCaracteresNaoNumericos(String cpf){
        return cpf.replaceAll("[^0-9]", "");
    }

    public static boolean validarCPF(String cpf) {
        //cpf = cpf.replaceAll("[^0-9]", ""); // Remove caracteres não numéricos

        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false; // Verifica se o CPF tem 11 dígitos e não é composto por dígitos repetidos
        }

        // Validação dos dígitos verificadores
        int[] digitos = new int[11];
        for (int i = 0; i < 11; i++) {
            digitos[i] = Integer.parseInt(cpf.substring(i, i + 1));
        }

        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += digitos[i] * (10 - i);
        }

        int resto = soma % 11;
        int digitoVerificador1 = (resto < 2) ? 0 : 11 - resto;

        if (digitoVerificador1 != digitos[9]) {
            return false; // Primeiro dígito verificador inválido
        }

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += digitos[i] * (11 - i);
        }

        resto = soma % 11;
        int digitoVerificador2 = (resto < 2) ? 0 : 11 - resto;

        return digitoVerificador2 == digitos[10]; // Retorna true se o segundo dígito verificador for válido
    }
}
