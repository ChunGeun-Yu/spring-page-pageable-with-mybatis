package log.test.springtest;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface TestMapper {
    List<HashMap<String,Object>> selectData(HashMap<String, Object> paramMap);
    long selectAllCount();
}
