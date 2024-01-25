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
    public void registrerKunde(){

        Konto enKonto = new Konto("01010110523", "105010123456", 720.0, "Lønnskonto", "NOK", new ArrayList<>());

        when(sjekk.loggetInn()).thenReturn("Logget inn");

        when(adminRepository.registrerKunde((any(Kunde.class)))).thenReturn("OK");

        String resultat = adminKontoController.registrerKonto(enKonto);

        assertEquals("OK", resultat);
    }
}
