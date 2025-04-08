package co.edu.uniquindio.banco.modelo.entidades;

import co.edu.uniquindio.banco.config.Constantes;
import co.edu.uniquindio.banco.modelo.vo.PorcentajeGastosIngresos;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Setter
public class BilleteraVirtual {
    private String numero;
    private float saldo;
    private Usuario usuario;
    private ArrayList<Transaccion> transacciones;


    public BilleteraVirtual(String numero, float saldo, Usuario usuario) {
        this.numero = numero;
        this.saldo = saldo;
        this.usuario = usuario;
        this.transacciones = new ArrayList<>();
    }

    public boolean tieneSaldo(float monto) {
        float montoConComision = monto + Constantes.COMISION;
        return saldo >= montoConComision;
    }

    public void retirar(float monto, Transaccion transaccion) throws Exception{

        float montoConComision = monto + Constantes.COMISION;

        if (montoConComision <= 0){
            throw new Exception("El monto a retirar debe ser mayor a cero");
        }

        transaccion.setComision(Constantes.COMISION);

        saldo -= montoConComision;
        transacciones.add(transaccion);
    }

    public void depositar(float monto, Transaccion transaccion) throws Exception {

        if (monto <= 0){
            throw new Exception("El monto a retirar debe ser mayor a cero");
        }

        saldo += monto;
        transacciones.add(transaccion);
    }

    public ArrayList<Transaccion> obtenerTransaccionesPeriodo(LocalDateTime inicio, LocalDateTime fin) {

        ArrayList<Transaccion> transaccionesMes = new ArrayList<>();

        for (int i = 0; i < transacciones.size(); i++) {
            if(transacciones.get(i).getFecha().isAfter(inicio) && transacciones.get(i).getFecha().isBefore(fin)){
                transaccionesMes.add(transacciones.get(i));
            }
        }
        return transaccionesMes;
    }

    public PorcentajeGastosIngresos obtenerPorcentajeGastosIngresos(int mes, int anio) {

        float ingresos = 0;
        float egresos = 0; //gastos

        for (Transaccion transaccion : transacciones){
            if(transaccion.getFecha().getMonthValue() == mes && transaccion.getFecha().getYear() == anio){
                if(transaccion.getBilleteraOrigen() == this) {
                    egresos += transaccion.getMonto() + transaccion.getComision();
                }else{
                    ingresos += transaccion.getMonto();
                }
            }
        }

        float total = ingresos + egresos;
        float porcentajeGastos = (egresos / total) * 100;
        float porcentajeIngresos = (ingresos / total) * 100;

        return new PorcentajeGastosIngresos(
                porcentajeGastos,
                porcentajeIngresos
        );
    }

    public ArrayList<Transaccion> obtenerTransacciones() {
        return transacciones;
    }

    public String getNumero() {
        return numero;
    }

    public float consultarSaldo() {
        return saldo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    @Override
    public String toString() {
        return "BilleteraVirtual{" +
                "numero='" + numero + '\'' +
                ", saldo=" + saldo +
                ", usuario=" + usuario +
                ", transacciones=" + transacciones +
                '}';
    }
}