package com.ibexsys.toolkit.datetime;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
//import org.springframework.format.datetime.standard.DateTimeConverters;

// Java 8 Date Time Toolkit Examples
// https://www.foreach.be/blog/java-8-date-and-time?
@SuppressWarnings("unused")
public class DzoneJava8DateTimeExamples {

	public DzoneJava8DateTimeExamples() {

		LocalDate.of(2018, 2, 13);
		LocalDate.parse("2018-02-13");

		LocalTime.of(6, 30);
		LocalTime.parse("06:30");

		LocalDateTime.of(2018, 2, 13, 6, 30);
		LocalDateTime.parse("2018-02-13T06:30");

	}

	public void localDateTimeConversion() {

		// LocalDate to LocalDateTime
		LocalDateTime dateTime = LocalDate.parse("2018-02-13").atTime(LocalTime.parse("06:30"));

		LocalTime.parse("06:30").atDate(LocalDate.parse("2018-02-13"));

		LocalDateTime.parse("2018-02-13T06:30").toLocalDate();
		LocalDateTime.parse("2018-02-13T06:30").toLocalTime();
		
		LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
		
		// Conversion back to java.util.date
		// represents Wed Feb 28 23:24:43 CET 2018
		Date now = new Date();

		LocalDateTime.ofInstant(now.toInstant(), ZoneId.systemDefault());

		Date.from(dateTime.toInstant(ZoneOffset.ofHours(1)));
		Date.from(dateTime.toInstant(ZoneId.systemDefault().getRules().getOffset(dateTime)));

	}

	public void dateTimeSimpleAdjustmentsExamples() {

		LocalDate.parse("2018-02-13").plusDays(5);
		LocalDate.parse("2018-02-13").plus(3, ChronoUnit.MONTHS);

		LocalTime.parse("06:30").minusMinutes(30);
		LocalTime.parse("06:30").minus(500, ChronoUnit.MILLIS);

		LocalDateTime.parse("2018-02-13T06:30").plus(Duration.ofHours(2));

		LocalDate.parse("2018-02-13").with(TemporalAdjusters.lastDayOfMonth());

	}
	
	public void durationAndPeriodExamples() {
		
		//	As you’ve noticed, in one of the above examples we’ve used a Duration object. 
		//	Duration and Period are two representations of time between two dates, 
		//	the former representing the difference of time in seconds and nanoseconds, 
		//	the latter in days, months and years.
		
		
		// Returns Value in day,months,years
		Period period = Period.between(LocalDate.parse("2018-01-18"), LocalDate.parse("201"
				+ "8-02-14"));
		
		// Returns difference in seconds and nanoseconds
		Duration duration = Duration.between(LocalDateTime.parse("2018-01-18T06:30"), 
				LocalDateTime.parse("2018-02-14T22:58"));
		
		// represents PT664H28M
		// We can parse the duration object using the ChronoUnit enumeration
		Duration duration2 = Duration.between(LocalDateTime.parse("2018-01-18T06:30"), LocalDateTime.parse("2018-02-14T22:58"));

		// returns 664
		long hours = duration2.toHours();

		// returns 664
		long hours2 = LocalDateTime.parse("2018-01-18T06:30").until(LocalDateTime.parse("2018-02-14T22:58"), ChronoUnit.HOURS);
	}
	
