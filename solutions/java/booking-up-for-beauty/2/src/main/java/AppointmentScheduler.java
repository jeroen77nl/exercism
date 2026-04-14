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
        int hour = appointmentDate.getHour();
        return hour >= 12 && hour < 18;
    }

    public String getDescription(LocalDateTime appointmentDate) {
        final String PATTERN = "'You have an appointment on' EEEE, MMMM d, yyyy, 'at' K:mm a.";
        return DateTimeFormatter.ofPattern(PATTERN, Locale.US).format(appointmentDate);
    }

    public LocalDate getAnniversaryDate() {
        LocalDate openingDate = LocalDate.of(2012, 9, 15);
        return LocalDate.of(
                    LocalDate.now().getYear(), 
                    openingDate.getMonth(), 
                    openingDate.getDayOfMonth());
    }
}
