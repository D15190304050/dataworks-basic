package stark.dataworks.basic.mathematics.geometry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import stark.dataworks.basic.data.json.JsonParser;
import stark.dataworks.basic.data.json.JsonSerializer;

// TODO: Refactor this class to simplify deserialization, i.e. make JsonSerializer.deserialize() possible here.
// To achieve this goal, we need to use camel, instead of Pascal naming convention for keys here.

/**
 * The {@link ShapeParser} class provides static methods to parse and return an instance of {@link IShape2D} from a {@link JsonNode} or a JSON string.
 */
public class ShapeParser
{
    // String literals of the keys.
    private static final String A = "A";
    private static final String B = "B";
    private static final String C = "C";
    private static final String X = "X";
    private static final String Y = "Y";
    private static final String CENTER_X = "CenterX";
    private static final String CENTER_Y = "CenterY";
    private static final String RADIUS = "Radius";
    private static final String END_POINT_1 = "EndPoint1";
    private static final String END_POINT_2 = "EndPoint2";
    private static final String POINTS = "Points";
    private static final String MAX_X = "MaxX";
    private static final String MIN_X = "MinX";
    private static final String MAX_Y = "MaxY";
    private static final String MIN_Y = "MinY";


    private ShapeParser()
    {
    }

    /**
     * Parses a straight line from the given {@link JsonNode} and returns a {@link Line} that can represent the straight line.
     *
     * @param lineJson A {@link JsonNode} with following data format.<p>
     *                 <code>{</code><p>
     *                 <code>"A": [A],</code><p>
     *                 <code>"B": [B],</code><p>
     *                 <code>"C": [C]</code><p>
     *                 <code>}</code><p>
     *                 where<p>
     *                 [A], [B], [C] represent the parameters for an equation of a straight line, with type {@code double}. (i.e. Ax + By + C = 0).
     * @return An instance of the {@link Line} class with parameters specified by the given {@link JsonNode}.
     * @throws NullPointerException     If argument "lineJson" is null.
     * @throws IllegalArgumentException If at least one of the keys "A", "B", "C" is not contained in "lineJson".
     * @throws NumberFormatException    If there is a value associated with a required key, while the value does not contain a parsable {@code double}. (i.e. the value associated with key "A" is "true").
     */


    public static Line parseLine(JsonNode lineJson)
    {
        if (lineJson == null)
            throw new NullPointerException("Argument \"lineJson\" cannot be null.");

        JsonNode aNode = lineJson.findValue(A);
        JsonNode bNode = lineJson.findValue(B);
        JsonNode cNode = lineJson.findValue(C);

        if (aNode == null)
            throw new IllegalArgumentException("Bad JSON, argument \"lineJson\" does not contain key \"" + A + "\".");
        if (bNode == null)
            throw new IllegalArgumentException("Bad JSON, argument \"lineJson\" does not contain key \"" + B + "\".");
        if (cNode == null)
            throw new IllegalArgumentException("Bad JSON, argument \"lineJson\" does not contain key \"" + C + "\".");

        double a = aNode.asDouble();
        double b = bNode.asDouble();
        double c = cNode.asDouble();
        return new Line(a, b, c);
    }

    /**
     * Parses a straight line from the given JSON string and returns a {@link Line} that can represent the straight line.
     *
     * @param lineJson A JSON string with following data format.<p>
     *                 <code>{</code><p>
     *                 <code>"A": [A],</code><p>
     *                 <code>"B": [B],</code><p>
     *                 <code>"C": [C]</code><p>
     *                 <code>}</code><p>
     *                 where<p>
     *                 [A], [B], [C] represent the parameters for an equation of a straight line, with type {@code double}. (i.e. Ax + By + C = 0).
     * @return An instance of the {@link Line} class with parameters specified by the given JSON string.
     * @throws NullPointerException     If argument "lineJson" is null.
     * @throws IllegalArgumentException If at least one of the keys "A", "B", "C" is not contained in "lineJson"; or if "lineJson" is not a valid JSON string.
     * @throws NumberFormatException    If there is a value associated with a required key, while the value does not contain a parsable {@code double}. (i.e. the value associated with key "A" is "true").
     * @implNote This method calls {@link ShapeParser#parseLine(JsonNode)}.
     */
    public static Line parseLine(String lineJson) throws JsonProcessingException
    {
        if (lineJson == null)
            throw new NullPointerException("Argument \"lineJson\" cannot be null.");

        if (!JsonParser.isValid(lineJson))
            throw new IllegalArgumentException("Argument \"lineJson\" must be a valid JSON string.");

        return parseLine(JsonParser.parseJson(lineJson));
    }

