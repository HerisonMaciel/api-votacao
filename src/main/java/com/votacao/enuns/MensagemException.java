package com.votacao.enuns;

public enum MensagemException {
    SESSAO_JA_EXISTE{
        @Override
        String getErroMensagem() {
            return "Sessão já cadastrada com este ID!";
        }
    },
    SESSAO_NAO_ENCONTRADA{
        @Override
        String getErroMensagem() {
            return "Sessão não encontrada!";
        }
    },
    SESSAO_FECHADA{
        @Override
        String getErroMensagem() {
            return "Sessão encerrada!";
        }
    },
    VOTO_JA_REGISTRADO{
        @Override
        String getErroMensagem() {
            return "Voto já resgistrado com esse CPF!";
        }
    },
    PAUTA_NAO_ENCONTRADA{
        @Override
        String getErroMensagem() {
            return "Pauta não encontrada!";
        }
    };

    abstract String getErroMensagem();
}
