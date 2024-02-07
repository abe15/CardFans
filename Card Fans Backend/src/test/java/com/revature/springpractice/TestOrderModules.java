package com.revature.springpractice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.runner.RunWith;
//import org.junit.jupiter.api.Test;
//import org.junit.Test;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.revature.cardfans.CardFans;
import com.revature.cardfans.controllers.UserController;
import com.revature.cardfans.dao.OrderDao;
import com.revature.cardfans.dao.ProductDao;
import com.revature.cardfans.dao.UserDao;
import com.revature.cardfans.models.Order;
import com.revature.cardfans.models.OrderItem;
import com.revature.cardfans.models.Product;
import com.revature.cardfans.models.User;
import com.revature.cardfans.models.payload.OrderEntry;
import com.revature.cardfans.models.payload.PlaceOrderRequest;
import com.revature.cardfans.models.payload.UserJwtInfo;
import com.revature.cardfans.services.IUserService;
import com.revature.cardfans.services.OrderService;
import com.revature.cardfans.services.OrderServiceImpl;
import com.revature.cardfans.services.ProductService;
import com.revature.cardfans.services.ProductServiceImpl;
import com.revature.cardfans.services.UserServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.restdocs.*;
import org.springframework.restdocs.mockmvc.*;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
@SpringBootTest(classes = CardFans.class)
public class TestOrderModules {

        @Autowired
        private WebApplicationContext context;

        private MockMvc mockMvc;
        private static List<Product> prodList = new ArrayList<Product>();
        private static List<User> userList = new ArrayList<User>();
        private static List<Order> orderList = new ArrayList<Order>();
        private static IUserService userService;
        private static UserDao userDao;
        private static Authentication auth;
        private static OrderService orderService;
        private static OrderDao orderDao;
        private static PlaceOrderRequest order1;

        public TestOrderModules() {
        }

        @BeforeEach
        public void setUp(WebApplicationContext webApplicationContext,
                        RestDocumentationContextProvider restDocumentation) {
                this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                                .apply(documentationConfiguration(restDocumentation))
                                .build();

                userDao = Mockito.mock(UserDao.class);
                orderDao = Mockito.mock(OrderDao.class);
                auth = Mockito.mock(Authentication.class);

                userList.add(new User(1, "abe", "barboza", "abe", "abe@gmail.com", "password", "123 new york st",
                                "new york",
                                "new York", "78960", null, null));
                userList.add(new User(3, "john", "smith", "john", "john@gmail.com", "password", "123 new york st",
                                "new york",
                                "new York", "78960", null, null));
                prodList.add(new Product(
                                1,
                                "Aviary",
                                "red, black",
                                "standard",
                                "birds",
                                5.99,
                                "Bird lovers from all over flock together to find a copy of this lovely deck",
                                17,
                                "standard", ""));
                prodList.add(new Product(4,
                                "Dragon",
                                "red, black",
                                "fancy",
                                "myth",
                                5.99,
                                "Mythical beasts of majesty dawn the backs of these gorgeous cards",
                                77,
                                "fancy", ""));
                prodList.add(new Product(7,
                                "Stargazer",
                                "same",
                                "standard",
                                "space",
                                5.99,
                                "Lovers of the stars unite! These cards will let you see far into the reaches of outer space any time of day",
                                33,
                                "standard", ""));

                // private int userId;
                // private List<OrderItemRequest> items;
                // private int productId;

                // private int quantity;
                order1 = new PlaceOrderRequest();
                order1.setUserId(1);

                OrderEntry o = new OrderEntry();
                o.setProductId(150);
                o.setQuantity(3);
                order1.insertOrderItem(o);

                Order o4 = new Order();
                o4.setOrderId(1);
                o4.setUser(userList.get(0));
                Order o5 = new Order();
                o5.setOrderId(2);
                o5.setUser(userList.get(0));
                Order o6 = new Order();
                o6.setOrderId(3);
                o6.setUser(userList.get(1));
                orderList.add(o4);
                orderList.add(o5);
                orderList.add(o6);
                /*
                 * OrderItem o1 = new OrderItem();
                 * o1.setOrderItemId(101);
                 * o1.setOrder(order1);
                 * o1.setProduct(prodList.get(0));
                 * o1.setQuantity(2);
                 * 
                 * order1.insertOrderItem(o1);
                 * 
                 * OrderItem o2 = new OrderItem();
                 * o2.setOrderItemId(201);
                 * o2.setOrder(order1);
                 * o2.setProduct(prodList.get(1));
                 * o2.setQuantity(5);
                 * order1.insertOrderItem(o2);
                 */
        }

        @AfterEach
        public void resetMockAfter() throws Exception {
                Mockito.reset(userDao);
                Mockito.reset(orderDao);
                Mockito.reset(auth);
        }

        @DisplayName("1. Test Order controller,HTTP.POST, Create order")
        @Test
        @WithMockUser(username = "anon2")
        public void testGetOrderByUserIdController() throws Exception {
        order1.setAddress1("asd");
        order1.setCity("asdsda");
        order1.setCountry("USA");
        order1.setPhoneNumber("9890-8980");
        order1.setEmail("asd@gmail.com");
                order1.setTotal(100.00);
                order1.setFirstName("abe");
                order1.setLastName("bar");
                order1.setAddress2("asdasd");
                order1.setZipCode("1231");
                order1.setState("asdasd");
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
                ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
                String requestJson = ow.writeValueAsString(order1);
                when(auth.getPrincipal()).thenReturn(new UserJwtInfo(userList.get(0).getUsername(), 1));
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders").principal(auth)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson).accept(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.orderId").exists())
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andDo(MockMvcResultHandlers.print())
                                .andDo(document("index"));

        }

        @DisplayName("2. Test Order service,HTTP.GET, Get orders by userid")
        @Test
        public void testOrderServiceFindByid() throws Exception {

                orderService = new OrderServiceImpl(orderDao);
                List<Order> filteredList = orderList.stream()
                                .filter(p -> p.getUser().getUserId() == 1).collect(Collectors.toList());
                when(orderDao.findByUser_UserId(1)).thenReturn(filteredList);

                // act (do the service call)
                List<Order> result = orderService.getOrdersByUserId(1);
                assertEquals(filteredList, result);
                verify(orderDao, times(1)).findByUser_UserId(1);

        }

}