    /**
     * Parses a circle from the given {@link JsonNode} and returns a {@link Circle} that can represent the circle.
     *
     * @param circleJson A {@link JsonNode} with following data format.<p>
     *                   <code>{</code><p>
     *                   <code>"CenterX": [CenterX],</code><p>
     *                   <code>"CenterY": [CenterY],</code><p>
     *                   <code>"Radius": [Radius]</code><p>
     *                   <code>}</code><p>
     *                   where<p>
     *                   [CenterX] represents the X-coordinate of the center of the circle, with type {@code double};<p>
     *                   [CenterY] represents the Y-coordinate of the center of the circle, with type {@code double};<p>
     *                   [Radius] represents the radius of the circle, with type {@code double}.
     * @return An instance of the {@link Circle} class with parameters specified by the given {@link JsonNode}.
     * @throws NullPointerException     If argument "circleJson" is null.
     * @throws IllegalArgumentException If at least one of the keys "CenterX", "CenterY", "Radius" is not contained in "circleJson".
     * @throws NumberFormatException    If there is a value associated with a required key, while the value does not contain a parsable {@code double}. (i.e. the value associated with key "CenterX" is "true").
     */


    public static Circle parseCircle(JsonNode circleJson)
    {
        if (circleJson == null)
            throw new NullPointerException("Argument \"circleJson\" cannot be null.");

        JsonNode centerXJson = circleJson.findValue(CENTER_X);
        JsonNode centerYJson = circleJson.findValue(CENTER_Y);
        JsonNode radiusJson = circleJson.findValue(RADIUS);

        if (centerXJson == null)
            throw new IllegalArgumentException("Bad JSON, argument \"circleJson\" does not contain key \"" + CENTER_X + "\".");
        if (centerYJson == null)
            throw new IllegalArgumentException("Bad JSON, argument \"circleJson\" does not contain key \"" + CENTER_Y + "\".");
        if (radiusJson == null)
            throw new IllegalArgumentException("Bad JSON, argument \"circleJson\" does not contain key \"" + RADIUS + "\".");

        double centerX = centerXJson.asDouble();
        double centerY = centerYJson.asDouble();
        double radius = radiusJson.asDouble();
        return new Circle(centerX, centerY, radius);
    }

    /**
     * Parses a circle from the given JSON string and returns a {@link Circle} that can represent the circle.
     *
     * @param circleJson A JSON string with following data format.<p>
     *                   <code>{</code><p>
     *                   <code>"CenterX": [CenterX],</code><p>
     *                   <code>"CenterY": [CenterY],</code><p>
     *                   <code>"Radius": [Radius]</code><p>
     *                   <code>}</code><p>
     *                   where<p>
     *                   [CenterX] represents the X-coordinate of the center of the circle, with type {@code double};<p>
     *                   [CenterY] represents the Y-coordinate of the center of the circle, with type {@code double};<p>
     *                   [Radius] represents the radius of the circle, with type {@code double}.
     * @return An instance of the {@link Circle} class with parameters specified by the given JSON string.
     * @throws NullPointerException     If argument "circleJson" is null.
     * @throws IllegalArgumentException If at least one of the keys "CenterX", "CenterY", "Radius" is not contained in "circleJson"; or if "circleJson" is not a valid JSON string.
     * @throws NumberFormatException    If there is a value associated with a required key, while the value does not contain a parsable {@code double}. (i.e. the value associated with key "CenterX" is "true").
     * @implNote This method calls {@link ShapeParser#parseCircle(JsonNode)}.
     */


