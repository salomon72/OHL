
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DisplayGameData {

    final List<GameFigure> figures;

    public DisplayGameData() {

        figures = Collections.synchronizedList(new ArrayList<GameFigure>());
    }
}
