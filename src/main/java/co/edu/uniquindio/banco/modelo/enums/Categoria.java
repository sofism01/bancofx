package co.edu.uniquindio.banco.modelo.enums;

import lombok.Getter;

@Getter
public enum Categoria {
    ALIMENTOS {
        @Override
        public String toString() {
            return "ALIMENTOS";
        }
    },
    TRANSPORTE {
        @Override
        public String toString() {
            return "TRANSPORTE";
        }
    },
    SALUD {
        @Override
        public String toString() {
            return "SALUD";
        }
    },
    EDUCACION {
        @Override
        public String toString() {
            return "EDUCACION";
        }
    },
    ENTRETENIMIENTO {
        @Override
        public String toString() {
            return "ENTRETENIMIENTO";
        }
    },
    OTROS {
        @Override
        public String toString() {
            return "OTROS";
        }
    }

}
