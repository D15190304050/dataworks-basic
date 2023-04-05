package stark.dataworks.geographical.trajectories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import stark.dataworks.data.json.JsonParser;
import stark.dataworks.mathematics.geometry.BoundingBox;
import stark.dataworks.mathematics.geometry.IShape2D;

/**
 *
 */
public class RegionOfInterest
{
    private static final String NAME = "Name";
    private static final String BOUNDING_BOX = "BoundingBox";
    private static final String MIN_X = "MinX";
    private static final String MAX_X = "MaxX";
    private static final String MIN_Y = "MinY";
    private static final String MAX_Y = "MaxY";

    private IShape2D region;
    private String name;

    private RegionOfInterest()
    {
        region = null;
        name = null;
    }


    public RegionOfInterest(IShape2D region, String name)
    {
        validateRegion(region);
        this.region = region;
        this.name = name;
    }

    /**
     * @param roiString A JSON string with following format.
     *                  <p>
     *                  <code>{</code><p>
     *                  <code>&nbsp;&nbsp;&nbsp;&nbsp;"Name": [Name],</code><p>
     *                  <code>&nbsp;&nbsp;&nbsp;&nbsp;"BoundingBox": {</code><p>
     *                  <code>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"MinX": [MinX],</code><p>
     *                  <code>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"MaxX": [MaxX],</code><p>
     *                  <code>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"MinY": [MinY],</code><p>
     *                  <code>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"MaxY": [MaxY]</code><p>
     *                  <code>&nbsp;&nbsp;&nbsp;&nbsp;}</code><p>
     *                  <code>}</code><p>
     *                  [Name] represents the name of the ROI with type string.<p>
     *                  [MinX], [MaxX], [MinY], [MaxY] represents coordinates of the ROI with type double.
     */
    public RegionOfInterest parse(String roiString) throws JsonProcessingException
    {
        RegionOfInterest roi = new RegionOfInterest();

        JsonNode roiJson = JsonParser.parseJson(roiString);

        roi.name = roiJson.findValue(NAME).textValue();
        JsonNode boundingBoxCoordinates = roiJson.findValue(BOUNDING_BOX);
        double minX = boundingBoxCoordinates.findValue(MIN_X).asDouble();
        double minY = boundingBoxCoordinates.findValue(MAX_X).asDouble();
        double maxX = boundingBoxCoordinates.findValue(MIN_Y).asDouble();
        double maxY = boundingBoxCoordinates.findValue(MAX_Y).asDouble();
        roi.region = new BoundingBox(minX, minY, maxX, maxY);

        return roi;
    }

    public IShape2D getRegion()
    {
        return region;
    }

    public void setRegion(IShape2D region)
    {
        validateRegion(region);
        this.region = region;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "Name: " + name + ", bounding box: " + region;
    }

    private static void validateRegion(IShape2D region)
    {
        if (region == null)
            throw new NullPointerException("Argument \"region\" cannot be null.");
    }
}
