package domain;

import java.util.ArrayList;
import java.util.List;

public class Product {

    private int productNummer;
    private String naam;
    private String beschrijving;
    private double prijs;

//    private ArrayList<OVChipkaart> OvChipkaarten;

    private ArrayList<Integer> ovChipkaartNummers;

    public Product(int productNummer, String naam, String beschrijving, double prijs) {
        this.productNummer = productNummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
        ovChipkaartNummers = new ArrayList<>();
    }



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

    public ArrayList<Integer> getOvChipkaartNummers() {
        return ovChipkaartNummers;
    }

    public void setOvChipkaartNummers(ArrayList<Integer> ovChipkaarts) {
        this.ovChipkaartNummers = ovChipkaarts;
    }

    public boolean voegOvChipKaartToe(OVChipkaart ovChipkaart){
        boolean toegevoegd = false;
        for (Integer kaartNummer : ovChipkaartNummers){
            if(kaartNummer != ovChipkaart.getKaartNummer()){
                ovChipkaartNummers.add(kaartNummer);
                toegevoegd = true;
            }
        }
        return toegevoegd;
    }
    public boolean verwijderOvChipKaart(OVChipkaart ovChipkaart){
        boolean verwijderd = false;
        for (Integer kaartNummer : ovChipkaartNummers){
            if(kaartNummer == ovChipkaart.getKaartNummer()){
                ovChipkaartNummers.remove(kaartNummer);
                verwijderd = true;
            }
        }
        return verwijderd;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productNumme=" + productNummer +
                ", naam='" + naam + '\'' +
                ", beschrijving='" + beschrijving + '\'' +
                ", prijs=" + prijs +
                ", ovChipkaartNummers=" + ovChipkaartNummers +
                '}';
    }


}
