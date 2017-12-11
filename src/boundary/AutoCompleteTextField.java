package boundary;

import Entity.Node;
import MapNavigation.DirectoryController;
import MapNavigation.SearchEngine;
import boundary.sceneControllers.MainSceneController;
import com.jfoenix.controls.JFXTextField;
import com.sun.org.apache.xalan.internal.xsltc.dom.NodeSortRecord;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import sun.applet.Main;

import java.util.LinkedList;
import java.util.List;


// new AutoCompleteTextField().getEntries().addAll(Arrays.asList("AA", "AB", "AC","BCA"));

public class AutoCompleteTextField extends JFXTextField
{
    private SearchEngine searchEngine;
    private MainSceneController mainSceneController;
    /** The existing autocomplete entries. */
    private ObservableList<Node> results;
    /** The popup used to select an entry. */
    private ContextMenu entriesPopup;
    private boolean originFlag;

    /** Construct a new AutoCompleteTextField. */
    public AutoCompleteTextField(DirectoryController dc, boolean originFlag) {
        super();
        this.originFlag = originFlag;
        setHover(true);
        searchEngine = new SearchEngine(dc);
        entriesPopup = new ContextMenu();
        textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                if (getText().length() == 0)
                {
                    entriesPopup.hide();
                } else
                {
                    results = searchEngine.Search(getText());
                    if (results.size() > 0)
                    {
                        populatePopup(results);
                        if (!entriesPopup.isShowing())
                        {
                            entriesPopup.show(AutoCompleteTextField.this, Side.BOTTOM, 0, 0);
                        }
                    } else
                    {
                        entriesPopup.hide();
                    }
                }
            }
        });

        focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean aBoolean2) {
                entriesPopup.hide();
            }
        });

    }

    /**
     * Populate the entry set with the given search results.  Display is limited to 10 entries, for performance.
     * @param searchResult The set of matching strings.
     */
    private void populatePopup(List<Node> searchResult) {
        List<CustomMenuItem> menuItems = new LinkedList<>();
        // If you'd like more entries, modify this line.
        int maxEntries = 10;
        int count = Math.min(searchResult.size(), maxEntries);
        for (int i = 0; i < count; i++)
        {
            final Node result = searchResult.get(i);
            Label entryLabel = new Label(result.getShortName());
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            item.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent actionEvent) {
                    setText(result.getShortName());
                    if (originFlag) mainSceneController.setOrigin(result);
                    else mainSceneController.setDestination(result);
                    entriesPopup.hide();
                }
            });
            menuItems.add(item);
        }
        entriesPopup.getItems().clear();
        entriesPopup.getItems().addAll(menuItems);
    }
    public void hide() {
        entriesPopup.hide();
    }

    public void setMainSceneController(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
    }
}