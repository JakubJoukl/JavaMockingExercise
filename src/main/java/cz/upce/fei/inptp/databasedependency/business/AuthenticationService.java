package cz.upce.fei.inptp.databasedependency.business;

import com.google.inject.Inject;
import cz.upce.fei.inptp.databasedependency.dao.IPersonDAO;
import cz.upce.fei.inptp.databasedependency.entity.Person;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Authentication service - used for authentication of users stored in db.
 * Authentication should success if login and password (hashed) matches.
 */
public class AuthenticationService implements IAuthenticationService {

    private IPersonDAO persondao;

    @Inject
    public AuthenticationService(IPersonDAO personDAO) {
        this.persondao = personDAO;
    }

    // TODO: add tests
    // TODO: Authenticate("user", "pass") - Person("user", encryptPwd("pass")) - pass
    // TODO: Authenticate("user", "invalid") - Person("user", encryptPwd("pass")) - fail
    // TODO: Authenticate("user", "pass") - nonexistent person - fail
    @Override
    public boolean Authenticate(String login, String password) {
        Person person = persondao.load("name = '" + login + "'");
        if (person == null) {
            return false;
        }

        return person.getPassword().equals(encryptPassword(password));
    }

    public static String encryptPassword(String password) {
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));

            return new BigInteger(1, crypt.digest()).toString(16);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(AuthenticationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
