import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

class AppointmentScheduler {
    public LocalDateTime schedule(String appointmentDateDescription) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("M/d/yyyy HH:mm:ss");
        return LocalDateTime.parse(appointmentDateDescription,dateTimeFormatter);
    }

    public boolean hasPassed(LocalDateTime appointmentDate) {
        return appointmentDate.isBefore(LocalDateTime.now());
    }

    public boolean isAfternoonAppointment(LocalDateTime appointmentDate) {
        return appointmentDate.getHour() >= 12 && appointmentDate.getHour() < 18;
    }

    public String getDescription(LocalDateTime appointmentDate) {
        return "You have an appointment on %s, at %s.".formatted(
                DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy", Locale.US)
                    .format(appointmentDate),
                DateTimeFormatter.ofPattern("K:mm a", Locale.US)
                    .format(appointmentDate));
    }

    public LocalDate getAnniversaryDate() {
        LocalDate openingDate = LocalDate.of(2012, 9, 15);
        return LocalDate.of(
                    LocalDate.now().getYear(), 
                    openingDate.getMonth(), 
                    openingDate.getDayOfMonth());
    }
}
