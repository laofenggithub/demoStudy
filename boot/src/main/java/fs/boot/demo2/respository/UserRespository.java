package fs.boot.demo2.respository;

import fs.boot.demo2.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@link User}{@link Repository}
 */
@Repository
public class UserRespository {
    /**
     * 采用内存型的存储方式->Map
     *
     * */
    private final ConcurrentMap<Integer,User> repository =
            new ConcurrentHashMap<Integer, User>();

    private final static AtomicInteger idGenerator =
            new AtomicInteger();
    /**
     * 保存用户对象
     *
     * @param user {@link User} 对象
     * @return 如果保存成功，返回<code>true</code>
     * 否则<code>false</code>
     */
    public boolean save(User user) {
        // ID 从1 开始
        Integer id = idGenerator.incrementAndGet();
        user.setId(id);
        return repository.put(id,user)==null;
    }

    public Collection<User> findAll(){
        return repository.values();
    }
}
