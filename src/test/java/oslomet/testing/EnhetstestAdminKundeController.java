package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKundeController {

    @InjectMocks
    //Skal testes
    private AdminKundeController adminKundeController;

    @Mock
    //Skal mockes
    private AdminRepository adminRepository;

    @Mock
    //skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void test_hentAlleOK() {


        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");
        Kunde toKunde = new Kunde("01010110524",
                "Jane", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        List<Kunde> kundeliste = new ArrayList<>();
        kundeliste.add(enKunde);
        kundeliste.add(toKunde);

        when(sjekk.loggetInn()).thenReturn("Logget inn");
        when(adminRepository.hentAlleKunder()).thenReturn(kundeliste);

        List<Kunde> resultat = adminKundeController.hentAlle();

        assertEquals(kundeliste, resultat);
    }

    @Test
    public void test_hentAlleIkke_logget_inn(){

        when(sjekk.loggetInn()).thenReturn(null);

        List<Kunde> resultat = adminKundeController.hentAlle();

        assertNull(resultat);
    }

    @Test
    public void test_hentAllefeil(){
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");
        Kunde toKunde = new Kunde("01010110524",
                "Jane", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        List<Kunde> kundeliste = new ArrayList<>();


        when(sjekk.loggetInn()).thenReturn("Logget inn");
        when(adminRepository.hentAlleKunder()).thenReturn(null);

        List<Kunde> resultat = adminKundeController.hentAlle();

        assertNull(resultat);
    }


    @Test
    public void test_LagreOK(){
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("Logget inn");
        when(adminRepository.registrerKunde((any(Kunde.class)))).thenReturn("OK");

        String resultat = adminKundeController.lagreKunde(enKunde);

        assertEquals("OK", resultat);
    }

    @Test
    public void test_registrert_ikke_loggetinn(){
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);


        String resultat = adminKundeController.lagreKunde(enKunde);

        assertEquals("Ikke logget inn",resultat);
    }

    @Test
    public void test_registrertFeil(){
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("Logget inn");
        when(adminRepository.registrerKunde((any(Kunde.class)))).thenReturn("Feil");

        String resultat = adminKundeController.lagreKunde((enKunde));

        assertEquals("Feil", resultat);

    }

    @Test
    public void test_endreOK(){
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");
        when(sjekk.loggetInn()).thenReturn("Logget inn");
        when(adminRepository.endreKundeInfo(any(Kunde.class))).thenReturn("OK");

        String resultat = adminKundeController.endre(enKunde);

        assertEquals("OK", resultat);
    }

    @Test
    public void test_endrefeil(){
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");
        when(sjekk.loggetInn()).thenReturn("Logget inn");
        when(adminRepository.endreKundeInfo(any(Kunde.class))).thenReturn(null);

        String resultat = adminKundeController.endre(enKunde);

        assertNull(resultat);

    }

    @Test
    public void test_endre_ikkeloggetinn(){
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);

        String resultat = adminKundeController.endre(enKunde);

        assertEquals("Ikke logget inn", resultat);
    }


}
