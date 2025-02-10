

public class Cita {
    private String idCita;
    private String fechaHora;
    private String motivo;

    public Cita(String idCita, String fechaHora, String motivo) {
        this.idCita = idCita;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
    }

    public String getIdCita() {
        return idCita;
    }

    public void setIdCita(String idCita) {
        this.idCita = idCita;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}

