package DOA.Psql;

import DOA.OVChipkaartDAO;
import DOA.ProductDAO;
import domain.OVChipkaart;
import domain.Product;

import java.sql.*;
import java.util.ArrayList;

public class ProductDAOPsql implements ProductDAO {

    private Connection conn;
    private OVChipkaartDAO odao;

    public ProductDAOPsql(Connection conn,OVChipkaartDAO odao) {
        this.conn = conn;
        this.odao = odao;
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
            PreparedStatement prepStatement = conn.prepareStatement("SELECT * FROM product WHERE product_nummer=?");
            prepStatement.setInt(1, productNummer);
            ResultSet result = prepStatement.executeQuery();

            Product product = null;
            while (result.next()) {

                String naam = result.getString("naam");
                String beschrijving = result.getString("beschrijving");
                double prijs = result.getDouble("prijs");


                product = new Product(productNummer, naam, beschrijving, prijs);

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
//            relatie naar product


            PreparedStatement prepStatement = conn.prepareStatement("SELECT product.product_nummer, naam, beschrijving, prijs FROM ov_chipkaart_product  JOIN product ON ov_chipkaart_product.product_nummer = product.product_nummer WHERE kaart_nummer = ?");
            prepStatement.setInt(1, ovChipkaart.getKaartNummer());

            ResultSet result = prepStatement.executeQuery();
            Product product = null;
            ArrayList<Product> producten = new ArrayList<>();

            while (result.next()) {

                int productNummer = result.getInt("product_nummer");
                String naam = result.getString("naam");
                String beschrijving = result.getString("beschrijving");
                double saldo = result.getDouble("prijs");

                product = new Product(productNummer, naam, beschrijving, saldo);
                producten.add(product);

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
            PreparedStatement prepStatement = conn.prepareStatement("SELECT * FROM product ");
            ResultSet result = prepStatement.executeQuery();
            ArrayList<Product> producten = new ArrayList<>();

            while(result.next()){

                int productNummer = result.getInt("product_nummer");
                String naam = result.getString("naam");
                String beschrijving = result.getString("beschrijving");
                double saldo = result.getDouble("prijs");

                producten.add(new Product(productNummer,naam,beschrijving,saldo));

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
