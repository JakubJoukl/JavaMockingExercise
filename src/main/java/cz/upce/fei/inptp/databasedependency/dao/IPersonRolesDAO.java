package cz.upce.fei.inptp.databasedependency.dao;

import cz.upce.fei.inptp.databasedependency.entity.PersonRole;

public interface IPersonRolesDAO extends DAO<PersonRole> {
    @Override
    void save(PersonRole object);

    @Override
    PersonRole load(String parameters);
}
