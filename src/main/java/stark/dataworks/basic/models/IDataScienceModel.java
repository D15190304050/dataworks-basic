package stark.dataworks.basic.models;

public interface IDataScienceModel
{
    void save(String modelPath);
    void restore(String modelPath);
}
