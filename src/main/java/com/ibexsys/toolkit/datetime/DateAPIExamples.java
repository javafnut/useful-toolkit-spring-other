package com.ibexsys.toolkit.datetime;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateAPIExamples {
	
	Date currentDate;
    Calendar cal;
	
	public DateAPIExamples(){		
		cal = Calendar.getInstance();
		currentDate = cal.getTime();	
	};
	
    public Date getCurrentDate() {
		return currentDate;
	};
	
	
    public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
	
	public static long getDaysBetween(Date start, Date end, boolean incEndDay) {
		
		if (incEndDay) {
			end = addDays(end,1);
		}
			
		return getDaysBetween(start,end);
	}
	
	public static long getDaysBetween(Date start, Date end) {
	    long diff = end.getTime() - start.getTime();
	    return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}
	
	
	public static Period getCalendeSince(Date startDate)  {
			
		LocalDate date = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate now = LocalDate.now();
		 
		return(Period.between(date, now));	
	}
	
	
	public static Period getDatesBetween(Date startDate)  {
		
		LocalDate date = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate now = LocalDate.now();
		 
		return(Period.between(date, now));
		 
	
	}
 
 

}
