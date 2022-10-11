package ecl.calculator.holidayPayCalculator.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ecl.calculator.holidayPayCalculator.service.CalculateService;
/*
 Контроллер с одним описанным запросом, который принимает среднюю 
 зарплату за 12 месяцев и количество дней отпуска. Также принимает коллекцию Map,
 где ключем является дата дня отпуска, а значение обозначает день недели или метку праздничного дня.
 
 По коллекции протсходит фильтрация, в ходе которой подсчитывается количество неоплачиваемых дней 
 */
@Controller
public class CalculateController {

    CalculateService calculateService;

    public CalculateController(CalculateService calculateService){
        this.calculateService=calculateService;
    }

    @GetMapping("/calculact")
    @ResponseBody 
    public float calculate(@RequestParam float averageSalary,
                          @RequestParam @Nullable Long countOfVacationDays,
                          @RequestParam @Nullable Map<String,String> vacationDays){
        
        vacationDays.remove("averageSalary");
        vacationDays.remove("countOfVacationDays");

        if(countOfVacationDays==null&&vacationDays.size()==0||averageSalary<0) {
        	return 0;
        }
        return calculateService.calculateSumOfSalaryForVacation(averageSalary,countOfVacationDays,vacationDays);
    }
}
