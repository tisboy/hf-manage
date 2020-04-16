package threadPool;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import com.baidu.disconf.client.common.annotations.DisconfUpdateService;
import com.baidu.disconf.client.common.update.IDisconfUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
@DisconfFile(filename = "threadPool.properties")
@DisconfUpdateService(classes = {ThreadPoolConfig.class})
public class ThreadPoolConfig implements IDisconfUpdate {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolConfig.class);

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 设置核心线程数
     */
    private int corePoolSize;
    /**
     * 设置最大线程数
     */
    private int maxPoolSize;

    @DisconfFileItem(name = "corePoolSize", associateField = "corePoolSize")
    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    @DisconfFileItem(name = "maxPoolSize", associateField = "maxPoolSize")
    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public void reload() throws Exception {
        LOGGER.info("start to change ThreadPool: corePoolSize={},maxPoolSize={}", corePoolSize, maxPoolSize);


        if(null != threadPoolTaskExecutor){
            threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
            threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        }

        LOGGER.info("change ThreadPool ok.");
    }
}
