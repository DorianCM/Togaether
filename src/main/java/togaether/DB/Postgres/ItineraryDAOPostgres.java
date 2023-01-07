package togaether.DB.Postgres;

import togaether.BL.Model.Itinerary;
import togaether.BL.Model.TransportCategory;
import togaether.DB.ItineraryDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItineraryDAOPostgres implements ItineraryDAO {

    PostgresFactory postgres;

    public ItineraryDAOPostgres(PostgresFactory postgres) {
        this.postgres = postgres;
    }

    @Override
    public int createItinerary(Itinerary itinerary) throws SQLException {
        try (Connection connection = this.postgres.getConnection()){
            String query = "INSERT INTO itinerary(travel,name,date,index,category, description) VALUES(?,?,?,?,?, ?) RETURNING travel_id;";
            try(PreparedStatement statement = connection.prepareStatement(query);){
                statement.setInt(1,itinerary.getTravel());
                statement.setString(2,itinerary.getName());
                statement.setDate(3, (Date) itinerary.getDateItinerary());
                statement.setInt(4,itinerary.getIndex());
                statement.setInt(5, itinerary.getCategory());
                statement.execute();
                ResultSet result = statement.getResultSet();
                result.next();
                return result.getInt(1);
            }
        }
        finally {
            this.postgres.getConnection().close();
        }
    }

    @Override
    public void deleteItineraryById(int Id) throws SQLException {

        try(Connection connection = this.postgres.getConnection()){
            String query = "DELETE FROM itinerary WHERE itinerary_id=?;";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(1,Id);
                statement.executeUpdate();
            }
        }
        finally{
            this.postgres.getConnection().close();
        }
    }

    @Override
    public Itinerary findItineraryById(int Id) throws SQLException {

        try(Connection connection = this.postgres.getConnection()){
            String query = "SELECT * FROM itinerary WHERE itinerary_id=?;";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(1,Id);
                try(ResultSet resultSet = statement.executeQuery()){
                    resultSet.next();

                    Itinerary itinerary = new Itinerary(resultSet.getInt("itinerary_id"),
                            resultSet.getInt("travel"),
                            resultSet.getString("name"),
                            resultSet.getDate("dateItinerary"),
                            resultSet.getInt("index"),
                            resultSet.getInt("category"),
                            resultSet.getString("description"));
                    return itinerary;
                }
            }
        }
        finally{
            this.postgres.getConnection().close();
        }
    }

    @Override
    public List<Itinerary> findItinerariesByTravelId(int Id) throws SQLException {
        try (Connection connection = this.postgres.getConnection()) {
            String query = "SELECT * FROM itinerary i INNER JOIN travel ON travel.travel_id=i.travel WHERE i.travel = ?; ";
            try (PreparedStatement statement = connection.prepareStatement(query);) {
                statement.setInt(1, Id);
                try(ResultSet resultSet = statement.executeQuery()){
                    ArrayList<Itinerary> itineraries = new ArrayList<>();
                    while(resultSet.next()){
                        Itinerary itinerary = new Itinerary(resultSet.getInt("itinerary_id"),
                                resultSet.getInt("travel"),
                                resultSet.getString("name"),
                                resultSet.getDate("dateItinerary"),
                                resultSet.getInt("index"),
                                resultSet.getInt("category"),
                                resultSet.getString("description"));

                        itineraries.add(itinerary);
                    }
                    return itineraries;
                }
            }
        }
        finally {
            this.postgres.getConnection().close();
        }
    }

    @Override
    public void updateItinerary(Itinerary itinerary) throws SQLException {

        try(Connection connection = this.postgres.getConnection()){
            String query = "UPDATE itinerary SET  name = ? , dateItinerary = ? , index = ?, description = ? WHERE itinerary_id = ? ;";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(5,itinerary.getItinerary_id());
                statement.setString(1,itinerary.getName());
                statement.setDate(2, (Date) itinerary.getDateItinerary());
                statement.setInt(3,  itinerary.getIndex());
                statement.setString(4,itinerary.getDescription());
                statement.executeUpdate();
            }
        }
        finally{
            this.postgres.getConnection().close();
        }
    }

    @Override
    public void deleteItinerariesByTravelId(int Id) throws SQLException {

        try(Connection connection = this.postgres.getConnection()){
            String query = "DELETE FROM itinerary WHERE travel=?;";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(1,Id);
                statement.executeUpdate();
            }
        }
        finally{
            this.postgres.getConnection().close();
        }
    }

    @Override
    public String findNameCatTransportById(int Id) throws SQLException {
        try (Connection connection = this.postgres.getConnection()) {
            String query = "SELECT nameTransportCat FROM transport_category cat WHERE cat_transport_id = ?; ";
            try (PreparedStatement statement = connection.prepareStatement(query);) {
                statement.setInt(1, Id);
                try(ResultSet resultSet = statement.executeQuery()){
                    return resultSet.getString("nameTransportCat");
                }
            }
        }
        finally {
            this.postgres.getConnection().close();
        }
    }

    @Override
    public int findIdCatTransportByName(String name) throws SQLException {
        try (Connection connection = this.postgres.getConnection()) {
            String query = "SELECT cat_transport_id FROM transport_category cat WHERE nameTransportCat = ?; ";
            try (PreparedStatement statement = connection.prepareStatement(query);) {
                statement.setString(1, name);
                try(ResultSet resultSet = statement.executeQuery()){
                    return resultSet.getInt("cat_transport_id");
                }
            }
        }
        finally {
            this.postgres.getConnection().close();
        }
    }

    @Override
    public List<TransportCategory> findAllCatTransport() throws SQLException {
        try (Connection connection = this.postgres.getConnection()) {
            String query = "SELECT * FROM transport_category ; ";
            try (PreparedStatement statement = connection.prepareStatement(query);) {
                try(ResultSet resultSet = statement.executeQuery()){
                    ArrayList<TransportCategory> categories = new ArrayList<>();
                    while(resultSet.next()){
                        TransportCategory transportCategory = new TransportCategory(resultSet.getInt("cat_transport_id"),
                                resultSet.getString("nameTransportCat"));
                        categories.add(transportCategory);
                    }
                    return categories;
                }
            }
        }
        finally {
            this.postgres.getConnection().close();
        }
    }

}
