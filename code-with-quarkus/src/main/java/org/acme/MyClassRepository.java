package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class MyClassRepository implements PanacheRepository<MyClass>{
}
