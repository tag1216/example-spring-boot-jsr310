package examples;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class JpaTest {

    @Autowired
    PlanRepository planRepository;

    @Test
    public void testJpa() {
        LocalDate date = LocalDate.now();
        Plan plan = new Plan();
        plan.setDate(date);
        planRepository.save(plan);
        assertThat(planRepository.findOne(plan.getId()).getDate(), is(date));
    }
}
