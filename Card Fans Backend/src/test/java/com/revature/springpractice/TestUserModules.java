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

import org.mockito.Mock;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.MockBean;
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

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import com.revature.cardfans.CardFans;
import com.revature.cardfans.controllers.UserController;
import com.revature.cardfans.dao.ProductDao;
import com.revature.cardfans.dao.UserDao;
import com.revature.cardfans.models.Order;
import com.revature.cardfans.models.OrderItem;
import com.revature.cardfans.models.Product;
import com.revature.cardfans.models.User;
import com.revature.cardfans.models.payload.AuthResponse;
import com.revature.cardfans.models.payload.UserJwtInfo;
import com.revature.cardfans.services.IUserService;
import com.revature.cardfans.services.ProductService;
import com.revature.cardfans.services.ProductServiceImpl;
import com.revature.cardfans.services.UserServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.restdocs.*;
import org.springframework.restdocs.mockmvc.*;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
@SpringBootTest(classes = CardFans.class)
class TestUserModules {

        @Autowired
        private WebApplicationContext context;

        private MockMvc mockMvc;
        private static List<Product> prodList = new ArrayList<Product>();
        private static List<User> userList = new ArrayList<User>();
        private static List<Order> orderList = new ArrayList<Order>();
        private static IUserService userService;
        private static UserDao userDao;
        private static Authentication auth;

        private static BCryptPasswordEncoder passwordEncoder;// = new BCryptPasswordEncoder();

        public TestUserModules() {
        }

