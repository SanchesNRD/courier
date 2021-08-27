package by.epam.courierexchange._main;

import by.epam.courierexchange.exception.DaoException;
import by.epam.courierexchange.exception.ServiceException;
import by.epam.courierexchange.model.dao.AddressDao;
import by.epam.courierexchange.model.dao.impl.AddressDaoImpl;
import by.epam.courierexchange.model.entity.Address;
import by.epam.courierexchange.model.entity.User;
import by.epam.courierexchange.model.service.impl.UserServiceImpl;
import by.epam.courierexchange.model.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String... args) {
        try {
            Optional<User> optionalUser = UserServiceImpl.getInstance().authorized("Zefix", "12345678");
            if(optionalUser.isEmpty()){
                System.out.println("empty");
            }
            else{
                System.out.println("not empty");
                System.out.println(optionalUser.get().toString());
            }
        } catch (ServiceException e) {
            System.out.println(e);
        }
    }
}
