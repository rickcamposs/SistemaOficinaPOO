package com.sistemaoficina.dto;

import java.time.LocalDateTime;

public class NotaFiscal {
    
    private int id;
    private LocalDateTime data;
    private double total = 0;

    public NotaFiscal(int id, LocalDateTime data) {
        this.id = id;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public double getTotal() {
        return total;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
}
