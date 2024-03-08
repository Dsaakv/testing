package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestBankController {

    @InjectMocks
    // denne skal testes
    private BankController bankController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentTransaksjoner_LoggetInn() {
        // Arrange
        String kontoNr = "12345";
        String fraDato = "2021-01-01";
        String tilDato = "2021-01-31";
        String personnummer = "01010110523";

        Konto expectedKonto = new Konto();
        when(sjekk.loggetInn()).thenReturn(personnummer);
        when(repository.hentTransaksjoner(kontoNr, fraDato, tilDato)).thenReturn(expectedKonto);

        // Act
        Konto result = bankController.hentTransaksjoner(kontoNr, fraDato, tilDato);

        // Assert
        assertEquals(expectedKonto, result);
    }

    @Test
    public void hentTransaksjoner_IkkeLoggetInn() {
        // Arrange
        String kontoNr = "12345";
        String fraDato = "2021-01-01";
        String tilDato = "2021-01-31";

        when(sjekk.loggetInn()).thenReturn(null);

        // Act
        Konto result = bankController.hentTransaksjoner(kontoNr, fraDato, tilDato);

        // Assert
        assertNull(result);
    }

    @Test
    public void hentSaldi_LoggetInn() {
        // Arrange
        String personnummer = "01010110523";
        List<Konto> expectedSaldi = Arrays.asList(
                new Konto("12345", personnummer, 5000, "Sparekonto", "NOK", null),
                new Konto("67890", personnummer, 12000, "Brukskonto", "NOK", null)
        );
        when(sjekk.loggetInn()).thenReturn(personnummer);
        when(repository.hentSaldi(anyString())).thenReturn(expectedSaldi);

        // Act
        List<Konto> result = bankController.hentSaldi();

        // Assert
        assertEquals(expectedSaldi, result);
    }

    @Test
    public void hentSaldi_IkkeLoggetInn() {
        // Arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // Act
        List<Konto> result = bankController.hentSaldi();

        // Assert
        assertNull(result);
    }

    @Test
    public void registrerBetaling_LoggetInn() {
        // Arrange
        String personnummer = "01010110523";
        Transaksjon betaling = new Transaksjon(); // Set up the Transaksjon object as necessary
        String expectedResponse = "OK";

        when(sjekk.loggetInn()).thenReturn(personnummer);
        when(repository.registrerBetaling(any(Transaksjon.class))).thenReturn(expectedResponse);

        // Act
        String result = bankController.registrerBetaling(betaling);

        // Assert
        assertEquals(expectedResponse, result);
    }

    @Test
    public void registrerBetaling_IkkeLoggetInn() {
        // Arrange
        Transaksjon betaling = new Transaksjon(); // Set up the Transaksjon object as necessary
        when(sjekk.loggetInn()).thenReturn(null);

        // Act
        String result = bankController.registrerBetaling(betaling);

        // Assert
        assertNull(result);
    }

    @Test
    public void hentKundeInfo_loggetInn() {

        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("Logget inn");

        when(repository.hentKundeInfo(anyString())).thenReturn(enKunde);

        // act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertEquals(enKunde, resultat);
    }

    @Test
    public void hentKundeInfo_IkkeloggetInn() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentKonti_LoggetInn()  {
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKonti(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentKonti_IkkeLoggetInn()  {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNull(resultat);
    }


    @Test
    public void testHentBetalingerWhenPersonnummerNotNull() {
        // Arrange
        String personnummer = "123456789";
        List<Transaksjon> transaksjoner = new ArrayList<>();
        transaksjoner.add(new Transaksjon(/* Construct Transaksjon object as needed */));
        
        Mockito.when(sjekk.loggetInn()).thenReturn(personnummer);
        Mockito.when(repository.hentBetalinger(personnummer)).thenReturn(transaksjoner);

        // Act
        List<Transaksjon> result = bankController.hentBetalinger();

        // Assert
        assertNotNull(result);
        assertEquals(transaksjoner, result);
    }

    @Test
    public void testHentBetalingerWhenPersonnummerNull() {
        // Arrange
        Mockito.when(sjekk.loggetInn()).thenReturn(null);

        // Act
        List<Transaksjon> result = bankController.hentBetalinger();

        // Assert
        assertNull(result);
    }

     @Test
    public void testUtforBetalingWhenLoggedInAndRepositoryReturnsOK() {
        // Arrange
        String personnummer = "123456789";
        int txID = 123;
        List<Transaksjon> transaksjoner = new ArrayList<>();
        transaksjoner.add(new Transaksjon(/* Construct Transaksjon object as needed */));
        
        when(sjekk.loggetInn()).thenReturn(personnummer);
        when(repository.utforBetaling(txID)).thenReturn("OK");
        when(repository.hentBetalinger(personnummer)).thenReturn(transaksjoner);

        // Act
        List<Transaksjon> result = bankController.utforBetaling(txID);

        // Assert
        assertNotNull(result);
        assertEquals(transaksjoner, result);
    }

    @Test
    public void testUtforBetalingIkkeLoggetInn() {
        // Arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // Act
        List<Transaksjon> result = bankController.utforBetaling(123);

        // Assert
        assertNull(result);
        verify(repository, never()).utforBetaling(anyInt());
    }

    @Test
    public void testUtforBetalingWhenRepositoryReturnsNotOK() {
        // Arrange
        String personnummer = "123456789";
        int txID = 123;
        
        when(sjekk.loggetInn()).thenReturn(personnummer);
        when(repository.utforBetaling(txID)).thenReturn("ERROR");

        // Act
        List<Transaksjon> result = bankController.utforBetaling(txID);

        // Assert
        assertNull(result);
        verify(repository, never()).hentBetalinger(anyString());
    }

    @Test
    public void testEndreKundeInfoLoggetInn() {
        // Arrange
        String personnummer = "123456789";
        Kunde innKunde = new Kunde();
        innKunde.setPersonnummer("987654321"); // random personnummer for testing
        
        when(sjekk.loggetInn()).thenReturn(personnummer);
        when(repository.endreKundeInfo(innKunde)).thenReturn("SUCCESS");

        // Act
        String result = bankController.endre(innKunde);

        // Assert
        assertEquals("SUCCESS", result);
        assertEquals(personnummer, innKunde.getPersonnummer());
    }

    @Test
    public void testEndreKundeInfoWhenIkkeLoggetInn() {
        // Arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // Act
        String result = bankController.endre(new Kunde());

        // Assert
        assertNull(result);
        verify(repository, never()).endreKundeInfo(any());
    }
}

