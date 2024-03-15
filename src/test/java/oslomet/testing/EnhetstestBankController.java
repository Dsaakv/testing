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

import static org.junit.Assert.assertNotNull;
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


        List<Transaksjon> transaksjonListe = new ArrayList<>();

        Transaksjon betaling1 = new Transaksjon(1, "20167348913",
                250, "2023-12-16", "Skatt", null, "22334412345");
        Transaksjon betaling2 = new Transaksjon(2, "20226581465",
                8500, "2023-12-25", "Husleie", null, "22334412345");

        transaksjonListe.add(betaling1);
        transaksjonListe.add(betaling2);

        Konto konto = new Konto("01010110523", "22334412345",
                15000, "Brukskonto", "NOK", transaksjonListe);


        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentTransaksjoner(anyString(), anyString(),anyString())).thenReturn(konto);

        // Act
        Konto result = bankController.hentTransaksjoner("22334412345", "2023-12-01", "2023-12-31");

        // Assert
        assertEquals(konto, result);
    }

    @Test
    public void hentTransaksjoner_IkkeLoggetInn() {
        // Arrange


        when(sjekk.loggetInn()).thenReturn(null);

        // Act
        Konto result = bankController.hentTransaksjoner("22334412345", "2023-12-01", "2023-12-31");

        // Assert
        assertNull(result);
    }

    @Test
    public void hentTransaksjoner_feil(){
        List<Transaksjon> transaksjonListe = new ArrayList<>();

        Transaksjon betaling1 = new Transaksjon(1, "20167348913",
                250, "2023-12-16", "Skatt", null, "22334412345");
        Transaksjon betaling2 = new Transaksjon(2, "20226581465",
                8500, "2023-12-25", "Husleie", null, "22334412345");

        transaksjonListe.add(betaling1);
        transaksjonListe.add(betaling2);

        Konto konto = new Konto("01010110523", "22334412345",
                15000, "Brukskonto", "NOK", transaksjonListe);


        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentTransaksjoner(anyString(), anyString(),anyString())).thenReturn(null);

        // Act
        Konto result = bankController.hentTransaksjoner("22334412345", "2023-12-01", "2023-12-31");

        // Assert
        assertNull(result);
    }

    @Test
    public void hentSaldi_LoggetInn() {
        // Arrange
        List<Konto> kontoListe = new ArrayList<>();

        Konto konto1 = new Konto("01010110523", "105010123456",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("01010110523", "22334412345",
                10234.5, "Brukskonto", "NOK", null);


        kontoListe.add(konto1);
        kontoListe.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentSaldi(any(String.class))).thenReturn(kontoListe);

        // Act
        List<Konto> result = bankController.hentSaldi();
        // Assert
        assertEquals(kontoListe, result);
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
    public void hentSaldi_feil(){
        List<Konto> kontoListe = new ArrayList<>();

        Konto konto1 = new Konto("01010110523", "105010123456",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("01010110523", "22334412345",
                10234.5, "Brukskonto", "NOK", null);


        kontoListe.add(konto1);
        kontoListe.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentSaldi(any(String.class))).thenReturn(null);

        // Act
        List<Konto> result = bankController.hentSaldi();
        // Assert
        assertNull(result);
    }

    @Test
    public void registrerBetaling_LoggetInn() {
        // Arrange

        Transaksjon betaling = new Transaksjon(1, "20206782459", 3000, "2024-01-01",
                "Haflund", "1", "22334412345");


        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.registrerBetaling(any(Transaksjon.class))).thenReturn("OK");

        // Act
        String result = bankController.registrerBetaling(betaling);

        // Assert
        assertEquals("OK", result);
    }

    @Test
    public void registrerBetaling_IkkeLoggetInn() {
        // Arrange
        Transaksjon betaling = new Transaksjon();
        when(sjekk.loggetInn()).thenReturn(null);

        // Act
        String result = bankController.registrerBetaling(betaling);

        // Assert
        assertNull(result);
    }

    @Test
    public void registrerBetaling_feil(){
        Transaksjon betaling = new Transaksjon(1, "20206782459", 3000, "2024-01-01",
                "Haflund", "1", "22334412345");


        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.registrerBetaling(any(Transaksjon.class))).thenReturn(null);

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

        when(sjekk.loggetInn()).thenReturn("01010110523");

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
    public void hentKundeInfo_feil(){

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKundeInfo(anyString())).thenReturn(null);

        // act
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

        when(repository.hentKonti(any(String.class))).thenReturn(konti);

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
    public void hentKonti_feil(){
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKonti(any(String.class))).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNull(resultat);
    }


    @Test
    public void testHentBetalingerWhenPersonnummerNotNull() {
        // Arrange

        List<Transaksjon> transaksjoner = new ArrayList<>();

        Transaksjon betaling1 = new Transaksjon(1, "20167348913", 250,
                "2023-12-16", "Skatt", "1", "105010123456");
        Transaksjon betaling2 = new Transaksjon(2, "20226581465", 8200,
                "2023-12-25", "Husleie", "1", "105010123456");

        transaksjoner.add(betaling1);
        transaksjoner.add(betaling2);
        
        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentBetalinger(any(String.class))).thenReturn(transaksjoner);

        // Act
        List<Transaksjon> result = bankController.hentBetalinger();

        // Assert
        assertEquals(transaksjoner, result);
    }

    @Test
    public void testHentBetalingerWhenPersonnummerNull() {
        // Arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // Act
        List<Transaksjon> result = bankController.hentBetalinger();

        // Assert
        assertNull(result);
    }

    @Test
    public void testHentBetalinger_feil(){
        List<Transaksjon> transaksjoner = new ArrayList<>();

        Transaksjon betaling1 = new Transaksjon(1, "20167348913", 250,
                "2023-12-16", "Skatt", "1", "105010123456");
        Transaksjon betaling2 = new Transaksjon(2, "20226581465", 8200,
                "2023-12-25", "Husleie", "1", "105010123456");

        transaksjoner.add(betaling1);
        transaksjoner.add(betaling2);

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentBetalinger(any(String.class))).thenReturn(null);

        // Act
        List<Transaksjon> result = bankController.hentBetalinger();

        // Assert
        assertNull(result);
    }

     @Test
    public void testUtforBetalingWhenLoggedInAndRepositoryReturnsOK() {
        // Arrange
         List<Transaksjon> transaksjoner = new ArrayList<>();

         Transaksjon betaling1 = new Transaksjon(1, "20167348913", 250,
                 "2023-12-16", "Skatt", "1", "105010123456");
         Transaksjon betaling2 = new Transaksjon(2, "20226581465", 8200,
                 "2023-12-25", "Husleie", "1", "105010123456");

         transaksjoner.add(betaling1);
         transaksjoner.add(betaling2);
        
        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.utforBetaling(any(Integer.class))).thenReturn("OK");
        when(repository.hentBetalinger(any(String.class))).thenReturn(transaksjoner);

        // Act
        List<Transaksjon> resultat = bankController.utforBetaling(1);

        // Assert
        assertEquals(transaksjoner, resultat);
    }

    @Test
    public void testUtforBetalingIkkeLoggetInn() {
        // Arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // Act
        List<Transaksjon> result = bankController.utforBetaling(123);

        // Assert
        assertNull(result);
    }

    @Test
    public void testUtforBetalingWhenRepositoryReturnsNotOK() {
        // Arrange
        List<Transaksjon> transaksjoner = new ArrayList<>();

        Transaksjon betaling1 = new Transaksjon(1, "20167348913", 250,
                "2023-12-16", "Skatt", "1", "105010123456");
        Transaksjon betaling2 = new Transaksjon(2, "20226581465", 8200,
                "2023-12-25", "Husleie", "1", "105010123456");

        transaksjoner.add(betaling1);
        transaksjoner.add(betaling2);

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.utforBetaling(any(Integer.class))).thenReturn(null);
        when(repository.hentBetalinger(any(String.class))).thenReturn(null);
        // Act
        List<Transaksjon> resultat = bankController.utforBetaling(1);

        // Assert
        assertNull(resultat);
    }

    @Test
    public void testEndreKundeInfoLoggetInn() {
        // Arrange
        Kunde kunde1 = new Kunde("01010110523",
                "Lene Marie", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("OK");

        // Act
        String resultat = bankController.endre(kunde1);

        // Assert
        assertEquals("OK", resultat);
    }

    @Test
    public void testEndreKundeInfoWhenIkkeLoggetInn() {
        // Arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // Act
        String result = bankController.endre(new Kunde());

        // Assert
        assertNull(result);
    }

    @Test
    public void testEndreKundeInfo_Feil(){
        Kunde kunde1 = new Kunde("01010110523",
                "Lene Marie", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn(null);

        // Act
        String resultat = bankController.endre(kunde1);

        // Assert
        assertNull(resultat);
    }
}

