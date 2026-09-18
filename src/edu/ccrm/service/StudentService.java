package edu.ccrm.service;

import edu.ccrm.domain.*;
import java.util.*;
import java.util.stream.Collectors;

public class StudentService implements Searchable<Student> {
    private final DataStore ds = DataStore.instance();

    public void add(Student s) { ds.students().put(s.getId(), s); }
    public Student add(String id, String regNo, String fullName, String email, String status) {
        Student student = new Student(id, regNo, fullName, email, status);
        add(student);
        return student;
    }
    public Optional<Student> byId(String id) { return Optional.ofNullable(ds.students().get(id)); }
    public List<Student> all() { return new ArrayList<>(ds.students().values()); }

    public List<Student> byStatus(String status) {
        return all().stream().filter(s -> Objects.equals(s.getStatus(), status)).collect(Collectors.toList());
    }

    @Override public List<Student> firstN(int n) { // explicit default-method override
        return Searchable.super.firstN(n);
    }
}
