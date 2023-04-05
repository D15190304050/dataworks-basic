package stark.dataworks.models;

public interface IDataScienceModel
{
    void save(String modelPath);
    void restore(String modelPath);
}