	public void ZonedDateAndZonedOffsetTime() {
		
		OffsetDateTime offsetDateTime = LocalDateTime.parse("2018-02-14T06:30").atOffset(ZoneOffset.ofHours(2));
		// Uses DateTimeFormatter.ISO_OFFSET_DATE_TIME for which the default format is
		// ISO_LOCAL_DATE_TIME followed by the offset ("+HH:mm:ss").
		OffsetDateTime offsetDateTime2 = OffsetDateTime.parse("2018-02-14T06:30+06:00");

		ZonedDateTime zonedDateTime = LocalDateTime.parse("2018-02-14T06:30").atZone(ZoneId.of("Europe/Paris"));
		// Uses DateTimeFormatter.ISO_ZONED_DATE_TIME for which the default format is
		// ISO_OFFSET_DATE_TIME followed by the the ZoneId in square brackets.
		ZonedDateTime zonedDateTime2 = ZonedDateTime.parse("2018-02-14T06:30+08:00[Asia/Macau]");
		// note that the offset does not matter in this case.
		// The following example will also return an offset of +08:00
		ZonedDateTime zonedDateTime3 = ZonedDateTime.parse("2018-02-14T06:30+06:00[Asia/Macau]");
		
	
		// When switching between them, you have to keep in mind that converting from a ZonedDateTime to OffsetDateTime 
		// will take daylight saving time into account, while converting in the other direction, from OffsetDateTime to 
		// ZonedDateTime, means you will not have information about the region of the zone, nor will there be any rules
		// applied for daylight saving time.
		
		
		ZonedDateTime winter = LocalDateTime.parse("2018-01-14T06:30").atZone(ZoneId.of("Europe/Paris"));
		ZonedDateTime summer = LocalDateTime.parse("2018-08-14T06:30").atZone(ZoneId.of("Europe/Paris"));

		// offset will be +01:00
		winter.toOffsetDateTime();
		// offset will be +02:00
		summer.toOffsetDateTime();

		zonedDateTime.toOffsetDateTime();

		LocalDateTime.parse("2018-02-14T06:30").atOffset(ZoneOffset.ofHours(5));
		offsetDateTime.toZonedDateTime();


		// offset will be +01:00
		winter.toOffsetDateTime();
		// offset will be +02:00
		summer.toOffsetDateTime();
		zonedDateTime.toOffsetDateTime();
		LocalDateTime.parse("2018-02-14T06:30").atOffset(ZoneOffset.ofHours(5));
		offsetDateTime.toZonedDateTime();
		
		// More Conversion
		// timeInMacau represents 2018-02-14T13:30+08:00[Asia/Macau]
		ZonedDateTime timeInMacau = LocalDateTime.parse( "2018-02-14T13:30" ).atZone( ZoneId.of( "Asia/Macau" ) );
		// timeInParis represents 2018-02-14T06:30+01:00[Europe/Paris]
		ZonedDateTime timeInParis = timeInMacau.withZoneSameInstant( ZoneId.of( "Europe/Paris" ) );
		OffsetDateTime offsetInMacau = LocalDateTime.parse( "2018-02-14T13:30" ).atOffset( ZoneOffset.ofHours( 8 ) );
		OffsetDateTime offsetInParis = offsetInMacau.withOffsetSameInstant( ZoneOffset.ofHours( 1 ) );
	}
	
	public void formatterExamples() {
		
		// Let’s say we want to convert all of patterns mentioned above.
		// 09-23-2018, 23/09/2018 and 2018-09-23 should all convert to the same LocalDate.
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[yyyy-MM-dd][dd/MM/yyyy][MM-dd-yyyy]");
		LocalDate.parse("09-23-2018", formatter);
		LocalDate.parse("23/09/2018", formatter);
		LocalDate.parse("2018-09-23", formatter);
		
		// Using the DateTimeFormatterBuilder
		
		DateTimeFormatter formatterByBuidler = new DateTimeFormatterBuilder()
				.appendOptional( DateTimeFormatter.ofPattern( "yyyy-MM-dd" ) )
				.optionalStart().appendPattern( "dd/MM/yyyy" ).optionalEnd()
				.optionalStart().appendPattern( "MM-dd-yyyy" ).optionalEnd()
				.toFormatter();
		
	}
	
	public void springDateTimeConvertersExampler() {
		
		ZonedDateTime zonedDateTime = LocalDateTime.parse("2018-01-14T06:30").atZone(ZoneId.of("Asia/Macau"));
		// will represent 2018-01-14T06:30, regardless of the region your application has specified
		//LocalDateTime localDateTime = conversionService.convert(zonedDateTime, LocalDateTime.class);
	}
	
	

}