    public static Circle parseCircle(String circleJson) throws JsonProcessingException
    {
        if (circleJson == null)
            throw new NullPointerException("Argument \"circleJson\" cannot be null.");

        if (!JsonParser.isValid(circleJson))
            throw new IllegalArgumentException("Argument \"circleJson\" must be a valid JSON string.");

        return parseCircle(JsonParser.parseJson(circleJson));
    }

    /**
     * Parses a point from the given {@link JsonNode} and returns a {@link Point} that can represent the point.
     *
     * @param pointJson A {@link JsonNode} with following data format.<p>
     *                  <code>{</code><p>
     *                  <code>"X": [X],</code><p>
     *                  <code>"Y": [Y],</code><p>
     *                  <code>}</code><p>
     *                  where<p>
     *                  [X] represents the X-coordinate of the point, with type {@code double};<p>
     *                  [Y] represents the Y-coordinate of the point, with type {@code double}.
     * @return An instance of the {@link Line} class with parameters specified by the given {@link JsonNode}.
     * @throws NullPointerException     If argument "pointJson" is null.
     * @throws IllegalArgumentException If at least one of the keys "X", "Y" is not contained in "pointJson".
     * @throws NumberFormatException    If there is a value associated with a required key, while the value does not contain a parsable {@code double}. (i.e. the value associated with key "X" is "true").
     */
    public static Point parsePoint(JsonNode pointJson)
    {
        return JsonSerializer.deserialize(pointJson, Point.class);
    }

    /**
     * Parses a point from the given JSON string and returns a {@link Point} that can represent the point.
     *
     * @param pointJson A JSON string with following data format.<p>
     *                  <code>{</code><p>
     *                  <code>"X": [X],</code><p>
     *                  <code>"Y": [Y],</code><p>
     *                  <code>}</code><p>
     *                  where<p>
     *                  [X] represents the X-coordinate of the point, with type {@code double};<p>
     *                  [Y] represents the Y-coordinate of the point, with type {@code double}.
     * @return An instance of the {@link Line} class with parameters specified by the given JSON string.
     * @throws NullPointerException     If argument "pointJson" is null.
     * @throws IllegalArgumentException If at least one of the keys "X", "Y" is not contained in "pointJson"; or if "pointJson" is not a valid JSON string.
     * @throws NumberFormatException    If there is a value associated with a required key, while the value does not contain a parsable {@code double}. (i.e. the value associated with key "X" is "true").
     * @implNote This method calls {@link ShapeParser#parsePoint(JsonNode)}.
     */


    public static Point parsePoint(String pointJson)
    {
        return JsonSerializer.deserialize(pointJson, Point.class);
    }

