package com.shyam.carsaleservice.testing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shyam.carsaleservice.entities.Car;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarRestControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @WithMockUser( roles = "ADMIN") //test adding a listing using ADMIN role
    @Test
    public void postMethodToCreateCar() throws Exception{
       Car car = new Car();
        car.setRegistration("test");
        car.setYear(1998);
        car.setMake("testMake");
        car.setCarModel("testModel");
        car.setPrice(21500.00);
        car.setColour("testColour");
        ObjectMapper JsonUtil = new ObjectMapper();
        String jsonReq = JsonUtil.writeValueAsString(car);

        MvcResult res = mockMvc.perform(post("/car/addListing").contentType(MediaType.APPLICATION_JSON).content(jsonReq)).andExpect(status().isOk()).andReturn();
        assertEquals(200,res.getResponse().getStatus());
        System.out.println("Add Car test done");
       // reset(service);
    }
    @WithMockUser( roles = "USER")
    @Test
    public void getMethodToCreateCar() throws Exception{ //test getting a listing using USER role


        MvcResult res = mockMvc.perform(get("/car/getListing/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        assertEquals(200,res.getResponse().getStatus());
        System.out.println("Get Car test done");
        // reset(service);
    }

    //more tests go here

}
