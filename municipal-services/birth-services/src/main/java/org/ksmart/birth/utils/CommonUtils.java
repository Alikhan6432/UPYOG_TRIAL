package org.ksmart.birth.utils;


import lombok.Data;
import org.ksmart.birth.birthregistry.model.RegisterBirthDetail;
import org.ksmart.birth.common.model.AuditDetails;
//import org.ksmart.birth.death.model.EgDeathDtl;
import org.springframework.stereotype.Component;

import lombok.Getter;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
@Getter
public class CommonUtils {

	public static Long currentDateTime() {
		LocalDateTime instance = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
		DateTimeFormatter formatter	= DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm");
		String formattedString = formatter.format(instance);
		Long dateTimeNow = LocalDateTime.now().atZone(ZoneId.of("Asia/Kolkata")).toInstant().toEpochMilli();
		return dateTimeNow;
	}

	public static LocalDate currentDate() {
		LocalDate instance = LocalDate.now(ZoneId.of("Asia/Kolkata"));
		return instance;
	}

//	public static LocalDate currentDate() {
//		LocalDate instance = LocalDate.now(ZoneId.of("Asia/Kolkata"));
////		ZoneId zone = ZoneId.of( "Asia/Calcutta") ;
////		ZonedDateTime zdt = ZonedDateTime.now(zone) ;
//		return instance;
//	}
public static String currentTime() {
	LocalTime instance = LocalTime.now(ZoneId.of("Asia/Kolkata"));
	DateTimeFormatter formatter	= DateTimeFormatter.ofPattern("hh:mm");
	String formattedString = formatter.format(instance);
	return formattedString;
}

//	public static String currentStringToTime(String time) {
//		//LocalTime instance = LocalTime.now(ZoneId.of("Asia/Kolkata"));
//		DateTimeFormatter formatter	= DateTimeFormatter.ofPattern("hh:mm");
//	//	String formattedString = formatter.format(time);
//		return formattedString;
//	}
	public static  LocalDateTime LongToDate(Long dateTime) {
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(dateTime), ZoneId.of("Asia/Kolkata"));
		return localDateTime;
	}

	public static AuditDetails buildAuditDetails(String by, Boolean create) {
		AuditDetails auditDetails;
		Long currentTime = currentDateTime();
		if (create) {
			auditDetails = AuditDetails.builder()
					.createdBy(by)
					.createdTime(currentTime)
					.lastModifiedBy(by)
					.lastModifiedTime(currentTime)
					.build();
		} else {
			auditDetails = AuditDetails.builder()
					.lastModifiedBy(by)
					.lastModifiedTime(currentTime)
					.build();
		}
		return auditDetails;
	}
}
