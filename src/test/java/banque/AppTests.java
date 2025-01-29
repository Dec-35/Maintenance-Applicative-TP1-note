package banque;

import banque.exceptions.SoldeInsuffisantException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests de comptes")
public class AppTests {
    private Compte compte1;

    @BeforeEach
    public void init() {
        compte1 = new Compte(1, "Doe", "John", "1 rue de Paris", 1000, 2300, 1000);
    }

    @DisplayName("Création de compte à solde zero")
    @Test
    public void creationCompteSoldeZero() {
        Compte compte = new Compte(1, "Doe", "John", "1 rue de Paris", 1000, 2000);
        assertEquals(0, compte.getSolde());
    }

    @DisplayName("Création de compte à solde négatif")
    @Test
    public void creationCompteSoldeNegatif() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Compte(1, "Doe", "John", "1 rue de Paris", 1000, 2000, -100)
        );
        assertEquals("Solde négatif", exception.getMessage());
    }

    @DisplayName("Création de compte avec découvert maximal négatif")
    @Test
    public void creationCompteDecouvertMaxNegatif() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Compte(1, "Doe", "John", "1 rue de Paris", -1000, 2000, 100)
        );
        assertEquals("Découvert maximal négatif", exception.getMessage());
    }

    @DisplayName("Création de compte avec débit maximal négatif")
    @Test
    public void creationCompteDebitMaxNegatif() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Compte(1, "Doe", "John", "1 rue de Paris", 1000, -2000, 100)
        );
        assertEquals("Debit maximal négatif", exception.getMessage());
    }

    @DisplayName("Créditer un compte")
    @Test
    public void crediterCompte() {
        compte1.crediter(100);
        assertEquals(1100, compte1.getSolde());
    }

    @DisplayName("Crediter un compte avec montant négatif")
    @Test
    public void crediterCompteMontantNegatif() {
        assertThrows(IllegalArgumentException.class, () -> compte1.crediter(-100));
    }

    @DisplayName("Débiter un compte")
    @Test
    public void debiterCompte() {
        compte1.debiter(100);
        assertEquals(900, compte1.getSolde());
    }

    @DisplayName("Débiter un compte avec montant négatif")
    @Test
    public void debiterCompteMontantNegatif() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> compte1.debiter(-100));
        assertEquals("Montant négatif", exception.getMessage());
    }

    @DisplayName("Débiter un compte avec solde insuffisant")
    @Test
    public void debiterCompteSoldeInsuffisant() {
        assertThrows(SoldeInsuffisantException.class, () -> compte1.debiter(2100));
    }

    @DisplayName("Débiter un compte avec montant supérieur au débit maximal")
    @Test
    public void debiterCompteMontantSuperieurDebitMax() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> compte1.debiter(2500));
        assertEquals("Montant superieur au debit maximal", exception.getMessage());
    }

    @DisplayName("Virement entre deux comptes")
    @Test
    public void virementCompte() {
        Compte compte2 = new Compte(2, "Doe", "Jane", "1 rue de Paris", 1000, 200, 1000);
        compte1.virerVers(100, compte2);
        assertEquals(900, compte1.getSolde());
        assertEquals(1100, compte2.getSolde());
    }

    @DisplayName("Virement entre deux comptes avec solde insuffisant")
    @Test
    public void virementCompteSoldeInsuffisant() {
        Compte compte2 = new Compte(2, "Doe", "Jane", "1 rue de Paris", 1000, 2500, 1000);
        assertThrows(SoldeInsuffisantException.class, () -> compte1.virerVers(2100, compte2));
    }

    @DisplayName("Compte à découvert")
    @Test
    public void testCompteDecouvert(){
        Compte compte = new Compte(1, "Doe", "John", "1 rue de Paris", 1000, 2000, 1000);
        assertFalse(compte.estDecouvert());
        compte.debiter(1100);
        assertTrue(compte.estDecouvert());
    }

    @DisplayName("Infos du compte")
    @Nested
    class TestsInfosCompte {
        @DisplayName("Numéro du compte")
        @Test
        public void testNumeroCompte() {
            assertEquals(1, compte1.getNumero());
        }

        @DisplayName("Nom du titulaire")
        @Test
        public void testNomTitulaire() {
            assertEquals("Doe", compte1.getNomTitulaire());
        }

        @DisplayName("Prénom du titulaire")
        @Test
        public void testPrenomTitulaire() {
            assertEquals("John", compte1.getPrenomTitulaire());
        }

        @DisplayName("Adresse du titulaire")
        @Test
        public void testAdresseTitulaire() {
            assertEquals("1 rue de Paris", compte1.getAddrTitulaire());
        }

        @DisplayName("Solde du compte")
        @Test
        public void testSoldeCompte() {
            assertEquals(1000, compte1.getSolde());
        }

        @DisplayName("Découvert maximal du compte")
        @Test
        public void testDecouvertMaxCompte() {
            assertEquals(1000, compte1.getDecouvertMax());
        }

        @DisplayName("Débit maximal du compte")
        @Test
        public void testDebitMaxCompte() {
            assertEquals(2300, compte1.getDebitMax());
        }

        @DisplayName("Débit autorisé")
        @Test
        public void testDebitAutorise() {
            assertEquals(1000, compte1.getDebitAutorise());
            compte1.debiter(1000);
            assertEquals(0, compte1.getDebitAutorise());
            compte1.crediter(3000);
            assertEquals(2300, compte1.getDebitAutorise());
        }

        @DisplayName("Modification du débit maximal")
        @Test
        public void testModifierDebitAutorise() {
            compte1.setDebitMax(2000);
            assertEquals(2000, compte1.getDebitMax());
        }

        @DisplayName("Modification du découvert maximal")
        @Test
        public void testModifierDecouvertMax() {
            compte1.setDecouvertMax(2000);
            assertEquals(2000, compte1.getDecouvertMax());
        }
    }
}
