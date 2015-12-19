package examples;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class JacksonTest {

    @Autowired
    ObjectMapper mapper;

    @Test
    public void test_JSR310シリアライザーなし() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        LocalDate date = LocalDate.parse("2015-12-31");

        String json = mapper.writeValueAsString(date);
        System.out.println(json);
//        assertThat(json, is("{'year':2015,'month':'DECEMBER','era':'CE','dayOfMonth':31,'dayOfWeek':'THURSDAY','dayOfYear':365,'leapYear':false,'monthValue':12,'chronology':{'id':'ISO','calendarType':'iso8601'}}".replaceAll("'", "\"")));
    }

    @Test
    public void test_JSR310シリアライザーあり() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        LocalDate date = LocalDate.parse("2015-12-31");

        String json = mapper.writeValueAsString(date);
        System.out.println(json);
        assertThat(json, is("[2015,12,31]"));
    }

    @Test
    public void test_JSR310シリアライザー書式指定() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        mapper.registerModule(module);

        LocalDate date = LocalDate.parse("2015-12-31");
        String json = mapper.writeValueAsString(date);
        System.out.println(json);
        assertThat(json, is("\"2015/12/31\""));
    }

    @Test
    public void test_Spring() throws Exception {
        LocalDate date = LocalDate.parse("2015-12-31");
        String json = mapper.writeValueAsString(date);
        System.out.println(json);
        assertThat(json, is("\"2015/12/31\""));
    }

    @Test
    public void test_Spring_Entity() throws Exception {
        Plan plan = new Plan();
        plan.setDate(LocalDate.parse("2015-12-31"));

        String json = mapper.writeValueAsString(plan);
        assertThat(json, containsString("\"date\":\"2015/12/31\""));

        plan = mapper.readValue(json, Plan.class);
        assertThat(plan.getDate(), is(LocalDate.parse("2015-12-31")));
    }
}
