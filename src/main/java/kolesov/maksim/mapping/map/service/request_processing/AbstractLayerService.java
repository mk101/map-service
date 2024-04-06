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
                for (Object coordinate : element.getCoordinates()) {
                    checkRanges(BigDecimal.valueOf(((Double) coordinate)), horizontalMax, horizontalMin, verticalMax, verticalMin);
                }
            }
            case POLYGON -> {
                List<List<Double>> ring = (List<List<Double>>) element.getCoordinates().get(0);
                for (List<Double> coordinates : ring) {
                    for (Double coordinate : coordinates) {
                        checkRanges(BigDecimal.valueOf(coordinate), horizontalMax, horizontalMin, verticalMax, verticalMin);
                    }
                }
            }
            default -> {
                for (Object coordinates : element.getCoordinates()) {
                    List<Double> doubles = (List<Double>) coordinates;
                    for (Double coordinate : doubles) {
                        checkRanges(BigDecimal.valueOf(coordinate), horizontalMax, horizontalMin, verticalMax, verticalMin);
                    }
                }
            }
        }
    }

    private static void checkRanges(BigDecimal coordinate, AtomicReference<BigDecimal> horizontalMax, AtomicReference<BigDecimal> horizontalMin, AtomicReference<BigDecimal> verticalMax, AtomicReference<BigDecimal> verticalMin) {

        if (horizontalMax.get() == null || horizontalMax.get().compareTo(coordinate) < 0) {
            horizontalMax.set(coordinate);
        }

        if (horizontalMin.get() == null || horizontalMin.get().compareTo(coordinate) > 0) {
            horizontalMin.set(coordinate);
        }

        if (verticalMax.get() == null || verticalMax.get().compareTo(coordinate) < 0) {
            verticalMax.set(coordinate);
        }

        if (verticalMin.get() == null || verticalMin.get().compareTo(coordinate) > 0) {
            verticalMin.set(coordinate);
        }
    }

}
