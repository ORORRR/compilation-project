package fr.femtost.disc.glcomp.m1comp6.gui;

import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;

import java.util.function.IntFunction;

public class CheckBoxFactory implements IntFunction<Node> {
    private ObservableList<Integer> checkedLines;
    private ObservableValue<Boolean> checkBoxesDisabled;

    private CheckBoxFactory(ObservableList<Integer> checkedLines, ObservableValue<Boolean> checkBoxesDisabled) {
        this.checkedLines = checkedLines;
        this.checkBoxesDisabled = checkBoxesDisabled;
    }

    public static IntFunction<Node> get(ObservableList<Integer> checkedLines, ObservableValue<Boolean> checkBoxesDisabled) {
        return new CheckBoxFactory(checkedLines, checkBoxesDisabled);
    }

    @Override
    public Node apply(int line) {
        CheckBox checkBox = new CheckBox();
        Integer lineNumber = line + 1;

        checkBox.setOnAction(event -> {
            if (checkBox.isSelected()) {
                checkedLines.add(lineNumber);
            } else {
                checkedLines.remove(lineNumber);
            }
        });

        checkBox.setSelected(checkedLines.contains(lineNumber));
        checkBox.setDisable(checkBoxesDisabled.getValue());

        checkedLines.addListener((ListChangeListener<Integer>) change -> {
            checkBox.setSelected(change.getList().contains(lineNumber));
        });

        checkBoxesDisabled.addListener((observable, oldValue, newValue) -> {
            checkBox.setDisable(newValue);
        });

        return checkBox;
    }
}
