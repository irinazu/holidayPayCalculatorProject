package ecl.calculator.holidayPayCalculator.service;

import java.util.Map;

import org.apache.commons.math3.util.Precision;
import org.springframework.stereotype.Service;

/*
 В данном классе проверяем входные параметры и расчитываем за
 */
@Service
public class CalculateService {
	 public float calculateSumOfSalaryForVacation(float averageSalary, Long countOfVacationDays, Map<String,String> vacationDays) {
		 	
	        float sumOfSalaryForVacationDays = 0;

	        if (vacationDays != null && vacationDays.size() != 0) {
	        	countOfVacationDays=(long) vacationDays.size();
	            Long dayWithoutSalary = vacationDays
	                    .values()
	                    .stream()
	                    .filter(x -> x.equals("day off") || x.equals("sunday") || x.equals("saturday"))
	                    .count();

	            countOfVacationDays = countOfVacationDays - dayWithoutSalary;
	        }

			if(countOfVacationDays>0) {
		        sumOfSalaryForVacationDays = (float) (averageSalary / 29.3 * countOfVacationDays);
		        sumOfSalaryForVacationDays=Precision.round(sumOfSalaryForVacationDays, 2);
		        return sumOfSalaryForVacationDays;
			}
			return 0;
	    }
}