    /**
     * Parses a line segment from the given {@link JsonNode} and returns a {@link LineSegment} that can represent the line segment.
     *
     * @param lineSegmentJson A {@link JsonNode} with following data format.<p>
     *                        <code>{</code><p>
     *                        <code>&nbsp;&nbsp;&nbsp;&nbsp;"EndPoint1":</code><p>
     *                        <code>&nbsp;&nbsp;&nbsp;&nbsp;{</code><p>
     *                        <code>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"X": [X],</code><p>
     *                        <code>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"Y": [Y]</code><p>
     *                        <code>&nbsp;&nbsp;&nbsp;&nbsp;},</code><p>
     *                        <code>&nbsp;&nbsp;&nbsp;&nbsp;"EndPoint2":</code><p>
     *                        <code>&nbsp;&nbsp;&nbsp;&nbsp;{</code><p>
     *                        <code>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"X": [X],</code><p>
     *                        <code>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"Y": [Y]</code><p>
     *                        <code>&nbsp;&nbsp;&nbsp;&nbsp;}</code><p>
     *                        <code>}</code><p>
     *                        where<p>
     *                        "EndPoint1" and "EndPoint2" encapsulate 2 end points of the line segment;<p>
     *                        [X] represents the X-coordinate of the point, with type {@code double};<p>
     *                        [Y] represents the Y-coordinate of the point, with type {@code double}.
     * @return An instance of the {@link LineSegment} class with parameters specified by the given {@link JsonNode}.
     * @throws NullPointerException     If "lineSegmentJson" is null.
     * @throws IllegalArgumentException If at least one of the keys "END_POINT_1", "END_POINT_2" is not contained in "pointJson"; or if at least one of the keys "X", "Y" is not contained in "EndPoint1" or "EndPoint2".
     * @throws NumberFormatException    If there is a value associated with a required key, while the value does not contain a parsable {@code double}. (i.e. the value associated with key "X" is "true").
     */
    public static LineSegment parseLineSegment(JsonNode lineSegmentJson)
    {
        return parseLineSegment(lineSegmentJson);
    }

    /**
     * Parses a line segment from the given JSON string and returns a {@link LineSegment} that can represent the line segment.
     *
     * @param lineSegmentJson A JSON string with following data format.<p>
     *                        <code>{</code><p>
     *                        <code>&nbsp;&nbsp;&nbsp;&nbsp;"EndPoint1":</code><p>
     *                        <code>&nbsp;&nbsp;&nbsp;&nbsp;{</code><p>
     *                        <code>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"X": [X],</code><p>
     *                        <code>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"Y": [Y]</code><p>
     *                        <code>&nbsp;&nbsp;&nbsp;&nbsp;},</code><p>
     *                        <code>&nbsp;&nbsp;&nbsp;&nbsp;"EndPoint2":</code><p>
     *                        <code>&nbsp;&nbsp;&nbsp;&nbsp;{</code><p>
     *                        <code>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"X": [X],</code><p>
     *                        <code>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"Y": [Y]</code><p>
     *                        <code>&nbsp;&nbsp;&nbsp;&nbsp;}</code><p>
     *                        <code>}</code><p>
     *                        where<p>
     *                        "EndPoint1" and "EndPoint2" encapsulate 2 end points of the line segment;<p>
     *                        [X] represents the X-coordinate of the point, with type {@code double};<p>
     *                        [Y] represents the Y-coordinate of the point, with type {@code double}.
     * @return An instance of the {@link LineSegment} class with parameters specified by the given JSON string.
     * @throws NullPointerException     If "lineSegmentJson" is null.
     * @throws IllegalArgumentException If at least one of the keys "END_POINT_1", "END_POINT_2" is not contained in "pointJson"; or if at least one of the keys "X", "Y" is not contained in "EndPoint1" or "EndPoint2"; or "lineSegmentJson" is not a valid JSON string.
     * @throws NumberFormatException    If there is a value associated with a required key, while the value does not contain a parsable {@code double}. (i.e. the value associated with key "X" is "true").
     * @implNote This method calls {@link ShapeParser#parseLineSegment(JsonNode)}.
     */
    public static LineSegment parseLineSegment(String lineSegmentJson)
    {
        if (lineSegmentJson == null)
            throw new NullPointerException("Argument \"lineSegmentJson\" cannot be null.");

        if (!JsonParser.isValid(lineSegmentJson))
            throw new IllegalArgumentException("Argument \"lineSegmentJson\" must be a valid JSON string.");

        return JsonSerializer.deserialize(lineSegmentJson, LineSegment.class);
    }

