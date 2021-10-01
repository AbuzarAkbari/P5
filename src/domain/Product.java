package domain;

import java.util.ArrayList;
import java.util.List;

public class Product {

    private int productNummer;
    private String naam;
    private String beschrijving;
    private double prijs;

    private ArrayList<OVChipkaart> OvChipkaarten;

//    private ArrayList<Integer> ovChipkaarten = new ArrayList<Integer>();

    public int getProductNummer() {
        return productNummer;
    }

    public void setProductNummer(int productNummer) {
        this.productNummer = productNummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public ArrayList<OVChipkaart> getOvChipkaarten() { return OvChipkaarten; }

    public void setOvChipkaarten(List<OVChipkaart> OvChipkaarten) {
        this.OvChipkaarten = (ArrayList<OVChipkaart>) OvChipkaarten;
        for (OVChipkaart ovChipkaart : this.OvChipkaarten) {
            ArrayList<Product> producten = new ArrayList();
            producten.add(this);
            ovChipkaart.setProducten(producten);
        }
    }

    public Product(int productNummer, String naam, String beschrijving, double prijs) {
        this.productNummer = productNummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

}
