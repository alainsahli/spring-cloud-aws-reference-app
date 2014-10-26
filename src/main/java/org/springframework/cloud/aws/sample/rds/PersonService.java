package org.springframework.cloud.aws.sample.rds;

import java.util.List;

/**
 * @author Agim Emruli
 */
public interface PersonService {

    List<Person> all();

    void store(Person person);

}
