package mapper;

import lombok.extern.slf4j.Slf4j;
import org.diguapao.cloud.framework.spring.SpringAopApplication;
import org.diguapao.cloud.framework.spring.aop.service.CalculateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {SpringAopApplication.class,},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                // JVM 启动参数
        })
public class CalculateServiceTest {

    @Resource
    private CalculateService calculateService;

    @Test
    public void divideTest() {
        log.info("第一轮测试开始");
        calculateService.divide(10, 2);
        log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.info("第二轮测试开始");
        calculateService.divide(10, 0);
    }

}