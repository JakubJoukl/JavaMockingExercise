package cz.upce.fei.inptp.databasedependency.dao;

import cz.upce.fei.inptp.databasedependency.entity.Person;

public interface IPersonDAO extends DAO<Person> {
    @Override
    void save(Person object);

    @Override
    Person load(String parameters);

    String getRoleWhereStringFor(Person person);
}