    /**
     * Parses a polyline from the given {@link JsonNode} and returns a {@link Polyline} that can represent the polyline.
     *
     * @param polylineJson A {@link JsonNode} with following data format.<p>
     *                     <code>{</code><p>
     *                     <code>&nbsp;&nbsp;&nbsp;&nbsp;"Points": [</code><p>
     *                     <code>&nbsp;&nbsp;&nbsp;&nbsp;{</code><p>
     *                     <code>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"X": [X],</code><p>
     *                     <code>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"Y": [Y]</code><p>
     *                     <code>&nbsp;&nbsp;&nbsp;&nbsp;},</code><p>
     *                     <code>&nbsp;&nbsp;&nbsp;&nbsp;...</code><p>
     *                     <code>&nbsp;&nbsp;&nbsp;&nbsp;]</code><p>
     *                     <code>}</code><p>
     *                     where<p>
     *                     "Points" is the key of the array that contains all points of the polyline;<p>
     *                     [X] represents the X-coordinate of the point, with type {@code double};<p>
     *                     [Y] represents the Y-coordinate of the point, with type {@code double}.
     * @return An instance of the {@link Polyline} class with parameters specified by the given {@link JsonNode}.
     * @throws NullPointerException     If "polylineJson" is null.
     * @throws IllegalArgumentException If "polylineJson" does not contain key "Points"; or if there is a {@link JsonNode} in the point array that does not contain key "X" or key "Y".
     * @throws NumberFormatException    If there is a value associated with a required key, while the value does not contain a parsable {@code double}. (i.e. the value associated with key "X" is "true").
     */
    public static Polyline parsePolyline(JsonNode polylineJson)
    {
        if (polylineJson == null)
            throw new NullPointerException("Argument \"polylineJson\" cannot be null.");

        JsonNode pointsJson = polylineJson.findValue(POINTS);
        if (pointsJson == null)
            throw new IllegalArgumentException("Bad JSON, argument \"lineSegmentJson\" does not contain key \"" + POINTS + "\".");

        Polyline polyline = new Polyline();

        Point[] points = JsonSerializer.deserialize(pointsJson.toString(), Point[].class);
        if (points != null)
        {
            for (Point point : points)
            {
                polyline.addEnd(point);
            }
        }

        return polyline;
    }

    /**
     * Parses a polyline from the given JSON string and returns a {@link Polyline} that can represent the polyline.
     *
     * @param polylineJson A JSON string with following data format.<p>
     *                     <code>{</code><p>
     *                     <code>&nbsp;&nbsp;&nbsp;&nbsp;"Points": [</code><p>
     *                     <code>&nbsp;&nbsp;&nbsp;&nbsp;{</code><p>
     *                     <code>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"X": [X],</code><p>
     *                     <code>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"Y": [Y]</code><p>
     *                     <code>&nbsp;&nbsp;&nbsp;&nbsp;},</code><p>
     *                     <code>&nbsp;&nbsp;&nbsp;&nbsp;...</code><p>
     *                     <code>&nbsp;&nbsp;&nbsp;&nbsp;]</code><p>
     *                     <code>}</code><p>
     *                     where<p>
     *                     "Points" is the key of the array that contains all points of the polyline;<p>
     *                     [X] represents the X-coordinate of the point, with type {@code double};<p>
     *                     [Y] represents the Y-coordinate of the point, with type {@code double}.
     * @return An instance of the {@link Polyline} class with parameters specified by the given JSON string.
     * @throws NullPointerException     If "polylineJson" is null.
     * @throws IllegalArgumentException If "polylineJson" does not contain key "Points"; or if there is a {@link JsonNode} in the point array that does not contain key "X" or key "Y"; or if "polylineJson" is not a valid JSON string.
     * @throws NumberFormatException    If there is a value associated with a required key, while the value does not contain a parsable {@code double}. (i.e. the value associated with key "X" is "true").
     * @implNote This method calls {@link ShapeParser#parsePolyline(JsonNode)}.
     */
    public static Polyline parsePolyline(String polylineJson) throws JsonProcessingException
    {
        if (polylineJson == null)
            throw new NullPointerException("Argument \"polylineJson\" cannot be null.");

        if (!JsonParser.isValid(polylineJson))
            throw new IllegalArgumentException("Argument \"polylineJson\" must be a valid JSON string.");

        return parsePolyline(JsonParser.parseJson(polylineJson));
    }

