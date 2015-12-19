package examples;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebAppConfiguration
public class MvcTest {

    private MockMvc mvc;

    @Autowired
    PlanRestController planRestController;

    @Autowired
    MappingJackson2HttpMessageConverter jackson2HttpMessageConverter;

    @Autowired
    FormattingConversionService conversionService;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders
                .standaloneSetup(planRestController)
                .setMessageConverters(jackson2HttpMessageConverter)
                .setConversionService(conversionService)
                .build();
    }

    @Test
    public void testIndex() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/plans").param("date", "2015/12/01"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"date\":\"2015/12/01\"")));
    }
}