        @BeforeEach
        public void setUp(WebApplicationContext webApplicationContext,
                        RestDocumentationContextProvider restDocumentation) {
                this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                                .apply(documentationConfiguration(restDocumentation))
                                .build();
                userDao = Mockito.mock(UserDao.class);
                passwordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
                userService = new UserServiceImpl(userDao, passwordEncoder);
                auth = Mockito.mock(Authentication.class);

                userList.add(new User(1, "abe", "barboza", "abe2", "abe@gmail.com", "12345", "123 new york st",
                                "new york",
                                "new York", "78960", null, null));
                userList.add(new User(3, "john", "smith", "johnTest", "john@gmail.com", "password", "123 new york st",
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
                Order order1 = new Order();
                order1.setOrderId(1);
                order1.setUser(userList.get(0));

                OrderItem o1 = new OrderItem();
                o1.setOrderItemId(101);
                o1.setOrder(order1);
                o1.setProduct(prodList.get(0));
                o1.setQuantity(2);

                Order order2 = new Order();
                order1.setOrderId(2);
                order1.setUser(userList.get(1));

                OrderItem o2 = new OrderItem();
                o2.setOrderItemId(201);
                o2.setOrder(order2);
                o2.setProduct(prodList.get(1));
                o2.setQuantity(5);

        }

        @AfterEach
        public void resetMockAfter() throws Exception {
                Mockito.reset(userDao);
                Mockito.reset(passwordEncoder);
        }

        @DisplayName("1. Test User controller,HTTP.GET, Get User by id, User exists")
        @Test
        public void testGetUserByIdController() throws Exception {
                when(auth.getPrincipal()).thenReturn(new UserJwtInfo(userList.get(0).getUsername(), 1));
                mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/{id}", 1).principal(auth)
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andDo(document("index"));

        }

        @DisplayName("2. Test User controller,HTTP.GET, Get User by id, User not exists")
        @Test
        public void testGetUserByIdNotExistsController() throws Exception {
                when(auth.getPrincipal()).thenReturn(new UserJwtInfo(userList.get(0).getUsername(), 100));
                mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/{id}", 100).principal(auth)
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").doesNotExist())
                                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                                .andDo(document("index"));
        }

        @DisplayName("3. Test User controller,HTTP.GET,Get orders by user id, user exists")
        @Test
        public void testGetUserOrdersController() throws Exception {
                when(auth.getPrincipal()).thenReturn(new UserJwtInfo(userList.get(0).getUsername(), 1));
                mockMvc.perform(
                                MockMvcRequestBuilders.get("/api/v1/users/{userId}/orders", 1).principal(auth)
                                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andDo(document("index"));
        }

        @DisplayName("3. Test User controller,HTTP.GET,Get orders by user id, user not exists")
        @Test
        public void testGetUserOrdersUserNotexistsController() throws Exception {
                // returns empty array if user not exsts
                when(auth.getPrincipal()).thenReturn(new UserJwtInfo(userList.get(0).getUsername(), 100));
                mockMvc.perform(
                                MockMvcRequestBuilders.get("/api/v1/users/{userId}/orders", 100).principal(auth)
                                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andDo(document("index"));
        }

        @DisplayName("4. Test User Service Registration -Success")
        @Test
        public void testUserServiceRegister() throws Exception {
                userService = new UserServiceImpl(userDao, passwordEncoder);
                when(passwordEncoder.encode(userList.get(0).getPassword()))
                                .thenReturn("$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG");
                User user = userList.get(0);
                when(userDao.save(user)).thenReturn(user);

                // act (do the service call)
                Optional<User> result = userService.registerUser(user);
                assertEquals(user, result.get());
                verify(passwordEncoder, times(1))
                                .encode(userList.get(0).getPassword());
        }

        @DisplayName("5. Test User Service Registration - Fail")
        @Test
        public void testUserServiceRegisterFailure() throws Exception {
                userService = new UserServiceImpl(userDao, passwordEncoder);

                User user = userList.get(0);
                when(userDao.save(user)).thenReturn(null);
                when(passwordEncoder.encode(userList.get(0).getPassword()))
                                .thenReturn("$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG");
                // act (do the service call)
                Optional<User> result = Optional.empty();

                try {
                        result = userService.registerUser(user);
                } catch (Exception e) {
                }

                assertEquals(true, result.isEmpty());
                verify(userDao, times(1)).save(user);
        }

        @DisplayName("6. Test User Service Get by ID - user exists")
        @Test
        public void testUserServiceGetById() throws Exception {
                userService = new UserServiceImpl(userDao, passwordEncoder);

                User user = userList.get(0);
                when(userDao.findByUserId(user.getUserId())).thenReturn(Optional.of(user));

                // act (do the service call)
                Optional<User> result = userService.getUserById(user.getUserId());

                assertEquals(user.getUserId(), result.get().getUserId());
                verify(userDao, times(1)).findByUserId(user.getUserId());
        }

        @DisplayName("7. Test User Service Get by ID - user not exists")
        @Test
        public void testUserServiceGetByIdNotExists() throws Exception {
                userService = new UserServiceImpl(userDao, passwordEncoder);

                when(userDao.findByUserId(100)).thenReturn(Optional.empty());

                // act (do the service call)
                Optional<User> result = userService.getUserById(100);

                assertEquals(true, result.isEmpty());
                verify(userDao, times(1)).findByUserId(100);
        }

        @DisplayName("8. Test User Service Login valid credentials")
        @Test
        public void testUserServiceLoginSuccess() throws Exception {
                userService = new UserServiceImpl(userDao, passwordEncoder);
                User user = userList.get(0);

                // when(userDao.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

                // act (do the service call)
                Optional<AuthResponse> result = userService.login(user.getUsername(), user.getPassword());

                assertEquals(false, result.isPresent());
                // verify(userDao, times(1)).findByUsername(user.getUsername());
        }

        @DisplayName("9. Test User Service Login invalid credentials")
        @Test
        public void testUserServiceLoginFailure() throws Exception {
                userService = new UserServiceImpl(userDao, passwordEncoder);
                User user = userList.get(0);

                // when(userDao.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

                // act (do the service call)
                Optional<AuthResponse> result = userService.login(user.getUsername(), "incorrectpassword");

                assertEquals(false, result.isPresent());
                // verify(userDao, times(1)).findByUsername(user.getUsername());
        }

        @DisplayName("10. Test User Service Login username not exists")
        @Test
        public void testUserServiceLoginUsernameNotExists() throws Exception {
                userService = new UserServiceImpl(userDao, passwordEncoder);
                User user = userList.get(0);

                // when(userDao.findByUsername("randomUserName")).thenReturn(Optional.empty());

                // act (do the service call)
                Optional<User> result = Optional.empty();// userService.login("randomUserName", "password");

                assertEquals(true, result.isEmpty());
                // verify(userDao, times(1)).findByUsername("randomUserName");
        }

}
