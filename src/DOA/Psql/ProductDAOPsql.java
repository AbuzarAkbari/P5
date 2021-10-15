package DOA.Psql;

import DOA.OVChipkaartDAO;
import DOA.ProductDAO;
import DOA.ReizigerDAO;
import domain.OVChipkaart;
import domain.Product;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO {

    private Connection conn;
    private OVChipkaartDAO odao;
    private ReizigerDAO rdao;


    public ProductDAOPsql(Connection conn) {
        this.conn = conn;
    }


    @Override
    public boolean save(Product product) throws SQLException {

        try {
            PreparedStatement prepStatement = conn.prepareStatement("INSERT INTO product (product_nummer,naam,beschrijving,prijs) VALUES (?, ?, ?, ?)");

            prepStatement.setInt(1, product.getProductNummer());
            prepStatement.setString(2, product.getNaam());
            prepStatement.setString(3, product.getBeschrijving());
            prepStatement.setDouble(4, product.getPrijs());

            prepStatement.executeQuery();

            prepStatement.close();

            List<Integer> ovChipkaarts = product.getOvChipkaartNummers();
            for (Integer ovNummer : ovChipkaarts){
                if (ovNummer!=null){
                    String qu = "INSERT INTO ov_chipkaart_product VALUES (?,?,?,?)";
                    PreparedStatement ps = conn.prepareStatement(qu);

                    ps.setInt(1,ovNummer);
                    ps.setInt(2,product.getProductNummer());
                    ps.setString(3,"");
                    ps.setDate(4,Date.valueOf(LocalDate.now()));
                    ps.close();

                }
            }


            return true;

        } catch (SQLException e) {
            System.out.println("product is niet toegevoegd");
        } catch (Exception e) {
            System.out.println(e);
        }

        return false;

    }

    @Override
    public boolean update(Product product) throws SQLException {
        try {
            PreparedStatement prepStatement = conn.prepareStatement("UPDATE product SET  naam=?,beschrijving=?,prijs=? WHERE product_nummer = ?");

            prepStatement.setString(1, product.getNaam());
            prepStatement.setString(2, product.getBeschrijving());
            prepStatement.setDouble(3, product.getPrijs());
            prepStatement.setInt(4, product.getProductNummer());

            prepStatement.executeQuery();
            prepStatement.close();

            List<Integer>  ovChipkaarts = product.getOvChipkaartNummers();
            for (Integer ovNummer : ovChipkaarts){
                if (ovNummer!=null){
                    String qu = "UPDATE ov_chipkaart_product SET kaart_nummer=?, product_nummer=? WHERE product_nummer=?";
                    PreparedStatement ps = conn.prepareStatement(qu);

                    ps.setInt(1,ovNummer);
                    ps.setInt(2,product.getProductNummer());
                    ps.setInt(3,product.getProductNummer());
                    ps.close();

                }
            }

            return true;
        } catch (SQLException e) {
            System.out.println("update is niet uitgevoerd");
        } catch (Exception e) {
            System.out.println(e);
        }

        return false;
    }

    @Override
    public boolean delete(Product product) throws SQLException {
        try {


            List<Integer>  ovChipkaarts = product.getOvChipkaartNummers();
            for (Integer ovNummer : ovChipkaarts){
                if (ovNummer!=null){
                    String qu = "DELETE FROM ov_chipkaart_product WHERE kaart_nummer=? ";
                    PreparedStatement ps = conn.prepareStatement(qu);

                    ps.setInt(1,product.getProductNummer());
                    ps.close();

                }
            }

            PreparedStatement prepStatement = conn.prepareStatement("DELETE FROM product WHERE product_nummer = ?");

            prepStatement.setInt(1, product.getProductNummer());

            prepStatement.executeQuery();
            prepStatement.close();

            return true;
        } catch (SQLException e) {
            System.out.println("delete is niet uitgevoerd");
        } catch (Exception e) {
            System.out.println(e);
        }

        return false;
    }

    @Override
    public Product findByProductNummer(int productNummer) throws SQLException {

        try {
            PreparedStatement prepStatement = conn.prepareStatement("SELECT product.product_nummer, product.naam, product.beschrijving, product.prijs, ,ov_chipkaart_product.kaart_nummer FROM ov_chipkaart_product  JOIN product ON ov_chipkaart_product.product_nummer = product.product_nummer WHERE kaart_nummer = ?");
            prepStatement.setInt(1, productNummer);
            ResultSet result = prepStatement.executeQuery();

            Product product = null;
            ArrayList<Integer> ovChipkaartNummers = new ArrayList<>();

            result.next();

            while (result.next()){
                String naam = result.getString("product_nummer");
                String beschrijving = result.getString("naam");
                double prijs = result.getDouble("prijs");
                int kaartNummer = result.getInt("kaart_nummer");
                product = new Product(productNummer, naam, beschrijving,prijs);
                ovChipkaartNummers.add(kaartNummer);
                product.setOvChipkaartNummers(ovChipkaartNummers);
            }
            prepStatement.close();
            result.close();
            return product;

        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public ArrayList<Product> findByChipkaart(OVChipkaart ovChipkaart) throws SQLException {
        try {
            ArrayList<Integer> ovChipkaartNummers = new ArrayList<>();
            ArrayList<Product> producten = new ArrayList<>();

            PreparedStatement prepStatement = conn.prepareStatement("SELECT product.product_nummer,product.naam,product.beschrijving, product.prijs FROM product, ov_chipkaart_product WHERE product.product_nummer = ov_chipkaart_product.product_nummer AND ? = ov_chipkaart_product.kaart_nummer");
            prepStatement.setInt(1, ovChipkaart.getKaartNummer());

            ResultSet result = prepStatement.executeQuery();

            result.next();
            while (result.next()){
                int productNummer = result.getInt("product_nummer");
                String naam = result.getString("naam");
                String beschrijving = result.getString("beschrijving");
                double prijs = result.getDouble("prijs");
                Product product = new Product(productNummer, naam, beschrijving, prijs);
                producten.add(product);
                ovChipkaartNummers.add(ovChipkaart.getKaartNummer());
                product.setOvChipkaartNummers(ovChipkaartNummers);
            }
            prepStatement.close();
            result.close();
            return producten;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public ArrayList<Product> findAll() throws SQLException {

        try {

            ArrayList<Product> producten = new ArrayList<>();
            ArrayList<Integer> ovChipkaartNummers = new ArrayList<>();

            PreparedStatement prepStatement = conn.prepareStatement("SELECT product.product_nummer, product.naam, product.beschrijving, product.prijs, ov_chipkaart_product.kaart_nummer FROM product, ov_chipkaart_product WHERE product.product_nummer = ov_chipkaart_product.product_nummer ");

            ResultSet result = prepStatement.executeQuery();

            while (result.next()){
                int productNummer = result.getInt("product_nummer");
                String naam = result.getString("naam");
                String beschrijving = result.getString("beschrijving");
                double prijs = result.getDouble("prijs");
                int kaartNummer = result.getInt("kaart_nummer");
                Product product = new Product(productNummer, naam, beschrijving, prijs);
                producten.add(product);
                ovChipkaartNummers.add(kaartNummer);
                product.setOvChipkaartNummers(ovChipkaartNummers);
            }


            prepStatement.close();
            result.close();
            return producten;
        } catch(SQLException e) {
            System.out.println(e);
        }

        return null;

    }
}
