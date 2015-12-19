package examples;

import static examples.PlanSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/plans")
public class PlanRestController {

    @Autowired
    PlanRepository planRepository;

    @RequestMapping(method = GET)
    public List<Plan> index(
            @RequestParam(required = false) /*@DateTimeFormat(pattern = "yyyy/MM/dd")*/  LocalDate date,
            @RequestParam(required = false) String title) {
        return planRepository.findAll(
                where(dateIs(date))
                .and(titleContains(title)),
                new Sort("date"));
    }

    @RequestMapping(path = "{id}", method = GET)
    public Plan get(@PathVariable long id) {
        return planRepository.findOne(id);
    }

    @RequestMapping(method = POST, consumes = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> post(@RequestBody Plan plan) {
        planRepository.save(plan);
        return new ResponseEntity<>(CREATED);
    }

    @RequestMapping(path = "{id}", method = PUT, consumes = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> put(@PathVariable long id, @RequestBody Plan plan) {
        plan.setId(id);
        planRepository.save(plan);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @RequestMapping(path = "{id}", method = DELETE, consumes = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> delete(@PathVariable long id) {
        planRepository.delete(id);
        return new ResponseEntity<>(NO_CONTENT);
    }
}
