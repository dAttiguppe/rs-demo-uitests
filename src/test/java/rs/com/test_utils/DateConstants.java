package rs.com.test_utils;

import org.joda.time.format.DateTimeFormat;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateConstants {
    public static final DateTimeFormatter DATE_TIME_FORMATTER_E_DD_MMM_YYYY = DateTimeFormatter.ofPattern("E dd MMM yyyy").withZone(ZoneId.of("UTC"));
    public static final DateTimeFormatter DATE_TIME_FORMATTER_E_MMM_DD_YYYY = DateTimeFormatter.ofPattern("E MMM dd yyyy").withZone(ZoneId.of("UTC"));
    public static final DateTimeFormatter TIME_FORMATTER_HH_COLON_MM = DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneId.of("UTC"));
    public static final DateTimeFormatter TIME_FORMATTER_HHMM = DateTimeFormatter.ofPattern("HHmm").withZone(ZoneId.of("UTC"));
    public static final DateTimeFormatter DATE_TIME_FORMATTER_DD_MM_YYYY = DateTimeFormatter.ofPattern("dd-MM-yyyy").withZone(ZoneId.of("UTC"));
    public static final DateTimeFormatter DATE_TIME_FORMATTER_DD_MM_YYYY_NOZONE = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final DateTimeFormatter DATE_FORMATTER_DD_MM_YYYY = DateTimeFormatter.ofPattern("dd/MM/yyyy").withZone(ZoneId.of("UTC"));
    public static final DateTimeFormatter DATE_FORMATTER_DD_MMM = DateTimeFormatter.ofPattern("dd MMM").withZone(ZoneId.of("UTC"));
    public static final DateTimeFormatter DATE_FORMATTER_DD_MMMM_YYYY = DateTimeFormatter.ofPattern("dd-MMMM-yyyy").withZone(ZoneId.of("UTC"));
    public static final DateTimeFormatter DATE_TIME_FORMATTER_DD_MM_YYYY_HH_MM_SS = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss").withZone(ZoneId.of("UTC"));
    public static final DateTimeFormatter DATE_TIME_FORMATTER_DD_MMMM_YYYY = DateTimeFormatter.ofPattern("dd MMMM yyyy").withZone(ZoneId.of("UTC"));
    public static final DateTimeFormatter DATE_TIME_FORMATTER_DD_MM_YYYY_HH_MM = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm").withZone(ZoneId.of("UTC"));
    public static final DateTimeFormatter DATE_TIME_FORMATTER_YYYY_MM_DD_HH_MM_SS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("UTC"));
    public static final DateTimeFormatter DATE_TIME_FORMATTER_E_DD_MMM_YY = DateTimeFormatter.ofPattern("E dd MMM yy");
    public static final org.joda.time.format.DateTimeFormatter DATE_TIME_FORMATTER_EE_DD_MMM_YY = DateTimeFormat.forPattern("EE dd MMM YY");
    public static final org.joda.time.format.DateTimeFormatter DATE_TIME_FORMATTER_DD_MMM_YY = DateTimeFormat.forPattern("dd-MM-YYYY");
}
