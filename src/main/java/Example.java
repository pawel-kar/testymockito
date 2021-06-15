import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Example {

    public static void main(String[] args) {
        Example example = new Example();
        example.run();


    }

    private void run() {

        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1L, "Andrzej", 2_000));
        employees.add(new Employee(2L, "Barbara", 2_500));
        employees.add(new Employee(3L, "Genowefa", 2_100));
        employees.add(new Employee(4L, "Stefan", 2_000));
        employees.add(new Employee(5L, "Genek", 3_100));

        List<Company> companies = new ArrayList<>();

        companies.add(new Company(1L, "LG"));
        companies.add(new Company(1L, "SAMSUNG"));
        companies.add(new Company(2L, "SIEMENS"));
        companies.add(new Company(3L, "SIEMENS"));
        companies.add(new Company(4L, "LG"));
        companies.add(new Company(4L, "SIEMENS"));


        Collection<Employee> men = findMen(employees);
        System.out.println(men);

        Double averageWomenSalary = averageWomenSalary(employees);
        System.out.println(averageWomenSalary);

        Map<String, List<Employee>> stringListMap = groupByCompanyName(employees, companies);
        System.out.println(stringListMap);
    }


    //wszyscy na ktorych imie nie kończy się na "a"
    Collection<Employee> findMen(Collection<Employee> employees) {
        return employees.stream()
                .collect(Collectors.partitioningBy(Predicate.not(employee -> employee.getName().endsWith("a"))))
                .get(Boolean.TRUE);
    }

    //srednie zarobki kobiet

    Double averageWomenSalary(Collection<Employee> employees) {
        List<Employee> women = employees.stream()
                .collect(Collectors.partitioningBy(employee -> employee.getName().endsWith("a")))
                .get(Boolean.TRUE);
        return women.stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .orElseThrow();
    }

    //niech zwróci pracowników pogrupowanych po nazwach firma

    Map<String, List<Employee>> groupByCompanyName(List<Employee> employees, List<Company> companies) {
        Map<Long, String> idCompanyName = companies.stream()
                .collect(Collectors.toMap(Company::getUserId, Company::getName));
        return employees.stream()
                .collect(Collectors.groupingBy(employee -> idCompanyName.get(employee.getId())));
    }


}
