package domain;

import java.sql.Date;
import java.util.ArrayList;

public class OVChipkaart {
    private int kaartNummer;
    private Date geldigTot;
    private int klasse;
    private double saldo;
    private Reiziger reiziger;

    private ArrayList<Product> producten = new ArrayList<Product>();


    public OVChipkaart(int kaartNummer,Date geldigTot,int klasse,double saldo,Reiziger reiziger){
        this.kaartNummer = kaartNummer;
        this.geldigTot = geldigTot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
    }

    public void setKaartNummer(int kaartNummer) {
        this.kaartNummer = kaartNummer;
    }

    public int getKaartNummer() {
        return kaartNummer;
    }

    public void setGeldigTot(Date geldigTot) {
        this.geldigTot = geldigTot;
    }

    public Date getGeldigTot() {
        return geldigTot;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public int getKlasse() {
        return klasse;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public ArrayList<Product> getProducten() {
        return producten;
    }

    public void setProducten(ArrayList<Product> producten) {
        this.producten = producten;
    }

    public void addProduct(Product product) {
        if(!producten.contains(product)) {
            producten.add(product);
        }
    }
    public void removeProduct(Product product) {

        producten.remove(product);
    }



    @Override
    public String toString() {
        return "OVChipkaart{" +
                "kaartNummer=" + kaartNummer +
                ", geldigTot=" + geldigTot +
                ", klasse=" + klasse +
                ", saldo=" + saldo +
                '}';
    }

}
