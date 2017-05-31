
package annonsklient;

import javafx.beans.property.SimpleStringProperty;


public class AdInfo {

    private SimpleStringProperty infoName;
    private SimpleStringProperty infoValue;

    public SimpleStringProperty infoNameProperty() {
      if (infoName == null) {
        infoName = new SimpleStringProperty(this, "name");
      }
      return infoName;
    }

    public SimpleStringProperty infoValueProperty() {
      if (infoValue == null) {
        infoValue = new SimpleStringProperty(this, "value");
      }
      return infoValue;
    }

    public AdInfo(String infoName, String infoValue) {
      this.infoName = new SimpleStringProperty(infoName);
      this.infoValue = new SimpleStringProperty(infoValue);
    }

    public String getInfoName() {
      return infoName.get();
    }

    public void setInfoName(String infoName) {
      this.infoName.set(infoName);
    }

    public String getInfoValue() {
      return infoValue.get();
    }

    public void setInfoValue(String infoValue) {
      this.infoValue.set(infoValue);
    }
}
