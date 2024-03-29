package dotjava.admin.models;

import dotjava.admin.ItemEntry;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dotjava.admin.config.db_config; // Assuming this class handles database connection

public class ItemEntry_db {

    public static void addItem(String name, int price) throws SQLException {
        Connection conn = db_config.conn;

        String insertQuery = "INSERT INTO item_db (name, price) VALUES (?, ?)";

        try (PreparedStatement insertStatement = conn.prepareStatement(insertQuery)) {
            insertStatement.setString(1, name);
            insertStatement.setInt(2, price);
            insertStatement.executeUpdate();
            System.out.println("Item added successfully.");
        }
    }

    public static void updateItem(int id, String name, int price) throws SQLException {
        Connection conn = db_config.conn;

        String updateQuery = "UPDATE item_db SET name = ?, price = ? WHERE id = ?";

        try (PreparedStatement updateStatement = conn.prepareStatement(updateQuery)) {
            updateStatement.setString(1, name);
            updateStatement.setInt(2, price);
            updateStatement.setInt(3, id);

            int affectedRows = updateStatement.executeUpdate();

            if (affectedRows > 0) {
                // Assuming ItemEntry has a static method to get item by ID
                ItemEntry updatedItem = getItemById(id);
                if (updatedItem != null) {
                    updatedItem.setName(name);
                    updatedItem.setPrice(price);
                }
                System.out.println("Item updated successfully.");
            } else {
                System.out.println("No rows affected. Item update might have failed.");
            }
        }
    }


    public static void deleteItem(int id) throws SQLException {
        Connection conn = db_config.conn;

        String deleteQuery = "DELETE FROM item_db WHERE id = ?";

        try (PreparedStatement deleteStatement = conn.prepareStatement(deleteQuery)) {
            deleteStatement.setInt(1, id);
            deleteStatement.executeUpdate();
            System.out.println("Item deleted successfully.");
        }
    }

    public static List<ItemEntry> getAllItems() throws SQLException {
        List<ItemEntry> items = new ArrayList<>();
        Connection conn = db_config.conn;

        String selectAllQuery = "SELECT * FROM item_db";

        try (Statement statement = conn.createStatement()) {
            ResultSet rs = statement.executeQuery(selectAllQuery);

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int price = rs.getInt("price");

                // Create ItemEntry objects without a constructor (assuming setter methods exist)
                ItemEntry item = new ItemEntry();
                item.setId(id);
                item.setName(name);
                item.setPrice(price);

                items.add(item);
            }

            rs.close();
        }

        return items;
    }

    public static ItemEntry getItemById(int id) throws SQLException {
        Connection conn = db_config.conn;

        String selectByIdQuery = "SELECT * FROM item_db WHERE id = ?";

        try (PreparedStatement selectStatement = conn.prepareStatement(selectByIdQuery)) {
            selectStatement.setInt(1, id);
            ResultSet rs = selectStatement.executeQuery();

            if (rs.next()) {
                int retrievedId = rs.getInt("id");
                String name = rs.getString("name");
                int price = rs.getInt("price");

                // Create ItemEntry objects without a constructor (assuming setter methods exist)
                ItemEntry item = new ItemEntry();
                item.setId(retrievedId);
                item.setName(name);
                item.setPrice(price);

                return item;
            } else {
                return null;
            }
        }
    }
    public static Integer setUserActivity(String actType) throws SQLException {
        try{
            String query = "INSERT INTO `activity_log` ( `tipe_act`, `id_user`, `username`, `date`) VALUES (?, ?, ?, CURRENT_TIMESTAMP())";

            PreparedStatement statement = db_config.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, actType);
            statement.setInt(2, 555); // Assuming user ID is 555
            statement.setString(3, "admin");

            int affectedRows = statement.executeUpdate();
            int insertedIdActivity = 0;

            if(affectedRows > 0){
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {
                        System.out.println("Inserted activity log ID: " + rs.getInt(1));
                        insertedIdActivity =  rs.getInt(1);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return insertedIdActivity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}