package examples;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

public class PlanSpecifications {

    private PlanSpecifications() {}

    public static Specification<Plan> dateIs(LocalDate date) {
        if (date == null) return null;
        return (root, query, cb) -> {
            return cb.equal(root.get("date"), date);
        };
    }

    public static Specification<Plan> titleContains(String title) {
        if (title == null) return null;
        return (root, query, cb) -> {
            return cb.like(root.get("title"), cb.concat("%", cb.concat(cb.literal(title), "%")));
        };
    }
}
