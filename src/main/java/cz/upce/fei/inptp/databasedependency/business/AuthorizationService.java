package cz.upce.fei.inptp.databasedependency.business;

import com.google.inject.Inject;
import cz.upce.fei.inptp.databasedependency.dao.IPersonDAO;
import cz.upce.fei.inptp.databasedependency.dao.PersonRolesDAO;
import cz.upce.fei.inptp.databasedependency.dao.PersonDAO;
import cz.upce.fei.inptp.databasedependency.dao.IPersonRolesDAO;
import cz.upce.fei.inptp.databasedependency.entity.PersonRole;
import cz.upce.fei.inptp.databasedependency.entity.Person;
import cz.upce.fei.inptp.databasedependency.entity.Role;

/**
 * Authorization service. User is authorized to access specified part of system,
 * if he has required access directly to specified part, or to upper level part.
 */
public class AuthorizationService implements IAuthorizationService {

    private IPersonDAO persondao;
    private IPersonRolesDAO personRolesDao;

    @Override
    public void setPersonDao(PersonDAO personDao) {
        this.persondao = personDao;
    }

    @Override
    public void setPersonRolesDao(PersonRolesDAO personRolesDao) {
        this.personRolesDao = personRolesDao;
    }

    @Inject
    public AuthorizationService(PersonDAO personDAO, IPersonRolesDAO personRolesDao) {
        this.persondao = personDAO;
        this.personRolesDao = personRolesDao;
    }

    // TODO: add tests
    // TODO: Authorize(person, "/section/subsection", rw) - PersonRole([Role("/section/subsection", rw)]) - pass
    // TODO: Authorize(person, "/section/subsection", rw) - PersonRole([Role("/section/subsection", ro)]) - fail
    // TODO: Authorize(person, "/section/subsection", rw) - PersonRole([Role("/section", rw)]) - pass
    // TODO: Authorize(person, "/section/subsection", rw) - PersonRole([Role("/section", ro)]) - fail
    // TODO: Authorize(person, "/section/subsection", rw) - PersonRole([Role("/", rw)]) - pass
    // TODO: Authorize(person, "/section/subsection", rw) - PersonRole([Role("/", ro)]) - fail
    // TODO: Authorize(person, "/section/subsection", rw) - PersonRole([Role("/section/subsection", admin)]) - pass
    // TODO: Authorize(person, "/section/subsection", rw) - PersonRole([Role("/section", admin)]) - pass
    // TODO: Authorize(person, "/section/subsection", rw) - PersonRole([Role("/", admin)]) - pass
    @Override
    public boolean Authorize(Person person, String section, AccessOperationType operationType) {
        String roleWhere = persondao.getRoleWhereStringFor(person);

        PersonRole roles = personRolesDao.load(roleWhere);
        if (roles == null) {
            return false;
        }

        do {
            for (Role role : roles.getRoles()) {
                if (role.getSection().equals(section)) {
                    if (role.getAccess().equals(operationType.getOp())) {
                        return true;
                    }

                    if (role.getAccess().equals("admin")) {
                        return true;
                    }

                    return false;
                }
            }

            section = getUpperLever(section);
            //System.out.println("newsection " + section);
        } while (!section.equals(""));

        return false;
    }

    // TODO: add tests
    // TODO: "/section/subsection/subsubsection" -> "/section/subsection"
    // TODO: "/section/subsection" -> "/section"
    // TODO: "/section" -> "/"
    // TODO: "/" -> ""
    private String getUpperLever(String section) {
        if (section.equals("/")) {
            return "";
        }

        String ret = section.substring(0, section.lastIndexOf("/") + 1);
        if(ret.equals("/")) return "/";
        else return ret.substring(0, ret.length() - 1);
    }

}
