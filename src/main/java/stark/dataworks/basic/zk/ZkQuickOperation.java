package stark.dataworks.basic.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class ZkQuickOperation
{
    private final CuratorFramework zkClient;

    public ZkQuickOperation(CuratorFramework zkClient)
    {
        this.zkClient = zkClient;
        this.zkClient.start();
    }

    public CuratorFramework getZkClient()
    {
        return zkClient;
    }

    public boolean nodeExists(String nodePath) throws Exception
    {
        Stat stat = zkClient.checkExists().forPath(nodePath);
        return null != stat;
    }

    public void tryCreateNode(String path, String data, CreateMode mode) throws Exception
    {
        if (!nodeExists(path))
        {
            zkClient.create()
                .creatingParentsIfNeeded()
                .withMode(mode)
                .forPath(path, data.getBytes(StandardCharsets.UTF_8));
        }
    }

    /**
     * Try to get data of a specified node.<br/>
     * Normally, you should call {@link ZkQuickOperation#nodeExists(String)} before calling {@link ZkQuickOperation#getNodeData(String)}.
     * @param path
     * @return
     * @throws Exception
     */
    public String getNodeData(String path) throws Exception
    {
        byte[] data = zkClient.getData().storingStatIn(new Stat()).forPath(path);
        return new String(data, StandardCharsets.UTF_8);
    }

    public Stat setNodeData(String path, String data) throws Exception
    {
        return zkClient.setData()
            .forPath(path, data.getBytes(StandardCharsets.UTF_8));
    }

    public Stat setNodeData(String path, String data, int version) throws Exception
    {
        return zkClient.setData()
            .withVersion(version)
            .forPath(path, data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Deletes a leaf node.
     * @param path
     * @throws Exception
     */
    public void deleteNode(String path) throws Exception
    {
        if (nodeExists(path))
            zkClient.delete().forPath(path);
    }

    /**
     * Deletes a leaf node.
     * @param path
     * @param version
     * @throws Exception
     */
    public void deleteNode(String path, int version) throws Exception
    {
        if (nodeExists(path))
        {
            zkClient.delete()
                .withVersion(version)
                .forPath(path);
        }
    }

    public void deleteNodeAndChildren(String path) throws Exception
    {
        zkClient.delete()
            .guaranteed()
            .deletingChildrenIfNeeded()
            .forPath(path);
    }

    public CuratorCache addTreeListener(String path, CuratorCacheListener listener)
    {
        CuratorCache curatorCache = CuratorCache.builder(zkClient, path).build();
        return addListener(curatorCache, listener);
    }

    public CuratorCache addNodeListener(String path, CuratorCacheListener listener)
    {
        CuratorCache curatorCache = CuratorCache.builder(zkClient, path).withOptions(CuratorCache.Options.SINGLE_NODE_CACHE).build();
        return addListener(curatorCache, listener);
    }

    private CuratorCache addListener(CuratorCache curatorCache, CuratorCacheListener listener)
    {
        curatorCache.start();
        curatorCache.listenable().addListener(listener);

        return curatorCache;
    }

    public List<String> getChildrenNames(String parentNodePath) throws Exception
    {
        return zkClient.getChildren().forPath(parentNodePath);
    }
}
