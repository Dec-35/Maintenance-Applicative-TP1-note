package banque;

import banque.exceptions.SoldeInsuffisantException;

public class Compte {

    private final double numero;
    private final String nomTitulaire;
    private final String prenomTitulaire;
    private final String addrTitulaire;
    private double solde;
    private double decouvertMax;
    private double debitMax;

    /**
     * Constructeur d'un compte
     * @param numero Le numéro du compte
     * @param nomTitulaire Le nom du titulaire
     * @param prenomTitulaire Le prénom du titulaire
     * @param addrTitulaire L'adresse du titulaire
     * @param decouvertMax Le découvert maximal autorisé
     * @param solde Le solde du compte
     *
     * @throws IllegalArgumentException Si le solde est négatif ou si le découvert maximal est négatif
     */
    public Compte(double numero, String nomTitulaire, String prenomTitulaire, String addrTitulaire, double decouvertMax, double debitMax, double solde) throws IllegalArgumentException{
        if(solde < 0) {
            throw new IllegalArgumentException("Solde négatif");
        }

        if(decouvertMax < 0) {
            throw new IllegalArgumentException("Découvert maximal négatif");
        }

        if(debitMax < 0) {
            throw new IllegalArgumentException("Debit maximal négatif");
        }

        this.numero = numero;
        this.nomTitulaire = nomTitulaire;
        this.prenomTitulaire = prenomTitulaire;
        this.addrTitulaire = addrTitulaire;
        this.decouvertMax = decouvertMax;
        this.debitMax = debitMax;
        this.solde = solde;
    }

    /**
     * Constructeur d'un compte avec un solde initial de 0
     * @param numero Le numéro du compte
     * @param nomTitulaire Le nom du titulaire
     * @param prenomTitulaire Le prénom du titulaire
     * @param addrTitulaire L'adresse du titulaire
     * @param decouvertMax Le découvert maximal autorisé
     */
    public Compte(double numero, String nomTitulaire, String prenomTitulaire, String addrTitulaire, double decouvertMax, double debitMax) {
        this(numero, nomTitulaire, prenomTitulaire, addrTitulaire, decouvertMax, debitMax, 0);
    }

    // METHODES

    /**
     * Credite le compte du montant passé en paramètre
     * @param montant La somme à créditer
     * @throws IllegalArgumentException Si le montant à créditer est négatif
     */
    public void crediter(double montant) {
        if(montant < 0) {
            throw new IllegalArgumentException("Montant négatif");
        }

        this.solde += montant;
    }

    /**
     * Débite le compte du montant passé en paramètre
     * @param montant La somme à débiter
     * @throws SoldeInsuffisantException Si le solde est insuffisant
     * @throws IllegalArgumentException Si le montant à débiter est négatif
     */
    public void debiter(double montant) throws SoldeInsuffisantException{
        if(montant < 0) {
            throw new IllegalArgumentException("Montant négatif");
        }

        if(montant > this.debitMax) {
            throw new IllegalArgumentException("Montant superieur au debit maximal");
        }

        if (this.solde - montant >= -this.decouvertMax) {
            this.solde -= montant;
        } else {
            throw new SoldeInsuffisantException("Solde insuffisant");
        }
    }

    /**
     * Vire le montant passé en paramètre du compte courant vers le compte destination
     * @param montant La somme à virer
     * @param destination Le compte de destination
     */
    public void virerVers(double montant, Compte destination) {
        this.debiter(montant);
        destination.crediter(montant);
    }

    /**
     * Détermine si le compte est à découvert
     * @return Vrai si le compte est à découvert
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

    public double getDecouvertMax() {
        return decouvertMax;
    }

    public double getDebitMax() {
        return debitMax;
    }

    public double getDebitAutorise() {
        return Math.min(debitMax, solde);
    }

    // SETTERS

    public void setDecouvertMax(double decouvertMax) {
        this.decouvertMax = decouvertMax;
    }

    public void setDebitMax(double debitMax) {
        this.debitMax = debitMax;
    }


}
