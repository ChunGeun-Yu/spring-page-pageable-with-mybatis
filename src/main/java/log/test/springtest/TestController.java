package log.test.springtest;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class TestController {

    @Autowired
    TestMapper testMapper;

    @GetMapping("/test")
    public Page<String> test(Pageable pageable) {

        log.info("pageable: {}", pageable);

        HashMap<String, Object> paramMap = new HashMap<>();

        paramMap.put("offset", pageable.getOffset());
        paramMap.put("size", pageable.getPageSize());

        List<HashMap<String, Object>> results = testMapper.selectData(paramMap);

        // 결과 map list  에서 ID 필드만 추출해서 list 로 생성
        List<String> ids = results.stream()
                .map(m -> (String)m.get("ID"))
                .collect(Collectors.toList());

        // total count 용 쿼리를 추가로 실행해줘야함.
        long totalCount = testMapper.selectAllCount();

        log.info("result: {}", results);
        log.info("totalCount: {}", totalCount);

        // 특정 page에 해당하는 list, 요청으로 받은 pageable, totalCount 로 Page 를 생성
        return new PageImpl<>(ids, pageable, totalCount);
    }
}
