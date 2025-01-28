package banque;

import banque.exceptions.SoldeInsuffisantException;

public class Compte {

    private final double numero;

    private final String nomTitulaire;

    private final String prenomTitulaire;

    private final String addrTitulaire;

    private double solde;

    private final double decouvertMax;

    public Compte(double numero, String nomTitulaire, String prenomTitulaire, String addrTitulaire, double decouvertMax, double solde) {
        this.numero = numero;
        this.nomTitulaire = nomTitulaire;
        this.prenomTitulaire = prenomTitulaire;
        this.addrTitulaire = addrTitulaire;
        this.decouvertMax = decouvertMax;
        this.solde = solde;
    }

    public Compte(double numero, String nomTitulaire, String prenomTitulaire, String addrTitulaire, double decouvertMax) {
        this(numero, nomTitulaire, prenomTitulaire, addrTitulaire, decouvertMax, 0);
    }

    // METHODES

    /**
     * Credite le compte du montant passé en paramètre
     * @param montant : La somme à créditer
     */
    public void crediter(double montant) {
        if(montant < 0) {
            throw new IllegalArgumentException("Montant négatif");
        }

        this.solde += montant;
    }

    /**
     * Débite le compte du montant passé en paramètre
     * @param montant : La somme à débiter
     */
    public void debiter(double montant) throws SoldeInsuffisantException{
        if(montant < 0) {
            throw new IllegalArgumentException("Montant négatif");
        }

        if (this.solde - montant >= -this.decouvertMax) {
            this.solde -= montant;
        } else {
            throw new SoldeInsuffisantException("Solde insuffisant");
        }
    }

    /**
     * Vire le montant passé en paramètre du compte courant vers le compte destination
     * @param montant : La somme à virer
     * @param destination : Le compte de destination
     */
    public void virerVers(double montant, Compte destination) {
        this.debiter(montant);
        destination.crediter(montant);
    }

    /**
     * Retourne vrai si le compte est à découvert
     * @return boolean
     */
    public boolean estDecouvert() {
        return this.solde < 0;
    }

    // GETTERS

    public double getNumero() {
        return numero;
    }

    public String getNomTitulaire() {
        return nomTitulaire;
    }

    public String getPrenomTitulaire() {
        return prenomTitulaire;
    }

    public String getAddrTitulaire() {
        return addrTitulaire;
    }

    public double getSolde() {
        return solde;
    }
}
