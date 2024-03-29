module Togaether {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.sql;
  requires spring.security.core;
    requires java.desktop;

    exports togaether;
  opens togaether to javafx.fxml;
  exports togaether.BL.Facade;
  opens togaether.BL.Facade to javafx.fxml;
  exports togaether.UI.Frame;
  opens togaether.UI.Frame to javafx.fxml;
  exports togaether.UI.Controller;
  opens togaether.UI.Controller to javafx.fxml;
  exports togaether.DB;
  exports togaether.BL.Model;
  exports togaether.BL.TogaetherException;
  opens togaether.BL.TogaetherException to javafx.fxml;
}