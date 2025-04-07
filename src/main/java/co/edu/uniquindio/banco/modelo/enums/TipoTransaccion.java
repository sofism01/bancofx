package co.edu.uniquindio.banco.modelo.enums;

import lombok.Getter;

@Getter
public enum TipoTransaccion {
    DEPOSITO {
        @Override
        public String toString() {
            return "DEPOSITO";
        }
    },
    RETIRO {
        @Override
        public String toString() {
            return "RETIRO";
        }
    }

    }

