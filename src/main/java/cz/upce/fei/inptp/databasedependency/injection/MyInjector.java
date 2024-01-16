package cz.upce.fei.inptp.databasedependency.injection;

import com.google.inject.AbstractModule;
import cz.upce.fei.inptp.databasedependency.business.AuthenticationService;
import cz.upce.fei.inptp.databasedependency.business.IAuthenticationService;
import cz.upce.fei.inptp.databasedependency.business.AuthorizationService;
import cz.upce.fei.inptp.databasedependency.business.IAuthorizationService;
import cz.upce.fei.inptp.databasedependency.dao.*;

public class MyInjector extends AbstractModule {
    @Override
    protected void configure() {
        bind(AbstractDatabase.class).to(Database.class);
        bind(IPersonDAO.class).to(PersonDAO.class);
        bind(IAuthenticationService.class).to(AuthenticationService.class);
        bind(IAuthorizationService.class).to(AuthorizationService.class);
        bind(IPersonRolesDAO.class).to(PersonRolesDAO.class);
    }
}
