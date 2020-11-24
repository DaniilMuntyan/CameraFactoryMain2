package entities.camera;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class Dimensions implements Comparable<Dimensions> {
    private Integer width;
    private Integer length;
    private Integer depth;

    @Override
    public String toString() {
        return width + "x" + length + "x" + depth + " см³";
    }

    public Dimensions(kpi.trspo.restapp.Dimensions dimensions) {
        this.width = dimensions.getWidth();
        this.length = dimensions.getLength();
        this.depth = dimensions.getDepth();
    }

    @Override
    public int compareTo(Dimensions object) {
        return  (this.width <= object.width && this.length <= object.length && this.depth <= object.depth) ? 1 : -1;
    }
}
