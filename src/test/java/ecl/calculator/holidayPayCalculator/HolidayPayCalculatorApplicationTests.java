package ecl.calculator.holidayPayCalculator;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import ecl.calculator.holidayPayCalculator.controller.CalculateController;

@SpringBootTest
@AutoConfigureMockMvc
class HolidayPayCalculatorApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void returnVacationPay() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/calculact")
				.param("averageSalary", "100000")
				.param("countOfVacationDays","28");
        MvcResult result = mockMvc.perform(request).andReturn();
        String content = result.getResponse().getContentAsString();
        assertEquals("95563.14", content);
        
	}
	
 		//В результате приходит сумма, рассчитанная из добавленных дней с вычитанием 
		//выходных и праздников, если приходят и countOfVacationDays, и сами дни, расчет идет по отмеченным дням
	@Test
	public void returnVacationPayWithoutHolidays() throws Exception {
	        RequestBuilder request = MockMvcRequestBuilders.get("/calculact")
					.param("averageSalary", "100000")
					.param("countOfVacationDays","14")
					.param("07.03.2022","monday")
					.param("08.03.2022","day off")
					.param("09.03.2022","wednesday")
					.param("10.03.2022","thursday")
					.param("11.03.2022","friday")
					.param("12.03.2022","saturday")
					.param("13.03.2022","sunday")
					.param("14.03.2022","monday")
					.param("15.03.2022","tuesday")
					.param("16.03.2022","wednesday")
					.param("17.03.2022","thursday")
					.param("18.03.2022","friday")
					.param("19.03.2022","saturday")
					.param("20.03.2022","sunday")
					.param("21.03.2022","monday")
					.param("22.03.2022","tuesday")
					.param("23.03.2022","wednesday")
					.param("24.03.2022","thursday")
					.param("25.03.2022","friday");
	        MvcResult result = mockMvc.perform(request).andReturn();
	        String content = result.getResponse().getContentAsString (); 
	        assertEquals("47781.57", content);
	    }
	 	
	 	//В результате приходит сумма, так как рассчет по дням
	 	@Test
	    public void withWrongVacationDays() throws Exception {
	        RequestBuilder request = MockMvcRequestBuilders.get("/calculact")
					.param("averageSalary", "100000")
					.param("countOfVacationDays","0")
					.param("07.03.2022","monday")
					.param("08.03.2022","day off")
					.param("09.03.2022","wednesday")
					.param("12.03.2022","saturday")
					.param("13.03.2022","sunday")
					.param("14.03.2022","monday");
	        MvcResult result = mockMvc.perform(request).andReturn();
	        String content = result.getResponse().getContentAsString (); 
	        assertEquals("10238.91", content);
	    }
	 	
	 	//В результате приходит ноль, так как при проверке оказывается, что Salary некорректна
	 	@Test
	    public void withWrongSalary() throws Exception {
	        RequestBuilder request = MockMvcRequestBuilders.get("/calculact")
					.param("averageSalary", "0")
					.param("countOfVacationDays","5")
					.param("07.03.2022","monday")
					.param("08.03.2022","day off")
					.param("09.03.2022","wednesday")
					.param("12.03.2022","saturday")
					.param("13.03.2022","sunday")
					.param("14.03.2022","monday");
	        MvcResult result = mockMvc.perform(request).andReturn();
	        String content = result.getResponse().getContentAsString (); 
	        assertEquals("0.0", content);
	    }
	 	
	 	//В результате приходит ноль, так как при проверке оказывается, что Salary и VacationDays некорректны
	 	@Test
	    public void withNegativeSalaryAndVacationDays() throws Exception {
	        RequestBuilder request = MockMvcRequestBuilders.get("/calculact")
					.param("averageSalary", "-8")
					.param("countOfVacationDays","-5")
					.param("07.03.2022","monday")
					.param("08.03.2022","day off")
					.param("09.03.2022","wednesday")
					.param("12.03.2022","saturday")
					.param("13.03.2022","sunday")
					.param("14.03.2022","monday");
	        MvcResult result = mockMvc.perform(request).andReturn();
	        String content = result.getResponse().getContentAsString (); 
	        assertEquals("0.0", content);	        
	    }
	 	
 		//В результате приходит ноль, так как при проверке оказывается, что Salary некорректна
	 	@Test
	    public void wrongSalary() throws Exception {
	        RequestBuilder request = MockMvcRequestBuilders.get("/calculact")
					.param("averageSalary", "-8")
					.param("07.03.2022","monday")
					.param("08.03.2022","day off")
					.param("09.03.2022","wednesday")
					.param("12.03.2022","saturday")
					.param("13.03.2022","sunday")
					.param("14.03.2022","monday");
	        MvcResult result = mockMvc.perform(request).andReturn();
	        String content = result.getResponse().getContentAsString (); 
	        assertEquals("0.0", content);	        
	    }
	 	
 		//В результате приходит ноль, так как при проверке оказывается, что данные некорректны
	 	@Test
	    public void withoutDays() throws Exception {
	        RequestBuilder request = MockMvcRequestBuilders.get("/calculact")
					.param("averageSalary", "100000");

	        MvcResult result = mockMvc.perform(request).andReturn();
	        String content = result.getResponse().getContentAsString (); 
	        assertEquals("0.0", content);	        
	    }
	 	
		//В результате приходит сумма, которая рассчитывается исходя из отмеченных дней
	 	@Test
	    public void withDays() throws Exception {
	        RequestBuilder request = MockMvcRequestBuilders.get("/calculact")
					.param("averageSalary", "100000")
					.param("07.03.2022","monday")
					.param("08.03.2022","day off")
					.param("09.03.2022","wednesday")
					.param("12.03.2022","saturday")
					.param("13.03.2022","sunday")
					.param("14.03.2022","monday");
	        MvcResult result = mockMvc.perform(request).andReturn();
	        String content = result.getResponse().getContentAsString (); 
	        assertEquals("10238.91", content);	        
	    }
}
