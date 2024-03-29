package togaether.DB;

import togaether.BL.Model.Travel;

import java.sql.SQLException;
import java.util.List;

public interface TravelDAO {

    public int createTravel(Travel travel) throws SQLException;

    public void deleteTravelById(int Id) throws SQLException;

    public Travel findTravelById(int Id) throws SQLException;

    public List<Travel> findTravelsByUserId(int Id) throws SQLException;
    public List<Travel> findTravelsParticipationByUserId(int Id) throws SQLException;

    public List<Travel> findTravelsArchivedByUserId(int Id) throws SQLException;
    public List<Travel> findTravelsArchivedParticipationByUserId(int Id) throws SQLException;

    public List<Travel> findTravelsByUserIdAndString(int Id, String str) throws SQLException;

    public void updateTravel(Travel travel) throws SQLException;

    public void updateOwnerTravel(Travel travel) throws SQLException;

    public void archiveTravel(Travel travel) throws SQLException;

    public void unarchiveTravel(Travel travel) throws SQLException;

    public Travel findLatestCreatedTravel() throws SQLException;

}
