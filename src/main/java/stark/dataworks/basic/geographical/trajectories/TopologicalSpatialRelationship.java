package stark.dataworks.basic.geographical.trajectories;

public class TopologicalSpatialRelationship
{
    private TopologicalPredicate predicate;
    private RegionOfInterest roi;


    public TopologicalSpatialRelationship(TopologicalPredicate predicate, RegionOfInterest roi)
    {
        validatePredicate(predicate);
        validateRoi(roi);

        this.predicate = predicate;
        this.roi = roi;
    }


    private void validatePredicate(TopologicalPredicate predicate)
    {
        if (predicate == null)
            throw new NullPointerException("Argument \"predicate\" is null.");
    }


    private void validateRoi(RegionOfInterest roi)
    {
        if (roi == null)
            throw new NullPointerException("Argument \"roi\" is null.");
    }

    public TopologicalPredicate getPredicate()
    {
        return predicate;
    }


    public void setPredicate(TopologicalPredicate predicate)
    {
        validatePredicate(predicate);
        this.predicate = predicate;
    }

    public RegionOfInterest getRoi()
    {
        return roi;
    }


    public void setRoi(RegionOfInterest roi)
    {
        validateRoi(roi);
        this.roi = roi;
    }

    @Override
    public String toString()
    {
        return predicate.toString() + " " + roi.getName();
    }
}
