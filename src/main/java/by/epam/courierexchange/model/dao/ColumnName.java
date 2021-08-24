package by.epam.courierexchange.model.dao;

public class ColumnName {
    //default for everyone
    public static final String ID = "id";
    public static final String TYPE = "type";
    public static final String STATUS = "status";
    public static final String STATUS_ID = "status_id";
    public static final String TYPE_ID = "type_id";

    //users
    public static final String USER_ID="users.id";
    public static final String USER_LOGIN = "login";
    public static final String USER_PASSWORD = "password";
    public static final String USER_MAIL = "mail";
    public static final String USER_NAME = "name";
    public static final String USER_SURNAME = "surname";
    public static final String USER_PHONE = "phone";

    //clients
    public static final String ADDRESS_ID = "address_id";
    //products
    public static final String PRODUCT_WEIGHT = "weight";
    public static final String PRODUCT_LENGTH = "length";
    public static final String PRODUCT_WIDTH = "width";
    public static final String PRODUCT_HEIGHT = "height";
    public static final String PRODUCT_MULTIPLIER = "multiplier";
    //client_product
    public static final String CLIENT_ID ="client_id";
    public static final String PRODUCT_ID = "product_id";

    //couriers
    public static final String COURIER_RATING = "rating";
    //transport
    public static final String TRANSPORT_NAME = "name";
    public static final String TRANSPORT_AVERAGE_SPEED = "average_speed";
    public static final String TRANSPORT_IMAGE = "image";
    public static final String TRANSPORT_MAX_PRODUCT_WEIGHT = "max_product_weight";
    //courier_transport
    public static final String COURTIER_ID = "courier_id";
    public static final String TRANSPORT_ID = "transport_id";

    //addresses
    public static final String ADDRESS_COUNTRY = "country";
    public static final String ADDRESS_CITY = "city";
    public static final String ADDRESS_STREET = "street";
    public static final String ADDRESS_STREET_NUMBER = "street_number";
    public static final String ADDRESS_APARTMENT ="apartment";

    //orders
    public static final String ORDER_DATE = "date";
}