    /**
     * Parses a bounding box from the given {@link JsonNode} and returns a {@link BoundingBox} that can represent the bounding box.
     * @param boundingBoxJson A {@link JsonNode} with following data format.<p>
     *                        <code>{</code><p>
     *                        <code>"MinX": [MinX],</code><p>
     *                        <code>"MinY": [MinY],</code><p>
     *                        <code>"MaxX": [MaxX],</code><p>
     *                        <code>"MaxY": [MaxY]</code><p>
     *                        <code>}</code><p>
     *                        where<p>
     *                        ([MinX], [MinY]) represents the coordinate of the bottom left point of the bounding box, with type ({@code double}, {@code double});<p>
     *                        ([MaxX], [MaxY]) represents the coordinate of the top right point of the bounding box, with type ({@code double}, {@code double}).<p>
     *                        Term "bottom left" and "top right" are measured in the Cartesian coordinate system, where right is X-axis positive direction, and up is Y-axis positive direction.
     *
     * @return An instance of the {@link BoundingBox} class with parameters specified by the given {@link JsonNode}.
     * @throws NullPointerException If "boundingBoxJson" is null.
     * @throws IllegalArgumentException If at least one of the keys "MinX", "MinY", "MaxX", "MaxY" is not contained in "boundingBoxJson".
     * @throws NumberFormatException    If there is a value associated with a required key, while the value does not contain a parsable {@code double}. (i.e. the value associated with key "MaxX" is "true").
     */
    public static BoundingBox parseBoundingBox(JsonNode boundingBoxJson)
    {
        return parseBoundingBox(boundingBoxJson.toString());
    }

    /**
     * Parses a bounding box from the given JSON string and returns a {@link BoundingBox} that can represent the bounding box.
     * @param boundingBoxJson A JSON string with following data format.<p>
     *                        <code>{</code><p>
     *                        <code>"MinX": [MinX],</code><p>
     *                        <code>"MinY": [MinY],</code><p>
     *                        <code>"MaxX": [MaxX],</code><p>
     *                        <code>"MaxY": [MaxY]</code><p>
     *                        <code>}</code><p>
     *                        where<p>
     *                        ([MinX], [MinY]) represents the coordinate of the bottom left point of the bounding box, with type ({@code double}, {@code double});<p>
     *                        ([MaxX], [MaxY]) represents the coordinate of the top right point of the bounding box, with type ({@code double}, {@code double}).<p>
     *                        Term "bottom left" and "top right" are measured in the Cartesian coordinate system, where right is X-axis positive direction, and up is Y-axis positive direction.
     *
     * @return An instance of the {@link BoundingBox} class with parameters specified by the given JSON string.
     * @throws NullPointerException If "boundingBoxJson" is null.
     * @throws IllegalArgumentException If at least one of the keys "MinX", "MinY", "MaxX", "MaxY" is not contained in "boundingBoxJson"; or "boundingBoxJson" is not a valid JSON string.
     * @throws NumberFormatException    If there is a value associated with a required key, while the value does not contain a parsable {@code double}. (i.e. the value associated with key "MaxX" is "true").
     * @implNote This method calls {@link ShapeParser#parseBoundingBox(JsonNode)}.
     */
    public static BoundingBox parseBoundingBox(String boundingBoxJson)
    {
        if (boundingBoxJson == null)
            throw new NullPointerException("Argument \"boundingBoxJson\" cannot be null.");

        if (!JsonParser.isValid(boundingBoxJson))
            throw new IllegalArgumentException("Argument \"boundingBoxJson\" is not a valid JSON string.");

        return JsonSerializer.deserialize(boundingBoxJson, BoundingBox.class);
    }
}
