package stark.dataworks.geographical.trajectories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import stark.dataworks.collections.LinkedList;
import stark.dataworks.data.json.JsonParser;
import stark.dataworks.data.json.JsonSerializer;

import java.sql.Timestamp;

// Note: sample rate is not a field in the Trajectory class because it can be calculated by 2 adjacent sampling points.
// And yet the trajectory may not have a stable sample rate. But, timestamp for each sample point is definite.
public class Trajectory
{
    private static final String TRAJECTORY_ID = "TrajectoryId";
    private static final String POINTS = "Points";
    private static final String X = "X";
    private static final String Y = "Y";
    private static final String SAMPLING_TIME = "SamplingTime";

    private LinkedList<SamplePoint> points;
    private int trajectoryId;
    private Object tag;


    public Iterable<SamplePoint> getPoints()
    {
        return points;
    }


    public int getTrajectoryId()
    {
        return trajectoryId;
    }

    /**
     * Gets the number of points of the trajectory.
     *
     * @return The number of points of the trajectory.
     */
    public int count()
    {
        return points.count();
    }

    public Trajectory()
    {
        this.trajectoryId = -1;
        this.points = new LinkedList<>();
        this.tag = null;
    }

    /**
     * @param trajectoryJsonText A JSON string that contains the ID and coordinates of the trajectory.<p>
     *                           <code>{</code><p>
     *                           <code>&nbsp;&nbsp;&nbsp;&nbsp;"TrajectoryId": [TrajectoryId],</code><p>
     *                           <code>&nbsp;&nbsp;&nbsp;&nbsp;"Points": [</code><p>
     *                           <code>&nbsp;&nbsp;&nbsp;&nbsp;{</code><p>
     *                           <code>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"X": [X],</code><p>
     *                           <code>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"Y": [Y]</code><p>
     *                           <code>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"SamplingTime": [SamplingTime]</code><p>
     *                           <code>&nbsp;&nbsp;&nbsp;&nbsp;}</code><p>
     *                           <code>&nbsp;&nbsp;&nbsp;&nbsp;...</code><p>
     *                           <code>&nbsp;&nbsp;&nbsp;&nbsp;]</code><p>
     *                           <code>}</code><p>
     *                           Copy and paste to a code editor (or other tools) to view this JSON format clearly.<p>
     *                           [TrajectoryId] represents the ID of the trajectory with type int.<p>
     *                           [X] represents the x-coordinate of the point with type double.<p>
     *                           [Y] represents the y-coordinate of the point with type double.<p>
     *                           [SamplingTime] represents the sampling time of the point with type {@link Timestamp}, this is an optional field.
     */

    public static Trajectory parse(String trajectoryJsonText) throws JsonProcessingException
    {
        if (trajectoryJsonText == null)
            throw new NullPointerException("Argument \"trajectoryJsonText\" is null.");

        if (!JsonParser.isValid(trajectoryJsonText))
            throw new IllegalArgumentException("Argument \"trajectoryJsonText\" cannot be parsed as JSON object.");

        Trajectory trajectory = new Trajectory();

        JsonNode trajectoryJson = JsonParser.parseJson(trajectoryJsonText);
        trajectory.trajectoryId = trajectoryJson.findValue(TRAJECTORY_ID).intValue();
        JsonNode pointsJson = trajectoryJson.findValue(POINTS);
        SamplePoint[] samplePoints = JsonSerializer.deserialize(pointsJson.toString(), SamplePoint[].class);
        trajectory.points.addAll(samplePoints);

        return trajectory;
    }


    public Trajectory(int id, Iterable<SamplePoint> points)
    {
        if (points == null)
            throw new NullPointerException("Argument \"points\" is null.");

        this.trajectoryId = id;
        this.points = new LinkedList<>(points);
        tag = null;
    }

    public Object getTag()
    {
        return tag;
    }

    public void setTag(Object tag)
    {
        this.tag = tag;
    }

    public void addSamplePoint(SamplePoint point)
    {
    }

    public JsonNode toJson()
    {
        ObjectNode trajectoryJson = JsonParser.createObjectNode();
        trajectoryJson.put(TRAJECTORY_ID, trajectoryId);

        ArrayNode points = JsonParser.createArrayNode();
        for (SamplePoint point : this.points)
        {
            JsonNode jsonNode = JsonParser.parseObjectToJsonNode(point);
            points.add(jsonNode);
        }
        trajectoryJson.set(POINTS, points);

        return trajectoryJson;
    }
}
