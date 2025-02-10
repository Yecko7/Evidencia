

public class RelacionCitaDoctorPaciente {
    private Cita cita;
    private Doctor doctor;
    private Paciente paciente;

    public RelacionCitaDoctorPaciente(Cita cita, Doctor doctor, Paciente paciente) {
        this.cita = cita;
        this.doctor = doctor;
        this.paciente = paciente;
    }

    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
}


