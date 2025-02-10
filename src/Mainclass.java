

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Mainclass {
        private static final List<Doctor> doctores = new ArrayList<>();
        private static final List<Paciente> pacientes = new ArrayList<>();
        private static final List<Cita> citas = new ArrayList<>();
        private static final List<RelacionCitaDoctorPaciente> relaciones = new ArrayList<>();
        private static final Scanner scanner = new Scanner(System.in);

        public static void main(String[] args) {
            cargarDoctores();
            cargarPacientes();
            cargarCitas();
            cargarRelaciones();

            menuPrincipal();
        }

        private static void menuPrincipal() {
            System.out.println("Bienvenido al sistema de administración de citas");
            System.out.println("1. Dar de alta doctores");
            System.out.println("2. Dar de alta pacientes");
            System.out.println("3. Crear cita");
            System.out.println("4. Relacionar cita con doctor y paciente");
            System.out.println("5. Ver citas relacionadas con doctor");
            System.out.println("6. Salir");
            System.out.print("Ingrese una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    darDeAltaDoctores();
                    break;
                case 2:
                    darDeAltaPacientes();
                    break;
                case 3:
                    crearCita();
                    break;
                case 4:
                    relacionarCitaDoctorPaciente();
                    break;
                case 5:
                    verCitasRelacionadasConDoctor();
                    break;
                case 6:
                    System.out.println("Gracias por usar el sistema de administración de citas. ¡Hasta luego!");
                    System.exit(0);
                default:
                    System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
                    break;
            }
        }

        private static void darDeAltaDoctores() {
            System.out.print("Ingrese el identificador único del doctor: ");
            String idDoctor = scanner.next();
            System.out.print("Ingrese el nombre completo del doctor: ");
            String nombre = scanner.next();
            System.out.print("Ingrese la especialidad del doctor: ");
            scanner.nextLine();
            String especialidad = scanner.nextLine();

            Doctor doctor = new Doctor(idDoctor, nombre, especialidad);
            doctores.add(doctor);

            guardarDoctores();

            System.out.println("El doctor ha sido dado de alta exitosamente.");
            regresarAlMenuPrincipal();
        }

        private static void darDeAltaPacientes() {
            System.out.print("Ingrese el identificador único del paciente: ");
            String idPaciente = scanner.next();
            System.out.print("Ingrese el nombre completo del paciente: ");
            String nombre = scanner.next();

            Paciente paciente = new Paciente(idPaciente, nombre);
            pacientes.add(paciente);

            guardarPacientes();

            System.out.println("El paciente ha sido dado de alta exitosamente.");
            regresarAlMenuPrincipal();
        }

        private static void crearCita() {
            System.out.print("Ingrese el identificador único de la cita: ");
            String idCita = scanner.next();
            System.out.print("Ingrese la fecha y hora de la cita: ");
            String fechaHora = scanner.next();
            System.out.print("Ingrese el motivo de la cita: ");
            scanner.nextLine();
            String motivo = scanner.nextLine();

            Cita cita = new Cita(idCita, fechaHora, motivo);
            citas.add(cita);

            guardarCitas();

            System.out.println("La cita ha sido creada exitosamente.");
            regresarAlMenuPrincipal();
        }

        private static void relacionarCitaDoctorPaciente() {
            System.out.print("Ingrese el identificador único de la cita: ");
            String idCita = scanner.next();
            System.out.print("Ingrese el identificador único del doctor: ");
            String idDoctor = scanner.next();
            System.out.print("Ingrese el identificador único del paciente: ");
            String idPaciente = scanner.next();

            Doctor doctor = buscarDoctorPorId(idDoctor);
            Paciente paciente = buscarPacientePorId(idPaciente);
            Cita cita = buscarCitaPorId(idCita);

            if (doctor != null && paciente != null && cita != null) {
                RelacionCitaDoctorPaciente relacion = new RelacionCitaDoctorPaciente(cita, doctor, paciente);
                relaciones.add(relacion);

                guardarRelaciones();

                System.out.println("La cita ha sido relacionada con el doctor");
            } else {
                System.out.println("No se encontró la cita, el doctor o el paciente");
            }

            regresarAlMenuPrincipal();
        }

        private static void verCitasRelacionadasConDoctor() {
            System.out.print("Ingrese el id del doctor: ");
            String idDoctor = scanner.next();

            Doctor doctor = buscarDoctorPorId(idDoctor);

            if (doctor != null) {
                List<Cita> citasRelacionadas = obtenerCitasRelacionadasConDoctor(doctor);

                if (citasRelacionadas.isEmpty()) {
                    System.out.println("No hay citas relacionadas con el doctor.");
                } else {
                    System.out.println("Citas relacionadas con el doctor " + doctor.getNombre() + ":");
                    for (Cita cita : citasRelacionadas) {
                        System.out.println("ID de la cita: " + cita.getIdCita());
                        System.out.println("Fecha y hora: " + cita.getFechaHora());
                        System.out.println("Motivo: " + cita.getMotivo());
                        System.out.println("----------------------");
                    }
                }
            } else {
                System.out.println("No se encontró el doctor. Verifique el identificador.");
            }

            regresarAlMenuPrincipal();
        }

        private static void regresarAlMenuPrincipal() {
            System.out.println();
            System.out.println("Presione enter para regresar al menú principal.");
            scanner.nextLine(); // Consume el salto de línea pendiente
            scanner.nextLine(); // Espera la entrada del usuario
            System.out.println();
            menuPrincipal();
        }

        private static void cargarDoctores() {
            try {
                File file = new File("db/doctores.txt");
                if (file.exists()) {
                    Scanner fileScanner = new Scanner(file);
                    while (fileScanner.hasNextLine()) {
                        String line = fileScanner.nextLine();
                        String[] parts = line.split(",");
                        String idDoctor = parts[0];
                        String nombre = parts[1];
                        String especialidad = parts[2];
                        Doctor doctor = new Doctor(idDoctor, nombre, especialidad);
                        doctores.add(doctor);
                    }
                    fileScanner.close();
                }
            } catch (IOException e) {
                System.out.println("Error al cargar los doctores desde la base de datos.");
            }
        }

        private static void guardarDoctores() {
            try {
                File file = new File("db/doctores.txt");
                FileWriter writer = new FileWriter(file);

                for (Doctor doctor : doctores) {
                    writer.write(doctor.getIdDoctor() + "," + doctor.getNombre() + "," + doctor.getEspecialidad() + "\n");
                }

                writer.close();
            } catch (IOException e) {
                System.out.println("Error al guardar los doctores en la base de datos.");
            }
        }

        private static void cargarPacientes() {
            try {
                File file = new File("db/pacientes.txt");
                if (file.exists()) {
                    Scanner fileScanner = new Scanner(file);
                    while (fileScanner.hasNextLine()) {
                        String line = fileScanner.nextLine();
                        String[] parts = line.split(",");
                        String idPaciente = parts[0];
                        String nombre = parts[1];
                        Paciente paciente = new Paciente(idPaciente, nombre);
                        pacientes.add(paciente);
                    }
                    fileScanner.close();
                }
            } catch (IOException e) {
                System.out.println("Error al cargar los pacientes desde la base de datos.");
            }
        }

        private static void guardarPacientes() {
            try {
                File file = new File("db/pacientes.txt");
                FileWriter writer = new FileWriter(file);

                for (Paciente paciente : pacientes) {
                    writer.write(paciente.getIdPaciente() + "," + paciente.getNombre() + "\n");
                }

                writer.close();
            } catch (IOException e) {
                System.out.println("Error al guardar los pacientes en la base de datos.");
            }
        }

        private static void cargarCitas() {
            try {
                File file = new File("db/citas.txt");
                if (file.exists()) {
                    Scanner fileScanner = new Scanner(file);
                    while (fileScanner.hasNextLine()) {
                        String line = fileScanner.nextLine();
                        String[] parts = line.split(",");
                        String idCita = parts[0];
                        String fechaHora = parts[1];
                        String motivo = parts[2];
                        Cita cita = new Cita(idCita, fechaHora, motivo);
                        citas.add(cita);
                    }
                    fileScanner.close();
                }
            } catch (IOException e) {
                System.out.println("Error al cargar las citas desde la base de datos.");
            }
        }

        private static void guardarCitas() {
            try {
                File file = new File("db/citas.txt");
                FileWriter writer = new FileWriter(file);

                for (Cita cita : citas) {
                    writer.write(cita.getIdCita() + "," + cita.getFechaHora() + "," + cita.getMotivo() + "\n");
                }

                writer.close();
            } catch (IOException e) {
                System.out.println("Error al guardar las citas en la base de datos.");
            }
        }

        private static void cargarRelaciones() {
            try {
                File file = new File("db/relaciones.txt");
                if (file.exists()) {
                    Scanner fileScanner = new Scanner(file);
                    while (fileScanner.hasNextLine()) {
                        String line = fileScanner.nextLine();
                        String[] parts = line.split(",");
                        String idCita = parts[0];
                        String idDoctor = parts[1];
                        String idPaciente = parts[2];

                        Doctor doctor = buscarDoctorPorId(idDoctor);
                        Paciente paciente = buscarPacientePorId(idPaciente);
                        Cita cita = buscarCitaPorId(idCita);

                        if (doctor != null && paciente != null && cita != null) {
                            RelacionCitaDoctorPaciente relacion = new RelacionCitaDoctorPaciente(cita, doctor, paciente);
                            relaciones.add(relacion);
                        }
                    }
                    fileScanner.close();
                }
            } catch (IOException e) {
                System.out.println("Error al cargar las relaciones desde la base de datos.");
            }
        }

        private static void guardarRelaciones() {
            try {
                File file = new File("db/relaciones.txt");
                FileWriter writer = new FileWriter(file);

                for (RelacionCitaDoctorPaciente relacion : relaciones) {
                    String idCita = relacion.getCita().getIdCita();
                    String idDoctor = relacion.getDoctor().getIdDoctor();
                    String idPaciente = relacion.getPaciente().getIdPaciente();
                    writer.write(idCita + "," + idDoctor + "," + idPaciente + "\n");
                }

                writer.close();
            } catch (IOException e) {
                System.out.println("Error al guardar las relaciones en la base de datos.");
            }
        }

        private static Doctor buscarDoctorPorId(String idDoctor) {
            for (Doctor doctor : doctores) {
                if (doctor.getIdDoctor().equals(idDoctor)) {
                    return doctor;
                }
            }
            return null;
        }

        private static Paciente buscarPacientePorId(String idPaciente) {
            for (Paciente paciente : pacientes) {
                if (paciente.getIdPaciente().equals(idPaciente)) {
                    return paciente;
                }
            }
            return null;
        }

        private static Cita buscarCitaPorId(String idCita) {
            for (Cita cita : citas) {
                if (cita.getIdCita().equals(idCita)) {
                    return cita;
                }
            }
            return null;
        }

        private static List<Cita> obtenerCitasRelacionadasConDoctor(Doctor doctor) {
            List<Cita> citasRelacionadas = new ArrayList<>();
            for (RelacionCitaDoctorPaciente relacion : relaciones) {
                if (relacion.getDoctor().equals(doctor)) {
                    citasRelacionadas.add(relacion.getCita());
                }
            }
            return citasRelacionadas;
        }
    }

