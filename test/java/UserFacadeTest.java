import togaether.BL.Model.*;
import togaether.DB.AbstractFactory;
import togaether.DB.UserDAO;
import org.junit.jupiter.api.*;

import java.sql.*;

public class UserFacadeTest {
  @Test
  void isConnectionValid() {
    AbstractFactory fact = AbstractFactory.createInstance();
    UserDAO userDB = fact.getUserDAO();

    try {
      User u = userDB.findByEmail("dorian.752@live.fr");
      Assertions.assertNotEquals(u, null);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }
}