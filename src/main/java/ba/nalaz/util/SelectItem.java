package ba.nalaz.util;

import java.io.Serializable;

public class SelectItem implements Serializable {
    Long id;
    String label;

    public SelectItem(Long id, String label) {
        this.id = id;
        this.label = label;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}

