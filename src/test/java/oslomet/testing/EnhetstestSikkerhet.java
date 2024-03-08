package oslomet.testing;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.servlet.http.HttpSession;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EnhetstestSikkerhet {
    @InjectMocks
    private Sikkerhet sikkerhet;

    @Mock
    private HttpSession session;

    @Mock
    private BankRepository bankRepository;

    @Test
    public void testSjekkLoggInn_ValidCredentials() {
        // Arrange
        String personnummer = "12345678901";
        String passord = "passord123";
        when(bankRepository.sjekkLoggInn(personnummer, passord)).thenReturn("OK");

        // Act
        String resultat = sikkerhet.sjekkLoggInn(personnummer, passord);

        // Assert
        assertEquals("OK", resultat);
    }

    @Test
    public void testSjekkLoggInn_InvalidPersonnummer() {
        // Arrange
        String personnummer = "123456"; // Ugyldig personnummer
        String passord = "password123";

        // Act
        String resultat = sikkerhet.sjekkLoggInn(personnummer, passord);

        // Assert
        assertEquals("Feil i personnummer", resultat);
    }
    @Test
    public void testSjekkLoggInn_InvalidPassord() {
        // Arrange
        String personnummer = "12345678901";
        String passord = "123"; // Ugyldig passord

        // Act
        String resultat = sikkerhet.sjekkLoggInn(personnummer, passord);

        // Assert
        assertEquals("Feil i passord", resultat);
    }
    @Test
    public void testSjekkLoggInn_IncorrectCredentials() {
        // Arrange
        String personnummer = "12345678901";
        String passord = "password123";
        when(bankRepository.sjekkLoggInn(personnummer, passord)).thenReturn("Feil");

        // Act
        String resultat = sikkerhet.sjekkLoggInn(personnummer, passord);

        // Assert
        assertEquals("Feil i personnummer eller passord", resultat);
    }

    @Test
    public void testLoggUt() {
        // Act
        sikkerhet.loggUt();

        // Assert
        assertEquals(null, session.getAttribute("Innlogget"));
    }
}
