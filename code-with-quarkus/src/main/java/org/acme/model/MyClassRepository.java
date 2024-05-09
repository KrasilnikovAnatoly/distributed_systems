package org.acme.model;

import org.acme.model.MyClass;
import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.hibernate.orm.panache.PanacheRepository;


@ApplicationScoped
public class MyClassRepository implements PanacheRepository<MyClass>{
}
