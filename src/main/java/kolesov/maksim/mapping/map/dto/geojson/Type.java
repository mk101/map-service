package kolesov.maksim.mapping.map.dto.geojson;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Type {

    FEATURE_COLLECTION("FeatureCollection"),
    FEATURE("Feature"),
    POINT("Point"),
    MULTI_POINT("MultiPoint"),
    LINE_STRING("LineString"),
    MULTI_LINE_STRING("MultiLineString"),
    POLYGON("Polygon"),
    MULTI_POLYGON("MultiPolygon")
    ;

    private final String name;

    @JsonValue
    public String getName() {
        return name;
    }

}
