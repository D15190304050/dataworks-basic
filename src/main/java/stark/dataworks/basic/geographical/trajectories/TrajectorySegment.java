package stark.dataworks.basic.geographical.trajectories;

import stark.dataworks.basic.mathematics.geometry.Polyline;
import stark.dataworks.basic.collections.LinkedList;
import stark.dataworks.basic.mathematics.geometry.BoundingBox;
import stark.dataworks.basic.mathematics.geometry.Point;

// Assuming each TrajectorySegment instance cannot be divided into sub-segments.
// An instance of the TrajectorySegment class only contains limited semantic information.
// For richer semantic information of a trajectory, see SemanticTrajectory
public class TrajectorySegment
{
    private LinkedList<Point> points;
    private String annotationInfo;
    private boolean moving;
    private LinkedList<TopologicalSpatialRelationship> relationships;
    private Object tag;


    public TrajectorySegment(Iterable<Point> points, boolean moving)
    {
        if (points == null)
            throw new NullPointerException("Argument \"points\" is null.");

        this.points = new LinkedList<>(points);
        this.moving = moving;
        tag = null;
        annotationInfo = "";
        relationships = new LinkedList<>();
    }

    public boolean isMoving()
    {
        return moving;
    }

    public Iterable<TopologicalSpatialRelationship> getRelationships()
    {
        return relationships;
    }

    public Object getTag()
    {
        return tag;
    }

    public void setTag(Object tag)
    {
        this.tag = tag;
    }

    public Iterable<Point> getPoints()
    {
        return points;
    }

    /**
     * Gets the number of points of the trajectory segment.
     * @return The number of points of the trajectory segment.
     */
    public int count()
    {
        return points.count();
    }

    public String getAnnotationInfo()
    {
        return annotationInfo;
    }

    public void setAnnotationInfo(String annotationInfo)
    {
        this.annotationInfo = annotationInfo;
    }

    @Override
    public String toString()
    {
        StringBuilder segmentInfo = new StringBuilder(annotationInfo);
        for (TopologicalSpatialRelationship relationship : relationships)
            segmentInfo.append("; ").append(relationship);
        return segmentInfo.toString();
    }

    public TopologicalSpatialRelationship determineTopologicalRelationship(RegionOfInterest roi)
    {
        if (roi == null)
            throw new NullPointerException("Argument \"roi\" is null.");

        BoundingBox box = (BoundingBox) roi.getRegion();
        Point boxCenter = new Point((box.getMinX() + box.getMaxX()) / 2, (box.getMinY() + box.getMaxY()) / 2);
        double radius = Math.max(box.getWidth(), box.getHeight());

        Polyline polyLine = new Polyline();
        polyLine.addEnd(points.getFirst().getValue());
        polyLine.addEnd(points.getLast().getValue());

        LinkedList<Point> intersections = new LinkedList<>(polyLine.getIntersectionsWith(box));

        boolean inside = allInside(box);
        boolean outside = allOutside(box);

        // 对于移动段
        // 2个交点：穿过
        // 1个交点，起点在里面：走出
        // 1个交点，终点在里面：走入
        // 全在里面(没有交点)：在里面走
        // 全在外面，距离ROI中心点不远：路过
        // 全在外面，距离ROI中心点较远：没有关系

        // 对于停留段
        // 全在里面：在里面停留
        // 全在外面，距离ROI中心不远：在外面停留

        // 其它情况：NULL

        if (moving)
        {
            if (intersections.count() == 2)
                return new TopologicalSpatialRelationship(TopologicalPredicate.WALK_THROUGH, roi);
            else if ((intersections.count() == 1) && box.contains(points.getFirst().getValue()))
                return new TopologicalSpatialRelationship(TopologicalPredicate.LEAVE, roi);
            else if ((intersections.count() == 1) && box.contains(points.getLast().getValue()))
                return new TopologicalSpatialRelationship(TopologicalPredicate.ENTER, roi);
            else if (inside)
                return new TopologicalSpatialRelationship(TopologicalPredicate.WALK_INSIDE, roi);
            else if (outside && polyLine.distanceTo(boxCenter) <= radius)
                return new TopologicalSpatialRelationship(TopologicalPredicate.PASS_BY, roi);
            else if (outside && polyLine.distanceTo(boxCenter) > radius)
                return new TopologicalSpatialRelationship(TopologicalPredicate.NULL, roi);
        }
        else
        {
            if (inside)
                return new TopologicalSpatialRelationship(TopologicalPredicate.STAY_INSIDE, roi);
            else if (outside && (polyLine.distanceTo(boxCenter) <= radius))
                return new TopologicalSpatialRelationship(TopologicalPredicate.STAY_OUTSIDE, roi);
        }

        return new TopologicalSpatialRelationship(TopologicalPredicate.NULL, roi);
    }

    private boolean allInside(BoundingBox box)
    {
        for (Point point : points)
        {
            if (!box.contains(point))
                return false;
        }

        return true;
    }

    private  boolean allOutside(BoundingBox box)
    {
        for (Point point : points)
        {
            if (box.contains(point))
                return false;
        }

        return true;
    }
}
