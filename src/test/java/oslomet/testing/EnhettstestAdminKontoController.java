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
    public void registrerKonto_ok(){

        Konto enKonto = new Konto("01010110523", "105010123456", 720.0, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("Admin");

        when(adminRepository.registrerKonto(any(Konto.class))).thenReturn("OK");

        String resultat = adminKontoController.registrerKonto(enKonto);

        assertEquals("OK", resultat);
    }

    @Test
    public void registrerKunde_feil(){

        Konto enKonto = new Konto("01010110523", "105010123456", 720.0, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("Admin");

        when(adminRepository.registrerKonto(any(Konto.class))).thenReturn(null);

        String resultat = adminKontoController.registrerKonto(enKonto);

        assertNull(resultat);
    }

    @Test
    public void registrerKunde_ikkeloggetinn(){

        Konto enKonto = new Konto("01010110523", "105010123456", 720.0, "Lønnskonto", "NOK", new ArrayList<>());

        when(sjekk.loggetInn()).thenReturn(null);


        String resultat = adminKontoController.registrerKonto(enKonto);

        assertEquals("Ikke innlogget", resultat);
    }

    @Test
    public void hentalle_ok() {


        List<Konto> kontoliste = new ArrayList<>();

        Konto konto1 = new Konto("12345678901", "10108976011", 800.0, "Brukskonto", "NOK", null);
        Konto konto2 = new Konto("12345678901", "10119085922", 4000.0, "Regningskonto", "NOK", null);
        Konto konto3 = new Konto("12345678901", "10110344455", 150000.0, "Sparekonto", "NOK", null);

        kontoliste.add(konto1);
        kontoliste.add(konto2);
        kontoliste.add(konto3);


        when(sjekk.loggetInn()).thenReturn("Admin");

        when(adminRepository.hentAlleKonti()).thenReturn(kontoliste);

        List<Konto> resultat = adminKontoController.hentAlleKonti();

        assertEquals(kontoliste, resultat);
    }

    @Test
    public void hentalle_feil(){

        when(sjekk.loggetInn()).thenReturn("Admin");

        when(adminRepository.hentAlleKonti()).thenReturn(null);

        List<Konto> resultat = adminKontoController.hentAlleKonti();

        assertNull(resultat);
    }

    @Test
    public void hentalle_ikkeloggetinn() {



        when(sjekk.loggetInn()).thenReturn(null);


        List<Konto> resultat = adminKontoController.hentAlleKonti();

        assertNull(resultat);
    }

    @Test
    public void endre_ok(){

        Konto konto2 = new Konto("12345678901", "10219085822", 13000.0, "sparekonto", "NOK", null);


        when(sjekk.loggetInn()).thenReturn("Admin");

        when(adminRepository.endreKonto(any(Konto.class))).thenReturn("OK");

        String resultat = adminKontoController.endreKonto(konto2);

        assertEquals("OK", resultat);
    }

    @Test
    public void endre_feil(){

        Konto konto2 = new Konto("12345678901", "10219085822", 13000.0, "sparekonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("Admin");


        String resultat = adminKontoController.endreKonto(konto2);

        assertNull(resultat);
    }

    @Test
    public void endre_logginnfeil(){

        Konto konto2 = new Konto("12345678901", "10219085822", 13000.0, "sparekonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn(null);

        String resultat = adminKontoController.registrerKonto(konto2);

        assertEquals("Ikke innlogget", resultat);
    }

    @Test
    public void slett_ok(){


        when(sjekk.loggetInn()).thenReturn("Admin");

        when(adminRepository.slettKonto(any(String.class))).thenReturn("OK");

        String resultat = adminKontoController.slettKonto("12345678901");

        assertEquals("OK", resultat);
    }

    @Test
    public void slett_feil(){

        when(sjekk.loggetInn()).thenReturn("Admin");

        when(adminRepository.slettKonto(any(String.class))).thenReturn(null);

        String resultat = adminKontoController.slettKonto("12345678901");

        assertNull(resultat);
    }

    @Test
    public void slett_ikkeloggetinn(){

        when(sjekk.loggetInn()).thenReturn(null);

        String resultat = adminKontoController.slettKonto("12345678901");

        assertEquals("Ikke innlogget", resultat);
    }
}
