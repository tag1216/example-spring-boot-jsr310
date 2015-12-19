package examples;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/web/plans")
public class PlanController {

    @Autowired
    PlanRepository planRepository;

    @RequestMapping(method = GET)
    public ModelAndView list() {
        ModelAndView model = new ModelAndView("plans");
        model.addObject("plans", planRepository.findAll(new Sort("date")));
        return model;
    }

    @RequestMapping(method = POST)
    public String create(@ModelAttribute Plan plan) {
        planRepository.save(plan);
        return "redirect:/web/plans";
    }

    @RequestMapping(path = "{id}", method = GET)
    public ModelAndView editForm(@PathVariable long id) {
        ModelAndView model = new ModelAndView("edit");
        model.addObject("plan", planRepository.findOne(id));
        return model;
    }

    @RequestMapping(path = "{id}", method = PUT)
    public String update(@PathVariable long id, @ModelAttribute Plan plan) {
        plan.setId(id);
        planRepository.save(plan);
        return "redirect:/web/plans";
    }

    @RequestMapping(path = "{id}", method = DELETE)
    public String delete(@PathVariable long id) {
        planRepository.delete(id);
        return "redirect:/web/plans";
    }

}
