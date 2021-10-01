package DOA;

import domain.OVChipkaart;
import domain.Product;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductDAO {
    public boolean save(Product product) throws SQLException;

    public boolean update(Product product) throws SQLException;

    public boolean delete(Product product) throws SQLException;

    public Product findByProductNummer(int productNummer) throws SQLException;


    public ArrayList<Product> findByChipkaart(OVChipkaart ovChipkaart) throws SQLException;

    public ArrayList<Product> findAll() throws SQLException;
}