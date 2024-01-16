package cz.upce.fei.inptp.databasedependency.business;

import cz.upce.fei.inptp.databasedependency.dao.PersonDAO;
import cz.upce.fei.inptp.databasedependency.dao.PersonRolesDAO;
import cz.upce.fei.inptp.databasedependency.entity.PersonRole;
import cz.upce.fei.inptp.databasedependency.entity.Role;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.LinkedList;

import static cz.upce.fei.inptp.databasedependency.business.AccessOperationType.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AuthorizationServiceTest {

    // TODO: Authorize(person, "/section/subsection", rw) - PersonRole([Role("/section/subsection", rw)]) - pass
    // TODO: Authorize(person, "/section/subsection", rw) - PersonRole([Role("/section/subsection", ro)]) - fail
    // TODO: Authorize(person, "/section/subsection", rw) - PersonRole([Role("/section", rw)]) - pass
    // TODO: Authorize(person, "/section/subsection", rw) - PersonRole([Role("/section", ro)]) - fail
    // TODO: Authorize(person, "/section/subsection", rw) - PersonRole([Role("/", rw)]) - pass
    // TODO: Authorize(person, "/section/subsection", rw) - PersonRole([Role("/", ro)]) - fail
    // TODO: Authorize(person, "/section/subsection", rw) - PersonRole([Role("/section/subsection", admin)]) - pass
    // TODO: Authorize(person, "/section/subsection", rw) - PersonRole([Role("/section", admin)]) - pass
    // TODO: Authorize(person, "/section/subsection", rw) - PersonRole([Role("/", admin)]) - pass


    private String section;
    private String access;
    private String modifier;

    @Test
    void authorizeSectionSubsectionRW() {
        PersonDAO personDAO = Mockito.mock(PersonDAO.class);
        when(personDAO.getRoleWhereStringFor(any())).thenReturn("TestRole");
        PersonRolesDAO personRolesDAO = Mockito.mock(PersonRolesDAO.class);

        LinkedList<Role> roles = new LinkedList<>();
        roles.add(new Role("/section/subsection", "rw", null));
        when(personRolesDAO.load("TestRole")).thenReturn(new PersonRole(null, roles));

        AuthorizationService authorizationService = new AuthorizationService(personDAO, personRolesDAO);

        boolean shouldSucceed = authorizationService.Authorize(null, "/section/subsection", Write);
        assertTrue(shouldSucceed);
    }

    @Test
    void authorizeSectionSubsectionRO() {
        PersonDAO personDAO = Mockito.mock(PersonDAO.class);
        when(personDAO.getRoleWhereStringFor(any())).thenReturn("TestRole");
        PersonRolesDAO personRolesDAO = Mockito.mock(PersonRolesDAO.class);

        LinkedList<Role> roles = new LinkedList<>();
        roles.add(new Role("/section/subsection", "ro", null));
        when(personRolesDAO.load("TestRole")).thenReturn(new PersonRole(null, roles));

        AuthorizationService authorizationService = new AuthorizationService(personDAO, personRolesDAO);
        authorizationService.setPersonDao(personDAO);
        authorizationService.setPersonRolesDao(personRolesDAO);

        boolean shouldFail = authorizationService.Authorize(null, "/section/subsection", Write);
        assertFalse(shouldFail);
    }

    @Test
    void authorizeSectionRW() {
        PersonDAO personDAO = Mockito.mock(PersonDAO.class);
        when(personDAO.getRoleWhereStringFor(any())).thenReturn("TestRole");
        PersonRolesDAO personRolesDAO = Mockito.mock(PersonRolesDAO.class);

        LinkedList<Role> roles = new LinkedList<>();
        roles.add(new Role("/section", "rw", null));
        when(personRolesDAO.load("TestRole")).thenReturn(new PersonRole(null, roles));

        AuthorizationService authorizationService = new AuthorizationService(personDAO, personRolesDAO);
        authorizationService.setPersonDao(personDAO);
        authorizationService.setPersonRolesDao(personRolesDAO);

        boolean shouldSucceed = authorizationService.Authorize(null, "/section/subsection", Write);
        assertTrue(shouldSucceed);
    }

    @Test
    void authorizeSectionRO() {
        PersonDAO personDAO = Mockito.mock(PersonDAO.class);
        when(personDAO.getRoleWhereStringFor(any())).thenReturn("TestRole");
        PersonRolesDAO personRolesDAO = Mockito.mock(PersonRolesDAO.class);

        LinkedList<Role> roles = new LinkedList<>();
        roles.add(new Role("/section", "ro", null));
        when(personRolesDAO.load("TestRole")).thenReturn(new PersonRole(null, roles));

        AuthorizationService authorizationService = new AuthorizationService(personDAO, personRolesDAO);
        authorizationService.setPersonDao(personDAO);
        authorizationService.setPersonRolesDao(personRolesDAO);

        boolean shouldFail = authorizationService.Authorize(null, "/section/subsection", Write);
        assertFalse(shouldFail);
    }

    @Test
    void authorizeRW() {
        PersonDAO personDAO = Mockito.mock(PersonDAO.class);
        when(personDAO.getRoleWhereStringFor(any())).thenReturn("TestRole");
        PersonRolesDAO personRolesDAO = Mockito.mock(PersonRolesDAO.class);

        LinkedList<Role> roles = new LinkedList<>();
        roles.add(new Role("/", "rw", null));
        when(personRolesDAO.load("TestRole")).thenReturn(new PersonRole(null, roles));

        AuthorizationService authorizationService = new AuthorizationService(personDAO, personRolesDAO);
        authorizationService.setPersonDao(personDAO);
        authorizationService.setPersonRolesDao(personRolesDAO);

        boolean shouldSucceed = authorizationService.Authorize(null, "/section/subsection", Write);
        assertTrue(shouldSucceed);
    }

    @Test
    void authorizeRO() {
        PersonDAO personDAO = Mockito.mock(PersonDAO.class);
        when(personDAO.getRoleWhereStringFor(any())).thenReturn("TestRole");
        PersonRolesDAO personRolesDAO = Mockito.mock(PersonRolesDAO.class);

        LinkedList<Role> roles = new LinkedList<>();
        roles.add(new Role("/", "ro", null));
        when(personRolesDAO.load("TestRole")).thenReturn(new PersonRole(null, roles));

        AuthorizationService authorizationService = new AuthorizationService(personDAO, personRolesDAO);
        authorizationService.setPersonDao(personDAO);
        authorizationService.setPersonRolesDao(personRolesDAO);

        boolean shouldFail = authorizationService.Authorize(null, "/section/subsection", Write);
        assertFalse(shouldFail);
    }

    @Test
    void authorizeAdminSectionSubsection() {
        PersonDAO personDAO = Mockito.mock(PersonDAO.class);
        when(personDAO.getRoleWhereStringFor(any())).thenReturn("TestRole");
        PersonRolesDAO personRolesDAO = Mockito.mock(PersonRolesDAO.class);

        LinkedList<Role> roles = new LinkedList<>();
        roles.add(new Role("/", "admin", null));
        when(personRolesDAO.load("TestRole")).thenReturn(new PersonRole(null, roles));

        AuthorizationService authorizationService = new AuthorizationService(personDAO, personRolesDAO);
        authorizationService.setPersonDao(personDAO);
        authorizationService.setPersonRolesDao(personRolesDAO);

        boolean shouldSucceed = authorizationService.Authorize(null, "/section/subsection", Write);
        assertTrue(shouldSucceed);
    }

    @Test
    void authorizeAdminSection() {
        PersonDAO personDAO = Mockito.mock(PersonDAO.class);
        when(personDAO.getRoleWhereStringFor(any())).thenReturn("TestRole");
        PersonRolesDAO personRolesDAO = Mockito.mock(PersonRolesDAO.class);

        LinkedList<Role> roles = new LinkedList<>();
        roles.add(new Role("/", "admin", null));
        when(personRolesDAO.load("TestRole")).thenReturn(new PersonRole(null, roles));

        AuthorizationService authorizationService = new AuthorizationService(personDAO, personRolesDAO);
        authorizationService.setPersonDao(personDAO);
        authorizationService.setPersonRolesDao(personRolesDAO);

        boolean shouldSucceed = authorizationService.Authorize(null, "/section", Write);
        assertTrue(shouldSucceed);
    }

    @Test
    void authorizeAdmin() {
        PersonDAO personDAO = Mockito.mock(PersonDAO.class);
        when(personDAO.getRoleWhereStringFor(any())).thenReturn("TestRole");
        PersonRolesDAO personRolesDAO = Mockito.mock(PersonRolesDAO.class);

        LinkedList<Role> roles = new LinkedList<>();
        roles.add(new Role("/", "admin", null));
        when(personRolesDAO.load("TestRole")).thenReturn(new PersonRole(null, roles));

        AuthorizationService authorizationService = new AuthorizationService(personDAO, personRolesDAO);
        authorizationService.setPersonDao(personDAO);
        authorizationService.setPersonRolesDao(personRolesDAO);

        boolean shouldSucceed = authorizationService.Authorize(null, "/", Write);
        assertTrue(shouldSucceed);
    }
}