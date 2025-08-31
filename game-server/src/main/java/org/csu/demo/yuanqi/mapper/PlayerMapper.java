package org.csu.demo.yuanqi.mapper;// in package ...mapper
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.csu.demo.yuanqi.entity.Player;

@Mapper // Tells Spring/MyBatis to create an implementation for this interface
public interface PlayerMapper extends BaseMapper<Player> {
    // BaseMapper already provides common methods like insert, delete, update, and selectById.
    // For custom queries like "findByUsername", we typically use a query wrapper.
}