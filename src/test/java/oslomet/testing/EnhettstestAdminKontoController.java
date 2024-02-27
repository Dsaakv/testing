package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)



public class EnhettstestAdminKontoController {

    @InjectMocks
    //Skal testes
    private AdminKontoController adminKontoController;

    @Mock
    //Skal mockes
    private AdminRepository adminRepository;

    @Mock
    //skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void registrerKunde_ok(){

        Konto enKonto = new Konto("01010110523", "105010123456", 720.0, "Lønnskonto", "NOK", new ArrayList<>());

        when(sjekk.loggetInn()).thenReturn("Logget inn");

        when(adminRepository.registrerKunde(any(Kunde.class))).thenReturn("OK");

        String resultat = adminKontoController.registrerKonto(enKonto);

        assertEquals("OK", resultat);
    }

    @Test
    public void registrerKunde_feil(){

        Konto enKonto = new Konto("01010110523", "105010123456", 720.0, "Lønnskonto", "NOK", new ArrayList<>());

        when(sjekk.loggetInn()).thenReturn("Logget inn");

        when(adminRepository.registrerKunde(any(Kunde.class))).thenReturn(null);

        String resultat = adminKontoController.registrerKonto(enKonto);

        assertEquals(null, resultat);
    }

    @Test
    public void registrerKunde_ikkeloggetinn(){

        Konto enKonto = new Konto("01010110523", "105010123456", 720.0, "Lønnskonto", "NOK", new ArrayList<>());

        when(sjekk.loggetInn()).thenReturn(null);

        when(adminRepository.registrerKunde(any(Kunde.class))).thenReturn("OK");

        String resultat = adminKontoController.registrerKonto(enKonto);

        assertEquals("Ikke innlogget", resultat);
    }

    @Test
    public void hentalle_ok() {


        Transaksjon transaksjon1 = new Transaksjon(1, "123456789", 500.0, "2024-02-27", "Deposit", "No", "01010110523");
        Transaksjon transaksjon2 = new Transaksjon(2, "987654321", 200.0, "2024-02-28", "Withdrawal", "Yes", "02020220876");

        Konto konto1 = new Konto("01010110523", "123456789", 1000.0, "Savings", "NOK", List.of(transaksjon1));
        Konto konto2 = new Konto("02020220876", "987654321", 500.0, "Checking", "USD", List.of(transaksjon2));


        List<Konto> kontoliste = new ArrayList<>();
        kontoliste.add(konto1);
        kontoliste.add(konto2);

        when(sjekk.loggetInn()).thenReturn("Logget inn");

        when(adminRepository.hentAlleKonti()).thenReturn(kontoliste);

        List<Konto> resultat = adminKontoController.hentAlleKonti();

        assertEquals(kontoliste, resultat);
    }

    @Test
    public void hentalle_feil(){

        Transaksjon transaksjon1 = new Transaksjon(1, "123456789", 500.0, "2024-02-27", "Deposit", "No", "01010110523");
        Transaksjon transaksjon2 = new Transaksjon(2, "987654321", 200.0, "2024-02-28", "Withdrawal", "Yes", "02020220876");

        Konto konto1 = new Konto("01010110523", "123456789", 1000.0, "Savings", "NOK", List.of(transaksjon1));
        Konto konto2 = new Konto("02020220876", "987654321", 500.0, "Checking", "USD", List.of(transaksjon2));


        List<Konto> kontoliste = new ArrayList<>();
        kontoliste.add(konto1);
        kontoliste.add(konto2);

        when(sjekk.loggetInn()).thenReturn("Logget inn");

        when(adminRepository.hentAlleKonti()).thenReturn(null);

        List<Konto> resultat = adminKontoController.hentAlleKonti();

        assertEquals(null, resultat);
    }

    @Test
    public void hentalle_ikkeloggetinn() {


        Transaksjon transaksjon1 = new Transaksjon(1, "123456789", 500.0, "2024-02-27", "Deposit", "No", "01010110523");
        Transaksjon transaksjon2 = new Transaksjon(2, "987654321", 200.0, "2024-02-28", "Withdrawal", "Yes", "02020220876");

        Konto konto1 = new Konto("01010110523", "123456789", 1000.0, "Savings", "NOK", List.of(transaksjon1));
        Konto konto2 = new Konto("02020220876", "987654321", 500.0, "Checking", "USD", List.of(transaksjon2));


        List<Konto> kontoliste = new ArrayList<>();
        kontoliste.add(konto1);
        kontoliste.add(konto2);

        when(sjekk.loggetInn()).thenReturn(null);

        when(adminRepository.hentAlleKonti()).thenReturn(kontoliste);

        List<Konto> resultat = adminKontoController.hentAlleKonti();

        assertEquals(null, resultat);
    }

}
