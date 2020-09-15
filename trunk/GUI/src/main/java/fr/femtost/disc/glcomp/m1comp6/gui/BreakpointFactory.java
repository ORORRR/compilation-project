package fr.femtost.disc.glcomp.m1comp6.gui;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import org.fxmisc.richtext.GenericStyledArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.util.function.IntFunction;

public class BreakpointFactory implements IntFunction<Node> {
    private IntFunction<Node> lineNumberFactory;
    private IntFunction<Node> checkBoxFactory;

    private BreakpointFactory(GenericStyledArea area, ObservableList<Integer> checkedLines, ObservableValue<Boolean> checkBoxesDisabled) {
        lineNumberFactory = LineNumberFactory.get(area);
        checkBoxFactory = CheckBoxFactory.get(checkedLines, checkBoxesDisabled);
    }

    public static IntFunction<Node> get(GenericStyledArea area, ObservableList<Integer> checkedLines, ObservableValue<Boolean> checkBoxesDisabled) {
        return new BreakpointFactory(area, checkedLines, checkBoxesDisabled);
    }

    @Override
    public Node apply(int line) {
        Node lineNumber = lineNumberFactory.apply(line);
        Node checkBox = checkBoxFactory.apply(line);
        Region spacer = new Region();

        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox hbox = new HBox(spacer, lineNumber, checkBox);

        hbox.getStyleClass().add("line-controls");
        hbox.setPrefWidth(60.0);

        return hbox;
    }
}
