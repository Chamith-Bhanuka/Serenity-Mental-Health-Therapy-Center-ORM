package lk.ijse.mentalHealthTherapyCenter.view.tdm;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProgramSelectTM {
    private String programId;
    private String programName;
    private Double fee;
    private BooleanProperty checked;

    public ProgramSelectTM(String programId, String programName, Double fee) {
        this.programId = programId;
        this.programName = programName;
        this.fee = fee;
        this.checked = new SimpleBooleanProperty(false);
    }

    public boolean isChecked() {
        return checked.get();
    }

    public void setChecked(boolean checked) {
        this.checked.set(checked);
    }

    public BooleanProperty checkedProperty() {
        return checked;
    }
}
