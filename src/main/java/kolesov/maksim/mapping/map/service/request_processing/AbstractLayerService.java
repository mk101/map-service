package kolesov.maksim.mapping.map.service.request_processing;

import io.hypersistence.utils.hibernate.type.range.Range;
import kolesov.maksim.mapping.map.dto.geojson.GeoJson;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public abstract class AbstractLayerService {

    /**
     * @return first -> horizontal; second -> vertical
     */
    protected List<Range<BigDecimal>> evaluateRanges(GeoJson json) {
        List<GeoJson.Element> elements = json.getElements();

        AtomicReference<BigDecimal> horizontalMin = new AtomicReference<>();
        AtomicReference<BigDecimal> horizontalMax= new AtomicReference<>();
        AtomicReference<BigDecimal> verticalMin = new AtomicReference<>();
        AtomicReference<BigDecimal> verticalMax = new AtomicReference<>();
        elements.forEach(e -> handleElement(e, horizontalMin, horizontalMax, verticalMin, verticalMax));

        return Arrays.asList(
                Range.closed(horizontalMin.get(), horizontalMax.get()),
                Range.closed(verticalMin.get(), verticalMax.get())
        );
    }

    @SuppressWarnings("unchecked")
    private void handleElement(
            GeoJson.Element element,
            AtomicReference<BigDecimal> horizontalMin,
            AtomicReference<BigDecimal> horizontalMax,
            AtomicReference<BigDecimal> verticalMin,
            AtomicReference<BigDecimal> verticalMax
    ) {
        switch (element.getType()) {
            case FEATURE_COLLECTION -> throw new IllegalArgumentException("FeatureCollection is illegal in this context");
            case FEATURE -> handleElement(element.getGeometry(), horizontalMin, horizontalMax, verticalMin, verticalMax);
            case POINT -> {
                BigDecimal x = BigDecimal.valueOf((Double) element.getCoordinates().get(0));
                BigDecimal y = BigDecimal.valueOf((Double) element.getCoordinates().get(1));

                horizontalMax.set(x);
                horizontalMin.set(x);
                verticalMax.set(y);
                verticalMin.set(y);
            }
            case POLYGON -> {
                List<List<Double>> ring = (List<List<Double>>) element.getCoordinates().get(0);
                for (List<Double> coordinates : ring) {
                    checkRangesHorizontal(BigDecimal.valueOf(coordinates.get(0)), horizontalMax, horizontalMin);
                    checkRangesVertical(BigDecimal.valueOf(coordinates.get(1)), verticalMax, verticalMin);
                }
            }
            default -> {
                for (Object coordinates : element.getCoordinates()) {
                    List<Double> doubles = (List<Double>) coordinates;
                    checkRangesHorizontal(BigDecimal.valueOf(doubles.get(0)), horizontalMax, horizontalMin);
                    checkRangesVertical(BigDecimal.valueOf(doubles.get(1)), verticalMax, verticalMin);
                }
            }
        }
    }

    private static void checkRangesHorizontal(BigDecimal coordinate, AtomicReference<BigDecimal> horizontalMax, AtomicReference<BigDecimal> horizontalMin) {

        if (horizontalMax.get() == null || horizontalMax.get().compareTo(coordinate) < 0) {
            horizontalMax.set(coordinate);
        }

        if (horizontalMin.get() == null || horizontalMin.get().compareTo(coordinate) > 0) {
            horizontalMin.set(coordinate);
        }
    }

    private static void checkRangesVertical(BigDecimal coordinate, AtomicReference<BigDecimal> verticalMax, AtomicReference<BigDecimal> verticalMin) {

        if (verticalMax.get() == null || verticalMax.get().compareTo(coordinate) < 0) {
            verticalMax.set(coordinate);
        }

        if (verticalMin.get() == null || verticalMin.get().compareTo(coordinate) > 0) {
            verticalMin.set(coordinate);
        }
    }

}
