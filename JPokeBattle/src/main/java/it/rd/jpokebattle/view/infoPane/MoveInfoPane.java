package it.rd.jpokebattle.view.infoPane;

import it.rd.jpokebattle.model.move.Move;
import it.rd.jpokebattle.model.move.MoveCategory;
import javafx.scene.control.Label;

public class MoveInfoPane extends InfoPane{
    private Move move;
    private final Label descriptionLbl = new Label();
    private final Label typeLbl = new Label();
    private final Label catLbl = new Label();
    private final Label powLbl = new Label();
    private final Label priorityLbl = new Label();
    private final Label precLbl = new Label();
    private final Label ppLbl = new Label();

    public MoveInfoPane(Move move) {
        this.move = move;
        setLabels();
    }

    private void setLabels() {
        descriptionLbl.setText(move.getDescription());
        typeLbl.setText("Tipo: " + move.getType().getFormattedName());
        catLbl.setText("Categoria: " + MoveCategory.getFormattedName(move.getCategory()));
        powLbl.setText("Potenza: " + move.getPower());
        priorityLbl.setText("Priorità: " + move.getPriority());
        precLbl.setText("Precisione: " + (int) move.getPrecision());
        ppLbl.setText("PP: " + move.getPP());
    }
}
