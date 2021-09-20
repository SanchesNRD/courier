package by.epam.courierexchange._main;

import by.epam.courierexchange.exception.DaoException;
import by.epam.courierexchange.exception.ServiceException;
import by.epam.courierexchange.model.dao.AddressDao;
import by.epam.courierexchange.model.dao.impl.AddressDaoImpl;
import by.epam.courierexchange.model.entity.Address;
import by.epam.courierexchange.model.entity.User;
import by.epam.courierexchange.model.service.impl.UserServiceImpl;
import by.epam.courierexchange.model.validator.UserValidator;
import by.epam.courierexchange.util.PasswordEncryption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Optional;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String... args) {
//        UserServiceImpl service = new UserServiceImpl();
//        try {
//            Optional<User> user;
//           user = service.authorization("Zefix", "");
//            System.out.println(user.get().toString());
//        } catch (ServiceException e) {
//            System.out.println("error");
//        }
//        String str = "$2a$10$RQzI/sqn2tQTud9DKslSJOFjTdR7W49/ZUr397mM7s302f2gdaGMC";
//        String str2 = PasswordEncryption.encode("a12345678");
//        System.out.println(str);
//        System.out.println(str2);
//        for (char i: str.toCharArray()) {
//            System.out.print(i + " ");
//        }
//        System.out.println();
//        for(char i: str2.toCharArray()){
//            System.out.print(i + " ");
//        }
//        System.out.println();
//        boolean bool = str.equals(str2);
//        boolean bool2 = PasswordEncryption.matches(PasswordEncryption.encode("a12345678"), "$2a$10$RQzI/sqn2tQTud9DKslSJOFjTdR7W49/ZUr397mM7s302f2gdaGMC");
//        System.out.println(bool);
//        System.out.println(bool2);
    }
}
